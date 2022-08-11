package tech.fastj.input.keyboard.events;

import tech.fastj.input.InputActionEvent;

import java.awt.event.KeyEvent;

/**
 * General implementation of a keyboard input event.
 *
 * @author Andrew Dey
 * @since 1.7.0
 */
public abstract class KeyboardActionEvent extends InputActionEvent {

    /** {@return the {@link KeyEvent raw AWT keyboard event}} */
    @Override
    public abstract KeyEvent getRawEvent();

    /** {@return the name of the key interacted with} */
    public abstract String getKeyName();
}
