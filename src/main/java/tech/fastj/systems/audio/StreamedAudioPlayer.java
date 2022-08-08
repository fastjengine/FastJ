package tech.fastj.systems.audio;

import tech.fastj.engine.FastJEngine;
import tech.fastj.logging.Log;
import tech.fastj.systems.audio.state.PlaybackState;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 * Class for playing {@link StreamedAudio streamed audio}.
 *
 * @author Andrew Dey
 * @since 1.5.0
 */
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
                throw new IllegalStateException("Input stream read error for audio file \"" + audio.getAudioPath().toAbsolutePath() + "\"", exception);
            } catch (InterruptedException exception) {
                Log.warn(StreamedAudioPlayer.class, "Audio file {} was interrupted: {}", audio.getAudioPath().toAbsolutePath(), exception.getMessage());
                Thread.currentThread().interrupt();
            } finally {
                sourceDataLine.drain();
                audio.stop();
            }
        });
    }

    /** See {@link StreamedAudio#play()}. */
    static void playAudio(StreamedAudio audio) {
        SourceDataLine sourceDataLine = audio.getAudioSource();
        AudioInputStream audioInputStream = audio.getAudioInputStream();

        if (sourceDataLine.isOpen()) {
            Log.warn(StreamedAudioPlayer.class, "Tried to play audio file \"{}\", but it was already open (and likely being used elsewhere.)", audio.getAudioPath().toString());
            return;
        }

        streamAudio(audio);

        try {
            sourceDataLine.open(audioInputStream.getFormat());
            LineEvent openLineEvent = new LineEvent(sourceDataLine, LineEvent.Type.OPEN, sourceDataLine.getLongFramePosition());
            AudioEvent openAudioEvent = new AudioEvent(openLineEvent, audio);
            FastJEngine.getGameLoop().fireEvent(openAudioEvent);

            sourceDataLine.start();

            audio.previousPlaybackState = audio.currentPlaybackState;
            audio.currentPlaybackState = PlaybackState.Playing;

            LineEvent startLineEvent = new LineEvent(sourceDataLine, LineEvent.Type.START, sourceDataLine.getLongFramePosition());
            AudioEvent startAudioEvent = new AudioEvent(startLineEvent, audio, PlaybackState.Playing);
            FastJEngine.getGameLoop().fireEvent(startAudioEvent);
        } catch (LineUnavailableException exception) {
            throw new IllegalStateException(
                    "No audio lines were available to load the file \"" + audio.getAudioPath().toAbsolutePath() + "\" as a StreamedAudio.",
                    exception
            );
        }
    }

    /** See {@link StreamedAudio#pause()}. */
    static void pauseAudio(StreamedAudio audio) {
        SourceDataLine sourceDataLine = audio.getAudioSource();

        if (!sourceDataLine.isOpen()) {
            Log.warn(StreamedAudioPlayer.class, "Tried to pause audio file \"{}\", but it wasn't being played.", audio.getAudioPath().toString());
            return;
        }

        sourceDataLine.stop();

        audio.previousPlaybackState = audio.currentPlaybackState;
        audio.currentPlaybackState = PlaybackState.Paused;

        LineEvent stopLineEvent = new LineEvent(sourceDataLine, LineEvent.Type.STOP, sourceDataLine.getLongFramePosition());
        AudioEvent stopAudioEvent = new AudioEvent(stopLineEvent, audio, PlaybackState.Paused);
        FastJEngine.getGameLoop().fireEvent(stopAudioEvent);
    }

    /** See {@link StreamedAudio#resume()}. */
    static void resumeAudio(StreamedAudio audio) {
        SourceDataLine sourceDataLine = audio.getAudioSource();

        if (!sourceDataLine.isOpen()) {
            Log.warn(StreamedAudioPlayer.class, "Tried to resume audio file \"{}\", but it wasn't being played.", audio.getAudioPath().toString());
            return;
        }

        sourceDataLine.start();

        audio.previousPlaybackState = audio.currentPlaybackState;
        audio.currentPlaybackState = PlaybackState.Playing;

        LineEvent startLineEvent = new LineEvent(sourceDataLine, LineEvent.Type.START, sourceDataLine.getLongFramePosition());
        AudioEvent startAudioEvent = new AudioEvent(startLineEvent, audio, PlaybackState.Playing);
        FastJEngine.getGameLoop().fireEvent(startAudioEvent);
    }

    /** See {@link StreamedAudio#stop()}. */
    static void stopAudio(StreamedAudio audio) {
        SourceDataLine sourceDataLine = audio.getAudioSource();

        if (!sourceDataLine.isOpen()) {
            Log.warn(StreamedAudioPlayer.class, "Tried to stop audio file \"{}\", but it wasn't being played.", audio.getAudioPath().toString());
            return;
        }

        sourceDataLine.stop();
        sourceDataLine.flush();
        sourceDataLine.close();

        audio.previousPlaybackState = audio.currentPlaybackState;
        audio.currentPlaybackState = PlaybackState.Stopped;

        LineEvent stopLineEvent = new LineEvent(sourceDataLine, LineEvent.Type.STOP, sourceDataLine.getLongFramePosition());
        AudioEvent stopAudioEvent = new AudioEvent(stopLineEvent, audio, PlaybackState.Stopped);
        FastJEngine.getGameLoop().fireEvent(stopAudioEvent);

        LineEvent closeLineEvent = new LineEvent(sourceDataLine, LineEvent.Type.CLOSE, sourceDataLine.getLongFramePosition());
        AudioEvent closeAudioEvent = new AudioEvent(closeLineEvent, audio);
        FastJEngine.getGameLoop().fireEvent(closeAudioEvent);
    }
}
