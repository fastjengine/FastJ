package tech.fastj.input.keyboard;

import tech.fastj.engine.FastJEngine;
import tech.fastj.input.keyboard.events.KeyboardActionEvent;
import tech.fastj.input.keyboard.events.KeyboardStateEvent;
import tech.fastj.input.keyboard.events.KeyboardTypedEvent;
import tech.fastj.logging.Log;
import tech.fastj.logging.LogLevel;
import tech.fastj.systems.execution.FastJScheduledThreadPool;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Class that stores key input information from the {@code Display}.
 *
 * @author Andrew Dey
 * @since 1.0.0
 */
public class Keyboard implements KeyListener {

    private static final Map<KeyDescription, Key> AllKeys = new TreeMap<>((o1, o2) -> {
        if (o1 == null && o2 == null) {
            return 0;
        }
        if (o1 == null) {
            return 1;
        }
        if (o2 == null) {
            return -1;
        }

        return o1.compareTo(o2);
    });

    private static final Set<Keys> AllKeysDown = new TreeSet<>();
    private static String lastKeyPressed = "";
    private static ScheduledExecutorService keyChecker;

    private static final Map<Integer, Consumer<KeyEvent>> KeyEventProcessor = Map.of(
            KeyEvent.KEY_PRESSED, (keyEvent) -> {
                KeyDescription keyDescription = KeyDescription.get(keyEvent.getKeyCode(), keyEvent.getKeyLocation());
                Key key = null;

                if (AllKeys.get(keyDescription) == null) {
                    key = new Key(keyEvent);
                    AllKeys.put(key.keyDescription, key);
                    keyDescription = KeyDescription.get(keyEvent.getKeyCode(), keyEvent.getKeyLocation());
                }

                if (key == null) {
                    key = AllKeys.get(keyDescription);
                }

                if (!key.currentlyPressed) {
                    key.setRecentPress(true);
                }

                key.setCurrentPress(true);
            },
            KeyEvent.KEY_RELEASED, (keyEvent) -> {
                KeyDescription keyDescription = KeyDescription.get(keyEvent.getKeyCode(), keyEvent.getKeyLocation());
                Key key = AllKeys.get(keyDescription);

                if (key != null) {
                    key.setCurrentPress(false);
                    key.setRecentPress(false);
                    key.setRecentRelease(true);
                }
            },
            KeyEvent.KEY_TYPED, (keyEvent) -> lastKeyPressed = KeyEvent.getKeyText(keyEvent.getKeyCode())
    );

    /** Initializes the keyboard. */
    public static void init() {
        if (FastJEngine.isLogging(LogLevel.Debug)) {
            Log.debug(Keyboard.class, "Initializing {}", Keyboard.class.getName());
        }

        keyChecker = new FastJScheduledThreadPool(1);
        keyChecker.scheduleWithFixedDelay(Keyboard::keyCheck, 1, 1, TimeUnit.MILLISECONDS);
        FastJEngine.getGameLoop().addClassAlias(KeyboardStateEvent.class, KeyboardActionEvent.class);
        FastJEngine.getGameLoop().addClassAlias(KeyboardTypedEvent.class, KeyboardActionEvent.class);

        if (FastJEngine.isLogging(LogLevel.Debug)) {
            Log.debug(Keyboard.class, "Keyboard initialization complete.");
        }
    }

    /** Updates each key if it was recently pressed. */
    private static void keyCheck() {
        for (Key key : AllKeys.values()) {
            if (key.recentPress) {
                key.setRecentPress(!key.pressProgress());
            } else if (key.recentRelease) {
                key.setRecentRelease(!key.releaseProgress());
            }
        }
    }

    /** Clears all key input from the keyboard. */
    public static void reset() {
        AllKeys.clear();

        FastJEngine.getGameLoop().removeClassAlias(KeyboardStateEvent.class);
        FastJEngine.getGameLoop().removeClassAlias(KeyboardTypedEvent.class);
    }

    /**
     * Checks if the specified key (at the specified key location) was recently pressed.
     * <p>
     * If the specified key was recently pressed, it will no longer be recently pressed when this method concludes.
     *
     * @param keyCode     Integer value to look for a specific key. The best way to look for a key is to use the
     *                    KeyEvent class.
     * @param keyLocation KeyType value that determines where this key is on the keyboard. This value is based on the
     *                    KeyEvent location values, using {@code STANDARD}, {@code LEFT}, {@code RIGHT}, and
     *                    {@code NUMPAD} to define the different possible locations for a key on the keyboard.
     * @return Boolean value that determines if the specified key was recently pressed.
     */
    public static boolean isKeyRecentlyPressed(int keyCode, int keyLocation) {
        KeyDescription keyDescription = KeyDescription.get(keyCode, keyLocation);
        if (keyDescription == null) {
            return false;
        }
        Key k = AllKeys.get(keyDescription);

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
     *                    KeyEvent location values, using {@code STANDARD}, {@code LEFT}, {@code RIGHT}, and
     *                    {@code NUMPAD} to define the different possible locations for a key on the keyboard.
     * @return Boolean value that determines if the specified key was recently released.
     */
    public static boolean isKeyRecentlyReleased(int keyCode, int keyLocation) {
        KeyDescription keyDescription = KeyDescription.get(keyCode, keyLocation);
        if (keyDescription == null) {
            return false;
        }
        Key key = AllKeys.get(keyDescription);

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
     *                    KeyEvent location values, using {@code STANDARD}, {@code LEFT}, {@code RIGHT}, and
     *                    {@code NUMPAD} to define the different possible locations for a key on the keyboard.
     * @return Boolean value that determines if the specified key is pressed.
     */
    public static boolean isKeyDown(int keyCode, int keyLocation) {
        KeyDescription keyDescription = KeyDescription.get(keyCode, keyLocation);
        if (keyDescription == null) {
            return false;
        }

        return AllKeys.get(keyDescription).isKeyDown;
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
        for (Key key : AllKeys.values()) {
            if (key.isKeyDown) {
                return true;
            }
        }
        return false;
    }

    public static Set<Keys> getKeysDown() {
        return Collections.unmodifiableSet(AllKeysDown);
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
        if (FastJEngine.isLogging(LogLevel.Trace)) {
            Log.trace(Keyboard.class, "Key {} was pressed in event {}", KeyEvent.getKeyText(e.getExtendedKeyCode()), e);
        }

        KeyboardActionEvent keyboardActionEvent = KeyboardStateEvent.fromKeyEvent(e);
        KeyEventProcessor.get(e.getID()).accept(e);
        FastJEngine.getGameLoop().fireEvent(keyboardActionEvent, FastJEngine.ProcessKeysDown);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (FastJEngine.isLogging(LogLevel.Trace)) {
            Log.trace(Keyboard.class, "Key {} was released in event {}", KeyEvent.getKeyText(e.getExtendedKeyCode()), e);
        }

        KeyboardActionEvent keyboardActionEvent = KeyboardStateEvent.fromKeyEvent(e);
        KeyEventProcessor.get(e.getID()).accept(e);
        FastJEngine.getGameLoop().fireEvent(keyboardActionEvent, FastJEngine.ProcessKeysDown);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (FastJEngine.isLogging(LogLevel.Trace)) {
            Log.trace(Keyboard.class, "Key {} was typed in event {}", String.valueOf(e.getKeyChar()), e);
        }

        KeyboardActionEvent keyboardActionEvent = KeyboardTypedEvent.fromKeyEvent(e);
        KeyEventProcessor.get(e.getID()).accept(e);
        FastJEngine.getGameLoop().fireEvent(keyboardActionEvent, FastJEngine.ProcessKeysDown);
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
            } else {
                AllKeysDown.add(keyDescription.enumKey);
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
            } else {
                AllKeysDown.remove(keyDescription.enumKey);
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
    private static class KeyDescription implements Comparable<KeyDescription> {
        private final int keyCode;
        private final int keyLocation;
        private final Keys enumKey;

        /**
         * Constructs a key description using the specified key event.
         *
         * @param keyEvent The key event which this key description will be derived from.
         */
        private KeyDescription(KeyEvent keyEvent) {
            keyCode = keyEvent.getKeyCode();
            keyLocation = keyEvent.getKeyLocation();
            enumKey = Keys.get(keyEvent);
        }

        /**
         * Gets the {@code KeyDescription} with the specified keycode and key location.
         *
         * @param keyCode     The key code to check for.
         * @param keyLocation The key description to check for.
         * @return The {@code KeyDescription} with the specified key code. If none matches, this returns {@code null}.
         */
        private static KeyDescription get(int keyCode, int keyLocation) {
            for (KeyDescription keyDescription : AllKeys.keySet()) {
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

        @Override
        public int compareTo(KeyDescription other) {
            int keyCodeComparison = Integer.compare(keyCode, other.keyCode);
            if (keyCodeComparison != 0) {
                return keyCodeComparison;
            }

            return Integer.compare(keyLocation, other.keyLocation);
        }
    }
}
