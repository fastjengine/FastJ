package tech.fastj.input.mouse.events;

import tech.fastj.input.mouse.MouseAction;
import tech.fastj.math.Pointf;

import java.awt.event.MouseEvent;
import java.util.Objects;

/**
 * Mouse event referring to a mouse {@link MouseAction#Move movement} or {@link MouseAction#Drag drag}.
 *
 * @author Andrew Dey
 * @since 1.7.0
 */
public class MouseMotionEvent extends MouseActionEvent {

    private final MouseEvent mouseEvent;
    private final Pointf mouseLocation;
    private final MouseAction eventType;

    private MouseMotionEvent(MouseEvent mouseEvent, MouseAction eventType) {
        this.mouseEvent = mouseEvent;
        this.mouseLocation = new Pointf(mouseEvent.getX(), mouseEvent.getY());
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

    /** {@return the location the mouse moved to, in this event} */
    public Pointf getMouseLocation() {
        return mouseLocation;
    }

    /**
     * {@return a mouse motion event instance from a {@link MouseEvent raw AWT event}}
     *
     * @param mouseEvent Raw {@link MouseEvent AWT mouse event}.
     * @param eventType  The type of {@link MouseAction mouse action} performed to create this event.
     */
    public static MouseMotionEvent fromMouseEvent(MouseEvent mouseEvent, MouseAction eventType) {
        return new MouseMotionEvent(mouseEvent, eventType);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        MouseMotionEvent mouseMotionEvent = (MouseMotionEvent) other;
        return mouseLocation.equals(mouseMotionEvent.mouseLocation)
            && eventType == mouseMotionEvent.eventType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mouseLocation, eventType);
    }

    @Override
    public String toString() {
        return "MouseMotionEvent{" +
            "mouseEvent=" + mouseEvent +
            ", mouseLocation=" + mouseLocation +
            ", eventType=" + eventType +
            '}';
    }
}
