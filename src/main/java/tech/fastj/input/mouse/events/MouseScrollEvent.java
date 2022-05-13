package tech.fastj.input.mouse.events;

import tech.fastj.input.mouse.MouseAction;
import tech.fastj.input.mouse.MouseScrollType;

import java.awt.event.MouseWheelEvent;
import java.util.Objects;

public class MouseScrollEvent extends MouseActionEvent {

    private final MouseWheelEvent mouseWheelEvent;
    private final MouseScrollType mouseScrollType;
    private final double wheelRotation;
    private final double scrollAmount;
    private final int scrollAmountPerWheelRotation;
    private final MouseAction eventType;

    private MouseScrollEvent(MouseWheelEvent mouseWheelEvent, MouseAction eventType) {
        this.mouseWheelEvent = mouseWheelEvent;
        this.eventType = eventType;
        this.mouseScrollType = MouseScrollType.get(mouseWheelEvent.getScrollType());
        this.wheelRotation = mouseWheelEvent.getPreciseWheelRotation();
        switch (mouseScrollType) {
            case Block: {
                this.scrollAmount = mouseWheelEvent.getPreciseWheelRotation();
                this.scrollAmountPerWheelRotation = 1;
                break;
            }
            case Unit: {
                this.scrollAmount = mouseWheelEvent.getUnitsToScroll();
                this.scrollAmountPerWheelRotation = mouseWheelEvent.getScrollAmount();
                break;
            }
            default: {
                throw new IllegalStateException("Invalid mouse scroll type: " + mouseScrollType);
            }
        }
    }

    @Override
    public MouseWheelEvent getRawEvent() {
        return mouseWheelEvent;
    }

    @Override
    public MouseAction getEventType() {
        return eventType;
    }

    public MouseScrollType getMouseScrollType() {
        return mouseScrollType;
    }

    public double getWheelRotation() {
        return wheelRotation;
    }

    public double getScrollAmount() {
        return scrollAmount;
    }

    public int getScrollAmountPerWheelRotation() {
        return scrollAmountPerWheelRotation;
    }

    public static MouseScrollEvent fromMouseWheelEvent(MouseWheelEvent mouseWheelEvent, MouseAction eventType) {
        return new MouseScrollEvent(mouseWheelEvent, eventType);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        MouseScrollEvent mouseScrollEvent = (MouseScrollEvent) other;
        return Double.compare(mouseScrollEvent.wheelRotation, wheelRotation) == 0
                && Double.compare(mouseScrollEvent.scrollAmount, scrollAmount) == 0
                && scrollAmountPerWheelRotation == mouseScrollEvent.scrollAmountPerWheelRotation
                && mouseScrollType == mouseScrollEvent.mouseScrollType
                && eventType == mouseScrollEvent.eventType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mouseScrollType, wheelRotation, scrollAmount, scrollAmountPerWheelRotation, eventType);
    }

    @Override
    public String toString() {
        return "MouseScrollEvent{" +
                "mouseWheelEvent=" + mouseWheelEvent +
                ", mouseScrollType=" + mouseScrollType +
                ", wheelRotation=" + wheelRotation +
                ", scrollAmount=" + scrollAmount +
                ", scrollAmountPerWheelRotation=" + scrollAmountPerWheelRotation +
                ", eventType=" + eventType +
                '}';
    }
}
