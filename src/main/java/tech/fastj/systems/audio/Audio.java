package tech.fastj.systems.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import java.net.URL;

public class Audio {

    /** Signifies that the audio should loop when it finishes playing. */
    public static final int LoopWhenFinishedPlaying = -1;

    private Clip clip;
    private AudioInputStream audioInputStream;

    private Runnable clipOpenAction;
    private Runnable clipCloseAction;
    private Runnable clipStartAction;
    private Runnable clipStopAction;

    Audio(URL audioPath) {
        clip = AudioManager.newClip();
        audioInputStream = AudioManager.newAudioStream(audioPath);

        clip.addLineListener(new SimpleAudioListener() {
            @Override
            public void update(LineEvent event) {
                LineEvent.Type lineEventType = event.getType();
                if (LineEvent.Type.OPEN.equals(lineEventType)) {
                    clipOpenAction.run();
                } else if (LineEvent.Type.CLOSE.equals(lineEventType)) {
                    clipCloseAction.run();
                } else if (LineEvent.Type.START.equals(lineEventType)) {
                    clipStartAction.run();
                } else if (LineEvent.Type.STOP.equals(lineEventType)) {
                    clipStopAction.run();
                    clip.stop();
                    clip.flush();
                    clip.drain();
                    clip.close();
                }
            }
        });
    }

    public Clip getClip() {
        return clip;
    }

    public AudioInputStream getAudioInputStream() {
        return audioInputStream;
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
