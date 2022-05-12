package tech.fastj.input;

import tech.fastj.engine.FastJEngine;

import tech.fastj.input.keyboard.Keyboard;
import tech.fastj.input.keyboard.KeyboardActionListener;
import tech.fastj.input.keyboard.events.KeyboardActionEvent;
import tech.fastj.input.mouse.MouseActionListener;
import tech.fastj.input.mouse.events.MouseActionEvent;

import java.util.List;

/**
 * Class to manage user input and input event processing.
 * <p>
 * This class fires input events to {@link KeyboardActionListener}s or {@link MouseActionListener}s in its lists of
 * keyboard/mouse action listeners.
 */
public class InputManager {

    /**
     * Gets the list of keyboard action listeners.
     *
     * @return The list of {@code KeyboardActionListeners}.
     */
    @SuppressWarnings("unchecked")
    public List<KeyboardActionListener> getKeyboardActionListeners() {
        return (List) FastJEngine.getGameLoop().getGameEventObservers(KeyboardActionEvent.class);
    }

    /**
     * Gets the list of mouse action listeners.
     *
     * @return The list of {@code MouseActionListeners}.
     */
    @SuppressWarnings("unchecked")
    public List<? extends MouseActionListener> getMouseActionListeners() {
        return (List) FastJEngine.getGameLoop().getGameEventObservers(MouseActionEvent.class);
    }

    /* Key Action Listeners */

    /**
     * Adds the specified {@code KeyboardActionListener}.
     * <p>
     * This method does not allow for {@code KeyboardActionListener}s to be added more than once.
     *
     * @param listener The {@code KeyboardActionListener} to be added.
     */
    public void addKeyboardActionListener(KeyboardActionListener listener) {
        FastJEngine.getGameLoop().addEventObserver(listener, KeyboardActionEvent.class);
    }

    /**
     * Removes the specified {@code KeyboardActionListener}.
     *
     * @param listener The {@code KeyboardActionListener} to be removed.
     */
    public void removeKeyboardActionListener(KeyboardActionListener listener) {
        FastJEngine.getGameLoop().removeEventObserver(listener, KeyboardActionEvent.class);
    }

    /** Fires a {@code keys down} event to all listening {@code KeyboardActionListeners}. */
    public void fireKeysDown() {
        if (Keyboard.areKeysDown()) {
            for (KeyboardActionListener listener : getKeyboardActionListeners()) {
                listener.onKeyDown(Keyboard.getKeysDown());
            }
        }
    }

    /* Mouse Action Listeners */

    /**
     * Adds the specified {@code MouseActionListener}.
     * <p>
     * This method does not allow for {@code MouseActionListener}s to be added more than once.
     *
     * @param listener The {@code MouseActionListener} to be added.
     */
    public void addMouseActionListener(MouseActionListener listener) {
        FastJEngine.getGameLoop().addEventObserver(listener, MouseActionEvent.class);
    }

    /**
     * Removes the specified {@code MouseActionListener}.
     *
     * @param listener The {@code MouseActionListener} to be removed.
     */
    public void removeMouseActionListener(MouseActionListener listener) {
        FastJEngine.getGameLoop().removeEventObserver(listener, MouseActionEvent.class);
    }

    /** Resets the input manager. */
    public void reset() {
        FastJEngine.getGameLoop().removeEventHandler(KeyboardActionEvent.class);
        FastJEngine.getGameLoop().removeEventHandler(MouseActionEvent.class);
    }
}
