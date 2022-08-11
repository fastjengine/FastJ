package tech.fastj.input.mouse.events;

import tech.fastj.input.mouse.MouseAction;
import tech.fastj.input.mouse.MouseButtons;

import java.awt.event.MouseEvent;
import java.util.Objects;

/**
 * Mouse event referring to a button {@link MouseAction#Press press}, {@link MouseAction#Release release}, or
 * {@link MouseAction#Click click}.
 *
 * @author Andrew Dey
 * @since 1.7.0
 */
public class MouseButtonEvent extends MouseActionEvent {

    private final MouseEvent mouseEvent;
    private final int button;
    private final int clickCount;
    private final MouseAction eventType;

    private MouseButtonEvent(MouseEvent mouseEvent, MouseAction eventType) {
        this.mouseEvent = mouseEvent;
        this.eventType = eventType;
        this.button = mouseEvent.getButton();
        this.clickCount = mouseEvent.getClickCount();
    }

    @Override
    public MouseEvent getRawEvent() {
        return mouseEvent;
    }

    @Override
    public MouseAction getEventType() {
        return eventType;
    }

    /**
     * {@return the mouse button this event was produced from}
     * <p>
     * The returned mouse button may correspond to {@link MouseButtons}, which contains particular definitions assuming only 3 mouse
     * buttons. Many computer mice contain more mouse buttons, so the {@code int} value was provided instead of the enum value.
     */
    public int getMouseButton() {
        return button;
    }

    /**
     * {@return the number of clicks performed on this event}
     * <p>
     * This value corresponds with whether this event was from a {@link MouseAction#Click mouse button click}. If so, the value will be
     * {@code 1} or more. Otherwise (if the event is a mouse button {@link MouseAction#Press press} or {@link MouseAction#Release release}),
     * it will be {@code 0}.
     */
    public int getClickCount() {
        return clickCount;
    }

    /**
     * {@return a mouse button event instance from a {@link MouseEvent raw AWT event}}
     *
     * @param mouseEvent Raw {@link MouseEvent AWT mouse event}.
     * @param eventType  The type of {@link MouseAction mouse action} performed to create this event.
     */
    public static MouseButtonEvent fromMouseEvent(MouseEvent mouseEvent, MouseAction eventType) {
        return new MouseButtonEvent(mouseEvent, eventType);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        MouseButtonEvent mouseButtonEvent = (MouseButtonEvent) other;
        return button == mouseButtonEvent.button
            && clickCount == mouseButtonEvent.clickCount
            && eventType == mouseButtonEvent.eventType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(button, clickCount, eventType);
    }

    @Override
    public String toString() {
        return "MouseButtonEvent{" +
            "mouseEvent=" + mouseEvent +
            ", button=" + button +
            ", clickCount=" + clickCount +
            ", eventType=" + eventType +
            '}';
    }
}
