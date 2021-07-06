package tech.fastj.example.polygon2d;

import tech.fastj.engine.FastJEngine;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.Display;
import tech.fastj.graphics.RenderStyle;
import tech.fastj.graphics.game.Polygon2D;
import tech.fastj.graphics.util.DrawUtil;

import tech.fastj.systems.control.SimpleManager;

import java.awt.BasicStroke;
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
         * should try switching from it to smallSquareMeshFromDrawUtil to see what happens!
         *
         * To create a simple Polygon2D with just a mesh, use Polygon2D.fromPoints(mesh). */
        Polygon2D smallSquare = Polygon2D.fromPoints(smallSquareMeshByHand);

        /* Super simple! Now, this alone does not cause the square to render to the screen. In
         * order for it to be rendered, you need to add it as a game object to the drawable
         * manager. */
        drawableManager.addGameObject(smallSquare);

        /* If you comment out the line above, you'll see that the small square does not get
         * rendered. */


        /* You can set the following properties of a Polygon2D:
         * - Mesh (Pointf array)
         * - Fill (solid color or a gradient)
         * - Outline Stroke (outline style in the form of a BasicStroke)
         * - Outline Color (same type of color as in Fill)
         * - RenderStyle (render the outline, fill, or both)
         * - Transformation (translation, rotation, scale)
         * - ShouldRender (whether the Polygon2D should be rendered to the screen)
         *
         * To show this off, we'll create a larger square with the following property values:
         * - Square mesh at (625, 25.5) with a size of 47.9
         * - Blue color
         * - black outline 5 pixels wide with rounded edges and endpoints)
         * - render style defining that it should be filled and outlined
         * - Translated by (20, 10)
         * - Rotation by 30 degrees
         * - Scaled down to 50% (0.5) */
        Pointf[] largeSquareMesh = DrawUtil.createBox(625f, 25.5f, 47.9f);
        BasicStroke largeSquareOutlineStroke = new BasicStroke(5.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

        Pointf largeSquareTranslation = new Pointf(20f, 10f);
        float largeSquareRotation = 30f;
        Pointf largeSquareScale = new Pointf(0.5f, 0.5f);

        Polygon2D largeSquare = Polygon2D.create(largeSquareMesh, RenderStyle.FillAndOutline)
                .withFill(Color.blue)
                .withOutline(largeSquareOutlineStroke, Color.black)
                .withTransform(largeSquareTranslation, largeSquareRotation, largeSquareScale)
                .build();

        // And of course, we need to add our large square to the drawable manager's game objects.
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
