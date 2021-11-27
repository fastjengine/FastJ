package tech.fastj.examples.mouse;

import tech.fastj.engine.FastJEngine;
import tech.fastj.graphics.display.FastJCanvas;
import tech.fastj.graphics.game.Polygon2D;
import tech.fastj.graphics.util.DrawUtil;

import tech.fastj.input.mouse.Mouse;
import tech.fastj.input.mouse.MouseAction;
import tech.fastj.input.mouse.MouseActionListener;
import tech.fastj.input.mouse.events.MouseButtonEvent;
import tech.fastj.input.mouse.MouseButtons;
import tech.fastj.input.mouse.events.MouseMotionEvent;
import tech.fastj.input.mouse.events.MouseScrollEvent;
import tech.fastj.input.mouse.events.MouseWindowEvent;
import tech.fastj.systems.control.SimpleManager;

import java.awt.Color;

public class Main extends SimpleManager {

    Polygon2D button;

    @Override
    public void init(FastJCanvas canvas) {
        /* Mouse */

        /* Mouse controls in FastJ can be achieved through a few different methods.
         * - Creating a MouseActionListener to do actions when the mouse is moved, dragged, clicked, and other
         * interactions with the game window.
         * - Polling the Mouse class directly for mouse state
         *
         * For this example, we'll work with the MouseActionListener in the init method, and polling in the update
         * method. */

        /* MouseActionListener */

        /* The MouseActionListener class is designed to set up actions depending on mouse input from the player.
         * It has methods for detecting when a mouse button is pressed, released, or clicked, as well as if the mouse is
         * moved, dragged, wheel scrolled, or if it exits/enters the game window.
         *
         * To demonstrate each method, I've chosen to log whenever any of the methods is called. Run the program to see
         * this in action. */

        inputManager.addMouseActionListener(new MouseActionListener() {
            @Override
            public void onMousePressed(MouseButtonEvent mouseButtonEvent) {
                FastJEngine.log("Mouse button {} pressed", mouseButtonEvent.getMouseButton());
            }

            @Override
            public void onMouseReleased(MouseButtonEvent mouseButtonEvent) {
                FastJEngine.log("Mouse button {} released", mouseButtonEvent.getMouseButton());
            }

            @Override
            public void onMouseClicked(MouseButtonEvent mouseButtonEvent) {
                FastJEngine.log("Mouse button {} clicked in succession {} times",
                        mouseButtonEvent.getMouseButton(),
                        mouseButtonEvent.getClickCount()
                );
            }

            @Override
            public void onMouseMoved(MouseMotionEvent mouseMotionEvent) {
                FastJEngine.log("Mouse moved to {}", mouseMotionEvent.getMouseLocation());
            }

            @Override
            public void onMouseDragged(MouseMotionEvent mouseMotionEvent) {
                FastJEngine.log("Mouse dragged {}", mouseMotionEvent.getMouseLocation());
            }

            @Override
            public void onMouseWheelScrolled(MouseScrollEvent mouseScrollEvent) {
                FastJEngine.log(
                        "Mouse wheel scrolled in {}s by {}",
                        mouseScrollEvent.getMouseScrollType().name().toLowerCase(),
                        mouseScrollEvent.getScrollAmount()
                );
            }

            @Override
            public void onMouseEntersScreen(MouseWindowEvent mouseWindowEvent) {
                FastJEngine.log(
                        "Mouse entered game window at {}",
                        mouseWindowEvent.getWindowInteractionPosition()
                );
            }

            @Override
            public void onMouseExitsScreen(MouseWindowEvent mouseWindowEvent) {
                FastJEngine.log(
                        "Mouse left game window at {}",
                        mouseWindowEvent.getWindowInteractionPosition()
                );
            }
        });

        button = Polygon2D.create(DrawUtil.createBox(300f, 300f, 100f, 40f))
                .withFill(Color.white)
                .withOutline(Polygon2D.DefaultOutlineStroke, Color.black)
                .build();
    }

    @Override
    public void update(FastJCanvas canvas) {
        /* Polling the Mouse for mouse state */

        /* The Mouse class has a plethora of methods for checking the mouse's state.
         *
         * - Mouse#interactsWith: check whether the mouse is intersecting with a specified Drawable in a certain way.
         * - Mouse#isOnScreen: check whether the mouse is within the game window.
         * - Mouse#isMouseButtonPressed: check whether the specified mouse button is held down.
         * - Mouse#getMouseLocation: gets the mouse's coordinate location on the screen.
         * - Mouse#getButtonLastPressed/Released/Clicked: gets the mouse button that was last pressed/released/clicked.
         * - Mouse#getScrollDirection: gets the direction of the mouse wheel's last scroll (up or down).
         *
         * To demonstrate these, I've added the following:
         * - If statements to check if the right mouse button is pressed or released
         * - If statement to check if the mouse wheel was scrolled upward (value of -1)
         * - If statement to check if the mouse was released while on a Polygon2D object
         *
         * Notes:
         * - The MouseAction class defines several types of mouse actions (pressed, released, moved, etc).
         * - The MouseButtons class defines a few buttons that are very commonly found on mice -- the left, right, and
         * middle mouse buttons. */

        if (Mouse.isMouseButtonPressed(MouseButtons.Right)) {
            FastJEngine.log("Right Mouse Button is pressed");
        } else {
            FastJEngine.log("Right Mouse button is released");
        }

        if (Mouse.getScrollDirection() == -1) {
            FastJEngine.log("Mouse Wheel was scrolled upwards");
        }

        if (Mouse.interactsWith(button, MouseAction.Release)) {
            FastJEngine.log("Mouse Button " + Mouse.getButtonLastReleased() + " was released on the button.");
        }
    }

    public static void main(String[] args) {
        FastJEngine.init("Hello, Mouse Controls!", new Main());
        FastJEngine.run();
    }
}
