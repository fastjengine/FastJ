package tech.fastj.example.mouse;

import tech.fastj.engine.FastJEngine;

import tech.fastj.graphics.display.Display;
import tech.fastj.graphics.game.Polygon2D;
import tech.fastj.graphics.util.DrawUtil;

import tech.fastj.input.mouse.Mouse;
import tech.fastj.input.mouse.MouseAction;
import tech.fastj.input.mouse.MouseActionListener;
import tech.fastj.input.mouse.MouseButtons;

import tech.fastj.systems.control.SimpleManager;

import java.awt.Color;
import java.awt.event.MouseEvent;

public class Main extends SimpleManager {

  Polygon2D button;

  @Override
  public void init(Display display) {
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

    inputManager.addMouseActionListener(
        new MouseActionListener() {
          @Override
          public void onMousePressed(MouseEvent mouseEvent) {
            FastJEngine.log("Mouse button " + mouseEvent.getButton() + " pressed");
          }

          @Override
          public void onMouseReleased(MouseEvent mouseEvent) {
            FastJEngine.log("Mouse button " + mouseEvent.getButton() + " released");
          }

          @Override
          public void onMouseClicked(MouseEvent mouseEvent) {
            FastJEngine.log("Mouse button " + mouseEvent.getButton() + " clicked");
          }

          @Override
          public void onMouseMoved(MouseEvent mouseEvent) {
            FastJEngine.log("Mouse moved to " + Mouse.getMouseLocation());
          }

          @Override
          public void onMouseDragged(MouseEvent mouseEvent) {
            FastJEngine.log("Mouse dragged to " + Mouse.getMouseLocation());
          }

          @Override
          public void onMouseWheelScrolled(MouseEvent mouseEvent) {
            FastJEngine.log(
                "Mouse wheel scroll: "
                    + Mouse.getScrollDirection()
                    + " at "
                    + Mouse.getMouseLocation());
          }

          @Override
          public void onMouseEntersScreen(MouseEvent mouseEvent) {
            FastJEngine.log("Mouse entered game window at " + Mouse.getMouseLocation());
          }

          @Override
          public void onMouseExitsScreen(MouseEvent mouseEvent) {
            FastJEngine.log("Mouse left game window at " + Mouse.getMouseLocation());
          }
        });

    button =
        Polygon2D.create(DrawUtil.createBox(300f, 300f, 100f, 40f))
            .withFill(Color.white)
            .withOutline(Polygon2D.DefaultOutlineStroke, Color.black)
            .build();
  }

  @Override
  public void update(Display display) {
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
      FastJEngine.log(
          "Mouse Button " + Mouse.getButtonLastReleased() + " was released on the button.");
    }
  }

  public static void main(String[] args) {
    FastJEngine.init("Hello, Mouse Controls!", new Main());
    FastJEngine.run();
  }
}
