package tech.fastj.systems.audio;

import tech.fastj.engine.CrashMessages;
import tech.fastj.engine.FastJEngine;

import tech.fastj.resources.files.FileUtil;
import tech.fastj.systems.audio.state.PlaybackState;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The manager of all audio-based content.
 *
 * @author Andrew Dey
 * @since 1.5.0
 */
public class AudioManager {

    private static final Map<String, MemoryAudio> MemoryAudioFiles = new ConcurrentHashMap<>();
    private static final Map<String, StreamedAudio> StreamedAudioFiles = new ConcurrentHashMap<>();
    private static ExecutorService audioEventExecutor = Executors.newWorkStealingPool();

    /**
     * Checks whether the computer supports audio output.
     *
     * @return Whether the computer supports audio output.
     */
    public static boolean isOutputSupported() {
        Line.Info outputLineInfo = new Line.Info(SourceDataLine.class);
        for (Mixer.Info mixerInfo : AudioSystem.getMixerInfo()) {
            Mixer mixer = AudioSystem.getMixer(mixerInfo);
            if (mixer.isLineSupported(outputLineInfo)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Plays the sound file at the specified {@link Path}.
     *
     * @param audioPath The file {@code Path} of the sound file to be played.
     */
    public static void playSound(Path audioPath) {
        StreamedAudio audio = new StreamedAudio(audioPath);
        audio.getAudioEventListener().setAudioStopAction(audioEvent -> audio.stop());
        audio.play();
    }

    /**
     * Plays the sound file at the specified {@link URL}.
     *
     * @param audioPath The {@code URL} path of the sound file to be played.
     */
    public static void playSound(URL audioPath) {
        StreamedAudio audio = new StreamedAudio(audioPath);
        audio.getAudioEventListener().setAudioStopAction(audioEvent -> audio.stop());
        audio.play();
    }

    /**
     * Loads a {@link MemoryAudio} object at the specified path into memory.
     *
     * @param audioPath The path of the {@code MemoryAudio} object to load.
     * @return The created {@link MemoryAudio} instance.
     */
    public static MemoryAudio loadMemoryAudio(Path audioPath) {
        MemoryAudio audio = new MemoryAudio(audioPath);
        MemoryAudioFiles.put(audio.getID(), audio);
        return audio;
    }

    /**
     * Loads a {@link MemoryAudio} object at the specified path into memory.
     *
     * @param audioPath The path of the {@code MemoryAudio} object to load.
     * @return The created {@link MemoryAudio} instance.
     */
    public static MemoryAudio loadMemoryAudio(URL audioPath) {
        MemoryAudio audio = new MemoryAudio(audioPath);
        MemoryAudioFiles.put(audio.getID(), audio);
        return audio;
    }

    /**
     * Loads all {@link MemoryAudio} objects at the specified paths into memory.
     *
     * @param audioPaths The paths of the {@code MemoryAudio} objects to load.
     * @return The created {@link MemoryAudio} instances.
     */
    public static MemoryAudio[] loadMemoryAudio(Path... audioPaths) {
        MemoryAudio[] audioInstances = new MemoryAudio[audioPaths.length];

        for (int i = 0; i < audioPaths.length; i++) {
            MemoryAudio audio = new MemoryAudio(audioPaths[i]);
            MemoryAudioFiles.put(audio.getID(), audio);
            audioInstances[i] = audio;
        }

        return audioInstances;
    }

    /**
     * Loads all {@link MemoryAudio} objects at the specified URLs into memory.
     *
     * @param audioPaths The URLs of the {@code MemoryAudio} objects to load.
     * @return The created {@link MemoryAudio} instances.
     */
    public static MemoryAudio[] loadMemoryAudio(URL... audioPaths) {
        MemoryAudio[] audioInstances = new MemoryAudio[audioPaths.length];

        for (int i = 0; i < audioPaths.length; i++) {
            MemoryAudio audio = new MemoryAudio(audioPaths[i]);
            MemoryAudioFiles.put(audio.getID(), audio);
            audioInstances[i] = audio;
        }

        return audioInstances;
    }

    /**
     * Loads a {@link StreamedAudio} object at the specified path into memory.
     *
     * @param audioPath The path of the {@code StreamedAudio} object to load.
     * @return The created {@link StreamedAudio} instance.
     */
    public static StreamedAudio loadStreamedAudio(Path audioPath) {
        StreamedAudio audio = new StreamedAudio(audioPath);
        StreamedAudioFiles.put(audio.getID(), audio);
        return audio;
    }

    /**
     * Loads a {@link StreamedAudio} object at the specified path into memory.
     *
     * @param audioPath The path of the {@code StreamedAudio} object to load.
     * @return The created {@link StreamedAudio} instance.
     */
    public static StreamedAudio loadStreamedAudio(URL audioPath) {
        StreamedAudio audio = new StreamedAudio(audioPath);
        StreamedAudioFiles.put(audio.getID(), audio);
        return audio;
    }

    /**
     * Loads all {@link StreamedAudio} objects at the specified paths into memory.
     *
     * @param audioPaths The paths of the {@code StreamedAudio} objects to load.
     * @return The created {@link StreamedAudio} instances.
     */
    public static StreamedAudio[] loadStreamedAudio(Path... audioPaths) {
        StreamedAudio[] audioInstances = new StreamedAudio[audioPaths.length];

        for (int i = 0; i < audioPaths.length; i++) {
            StreamedAudio audio = new StreamedAudio(audioPaths[i]);
            StreamedAudioFiles.put(audio.getID(), audio);
            audioInstances[i] = audio;
        }

        return audioInstances;
    }

    /**
     * Loads all {@link StreamedAudio} objects at the specified URLs into memory.
     *
     * @param audioPaths The URLs of the {@code StreamedAudio} objects to load.
     * @return The created {@link StreamedAudio} instances.
     */
    public static StreamedAudio[] loadStreamedAudio(URL... audioPaths) {
        StreamedAudio[] audioInstances = new StreamedAudio[audioPaths.length];

        for (int i = 0; i < audioPaths.length; i++) {
            StreamedAudio audio = new StreamedAudio(audioPaths[i]);
            StreamedAudioFiles.put(audio.getID(), audio);
            audioInstances[i] = audio;
        }

        return audioInstances;
    }

    /**
     * Unloads the {@link MemoryAudio} object with the specified id from memory.
     *
     * @param id The id of the {@code MemoryAudio} object to remove.
     */
    public static void unloadMemoryAudio(String id) {
        MemoryAudioFiles.remove(id);
    }

    /**
     * Unloads all {@link MemoryAudio} objects with the specified ids from memory.
     *
     * @param ids The ids of the {@code MemoryAudio} objects to remove.
     */
    public static void unloadMemoryAudio(String... ids) {
        for (String id : ids) {
            MemoryAudioFiles.remove(id);
        }
    }

    /**
     * Unloads the {@link StreamedAudio} object with the specified id from memory.
     *
     * @param id The id of the {@code StreamedAudio} object to remove.
     */
    public static void unloadStreamedAudio(String id) {
        StreamedAudioFiles.remove(id);
    }

    /**
     * Unloads all {@link StreamedAudio} objects with the specified ids from memory.
     *
     * @param ids The ids of the {@code StreamedAudio} objects to remove.
     */
    public static void unloadStreamedAudio(String... ids) {
        for (String id : ids) {
            StreamedAudioFiles.remove(id);
        }
    }

    /**
     * Gets the {@link MemoryAudio} object from the loaded audio sets based on the provided {@code audioPath}.
     *
     * @param id The id of the audio to get.
     * @return The {@code Audio} object.
     */
    public static MemoryAudio getMemoryAudio(String id) {
        return MemoryAudioFiles.get(id);
    }

    /**
     * Gets the {@link StreamedAudio} object from the loaded audio sets based on the provided {@code audioPath}.
     *
     * @param id The id of the audio to get.
     * @return The {@code Audio} object.
     */
    public static StreamedAudio getStreamedAudio(String id) {
        return StreamedAudioFiles.get(id);
    }


    /** Resets the {@code AudioManager}, removing all of its loaded audio files. */
    public static void reset() {
        MemoryAudioFiles.forEach((s, audio) -> {
            if (audio.currentPlaybackState != PlaybackState.Stopped) {
                audio.stop();
            }
        });
        MemoryAudioFiles.clear();

        StreamedAudioFiles.forEach((s, audio) -> {
            if (audio.currentPlaybackState != PlaybackState.Stopped) {
                audio.stop();
            }
        });
        StreamedAudioFiles.clear();

        audioEventExecutor.shutdownNow();
        audioEventExecutor = Executors.newWorkStealingPool();
    }

    static Path pathFromURL(URL audioPath) {
        String urlPath = audioPath.getPath();
        String urlProtocol = audioPath.getProtocol();

        if (urlPath.startsWith(urlProtocol)) {
            return Path.of(urlPath.substring(urlProtocol.length()));
        } else if (urlPath.startsWith("file:///")) {
            return Path.of(urlPath.substring(8));
        } else {
            // In this case, the file path starts with "/" which may need to be removed depending
            // on the operating system.
            return Path.of(
                    urlPath.startsWith("/") && !System.getProperty("os.name").startsWith("Mac")
                    ? urlPath.replaceFirst("/*+", "")
                    : urlPath
            );
        }
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
        } catch (IOException exception) {
            FastJEngine.error(
                    CrashMessages.theGameCrashed("an I/O error while loading sound."),
                    exception
            );
        } catch (UnsupportedAudioFileException exception) {
            FastJEngine.error(
                    CrashMessages.theGameCrashed("an audio file reading error."),
                    new UnsupportedAudioFileException(audioPath.toAbsolutePath() + " is of an unsupported file format \"" + FileUtil.getFileExtension(audioPath) + "\".")
            );
        }

        return null;
    }

    /** Safely generates an {@link AudioInputStream} object, crashing the engine if something goes wrong. */
    static AudioInputStream newAudioStream(URL audioPath) {
        try {
            return AudioSystem.getAudioInputStream(audioPath);
        } catch (IOException exception) {
            FastJEngine.error(
                    CrashMessages.theGameCrashed("an I/O error while loading sound."),
                    exception
            );
        } catch (UnsupportedAudioFileException exception) {
            FastJEngine.error(
                    CrashMessages.theGameCrashed("an audio file reading error."),
                    new UnsupportedAudioFileException(audioPath.getPath() + " is of an unsupported file format \"" + FileUtil.getFileExtension(Path.of(audioPath.getPath())) + "\".")
            );
        }

        return null;
    }

    /** Safely generates a {@link SourceDataLine} object, crashing the engine if something goes wrong. */
    static SourceDataLine newSourceDataLine(AudioFormat audioFormat) {
        DataLine.Info lineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);

        if (!AudioSystem.isLineSupported(lineInfo)) {
            FastJEngine.warning("No audio output lines supported.");
            return null;
        }

        try {
            return (SourceDataLine) AudioSystem.getLine(lineInfo);
        } catch (LineUnavailableException exception) {
            FastJEngine.error(
                    CrashMessages.theGameCrashed("an audio error while trying to open an audio line."),
                    exception
            );
            return null;
        }
    }

    static void fireAudioEvent(Audio audio, LineEvent audioEventType) {
        audioEventExecutor.submit(() -> audio.getAudioEventListener().fireEvent(audioEventType));
    }
}
