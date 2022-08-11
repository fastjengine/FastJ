package tech.fastj.input;

import tech.fastj.gameloop.event.Event;

import java.awt.event.InputEvent;

/**
 * General abstraction referring to input events.
 *
 * @author Andrew Dey
 * @since 1.7.0
 */
public abstract class InputActionEvent extends Event {
    /** {@return the {@link InputEvent raw AWT input event}} */
    public abstract InputEvent getRawEvent();
}
