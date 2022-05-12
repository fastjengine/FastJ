package tech.fastj.systems.audio;

import tech.fastj.engine.FastJEngine;

import tech.fastj.systems.audio.state.PlaybackState;

import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 * An audio object used for sound playback.
 * <p>
 * This type of {@link Audio} is streamed from the file, reducing the amount of memory taken when loading it.
 * <p>
 * In addition to {@link Audio the controls all Audio types in FastJ support}, {@code StreamedAudio} supports the
 * following extra controls:
 * <ul>
 *     <li>Gain Controls</li>
 *     <li>Pan Controls</li>
 *     <li>Balance Controls</li>
 *     <li>Mute Controls</li>
 * </ul>
 *
 * @author Andrew Dey
 * @since 1.5.0
 */
public class StreamedAudio extends Audio {

    private final AudioInputStream audioInputStream;

    private SourceDataLine sourceDataLine;

    private FloatControl gainControl;
    private FloatControl panControl;
    private FloatControl balanceControl;
    private BooleanControl muteControl;

    private AudioEventListener audioEventListener;

    private boolean forcedStop;

    private final LineListener eventHelper = event -> {
        if ("STOP".equals(event.getType().toString())) {
            if (forcedStop) {
                System.out.println("decline on forced stop");
                return;
            }

            System.out.println("natural stop");

            LineEvent stopLineEvent = new LineEvent(sourceDataLine, LineEvent.Type.STOP, sourceDataLine.getLongFramePosition());
            AudioEvent stopAudioEvent = new AudioEvent(stopLineEvent, this);
            FastJEngine.getGameLoop().fireEvent(stopAudioEvent);
        }
    };

    private static ScheduledExecutorService forcedTimeout = Executors.newScheduledThreadPool(2);

    public static void reset() {
        forcedTimeout.shutdownNow();
        forcedTimeout = Executors.newScheduledThreadPool(2);
    }

    /**
     * Constructs the {@code StreamedAudio} object with the given path.
     *
     * @param audioPath The path of the audio to use.
     */
    StreamedAudio(Path audioPath) {
        super(audioPath, UUID.randomUUID().toString());

        audioInputStream = Objects.requireNonNull(AudioManager.newAudioStream(audioPath));

        initializeStreamData();
    }

    /**
     * Constructs the {@code StreamedAudio} object with the given URL.
     *
     * @param audioPath The path of the audio to use.
     */
    StreamedAudio(URL audioPath) {
        super(AudioManager.pathFromURL(audioPath), UUID.randomUUID().toString());

        String urlPath = audioPath.getPath();
        String urlProtocol = audioPath.getProtocol();

        if (urlPath.startsWith(urlProtocol) || urlPath.startsWith("file:///")) {
            audioInputStream = Objects.requireNonNull(AudioManager.newAudioStream(audioPath));
        } else {
            audioInputStream = Objects.requireNonNull(AudioManager.newAudioStream(this.audioPath));
        }

        initializeStreamData();
    }

    /** Initializes a {@code StreamedAudio}'s data line, controls, and event listeners. */
    private void initializeStreamData() {
        sourceDataLine = Objects.requireNonNull(AudioManager.newSourceDataLine(audioInputStream.getFormat()));
        sourceDataLine.addLineListener(eventHelper);

        try {
            sourceDataLine.open(audioInputStream.getFormat());
        } catch (LineUnavailableException exception) {
            throw new IllegalStateException(
                    "No audio lines were available to load the file \"" + audioPath.toAbsolutePath() + "\" as a StreamedAudio.",
                    exception
            );
        }

        gainControl = (FloatControl) sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN);
        panControl = (FloatControl) sourceDataLine.getControl(FloatControl.Type.PAN);
        balanceControl = (FloatControl) sourceDataLine.getControl(FloatControl.Type.BALANCE);
        muteControl = (BooleanControl) sourceDataLine.getControl(BooleanControl.Type.MUTE);

        sourceDataLine.close();

        audioEventListener = new AudioEventListener(this);
        currentPlaybackState = PlaybackState.Stopped;
        previousPlaybackState = PlaybackState.Stopped;
    }

    /**
     * Gets the audio's gain control.
     *
     * @return The audio's gain control.
     */
    public FloatControl gainControl() {
        return gainControl;
    }

    /**
     * Gets the audio's pan control.
     *
     * @return The audio's pan control.
     */
    public FloatControl panControl() {
        return panControl;
    }

    /**
     * Gets the audio's balance control.
     *
     * @return The audio's balance control.
     */
    public FloatControl balanceControl() {
        return balanceControl;
    }

    /**
     * Gets the audio's mute control.
     *
     * @return The audio's mute control.
     */
    public BooleanControl muteControl() {
        return muteControl;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public Path getAudioPath() {
        return audioPath;
    }

    @Override
    public PlaybackState getCurrentPlaybackState() {
        return currentPlaybackState;
    }

    @Override
    public PlaybackState getPreviousPlaybackState() {
        return previousPlaybackState;
    }

    @Override
    public AudioEventListener getAudioEventListener() {
        return audioEventListener;
    }

    @Override
    public AudioInputStream getAudioInputStream() {
        return audioInputStream;
    }

    /**
     * Gets the audio's backing {@link SourceDataLine} object.
     *
     * @return The audio's {@code SourceDataLine}.
     */
    @Override
    public SourceDataLine getAudioSource() {
        return sourceDataLine;
    }

    @Override
    public void play() {
        StreamedAudioPlayer.playAudio(this);
    }

    @Override
    public void pause() {
        forcedStop = true;
        StreamedAudioPlayer.pauseAudio(this);
        forcedTimeout.schedule(() -> forcedStop = false, 20, TimeUnit.MILLISECONDS);
    }

    @Override
    public void resume() {
        StreamedAudioPlayer.resumeAudio(this);
    }

    @Override
    public void stop() {
        forcedStop = true;
        StreamedAudioPlayer.stopAudio(this);
        forcedTimeout.schedule(() -> forcedStop = false, 20, TimeUnit.MILLISECONDS);
    }

    @Override
    public String toString() {
        return "StreamedAudio{" +
                "audioPath=" + audioPath +
                ", id='" + id + '\'' +
                ", audioInputStream=" + audioInputStream +
                ", sourceDataLine=" + sourceDataLine +
                ", gainControl=" + gainControl +
                ", panControl=" + panControl +
                ", balanceControl=" + balanceControl +
                ", muteControl=" + muteControl +
                ", audioEventListener=" + audioEventListener +
                ", currentPlaybackState=" + currentPlaybackState +
                ", previousPlaybackState=" + previousPlaybackState +
                '}';
    }
}
