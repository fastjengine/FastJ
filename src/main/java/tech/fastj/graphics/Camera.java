package tech.fastj.graphics;

import tech.fastj.math.Maths;
import tech.fastj.math.Pointf;

import java.awt.geom.AffineTransform;
import java.util.Objects;

/**
 * Class that allows for transformation of the {@code Display} which the camera is passed to.
 *
 * @author Andrew Dey
 * @version 1.0.0
 */
public class Camera {

    /** {@link Pointf} representing a default translation of {@code (0f, 0f)}. */
    public static final Pointf DefaultTranslation = Pointf.Origin.copy();
    /** {@code float} representing a default rotation value of {@code 0f}. */
    public static final float DefaultRotation = 0f;

    /** A camera with no transformations. */
    public static final Camera Default = new Camera();

    private final Pointf translation;
    private float rotation;

    /** Constructs a {@code Camera} with default transformations. */
    public Camera() {
        this(DefaultTranslation.copy(), DefaultRotation);
    }

    /**
     * Constructs a {@code Camera} with translation set to the specified value, and rotation set to 0.
     *
     * @param setTranslation Sets the translation of this {@code Camera}.
     */
    public Camera(Pointf setTranslation) {
        this(setTranslation, DefaultRotation);
    }

    /**
     * Constructs a {@code Camera} with rotation set to the specified value, and translation set to 0.
     *
     * @param setRotation Sets the rotation of this {@code Camera}.
     */
    public Camera(float setRotation) {
        this(DefaultTranslation, setRotation);
    }

    /**
     * Constructs a {@code Camera} with translation and rotation set to the specified values.
     *
     * @param setTranslation Sets the translation of this {@code Camera}.
     * @param setRotation    Sets the rotation of this {@code Camera}.
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
     * @param translationMod {@code Pointf} value which the {@code Camera}'s x and y location will be modified by.
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
     */
    public Pointf getTranslation() {
        return translation;
    }

    /**
     * Gets the transformation of this {@code Camera} object as an {@code AffineTransform}.
     *
     * @return The overall transformation of the {@code Camera} object as an {@code AffineTransform} value.
     */
    public AffineTransform getTransformation() {
        AffineTransform at = new AffineTransform();

        if (rotation != Camera.DefaultRotation) {
            at.rotate(Math.toRadians(rotation));
        }
        if (translation.x != Camera.DefaultTranslation.x || translation.y != Camera.DefaultTranslation.y) {
            at.translate(translation.x, translation.y);
        }

        return at;
    }

    /** Resets the camera's transformation to the default. */
    public void reset() {
        translation.reset();
        rotation = Camera.DefaultRotation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Camera camera = (Camera) o;
        return Maths.floatEquals(camera.rotation, rotation) && Objects.equals(translation, camera.translation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(translation, rotation);
    }

    @Override
    public String toString() {
        return "Camera{" +
                "translation=" + translation +
                ", rotation=" + rotation +
                '}';
    }
}
