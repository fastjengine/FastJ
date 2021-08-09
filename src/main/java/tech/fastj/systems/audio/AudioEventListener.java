package tech.fastj.systems.audio;

import tech.fastj.systems.audio.state.PlaybackState;

import javax.sound.sampled.LineEvent;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * An event listener for the {@link Audio} interface.
 *
 * @author Andrew Dey
 * @since 1.5.0
 */
public class AudioEventListener {

    private Consumer<LineEvent> audioOpenAction;
    private Consumer<LineEvent> audioCloseAction;
    private Consumer<LineEvent> audioStartAction;
    private Consumer<LineEvent> audioStopAction;
    private Consumer<LineEvent> audioPauseAction;
    private Consumer<LineEvent> audioResumeAction;

    private final Audio audio;

    private static final Map<LineEvent.Type, BiConsumer<LineEvent, AudioEventListener>> AudioEventProcessor = Map.of(
            LineEvent.Type.OPEN, (audioEvent, audioEventListener) -> audioEventListener.audioOpenAction.accept(audioEvent),
            LineEvent.Type.START, (audioEvent, audioEventListener) -> {
                PlaybackState previousPlaybackState = audioEventListener.audio.getPreviousPlaybackState();
                switch (previousPlaybackState) {
                    /* The audio event system includes audio events for when audio is paused, and
                     * when the audio's playing stream is stopped (either temporarily or
                     * permanently).
                     *
                     * As of right now, I've intentionally had both trigger because it made sense
                     * at the time -- when an audio stream stops, it could be either paused or
                     * completely stopped. If it is paused, then an extra event should be created
                     * for that.
                     *
                     * Feel free to dispute this though -- I've been considering adding
                     * those break statements for a while now. */
                    case Paused: {
                        audioEventListener.audioResumeAction.accept(audioEvent);
                    }
                    case Stopped: {
                        audioEventListener.audioStartAction.accept(audioEvent);
                        break;
                    }
                    default: {
                        throw new IllegalStateException("audio state was unexpected and invalid:\n" + previousPlaybackState);
                    }
                }
            },
            LineEvent.Type.STOP, (audioEvent, audioEventListener) -> {
                PlaybackState currentPlaybackState = audioEventListener.audio.getCurrentPlaybackState();
                switch (currentPlaybackState) {
                    /* See the above comment. */
                    case Paused: {
                        audioEventListener.audioPauseAction.accept(audioEvent);
                    }
                    case Stopped: {
                        audioEventListener.audioStopAction.accept(audioEvent);
                        break;
                    }
                    default: {
                        throw new IllegalStateException("audio state was unexpected and invalid:\n" + currentPlaybackState);
                    }
                }
            },
            LineEvent.Type.CLOSE, (audioEvent, audioEventListener) -> audioEventListener.audioCloseAction.accept(audioEvent)
    );

    /**
     * Initializes an {@code AudioEventListener} with the specified {@code Audio} object, immediately attaching to it
     * for listening.
     *
     * @param audio The {@code Audio} object for the event listener to listen to.
     */
    AudioEventListener(Audio audio) {
        this.audio = Objects.requireNonNull(audio);
    }

    /**
     * Gets the "audio open" event action.
     *
     * @return The "audio open" event action.
     */
    public Consumer<LineEvent> getAudioOpenAction() {
        return audioOpenAction;
    }

    /**
     * Gets the "audio close" event action.
     *
     * @return The "audio close" event action.
     */
    public Consumer<LineEvent> getAudioCloseAction() {
        return audioCloseAction;
    }

    /**
     * Gets the "audio start" event action.
     *
     * @return The "audio start" event action.
     */
    public Consumer<LineEvent> getAudioStartAction() {
        return audioStartAction;
    }

    /**
     * Gets the "audio stop" event action.
     *
     * @return The "audio stop" event action.
     */
    public Consumer<LineEvent> getAudioStopAction() {
        return audioStopAction;
    }

    /**
     * Gets the "audio pause" event action.
     *
     * @return The "audio pause" event action.
     */
    public Consumer<LineEvent> getAudioPauseAction() {
        return audioPauseAction;
    }

    /**
     * Gets the "audio resume" event action.
     *
     * @return The "audio resume" event action.
     */
    public Consumer<LineEvent> getAudioResumeAction() {
        return audioResumeAction;
    }

    /**
     * Sets the "audio open" event action to the action specified.
     *
     * @param audioOpenAction The action to set.
     */
    public void setAudioOpenAction(Consumer<LineEvent> audioOpenAction) {
        this.audioOpenAction = audioOpenAction;
    }

    /**
     * Sets the "audio close" event action to the action specified.
     *
     * @param audioCloseAction The action to set.
     */
    public void setAudioCloseAction(Consumer<LineEvent> audioCloseAction) {
        this.audioCloseAction = audioCloseAction;
    }

    /**
     * Sets the "audio start" event action to the action specified.
     *
     * @param audioStartAction The action to set.
     */
    public void setAudioStartAction(Consumer<LineEvent> audioStartAction) {
        this.audioStartAction = audioStartAction;
    }

    /**
     * Sets the "audio stop" event action to the action specified.
     *
     * @param audioStopAction The action to set.
     */
    public void setAudioStopAction(Consumer<LineEvent> audioStopAction) {
        this.audioStopAction = audioStopAction;
    }

    /**
     * Sets the "audio pause" event action to the action specified.
     *
     * @param audioPauseAction The action to set.
     */
    public void setAudioPauseAction(Consumer<LineEvent> audioPauseAction) {
        this.audioPauseAction = audioPauseAction;
    }

    /**
     * Sets the "audio resume" event action to the action specified.
     *
     * @param audioResumeAction The action to set.
     */
    public void setAudioResumeAction(Consumer<LineEvent> audioResumeAction) {
        this.audioResumeAction = audioResumeAction;
    }

    /**
     * Fires an audio event to the event listener.
     *
     * @param audioEvent The event fired.
     */
    public void fireEvent(LineEvent audioEvent) {
        AudioEventProcessor.get(audioEvent.getType()).accept(audioEvent, this);
    }
}
