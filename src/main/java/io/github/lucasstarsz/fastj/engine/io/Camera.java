package io.github.lucasstarsz.fastj.engine.io;

import io.github.lucasstarsz.fastj.engine.util.math.Pointf;

import java.awt.geom.AffineTransform;

/**
 * Class that allows for transformation of the {@code Display} which the camera is passed to.
 *
 * @author Andrew Dey
 * @version 0.3.2a
 */
public class Camera {

    /** A camera with no transformations. */
    public static final Camera DEFAULT = new Camera();

    private final Pointf translation;
    private float rotation;

    /** Constructs a {@code Camera} with transformation set to 0. */
    public Camera() {
        this(new Pointf(), 0f);
    }

    /**
     * Constructs a {@code Camera} with translation set to the specified value, and rotation set to
     * 0.
     *
     * @param setTranslation Sets the translation of this {@code Camera}.
     * @see Pointf
     */
    public Camera(Pointf setTranslation) {
        this(setTranslation, 0f);
    }

    /**
     * Constructs a {@code Camera} with rotation set to the specified value, and translation set to
     * 0.
     *
     * @param setRotation Sets the rotation of this {@code Camera}.
     */
    public Camera(float setRotation) {
        this(new Pointf(), setRotation);
    }

    /**
     * Constructs a {@code Camera} with translation and rotation set to the specified values.
     *
     * @param setTranslation Sets the translation of this {@code Camera}.
     * @param setRotation    Sets the rotation of this {@code Camera}.
     * @see Pointf
     */
    public Camera(Pointf setTranslation, float setRotation) {
        translation = setTranslation;
        rotation = setRotation;
    }

    /**
     * Applies a modifier to the {@code Camera}'s rotation.
     *
     * @param rotationMod Modifier for the {@code Camera}'s rotation.
     */
    public void rotate(float rotationMod) {
        rotation += rotationMod;
    }

    /**
     * Applies a modifier to the {@code Camera}'s translation.
     *
     * @param translationMod {@code Pointf} value which the {@code Camera}'s x and y location will be
     *                    modified by.
     * @see Pointf
     */
    public void translate(Pointf translationMod) {
        translation.add(translationMod);
    }

    /**
     * Gets the {@code Camera}'s current rotation.
     *
     * @return The {@code Camera}'s current rotation.
     */
    public double getRotation() {
        return rotation;
    }

    /**
     * Gets the {@code Camera}'s current translation.
     *
     * @return The {@code Camera}'s current translation.
     *
     * @see Pointf
     */
    public Pointf getTranslation() {
        return translation;
    }

    /**
     * Gets the transformation of this {@code Camera} object as an {@code AffineTransform}.
     *
     * @return The overall transformation of the {@code Camera} object as an {@code AffineTransform}
     * value.
     *
     * @see AffineTransform
     */
    public AffineTransform getTransformation() {
        AffineTransform at = new AffineTransform();
        at.rotate(rotation);
        at.translate(translation.x, translation.y);
        return at;
    }

    /** Resets the camera's transformation. */
    public void reset() {
        translation.set(0, 0);
        rotation = 0;
    }
}
