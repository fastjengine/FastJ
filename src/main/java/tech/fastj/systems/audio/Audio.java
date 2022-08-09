package tech.fastj.systems.audio;

import tech.fastj.systems.audio.state.PlaybackState;
import tech.fastj.systems.tags.TaggableEntity;

import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;

/**
 * The base of sound instances used in FastJ for audio playback.
 * <p>
 * All {@code Audio} objects support the following:
 * <ul>
 *     <li>{@link #play() Play}, {@link #pause() Pause}, {@link #resume() Resume}, and {@link #stop() Stop}</li>
 *     <li>
 *         Audio Event Hooks ({@link AudioEventListener#setAudioOpenAction(Consumer) Audio Stream Open},
 *         {@link AudioEventListener#setAudioCloseAction(Consumer) Audio Stream Close},
 *         {@link AudioEventListener#setAudioStartAction(Consumer) Play}, {@link AudioEventListener#setAudioStopAction(Consumer) Pause},
 *         {@link AudioEventListener#setAudioResumeAction(Consumer) Resume}, and
 *         {@link AudioEventListener#setAudioStopAction(Consumer) Stop}).
 *     </li>
 *     <li>{@link PlaybackState Playback States}</li>
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

    /** {@return The audio path's id} */
    public abstract String getID();

    /**
     * Gets the audio's {@link Path}.
     *
     * @return The audio's file path.
     */
    public abstract Path getAudioPath();

    /** {@return the audio's current playback state} */
    public abstract PlaybackState getCurrentPlaybackState();

    /** {@return the audio's previous playback state} */
    public abstract PlaybackState getPreviousPlaybackState();

    /** {@return the audio's {@link AudioEventListener audio event listener}} */
    public abstract AudioEventListener getAudioEventListener();

    /** {@return the audio's {@link AudioInputStream audio input stream}} */
    public abstract AudioInputStream getAudioInputStream();

    /** {return the audio's backing source object} */
    public abstract DataLine getAudioSource();

    /**
     * Starts playing audio's sound, opening IO connections as needed.
     * <p>
     * Notes:
     * <ul>
     *     <li><b>This method is not used for resuming audio when paused.</b> To resume audio, use {@link #resume()}.</li>
     *     <li>To stop the audio's playback, use {@link Audio#stop()}.</li>
     *     <li>
     *         Opening the audio stream fires an {@link AudioEvent#AudioEvent(LineEvent, Audio)} "audio open" event}, which can be
     *         {@link AudioEventListener#setAudioOpenAction(Consumer) hooked into}.
     *     </li>
     *     <li>
     *         Starting the audio's playback fires an {@link AudioEvent#AudioEvent(LineEvent, Audio, PlaybackState)} "audio start" event},
     *         which can be {@link AudioEventListener#setAudioStartAction(Consumer) hooked into}.
     *     </li>
     * </ul>
     */
    public abstract void play();

    /**
     * Pauses audio playback, if it was playing.
     * <p>
     * Notes:
     * <ul>
     *     <li>
     *         Pausing the audio's playback fires an {@link AudioEvent#AudioEvent(LineEvent, Audio, PlaybackState)} "audio pause" event},
     *         which can be {@link AudioEventListener#setAudioPauseAction(Consumer) hooked into}.
     *     </li>
     * </ul>
     */
    public abstract void pause();

    /**
     * Resumes audio playback, if it was paused.
     * <p>
     * Notes:
     * <ul>
     *     <li>
     *         Resuming the audio's playback fires an {@link AudioEvent#AudioEvent(LineEvent, Audio, PlaybackState)} "audio resume" event},
     *         which can be {@link AudioEventListener#setAudioResumeAction(Consumer) hooked into}.
     *     </li>
     * </ul>
     */
    public abstract void resume();

    /**
     * Stops the audio's sound output entirely, and closes any IO connections.
     * <p>
     * Notes:
     * <ul>
     *     <li><b>This method is not used for pausing audio when playing.</b> To pause audio, use {@link #pause()}.</li>
     *     <li>To start the audio's playback again, use {@link Audio#play()}.</li>
     *     <li>
     *         Closing the audio stream fires an {@link AudioEvent#AudioEvent(LineEvent, Audio)} "audio close" event}, which can be
     *         {@link AudioEventListener#setAudioCloseAction(Consumer) hooked into}.
     *     </li>
     *     <li>
     *         Stopping the audio's playback fires an {@link AudioEvent#AudioEvent(LineEvent, Audio, PlaybackState)} "audio stop" event},
     *         which can be {@link AudioEventListener#setAudioStopAction(Consumer) hooked into}.
     *     </li>
     * </ul>
     */
    public abstract void stop();

    /** {@return the audio's current playback position, in {@link TimeUnit#MILLISECONDS milliseconds}} */
    public long getPlaybackPosition() {
        return TimeUnit.MILLISECONDS.convert(getAudioSource().getMicrosecondPosition(), TimeUnit.MILLISECONDS);
    }
}
