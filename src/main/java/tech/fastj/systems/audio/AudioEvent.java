package tech.fastj.systems.audio;

import tech.fastj.gameloop.event.Event;
import tech.fastj.systems.audio.state.PlaybackState;

import javax.sound.sampled.LineEvent;

/**
 * {@link Event} typically fired from an {@link Audio} instance.
 * <p>
 * This event is fired when an {@link Audio} instance {@link Audio#play() opens/begins playing}, {@link Audio#stop() closes/stops playing},
 * {@link Audio#pause() pauses}, or {@link Audio#resume() resumes}.
 *
 * @author Andrew Dey
 * @since 1.7.0
 */
public class AudioEvent extends Event {

    private final LineEvent rawEvent;
    private final Audio eventSource;
    private final PlaybackState eventState;

    /**
     * Constructs an {@link AudioEvent audio event} with a {@link #getEventState() null playback state change}.
     *
     * @param rawEvent    The {@link LineEvent raw audio event} this event wraps around.
     * @param eventSource The {@link Audio source of the event}.
     */
    public AudioEvent(LineEvent rawEvent, Audio eventSource) {
        this.rawEvent = rawEvent;
        this.eventSource = eventSource;
        this.eventState = null;
    }

    /**
     * Constructs an {@link AudioEvent audio event}.
     *
     * @param rawEvent    The {@link LineEvent raw audio event} this event wraps around.
     * @param eventSource The {@link Audio source of the event}.
     * @param eventState  The {@link PlaybackState playback state change}.
     */
    public AudioEvent(LineEvent rawEvent, Audio eventSource, PlaybackState eventState) {
        this.rawEvent = rawEvent;
        this.eventSource = eventSource;
        this.eventState = eventState;
    }

    /** {@return the {@link LineEvent raw audio event} this event wraps around} */
    public LineEvent getRawEvent() {
        return rawEvent;
    }

    /** {@return the source of the {@link LineEvent}} */
    public Audio getEventSource() {
        return eventSource;
    }

    /**
     * {@return the {@link PlaybackState playback state change, if any}}
     * <p>
     * The {@link PlaybackState} can be {@code null} if the event was created without a change in the
     * {@link #getEventSource() event source's} playback state.
     *
     * <ul>
     *     <li>This will be null for events fired when the audio {@link Audio#play() opens} or {@link Audio#stop() closes}.</li>
     *     <li>This will <b>not</b> be null for events fired when the audio {@link Audio#play() beings playing}.</li>
     * </ul>
     */
    public PlaybackState getEventState() {
        return eventState;
    }

    @Override
    public String toString() {
        return "AudioEvent{" +
            "rawEvent=" + rawEvent +
            ", eventSource=" + eventSource +
            ", eventState=" + eventState +
            '}';
    }
}
