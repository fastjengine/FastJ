package tech.fastj.graphics.display;

import tech.fastj.math.Pointf;
import tech.fastj.math.Transform2D;

import java.awt.geom.AffineTransform;
import java.util.Objects;

/**
 * Class that allows for transformation of the {@code Display} which the camera is passed to.
 *
 * @author Andrew Dey
 * @version 1.0.0
 */
public class Camera {

    /** A camera with no transformations. */
    public static final Camera Default = new Camera();

    private final Transform2D transform;

    /** Constructs a {@code Camera} with default transformations. */
    public Camera() {
        this(Transform2D.DefaultTranslation, Transform2D.DefaultRotation, Transform2D.DefaultScale);
    }

    /**
     * Constructs a {@code Camera} with translation and rotation set to the specified values.
     *
     * @param setTranslation {@code Pointf} parameter that the {@code Camera}'s translation will be set to.
     * @param setRotation    float parameter that the {@code Camera}'s rotation will be set to.
     * @param setScale       {@code Pointf} parameter that the {@code Camera}'s scale will be set to.
     */
    public Camera(Pointf setTranslation, float setRotation, Pointf setScale) {
        transform = new Transform2D();
        setTransform(setTranslation, setRotation, setScale);
    }

    /**
     * Gets the centerpoint of the {@code Camera}.
     *
     * @return The centerpoint, as a {@code Pointf}.
     */
    public Pointf getCenter() {
        return getTranslation().copy();
    }

    /**
     * Gets the {@code Camera}'s translation.
     *
     * @return A {@code Pointf} that represents the current translation of the {@code Camera}.
     */
    public Pointf getTranslation() {
        return transform.getTranslation();
    }

    /**
     * Sets the {@code Camera}'s translation to the specified value.
     *
     * @param setTranslation {@code Pointf} parameter that the {@code Camera}'s translation will be set to.
     * @return The {@code Camera}, for method chaining.
     */
    public Camera setTranslation(Pointf setTranslation) {
        if (getTranslation().equals(setTranslation)) {
            return this;
        }

        transform.setTranslation(setTranslation);
        return this;
    }

    /**
     * Gets the {@code Camera}'s rotation.
     *
     * @return A float that represents the current rotation of the {@code Camera}.
     */
    public float getRotation() {
        return transform.getRotation();
    }

    /**
     * Sets the {@code Camera}'s rotation to the specified value.
     *
     * @param setRotation float parameter that the {@code Camera}'s rotation will be set to.
     * @return The {@code Camera}, for method chaining.
     */
    public Camera setRotation(float setRotation) {
        if (getRotation() == setRotation) {
            return this;
        }

        transform.setRotation(setRotation);
        return this;
    }

    /**
     * Gets the {@code Camera}'s scale.
     *
     * @return A {@code Pointf} that represents the current scale of the object.
     */
    public Pointf getScale() {
        return transform.getScale();
    }

    /**
     * Sets the {@code Camera}'s scale to the specified value.
     *
     * @param setScale {@code Pointf} parameter that the {@code Camera}'s scale will be set to.
     * @return The {@code Camera}, for method chaining.
     */
    public Camera setScale(Pointf setScale) {
        if (getScale().equals(setScale)) {
            return this;
        }

        transform.setScale(setScale);
        return this;
    }

    /**
     * Sets the {@code Camera}'s translation, rotation, and scale to the specified values.
     *
     * @param setTranslation {@code Pointf} parameter that the {@code Camera}'s translation will be set to.
     * @param setRotation    float parameter that the {@code Camera}'s rotation will be set to.
     * @param setScale       {@code Pointf} parameter that the {@code Camera}'s scale will be set to.
     * @return The {@code Camera}, for method chaining.
     */
    public Camera setTransform(Pointf setTranslation, float setRotation, Pointf setScale) {
        setTranslation(setTranslation);
        setRotation(setRotation);
        setScale(setScale);
        return this;
    }

    /**
     * Translates the {@code Camera}'s position by the specified translation.
     *
     * @param translationMod {@code Pointf} parameter that the {@code Camera}'s x and y location will be translated by.
     */
    public void translate(Pointf translationMod) {
        if (Transform2D.DefaultTranslation.equals(translationMod)) {
            return;
        }

        transform.translate(translationMod);
    }

    /**
     * Rotates the {@code Camera} in the direction of the specified rotation, about the specified centerpoint.
     *
     * @param rotationMod float parameter that the {@code Camera} will be rotated by.
     * @param centerpoint {@code Pointf} parameter that the {@code Camera} will be rotated about.
     */
    public void rotate(float rotationMod, Pointf centerpoint) {
        if (rotationMod == Transform2D.DefaultRotation) {
            return;
        }

        transform.rotate(rotationMod, centerpoint);
    }

    /**
     * Scales the {@code Camera} in by the amount specified in the specified scale, from the specified centerpoint.
     *
     * @param scaleMod    {@code Pointf} parameter that the {@code Camera}'s width and height will be scaled by.
     * @param centerpoint {@code Pointf} parameter that the {@code Camera} will be scaled about.
     */
    public void scale(Pointf scaleMod, Pointf centerpoint) {
        if (Transform2D.DefaultScale.equals(scaleMod)) {
            return;
        }

        transform.scale(scaleMod, centerpoint);
    }

    /**
     * Gets the rotation, normalized to be within a range of {@code (-360, 360)}.
     *
     * @return The normalized rotation.
     */
    public float getRotationWithin360() {
        return transform.getRotationWithin360();
    }

    /**
     * Gets the entire transformation of the {@code Camera}.
     *
     * @return The transformation, as an {@link AffineTransform}.
     */
    public AffineTransform getTransformation() {
        return transform.getAffineTransform();
    }

    /**
     * Rotates the {@code Camera} in the direction of the specified rotation, about its center.
     *
     * @param rotationMod float parameter that the {@code Camera} will be rotated by.
     */
    public void rotate(float rotationMod) {
        rotate(rotationMod, Pointf.Origin);
    }

    /**
     * Scales the {@code Camera} in by the amount specified in the specified scale, about its center.
     *
     * @param scaleXY float parameter that the {@code Camera} will be scaled by, acting as both the x and y values.
     */
    public void scale(float scaleXY) {
        scale(new Pointf(scaleXY), getCenter());
    }

    /**
     * Scales the {@code Camera} in by the amount specified in the specified scale, about its center.
     *
     * @param scaleMod {@code Pointf} parameter that the {@code Camera} will be scaled by, based on its x and y values.
     */
    public void scale(Pointf scaleMod) {
        scale(scaleMod, getCenter());
    }

    /** Resets the camera's transformation to the default. */
    public void reset() {
        transform.reset();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Camera camera = (Camera) other;
        return camera.transform.equals(transform);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transform);
    }

    @Override
    public String toString() {
        return "Camera{" +
                "transform=" + transform +
                '}';
    }
}
