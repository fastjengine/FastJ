package tech.fastj.input.mouse;

import tech.fastj.graphics.display.Display;

/** Enum defining the different types of mouse actions. */
public enum MouseAction {
    /** Mouse action where a mouse button was pressed. */
    Press,
    /** Mouse action where a mouse button, originally pressed, was released. */
    Release,
    /** Mouse action where a mouse button was pressed and released. */
    Click,
    /** Mouse action where the mouse was moved. */
    Move,
    /** Mouse action where the mouse was moved while a button was clicked. */
    Drag,
    /** Mouse action where the scroll wheel was scrolled. */
    WheelScroll,
    /** Mouse action where the mouse has moved onto the {@link Display}. */
    Enter,
    /** Mouse action where the mouse has moved off of the {@link Display}. */
    Exit;

    boolean recentAction;

    MouseAction() {
        recentAction = false;
    }
}
