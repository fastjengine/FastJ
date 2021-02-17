package io.github.lucasstarsz.fastj.framework.graphics;

import io.github.lucasstarsz.fastj.framework.math.Pointf;

import java.awt.geom.AffineTransform;

public abstract class TransformableDrawable extends Drawable {

    /**
     * Gets the translation of the {@code Drawable}.
     *
     * @return A {@code Pointf} that represents the current translation of the {@code Drawable}.
     * @see io.github.lucasstarsz.fastj.framework.math.Pointf
     */
    public abstract Pointf getTranslation();

    /**
     * Sets the {@code Drawable}'s translation to the specified value.
     *
     * @param setTranslation {@code Pointf} parameter that the {@code Drawable}'s translation will be set to.
     * @see io.github.lucasstarsz.fastj.framework.math.Pointf
     */
    public void setTranslation(Pointf setTranslation) {
        translate(Pointf.multiply(getTranslation(), -1f).add(setTranslation));
    }

    /**
     * Gets the rotation of the {@code Drawable}.
     *
     * @return A float that represents the current rotation of the {@code Drawable}.
     */
    public abstract float getRotation();

    /**
     * Sets the {@code Drawable}'s rotation to the specified value.
     *
     * @param setRotation float parameter that the {@code Drawable}'s rotation will be set to.
     */
    public void setRotation(float setRotation) {
        rotate(-getRotation() + setRotation);
    }

    /**
     * Gets the scale of the {@code Drawable}.
     *
     * @return A {@code Pointf} that represents the current scale of the object.
     * @see io.github.lucasstarsz.fastj.framework.math.Pointf
     */
    public abstract Pointf getScale();

    /**
     * Sets the {@code Drawable}'s scale to the specified value.
     *
     * @param setScale {@code Pointf} parameter that the {@code Drawable}'s scale will be set to.
     * @see io.github.lucasstarsz.fastj.framework.math.Pointf
     */
    public void setScale(Pointf setScale) {
        scale(Pointf.multiply(getScale(), -1f).add(setScale));
    }

    /**
     * Translates the {@code Drawable}'s position by the specified translation.
     *
     * @param translationMod {@code Pointf} parameter that the {@code Drawable}'s x and y location will be translated
     *                       by.
     * @see io.github.lucasstarsz.fastj.framework.math.Pointf
     */
    public abstract void translate(Pointf translationMod);

    /**
     * Rotates the {@code Drawable} in the direction of the specified rotation, about the specified centerpoint.
     *
     * @param rotationMod The float parameter that the {@code Drawable} will be rotated by.
     * @param centerpoint {@code Pointf} parameter that the {@code Drawable} will be rotated about.
     * @see io.github.lucasstarsz.fastj.framework.math.Pointf
     */
    public abstract void rotate(float rotationMod, Pointf centerpoint);

    /**
     * Scales the {@code Drawable} in by the amount specified in the specified scale, from the specified centerpoint.
     *
     * @param scaleMod    {@code Pointf} parameter that the {@code Drawable}'s width and height will be scaled by.
     * @param centerpoint {@code Pointf} parameter that the {@code Drawable} will be scaled about.
     * @see io.github.lucasstarsz.fastj.framework.math.Pointf
     */
    public abstract void scale(Pointf scaleMod, Pointf centerpoint);

    /**
     * Gets the rotation, normalized to be within 360 degrees.
     *
     * @return The normalized rotation.
     */
    public float getRotationWithin360() {
        return getRotation() % 360;
    }

    /**
     * Gets the entire transformation of the {@code Drawable}.
     *
     * @return The transformation, as an {@code AffineTransform}.
     * @see AffineTransform
     */
    public AffineTransform getTransformation() {
        final AffineTransform transformation = new AffineTransform();

        final Pointf scale = getScale();
        final Pointf location = getTranslation();

        transformation.setToScale(scale.x, scale.y);
        transformation.setToRotation(getRotation());
        transformation.setToTranslation(location.x, location.y);

        return transformation;
    }

    /**
     * Rotates the {@code Drawable} in the direction of the specified rotation, about its center.
     *
     * @param rotVal Float parameter that the {@code Drawable} will be rotated by.
     */
    public void rotate(float rotVal) {
        rotate(rotVal, getCenter());
    }

    /**
     * Scales the {@code Drawable} in by the amount specified in the specified scale, about its center.
     *
     * @param scaleXY Float value that the {@code Drawable} will be scaled by, acting as both the x and y values.
     */
    public void scale(float scaleXY) {
        scale(new Pointf(scaleXY), getCenter());
    }

    /**
     * Scales the {@code Drawable} in by the amount specified in the specified scale, about its center.
     *
     * @param scale {@code Pointf} parameter that the {@code Drawable} will be scaled by, based on its x and y values.
     * @see io.github.lucasstarsz.fastj.framework.math.Pointf
     */
    public void scale(Pointf scale) {
        scale(scale, getCenter());
    }
}
