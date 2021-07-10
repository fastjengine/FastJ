package tech.fastj.systems.audio;

import tech.fastj.engine.CrashMessages;
import tech.fastj.engine.FastJEngine;

import tech.fastj.systems.audio.state.PlaybackState;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class StreamedAudioPlayer {

    public static final int BufferSize = 4096;
    private static ExecutorService lineWriter = Executors.newWorkStealingPool();

    /** Resets the {@code StreamedAudioPlayer}, removing all of its loaded audio files. */
    public static void reset() {
        lineWriter.shutdownNow();
        lineWriter = Executors.newWorkStealingPool();
    }

    /** Sets up the audio streaming process for the specified {@link StreamedAudio} object. */
    static void streamAudio(StreamedAudio audio) {
        lineWriter.submit(() -> {
            SourceDataLine sourceDataLine = audio.getAudioSource();
            AudioInputStream audioInputStream = audio.getAudioInputStream();

            int sourceLength;
            byte[] soundSamples = new byte[BufferSize];
            try {
                while ((sourceLength = audioInputStream.read(soundSamples, 0, BufferSize)) != -1) {
                    while (audio.currentPlaybackState != PlaybackState.Playing) {
                        TimeUnit.MILLISECONDS.sleep(100);
                    }
                    sourceDataLine.write(soundSamples, 0, sourceLength);
                }
            } catch (IOException exception) {
                FastJEngine.error(CrashMessages.theGameCrashed("an error while trying to play sound."), exception);
            } catch (InterruptedException exception) {
                FastJEngine.error(CrashMessages.theGameCrashed("an error while trying to play sound."), exception);
                Thread.currentThread().interrupt();
            } finally {
                sourceDataLine.drain();
            }
        });
    }

    /** See {@link StreamedAudio#play()}. */
    static void playAudio(StreamedAudio audio) {
        SourceDataLine sourceDataLine = audio.getAudioSource();
        AudioInputStream audioInputStream = audio.getAudioInputStream();

        if (sourceDataLine.isOpen()) {
            FastJEngine.warning("Tried to play audio file \"" + audio.getAudioPath().toString() + "\", but it was already open (and likely being used elsewhere.)");
            return;
        }

        try {
            sourceDataLine.open(audioInputStream.getFormat());
            sourceDataLine.start();

            audio.previousPlaybackState = audio.currentPlaybackState;
            audio.currentPlaybackState = PlaybackState.Playing;
        } catch (LineUnavailableException exception) {
            FastJEngine.error(CrashMessages.theGameCrashed("an error while trying to play sound."), exception);
        }
    }

    /** See {@link StreamedAudio#pause()}. */
    static void pauseAudio(StreamedAudio audio) {
        SourceDataLine sourceDataLine = audio.getAudioSource();

        if (!sourceDataLine.isOpen()) {
            FastJEngine.warning("Tried to pause audio file \"" + audio.getAudioPath().toString() + "\", but it wasn't being played.");
            return;
        }

        sourceDataLine.stop();

        audio.previousPlaybackState = audio.currentPlaybackState;
        audio.currentPlaybackState = PlaybackState.Paused;
    }

    /** See {@link StreamedAudio#resume()}. */
    static void resumeAudio(StreamedAudio audio) {
        SourceDataLine sourceDataLine = audio.getAudioSource();

        if (!sourceDataLine.isOpen()) {
            FastJEngine.warning("Tried to resume audio file \"" + audio.getAudioPath().toString() + "\", but it wasn't being played.");
            return;
        }

        sourceDataLine.start();

        audio.previousPlaybackState = audio.currentPlaybackState;
        audio.currentPlaybackState = PlaybackState.Playing;
    }

    /** See {@link StreamedAudio#stop()}. */
    static void stopAudio(StreamedAudio audio) {
        SourceDataLine sourceDataLine = audio.getAudioSource();

        if (!sourceDataLine.isOpen()) {
            FastJEngine.warning("Tried to stop audio file \"" + audio.getAudioPath().toString() + "\", but it wasn't being played.");
            return;
        }

        sourceDataLine.stop();
        sourceDataLine.flush();
        sourceDataLine.close();

        audio.previousPlaybackState = audio.currentPlaybackState;
        audio.currentPlaybackState = PlaybackState.Stopped;
    }
}
