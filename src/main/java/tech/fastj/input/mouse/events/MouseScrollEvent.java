package tech.fastj.input.mouse.events;

import tech.fastj.input.mouse.MouseAction;
import tech.fastj.input.mouse.MouseScrollType;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Objects;

/**
 * Mouse event referring to a mouse {@link MouseAction#WheelScroll wheel scroll}.
 *
 * @author Andrew Dey
 * @since 1.7.0
 */
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
            case Block -> {
                this.scrollAmount = mouseWheelEvent.getPreciseWheelRotation();
                this.scrollAmountPerWheelRotation = 1;
            }
            case Unit -> {
                this.scrollAmount = mouseWheelEvent.getUnitsToScroll();
                this.scrollAmountPerWheelRotation = mouseWheelEvent.getScrollAmount();
            }
            default -> throw new IllegalStateException("Invalid mouse scroll type: " + mouseScrollType);
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

    /**
     * {@return the type of mouse wheel scroll}
     *
     * @see MouseWheelEvent#getScrollType()
     */
    public MouseScrollType getMouseScrollType() {
        return mouseScrollType;
    }

    /**
     * {@return the mouse wheel rotation amount}
     *
     * @see MouseWheelEvent#getPreciseWheelRotation()
     */
    public double getWheelRotation() {
        return wheelRotation;
    }

    /**
     * {@return the amount of mouse wheel scrolling}
     * <p>
     * This value is dependent on the {@link #getMouseScrollType() type of mouse scrolling} which was done. While the value returned is
     * properly scaled to the input, its backing value can vary:
     * <ul>
     *     <li>
     *         {@link MouseWheelEvent#getPreciseWheelRotation()} if the {@link #getMouseScrollType() mouse scroll type} was
     *         {@link MouseScrollType#Block}
     *     </li>
     *     <li>
     *         {@link MouseWheelEvent#getUnitsToScroll()} if the {@link #getMouseScrollType() mouse scroll type} was
     *         {@link MouseScrollType#Unit}
     *     </li>
     * </ul>
     *
     * @see MouseWheelEvent#getScrollAmount()
     * @see MouseWheelEvent#getPreciseWheelRotation()
     */
    public double getScrollAmount() {
        return scrollAmount;
    }

    /**
     * {@return the scroll amount for a single wheel rotation, based on the scroll type and amount}
     * <p>
     * This value is dependent on the {@link #getMouseScrollType() type of mouse scrolling} which was done. While the value returned is
     * properly scaled to the input, its backing value can vary:
     * <ul>
     *     <li>{@code 1} if the {@link #getMouseScrollType() mouse scroll type} was {@link MouseScrollType#Block}</li>
     *     <li>
     *         {@link MouseWheelEvent#getScrollAmount()} if the {@link #getMouseScrollType() mouse scroll type} was
     *         {@link MouseScrollType#Unit}
     *     </li>
     * </ul>
     */
    public int getScrollAmountPerWheelRotation() {
        return scrollAmountPerWheelRotation;
    }

    /**
     * {@return a mouse wheel scroll event instance from a {@link MouseEvent raw AWT event}}
     *
     * @param mouseWheelEvent Raw {@link MouseWindowEvent AWT mouse wheel event}.
     * @param eventType       The type of {@link MouseAction mouse action} performed to create this event.
     */
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
