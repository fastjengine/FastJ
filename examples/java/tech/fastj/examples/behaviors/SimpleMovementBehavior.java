package tech.fastj.examples.behaviors;

import tech.fastj.engine.FastJEngine;
import tech.fastj.graphics.game.GameObject;
import tech.fastj.math.Pointf;
import tech.fastj.systems.behaviors.Behavior;

public class SimpleMovementBehavior implements Behavior {

    /* Behaviors (any class which implements the Behavior interface) have a few useful methods:
     * - init(), which takes in the GameObject that uses the behavior and is called when the
     *   behavior gets initialized (usually right after the GameObject gets initialized).
     * - update(), which takes in the same GameObject that is provided in init() and is called
     *   after every SimpleManager/Scene update() call.
     *
     * You'll get to know these methods inside and out with time. */

    private String gameObjectId;

    @Override
    public void init(GameObject gameObject) {
        /* Since this will only be called on first initialization, we can use it to accomplish
         * initial actions.
         *
         * In this case, we'll simply change the game object's translation to (50, 50). Since
         * translate(Pointf) is available to all GameObjects, this is general enough to be applied
         * to any sort of GameObject that could be used with this Behavior. */

        gameObject.translate(new Pointf(50f, 50f));

        /* We'll also log that the behavior has been initialized, to get a sense of when this
         * method is called. While doing so, we'll keep track of the game object's id for later
         * usage. */
        FastJEngine.log("SimpleMovement behavior for GameObject " + gameObject.getID() + " has been initialized.");
        gameObjectId = gameObject.getID();
    }

    @Override
    public void fixedUpdate(GameObject gameObject) {
        /* Since this will always be called right after the fixedUpdate() call on the
         * SimpleManager/Scene, we can use it to accomplish repeated actions.
         *
         * In this case, we'll rotate the game object by 5 degrees every time the method is called.
         * Since rotate(float) is available to all GameObjects, this is general enough to be applied
         * to any sort of GameObject that could be used with this Behavior. */

        gameObject.rotate(5f);
    }

    @Override
    public void destroy() {
        /* You can also implement this method -- destroy().
         * When the behavior's game object is destroyed, the behavior gets destroyed as well. You
         * can choose to include specific behavior for when the behavior is destroyed using this
         * method. */

        /* First, we'll log that the behavior was destroyed using the gameObjectId we kept from
         * before. */
        FastJEngine.log("SimpleMovement behavior for GameObject " + gameObjectId + " has been destroyed.");

        /* Now that the id is no longer used, we'll set the gameObjectId we kept from before to
         * null. */
        gameObjectId = null;
    }
}
