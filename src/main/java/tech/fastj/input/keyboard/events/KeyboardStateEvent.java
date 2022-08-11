package tech.fastj.input.keyboard.events;

import tech.fastj.input.keyboard.Keys;

import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Key event referring to a key being pressed or released.
 *
 * @author Andrew Dey
 * @since 1.7.0
 */
public class KeyboardStateEvent extends KeyboardActionEvent {

    private static final Map<int[], Keys> KeyboardMap = new ConcurrentHashMap<>();
    private final KeyEvent keyEvent;
    private final Keys key;

    private KeyboardStateEvent(KeyEvent keyEvent) {
        this.keyEvent = keyEvent;
        if (KeyboardMap.containsKey(new int[] {keyEvent.getKeyCode(), keyEvent.getKeyLocation()})) {
            key = KeyboardMap.get(new int[] {keyEvent.getKeyCode(), keyEvent.getKeyLocation()});
        } else {
            key = Keys.get(keyEvent);
            KeyboardMap.put(new int[] {keyEvent.getKeyCode(), keyEvent.getKeyLocation()}, key);
        }
    }

    /** {@return the key interacted with} */
    public Keys getKey() {
        return key;
    }

    @Override
    public KeyEvent getRawEvent() {
        return keyEvent;
    }

    @Override
    public String getKeyName() {
        return String.valueOf(keyEvent.getKeyChar());
    }

    /**
     * {@return a keyboard state event instance from a {@link KeyEvent raw AWT event}}
     *
     * @param keyEvent {@link KeyEvent raw AWT keyboard event}}
     */
    public static KeyboardStateEvent fromKeyEvent(KeyEvent keyEvent) {
        return new KeyboardStateEvent(keyEvent);
    }
}
