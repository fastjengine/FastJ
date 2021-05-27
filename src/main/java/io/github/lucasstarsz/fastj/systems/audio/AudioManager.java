package io.github.lucasstarsz.fastj.systems.audio;

import io.github.lucasstarsz.fastj.engine.CrashMessages;
import io.github.lucasstarsz.fastj.engine.FastJEngine;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AudioManager {

    private static final ExecutorService AudioPlayer = Executors.newFixedThreadPool(Math.max(Runtime.getRuntime().availableProcessors() / 4, 1));
    private static final Map<String, Audio> AudioFiles = new HashMap<>();

    public static void loadAudio(URL... audioPaths) {
        for (URL audioPath : audioPaths) {
            AudioFiles.put(audioPath.getPath(), new Audio(audioPath));
        }
    }

    public static Audio getAudio(URL audioPath) {
        return getAudio(audioPath.getPath());
    }

    public static Audio getAudio(String audioPath) {
        return AudioFiles.get(audioPath);
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

    static AudioInputStream newAudioStream(URL audioPath) {
        try {
            return AudioSystem.getAudioInputStream(audioPath);
        } catch (UnsupportedAudioFileException | IOException exception) {
            FastJEngine.error(
                    CrashMessages.theGameCrashed("an error while loading sound."),
                    exception
            );

            return null;
        }


    }

    public static void stop() {
        AudioPlayer.shutdownNow();
    }

    static void playAudio(Audio audio) {
        AudioPlayer.execute(() -> {
            Clip clip = audio.getClip();
            AudioInputStream audioInputStream = audio.getAudioInputStream();
            try {
                System.out.println("?");
                clip.open(audioInputStream);
                clip.start();
            } catch (LineUnavailableException | IOException exception) {
                FastJEngine.error(CrashMessages.theGameCrashed("an error while trying to play sound."), exception);
            }
        });
    }
}
