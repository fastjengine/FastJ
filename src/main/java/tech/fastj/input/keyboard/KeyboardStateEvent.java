package tech.fastj.input.keyboard;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class KeyboardStateEvent implements KeyboardEvent {
    private static final Map<int[], Keys> KeyboardMap = new HashMap<>();
    private final KeyEvent keyEvent;
    private final Keys key;

    private KeyboardStateEvent(KeyEvent keyEvent) {
        this.keyEvent = keyEvent;
        if (KeyboardMap.containsKey(new int[]{keyEvent.getKeyCode(), keyEvent.getKeyLocation()})) {
            key = KeyboardMap.get(new int[]{keyEvent.getKeyCode(), keyEvent.getKeyLocation()});
        } else {
            key = Keys.get(keyEvent);
            KeyboardMap.put(new int[]{keyEvent.getKeyCode(), keyEvent.getKeyLocation()}, key);
        }
    }

    public Keys getKey() {
        return key;
    }

    @Override
    public KeyEvent getKeyEvent() {
        return keyEvent;
    }

    @Override
    public String getKeyName() {
        return KeyEvent.getKeyText(keyEvent.getExtendedKeyCode());
    }

    static KeyboardStateEvent fromKeyEvent(KeyEvent keyEvent) {
        return new KeyboardStateEvent(keyEvent);
    }
}
