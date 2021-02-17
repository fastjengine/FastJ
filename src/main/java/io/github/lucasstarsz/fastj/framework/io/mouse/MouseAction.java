package io.github.lucasstarsz.fastj.framework.io.mouse;

/** Enum that defines the different types of mouse actions available. */
public enum MouseAction {
    PRESS,
    RELEASE,
    CLICK,
    MOVE,
    DRAG,
    WHEEL_SCROLL,
    ENTER,
    EXIT;

    boolean recentAction;

    MouseAction() {
        recentAction = false;
    }
}
