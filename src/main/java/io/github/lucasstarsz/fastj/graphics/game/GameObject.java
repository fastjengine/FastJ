package io.github.lucasstarsz.fastj.graphics.game;

import io.github.lucasstarsz.fastj.math.Pointf;
import io.github.lucasstarsz.fastj.graphics.Drawable;

import io.github.lucasstarsz.fastj.systems.behaviors.Behavior;
import io.github.lucasstarsz.fastj.systems.control.Scene;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

/**
 * A type of {@link Drawable} that can be easily transformed and otherwise manipulated.
 * <p>
 * The {@code GameObject} class is one of the most useful backing classes in the engine. It contains the logic needed to
 * transform objects that can be rendered to the screen -- polygons, models, and (still a work in progress) text.
 *
 * @author Andrew Dey
 * @version 1.0.0
 */
public abstract class GameObject extends Drawable {

    /** {@link Pointf} representing a default translation of {@code (0f, 0f)}. */
    public static final Pointf DefaultTranslation = Pointf.Origin.copy();
    /** {@link Pointf} representing a default scale of {@code (1f, 1f)}. */
    public static final Pointf DefaultScale = new Pointf(1f).copy();
    /** {@code float} representing a default rotation value of {@code 0f}. */
    public static final float DefaultRotation = 0f;

    private final List<Behavior> behaviors;

    /** Initializes {@link GameObject} internals. */
    protected GameObject() {
        behaviors = new ArrayList<>();
    }

    /**
     * Gets the {@code GameObject}'s list of {@link Behavior}s.
     *
     * @return The list of {@code Behavior}s.
     */
    public List<Behavior> getBehaviors() {
        return behaviors;
    }

    /**
     * Gets the {@code GameObject}'s translation.
     *
     * @return A {@code Pointf} that represents the current translation of the {@code GameObject}.
     */
    public abstract Pointf getTranslation();

    /**
     * Sets the {@code GameObject}'s translation to the specified value.
     *
     * @param setTranslation {@code Pointf} parameter that the {@code GameObject}'s translation will be set to.
     * @return The {@code GameObject}, for method chaining.
     */
    public GameObject setTranslation(Pointf setTranslation) {
        translate(Pointf.multiply(getTranslation(), -1f).add(setTranslation));
        return this;
    }

    /**
     * Gets the {@code GameObject}'s rotation.
     *
     * @return A float that represents the current rotation of the {@code GameObject}.
     */
    public abstract float getRotation();

    /**
     * Sets the {@code GameObject}'s rotation to the specified value.
     *
     * @param setRotation float parameter that the {@code GameObject}'s rotation will be set to.
     * @return The {@code GameObject}, for method chaining.
     */
    public GameObject setRotation(float setRotation) {
        rotate(-getRotation() + setRotation);
        return this;
    }

    /**
     * Gets the {@code GameObject}'s scale.
     *
     * @return A {@code Pointf} that represents the current scale of the object.
     */
    public abstract Pointf getScale();

    /**
     * Sets the {@code GameObject}'s scale to the specified value.
     *
     * @param setScale {@code Pointf} parameter that the {@code GameObject}'s scale will be set to.
     * @return The {@code GameObject}, for method chaining.
     */
    public GameObject setScale(Pointf setScale) {
        scale(Pointf.multiply(getScale(), -1f).add(setScale));
        return this;
    }

    /**
     * Translates the {@code GameObject}'s position by the specified translation.
     *
     * @param translationMod {@code Pointf} parameter that the {@code GameObject}'s x and y location will be translated
     *                       by.
     */
    public abstract void translate(Pointf translationMod);

    /**
     * Rotates the {@code GameObject} in the direction of the specified rotation, about the specified center point.
     *
     * @param rotationMod float parameter that the {@code GameObject} will be rotated by.
     * @param centerpoint {@code Pointf} parameter that the {@code GameObject} will be rotated about.
     */
    public abstract void rotate(float rotationMod, Pointf centerpoint);

    /**
     * Scales the {@code GameObject} in by the amount specified in the specified scale, from the specified center
     * point.
     *
     * @param scaleMod    {@code Pointf} parameter that the {@code GameObject}'s width and height will be scaled by.
     * @param centerpoint {@code Pointf} parameter that the {@code GameObject} will be scaled about.
     */
    public abstract void scale(Pointf scaleMod, Pointf centerpoint);

    /**
     * Gets the rotation, normalized to be within a range of {@code (-360, 360)}.
     *
     * @return The normalized rotation.
     */
    public float getRotationWithin360() {
        return getRotation() % 360;
    }

    /**
     * Gets the entire transformation of the {@code GameObject}.
     *
     * @return The transformation, as an {@link AffineTransform}.
     */
    public AffineTransform getTransformation() {
        AffineTransform transformation = new AffineTransform();
        Pointf scale = getScale();
        Pointf location = getTranslation();

        transformation.setToScale(scale.x, scale.y);
        transformation.setToRotation(getRotation());
        transformation.setToTranslation(location.x, location.y);

        return transformation;
    }

    /**
     * Rotates the {@code GameObject} in the direction of the specified rotation, about its center.
     *
     * @param rotationMod float parameter that the {@code GameObject} will be rotated by.
     */
    public void rotate(float rotationMod) {
        rotate(rotationMod, getCenter());
    }

    /**
     * Scales the {@code GameObject} in by the amount specified in the specified scale, about its center.
     *
     * @param scaleXY float parameter that the {@code GameObject} will be scaled by, acting as both the x and y values.
     */
    public void scale(float scaleXY) {
        scale(new Pointf(scaleXY), getCenter());
    }

    /**
     * Scales the {@code GameObject} in by the amount specified in the specified scale, about its center.
     *
     * @param scaleMod {@code Pointf} parameter that the {@code GameObject} will be scaled by, based on its x and y
     *                 values.
     */
    public void scale(Pointf scaleMod) {
        scale(scaleMod, getCenter());
    }

    /** Calls the {@link Behavior#init} method for each of the {@code GameObject}'s behaviors. */
    public void initBehaviors() {
        for (Behavior behavior : behaviors) {
            behavior.init(this);
        }
    }

    /** Calls the {@link Behavior#update} method for each of the {@code GameObject}'s behaviors. */
    public void updateBehaviors() {
        for (Behavior behavior : behaviors) {
            behavior.update(this);
        }
    }

    /** Calls the {@link Behavior#destroy} method for each of the {@code GameObject}'s behaviors. */
    public void destroyAllBehaviors() {
        for (Behavior behavior : behaviors) {
            behavior.destroy();
        }
    }

    /** Clears the {@code GameObject}'s list of {@code Behavior}s. */
    public void clearAllBehaviors() {
        behaviors.clear();
    }

    /**
     * Adds the specified {@link Behavior} to the {@code GameObject}'s list of {@code Behavior}s.
     * <p>
     * {@code Behavior}s can be added as many times as needed.
     *
     * @param behavior {@code Behavior} parameter to be added.
     * @param origin   Scene that the {@code GameObject} will be added to, as a behavior listener.
     * @return the {@code GameObject} is returned for method chaining.
     */
    public GameObject addBehavior(Behavior behavior, Scene origin) {
        behaviors.add(behavior);
        origin.addBehaviorListener(this);

        return this;
    }

    /**
     * Removes the specified {@link Behavior} from the {@code GameObject}'s list of {@code Behavior}s.
     *
     * @param behavior    {@code Behavior} parameter to be removed from.
     * @param originScene Scene that, if the {@code GameObject} no longer has any Behaviors, the {@code GameObject} will
     *                    be removed from as a behavior listener.
     * @return the {@code GameObject} is returned for method chaining.
     */
    public GameObject removeBehavior(Behavior behavior, Scene originScene) {
        behaviors.remove(behavior);
        if (behaviors.size() == 0) {
            originScene.removeBehaviorListener(this);
        }

        return this;
    }

    /**
     * Renders the {@code GameObject} to the specified {@link Graphics2D} parameter.
     *
     * @param g The {@code Graphics2D} parameter to render the {@code GameObject} to.
     */
    public abstract void render(Graphics2D g);

    /**
     * Destroys all references of the {@code GameObject}'s behaviors and removes its references from the scene.
     *
     * @param origin {@code Scene} parameter that will have all references to this {@code GameObject} removed.
     */
    @Override
    protected void destroyTheRest(Scene origin) {
        super.destroyTheRest(origin);

        origin.drawableManager.removeGameObject(this);
        origin.removeBehaviorListener(this);

        destroyAllBehaviors();
        clearAllBehaviors();
    }

    @Override
    public String toString() {
        return "GameObject{" +
                "collisionPath=" + collisionPath +
                ", behaviors=" + behaviors +
                '}';
    }
}
