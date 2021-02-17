package io.github.lucasstarsz.fastj.engine.io;

import io.github.lucasstarsz.fastj.engine.FastJEngine;
import io.github.lucasstarsz.fastj.engine.graphics.Drawable;
import io.github.lucasstarsz.fastj.engine.util.math.Pointf;

import java.awt.event.*;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Mouse class that takes mouse input from the {@code Display}, and uses it to store variables about the mouse's current
 * state.
 *
 * @author Andrew Dey
 * @version 0.3.2a
 * @see MouseListener
 * @see MouseMotionListener
 * @see MouseWheelListener
 */
public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {

    private static final ScheduledExecutorService mouseExecutor = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

    /** Enum that defines the different types of mouse actions available. */
    public enum MouseAction {
        PRESS,
        RELEASE,
        CLICK,
        MOVE,
        DRAG,
        WHEEL_SCROLL,
        ENTER,
        EXIT;

        private boolean recentAction;

        MouseAction() {
            recentAction = false;
        }
    }

    private static int buttonLastPressed = -1;
    private static int buttonLastReleased = -1;
    private static int buttonLastClicked = -1;
    private static int lastScrollDirection = 0;

    private static boolean currentlyOnScreen;

    private static final Map<Integer, MouseButton> mouseButtons = new HashMap<>();
    private static Pointf mouseLocation = new Pointf();


    /**
     * Determines whether the specified {@code Drawable} intersects the mouse, if the mouse is currently performing the
     * specified {@code MouseAction}.
     *
     * @param button            The {@code Drawable} to be checked if the mouse is currently interacting with.
     * @param recentMouseAction The {@code MouseAction} that the mouse has to be currently doing, in order to return
     *                          true.
     * @return Returns whether the mouse intersects the specified {@code Drawable}, and is currently performing the
     * specified {@code MouseAction}.
     * @see Drawable
     * @see io.github.lucasstarsz.fastj.engine.io.Mouse.MouseAction
     */
    public static boolean interactsWith(Drawable button, MouseAction recentMouseAction) {
        PathIterator btnPI = button.getCollisionPath().getPathIterator(null);
        boolean result = Path2D.Float.intersects(btnPI, mouseLocation.x, mouseLocation.y, 1, 1) && recentMouseAction.recentAction;

        recentMouseAction.recentAction = false;

        return result;
    }

    /**
     * Gets the value that determines whether the mouse is currently on the {@code Display} window.
     *
     * @return The boolean value that represents whether the mouse is currently on the {@code Display} window.
     */
    public static boolean isOnScreen() {
        return currentlyOnScreen;
    }

    /**
     * Gets the value that determines whether the specified mouse button is currently pressed.
     * <p>
     * You can get button values from the {@code MouseEvent} class.
     *
     * @param buttonNumber The int value that represents which button to check for.
     * @return The boolean value that represents whether the specified button is pressed.
     */
    public static boolean isMouseButtonPressed(int buttonNumber) {
        if (!mouseButtons.containsKey(buttonNumber)) {
            return false;
        }

        return mouseButtons.get(buttonNumber).currentlyPressed;
    }

    /**
     * Gets the location of the mouse on the {@code Display}.
     *
     * @return The {@code Pointf} that represents the location of the mouse on the {@code Display}.
     * @see Pointf
     */
    public static Pointf getMouseLocation() {
        return mouseLocation;
    }

    /**
     * Gets the value of the button that was last pressed.
     *
     * @return Returns the integer value of the last button pressed.
     */
    public static int getButtonLastPressed() {
        return buttonLastPressed;
    }

    /**
     * Gets the value of the button that was last released.
     *
     * @return Returns the integer value of the last button released.
     */
    public static int getButtonLastReleased() {
        return buttonLastReleased;
    }

    /**
     * Gets the value of the button that was last clicked.
     *
     * @return Returns the integer value of the last button clicked.
     */
    public static int getButtonLastClicked() {
        return buttonLastClicked;
    }

    /**
     * Gets the last mouse wheel scroll direction.
     *
     * @return Returns the integer value of the direction of the last mouse scroll.
     */
    public static int getScrollDirection() {
        return lastScrollDirection;
    }

    /**
     * Forces a quick end to the specified mouse action.
     *
     * @param e MouseAction enum that determine the type of mouse action that should be force ended.
     * @see io.github.lucasstarsz.fastj.engine.io.Mouse.MouseAction
     */
    public static void endProcess(MouseAction e) {
        e.recentAction = false;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!MouseAction.PRESS.recentAction) createSleeperThread(MouseAction.PRESS);

        if (!mouseButtons.containsKey(e.getButton())) {
            MouseButton btn = new MouseButton(e);
            mouseButtons.put(btn.buttonLocation, btn);
        }
        buttonLastPressed = e.getButton();
        mouseButtons.get(e.getButton()).currentlyPressed = true;

        if (FastJEngine.getLogicManager() != null) {
            FastJEngine.getLogicManager().fireMouseAction(MouseAction.PRESS, e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!MouseAction.RELEASE.recentAction) createSleeperThread(MouseAction.RELEASE);

        if (mouseButtons.containsKey(e.getButton())) {
            mouseButtons.get(e.getButton()).currentlyPressed = false;
        }
        buttonLastReleased = e.getButton();

        if (FastJEngine.getLogicManager() != null) {
            FastJEngine.getLogicManager().fireMouseAction(MouseAction.RELEASE, e);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!MouseAction.CLICK.recentAction) createSleeperThread(MouseAction.CLICK);

        buttonLastClicked = e.getButton();

        if (FastJEngine.getLogicManager() != null) {
            FastJEngine.getLogicManager().fireMouseAction(MouseAction.CLICK, e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!MouseAction.MOVE.recentAction) createSleeperThread(MouseAction.MOVE);

        mouseLocation = Pointf.divide(new Pointf(e.getX(), e.getY()), FastJEngine.getDisplay().getResolutionScale());

        if (FastJEngine.getLogicManager() != null) {
            FastJEngine.getLogicManager().fireMouseAction(MouseAction.MOVE, e);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!MouseAction.DRAG.recentAction) createSleeperThread(MouseAction.DRAG);

        mouseLocation = Pointf.divide(new Pointf(e.getX(), e.getY()), FastJEngine.getDisplay().getResolutionScale());

        if (FastJEngine.getLogicManager() != null) {
            FastJEngine.getLogicManager().fireMouseAction(MouseAction.DRAG, e);
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (!MouseAction.WHEEL_SCROLL.recentAction) createSleeperThread(MouseAction.WHEEL_SCROLL);

        lastScrollDirection = e.getWheelRotation();

        if (FastJEngine.getLogicManager() != null) {
            FastJEngine.getLogicManager().fireMouseWheelAction(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (MouseAction.ENTER.recentAction) createSleeperThread(MouseAction.ENTER);

        currentlyOnScreen = true;

        if (FastJEngine.getLogicManager() != null) {
            FastJEngine.getLogicManager().fireMouseAction(MouseAction.ENTER, e);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (MouseAction.EXIT.recentAction) createSleeperThread(MouseAction.EXIT);

        currentlyOnScreen = false;

        if (FastJEngine.getLogicManager() != null) {
            FastJEngine.getLogicManager().fireMouseAction(MouseAction.EXIT, e);
        }
    }

    /**
     * Creates an executor that makes a {@code MouseAction} true for 50 milliseconds, and then false.
     *
     * @param e The {@code MouseAction} to be used in the executor.
     * @see io.github.lucasstarsz.fastj.engine.io.Mouse.MouseAction
     */
    private static void createSleeperThread(MouseAction e) {
        e.recentAction = true;
        mouseExecutor.schedule(() -> e.recentAction = false, 50, TimeUnit.MILLISECONDS);
    }

    /** Resets the {@code Mouse}. */
    public static void reset() {
        buttonLastPressed = -1;
        buttonLastReleased = -1;
        buttonLastClicked = -1;
        lastScrollDirection = 0;
        currentlyOnScreen = false;

        mouseButtons.clear();
        mouseLocation.reset();
    }

    /** Private class to store the value of a mouse button, and whether it is currently pressed. */
    private static class MouseButton {
        private final int buttonLocation;
        private boolean currentlyPressed;

        /**
         * Constructs a {@code MouseButton} using the specified {@code MouseEvent}.
         *
         * @param event The event which the {@code MouseButton} will be derived from.
         * @see MouseEvent
         */
        private MouseButton(MouseEvent event) {
            buttonLocation = event.getButton();
            currentlyPressed = true;
        }

        @Override
        public String toString() {
            return "MouseButton{" +
                    "buttonLocation=" + buttonLocation +
                    ", currentlyPressed=" + currentlyPressed +
                    '}';
        }
    }
}
