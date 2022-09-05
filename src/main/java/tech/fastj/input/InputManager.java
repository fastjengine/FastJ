package tech.fastj.input;

import tech.fastj.engine.FastJEngine;
import tech.fastj.gameloop.event.EventObserverCombo;
import tech.fastj.graphics.display.FastJCanvas;
import tech.fastj.input.keyboard.Keyboard;
import tech.fastj.input.keyboard.KeyboardActionListener;
import tech.fastj.input.keyboard.events.KeyboardActionEvent;
import tech.fastj.input.mouse.MouseActionListener;
import tech.fastj.input.mouse.events.MouseActionEvent;
import tech.fastj.systems.control.Scene;
import tech.fastj.systems.control.SimpleManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class to manage user input and input event processing.
 * <p>
 * This class fires input events to {@link KeyboardActionListener}s or {@link MouseActionListener}s in its lists of keyboard/mouse action
 * listeners.
 */
public class InputManager {

    private final List<KeyboardActionListener> keyboardActionListeners;
    private final List<MouseActionListener> mouseActionListeners;

    private boolean isLoaded;

    /** Initializes {@link InputManager}'s internals. */
    public InputManager() {
        keyboardActionListeners = new ArrayList<>();
        mouseActionListeners = new ArrayList<>();
    }

    /**
     * Gets the list of keyboard action listeners.
     *
     * @return The list of {@link KeyboardActionListener keyboard action listeners}.
     */
    @SuppressWarnings("unchecked")
    public List<KeyboardActionListener> getKeyboardActionListeners() {
        return (List) FastJEngine.getGameLoop().getEventObservers(KeyboardActionEvent.class)
            .stream()
            .map(EventObserverCombo::eventObserver)
            .collect(Collectors.toList());
    }

    /**
     * Gets the list of mouse action listeners.
     *
     * @return The list of {@code MouseActionListeners}.
     */
    @SuppressWarnings("unchecked")
    public List<MouseActionListener> getMouseActionListeners() {
        return (List) FastJEngine.getGameLoop().getEventObservers(MouseActionEvent.class)
            .stream()
            .map(EventObserverCombo::eventObserver)
            .collect(Collectors.toList());
    }

    /* Key Action Listeners */

    /**
     * Adds the specified {@link KeyboardActionListener}.
     * <p>
     * This method does not allow for {@link KeyboardActionListener}s to be added more than once.
     *
     * @param listener The {@link KeyboardActionListener} to be added.
     */
    public void addKeyboardActionListener(KeyboardActionListener listener) {
        keyboardActionListeners.add(listener);
        FastJEngine.getGameLoop().addEventObserver(KeyboardActionEvent.class, listener);
    }

    /**
     * Removes the specified {@link KeyboardActionListener}.
     *
     * @param listener The {@link KeyboardActionListener} to be removed.
     */
    public void removeKeyboardActionListener(KeyboardActionListener listener) {
        keyboardActionListeners.remove(listener);
        FastJEngine.getGameLoop().removeEventObserver(KeyboardActionEvent.class, listener);
    }

    /** Fires a {@code keys down} event to all listening {@link KeyboardActionListener keyboard action listeners}. */
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
        mouseActionListeners.add(listener);
        FastJEngine.getGameLoop().addEventObserver(MouseActionEvent.class, listener);
    }

    /**
     * Removes the specified {@code MouseActionListener}.
     *
     * @param listener The {@code MouseActionListener} to be removed.
     */
    public void removeMouseActionListener(MouseActionListener listener) {
        mouseActionListeners.remove(listener);
        FastJEngine.getGameLoop().removeEventObserver(MouseActionEvent.class, listener);
    }

    /**
     * Adds all the input manager's input listeners from the {@link FastJEngine#getGameLoop() engine's game loop}, particularly called
     * internally during {@link Scene#load(FastJCanvas) scene loading} or
     * {@link SimpleManager#init(FastJCanvas) simple maanger initialization}.
     * <p>
     * You do not usually need to call this method yourself -- your game structure (usually {@link Scene} or {@link SimpleManager})
     * automatically calls this when necessary.
     */
    public void load() {
        if (isLoaded) {
            return;
        }

        for (MouseActionListener mouseActionListener : mouseActionListeners) {
            FastJEngine.getGameLoop().addEventObserver(MouseActionEvent.class, mouseActionListener);
        }
        for (KeyboardActionListener keyboardActionListener : keyboardActionListeners) {
            FastJEngine.getGameLoop().addEventObserver(KeyboardActionEvent.class, keyboardActionListener);
        }
        isLoaded = true;
    }

    /**
     * Removes all the input manager's input listeners from the {@link FastJEngine#getGameLoop() engine's game loop}, particularly called
     * during {@link Scene#unload(FastJCanvas) scene unloading} or {@link SimpleManager#reset() simple maanger resetting}.
     * <p>
     * You do not usually need to call this method yourself -- your game structure (usually {@link Scene} or {@link SimpleManager})
     * automatically calls this when necessary.
     */
    public void unload() {
        if (!isLoaded) {
            return;
        }
        for (MouseActionListener mouseActionListener : mouseActionListeners) {
            FastJEngine.getGameLoop().removeEventObserver(MouseActionEvent.class, mouseActionListener);
        }
        for (KeyboardActionListener keyboardActionListener : keyboardActionListeners) {
            FastJEngine.getGameLoop().removeEventObserver(KeyboardActionEvent.class, keyboardActionListener);
        }
        isLoaded = false;
    }

    /** Resets the input manager. */
    public void reset() {
        FastJEngine.getGameLoop().removeEventHandler(KeyboardActionEvent.class);
        FastJEngine.getGameLoop().removeEventHandler(MouseActionEvent.class);
    }
}
