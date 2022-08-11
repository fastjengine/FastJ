package tech.fastj.systems.behaviors;

import tech.fastj.graphics.display.FastJCanvas;
import tech.fastj.graphics.game.GameObject;
import tech.fastj.math.Pointf;
import tech.fastj.systems.control.Scene;
import tech.fastj.systems.control.SceneManager;
import tech.fastj.systems.control.SimpleManager;

/**
 * Interface that allows for the addition of behaviors to {@link GameObject game objects}.
 * <p>
 * Behaviors go hand-in-hand with {@link GameObject game objects}. A {@link GameObject game object} can have as many references to the same
 * {@code behavior} as you may need.
 * <p>
 * This is a simple, complete example of adding a behavior to a game object. This can be applied to both {@link Scene scenes} and
 * {@link SimpleManager simple managers}, but the example will use {@link SimpleManager}. You can set up the {@code init} method call in a
 * scene with {@link Scene#load(FastJCanvas)} to have the same effect -- refer to {@link SceneManager} for setting up a scene-based game.
 * {@snippet lang = "java":
 * import tech.fastj.engine.FastJEngine;
 * import tech.fastj.graphics.display.FastJCanvas;
 * import tech.fastj.graphics.game.Polygon2D;
 * import tech.fastj.systems.behaviors.Behavior;
 * import tech.fastj.systems.control.SimpleManager;
 *
 * public class BehaviorExample extends SimpleManager {
 *     @Override
 *     public void init(FastJCanvas canvas) {
 *         Polygon2D box = Polygon2D.fromPath(DrawUtil.createBox(0f, 0f, 10f));
 *         drawableManager().addGameObject(box);
 *
 *         Behavior rotationBehavior = Behavior.simpleRotation(1f); // @highlight
 *         box.addBehavior(rotationBehavior, this); // @highlight
 *     }
 *
 *     public static void main(String[] args) {
 *         FastJEngine.init("Rotating Box", new BehaviorExample());
 *         FastJEngine.run();
 *     }
 * }}
 *
 * @author Andrew Dey
 * @since 1.0.0
 */
public interface Behavior {

    /**
     * Gets an instance of {@link Behavior} that, when assigned, {@link GameObject#translate(Pointf) translates its game object} by the
     * specified translation every {@link #fixedUpdate(GameObject) fixed update} call.
     *
     * @param translationModifier The {@code Pointf} value to be used for translation.
     * @return The newly created {@code Behavior}.
     */
    static Behavior simpleTranslation(Pointf translationModifier) {
        return new Behavior() {
            @Override
            public void fixedUpdate(GameObject gameObject) {
                gameObject.translate(translationModifier);
            }
        };
    }

    /**
     * Gets an instance of {@link Behavior} that, when assigned, {@link GameObject#rotate(float) rotates its game object} by the specified
     * rotation every {@link #fixedUpdate(GameObject) fixed update} call.
     *
     * @param rotationModifier The float value to be used for rotation.
     * @return The newly created {@code Behavior}.
     */
    static Behavior simpleRotation(float rotationModifier) {
        return new Behavior() {
            @Override
            public void fixedUpdate(GameObject gameObject) {
                gameObject.rotate(rotationModifier);
            }
        };
    }

    /**
     * Gets an instance of {@link Behavior} that, when assigned, {@link GameObject#scale(float) scales its game object} by the specified
     * scale every {@link #fixedUpdate(GameObject) fixed update} call.
     *
     * @param scaleModifier The {@code Pointf} value to be used for scaling.
     * @return The newly created {@code Behavior}.
     */
    static Behavior simpleScale(Pointf scaleModifier) {
        return new Behavior() {
            @Override
            public void fixedUpdate(GameObject gameObject) {
                gameObject.scale(scaleModifier);
            }
        };
    }

    /**
     * Initializes the behavior and its assigned {@link GameObject}.
     * <p>
     * This method is used for modifying anything about the {@link GameObject game object}(s) that this behavior is assigned to, before the
     * game begins its update cycle. It is called after the declaring *
     * {@link Scene#load(FastJCanvas) scene}/{@link SimpleManager#init(FastJCanvas) simple manager} has completed its init/load method.
     *
     * @param gameObject A {@link GameObject} that has been assigned this behavior.
     */
    default void init(GameObject gameObject) {
    }

    /**
     * Updates the behavior and its assigned {@link GameObject} on {@code fixed update}.
     * <p>
     * This method is used for modifying anything about the {@link GameObject game object}(s) that this behavior is assigned to, before the
     * game begins its update cycle. It is called after the declaring
     * {@link Scene#fixedUpdate(FastJCanvas) scene}/{@link SimpleManager#fixedUpdate(FastJCanvas) simple manager} has completed its fixed
     * update method.
     *
     * @param gameObject A GameObject that has been assigned this behavior.
     */
    default void fixedUpdate(GameObject gameObject) {
    }

    /**
     * Updates the behavior and its assigned {@link GameObject} on {@code update}.
     * <p>
     * This method is used for modifying anything about the {@link GameObject game object}(s) that this behavior is assigned to, before the
     * game begins its update cycle. It is called after the declaring
     * {@link Scene#update(FastJCanvas) scene}/{@link SimpleManager#update(FastJCanvas) simple manager} has completed its update method.
     *
     * @param gameObject A GameObject that has been assigned this behavior.
     */
    default void update(GameObject gameObject) {
    }

    /** Deletes/resets the {@link Behavior behavior}'s state as necessary. */
    default void destroy() {
    }
}
