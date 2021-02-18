package io.github.lucasstarsz.fastj.framework.io.mouse;

/** Convenience enum defining common mouse button values. */
public enum MouseButtons {
    /** Enum value for the left mouse button. */
    LEFT(1),
    /** Enum value for the right mouse button. */
    RIGHT(3),
    /** Enum value for the middle mouse button. */
    MIDDLE(2);

    final int buttonValue;

    MouseButtons(int buttonValue) {
        this.buttonValue = buttonValue;
    }
}
