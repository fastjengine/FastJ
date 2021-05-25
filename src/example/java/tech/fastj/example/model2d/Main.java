package tech.fastj.example.model2d;

import tech.fastj.engine.FastJEngine;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.Display;
import tech.fastj.graphics.DrawUtil;
import tech.fastj.graphics.game.Model2D;
import tech.fastj.graphics.game.Polygon2D;

import tech.fastj.systems.control.SimpleManager;

public class Main extends SimpleManager {
    @Override
    public void init(Display display) {
        /* Model2D */

        /* WARNING: If you're not familiar with FastJ, you'll need to read through the
         * "polygon2d" example first.
         *
         * In order to create a Model2D, you'll need an array of Polygon2D objects.
         * A Model2D is essentially a collection of Polygon2D objects, and it treats those objects
         * as one game object. */

        // We'll create two squares -- just like the ones from the "hellopolygon2d" example.
        Pointf[] smallSquareMesh1 = DrawUtil.createBox(0f, 0f, 50f);
        Polygon2D smallSquare1 = new Polygon2D(smallSquareMesh1);

        // To visualize the two squares as different, we'll change the second one up a bit.
        Pointf[] smallSquareMesh2 = DrawUtil.createBox(50f, 50f, 25f);
        Polygon2D smallSquare2 = new Polygon2D(smallSquareMesh2);

        // In order to create our Model2D, we need those squares in an array.
        Polygon2D[] smallSquaresArray = {smallSquare1, smallSquare2};

        // Now, we can create our Model2D object.
        Model2D smallSquares = new Model2D(smallSquaresArray);

        /* Super simple! Now, this alone does not cause the model to render to the screen. In order
         * for it to be rendered, you need to add it as a game object to the drawable manager. */
        drawableManager.addGameObject(smallSquares);
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
