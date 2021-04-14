package io.github.lucasstarsz.fastj.systems.input.mouse;

import java.awt.event.MouseEvent;

/** Convenience enum defining common mouse button values. */
public enum MouseButtons {
    /** Enum value for the left mouse button. */
    LEFT(MouseEvent.BUTTON1),
    /** Enum value for the right mouse button. */
    RIGHT(MouseEvent.BUTTON3),
    /** Enum value for the middle mouse button. */
    MIDDLE(MouseEvent.BUTTON2);

    final int buttonValue;

    MouseButtons(int buttonValue) {
        this.buttonValue = buttonValue;
    }
}
