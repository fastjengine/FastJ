package tech.fastj.input.mouse.events;

import tech.fastj.input.InputActionEvent;
import tech.fastj.input.mouse.MouseAction;

import java.awt.event.MouseEvent;

/**
 * General implementation of a mouse input event.
 *
 * @author Andrew Dey
 * @since 1.7.0
 */
public abstract class MouseActionEvent extends InputActionEvent {

    /** Empty default constructor. */
    protected MouseActionEvent() {
    }

    /** {@return the raw {@link MouseEvent AWT mouse event}} */
    @Override
    public abstract MouseEvent getRawEvent();

    /** {@return the {@link MouseAction type of mouse action} the event was based from} */
    public abstract MouseAction getEventType();
}
