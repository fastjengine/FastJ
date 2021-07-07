package tech.fastj.input;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import tech.fastj.input.keyboard.Keyboard;
import tech.fastj.input.keyboard.KeyboardActionListener;
import tech.fastj.input.keyboard.KeyboardEvent;
import tech.fastj.input.keyboard.KeyboardStateEvent;
import tech.fastj.input.keyboard.KeyboardTypedEvent;
import tech.fastj.input.mouse.Mouse;
import tech.fastj.input.mouse.MouseActionListener;

/**
 * Class to manage user input and input event processing.
 * <p>
 * This class fires input events to {@link KeyboardActionListener}s or {@link MouseActionListener}s in its lists of
 * keyboard/mouse action listeners.
 */
public class InputManager {
    private final List<KeyboardActionListener> keyActionListeners;
    private final List<MouseActionListener> mouseActionListeners;

    private final List<InputEvent> receivedInputEvents;
    private final List<InputEvent> eventBacklog;
    private volatile boolean isProcessingEvents;

    private static final Map<Integer, BiConsumer<MouseEvent, List<MouseActionListener>>> MouseActionProcessor = Map.of(
            MouseEvent.MOUSE_PRESSED, (mouseEvent, mouseActionListenerList) -> {
                for (MouseActionListener mouseActionListener : mouseActionListenerList) {
                    mouseActionListener.onMousePressed(mouseEvent);
                }
            },
            MouseEvent.MOUSE_RELEASED, (mouseEvent, mouseActionListenerList) -> {
                for (MouseActionListener mouseActionListener : mouseActionListenerList) {
                    mouseActionListener.onMouseReleased(mouseEvent);
                }
            },
            MouseEvent.MOUSE_CLICKED, (mouseEvent, mouseActionListenerList) -> {
                for (MouseActionListener mouseActionListener : mouseActionListenerList) {
                    mouseActionListener.onMouseClicked(mouseEvent);
                }
            },
            MouseEvent.MOUSE_MOVED, (mouseEvent, mouseActionListenerList) -> {
                for (MouseActionListener mouseActionListener : mouseActionListenerList) {
                    mouseActionListener.onMouseMoved(mouseEvent);
                }
            },
            MouseEvent.MOUSE_DRAGGED, (mouseEvent, mouseActionListenerList) -> {
                for (MouseActionListener mouseActionListener : mouseActionListenerList) {
                    mouseActionListener.onMouseDragged(mouseEvent);
                }
            },
            MouseEvent.MOUSE_ENTERED, (mouseEvent, mouseActionListenerList) -> {
                for (MouseActionListener mouseActionListener : mouseActionListenerList) {
                    mouseActionListener.onMouseEntersScreen(mouseEvent);
                }
            },
            MouseEvent.MOUSE_EXITED, (mouseEvent, mouseActionListenerList) -> {
                for (MouseActionListener mouseActionListener : mouseActionListenerList) {
                    mouseActionListener.onMouseExitsScreen(mouseEvent);
                }
            },
            MouseEvent.MOUSE_WHEEL, (mouseEvent, mouseActionListenerList) -> {
                for (MouseActionListener mouseActionListener : mouseActionListenerList) {
                    mouseActionListener.onMouseWheelScrolled(mouseEvent);
                }
            }
    );

    private static final Map<Integer, BiConsumer<KeyboardEvent, List<KeyboardActionListener>>> KeyboardActionProcessor = Map.of(
            KeyEvent.KEY_PRESSED, (keyboardEvent, keyActionListenerList) -> {
                for (KeyboardActionListener keyboardActionListener : keyActionListenerList) {
                    keyboardActionListener.onKeyRecentlyPressed((KeyboardStateEvent) keyboardEvent);
                }
            },
            KeyEvent.KEY_RELEASED, (keyboardEvent, keyActionListenerList) -> {
                for (KeyboardActionListener keyboardActionListener : keyActionListenerList) {
                    keyboardActionListener.onKeyReleased((KeyboardStateEvent) keyboardEvent);
                }
            },
            KeyEvent.KEY_TYPED, (keyboardEvent, keyActionListenerList) -> {
                for (KeyboardActionListener keyboardActionListener : keyActionListenerList) {
                    keyboardActionListener.onKeyTyped((KeyboardTypedEvent) keyboardEvent);
                }
            }
    );

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
     * Fires a keyboard event to all listening {@code KeyboardActionListeners}.
     *
     * @param keyboardEvent The event to be fired to the action listeners.
     */
    public void fireKeyEvent(KeyboardEvent keyboardEvent) {
        KeyboardActionProcessor.get(keyboardEvent.getKeyEvent().getID()).accept(keyboardEvent, keyActionListeners);
    }

    /** Fires a {@code keys down} event to all listening {@code KeyboardActionListeners}. */
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
     * Fires a mouse event to all listening {@code MouseActionListeners}.
     *
     * @param mouseEvent The event to be fired to the action listeners.
     */
    public void fireMouseEvent(MouseEvent mouseEvent) {
        MouseActionProcessor.get(mouseEvent.getID()).accept(mouseEvent, mouseActionListeners);
    }

    /* Received input */

    /**
     * Stores the specified input in the event list to be processed later.
     * <p>
     * If event processing is still going on when the event is received, the event gets added to the backlog. That
     * backlog gets emptied into the main event list after all the events in that main list have been processed.
     *
     * @param event The event to be stored for processing later.
     * @see #processEvents()
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
     */
    public void processEvents() {
        isProcessingEvents = true;

        for (InputEvent inputEvent : receivedInputEvents) {
            if (inputEvent instanceof MouseEvent) {
                Mouse.processEvent(this, (MouseEvent) inputEvent);
            } else if (inputEvent instanceof KeyEvent) {
                Keyboard.processEvent(this, (KeyEvent) inputEvent);
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
