package io.github.lucasstarsz.fastj.systems.input.mouse;

import io.github.lucasstarsz.fastj.systems.render.Display;

/** Enum defining the different types of mouse actions. */
public enum MouseAction {
    /** Mouse action where a mouse button was pressed. */
    PRESS,
    /** Mouse action where a mouse button, originally pressed, was released. */
    RELEASE,
    /** Mouse action where a mouse button was pressed and released. */
    CLICK,
    /** Mouse action where the mouse was moved. */
    MOVE,
    /** Mouse action where the mouse was moved while a button was clicked. */
    DRAG,
    /** Mouse action where the scroll wheel was scrolled. */
    WHEEL_SCROLL,
    /** Mouse action where the mouse has moved onto the {@link Display}. */
    ENTER,
    /** Mouse action where the mouse has moved off of the {@link Display}. */
    EXIT;

    boolean recentAction;

    MouseAction() {
        recentAction = false;
    }
}
