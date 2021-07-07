package tech.fastj.input.keyboard;

import tech.fastj.engine.FastJEngine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

import tech.fastj.input.InputManager;

/**
 * Class that stores key input information from the {@code Display}.
 *
 * @author Andrew Dey
 * @version 1.0.0
 */
public class Keyboard implements KeyListener {

    private static final Map<KeyDescription, Key> Keys = new HashMap<>();
    private static String lastKeyPressed = "";
    private static ScheduledExecutorService keyChecker;

    private static final Map<Integer, BiConsumer<InputManager, KeyEvent>> KeyEventProcessor = Map.of(
            KeyEvent.KEY_PRESSED, (inputManager, keyEvent) -> {
                KeyDescription keyDescription = KeyDescription.get(keyEvent.getKeyCode(), keyEvent.getKeyLocation());
                Key key = null;

                if (Keys.get(keyDescription) == null) {
                    key = new Key(keyEvent);
                    Keys.put(key.keyDescription, key);
                    keyDescription = KeyDescription.get(keyEvent.getKeyCode(), keyEvent.getKeyLocation());
                }

                if (key == null) {
                    key = Keys.get(keyDescription);
                }

                if (!key.currentlyPressed) {
                    key.setRecentPress(true);
                    inputManager.fireKeyEvent(KeyboardStateEvent.fromKeyEvent(keyEvent));
                }

                key.setCurrentPress(true);
            },
            KeyEvent.KEY_RELEASED, (inputManager, keyEvent) -> {
                KeyDescription keyDescription = KeyDescription.get(keyEvent.getKeyCode(), keyEvent.getKeyLocation());
                Key key = Keys.get(keyDescription);

                if (key != null) {
                    key.setCurrentPress(false);
                    key.setRecentPress(false);
                    key.setRecentRelease(true);
                }

                inputManager.fireKeyEvent(KeyboardStateEvent.fromKeyEvent(keyEvent));
            },
            KeyEvent.KEY_TYPED, (inputManager, keyEvent) -> {
                lastKeyPressed = KeyEvent.getKeyText(keyEvent.getKeyCode());
                inputManager.fireKeyEvent(KeyboardTypedEvent.fromKeyEvent(keyEvent));
            }
    );

    /** Initializes the keyboard. */
    public static void init() {
        keyChecker = Executors.newSingleThreadScheduledExecutor();
        keyChecker.scheduleWithFixedDelay(Keyboard::keyCheck, 1, 1, TimeUnit.MILLISECONDS);
    }

    /** Updates each key if it was recently pressed. */
    private static void keyCheck() {
        for (Key key : Keys.values()) {
            if (key.recentPress) {
                key.setRecentPress(!key.pressProgress());
            } else if (key.recentRelease) {
                key.setRecentRelease(!key.releaseProgress());
            }
        }
    }

    /** Clears all key input from the keyboard. */
    public static void reset() {
        Keys.clear();
    }

    /**
     * Checks if the specified key (at the specified key location) was recently pressed.
     * <p>
     * If the specified key was recently pressed, it will no longer be recently pressed when this method concludes.
     *
     * @param keyCode     Integer value to look for a specific key. The best way to look for a key is to use the
     *                    KeyEvent class.
     * @param keyLocation KeyType value that determines where this key is on the keyboard. This value is based on the
     *                    KeyEvent location values, using {@code STANDARD}, {@code LEFT}, {@code RIGHT}, and {@code
     *                    NUMPAD} to define the different possible locations for a key on the keyboard.
     * @return Boolean value that determines if the specified key was recently pressed.
     */
    public static boolean isKeyRecentlyPressed(int keyCode, int keyLocation) {
        KeyDescription keyDescription = KeyDescription.get(keyCode, keyLocation);
        if (keyDescription == null) {
            return false;
        }
        Key k = Keys.get(keyDescription);

        boolean recentlyPressed = k.recentPress;
        k.recentPress = false;
        return recentlyPressed;
    }

    /**
     * Checks if the specified key was recently pressed.
     * <p>
     * If the specified key was recently pressed, it will no longer be recently pressed when this method concludes.
     *
     * @param key Enum value specifying a specific key.
     * @return Boolean value that determines if the specified key was recently pressed.
     */
    public static boolean isKeyRecentlyPressed(Keys key) {
        return isKeyRecentlyPressed(key.keyCode, key.keyLocation);
    }

    /**
     * Checks if the specified key (at the specified key location) was recently released.
     * <p>
     * If the specified key was recently released, it will no longer be recently released when this method concludes.
     *
     * @param keyCode     Integer value to look for a specific key. The best way to look for a key is to use the
     *                    KeyEvent class.
     * @param keyLocation Integer value that determines where this key is on the keyboard. This value is based on the
     *                    KeyEvent location values, using {@code STANDARD}, {@code LEFT}, {@code RIGHT}, and {@code
     *                    NUMPAD} to define the different possible locations for a key on the keyboard.
     * @return Boolean value that determines if the specified key was recently released.
     */
    public static boolean isKeyRecentlyReleased(int keyCode, int keyLocation) {
        KeyDescription keyDescription = KeyDescription.get(keyCode, keyLocation);
        if (keyDescription == null) {
            return false;
        }
        Key key = Keys.get(keyDescription);

        boolean recentlyReleased = key.recentRelease;
        key.recentRelease = false;

        return recentlyReleased;
    }

    /**
     * Checks if the specified key was recently released.
     * <p>
     * If the specified key was recently released, it will no longer be recently released when this method concludes.
     * <p>
     * If the key specified is either {@code KeyEvent.VK_CONTROL} or {@code KeyEvent.VK_SHIFT}, by default it will check
     * for the left location.
     *
     * @param key Enum value specifying a specific key.
     * @return Boolean value that determines if the specified key was recently released.
     */
    public static boolean isKeyRecentlyReleased(Keys key) {
        return isKeyRecentlyReleased(key.keyCode, key.keyLocation);
    }

    /**
     * Checks if the specified key (at the specified key location) is currently pressed.
     *
     * @param keyCode     Integer value to look for a specific key. The best way to look for a key is to use the
     *                    KeyEvent class.
     * @param keyLocation Integer value that determines where this key is on the keyboard. This value is based on the
     *                    KeyEvent location values, using {@code STANDARD}, {@code LEFT}, {@code RIGHT}, and {@code
     *                    NUMPAD} to define the different possible locations for a key on the keyboard.
     * @return Boolean value that determines if the specified key is pressed.
     */
    public static boolean isKeyDown(int keyCode, int keyLocation) {
        KeyDescription keyDescription = KeyDescription.get(keyCode, keyLocation);
        if (keyDescription == null) {
            return false;
        }

        return Keys.get(keyDescription).isKeyDown;
    }

    /**
     * Checks if the specified key is currently pressed.
     * <p>
     * If the key specified is either {@code KeyEvent.VK_CONTROL} or {@code KeyEvent.VK_SHIFT}, by default it will check
     * for the left location.
     *
     * @param key Enum value specifying a specific key.
     * @return Boolean value that determines if the specified key is pressed.
     */
    public static boolean isKeyDown(Keys key) {
        return isKeyDown(key.keyCode, key.keyLocation);
    }

    /**
     * Gets the last key character pressed.
     *
     * @return Returns the String value of the last key character pressed.
     */
    public static String getLastKeyPressed() {
        return lastKeyPressed;
    }

    /**
     * Checks if any keys are pressed.
     *
     * @return boolean that determines whether there are any keys pressed.
     */
    public static boolean areKeysDown() {
        for (Key key : Keys.values()) {
            if (key.isKeyDown) {
                return true;
            }
        }
        return false;
    }

    public static void stop() {
        reset();

        if (keyChecker != null) {
            keyChecker.shutdownNow();
        }
        keyChecker = null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        FastJEngine.getLogicManager().receivedInputEvent(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        FastJEngine.getLogicManager().receivedInputEvent(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        FastJEngine.getLogicManager().receivedInputEvent(e);
    }

    /**
     * Processes the specified key event for the specified input manager, based on its event type.
     *
     * @param inputManager The input manager to fire the event to.
     * @param event        The key event to process.
     */
    public static void processEvent(InputManager inputManager, KeyEvent event) {
        KeyEventProcessor.get(event.getID()).accept(inputManager, event);
        /* Don't call the fireKeyEvent here!
         * KeyEvent.KEY_PRESSED only gets called under certain
         * conditions, so it cannot be abstracted to work here without some serious effort. */
    }

    /**
     * Class to store the data and state of a key being pressed or not.
     * <p>
     * Each {@code Key} also contains a {@code KeyDescription} that defines the code of the key, and its location on the
     * keyboard.
     */
    private static class Key {
        private final KeyDescription keyDescription;
        private boolean isKeyDown;

        private boolean recentPress;
        private boolean recentRelease;
        private boolean currentlyPressed;

        private int pressTimer;
        private int releaseTimer;

        /**
         * Constructs a key with a {@link Keyboard.KeyDescription}.
         *
         * @param keyEvent The key event from which to create the key description.
         */
        private Key(KeyEvent keyEvent) {
            keyDescription = new KeyDescription(keyEvent);
        }

        /** Progresses the key's state of being recently pressed. */
        private boolean pressProgress() {
            pressTimer++;
            return pressTimer >= 25;
        }

        /** Progresses the key's state of being recently released. */
        private boolean releaseProgress() {
            releaseTimer++;
            return releaseTimer >= 25;
        }

        /**
         * Sets whether the key is currently pressed.
         *
         * @param keyPressed Boolean that determines whether the key is currently pressed.
         */
        private void setCurrentPress(boolean keyPressed) {
            isKeyDown = keyPressed;
            currentlyPressed = keyPressed;
        }

        /**
         * Sets whether the key is recently pressed.
         *
         * @param keyPressed Boolean that determines whether the key is recently pressed.
         */
        private void setRecentPress(boolean keyPressed) {
            recentPress = keyPressed;
            if (!keyPressed) {
                pressTimer = 0;
            }
        }

        /**
         * Sets whether the key is recently released.
         *
         * @param keyReleased Boolean that determines whether the key is recently released.
         */
        private void setRecentRelease(boolean keyReleased) {
            recentRelease = keyReleased;
            if (!keyReleased) {
                releaseTimer = 0;
            }
        }

        @Override
        public String toString() {
            return "Key{" +
                    "keyDescription=" + keyDescription +
                    ", isKeyDown=" + isKeyDown +
                    ", recentPress=" + recentPress +
                    ", recentRelease=" + recentRelease +
                    ", lock=" + currentlyPressed +
                    ", pressTimer=" + pressTimer +
                    ", releaseTimer=" + releaseTimer +
                    '}';
        }
    }

    /** Class used to define a {@link Keyboard.Key}. */
    private static class KeyDescription {
        private final int keyCode;
        private final int keyLocation;

        /**
         * Constructs a key description using the specified key event.
         *
         * @param keyEvent The key event which this key description will be derived from.
         */
        private KeyDescription(KeyEvent keyEvent) {
            keyCode = keyEvent.getKeyCode();
            keyLocation = keyEvent.getKeyLocation();
        }

        /**
         * Gets the {@code KeyDescription} with the specified keycode and key location.
         *
         * @param keyCode     The key code to check for.
         * @param keyLocation The key description to check for.
         * @return The {@code KeyDescription} with the specified key code. If none matches, this returns {@code null}.
         */
        private static KeyDescription get(int keyCode, int keyLocation) {
            for (KeyDescription keyDescription : Keys.keySet()) {
                if (keyDescription.keyCode == keyCode && keyDescription.keyLocation == keyLocation) {
                    return keyDescription;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return "KeyDescription{" +
                    "keyCode=" + keyCode +
                    ", keyLocation=" + keyLocation +
                    '}';
        }
    }
}
