package tech.fastj.example.polygon2d;

import tech.fastj.engine.FastJEngine;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.Display;
import tech.fastj.graphics.util.DrawUtil;
import tech.fastj.graphics.game.Polygon2D;

import tech.fastj.systems.control.SimpleManager;

import java.awt.Color;

public class Main extends SimpleManager {

    @Override
    public void init(Display display) {
        /* Polygon2D */

        /* In order to create a Polygon2D, you first need to create a mesh.
         * A mesh is essentially an array of "Pointf"s (vectors) that form a shape when drawn in
         * order. This mesh is the base upon which the Polygon2D can be constructed, and defines
         * what sort of shape gets drawn to the screen. */

        // You can create a mesh by hand...
        Pointf[] smallSquareMeshByHand = {
                new Pointf(0f, 0f),
                new Pointf(0f, 50f),
                new Pointf(50f, 50f),
                new Pointf(50f, 0f)
        };

        // ...or by using one of the many "DrawUtil.create" methods.
        Pointf[] smallSquareMeshFromDrawUtil = DrawUtil.createBox(0f, 0f, 50f);

        /* The two meshes above result in the exact same thing: a square at the point (0, 0) with a
         * size of 50. */


        /* Now, we can create our Polygon2D. We'll use smallSquareMeshByHand for right now, but you
         * should try switching from it to smallSquareMeshFromDrawUtil to see what happens! */
        Polygon2D smallSquare = new Polygon2D(smallSquareMeshByHand);

        /* Super simple! Now, this alone does not cause the square to render to the screen. In
         * order for it to be rendered, you need to add it as a game object to the drawable
         * manager. */
        drawableManager.addGameObject(smallSquare);

        /* If you comment out the line above, you'll see that the small square does not get
         * rendered. */


        /* You can set the following properties of a Polygon2D:
         * - Mesh (Pointf[])
         * - Color
         * - PaintFilled (render the outline or fill)
         * - Rotation
         * - Scale
         * - Translation
         *
         * To show this off, we'll create a larger square with the following property values:
         * - Square mesh at (625, 25) with a size of 47.9
         * - Blue color
         * - Outline
         * - Rotation of 30 degrees
         * - Scaled to 50% (0.5)
         * - Translated by (20, 10) */
        Pointf[] largeSquareMesh = DrawUtil.createBox(625f, 25.5f, 47.9f);
        Polygon2D largeSquare = new Polygon2D(largeSquareMesh)
                .setPaint(Color.blue)
                .setFilled(false);

        /* As you can see from the code above, many of the methods Polygon2D contains allow for
         * method chaining.
         * From the code below, you can see that other methods do not. */

        largeSquare.rotate(30f);
        largeSquare.scale(new Pointf(0.5f, 0.5f));
        largeSquare.translate(new Pointf(20f, 10f));

        drawableManager.addGameObject(largeSquare);
    }

    @Override
    public void update(Display display) {
        // Empty -- this example does not make use of this method.
    }

    public static void main(String[] args) {
        FastJEngine.init("Hello, Polygon2D!", new Main());
        FastJEngine.run();
    }
}
