package tech.fastj.input.mouse;

import java.awt.event.MouseEvent;

/** Convenience enum defining common mouse button values. */
public enum MouseButtons {
    /** Enum value for the left mouse button. */
    Left(MouseEvent.BUTTON1),
    /** Enum value for the right mouse button. */
    Right(MouseEvent.BUTTON3),
    /** Enum value for the middle mouse button. */
    Middle(MouseEvent.BUTTON2);

    final int buttonValue;

    MouseButtons(int buttonValue) {
        this.buttonValue = buttonValue;
    }
}
