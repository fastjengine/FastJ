package tech.fastj.input.keyboard.events;

import java.awt.event.KeyEvent;

/**
 * Key event referring to a key being typed.
 *
 * @author Andrew Dey
 * @since 1.7.0
 */
public class KeyboardTypedEvent extends KeyboardActionEvent {

    private final KeyEvent keyEvent;

    private KeyboardTypedEvent(KeyEvent keyEvent) {
        this.keyEvent = keyEvent;
    }

    @Override
    public String getKeyName() {
        return String.valueOf(keyEvent.getKeyChar());
    }

    @Override
    public KeyEvent getRawEvent() {
        return keyEvent;
    }

    /**
     * {@return a keyboard type event instance from a {@link KeyEvent raw AWT event}}
     *
     * @param keyEvent {@link KeyEvent raw AWT keyboard event}}
     */
    public static KeyboardTypedEvent fromKeyEvent(KeyEvent keyEvent) {
        return new KeyboardTypedEvent(keyEvent);
    }
}
