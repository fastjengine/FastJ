package io.github.lucasstarsz.fastj.framework.systems.behaviors;

import io.github.lucasstarsz.fastj.framework.graphics.TransformableDrawable;
import io.github.lucasstarsz.fastj.framework.math.Pointf;

/**
 * Interface that allows for the addition of behaviors to {@code Drawable}s.
 * <p>
 * Behaviors can be assigned to any Drawable, as many times as you want.
 *
 * @author Andrew Dey
 * @version 1.0.0
 * @see io.github.lucasstarsz.fastj.framework.systems.behaviors.Behavior
 * @since 1.0.0
 */
public interface Behavior {

    /**
     * Gets an instance of {@code Behavior} that, when assigned to a {@code Drawable}, translates it by the specified
     * translation every update call.
     *
     * @param translationModifier The {@code Pointf} value to be used for translation.
     * @return The newly created {@code Behavior}.
     * @see io.github.lucasstarsz.fastj.framework.math.Pointf
     */
    static Behavior simpleTranslation(Pointf translationModifier) {
        return new Behavior() {
            @Override
            public void init(TransformableDrawable obj) {
            }

            @Override
            public void update(TransformableDrawable obj) {
                obj.translate(translationModifier);
            }
        };
    }

    /**
     * Gets an instance of {@code Behavior} that, when assigned to a {@code Drawable}, rotates it by the specified
     * rotation every update call.
     *
     * @param rotationModifier The float value to be used for rotation.
     * @return The newly created {@code Behavior}.
     */
    static Behavior simpleRotation(float rotationModifier) {
        return new Behavior() {
            @Override
            public void init(TransformableDrawable obj) {
            }

            @Override
            public void update(TransformableDrawable obj) {
                obj.rotate(rotationModifier);
            }
        };
    }

    /**
     * Gets an instance of {@code Behavior} that, when assigned to a {@code Drawable}, scales it by the specified scale
     * every update call.
     *
     * @param scaleModifier The {@code Pointf} value to be used for scaling.
     * @return The newly created {@code Behavior}.
     * @see io.github.lucasstarsz.fastj.framework.math.Pointf
     */
    static Behavior simpleScale(Pointf scaleModifier) {
        return new Behavior() {
            @Override
            public void init(TransformableDrawable obj) {
            }

            @Override
            public void update(TransformableDrawable obj) {
                obj.scale(scaleModifier);
            }
        };
    }

    /**
     * Initializes the assigned {@code Drawable}.
     * <p>
     * This method is used for modifying anything about the Drawable(s) that this behavior is assigned to, before the
     * game is rendered. It is called after the parent {@code Scene} has completed its {@code load()} method.
     *
     * @param obj A Drawable that has been assigned this behavior.
     * @see TransformableDrawable
     */
    void init(TransformableDrawable obj);

    /**
     * Updates the assigned {@code Drawable}.
     * <p>
     * This method is used to modify anything about the assigned {@code Drawable}, every time the assigned {@code
     * Drawable}'s containing {@code Scene} updates. It is called after the parent {@code Scene} has completed its
     * {@code update()} method.
     *
     * @param obj A Drawable that has been assigned this behavior.
     * @see TransformableDrawable
     */
    void update(TransformableDrawable obj);

    /** Destroys any leftover memory in the {@code Behavior}. */
    default void destroy() {
    }
}
