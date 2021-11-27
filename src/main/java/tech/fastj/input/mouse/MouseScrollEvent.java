package tech.fastj.input.mouse;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MouseScrollEvent implements MouseActionEvent {

    private final MouseWheelEvent mouseWheelEvent;
    private final MouseScrollType mouseScrollType;
    private final double wheelRotation;
    private final double scrollAmount;

    public MouseScrollEvent(MouseWheelEvent mouseWheelEvent) {
        this.mouseWheelEvent = mouseWheelEvent;
        this.mouseScrollType = MouseScrollType.get(mouseWheelEvent.getScrollType());
        this.wheelRotation = mouseWheelEvent.getPreciseWheelRotation();
        switch (mouseScrollType) {
            case Block: {
                this.scrollAmount = mouseWheelEvent.getPreciseWheelRotation();
                break;
            }
            case Unit: {
                this.scrollAmount = mouseWheelEvent.getUnitsToScroll();
                break;
            }
            default: {
                throw new IllegalStateException("Invalid mouse scroll type: " + mouseScrollType);
            }
        }
    }

    @Override
    public MouseEvent getMouseEvent() {
        return mouseWheelEvent;
    }

    public MouseWheelEvent getMouseWheelEvent() {
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
}
