package tech.fastj.graphics;

import tech.fastj.math.Pointf;

import java.awt.geom.AffineTransform;

public class Transform {

    private final AffineTransform affineTransform = new AffineTransform();

    public AffineTransform getAffineTransform() {
        return affineTransform;
    }

    public Pointf getTranslation() {
        return new Pointf((float) affineTransform.getTranslateX(), (float) affineTransform.getTranslateY());
    }

    public Pointf getScale() {
        return new Pointf((float) affineTransform.getScaleX(), (float) affineTransform.getScaleY());
    }

    public float getRotation() {
        return (float) Math.atan2(affineTransform.getShearY(), affineTransform.getScaleY());
    }

    public float getRotationWithin360() {
        return getRotation() % 360.0f;
    }

    public Pointf getShear() {
        return new Pointf((float) affineTransform.getShearX(), (float) affineTransform.getShearY());
    }

    public void setTranslation(Pointf translation) {
        affineTransform.setToTranslation(translation.x, translation.y);
    }

    public void setScale(Pointf scale) {
        affineTransform.setToScale(scale.x, scale.y);
    }

    public void setRotation(float rotation) {
        affineTransform.setToRotation(Math.toRadians(rotation));
    }

    public void setShear(Pointf shear) {
        affineTransform.setToShear(shear.x, shear.y);
    }

    public void translate(Pointf translation) {
        affineTransform.translate(translation.x, translation.y);
    }

    public void scale(Pointf scale) {
        affineTransform.scale(scale.x, scale.y);
    }

    public void rotate(float rotation) {
        affineTransform.rotate(Math.toRadians(rotation));
    }

    public void shear(Pointf shear) {
        affineTransform.shear(shear.x, shear.y);
    }

    public void reset() {
        affineTransform.setToIdentity();
    }
}
