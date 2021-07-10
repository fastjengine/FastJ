package tech.fastj.systems.audio;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/** An event listener for the {@link MemoryAudio} class. */
public class AudioEventListener implements LineListener {

    private Runnable audioOpenAction;
    private Runnable audioCloseAction;
    private Runnable audioStartAction;
    private Runnable audioStopAction;
    private Runnable audioPauseAction;
    private Runnable audioResumeAction;

    private final Audio audio;

    private static final Map<LineEvent.Type, Consumer<AudioEventListener>> AudioEventProcessor = Map.of(
            LineEvent.Type.OPEN, audioEventListener -> audioEventListener.audioOpenAction.run(),
            LineEvent.Type.CLOSE, audioEventListener -> audioEventListener.audioCloseAction.run(),
            LineEvent.Type.START, audioEventListener -> {
                switch (audioEventListener.audio.getPreviousPlaybackState()) {
                    case Paused: {
                        audioEventListener.audioResumeAction.run();
                        break;
                    }
                    case Stopped: {
                        audioEventListener.audioStartAction.run();
                        break;
                    }
                }
            },
            LineEvent.Type.STOP, audioEventListener -> {
                switch (audioEventListener.audio.getCurrentPlaybackState()) {
                    case Paused: {
                        audioEventListener.audioPauseAction.run();
                        break;
                    }
                    case Stopped: {
                        audioEventListener.audioStopAction.run();
                        break;
                    }
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
        this.audio.getAudioSource().addLineListener(this);
    }

    /**
     * Gets the "audio open" event action.
     *
     * @return The "audio open" event action.
     */
    public Runnable getAudioOpenAction() {
        return audioOpenAction;
    }

    /**
     * Gets the "audio close" event action.
     *
     * @return The "audio close" event action.
     */
    public Runnable getAudioCloseAction() {
        return audioCloseAction;
    }

    /**
     * Gets the "audio start" event action.
     *
     * @return The "audio start" event action.
     */
    public Runnable getAudioStartAction() {
        return audioStartAction;
    }

    /**
     * Gets the "audio stop" event action.
     *
     * @return The "audio stop" event action.
     */
    public Runnable getAudioStopAction() {
        return audioStopAction;
    }

    /**
     * Gets the "audio pause" event action.
     *
     * @return The "audio pause" event action.
     */
    public Runnable getAudioPauseAction() {
        return audioPauseAction;
    }

    /**
     * Gets the "audio resume" event action.
     *
     * @return The "audio resume" event action.
     */
    public Runnable getAudioResumeAction() {
        return audioResumeAction;
    }

    /**
     * Sets the "audio open" event action to the action specified.
     *
     * @param audioOpenAction The action to set.
     */
    public void setAudioOpenAction(Runnable audioOpenAction) {
        this.audioOpenAction = audioOpenAction;
    }

    /**
     * Sets the "audio close" event action to the action specified.
     *
     * @param audioCloseAction The action to set.
     */
    public void setAudioCloseAction(Runnable audioCloseAction) {
        this.audioCloseAction = audioCloseAction;
    }

    /**
     * Sets the "audio start" event action to the action specified.
     *
     * @param audioStartAction The action to set.
     */
    public void setAudioStartAction(Runnable audioStartAction) {
        this.audioStartAction = audioStartAction;
    }

    /**
     * Sets the "audio stop" event action to the action specified.
     *
     * @param audioStopAction The action to set.
     */
    public void setAudioStopAction(Runnable audioStopAction) {
        this.audioStopAction = audioStopAction;
    }

    /**
     * Sets the "audio pause" event action to the action specified.
     *
     * @param audioPauseAction The action to set.
     */
    public void setAudioPauseAction(Runnable audioPauseAction) {
        this.audioPauseAction = audioPauseAction;
    }

    /**
     * Sets the "audio resume" event action to the action specified.
     *
     * @param audioResumeAction The action to set.
     */
    public void setAudioResumeAction(Runnable audioResumeAction) {
        this.audioResumeAction = audioResumeAction;
    }

    @Override
    public void update(LineEvent event) {
        LineEvent.Type audioEventType = event.getType();
        AudioEventProcessor.get(audioEventType).accept(this);
    }
}
