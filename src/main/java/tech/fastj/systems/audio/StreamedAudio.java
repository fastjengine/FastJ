package tech.fastj.systems.audio;

import tech.fastj.engine.FastJEngine;

import tech.fastj.systems.audio.state.PlaybackState;

import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.FloatControl;
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

    private AudioInputStream audioInputStream;

    private SourceDataLine sourceDataLine;

    private FloatControl gainControl;
    private FloatControl panControl;
    private FloatControl balanceControl;
    private BooleanControl muteControl;

    private AudioEventListener audioEventListener;

    /**
     * Constructs the {@code StreamedAudio} object with the given path.
     *
     * @param audioPath The path of the audio to use.
     */
    StreamedAudio(Path audioPath) {
        super(audioPath, UUID.randomUUID().toString());

        audioInputStream = Objects.requireNonNull(AudioManager.newAudioStream(audioPath));

        initializeStreamData(false);
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

        initializeStreamData(false);
    }

    /** Initializes a {@code StreamedAudio}'s data line, controls, and event listeners. */
    private void initializeStreamData(boolean resetInputStream) {
        if (resetInputStream) {
            audioInputStream = Objects.requireNonNull(AudioManager.newAudioStream(audioPath));
        }
        System.out.println("reset data line");
        sourceDataLine = Objects.requireNonNull(AudioManager.newSourceDataLine(audioInputStream.getFormat()));

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


        Consumer<AudioEvent> transferOpenAction = null;
        Consumer<AudioEvent> transferCloseAction = null;
        Consumer<AudioEvent> transferStartAction = null;
        Consumer<AudioEvent> transferStopAction = null;
        Consumer<AudioEvent> transferPauseAction = null;
        Consumer<AudioEvent> transferResumeAction = null;
        if (audioEventListener != null) {
            transferOpenAction = audioEventListener.getAudioOpenAction();
            transferCloseAction = audioEventListener.getAudioCloseAction();
            transferStartAction = audioEventListener.getAudioStartAction();
            transferStopAction = audioEventListener.getAudioStopAction();
            transferPauseAction = audioEventListener.getAudioPauseAction();
            transferResumeAction = audioEventListener.getAudioResumeAction();
            FastJEngine.getGameLoop().removeEventObserver(audioEventListener, AudioEvent.class);
        }

        audioEventListener = new AudioEventListener(this);
        audioEventListener.setAudioOpenAction(transferOpenAction);
        audioEventListener.setAudioCloseAction(transferCloseAction);
        audioEventListener.setAudioStartAction(transferStartAction);
        audioEventListener.setAudioStopAction(transferStopAction);
        audioEventListener.setAudioPauseAction(transferPauseAction);
        audioEventListener.setAudioResumeAction(transferResumeAction);

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
        StreamedAudioPlayer.pauseAudio(this);
    }

    @Override
    public void resume() {
        StreamedAudioPlayer.resumeAudio(this);
    }

    @Override
    public void stop() {
        StreamedAudioPlayer.stopAudio(this);
    }

    public void reset() {
        if (sourceDataLine.isRunning()) {
            System.out.println("still running");
            stop();
        }
        if (!sourceDataLine.isRunning()) {
            System.out.println("reset time");
            initializeStreamData(true);
        }
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
