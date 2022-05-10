package tech.fastj.systems.audio;

import tech.fastj.systems.audio.state.PlaybackState;
import tech.fastj.systems.tags.TaggableEntity;

import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

/**
 * The base of sound objects used in FastJ for audio playback.
 * <p>
 * All {@code Audio} objects support the following controls:
 * <ul>
 *     <li>Play, Pause, Resume, Stop</li>
 *     <li>Audio Event Hooks (Audio Stream open, Audio Stream Close, Play, Pause, Resume, Stop)</li>
 *     <li>Playback State-keeping</li>
 * </ul>
 *
 * @author Andrew Dey
 * @since 1.5.0
 */
public abstract class Audio extends TaggableEntity {

    protected final Path audioPath;
    protected final String id;

    PlaybackState currentPlaybackState;
    PlaybackState previousPlaybackState;

    protected Audio(Path audioPath, String id) {
        this.audioPath = audioPath;
        this.id = id;
        currentPlaybackState = PlaybackState.Stopped;
        previousPlaybackState = PlaybackState.Stopped;
    }

    /**
     * Gets the audio path's id.
     *
     * @return The audio path's id.
     */
    public abstract String getID();

    /**
     * Gets the audio's {@link Path}.
     *
     * @return The audio's file path.
     */
    public abstract Path getAudioPath();

    /**
     * Gets the audio's current playback state.
     *
     * @return The audio current playback state.
     */
    public abstract PlaybackState getCurrentPlaybackState();

    /**
     * Gets the audio's previous playback state.
     *
     * @return The audio's previous playback state.
     */
    public abstract PlaybackState getPreviousPlaybackState();

    /**
     * Gets the audio's {@link AudioEventListener}.
     *
     * @return The audio's {@code AudioEventListener}.
     */
    public abstract AudioEventListener getAudioEventListener();

    /**
     * Gets the audio's {@link AudioInputStream} object.
     *
     * @return The audio's {@code AudioInputStream}.
     */
    public abstract AudioInputStream getAudioInputStream();

    /**
     * Gets the audio's backing source object.
     *
     * @return The audio's backing source object.
     */
    public abstract DataLine getAudioSource();

    /**
     * Starts playing audio's sound, if it was not previously playing.
     * <p>
     * Notes:
     * <ul>
     *     <li>To stop the audio's playback, use {@link Audio#stop()}.</li>
     *     <li>Starting the audio's playback calls an "audio start" event, which can be hooked into using {@link AudioEventListener#setAudioStartAction(Consumer)}.</li>
     *     <li>Starting the audio's playback calls an "audio open" event, which can be hooked into using {@link AudioEventListener#setAudioOpenAction(Consumer)}.</li>
     * </ul>
     */
    public abstract void play();

    /**
     * Pauses audio playback, if it was playing.
     * <p>
     * Notes:
     * <ul>
     *     <li>Starting the audio's playback calls an "audio pause" event, which can be hooked into using {@link AudioEventListener#setAudioPauseAction(Consumer)}.</li>
     * </ul>
     */
    public abstract void pause();

    /**
     * Resumes audio playback, if it was paused.
     * <p>
     * Notes:
     * <ul>
     *     <li>Starting the audio's playback calls an "audio resume" event, which can be hooked into using {@link AudioEventListener#setAudioResumeAction(Consumer)}.</li>
     * </ul>
     */
    public abstract void resume();

    /**
     * Stops the audio's sound output entirely.
     * <p>
     * Notes:
     * <ul>
     *     <li>To start the audio's playback again, use {@link Audio#play()}.</li>
     *     <li>Stopping the audio's playback calls an "audio stop" event, which can be hooked into using {@link AudioEventListener#setAudioStopAction(Consumer)}.</li>
     *     <li>Stopping the audio's playback calls an "audio close" event, which can be hooked into using {@link AudioEventListener#setAudioCloseAction(Consumer)}.</li>
     * </ul>
     */
    public abstract void stop();

    /**
     * Gets the audio's current playback position, in milliseconds.
     *
     * @return The audio's current playback position, in milliseconds.
     */
    public long getPlaybackPosition() {
        return TimeUnit.MILLISECONDS.convert(getAudioSource().getMicrosecondPosition(), TimeUnit.MILLISECONDS);
    }
}
