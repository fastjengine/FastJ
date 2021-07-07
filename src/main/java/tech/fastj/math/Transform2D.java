package tech.fastj.math;

import java.awt.geom.AffineTransform;
import java.util.Objects;

/** Convenience class for storing/performing 2D transformations using {@link AffineTransform}. */
public class Transform2D {

    /** {@link Pointf} representing a default translation of {@code (0f, 0f)}. */
    public static final Pointf DefaultTranslation = Pointf.Origin.copy();
    /** {@link Pointf} representing a default scale of {@code (1f, 1f)}. */
    public static final Pointf DefaultScale = new Pointf(1f).copy();
    /** {@code float} representing a default rotation value of {@code 0f}. */
    public static final float DefaultRotation = 0f;

    private final AffineTransform translationTransform = new AffineTransform();
    private final AffineTransform rotationTransform = new AffineTransform();
    private final AffineTransform scaleTransform = new AffineTransform();
    private Pointf lastRotationPoint = Pointf.Origin.copy();
    private Pointf lastScalePoint = Pointf.Origin.copy();
    private float rotation = DefaultRotation;

    public AffineTransform getAffineTransform() {
        AffineTransform result = new AffineTransform();
        result.preConcatenate(rotationTransform);
        result.preConcatenate(translationTransform);
        result.preConcatenate(scaleTransform);
        return result;
    }

    public Pointf getTranslation() {
        return new Pointf((float) translationTransform.getTranslateX(), (float) translationTransform.getTranslateY());
    }

    public Pointf getScale() {
        return new Pointf((float) scaleTransform.getScaleX(), (float) scaleTransform.getScaleY());
    }

    public float getRotation() {
        float approximatedRotation = (float) Math.toDegrees(Math.atan2(rotationTransform.getShearY(), rotationTransform.getScaleY()));
        float normalizedRotation = rotation % 360f;

        if (Maths.floatEquals(normalizedRotation, approximatedRotation)) {
            return rotation;
        }

        float rotationCheck = Math.abs(normalizedRotation) + Math.abs(approximatedRotation);
        if (Maths.floatEquals(rotationCheck, 360f) || Maths.floatEquals(rotationCheck, 0f)) {
            return rotation;
        }

        // according to logical thinking, this should not be reached.
        throw new IllegalStateException(
                "Something went wrong calculating the rotation.... we expected " + rotation + ", but we got " + approximatedRotation + " instead. :("
        );
    }

    public float getRotationWithin360() {
        return getRotation() % 360f;
    }

    public void setTranslation(Pointf translation) {
        Pointf oldTranslation = getTranslation();
        translate(new Pointf(-oldTranslation.x + translation.x, -oldTranslation.y + translation.y));
    }

    public void setScale(Pointf scale) {
        Pointf oldScale = getScale();
        scale(new Pointf(-oldScale.x + scale.x, -oldScale.y + scale.y), lastScalePoint);
    }

    public void setRotation(float rotation) {
        rotate(-getRotation() + rotation, lastRotationPoint);
    }

    public void translate(Pointf translation) {
        translationTransform.translate(translation.x, translation.y);
    }

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

    public void rotate(float rotation, Pointf centerpoint) {
        this.rotation += rotation;
        lastRotationPoint = centerpoint.copy();
        rotationTransform.rotate(Math.toRadians(rotation), centerpoint.x, centerpoint.y);
    }

    public void reset() {
        rotation = DefaultRotation;
        resetTranslation();
        resetRotation();
        resetScale();
    }

    public void resetTranslation() {
        translationTransform.setToIdentity();
    }

    public void resetRotation() {
        rotationTransform.setToIdentity();
    }

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
