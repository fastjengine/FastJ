package io.github.lucasstarsz.fastj.graphics.shapes;

import io.github.lucasstarsz.fastj.graphics.Boundary;
import io.github.lucasstarsz.fastj.graphics.DrawUtil;
import io.github.lucasstarsz.fastj.graphics.GameObject;
import io.github.lucasstarsz.fastj.math.Maths;
import io.github.lucasstarsz.fastj.math.Pointf;
import io.github.lucasstarsz.fastj.systems.game.Scene;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.Objects;

/**
 * {@code Drawable} subclass for grouping an array of {@code Polygon2D}s under a single object.
 * <p>
 * This class is compatible with loading from a .PSDF file, using the {@code DrawUtil.load2DModel()} method.
 *
 * @author Andrew Dey
 * @version 1.0.0
 */
public class Model2D extends GameObject {

    private Polygon2D[] polyArr;
    private Polygon2D collisionObject;

    private float rotation;
    private Pointf scale;
    private Pointf translation;

    /**
     * Model2D constructor.
     * <p>
     * This takes an array of {@code Pointf} values (which make up the points of the polygon), and a boolean to
     * determine whether the Model2D should be drawn.
     *
     * @param polygonArray  Array of {@code Polygon2D}s used to create the Model2D.
     * @param shouldBeShown Boolean that determines whether this Model2D should be drawn to the screen.
     */
    public Model2D(Polygon2D[] polygonArray, boolean shouldBeShown) {
        polyArr = polygonArray;

        setBounds(createBounds());

        rotation = 0;
        scale = new Pointf(1);
        translation = new Pointf(getBound(Boundary.TOP_LEFT));

        setCollisionPoints();

        setShouldRender(shouldBeShown);
    }

    /**
     * Model2D constructor.
     * <p>
     * Alongside the normal constructor, this allows you to set a location, rotation, and scale for the object,
     * alongside the normal values needed for a Model2D.
     *
     * @param polygonArray  Array of {@code Polygon2D}s used to create the Model2D.
     * @param location      {@code Pointf} that defines the x and y location of the created Model2D. All Polygon2D
     *                      objects will be translated to that location, relative to where the objects are.
     * @param rotVal        Float value that defines the value that the Model2D will be rotated to, on creation.
     * @param scaleVal      {@code Pointf} that defines the values that the Model2D will be scaled to, on creation.
     * @param shouldBeShown Boolean that determines whether this Model2D should be drawn to the screen.
     */
    public Model2D(Polygon2D[] polygonArray, Pointf location, float rotVal, Pointf scaleVal, boolean shouldBeShown) {
        polyArr = polygonArray;

        setBounds(createBounds());

        rotation = 0;
        scale = new Pointf(1);
        translation = new Pointf(getBound(Boundary.TOP_LEFT));

        setTranslation(location);
        rotate(rotVal);
        scale(scaleVal);

        setBounds(createBounds());

        setCollisionPoints();

        setShouldRender(shouldBeShown);
    }

    /**
     * Gets the array of {@code Polygon2D}s for this Model2D.
     *
     * @return The array of {@code Polygon2D}s.
     */
    public Polygon2D[] getObjects() {
        return polyArr;
    }

    @Override
    public float getRotation() {
        return rotation;
    }

    @Override
    public Pointf getScale() {
        return scale;
    }

    @Override
    public Pointf getTranslation() {
        return translation;
    }

    @Override
    public void translate(Pointf translationMod) {
        translation.add(translationMod);

        for (Polygon2D obj : polyArr) {
            obj.translate(translationMod);
        }

        collisionObject.translate(translationMod);
        setCollisionPath(collisionObject.getRenderPath());

        translateBounds(translationMod);
    }

    @Override
    public void rotate(float rotationMod, Pointf centerpoint) {
        rotation += rotationMod;

        for (Polygon2D obj : polyArr) {
            obj.rotate(rotationMod, centerpoint);
        }

        collisionObject.rotate(rotationMod, centerpoint);
        setCollisionPath(collisionObject.getRenderPath());

        setBounds(createBounds());
    }

    @Override
    public void scale(Pointf scaleMod, Pointf centerpoint) {
        scale.add(scaleMod);

        for (Polygon2D obj : polyArr) {
            obj.scale(scaleMod, centerpoint);
        }

        collisionObject.scale(scaleMod, centerpoint);
        setCollisionPath(collisionObject.getRenderPath());

        setBounds(createBounds());
    }

    @Override
    public void render(Graphics2D g) {
        if (!shouldRender()) return;
        for (Polygon2D obj : polyArr) {
            obj.render(g);
        }
    }

    @Override
    public void destroy(Scene originScene) {
        for (Polygon2D obj : polyArr) {
            obj.destroy(originScene);
        }
        polyArr = null;

        scale = null;
        rotation = 0;
        translation = null;

        collisionObject.destroy(originScene);
        collisionObject = null;

        destroyTheRest(originScene);
    }

    /** Sets the collision points for the {@code Model2D}. */
    private void setCollisionPoints() {
        collisionObject = new Polygon2D(DrawUtil.createCollisionOutline(polyArr), Color.black, false, false);
        setCollisionPath(collisionObject.getRenderPath());
    }

    /** Creates the boundaries for the {@code Model2D}. */
    private Pointf[] createBounds() {
        Pointf[] boundaries = new Pointf[4];

        /* Individually set each boundary point -- Arrays#fill uses the same object
         * for each point, making it undesirable for this use case. */
        boundaries[0] = polyArr[0].getCenter().copy();
        boundaries[1] = polyArr[0].getCenter().copy();
        boundaries[2] = polyArr[0].getCenter().copy();
        boundaries[3] = polyArr[0].getCenter().copy();

        for (Polygon2D b : polyArr) {
            for (Pointf coord : b.getBounds()) {
                // top left
                boundaries[0].x = Math.min(boundaries[0].x, coord.x);
                boundaries[0].y = Math.min(boundaries[0].y, coord.y);

                // top right
                boundaries[1].x = Math.max(boundaries[1].x, coord.x);

                // bottom right
                boundaries[2].y = Math.max(boundaries[2].y, coord.y);
            }
        }

        // top right
        boundaries[1].y = boundaries[0].y;

        // bottom right
        boundaries[2].x = boundaries[1].x;

        // bottom left
        boundaries[3].x = boundaries[0].x;
        boundaries[3].y = boundaries[2].y;

        return boundaries;
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
        return Objects.equals(translation, model2D.translation)
                && Objects.equals(scale, model2D.scale)
                && Maths.floatEquals(model2D.rotation, rotation)
                && Arrays.equals(polyArr, model2D.polyArr)
                && Objects.equals(collisionObject, model2D.collisionObject);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(collisionObject, rotation, scale, translation);
        result = 31 * result + Arrays.hashCode(polyArr);
        return result;
    }
}
