package tech.fastj.input.mouse;

import tech.fastj.graphics.display.Display;
import tech.fastj.input.mouse.events.MouseButtonEvent;
import tech.fastj.input.mouse.events.MouseMotionEvent;
import tech.fastj.input.mouse.events.MouseWindowEvent;

import java.awt.event.MouseWheelEvent;

/** Enum defining the different types of mouse actions. */
public enum MouseAction {
    /** {@link MouseButtonEvent Mouse button action} where a mouse button was pressed. */
    Press,
    /** {@link MouseButtonEvent Mouse button action} where a mouse button, originally pressed, was released. */
    Release,
    /** {@link MouseButtonEvent Mouse button action} where a mouse button was pressed and released. */
    Click,
    /** {@link MouseMotionEvent Mouse motion action} where the mouse was moved. */
    Move,
    /** {@link MouseMotionEvent Mouse motion action} where the mouse was moved while a button was clicked. */
    Drag,
    /** {@link MouseWheelEvent Mouse wheel action} where the scroll wheel was scrolled. */
    WheelScroll,
    /** {@link MouseWindowEvent Mouse window action} where the mouse has moved onto the {@link Display}. */
    Enter,
    /** {@link MouseWindowEvent Mouse window action} where the mouse has moved off of the {@link Display}. */
    Exit;

    boolean recentAction;

    MouseAction() {
        recentAction = false;
    }
}
