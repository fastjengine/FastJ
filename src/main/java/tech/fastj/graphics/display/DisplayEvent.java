package tech.fastj.graphics.display;

import tech.fastj.gameloop.event.GameEvent;

import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;

public class DisplayEvent<T extends Display> implements GameEvent {

    private final DisplayEventType eventType;
    private final ComponentEvent rawEvent;
    private final T displaySource;
    private final boolean hasWindowEvent;

    private boolean isConsumed = false;

    public DisplayEvent(DisplayEventType eventType, WindowEvent rawEvent, T displaySource) {
        this.eventType = eventType;
        this.rawEvent = rawEvent;
        this.displaySource = displaySource;
        this.hasWindowEvent = true;
    }

    public DisplayEvent(DisplayEventType eventType, ComponentEvent rawEvent, T displaySource) {
        this.eventType = eventType;
        this.rawEvent = rawEvent;
        this.displaySource = displaySource;
        this.hasWindowEvent = false;
    }

    public DisplayEventType getEventType() {
        return eventType;
    }

    public ComponentEvent getRawEvent() {
        return rawEvent;
    }

    public T getDisplaySource() {
        return displaySource;
    }

    public boolean hasWindowEvent() {
        return hasWindowEvent;
    }

    @Override
    public boolean isConsumed() {
        return isConsumed;
    }

    @Override
    public void consume() {
        isConsumed = true;
    }
}
