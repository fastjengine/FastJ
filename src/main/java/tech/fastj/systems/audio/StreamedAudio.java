package tech.fastj.systems.audio;

import tech.fastj.engine.CrashMessages;
import tech.fastj.engine.FastJEngine;

import tech.fastj.systems.audio.state.PlaybackState;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

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
public class StreamedAudio implements Audio {

    private final Path audioPath;
    private final String id;
    private final AudioInputStream audioInputStream;

    private SourceDataLine sourceDataLine;

    private FloatControl gainControl;
    private FloatControl panControl;
    private FloatControl balanceControl;
    private BooleanControl muteControl;

    private AudioEventListener audioEventListener;
    PlaybackState currentPlaybackState;
    PlaybackState previousPlaybackState;

    /**
     * Constructs the {@code StreamedAudio} object with the given path.
     *
     * @param audioPath The path of the audio to use.
     */
    StreamedAudio(Path audioPath) {
        this.audioPath = audioPath;
        this.id = UUID.randomUUID().toString();

        audioInputStream = Objects.requireNonNull(AudioManager.newAudioStream(audioPath));

        initializeStreamData();
    }

    /**
     * Constructs the {@code StreamedAudio} object with the given URL.
     *
     * @param audioPath The path of the audio to use.
     */
    StreamedAudio(URL audioPath) {
        this.id = UUID.randomUUID().toString();
        this.audioPath = AudioManager.pathFromURL(audioPath);

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

        try {
            sourceDataLine.open(audioInputStream.getFormat());
        } catch (LineUnavailableException exception) {
            FastJEngine.error(CrashMessages.theGameCrashed("an error while trying to open sound."), exception);
        }

        gainControl = (FloatControl) sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN);
        panControl = (FloatControl) sourceDataLine.getControl(FloatControl.Type.PAN);
        balanceControl = (FloatControl) sourceDataLine.getControl(FloatControl.Type.BALANCE);
        muteControl = (BooleanControl) sourceDataLine.getControl(BooleanControl.Type.MUTE);

        sourceDataLine.close();
        StreamedAudioPlayer.streamAudio(this);

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
