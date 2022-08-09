package tech.fastj.systems.audio;

import tech.fastj.engine.FastJEngine;
import tech.fastj.gameloop.event.EventObserver;
import tech.fastj.systems.audio.state.PlaybackState;

import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.sound.sampled.LineEvent;

/**
 * An event listener for the {@link Audio} interface.
 *
 * @author Andrew Dey
 * @since 1.5.0
 */
public class AudioEventListener implements EventObserver<AudioEvent> {

    private Consumer<AudioEvent> audioOpenAction;
    private Consumer<AudioEvent> audioCloseAction;
    private Consumer<AudioEvent> audioStartAction;
    private Consumer<AudioEvent> audioStopAction;
    private Consumer<AudioEvent> audioPauseAction;
    private Consumer<AudioEvent> audioResumeAction;

    private final Audio audio;

    private static final Map<LineEvent.Type, BiConsumer<AudioEvent, AudioEventListener>> AudioEventProcessor = Map.of(
            LineEvent.Type.OPEN, (audioEvent, audioEventListener) -> {
                if (audioEventListener.audioOpenAction != null) {
                    audioEventListener.audioOpenAction.accept(audioEvent);
                }
            },
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
                        if (audioEventListener.audioResumeAction != null) {
                            audioEventListener.audioResumeAction.accept(audioEvent);
                        }
                    }
                    case Stopped: {
                        if (audioEventListener.audioStartAction != null) {
                            audioEventListener.audioStartAction.accept(audioEvent);
                        }
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
                        if (audioEventListener.audioPauseAction != null) {
                            audioEventListener.audioPauseAction.accept(audioEvent);
                        }
                    }
                    case Stopped: {
                        if (audioEventListener.audioStopAction != null) {
                            audioEventListener.audioStopAction.accept(audioEvent);
                        }
                        break;
                    }
                    default: {
                        throw new IllegalStateException("audio state was unexpected and invalid:\n" + currentPlaybackState);
                    }
                }
            },
            LineEvent.Type.CLOSE, (audioEvent, audioEventListener) -> {
                if (audioEventListener.audioCloseAction != null) {
                    audioEventListener.audioCloseAction.accept(audioEvent);
                }
            }
    );

    /**
     * Initializes an {@code AudioEventListener} with the specified {@code Audio} object, immediately attaching to it
     * for listening.
     *
     * @param audio The {@code Audio} object for the event listener to listen to.
     */
    AudioEventListener(Audio audio) {
        this.audio = Objects.requireNonNull(audio);
        FastJEngine.getGameLoop().addEventObserver(this, AudioEvent.class);
    }

    /** {@return the "audio open" event action} */
    public Consumer<AudioEvent> getAudioOpenAction() {
        return audioOpenAction;
    }

    /** {@return the "audio close" event action} */
    public Consumer<AudioEvent> getAudioCloseAction() {
        return audioCloseAction;
    }

    /** {@return the "audio start" event action} */
    public Consumer<AudioEvent> getAudioStartAction() {
        return audioStartAction;
    }

    /** {@return the "audio stop" event action} */
    public Consumer<AudioEvent> getAudioStopAction() {
        return audioStopAction;
    }

    /** {@return the "audio pause" event action} */
    public Consumer<AudioEvent> getAudioPauseAction() {
        return audioPauseAction;
    }

    /** {@return the "audio resume" event action} */
    public Consumer<AudioEvent> getAudioResumeAction() {
        return audioResumeAction;
    }

    /**
     * Sets the "audio open" event action to the action specified.
     *
     * @param audioOpenAction The action to run when the audio {@link Audio#play() is opened to start playing}.
     */
    public void setAudioOpenAction(Consumer<AudioEvent> audioOpenAction) {
        this.audioOpenAction = audioOpenAction;
    }

    /**
     * Sets the "audio close" event action to the action specified.
     *
     * @param audioCloseAction The action to run when the audio {@link Audio#() is closed to stop playing}.
     */
    public void setAudioCloseAction(Consumer<AudioEvent> audioCloseAction) {
        this.audioCloseAction = audioCloseAction;
    }

    /**
     * Sets the "audio start" event action to the action specified.
     *
     * @param audioStartAction The action to run when the audio {@link Audio#play() begins playing, regardless of if it is resuming}.
     */
    public void setAudioStartAction(Consumer<AudioEvent> audioStartAction) {
        this.audioStartAction = audioStartAction;
    }

    /**
     * Sets the "audio stop" event action to the action specified.
     *
     * @param audioStopAction The action to run when the audio {@link Audio#stop()} stops playing, regardless of if it is pausing}.
     */
    public void setAudioStopAction(Consumer<AudioEvent> audioStopAction) {
        this.audioStopAction = audioStopAction;
    }

    /**
     * Sets the "audio pause" event action to the action specified.
     *
     * @param audioPauseAction The action to run when the audio {@link Audio#pause()} is paused}.
     */
    public void setAudioPauseAction(Consumer<AudioEvent> audioPauseAction) {
        this.audioPauseAction = audioPauseAction;
    }

    /**
     * Sets the "audio resume" event action to the action specified.
     *
     * @param audioResumeAction The action to run when the audio {@link Audio#resume()} is resumed}.
     */
    public void setAudioResumeAction(Consumer<AudioEvent> audioResumeAction) {
        this.audioResumeAction = audioResumeAction;
    }

    /**
     * Fires an audio event to the event listener.
     *
     * @param audioEvent The event fired.
     */
    @Override
    public void eventReceived(AudioEvent audioEvent) {
        AudioEventProcessor.get(audioEvent.getRawEvent().getType()).accept(audioEvent, this);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        AudioEventListener audioEventListener = (AudioEventListener) other;
        return Objects.equals(audioOpenAction, audioEventListener.audioOpenAction)
                && Objects.equals(audioCloseAction, audioEventListener.audioCloseAction)
                && Objects.equals(audioStartAction, audioEventListener.audioStartAction)
                && Objects.equals(audioStopAction, audioEventListener.audioStopAction)
                && Objects.equals(audioPauseAction, audioEventListener.audioPauseAction)
                && Objects.equals(audioResumeAction, audioEventListener.audioResumeAction)
                && audio.equals(audioEventListener.audio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(audioOpenAction, audioCloseAction, audioStartAction, audioStopAction, audioPauseAction, audioResumeAction, audio);
    }
}
