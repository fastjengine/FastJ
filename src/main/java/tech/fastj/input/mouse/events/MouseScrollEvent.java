package tech.fastj.input.mouse.events;

import tech.fastj.input.mouse.MouseScrollType;

import java.awt.event.MouseWheelEvent;

public class MouseScrollEvent implements MouseActionEvent {

    private final MouseWheelEvent mouseWheelEvent;
    private final MouseScrollType mouseScrollType;
    private final double wheelRotation;
    private final double scrollAmount;
    private final int scrollAmountPerWheelRotation;

    private MouseScrollEvent(MouseWheelEvent mouseWheelEvent) {
        this.mouseWheelEvent = mouseWheelEvent;
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

    public static MouseScrollEvent fromMouseWheelEvent(MouseWheelEvent mouseWheelEvent) {
        return new MouseScrollEvent(mouseWheelEvent);
    }
}
