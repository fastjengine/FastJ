package tech.fastj.input.mouse;

import tech.fastj.engine.FastJEngine;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.Drawable;
import tech.fastj.graphics.display.Display;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import tech.fastj.input.InputManager;

/**
 * Mouse class that takes mouse input from the {@code Display}, and uses it to store variables about the mouse's current
 * state.
 *
 * @author Andrew Dey
 * @version 1.0.0
 */
public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {

    private static final ScheduledExecutorService MouseExecutor = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    private static final Map<Integer, MouseButton> MouseButtons = new HashMap<>();

    private static final int InitialMouseButton = -1;
    private static final int InitialScrollDirection = 0;

    private static int buttonLastPressed = Mouse.InitialMouseButton;
    private static int buttonLastReleased = Mouse.InitialMouseButton;
    private static int buttonLastClicked = Mouse.InitialMouseButton;
    private static int lastScrollDirection = Mouse.InitialScrollDirection;

    private static boolean currentlyOnScreen;
    private static Pointf mouseLocation = new Pointf();

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
                        FastJEngine.getDisplay().getResolutionScale()
                );
            },
            MouseEvent.MOUSE_DRAGGED, mouseEvent -> {
                if (!MouseAction.Drag.recentAction) {
                    createSleeperThread(MouseAction.Drag);
                }

                mouseLocation = Pointf.divide(
                        new Pointf(mouseEvent.getX(), mouseEvent.getY()),
                        FastJEngine.getDisplay().getResolutionScale()
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
                lastScrollDirection = mouseWheelEvent.getWheelRotation();
            }
    );

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
        boolean result = Path2D.Float.intersects(buttonPathIterator, mouseLocation.x, mouseLocation.y, 1, 1) && recentMouseAction.recentAction;

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
     * You can get button values from the {@code MouseEvent} class, or from predefined values in the {@link
     * MouseButtons} class.
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
        MouseExecutor.schedule(() -> e.recentAction = false, 50, TimeUnit.MILLISECONDS);
    }

    /** Resets the {@code Mouse}. */
    public static void reset() {
        buttonLastPressed = -1;
        buttonLastReleased = -1;
        buttonLastClicked = -1;
        lastScrollDirection = 0;
        currentlyOnScreen = false;

        MouseButtons.clear();
        mouseLocation.reset();
    }

    public static void stop() {
        reset();
        MouseExecutor.shutdownNow();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        FastJEngine.getLogicManager().receivedInputEvent(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        FastJEngine.getLogicManager().receivedInputEvent(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        FastJEngine.getLogicManager().receivedInputEvent(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        FastJEngine.getLogicManager().receivedInputEvent(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        FastJEngine.getLogicManager().receivedInputEvent(e);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        FastJEngine.getLogicManager().receivedInputEvent(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        FastJEngine.getLogicManager().receivedInputEvent(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        FastJEngine.getLogicManager().receivedInputEvent(e);
    }

    /**
     * Processes the specified mouse event for the specified input manager, based on its event type.
     *
     * @param inputManager The input manager to fire the event to.
     * @param event        The mouse event to process.
     */
    public static void processEvent(InputManager inputManager, MouseEvent event) {
        MouseEventProcessor.get(event.getID()).accept(event);
        inputManager.fireMouseEvent(event);
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
