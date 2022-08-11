package tech.fastj.input.mouse;

import java.awt.event.MouseEvent;

/**
 * Common mouse button values.
 * <p>
 * Many computer mice only contain 3 buttons, and as such this enum only defines for those first three. However, this enum is entirely
 * optional -- you can work with mouse buttons numbering as many as you wish, outside the range of these first three.
 */
public enum MouseButtons {
    /** Left mouse button. */
    Left(MouseEvent.BUTTON1),
    /** Right mouse button. */
    Right(MouseEvent.BUTTON3),
    /** Middle mouse button. */
    Middle(MouseEvent.BUTTON2);

    final int buttonValue;

    MouseButtons(int buttonValue) {
        this.buttonValue = buttonValue;
    }
}
