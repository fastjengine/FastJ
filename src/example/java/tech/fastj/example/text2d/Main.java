package tech.fastj.example.text2d;

import tech.fastj.engine.FastJEngine;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.display.Display;
import tech.fastj.graphics.game.Text2D;

import tech.fastj.systems.control.SimpleManager;

import java.awt.Color;
import java.awt.Font;

public class Main extends SimpleManager {

    @Override
    public void init(Display display) {
        /* Text2D */

        /* In order to create a Text2D, you just need to have a String containing text. */

        // The text is pretty simple -- just a String containing text.
        String text = "Hello, FastJ Text2D!";
        // Simple Text2D creation can be done with Text2D.fromText(text)
        Text2D message = Text2D.fromText(text);

        /* Super simple! Now, this alone does not cause the text to render to the screen. In order
         * for it to be rendered, you need to add it as a game object to the drawable manager. */
        drawableManager.addGameObject(message);

        /* If you comment out the line above, you'll see that the text does not get rendered. */


        /* You can set the following properties of a Text2D:
         * - Text (String)
         * - Fill paint (solid color or a gradient)
         * - Font
         * - Transformation (translation, rotation, scale)
         * - ShouldRender (whether the Polygon2D should be rendered to the screen)
         *
         * To show this off, we'll create a Text2D object with the following attributes:
         * - Text containing "Interesting Text2D ya got there..."
         * - Magenta color
         * - Times New Roman font, size 12 italic
         * - Translated by (20, 10)
         * - Rotation by 30 degrees
         * - Scaled down to 50% (0.5) */
        String interestingText = "Interesting Text2D ya got there...";
        Font interestingFont = new Font("Times New Roman", Font.ITALIC, 12);

        Pointf largeSquareTranslation = new Pointf(20f, 10f);
        float largeSquareRotation = 30f;
        Pointf largeSquareScale = new Pointf(0.5f, 0.5f);

        Text2D interestingText2D = Text2D.create(interestingText)
                .withFill(Color.magenta)
                .withFont(interestingFont)
                .withTransform(largeSquareTranslation, largeSquareRotation, largeSquareScale)
                .build();

        // And of course, we need to add our interesting text to the drawable manager's game objects.
        drawableManager.addGameObject(interestingText2D);
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
