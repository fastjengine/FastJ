package tech.fastj.systems.audio;

import tech.fastj.gameloop.event.GameEvent;

import javax.sound.sampled.LineEvent;

public class AudioEvent implements GameEvent {

    private final LineEvent rawEvent;
    private final Audio eventSource;

    private boolean isConsumed = false;

    public AudioEvent(LineEvent rawEvent, Audio eventSource) {
        this.rawEvent = rawEvent;
        this.eventSource = eventSource;
    }

    public LineEvent getRawEvent() {
        return rawEvent;
    }

    public Audio getEventSource() {
        return eventSource;
    }

    @Override
    public boolean isConsumed() {
        return isConsumed;
    }

    @Override
    public void consume() {
        isConsumed = true;
    }

    @Override
    public String toString() {
        return "AudioEvent{" +
                "rawEvent=" + rawEvent +
                ", eventSource=" + eventSource +
                '}';
    }
}
