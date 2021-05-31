package tech.fastj.systems.audio;

import tech.fastj.engine.CrashMessages;
import tech.fastj.engine.FastJEngine;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/** The manager of all audio-based content. */
public class AudioManager {

    private static final Map<String, Audio> AudioFiles = new HashMap<>();

    /**
     * Loads all {@link Audio} objects at the specified paths into memory.
     *
     * @param audioPaths The paths of the {@code Audio} objects to load.
     */
    public static void loadAudio(Path... audioPaths) {
        for (Path audioPath : audioPaths) {
            AudioFiles.put(audioPath.toString(), new Audio(audioPath));
        }
    }

    /**
     * Unloads all {@link Audio} objects at the specified paths from memory.
     *
     * @param audioPaths The paths of the {@code Audio} objects to remove.
     */
    public static void unloadAudio(Path... audioPaths) {
        for (Path audioPath : audioPaths) {
            AudioFiles.remove(audioPath.toString());
        }
    }

    /**
     * Gets the {@link Audio} object from the loaded audio sets based on the provided {@code audioPath}.
     *
     * @param audioPath The path of the audio to get, as a {@code Path}.
     * @return The {@code Audio} object.
     */
    public static Audio getAudio(Path audioPath) {
        return getAudio(audioPath.toString());
    }

    /**
     * Gets the {@link Audio} object from the loaded audio sets based on the provided {@code audioPath}.
     *
     * @param audioPath The path of the audio to get, as a string.
     * @return The {@code Audio} object.
     */
    public static Audio getAudio(String audioPath) {
        return AudioFiles.get(audioPath);
    }

    public static void reset() {
        AudioFiles.clear();
    }

    /** Safely generates a {@link Clip} object, crashing the engine if something goes wrong. */
    static Clip newClip() {
        try {
            return AudioSystem.getClip();
        } catch (LineUnavailableException exception) {
            FastJEngine.error(
                    CrashMessages.theGameCrashed("an error while loading sound."),
                    exception
            );
            return null;
        }
    }

    /** Safely generates an {@link AudioInputStream} object, crashing the engine if something goes wrong. */
    static AudioInputStream newAudioStream(Path audioPath) {
        try {
            return AudioSystem.getAudioInputStream(audioPath.toFile());
        } catch (UnsupportedAudioFileException | IOException exception) {
            FastJEngine.error(
                    CrashMessages.theGameCrashed("an error while loading sound."),
                    exception
            );

            return null;
        }
    }

    /** See {@link Audio#play()}. */
    static void playAudio(Audio audio) {
        Clip clip = audio.getClip();

        if (clip.isOpen()) {
            FastJEngine.warning("Tried to play audio file \"" + audio.getAudioPath().toString() + "\", but it was already open (and likely being used elsewhere.)");
            return;
        }

        try {
            AudioInputStream audioInputStream = audio.getAudioInputStream();
            clip.open(audioInputStream);
            clip.start();
        } catch (LineUnavailableException | IOException exception) {
            FastJEngine.error(CrashMessages.theGameCrashed("an error while trying to play sound."), exception);
        }
    }

    /** See {@link Audio#pause()}. */
    static void pauseAudio(Audio audio) {
        Clip clip = audio.getClip();

        if (!clip.isOpen()) {
            FastJEngine.warning("Tried to pause audio file \"" + audio.getAudioPath().toString() + "\", but it wasn't being played.");
        } else {
            clip.stop();
        }
    }

    /** See {@link Audio#resume()}. */
    static void resumeAudio(Audio audio) {
        Clip clip = audio.getClip();

        if (!clip.isOpen()) {
            FastJEngine.warning("Tried to resume audio file \"" + audio.getAudioPath().toString() + "\", but it wasn't being played.");
        } else {
            clip.start();
        }
    }

    /** See {@link Audio#stop()}. */
    static void stopAudio(Audio audio) {
        Clip clip = audio.getClip();

        if (!clip.isOpen()) {
            FastJEngine.warning("Tried to stop audio file \"" + audio.getAudioPath().toString() + "\", but it wasn't being played.");
        } else {
            clip.stop();
            clip.flush();
            clip.drain();
            clip.close();
        }
    }

    /** See {@link Audio#seek(long)}. */
    static void seekInAudio(Audio audio, long timeChange) {
        Clip clip = audio.getClip();

        if (clip.isActive()) {
            FastJEngine.warning("Tried to change the playback position of audio file \"" + audio.getAudioPath().toString() + "\", but it was still running.");
            return;
        }

        long timeChangeInMilliseconds = TimeUnit.MICROSECONDS.convert(timeChange, TimeUnit.MILLISECONDS);
        clip.setMicrosecondPosition(clip.getMicrosecondPosition() + timeChangeInMilliseconds);
    }

    /** See {@link Audio#setPlaybackPosition(long)}. */
    static void setAudioPlaybackPosition(Audio audio, long playbackPosition) {
        Clip clip = audio.getClip();

        if (clip.isActive()) {
            FastJEngine.warning("Tried to set the playback position of audio file \"" + audio.getAudioPath().toString() + "\", but it was still running.");
            return;
        }

        long playbackPositionInMilliseconds = TimeUnit.MICROSECONDS.convert(playbackPosition, TimeUnit.MILLISECONDS);
        clip.setMicrosecondPosition(playbackPositionInMilliseconds);
    }

    /** See {@link Audio#rewindToBeginning()}. */
    static void rewindAudioToBeginning(Audio audio) {
        Clip clip = audio.getClip();

        if (clip.isActive()) {
            FastJEngine.warning("Tried to rewind audio file \"" + audio.getAudioPath().toString() + "\", but it was still running.");
            return;
        }

        clip.setMicrosecondPosition(0L);
    }
}
