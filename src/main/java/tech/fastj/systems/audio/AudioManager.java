package tech.fastj.systems.audio;

import tech.fastj.engine.CrashMessages;
import tech.fastj.engine.FastJEngine;
import tech.fastj.math.Maths;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AudioManager {

    private static final int MinimumAudioThreads = 1;
    private static final int MaximumAudioThreads = 4;
    private static final ExecutorService AudioPlayer = Executors.newFixedThreadPool(
            Maths.withinIntegerRange(
                    Runtime.getRuntime().availableProcessors(),
                    MinimumAudioThreads,
                    MaximumAudioThreads
            )
    );

    private static final Map<String, Audio> AudioFiles = new HashMap<>();

    public static void loadAudio(Path... audioPaths) {
        for (Path audioPath : audioPaths) {
            AudioFiles.put(audioPath.toString(), new Audio(audioPath));
        }
    }

    public static void unloadAudio(Path... audioPaths) {
        for (Path audioPath : audioPaths) {
            AudioFiles.remove(audioPath.toString());
        }
    }

    public static Audio getAudio(Path audioPath) {
        return getAudio(audioPath.toString());
    }

    public static Audio getAudio(String audioPath) {
        return AudioFiles.get(audioPath);
    }

    public static void unloadAllAudio() {
        AudioFiles.clear();
    }

    public static void stop() {
        AudioPlayer.shutdownNow();
        unloadAllAudio();
    }

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

    static void playAudio(Audio audio) {
        AudioPlayer.execute(() -> {
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
        });
    }

    static void pauseAudio(Audio audio) {
        AudioPlayer.execute(() -> {
            Clip clip = audio.getClip();

            if (!clip.isOpen()) {
                FastJEngine.warning("Tried to pause audio file \"" + audio.getAudioPath().toString() + "\", but it wasn't being played.");
            } else {
                clip.stop();
            }
        });
    }

    static void resumeAudio(Audio audio) {
        AudioPlayer.execute(() -> {
            Clip clip = audio.getClip();

            if (!clip.isOpen()) {
                FastJEngine.warning("Tried to resume audio file \"" + audio.getAudioPath().toString() + "\", but it wasn't being played.");
            } else {
                clip.start();
            }
        });
    }

    static void stopAudio(Audio audio) {
        AudioPlayer.execute(() -> {
            Clip clip = audio.getClip();

            if (!clip.isOpen()) {
                FastJEngine.warning("Tried to stop audio file \"" + audio.getAudioPath().toString() + "\", but it wasn't being played.");
            } else {
                clip.stop();
                clip.flush();
                clip.drain();
                clip.close();
            }
        });
    }
}
