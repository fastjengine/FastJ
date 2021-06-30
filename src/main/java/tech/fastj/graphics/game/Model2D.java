package tech.fastj.graphics.game;

import tech.fastj.math.Pointf;
import tech.fastj.graphics.Drawable;
import tech.fastj.graphics.util.DrawUtil;

import tech.fastj.systems.control.Scene;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Arrays;

/**
 * {@code Drawable} subclass for grouping an array of {@code Polygon2D}s under a single object.
 *
 * @author Andrew Dey
 * @version 1.0.0
 */
public class Model2D extends GameObject {

    private Polygon2D[] polyArr;

    /**
     * Model2D constructor that takes in an array of {@link Polygon2D} objects.
     * <p>
     * This takes an array of {@code Pointf} values (which make up the points of the polygon), and defaults whether the
     * {@code Model2D} should be shown to {@link Drawable#DefaultShouldRender}.
     *
     * @param polygonArray Array of {@code Polygon2D}s used to create the Model2D.
     */
    public Model2D(Polygon2D[] polygonArray) {
        this(polygonArray, Drawable.DefaultShouldRender);
    }

    /**
     * Model2D constructor that takes in an array of {@link Polygon2D} objects and a shouldRender variable.
     * <p>
     * This takes an array of {@code Pointf} values (which make up the points of the polygon), and a boolean to
     * determine whether the Model2D should be rendered.
     *
     * @param polygonArray Array of {@code Polygon2D}s used to create the Model2D.
     * @param shouldRender Boolean that determines whether this Model2D should be drawn to the screen.
     */
    public Model2D(Polygon2D[] polygonArray, boolean shouldRender) {
        polyArr = polygonArray;

        setCollisionPath(DrawUtil.createPath(DrawUtil.createCollisionOutline(polyArr)));
        setShouldRender(shouldRender);
    }

    /**
     * {@code Model2D} constructor that takes in an array of {@code Polygon2D}s, a shouldRender variable, and an initial
     * translation, rotation, and scale for the model.
     * <p>
     * Alongside the normal constructor, this allows you to set a location, rotation, and scale for the object,
     * alongside the normal values needed for a Model2D.
     *
     * @param polygonArray Array of {@code Polygon2D}s used to create the Model2D.
     * @param location     {@code Pointf} that defines the x and y location of the created Model2D. All Polygon2D
     *                     objects will be translated to that location, relative to where the objects are.
     * @param rotVal       Float value that defines the value that the Model2D will be rotated to, on creation.
     * @param scaleVal     {@code Pointf} that defines the values that the Model2D will be scaled to, on creation.
     * @param shouldRender Boolean that determines whether this Model2D should be drawn to the screen.
     */
    public Model2D(Polygon2D[] polygonArray, Pointf location, float rotVal, Pointf scaleVal, boolean shouldRender) {
        polyArr = polygonArray;

        setCollisionPath(DrawUtil.createPath(DrawUtil.createCollisionOutline(polyArr)));

        setTranslation(location);
        setRotation(rotVal);
        setScale(scaleVal);

        setShouldRender(shouldRender);
    }

    /**
     * Gets the array of {@code Polygon2D}s for this Model2D.
     *
     * @return The array of {@code Polygon2D}s.
     */
    public Polygon2D[] getPolygons() {
        return polyArr;
    }

    @Override
    public void render(Graphics2D g) {
        if (!shouldRender()) {
            return;
        }

        AffineTransform oldTransform = (AffineTransform) g.getTransform().clone();
        g.transform(getTransformation());

        for (Polygon2D obj : polyArr) {
            obj.render(g);
        }

        g.setTransform(oldTransform);
    }

    @Override
    public void destroy(Scene originScene) {
        for (Polygon2D obj : polyArr) {
            obj.destroy(originScene);
        }
        polyArr = null;

        destroyTheRest(originScene);
    }

    /**
     * Checks for equality between the {@code Model2D} and the other specified.
     *
     * @param other The {@code Model2D} to check for equality against.
     * @return Whether the two {@code Model2D}s are equal.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Model2D model2D = (Model2D) other;
        return Arrays.deepEquals(polyArr, model2D.polyArr);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Arrays.hashCode(polyArr);
        return result;
    }

    @Override
    public String toString() {
        return "Model2D{" +
                "polyArr=" + Arrays.deepToString(polyArr) +
                ", rotation=" + getRotation() +
                ", scale=" + getScale() +
                ", translation=" + getTranslation() +
                '}';
    }
}
