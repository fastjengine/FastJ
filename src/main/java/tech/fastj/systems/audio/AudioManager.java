package tech.fastj.systems.audio;

import tech.fastj.engine.FastJEngine;
import tech.fastj.gameloop.event.EventHandler;
import tech.fastj.gameloop.event.EventObserver;
import tech.fastj.resources.files.FileUtil;
import tech.fastj.systems.audio.state.PlaybackState;
import tech.fastj.systems.tags.TagHandler;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sound.sampled.*;

/**
 * The manager of all audio-based content.
 *
 * @author Andrew Dey
 * @since 1.5.0
 */
public class AudioManager implements TagHandler<Audio>, EventHandler<AudioEvent, EventObserver<AudioEvent>> {

    private final Map<String, MemoryAudio> memoryAudioFiles = new ConcurrentHashMap<>();
    private final Map<String, StreamedAudio> streamedAudioFiles = new ConcurrentHashMap<>();

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
        audio.play();
    }

    /**
     * Plays the sound file at the specified {@link URL}.
     *
     * @param audioPath The {@code URL} path of the sound file to be played.
     */
    public static void playSound(URL audioPath) {
        StreamedAudio audio = new StreamedAudio(audioPath);
        audio.play();
    }


    public void init() {
        FastJEngine.getGameLoop().addEventHandler(this, AudioEvent.class);
    }

    /**
     * Loads a {@link MemoryAudio} object at the specified path into memory.
     *
     * @param audioPath The path of the {@code MemoryAudio} object to load.
     * @return The created {@link MemoryAudio} instance.
     */
    public MemoryAudio loadMemoryAudio(Path audioPath) {
        MemoryAudio audio = new MemoryAudio(audioPath);
        memoryAudioFiles.put(audio.getID(), audio);
        return audio;
    }

    /**
     * Loads a {@link MemoryAudio} object at the specified path into memory.
     *
     * @param audioPath The path of the {@code MemoryAudio} object to load.
     * @return The created {@link MemoryAudio} instance.
     */
    public MemoryAudio loadMemoryAudio(URL audioPath) {
        MemoryAudio audio = new MemoryAudio(audioPath);
        memoryAudioFiles.put(audio.getID(), audio);
        return audio;
    }

    /**
     * Loads all {@link MemoryAudio} objects at the specified paths into memory.
     *
     * @param audioPaths The paths of the {@code MemoryAudio} objects to load.
     * @return The created {@link MemoryAudio} instances.
     */
    public MemoryAudio[] loadMemoryAudio(Path... audioPaths) {
        MemoryAudio[] audioInstances = new MemoryAudio[audioPaths.length];

        for (int i = 0; i < audioPaths.length; i++) {
            MemoryAudio audio = new MemoryAudio(audioPaths[i]);
            memoryAudioFiles.put(audio.getID(), audio);
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
    public MemoryAudio[] loadMemoryAudio(URL... audioPaths) {
        MemoryAudio[] audioInstances = new MemoryAudio[audioPaths.length];

        for (int i = 0; i < audioPaths.length; i++) {
            MemoryAudio audio = new MemoryAudio(audioPaths[i]);
            memoryAudioFiles.put(audio.getID(), audio);
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
    public StreamedAudio loadStreamedAudio(Path audioPath) {
        StreamedAudio audio = new StreamedAudio(audioPath);
        streamedAudioFiles.put(audio.getID(), audio);
        return audio;
    }

    /**
     * Loads a {@link StreamedAudio} object at the specified path into memory.
     *
     * @param audioPath The path of the {@code StreamedAudio} object to load.
     * @return The created {@link StreamedAudio} instance.
     */
    public StreamedAudio loadStreamedAudio(URL audioPath) {
        StreamedAudio audio = new StreamedAudio(audioPath);
        streamedAudioFiles.put(audio.getID(), audio);
        return audio;
    }

    /**
     * Loads all {@link StreamedAudio} objects at the specified paths into memory.
     *
     * @param audioPaths The paths of the {@code StreamedAudio} objects to load.
     * @return The created {@link StreamedAudio} instances.
     */
    public StreamedAudio[] loadStreamedAudio(Path... audioPaths) {
        StreamedAudio[] audioInstances = new StreamedAudio[audioPaths.length];

        for (int i = 0; i < audioPaths.length; i++) {
            StreamedAudio audio = new StreamedAudio(audioPaths[i]);
            streamedAudioFiles.put(audio.getID(), audio);
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
    public StreamedAudio[] loadStreamedAudio(URL... audioPaths) {
        StreamedAudio[] audioInstances = new StreamedAudio[audioPaths.length];

        for (int i = 0; i < audioPaths.length; i++) {
            StreamedAudio audio = new StreamedAudio(audioPaths[i]);
            streamedAudioFiles.put(audio.getID(), audio);
            audioInstances[i] = audio;
        }

        return audioInstances;
    }

    /**
     * Unloads the {@link MemoryAudio} object with the specified id from memory.
     *
     * @param id The id of the {@code MemoryAudio} object to remove.
     */
    public void unloadMemoryAudio(String id) {
        memoryAudioFiles.remove(id);
    }

    /**
     * Unloads all {@link MemoryAudio} objects with the specified ids from memory.
     *
     * @param ids The ids of the {@code MemoryAudio} objects to remove.
     */
    public void unloadMemoryAudio(String... ids) {
        for (String id : ids) {
            memoryAudioFiles.remove(id);
        }
    }

    /**
     * Unloads the {@link StreamedAudio} object with the specified id from memory.
     *
     * @param id The id of the {@code StreamedAudio} object to remove.
     */
    public void unloadStreamedAudio(String id) {
        streamedAudioFiles.remove(id);
    }

    /**
     * Unloads all {@link StreamedAudio} objects with the specified ids from memory.
     *
     * @param ids The ids of the {@code StreamedAudio} objects to remove.
     */
    public void unloadStreamedAudio(String... ids) {
        for (String id : ids) {
            streamedAudioFiles.remove(id);
        }
    }

    /**
     * Gets the {@link MemoryAudio} object from the loaded audio sets based on the provided {@code audioPath}.
     *
     * @param id The id of the audio to get.
     * @return The {@code Audio} object.
     */
    public MemoryAudio getMemoryAudio(String id) {
        return memoryAudioFiles.get(id);
    }

    /**
     * Gets the {@link StreamedAudio} object from the loaded audio sets based on the provided {@code audioPath}.
     *
     * @param id The id of the audio to get.
     * @return The {@code Audio} object.
     */
    public StreamedAudio getStreamedAudio(String id) {
        return streamedAudioFiles.get(id);
    }


    /** Resets the {@code AudioManager}, removing all of its loaded audio files. */
    public void reset() {
        memoryAudioFiles.forEach((s, audio) -> {
            if (audio.currentPlaybackState != PlaybackState.Stopped) {
                audio.stop();
            }
        });
        memoryAudioFiles.clear();

        streamedAudioFiles.forEach((s, audio) -> {
            if (audio.currentPlaybackState != PlaybackState.Stopped) {
                audio.stop();
            }
        });
        streamedAudioFiles.clear();

        FastJEngine.getGameLoop().removeEventHandler(AudioEvent.class);
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
            throw new IllegalStateException("No audio lines were available to load the clip.", exception);
        }
    }

    /** Safely generates an {@link AudioInputStream} object, crashing the engine if something goes wrong. */
    static AudioInputStream newAudioStream(Path audioPath) {
        try {
            return AudioSystem.getAudioInputStream(audioPath.toFile());
        } catch (IOException exception) {
            throw new IllegalStateException(exception.getMessage(), exception);
        } catch (UnsupportedAudioFileException exception) {
            throw new IllegalArgumentException(
                    audioPath.toAbsolutePath() + " is of an unsupported file format \"" + FileUtil.getFileExtension(audioPath) + "\".",
                    exception
            );
        }
    }

    /** Safely generates an {@link AudioInputStream} object, crashing the engine if something goes wrong. */
    static AudioInputStream newAudioStream(URL audioPath) {
        try {
            return AudioSystem.getAudioInputStream(audioPath);
        } catch (IOException exception) {
            throw new IllegalStateException(exception.getMessage(), exception);
        } catch (UnsupportedAudioFileException exception) {
            throw new IllegalArgumentException(
                    audioPath.getPath() + " is of an unsupported file format \"" + FileUtil.getFileExtension(Path.of(audioPath.getPath())) + "\".",
                    exception
            );
        }
    }

    /** Safely generates a {@link SourceDataLine} object, crashing the engine if something goes wrong. */
    static SourceDataLine newSourceDataLine(AudioFormat audioFormat) {
        DataLine.Info lineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);

        if (!AudioSystem.isLineSupported(lineInfo)) {
            throw new IllegalArgumentException("No audio output lines supported for " + audioFormat);
        }

        try {
            return (SourceDataLine) AudioSystem.getLine(lineInfo);
        } catch (LineUnavailableException exception) {
            throw new IllegalStateException("No audio lines were available to load the data line with format " + audioFormat + ".", exception);
        }
    }

    @Override
    public List<Audio> getTaggableEntities() {
        List<Audio> result = new ArrayList<>();
        result.addAll(memoryAudioFiles.values());
        result.addAll(streamedAudioFiles.values());

        return result;
    }

    @Override
    public void handleEvent(List<EventObserver<AudioEvent>> eventObservers, AudioEvent audioEvent) {
        for (EventObserver<AudioEvent> eventObserver : eventObservers) {
            if (audioEvent.getEventSource().getAudioEventListener().equals(eventObserver)) {
                eventObserver.eventReceived(audioEvent);
                return;
            }
        }
    }
}
