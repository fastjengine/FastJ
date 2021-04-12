package io.github.lucasstarsz.fastj.systems.input;

import io.github.lucasstarsz.fastj.systems.input.keyboard.Keyboard;
import io.github.lucasstarsz.fastj.systems.input.keyboard.KeyboardActionListener;
import io.github.lucasstarsz.fastj.systems.input.mouse.Mouse;
import io.github.lucasstarsz.fastj.systems.input.mouse.MouseActionListener;
import io.github.lucasstarsz.fastj.systems.control.Scene;

import java.awt.event.InputEvent;
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
 */
public class InputManager {
    private final List<KeyboardActionListener> keyActionListeners;
    private final List<MouseActionListener> mouseActionListeners;

    private final List<InputEvent> receivedInputEvents;
    private final List<InputEvent> eventBacklog;
    private volatile boolean isProcessingEvents;

    /** Constructs an {@code InputManager}, initializing its internal variables. */
    public InputManager() {
        keyActionListeners = new ArrayList<>();
        mouseActionListeners = new ArrayList<>();

        receivedInputEvents = new ArrayList<>();
        eventBacklog = new ArrayList<>();
    }

    /**
     * Gets the list of keyboard action listeners for this {@code InputManager}.
     *
     * @return The list of {@code KeyboardActionListeners}.
     */
    public List<KeyboardActionListener> getKeyboardActionListeners() {
        return keyActionListeners;
    }

    /**
     * Gets the list of mouse action listeners for this {@code InputManager}.
     *
     * @return The list of {@code MouseActionListeners}.
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
     */
    public void removeKeyboardActionListener(KeyboardActionListener listener) {
        keyActionListeners.remove(listener);
    }

    /**
     * Fires a {@code key recently pressed} event to all listening {@code KeyboardActionListeners}.
     *
     * @param e The event to be fired through to the action listener.
     */
    public void fireKeyRecentlyPressed(KeyEvent e) {
        for (KeyboardActionListener listener : keyActionListeners) {
            listener.onKeyRecentlyPressed(e);
        }
    }

    /**
     * Fires a {@code key recently released} event to all listening {@code KeyboardActionListeners}.
     *
     * @param e The event to be fired through to the action listener.
     */
    public void fireKeyReleased(KeyEvent e) {
        for (KeyboardActionListener listener : keyActionListeners) {
            listener.onKeyReleased(e);
        }
    }

    /**
     * Fires a {@code key recently typed} event to all listening {@code KeyboardActionListeners}.
     *
     * @param e The event to be fired through to the action listener.
     */
    public void fireKeyTyped(KeyEvent e) {
        for (KeyboardActionListener listener : keyActionListeners) {
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
            for (KeyboardActionListener listener : keyActionListeners) {
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
     */
    public void removeMouseActionListener(MouseActionListener listener) {
        mouseActionListeners.remove(listener);
    }

    /**
     * Fires a {@code mouse button recently pressed} event to all listening {@code MouseActionListeners}.
     *
     * @param e The event to be fired through to the action listener.
     */
    public void fireMousePressed(MouseEvent e) {
        for (MouseActionListener listener : mouseActionListeners) {
            listener.onMousePressed(e);
        }
    }

    /**
     * Fires a {@code mouse button recently released} event to all listening {@code MouseActionListeners}.
     *
     * @param e The event to be fired through to the action listener.
     */
    public void fireMouseReleased(MouseEvent e) {
        for (MouseActionListener listener : mouseActionListeners) {
            listener.onMouseReleased(e);
        }
    }

    /**
     * Fires a {@code mouse button recently clicked} event to all listening {@code MouseActionListeners}.
     *
     * @param e The event to be fired through to the action listener.
     */
    public void fireMouseClicked(MouseEvent e) {
        for (MouseActionListener listener : mouseActionListeners) {
            listener.onMouseClicked(e);
        }
    }

    /**
     * Fires a {@code mouse moved} event to all listening {@code MouseActionListeners}.
     *
     * @param e The event to be fired through to the action listener.
     */
    public void fireMouseMoved(MouseEvent e) {
        for (MouseActionListener listener : mouseActionListeners) {
            listener.onMouseMoved(e);
        }
    }

    /**
     * Fires a {@code mouse dragged} event to all listening {@code MouseActionListeners}.
     *
     * @param e The event to be fired through to the action listener.
     */
    public void fireMouseDragged(MouseEvent e) {
        for (MouseActionListener listener : mouseActionListeners) {
            listener.onMouseDragged(e);
        }
    }

    /**
     * Fires a {@code mouse wheel scrolled} event to all listening {@code MouseActionListeners}.
     *
     * @param e The event to be fired through to the action listener.
     */
    public void fireMouseWheelScrolled(MouseWheelEvent e) {
        for (MouseActionListener listener : mouseActionListeners) {
            listener.onMouseWheelScrolled(e);
        }
    }

    /**
     * Fires a {@code mouse entered screen} event to all listening {@code MouseActionListeners}.
     *
     * @param e The event to be fired through to the action listener.
     */
    public void fireMouseEntered(MouseEvent e) {
        for (MouseActionListener listener : mouseActionListeners) {
            listener.onMouseEntersScreen(e);
        }
    }

    /**
     * Fires a {@code mouse exited screen} event to all listening {@code MouseActionListeners}.
     *
     * @param e The event to be fired through to the action listener.
     */
    public void fireMouseExited(MouseEvent e) {
        for (MouseActionListener listener : mouseActionListeners) {
            listener.onMouseExitsScreen(e);
        }
    }

    /* Received input */

    /**
     * Stores the specified input in the event list to be processed later. (See: {@link #processEvents(Scene)}
     * <p>
     * If event processing is still going on when the event is received, the event gets added to the backlog. That
     * backlog gets emptied into the main event list after all the events in that main list have been processed.
     *
     * @param event The event to be stored for processing later.
     */
    public void receivedInputEvent(InputEvent event) {
        if (isProcessingEvents) {
            eventBacklog.add(event);
        } else {
            receivedInputEvents.add(event);
        }
    }

    /**
     * Processes all events in the event list, then clears them from the list.
     * <p>
     * This method also empties the event backlog into the main event set after all the current events have been
     * processed and removed.
     *
     * @param current The scene to process events for.
     */
    public void processEvents(Scene current) {
        isProcessingEvents = true;

        for (InputEvent event : receivedInputEvents) {
            if (event instanceof MouseEvent) {
                Mouse.processEvent(current, (MouseEvent) event);
            } else if (event instanceof KeyEvent) {
                Keyboard.processEvent(current, (KeyEvent) event);
            }
        }

        receivedInputEvents.clear();

        isProcessingEvents = false;

        // empty event backlog into received input events
        receivedInputEvents.addAll(eventBacklog);
        eventBacklog.clear();
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
