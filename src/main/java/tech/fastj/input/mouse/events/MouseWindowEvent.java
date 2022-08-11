package tech.fastj.input.mouse.events;

import tech.fastj.graphics.display.Display;
import tech.fastj.input.mouse.MouseAction;
import tech.fastj.math.Pointf;

import java.awt.event.MouseEvent;
import java.util.Objects;

/**
 * Mouse event referring to a mouse {@link MouseAction#Enter entering} or {@link MouseAction#Exit exiting} the {@link Display display}.
 *
 * @author Andrew Dey
 * @since 1.7.0
 */
public class MouseWindowEvent extends MouseActionEvent {

    private final MouseEvent mouseEvent;
    private final Pointf windowInteractionPosition;
    private final MouseAction eventType;

    private MouseWindowEvent(MouseEvent mouseEvent, MouseAction eventType) {
        this.mouseEvent = mouseEvent;
        this.windowInteractionPosition = new Pointf(mouseEvent.getX(), mouseEvent.getY());
        this.eventType = eventType;
    }

    @Override
    public MouseEvent getRawEvent() {
        return mouseEvent;
    }

    @Override
    public MouseAction getEventType() {
        return eventType;
    }

    /** {@return the location where the mouse entered/exited the {@link Display}} */
    public Pointf getWindowInteractionPosition() {
        return windowInteractionPosition;
    }

    /**
     * {@return a mouse window event instance from a {@link MouseEvent raw AWT event}}
     *
     * @param mouseEvent Raw {@link MouseEvent AWT mouse event}.
     * @param eventType  The type of {@link MouseAction mouse action} performed to create this event.
     */
    public static MouseWindowEvent fromMouseEvent(MouseEvent mouseEvent, MouseAction eventType) {
        return new MouseWindowEvent(mouseEvent, eventType);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        MouseWindowEvent mouseWindowEvent = (MouseWindowEvent) other;
        return windowInteractionPosition.equals(mouseWindowEvent.windowInteractionPosition)
            && eventType == mouseWindowEvent.eventType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(windowInteractionPosition, eventType);
    }

    @Override
    public String toString() {
        return "MouseWindowEvent{" +
            "mouseEvent=" + mouseEvent +
            ", windowInteractionPosition=" + windowInteractionPosition +
            ", eventType=" + eventType +
            '}';
    }
}
