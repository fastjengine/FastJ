package tech.fastj.input.keyboard.events;

import java.awt.event.KeyEvent;

public class KeyboardTypedEvent implements KeyboardActionEvent {

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

    public static KeyboardTypedEvent fromKeyEvent(KeyEvent keyEvent) {
        return new KeyboardTypedEvent(keyEvent);
    }
}
