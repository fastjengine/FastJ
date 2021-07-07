package tech.fastj.input.keyboard;

import java.awt.event.KeyEvent;

public class KeyboardTypedEvent implements KeyboardEvent {
    private final KeyEvent keyEvent;

    private KeyboardTypedEvent(KeyEvent keyEvent) {
        this.keyEvent = keyEvent;
    }

    public String getKeyName() {
        return KeyEvent.getKeyText(keyEvent.getExtendedKeyCode());
    }

    public KeyEvent getKeyEvent() {
        return keyEvent;
    }

    static KeyboardTypedEvent fromKeyEvent(KeyEvent keyEvent) {
        return new KeyboardTypedEvent(keyEvent);
    }
}
