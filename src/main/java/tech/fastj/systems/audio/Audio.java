package tech.fastj.systems.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Audio {

    /** Signifies that the audio should loop when it finishes playing. */
    public static final int ContinuousLoop = Clip.LOOP_CONTINUOUSLY;

    private final Clip clip;
    private final AudioInputStream audioInputStream;
    private final SimpleLineListener simpleLineListener;
    private final Path audioPath;

    Audio(Path audioPath) {
        this.audioPath = audioPath;

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

    public Path getAudioPath() {
        return audioPath;
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

    public void pause() {
        AudioManager.pauseAudio(this);
    }

    public void resume() {
        AudioManager.resumeAudio(this);
    }

    public void stop() {
        AudioManager.stopAudio(this);
    }

    @Override
    public String toString() {
        return "Audio{" +
                "clip=" + clip +
                ", audioPath=\"" + audioPath.toString() +
                "\", audioInputStream=" + audioInputStream +
                ", simpleLineListener=" + simpleLineListener +
                '}';
    }
}
