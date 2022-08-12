package tech.fastj.graphics.display;

import tech.fastj.gameloop.event.Event;

import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;

/**
 * General event for a {@link Display} to produce window events from.
 *
 * @param <T> The type of the {@link Display display}.
 */
public class DisplayEvent<T extends Display> extends Event {

    private final DisplayEventType eventType;
    private final ComponentEvent rawEvent;
    private final T displaySource;
    private final boolean hasWindowEvent;

    /**
     * Constructs a display event.
     *
     * @param eventType     The {@link DisplayEventType type} of display event.
     * @param rawEvent      The {@link WindowEvent raw AWT window event}.
     * @param displaySource The {@link Display source of the event}.
     */
    public DisplayEvent(DisplayEventType eventType, WindowEvent rawEvent, T displaySource) {
        this.eventType = eventType;
        this.rawEvent = rawEvent;
        this.displaySource = displaySource;
        this.hasWindowEvent = true;
    }

    /**
     * Constructs a display event.
     *
     * @param eventType     The {@link DisplayEventType type} of display event.
     * @param rawEvent      The {@link ComponentEvent raw AWT component event}, signaling a {@link DisplayEventType#Resize resize} or
     *                      {@link DisplayEventType#Move move} event.
     * @param displaySource The {@link Display source of the event}.
     */
    public DisplayEvent(DisplayEventType eventType, ComponentEvent rawEvent, T displaySource) {
        this.eventType = eventType;
        this.rawEvent = rawEvent;
        this.displaySource = displaySource;
        this.hasWindowEvent = false;
    }

    /** {@return the type of display event} */
    public DisplayEventType getEventType() {
        return eventType;
    }

    /**
     * {@return the raw AWT event}
     * <p>
     * When checking the {@link #getEventType() display event type}, both {@link DisplayEventType#Resize resize} and
     * {@link DisplayEventType#Move move} types signal a non-window event. If a window event <i>is</i> present, then this can be cast to
     * {@link WindowEvent}.
     */
    public ComponentEvent getRawEvent() {
        return rawEvent;
    }

    /** {@return the event's source {@link Display display}} */
    public T getDisplaySource() {
        return displaySource;
    }

    /**
     * {@return whether the event has a window event}
     * <p>
     * When checking the {@link #getEventType() display event type}, both {@link DisplayEventType#Resize resize} and
     * {@link DisplayEventType#Move move} types signal a non-window event. If a window event <i>is</i> present, then {@link #getRawEvent()}
     * can be cast to {@link WindowEvent}.
     */
    public boolean hasWindowEvent() {
        return hasWindowEvent;
    }
}
