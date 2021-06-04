package tech.fastj.systems.audio;

import tech.fastj.engine.CrashMessages;
import tech.fastj.engine.FastJEngine;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AudioPlayer {

    public static final int BufferSize = 4096;

    private static final ExecutorService LineWriter = Executors.newWorkStealingPool();

    public static void playSoundEffect(Path soundPath) {
        AudioInputStream audioInputStream = AudioManager.newAudioStream(soundPath);
        if (audioInputStream == null) {
            return;
        }

        SourceDataLine sourceDataLine = getAudioOutputLine(audioInputStream.getFormat());
        if (sourceDataLine == null) {
            return;
        }

        LineWriter.submit(() -> {
            try {
                sourceDataLine.open(audioInputStream.getFormat());
                sourceDataLine.start();
            } catch (LineUnavailableException exception) {
                FastJEngine.error(CrashMessages.theGameCrashed("an error while trying to play sound."), exception);
                return;
            }
//            System.out.println("float control time yay");
//            System.out.println(Arrays.toString(sourceDataLine.getControls()));
//            FloatControl gainControl = (FloatControl) sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN);
//            gainControl.setValue(gainControl.getMaximum());
//            FloatControl panControl = (FloatControl) sourceDataLine.getControl(FloatControl.Type.PAN);
//            panControl.shift(-1.0f, 1.0f, 100000000);
//            System.out.println(floatControl);
//            System.out.println("float control time over?");
//            System.out.println(sourceDataLine.getClass());

            int sourceLength;
            byte[] soundSamples = new byte[BufferSize];
            try {
                while ((sourceLength = audioInputStream.read(soundSamples, 0, BufferSize)) != -1) {
                    sourceDataLine.write(soundSamples, 0, sourceLength);
                }
            } catch (IOException exception) {
                FastJEngine.error(CrashMessages.theGameCrashed("an error while trying to play sound."), exception);
            } finally {
                sourceDataLine.drain();
                sourceDataLine.close();
            }
        });
    }


    private static SourceDataLine getAudioOutputLine(AudioFormat audioFormat) {
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

    public static void reset() {
        LineWriter.shutdownNow();
    }
}
