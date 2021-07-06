package tech.fastj.example.model2d;

import tech.fastj.engine.FastJEngine;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.Display;
import tech.fastj.graphics.game.Model2D;
import tech.fastj.graphics.game.Polygon2D;
import tech.fastj.graphics.util.DrawUtil;

import tech.fastj.systems.control.SimpleManager;

public class Main extends SimpleManager {

    @Override
    public void init(Display display) {
        /* Model2D */

        /* WARNING: If you're not familiar with FastJ, you'll need to read through the
         * "polygon2d" example first. */


        /* In order to create a Model2D, you'll need an array of Polygon2D objects.
         * A Model2D is essentially a collection of Polygon2D objects, and it treats those objects
         * as one game object. */

        // We'll create two squares -- just like the ones from the "hellopolygon2d" example.
        Pointf[] smallSquareMesh1 = DrawUtil.createBox(0f, 0f, 50f);
        Polygon2D smallSquare1 = Polygon2D.fromPoints(smallSquareMesh1);

        // To visualize the two squares as different, we'll change the second one up a bit.
        Pointf[] smallSquareMesh2 = DrawUtil.createBox(50f, 50f, 25f);
        Polygon2D smallSquare2 = Polygon2D.fromPoints(smallSquareMesh2);

        // In order to create our Model2D, we need those squares in an array.
        Polygon2D[] smallSquaresArray = {smallSquare1, smallSquare2};

        // Now, we can create our Model2D object.
        // This can be done using Model2D.fromPolygons(polygons).
        Model2D smallSquares = Model2D.fromPolygons(smallSquaresArray);

        /* Super simple! Now, this alone does not cause the model to render to the screen. In order
         * for it to be rendered, you need to add it as a game object to the drawable manager. */
        drawableManager.addGameObject(smallSquares);

        /* If you comment out the line above, you'll see that the text does not get rendered. */


        /* You can set the following properties of a Model2D:
         * - Polygons (Polygon2D array)
         * - Transformation (translation, rotation, scale)
         * - ShouldRender (whether the Polygon2D should be rendered to the screen)
         *
         * To show this off, we'll create a Model2D object with the following attributes:
         * - Polygon array consisting of 2 squares next to each other
         * - Translated by (20, 10)
         * - Rotation by 30 degrees
         * - Scaled down to 50% (0.5) */
        Polygon2D[] squares = {
                Polygon2D.fromPoints(DrawUtil.createBox(10, 10, 100)),
                Polygon2D.fromPoints(DrawUtil.createBox(110, 110, 100))
        };

        Pointf largeSquareTranslation = new Pointf(20f, 10f);
        float largeSquareRotation = 30f;
        Pointf largeSquareScale = new Pointf(0.5f, 0.5f);

        Model2D squaresTogether = Model2D.create(squares)
                .withTransform(largeSquareTranslation, largeSquareRotation, largeSquareScale)
                .build();

        // And of course, we need to add our model of squares to the drawable manager's game objects.
        drawableManager.addGameObject(squaresTogether);
    }

    @Override
    public void update(Display display) {
        // Empty -- this example does not make use of this method.
    }

    public static void main(String[] args) {
        FastJEngine.init("Hello, Model2D!", new Main());
        FastJEngine.run();
    }
}
