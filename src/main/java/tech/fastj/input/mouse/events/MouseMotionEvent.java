package tech.fastj.input.mouse.events;

import tech.fastj.math.Pointf;

import tech.fastj.input.mouse.MouseAction;

import java.awt.event.MouseEvent;
import java.util.Objects;

public class MouseMotionEvent implements MouseActionEvent {

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

    public Pointf getMouseLocation() {
        return mouseLocation;
    }

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
