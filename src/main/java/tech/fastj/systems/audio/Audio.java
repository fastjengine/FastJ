package tech.fastj.systems.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

public class Audio {

    /** Signifies that the audio should loop when it finishes playing. */
    public static final int LoopWhenFinishedPlaying = -1;

    private final Clip clip;
    private final AudioInputStream audioInputStream;
    private final SimpleLineListener simpleLineListener;

    Audio(Path audioPath) {
        clip = Objects.requireNonNull(AudioManager.newClip());
        audioInputStream = AudioManager.newAudioStream(audioPath);

        simpleLineListener = new SimpleLineListener(
                () -> {
                },
                () -> {
                },
                () -> {
                },
                () -> {
                    clip.stop();
                    clip.flush();
                    clip.drain();
                    clip.close();
                }
        );

        clip.addLineListener(simpleLineListener);
    }

    public Clip getClip() {
        return clip;
    }

    public AudioInputStream getAudioInputStream() {
        return audioInputStream;
    }

    public SimpleLineListener getSimpleLineListener() {
        return simpleLineListener;
    }

    public void setLoopPoints(int loopStart, int loopEnd) {
        clip.setLoopPoints(loopStart, loopEnd);
    }

    public void setLoopCount(int loopCount) {
        clip.loop(loopCount);
    }

    public void play() {
        AudioManager.playAudio(this);
    }

    @Override
    public String toString() {
        return "Audio{" +
                "clip=" + clip +
                ", audioInputStream=" + audioInputStream +
                '}';
    }
}
