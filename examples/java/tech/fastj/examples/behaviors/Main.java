package tech.fastj.examples.behaviors;

import tech.fastj.engine.FastJEngine;
import tech.fastj.graphics.display.FastJCanvas;
import tech.fastj.graphics.game.Polygon2D;
import tech.fastj.graphics.game.RenderStyle;
import tech.fastj.graphics.util.DrawUtil;

import tech.fastj.systems.behaviors.Behavior;
import tech.fastj.systems.control.SimpleManager;

import java.awt.Color;

public class Main extends SimpleManager {

    @Override
    public void init(FastJCanvas canvas) {

        /* Behaviors in FastJ */

        /* FastJ's behavior system provides a simple and effective way to control GameObjects.
         *
         * A behavior:
         * - Is any class that implements the Behavior interface
         * - can be attached to any GameObject
         * - has instances, allowing behaviors to be used more than one GameObject at a time
         * - provides methods to control game objects through SimpleManager/Scene initialization
         *   and updating */


        /* Let's jump straight into it. Since a Behavior is just a class, we can simply create one.
         * From here, jump over to tech.fastj.examples.behaviors.SimpleMovementBehavior.java to see
         * what makes up the behavior. */
        SimpleMovementBehavior movementBehavior = new SimpleMovementBehavior();


        /* To use the Behavior, we need to attach it to a game object.
         * For this demonstration, we'll create a simple box.
         * Its starting position will be (0, 0), and its rotation will be 0 degrees -- keep a close
         * eye on how the behavior affects these properties. */
        Polygon2D box = Polygon2D.fromPoints(DrawUtil.createBox(0f, 0f, 50f));

        /* And lastly, we need to attach the behavior to the game object. It's as simple as calling
         * gameObject.addBehavior(Behavior, SimpleManager/Scene) -- the SimpleManager/Scene is the
         * manager the behavior is in. */
        box.addBehavior(movementBehavior, this);
        drawableManager.addGameObject(box);


        /* Pre-defined Behaviors */

        /* FastJ also contains some pre-defined behaviors to avoid having users create an entire
         * Behavior just to have it constantly transform an object.
         *
         * There are three predefined behaviors:
         * - Behavior.simpleTranslation -- constantly translates a game object by the amount specified
         * - Behavior.simpleRotation -- constantly rotates a game object by the amount specified
         * - Behavior.simpleScale -- constantly scales a game object by the amount specified
         *
         * For this example, we'll just use simpleRotation. */

        Polygon2D premadeBehaviorsBox = Polygon2D.create(DrawUtil.createBox(500f, 500f, 50f), RenderStyle.FillAndOutline)
                .withFill(Color.red)
                .withOutline(Polygon2D.DefaultOutlineStroke, Polygon2D.DefaultOutlineColor)
                .build();

        premadeBehaviorsBox.addBehavior(Behavior.simpleRotation(3f), this);
        drawableManager.addGameObject(premadeBehaviorsBox);
    }

    @Override
    public void fixedUpdate(FastJCanvas canvas) {
        // Empty -- this example does not make use of this method.
    }

    @Override
    public void update(FastJCanvas canvas) {
        // Empty -- this example does not make use of this method.
    }

    public static void main(String[] args) {
        FastJEngine.init("Hello, FastJ Behaviors!", new Main());
        FastJEngine.run();
    }
}
