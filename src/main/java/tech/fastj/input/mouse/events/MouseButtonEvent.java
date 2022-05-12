package tech.fastj.input.mouse.events;

import tech.fastj.input.mouse.MouseAction;

import java.awt.event.MouseEvent;
import java.util.Objects;

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

    public int getMouseButton() {
        return button;
    }

    public int getClickCount() {
        return clickCount;
    }

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
