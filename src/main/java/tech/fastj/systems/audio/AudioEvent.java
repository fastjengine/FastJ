package tech.fastj.systems.audio;

import tech.fastj.systems.audio.state.PlaybackState;

import tech.fastj.gameloop.event.Event;

import javax.sound.sampled.LineEvent;

public class AudioEvent extends Event {

    private final LineEvent rawEvent;
    private final Audio eventSource;
    private final PlaybackState eventState;

    public AudioEvent(LineEvent rawEvent, Audio eventSource) {
        this.rawEvent = rawEvent;
        this.eventSource = eventSource;
        this.eventState = null;
    }

    public AudioEvent(LineEvent rawEvent, Audio eventSource, PlaybackState eventState) {
        this.rawEvent = rawEvent;
        this.eventSource = eventSource;
        this.eventState = eventState;
    }

    public LineEvent getRawEvent() {
        return rawEvent;
    }

    public Audio getEventSource() {
        return eventSource;
    }

    public PlaybackState getEventState() {
        return eventState;
    }

    @Override
    public String toString() {
        return "AudioEvent{" +
                "rawEvent=" + rawEvent +
                ", eventSource=" + eventSource +
                '}';
    }
}
