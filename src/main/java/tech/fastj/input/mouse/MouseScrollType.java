package tech.fastj.input.mouse;

import java.awt.event.MouseWheelEvent;

/**
 * Types of scrolling produced by user input.
 *
 * @see MouseWheelEvent#getScrollType()
 */
public enum MouseScrollType {

    /**
     * Constant representing scrolling by "units" (like scrolling with the arrow keys).
     *
     * @see MouseWheelEvent#WHEEL_UNIT_SCROLL
     */
    Unit(MouseWheelEvent.WHEEL_UNIT_SCROLL),

    /**
     * Constant representing scrolling by a "block" (like scrolling with page-up, page-down keys).
     *
     * @see MouseWheelEvent#WHEEL_BLOCK_SCROLL
     */
    Block(MouseWheelEvent.WHEEL_BLOCK_SCROLL);

    final int scrollType;

    MouseScrollType(int scrollType) {
        this.scrollType = scrollType;
    }

    public static MouseScrollType get(int scrollType) {
        if (scrollType == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
            return Unit;
        } else if (scrollType == MouseWheelEvent.WHEEL_BLOCK_SCROLL) {
            return Block;
        }

        throw new IllegalArgumentException("Invalid scroll type: " + scrollType);
    }
}
