package tech.fastj.example.text2d;

import tech.fastj.engine.FastJEngine;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.Display;
import tech.fastj.graphics.game.Text2D;

import tech.fastj.systems.control.SimpleManager;

public class Main extends SimpleManager {

    @Override
    public void init(Display display) {
        /* Text2D */

        /* In order to create a Text2D, you will need two things:
         * 1. A message, or some text to display.
         * 2. A location for the message to be rendered at. */

        // The message is pretty simple -- just a String containing text.
        String messageString = "Hello, FastJ Text2D!";

        /* The location is fairly simple as well -- just a Pointf containing the x and y location.
         *
         * For the sake of clarification, a Pointf is a 2D vector containing x and y variables.
         * In this case, "messageLocation" represents the point (0, 25) in the 2D coordinate space.
         *
         * Note: Text2D render differently from polygons. They are rendered such that the y
         * location represents the bottom of the text shown.
         * One good way to account for this is to add the size of the font to the y position. Since
         * we did not create a custom font for the Text2D, we can use the size of the default font,
         * "Text2D#DefaultFont#getSize()". */
        Pointf messageLocation = new Pointf(0f, 0f + Text2D.DefaultFont.getSize());


        // Now, we put it all together!
        Text2D message = new Text2D(messageString, messageLocation);

        /* Super simple! Now, this alone does not cause the text to render to the screen. In order
         * for it to be rendered, you need to add it as a game object to the drawable manager. */
        drawableManager.addGameObject(message);

        /* If you comment out the line above, you'll see that the text does not get rendered. */


        /*  */
    }

    @Override
    public void update(Display display) {
        // Empty -- this example does not make use of this method.
    }

    public static void main(String[] args) {
        FastJEngine.init("Hello, Text2D!", new Main());
        FastJEngine.run();
    }
}
