package tech.fastj.input.mouse;

import tech.fastj.engine.FastJEngine;
import tech.fastj.graphics.Drawable;
import tech.fastj.graphics.display.Display;
import tech.fastj.input.mouse.events.MouseActionEvent;
import tech.fastj.input.mouse.events.MouseButtonEvent;
import tech.fastj.input.mouse.events.MouseMotionEvent;
import tech.fastj.input.mouse.events.MouseScrollEvent;
import tech.fastj.input.mouse.events.MouseWindowEvent;
import tech.fastj.logging.Log;
import tech.fastj.logging.LogLevel;
import tech.fastj.math.Pointf;
import tech.fastj.systems.execution.FastJScheduledThreadPool;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Mouse class that takes mouse input from the {@code Display}, and uses it to store variables about the mouse's current
 * state.
 *
 * @author Andrew Dey
 * @since 1.0.0
 */
public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {

    private static final Map<Integer, MouseButton> MouseButtons = new HashMap<>();

    private static final int InitialMouseButton = -1;
    private static final int InitialWheelRotation = 0;
    private static final int InitialClickCount = 0;

    private static ScheduledExecutorService mouseExecutor = new FastJScheduledThreadPool(Runtime.getRuntime().availableProcessors());

    private static int buttonLastPressed = Mouse.InitialMouseButton;
    private static int buttonLastReleased = Mouse.InitialMouseButton;
    private static int buttonLastClicked = Mouse.InitialMouseButton;
    private static int lastClickCount = Mouse.InitialClickCount;
    private static double lastWheelRotation = Mouse.InitialWheelRotation;
    private static double lastScrollAmount = Mouse.InitialWheelRotation;

    private static boolean currentlyOnScreen;
    private static Pointf mouseLocation = Pointf.origin();

    private static final Map<Integer, Consumer<MouseEvent>> MouseEventProcessor = Map.of(
            MouseEvent.MOUSE_PRESSED, mouseEvent -> {
                if (!MouseAction.Press.recentAction) {
                    createSleeperThread(MouseAction.Press);
                }

                if (!MouseButtons.containsKey(mouseEvent.getButton())) {
                    MouseButton btn = new MouseButton(mouseEvent);
                    MouseButtons.put(btn.buttonLocation, btn);
                }

                buttonLastPressed = mouseEvent.getButton();
                MouseButtons.get(mouseEvent.getButton()).currentlyPressed = true;
            },
            MouseEvent.MOUSE_RELEASED, mouseEvent -> {
                if (!MouseAction.Release.recentAction) {
                    createSleeperThread(MouseAction.Release);
                }

                if (MouseButtons.containsKey(mouseEvent.getButton())) {
                    MouseButtons.get(mouseEvent.getButton()).currentlyPressed = false;
                }

                buttonLastReleased = mouseEvent.getButton();
                lastClickCount = mouseEvent.getClickCount();
            },
            MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                if (!MouseAction.Click.recentAction) {
                    createSleeperThread(MouseAction.Click);
                }

                buttonLastClicked = mouseEvent.getButton();
            },
            MouseEvent.MOUSE_MOVED, mouseEvent -> {
                if (!MouseAction.Move.recentAction) {
                    createSleeperThread(MouseAction.Move);
                }

                mouseLocation = Pointf.divide(
                        new Pointf(mouseEvent.getX(), mouseEvent.getY()),
                        FastJEngine.getCanvas().getResolutionScale()
                );
            },
            MouseEvent.MOUSE_DRAGGED, mouseEvent -> {
                if (!MouseAction.Drag.recentAction) {
                    createSleeperThread(MouseAction.Drag);
                }

                mouseLocation = Pointf.divide(
                        new Pointf(mouseEvent.getX(), mouseEvent.getY()),
                        FastJEngine.getCanvas().getResolutionScale()
                );
            },
            MouseEvent.MOUSE_ENTERED, mouseEvent -> {
                if (MouseAction.Enter.recentAction) {
                    createSleeperThread(MouseAction.Enter);
                }

                currentlyOnScreen = true;
            },
            MouseEvent.MOUSE_EXITED, mouseEvent -> {
                if (MouseAction.Enter.recentAction) {
                    createSleeperThread(MouseAction.Exit);
                }

                currentlyOnScreen = false;
            },
            MouseEvent.MOUSE_WHEEL, mouseEvent -> {
                if (!MouseAction.WheelScroll.recentAction) {
                    createSleeperThread(MouseAction.WheelScroll);
                }

                MouseWheelEvent mouseWheelEvent = (MouseWheelEvent) mouseEvent;
                lastWheelRotation = mouseWheelEvent.getPreciseWheelRotation();
                switch (mouseWheelEvent.getScrollType()) {
                    case MouseWheelEvent.WHEEL_BLOCK_SCROLL: {
                        lastScrollAmount = mouseWheelEvent.getPreciseWheelRotation();
                        break;
                    }
                    case MouseWheelEvent.WHEEL_UNIT_SCROLL: {
                        lastScrollAmount = mouseWheelEvent.getUnitsToScroll();
                        break;
                    }
                    default: {
                        throw new IllegalStateException("Invalid mouse scroll type: " + mouseWheelEvent.getScrollType());
                    }
                }
            }
    );

    private static final Map<Integer, Function<MouseEvent, MouseActionEvent>> MouseActionEventCreator = Map.of(
            MouseEvent.MOUSE_PRESSED, mouseEvent -> MouseButtonEvent.fromMouseEvent(mouseEvent, MouseAction.Press),
            MouseEvent.MOUSE_RELEASED, mouseEvent -> MouseButtonEvent.fromMouseEvent(mouseEvent, MouseAction.Release),
            MouseEvent.MOUSE_CLICKED, mouseEvent -> MouseButtonEvent.fromMouseEvent(mouseEvent, MouseAction.Click),
            MouseEvent.MOUSE_MOVED, mouseEvent -> MouseMotionEvent.fromMouseEvent(mouseEvent, MouseAction.Move),
            MouseEvent.MOUSE_DRAGGED, mouseEvent -> MouseMotionEvent.fromMouseEvent(mouseEvent, MouseAction.Drag),
            MouseEvent.MOUSE_ENTERED, mouseEvent -> MouseWindowEvent.fromMouseEvent(mouseEvent, MouseAction.Enter),
            MouseEvent.MOUSE_EXITED, mouseEvent -> MouseWindowEvent.fromMouseEvent(mouseEvent, MouseAction.Exit),
            MouseEvent.MOUSE_WHEEL, mouseEvent -> MouseScrollEvent.fromMouseWheelEvent((MouseWheelEvent) mouseEvent, MouseAction.WheelScroll)
    );

    /** Initializes the mouse. */
    public static void init() {
        if (FastJEngine.isLogging(LogLevel.Debug)) {
            Log.debug(Mouse.class, "Initializing {}", Mouse.class.getName());
        }

        mouseExecutor = new FastJScheduledThreadPool(Runtime.getRuntime().availableProcessors());

        FastJEngine.getGameLoop().addClassAlias(MouseWindowEvent.class, MouseActionEvent.class);
        FastJEngine.getGameLoop().addClassAlias(MouseScrollEvent.class, MouseActionEvent.class);
        FastJEngine.getGameLoop().addClassAlias(MouseMotionEvent.class, MouseActionEvent.class);
        FastJEngine.getGameLoop().addClassAlias(MouseButtonEvent.class, MouseActionEvent.class);

        if (FastJEngine.isLogging(LogLevel.Debug)) {
            Log.debug(Mouse.class, "Mouse initialization complete.");
        }
    }

    /**
     * Determines whether the specified {@code Drawable} intersects the mouse, if the mouse is currently performing the
     * specified {@code MouseAction}.
     *
     * @param button            The {@code Drawable} to be checked if the mouse is currently interacting with.
     * @param recentMouseAction The {@code MouseAction} that the mouse has to be currently doing, in order to return
     *                          true.
     * @return Returns whether the mouse intersects the specified {@code Drawable}, and is currently performing the
     * specified {@code MouseAction}.
     */
    public static boolean interactsWith(Drawable button, MouseAction recentMouseAction) {
        PathIterator buttonPathIterator = button.getCollisionPath().getPathIterator(null);
        return Path2D.intersects(buttonPathIterator, mouseLocation.x, mouseLocation.y, 1, 1) && recentMouseAction.recentAction;
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
     * You can get button values from the {@code MouseEvent} class, or from predefined values in the
     * {@link MouseButtons} class.
     *
     * @param mouseButton The {@link MouseButtons} enum value defining which button to check for.
     * @return The boolean value that represents whether the specified button is pressed.
     */
    public static boolean isMouseButtonPressed(MouseButtons mouseButton) {
        return isMouseButtonPressed(mouseButton.buttonValue);
    }

    /**
     * Gets the value that determines whether the specified mouse button is currently pressed.
     * <p>
     * You can get button values from the {@link MouseButtons} enum, the {@code MouseEvent} class, or define your own --
     * the button numbers are just integers corresponding to buttons on the mouse.
     *
     * @param buttonNumber The int value defining which button to check for.
     * @return The boolean value that represents whether the specified button is pressed.
     */
    public static boolean isMouseButtonPressed(int buttonNumber) {
        if (!MouseButtons.containsKey(buttonNumber)) {
            return false;
        }

        return MouseButtons.get(buttonNumber).currentlyPressed;
    }

    /**
     * Gets the location of the mouse on the {@link Display}.
     *
     * @return The {@code Pointf} that represents the location of the mouse on the {@code Display}.
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
     * Gets the amount of times the last button (which can be gotten using {@link #getButtonLastClicked()}) was clicked
     * consecutively.
     *
     * @return Returns the number of times the last button was clicked consecutively.
     */
    public static int getLastClickCount() {
        return lastClickCount;
    }

    /**
     * Gets the last mouse wheel rotation.
     *
     * @return Returns the precise double value of the direction of the last mouse scroll.
     */
    public static double getLastWheelRotation() {
        return lastWheelRotation;
    }

    /**
     * Gets the last scroll amount, which may be affected by the {@link MouseScrollType type of scrolling performed}.
     *
     * @return The precise double value of the last scroll amount.
     * @see MouseWheelEvent#WHEEL_BLOCK_SCROLL
     * @see MouseWheelEvent#WHEEL_UNIT_SCROLL
     */
    public static double getLastScrollAmount() {
        return lastScrollAmount;
    }

    /**
     * Forces a quick end to the specified mouse action.
     *
     * @param e MouseAction enum that determine the type of mouse action that should be force ended.
     */
    public static void endProcess(MouseAction e) {
        e.recentAction = false;
    }

    /**
     * Creates an executor that makes a {@code MouseAction} true for 50 milliseconds, and then false.
     *
     * @param e The {@code MouseAction} to be used in the executor.
     */
    private static void createSleeperThread(MouseAction e) {
        e.recentAction = true;
        mouseExecutor.schedule(() -> e.recentAction = false, 50, TimeUnit.MILLISECONDS);
    }

    /** Resets the {@code Mouse}. */
    public static void reset() {
        buttonLastPressed = InitialMouseButton;
        buttonLastReleased = InitialMouseButton;
        buttonLastClicked = InitialMouseButton;
        lastClickCount = InitialClickCount;
        lastWheelRotation = InitialWheelRotation;
        currentlyOnScreen = false;

        MouseButtons.clear();
        mouseLocation.reset();

        FastJEngine.getGameLoop().removeClassAlias(MouseWindowEvent.class);
        FastJEngine.getGameLoop().removeClassAlias(MouseScrollEvent.class);
        FastJEngine.getGameLoop().removeClassAlias(MouseMotionEvent.class);
        FastJEngine.getGameLoop().removeClassAlias(MouseButtonEvent.class);
    }

    public static void stop() {
        reset();
        mouseExecutor.shutdownNow();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (FastJEngine.isLogging(LogLevel.Trace)) {
            Log.trace(Mouse.class, "Mouse button {} was pressed at screen location {} in event {}", e.getButton(), e.getLocationOnScreen(), e);
        }

        MouseActionEvent mouseActionEvent = MouseActionEventCreator.get(MouseEvent.MOUSE_PRESSED).apply(e);
        MouseEventProcessor.get(e.getID()).accept(e);
        FastJEngine.getGameLoop().fireEvent(mouseActionEvent, FastJEngine.ProcessKeysDown);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (FastJEngine.isLogging(LogLevel.Trace)) {
            Log.trace(Mouse.class, "Mouse button {} was released at screen location {} in event {}", e.getButton(), e.getLocationOnScreen(), e);
        }

        MouseActionEvent mouseActionEvent = MouseActionEventCreator.get(MouseEvent.MOUSE_RELEASED).apply(e);
        MouseEventProcessor.get(e.getID()).accept(e);
        FastJEngine.getGameLoop().fireEvent(mouseActionEvent, FastJEngine.ProcessKeysDown);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (FastJEngine.isLogging(LogLevel.Trace)) {
            Log.trace(Mouse.class, "Mouse button {} was clicked at screen location {} in event {}", e.getButton(), e.getLocationOnScreen(), e);
        }

        MouseActionEvent mouseActionEvent = MouseActionEventCreator.get(MouseEvent.MOUSE_CLICKED).apply(e);
        MouseEventProcessor.get(e.getID()).accept(e);
        FastJEngine.getGameLoop().fireEvent(mouseActionEvent, FastJEngine.ProcessKeysDown);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (FastJEngine.isLogging(LogLevel.Trace)) {
            Log.trace(Mouse.class, "Mouse was moved at screen location {} in event {}", e.getLocationOnScreen(), e);
        }

        MouseActionEvent mouseActionEvent = MouseActionEventCreator.get(MouseEvent.MOUSE_MOVED).apply(e);
        MouseEventProcessor.get(e.getID()).accept(e);
        FastJEngine.getGameLoop().fireEvent(mouseActionEvent, FastJEngine.ProcessKeysDown);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (FastJEngine.isLogging(LogLevel.Trace)) {
            Log.trace(Mouse.class, "Mouse was dragged at screen location {} in event {}", e.getLocationOnScreen(), e);
        }

        MouseActionEvent mouseActionEvent = MouseActionEventCreator.get(MouseEvent.MOUSE_DRAGGED).apply(e);
        MouseEventProcessor.get(e.getID()).accept(e);
        FastJEngine.getGameLoop().fireEvent(mouseActionEvent, FastJEngine.ProcessKeysDown);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (FastJEngine.isLogging(LogLevel.Trace)) {
            Log.trace(Mouse.class, "Mouse wheel was scrolled in direction {} at screen location {} in event {}", e.getWheelRotation(), e.getLocationOnScreen(), e);
        }

        MouseActionEvent mouseActionEvent = MouseActionEventCreator.get(MouseEvent.MOUSE_WHEEL).apply(e);
        MouseEventProcessor.get(e.getID()).accept(e);
        FastJEngine.getGameLoop().fireEvent(mouseActionEvent, FastJEngine.ProcessKeysDown);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (FastJEngine.isLogging(LogLevel.Trace)) {
            Log.trace(Mouse.class, "Mouse entered window at screen location {} in event {}", e.getLocationOnScreen(), e);
        }

        MouseActionEvent mouseActionEvent = MouseActionEventCreator.get(MouseEvent.MOUSE_ENTERED).apply(e);
        MouseEventProcessor.get(e.getID()).accept(e);
        FastJEngine.getGameLoop().fireEvent(mouseActionEvent, FastJEngine.ProcessKeysDown);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (FastJEngine.isLogging(LogLevel.Trace)) {
            Log.trace(Mouse.class, "Mouse exited window at screen location {} in event {}", e.getLocationOnScreen(), e);
        }

        MouseActionEvent mouseActionEvent = MouseActionEventCreator.get(MouseEvent.MOUSE_EXITED).apply(e);
        MouseEventProcessor.get(e.getID()).accept(e);
        FastJEngine.getGameLoop().fireEvent(mouseActionEvent, FastJEngine.ProcessKeysDown);
    }

    /** Private class to store the value of a mouse button, and whether it is currently pressed. */
    private static class MouseButton {
        private final int buttonLocation;
        private boolean currentlyPressed;

        /**
         * Constructs a {@code MouseButton} using the specified {@code MouseEvent}.
         *
         * @param event The event which the {@code MouseButton} will be derived from.
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
