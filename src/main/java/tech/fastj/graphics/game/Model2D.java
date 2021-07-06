package tech.fastj.graphics.game;

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

    private Polygon2D[] polygons;

    /**
     * Model2D constructor that takes in an array of {@link Polygon2D} objects.
     * <p>
     * This constructor defaults the {@code shouldRender} boolean to {@link Drawable#DefaultShouldRender}.
     *
     * @param polygons Array of {@code Polygon2D}s used to create the Model2D.
     */
    Model2D(Polygon2D[] polygons) {
        this.polygons = polygons;
        setCollisionPath(DrawUtil.createPath(DrawUtil.createCollisionOutline(this.polygons)));
    }

    /**
     * Gets a {@link Model2DBuilder} instance while setting the eventual {@link Model2D}'s {@code points} field.
     * <p>
     *
     * @param polygons {@code Polygon2D} array that defines the polygons for the {@code Model2D}.
     * @return A {@code Model2DBuilder} instance for creating a {@code Model2D}.
     */
    public static Model2DBuilder create(Polygon2D[] polygons) {
        return new Model2DBuilder(polygons, Drawable.DefaultShouldRender);
    }

    /**
     * Gets a {@link Model2DBuilder} instance while setting the eventual {@link Model2D}'s {@code points} and {@code
     * shouldRender} fields.
     * <p>
     *
     * @param polygons {@code Polygon2D} array that defines the polygons for the {@code Model2D}.
     * @return A {@code Model2DBuilder} instance for creating a {@code Model2D}.
     */
    public static Model2DBuilder create(Polygon2D[] polygons, boolean shouldRender) {
        return new Model2DBuilder(polygons, shouldRender);
    }

    /**
     * Creates a {@code Model2D} from the specified points.
     *
     * @param polygons {@code Polygon2D} array that defines the polygons for the {@code Model2D}.
     * @return The resulting {@code Model2D}.
     */
    public static Model2D fromPolygons(Polygon2D[] polygons) {
        return new Model2DBuilder(polygons, Drawable.DefaultShouldRender).build();
    }

    /**
     * Gets the array of {@code Polygon2D}s for this Model2D.
     *
     * @return The array of {@code Polygon2D}s.
     */
    public Polygon2D[] getPolygons() {
        return polygons;
    }

    @Override
    public void render(Graphics2D g) {
        if (!shouldRender()) {
            return;
        }

        AffineTransform oldTransform = (AffineTransform) g.getTransform().clone();
        g.transform(getTransformation());

        for (Polygon2D obj : polygons) {
            obj.render(g);
        }

        g.setTransform(oldTransform);
    }

    @Override
    public void destroy(Scene originScene) {
        for (Polygon2D obj : polygons) {
            obj.destroy(originScene);
        }
        polygons = null;

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
        return Arrays.deepEquals(polygons, model2D.polygons);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Arrays.hashCode(polygons);
        return result;
    }

    @Override
    public String toString() {
        return "Model2D{" +
                "polyArr=" + Arrays.deepToString(polygons) +
                ", rotation=" + getRotation() +
                ", scale=" + getScale() +
                ", translation=" + getTranslation() +
                '}';
    }
}
