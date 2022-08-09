package tech.fastj.math;

import tech.fastj.logging.Log;

import java.awt.geom.AffineTransform;
import java.util.Objects;

/**
 * Convenience class for storing/performing 2D transformations using {@link AffineTransform}.
 *
 * @author Andrew Dey
 * @since 1.5.0
 */
public class Transform2D {

    /** {@link Pointf} representing a default translation of {@code (0f, 0f)}. */
    public static final Pointf DefaultTranslation = Pointf.origin();

    /** {@link Pointf} representing a default scale of {@code (1f, 1f)}. */
    public static final Pointf DefaultScale = Pointf.unit();

    /** {@code float} representing a default rotation value of {@code 0f}. */
    public static final float DefaultRotation = 0f;

    /** The desired floating-point precision for {@link #getRotation() rotation checks} in FastJ. */
    public static final float RotationPrecision = 0.01f;

    private final AffineTransform translationTransform = new AffineTransform();
    private final AffineTransform rotationTransform = new AffineTransform();
    private final AffineTransform scaleTransform = new AffineTransform();
    private Pointf lastRotationPoint = Pointf.origin();
    private Pointf lastScalePoint = Pointf.origin();
    private float rotation = DefaultRotation;

    /** {@return the translation, rotation, and scale transforms, combined using {@link AffineTransform#preConcatenate(AffineTransform)}} */
    public AffineTransform getAffineTransform() {
        AffineTransform result = new AffineTransform();
        result.preConcatenate(rotationTransform);
        result.preConcatenate(translationTransform);
        result.preConcatenate(scaleTransform);
        return result;
    }

    /** {@return the transform's current translation} */
    public Pointf getTranslation() {
        return new Pointf((float) translationTransform.getTranslateX(), (float) translationTransform.getTranslateY());
    }

    /** {@return the transform's current scale} */
    public Pointf getScale() {
        return new Pointf((float) scaleTransform.getScaleX(), (float) scaleTransform.getScaleY());
    }

    /**
     * {@return the transform's current rotation}
     * <p>
     * If the calculated rotation cannot be verified as correct, a warning is logged in FastJ.
     */
    public float getRotation() {
        float approximatedRotation = (float) Math.toDegrees(Math.atan2(rotationTransform.getShearY(), rotationTransform.getScaleY()));
        float normalizedRotation = rotation % 360f;

        if (Float.compare(normalizedRotation, approximatedRotation) < RotationPrecision) {
            return rotation;
        }

        float rotationCheck = Math.abs(normalizedRotation) + Math.abs(approximatedRotation);
        if (Float.compare(rotationCheck, 360f) < RotationPrecision || Float.compare(rotationCheck, 0f) < RotationPrecision) {
            return rotation;
        }

        float rotationCheck2 = (rotation - approximatedRotation) % 360;
        if (Float.compare(rotationCheck2, 0.0f) < RotationPrecision || Float.compare(rotationCheck2, 360.0f) < RotationPrecision) {
            return rotation;
        }

        float rotationCheck3 = (rotation + approximatedRotation) % 360;
        if (Float.compare(rotationCheck3, 0.0f) < RotationPrecision || Float.compare(rotationCheck3, 360.0f) < RotationPrecision) {
            return rotation;
        }

        Log.warn("Something may have went wrong approximating the rotation.... we expected " + rotation + ", but we got " + approximatedRotation + " instead.");
        return rotation;
    }

    /** {@return the transform's current rotation, within the range of {@code -360f} to {@code 360f}} */
    public float getRotationWithin360() {
        return getRotation() % 360f;
    }

    /**
     * Sets the translation to the given point.
     *
     * @param translation the translation to set the translation transform to.
     */
    public void setTranslation(Pointf translation) {
        Pointf oldTranslation = getTranslation();
        translate(new Pointf(-oldTranslation.x + translation.x, -oldTranslation.y + translation.y));
    }

    /**
     * Sets the scale transform to the given scale, scaled relative to its last scale point.
     *
     * @param scale The scale to set the scale transform to.
     */
    public void setScale(Pointf scale) {
        Pointf oldScale = getScale();
        scale(new Pointf(-oldScale.x + scale.x, -oldScale.y + scale.y), lastScalePoint);
    }

    /**
     * Sets the rotation transform to the given rotation, rotated about its last rotation point.
     *
     * @param rotation The rotation to set the rotation transform to.
     */
    public void setRotation(float rotation) {
        rotate(-getRotation() + rotation, lastRotationPoint);
    }

    /**
     * Translates the translation transform by the given translation amount.
     *
     * @param translation The amount to translate by.
     */
    public void translate(Pointf translation) {
        translationTransform.translate(translation.x, translation.y);
    }

    /**
     * Modifies the scale transform relative to the given centerpoint.
     *
     * @param scale       The amount to change the scale by.
     * @param centerpoint The point to scale relative to.
     */
    public void scale(Pointf scale, Pointf centerpoint) {
        lastScalePoint = centerpoint.copy();

        Pointf newScale = Pointf.add(scale, getScale());
        Pointf scaleDifference = Pointf.subtract(getScale(), scale);
        Pointf moveBack = Pointf.multiply(scaleDifference, centerpoint);

        scaleTransform.translate(-centerpoint.x, -centerpoint.y);

        scaleTransform.scale(1f / scaleTransform.getScaleX(), 1f / scaleTransform.getScaleY());

        scaleTransform.translate(moveBack.x, moveBack.y);
        scaleTransform.scale(newScale.x, newScale.y);
    }

    /**
     * Rotates the rotation transform about the given centerpoint.
     *
     * @param rotation    The amount to rotate by.
     * @param centerpoint The point to rotate about.
     */
    public void rotate(float rotation, Pointf centerpoint) {
        this.rotation += rotation;
        lastRotationPoint = centerpoint.copy();
        rotationTransform.rotate(Math.toRadians(rotation), centerpoint.x, centerpoint.y);
    }

    /**
     * Resets the entire {@link Transform2D} -- its {@link #resetTranslation() translation}, {@link #resetRotation() rotation}, and
     * {@link #resetScale() scale}.
     * <p>
     * This <b>does not</b> reset the last rotation or scale points.
     */
    public void reset() {
        rotation = DefaultRotation;
        resetTranslation();
        resetRotation();
        resetScale();
    }

    /** Resets the translation. */
    public void resetTranslation() {
        translationTransform.setToIdentity();
    }

    /** Resets the rotation, without changing the last rotation point. */
    public void resetRotation() {
        rotationTransform.setToIdentity();
    }

    /** Resets the scale, without changing the last scale point. */
    public void resetScale() {
        scaleTransform.setToIdentity();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Transform2D transform2D = (Transform2D) other;
        return Maths.floatEquals(rotation, transform2D.rotation)
            && getTranslation().equals(transform2D.getTranslation())
            && getScale().equals(transform2D.getScale());
    }

    @Override
    public int hashCode() {
        return Objects.hash(translationTransform, rotationTransform, scaleTransform, lastRotationPoint, lastScalePoint, rotation);
    }

    @Override
    public String toString() {
        return "Transform2D{" +
            "translation=" + getTranslation() +
            ", rotation=" + rotation +
            ", scale=" + getScale() +
            '}';
    }
}
