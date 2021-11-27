package tech.fastj.input.keyboard.events;

import java.awt.event.KeyEvent;

public class KeyboardTypedEvent implements KeyboardActionEvent {

    private final KeyEvent keyEvent;

    private KeyboardTypedEvent(KeyEvent keyEvent) {
        this.keyEvent = keyEvent;
    }

    public String getKeyName() {
        return KeyEvent.getKeyText(keyEvent.getExtendedKeyCode());
    }

    public KeyEvent getRawEvent() {
        return keyEvent;
    }

    public static KeyboardTypedEvent fromKeyEvent(KeyEvent keyEvent) {
        return new KeyboardTypedEvent(keyEvent);
    }
}
