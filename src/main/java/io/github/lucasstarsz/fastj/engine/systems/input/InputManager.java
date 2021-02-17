package io.github.lucasstarsz.fastj.engine.systems.input;

import io.github.lucasstarsz.fastj.engine.io.Keyboard;
import io.github.lucasstarsz.fastj.engine.io.listeners.KeyboardActionListener;
import io.github.lucasstarsz.fastj.engine.io.listeners.MouseActionListener;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to manage user input.
 * <p>
 * This class fires input events to {@code KeyboardActionListener}s or {@code MouseActionListener}s in its lists of
 * keyboard/mouse action listeners.
 *
 * @see io.github.lucasstarsz.fastj.engine.io.listeners.KeyboardActionListener
 * @see io.github.lucasstarsz.fastj.engine.io.listeners.MouseActionListener
 */
public abstract class InputManager {
    private final List<KeyboardActionListener> keyActionListeners;
    private final List<MouseActionListener> mouseActionListeners;

    /** Constructs an {@code InputManager}, initializing its internal variables. */
    protected InputManager() {
        keyActionListeners = new ArrayList<>();
        mouseActionListeners = new ArrayList<>();
    }

    /**
     * Gets the list of keyboard action listeners for this {@code InputManager}.
     *
     * @return The list of {@code KeyboardActionListeners}.
     * @see io.github.lucasstarsz.fastj.engine.io.listeners.KeyboardActionListener
     */
    public List<KeyboardActionListener> getKeyboardActionListeners() {
        return keyActionListeners;
    }

    /**
     * Gets the list of mouse action listeners for this {@code InputManager}.
     *
     * @return The list of {@code MouseActionListeners}.
     * @see io.github.lucasstarsz.fastj.engine.io.listeners.KeyboardActionListener
     */
    public List<MouseActionListener> getMouseActionListeners() {
        return mouseActionListeners;
    }

    /* Key Action Listeners */

    /**
     * Adds the specified {@code KeyboardActionListener}.
     * <p>
     * This method does not allow for {@code KeyboardActionListener}s to be added more than once.
     *
     * @param listener The {@code KeyboardActionListener} to be added.
     * @see io.github.lucasstarsz.fastj.engine.io.listeners.KeyboardActionListener
     */
    public void addKeyboardActionListener(KeyboardActionListener listener) {
        if (!keyActionListeners.contains(listener)) {
            keyActionListeners.add(listener);
        }
    }

    /**
     * Removes the specified {@code KeyboardActionListener}.
     *
     * @param listener The {@code KeyboardActionListener} to be removed.
     * @see io.github.lucasstarsz.fastj.engine.io.listeners.KeyboardActionListener
     */
    public void removeKeyboardActionListener(KeyboardActionListener listener) {
        keyActionListeners.remove(listener);
    }

    /**
     * Fires a {@code key recently pressed} event to all listening {@code KeyboardActionListeners}.
     *
     * @param e The event to be fired through to the action listener.
     * @see KeyEvent
     */
    public void fireKeyRecentlyPressed(KeyEvent e) {
        List<KeyboardActionListener> keyListenersCopy = new ArrayList<>(keyActionListeners);
        for (KeyboardActionListener listener : keyListenersCopy) {
            listener.onKeyRecentlyPressed(e);
        }
    }

    /**
     * Fires a {@code key recently released} event to all listening {@code KeyboardActionListeners}.
     *
     * @param e The event to be fired through to the action listener.
     * @see KeyEvent
     */
    public void fireKeyReleased(KeyEvent e) {
        List<KeyboardActionListener> keyListenersCopy = new ArrayList<>(keyActionListeners);

        for (KeyboardActionListener listener : keyListenersCopy) {
            listener.onKeyReleased(e);
        }
    }

    /**
     * Fires a {@code key recently typed} event to all listening {@code KeyboardActionListeners}.
     *
     * @param e The event to be fired through to the action listener.
     * @see KeyEvent
     */
    public void fireKeyTyped(KeyEvent e) {
        List<KeyboardActionListener> keyListenersCopy = new ArrayList<>(keyActionListeners);

        for (KeyboardActionListener listener : keyListenersCopy) {
            listener.onKeyTyped(e);
        }
    }

    /**
     * Fires a {@code keys down} event to all listening {@code KeyboardActionListeners}.
     * <p>
     *
     * <b>NOTE:</b> When used by a FastJ {@code Scene}, this event gets fired every engine update
     * call, if there are any keys pressed.
     */
    public void fireKeysDown() {
        if (Keyboard.areKeysDown()) {
            List<KeyboardActionListener> keyListenersCopy = new ArrayList<>(keyActionListeners);
            for (KeyboardActionListener listener : keyListenersCopy) {
                listener.onKeyDown();
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
     * @see io.github.lucasstarsz.fastj.engine.io.listeners.MouseActionListener
     */
    public void addMouseActionListener(MouseActionListener listener) {
        if (!mouseActionListeners.contains(listener)) {
            mouseActionListeners.add(listener);
        }
    }

    /**
     * Removes the specified {@code MouseActionListener}.
     *
     * @param listener The {@code MouseActionListener} to be removed.
     * @see io.github.lucasstarsz.fastj.engine.io.listeners.MouseActionListener
     */
    public void removeMouseActionListener(MouseActionListener listener) {
        mouseActionListeners.remove(listener);
    }

    /**
     * Fires a {@code mouse button recently pressed} event to all listening {@code MouseActionListeners}.
     *
     * @param e The event to be fired through to the action listener.
     * @see MouseEvent
     */
    public void fireMousePressed(MouseEvent e) {
        List<MouseActionListener> mouseListenersCopy = new ArrayList<>(mouseActionListeners);

        for (MouseActionListener listener : mouseListenersCopy) {
            listener.onMousePressed(e);
        }
    }

    /**
     * Fires a {@code mouse button recently released} event to all listening {@code MouseActionListeners}.
     *
     * @param e The event to be fired through to the action listener.
     * @see MouseEvent
     */
    public void fireMouseReleased(MouseEvent e) {
        List<MouseActionListener> mouseListenersCopy = new ArrayList<>(mouseActionListeners);

        for (MouseActionListener listener : mouseListenersCopy) {
            listener.onMouseReleased(e);
        }
    }

    /**
     * Fires a {@code mouse button recently clicked} event to all listening {@code MouseActionListeners}.
     *
     * @param e The event to be fired through to the action listener.
     * @see MouseEvent
     */
    public void fireMouseClicked(MouseEvent e) {
        List<MouseActionListener> mouseListenersCopy = new ArrayList<>(mouseActionListeners);

        for (MouseActionListener listener : mouseListenersCopy) {
            listener.onMouseClicked(e);
        }
    }

    /**
     * Fires a {@code mouse moved} event to all listening {@code MouseActionListeners}.
     *
     * @param e The event to be fired through to the action listener.
     * @see MouseEvent
     */
    public void fireMouseMoved(MouseEvent e) {
        List<MouseActionListener> mouseListenersCopy = new ArrayList<>(mouseActionListeners);

        for (MouseActionListener listener : mouseListenersCopy) {
            listener.onMouseMoved(e);
        }
    }

    /**
     * Fires a {@code mouse dragged} event to all listening {@code MouseActionListeners}.
     *
     * @param e The event to be fired through to the action listener.
     * @see MouseEvent
     */
    public void fireMouseDragged(MouseEvent e) {
        List<MouseActionListener> mouseListenersCopy = new ArrayList<>(mouseActionListeners);

        for (MouseActionListener listener : mouseListenersCopy) {
            listener.onMouseDragged(e);
        }
    }

    /**
     * Fires a {@code mouse wheel scrolled} event to all listening {@code MouseActionListeners}.
     *
     * @param e The event to be fired through to the action listener.
     * @see MouseWheelEvent
     */
    public void fireMouseWheelScrolled(MouseWheelEvent e) {
        List<MouseActionListener> mouseListenersCopy = new ArrayList<>(mouseActionListeners);

        for (MouseActionListener listener : mouseListenersCopy) {
            listener.onMouseWheelScrolled(e);
        }
    }

    /**
     * Fires a {@code mouse entered screen} event to all listening {@code MouseActionListeners}.
     *
     * @param e The event to be fired through to the action listener.
     * @see MouseEvent
     */
    public void fireMouseEntered(MouseEvent e) {
        List<MouseActionListener> mouseListenersCopy = new ArrayList<>(mouseActionListeners);

        for (MouseActionListener listener : mouseListenersCopy) {
            listener.onMouseEntersScreen(e);
        }
    }

    /**
     * Fires a {@code mouse exited screen} event to all listening {@code MouseActionListeners}.
     *
     * @param e The event to be fired through to the action listener.
     * @see MouseEvent
     */
    public void fireMouseExited(MouseEvent e) {
        List<MouseActionListener> mouseListenersCopy = new ArrayList<>(mouseActionListeners);

        for (MouseActionListener listener : mouseListenersCopy) {
            listener.onMouseExitsScreen(e);
        }
    }

    /* Reset */

    /** Clears the list of keyboard action listeners. */
    public void clearKeyActionListeners() {
        keyActionListeners.clear();
    }

    /** Clears the list of mouse action listeners. */
    public void clearMouseActionListeners() {
        mouseActionListeners.clear();
    }

    /** Clears all action listener lists, effectively resetting the input manager. */
    public void clearAllLists() {
        clearKeyActionListeners();
        clearMouseActionListeners();
    }
}
