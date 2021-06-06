package tech.fastj.graphics.game;

import tech.fastj.math.Maths;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.Boundary;
import tech.fastj.graphics.util.DrawUtil;

import tech.fastj.systems.control.Scene;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.Objects;

/**
 * {@code Drawable} subclass for drawing a polygon.
 *
 * @author Andrew Dey
 * @version 1.0.0
 */
public class Polygon2D extends GameObject {

    /** {@link Paint} representing the default paint value as the color black: {@code (0, 0, 0)}. */
    public static final Paint DefaultPaint = Color.black;
    /** {@code boolean} representing the default "should fill" value of {@code true}. */
    public static final boolean DefaultFill = true;
    /** {@code boolean} representing the default "should render" value of {@code true}. */
    public static final boolean DefaultShow = true;

    private Path2D.Float renderPath;
    private Pointf[] points;

    private Paint paint;
    private boolean shouldFill;

    private float rotation;
    private Pointf scale;
    private Pointf translation;


    /**
     * {@code Polygon2D} constructor that takes in a set of points.
     * <p>
     * This constructor defaults the paint to {@link #DefaultPaint}, the fill to {@link #DefaultFill}, and sets the
     * {@code show} boolean to {@link #DefaultShow}.
     *
     * @param pts {@code Pointf} array that defines the points for the polygon.
     */
    public Polygon2D(Pointf[] pts) {
        this(pts, DefaultPaint, DefaultFill, DefaultShow);
    }

    /**
     * {@code Polygon2D} constructor that takes in a set of points, a paint, a fill variable, and a show variable.
     *
     * @param pts   {@code Pointf} array that defines the points for the polygon.
     * @param paint {@code Paint} variable that sets the paint of the polygon.
     * @param fill  Boolean that determines whether the polygon should be filled, or only outlined.
     * @param show  Boolean that determines whether the polygon should be shown on screen.
     */
    public Polygon2D(Pointf[] pts, Paint paint, boolean fill, boolean show) {
        super();
        points = pts;

        renderPath = DrawUtil.createPath(points);
        setBoundaries(renderPath);

        rotation = GameObject.DefaultRotation;
        scale = GameObject.DefaultScale.copy();
        translation = new Pointf(getBound(Boundary.TopLeft));

        setPaint(paint);
        setFilled(fill);

        setCollisionPath(renderPath);
        setShouldRender(show);
    }

    /**
     * {@code Polygon2D} constructor that takes in a set of points, a paint variable, fill variable, a show variable,
     * and the translation, rotation, and scale of the polygon.
     *
     * @param pts         {@code Pointf} array that defines the points for the polygon.
     * @param setLocation {@code Pointf} to set the initial location of the polygon.
     * @param setRotation {@code Pointf} to set the initial rotation of the polygon.
     * @param setScale    {@code Pointf} to set the initial scale of the polygon.
     * @param paint       {@code Paint} variable that sets the paint of the polygon.
     * @param fill        Boolean that determines whether the polygon should be filled, or only outlined.
     * @param show        Boolean that determines whether the polygon should be shown on screen.
     */
    public Polygon2D(Pointf[] pts, Pointf setLocation, float setRotation, Pointf setScale, Paint paint, boolean fill, boolean show) {
        super();
        points = pts;

        renderPath = DrawUtil.createPath(points);
        setBoundaries(renderPath);

        rotation = GameObject.DefaultRotation;
        scale = GameObject.DefaultScale.copy();
        translation = new Pointf(getBound(Boundary.TopLeft));

        setTranslation(setLocation);
        setRotation(setRotation);
        setScale(setScale);

        setPaint(paint);
        setFilled(fill);

        setCollisionPath(renderPath);
        setShouldRender(show);
    }

    /**
     * Gets the rendered {@code Path2D.Float} for this polygon.
     *
     * @return The {@code Path2D.Float} for this polygon.
     */
    public Path2D.Float getRenderPath() {
        return renderPath;
    }

    /**
     * Gets the original points that were set for this polygon.
     *
     * @return The original set of points for this polygon, as a {@code Pointf[]}.
     */
    public Pointf[] getOriginalPoints() {
        return points;
    }

    /**
     * Gets the paint for this polygon.
     *
     * @return The {@code Paint} set for this polygon.
     */
    public Paint getPaint() {
        return paint;
    }

    /**
     * Sets the paint for this polygon.
     *
     * @param newPaint The {@code Paint} to be used for the polygon.
     * @return This instance of the {@code Polygon2D}, for method chaining.
     */
    public Polygon2D setPaint(Paint newPaint) {
        paint = newPaint;
        return this;
    }

    /**
     * Gets the fill boolean for this polygon.
     *
     * @return The boolean variable for this polygon, which determines if the polygon should be filled, or only
     * outlined.
     */
    public boolean isFilled() {
        return shouldFill;
    }

    /**
     * Sets the fill boolean for the object.
     *
     * @param fill Boolean to determine if the polygon should be filled, or only outlined.
     * @return This instance of the {@code Polygon2D}, for method chaining.
     */
    public Polygon2D setFilled(boolean fill) {
        shouldFill = fill;
        return this;
    }

    /**
     * Gets the {@code Pointf} array associated with the current state of the polygon.
     *
     * @return The {@code Pointf} array associated with the current state of the polygon.
     */
    public Pointf[] getPoints() {
        return DrawUtil.pointsOfPath(renderPath);
    }

    /**
     * Replaces the current point array with the parameter point array.
     * <p>
     * This does not reset the rotation, scale, or location of the original, unless specified with the second, third,
     * and fourth parameters.
     *
     * @param pts              {@code Pointf} array that will replace the current points of the polygon.
     * @param resetTranslation Boolean to determine if the translation should be reset.
     * @param resetRotation    Boolean to determine if the rotation should be reset.
     * @param resetScale       Boolean to determine if the scale should be reset.
     */
    public void modifyPoints(Pointf[] pts, boolean resetTranslation, boolean resetRotation, boolean resetScale) {
        points = pts;
        renderPath = DrawUtil.createPath(points);

        if (resetTranslation) {
            translation.set(GameObject.DefaultTranslation.x, GameObject.DefaultTranslation.y);
        }
        if (resetRotation) {
            rotation = GameObject.DefaultRotation;
        }
        if (resetScale) {
            scale.set(GameObject.DefaultScale.x, GameObject.DefaultScale.y);
        }

        setBoundaries(renderPath);
        setCollisionPath(renderPath);
    }

    @Override
    public Pointf getTranslation() {
        return translation;
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
    public void translate(Pointf translationMod) {
        AffineTransform at = AffineTransform.getTranslateInstance(translationMod.x, translationMod.y);
        renderPath = (Path2D.Float) renderPath.createTransformedShape(at);

        translation.add(translationMod);

        translateBounds(translationMod);
        setCollisionPath(renderPath);
    }

    @Override
    public void rotate(float rotationMod, Pointf centerpoint) {
        AffineTransform polyAT = AffineTransform.getRotateInstance(Math.toRadians(rotationMod), centerpoint.x, centerpoint.y);
        renderPath = (Path2D.Float) renderPath.createTransformedShape(polyAT);

        rotation += rotationMod;

        setCollisionPath(renderPath);
        setBoundaries(renderPath);
    }

    @Override
    public void scale(Pointf scaleMod, Pointf centerpoint) {
        final Pointf[] renderCopy = DrawUtil.pointsOfPath(renderPath);
        final Pointf oldScale = new Pointf(scale);

        scale.add(scaleMod);

        for (Pointf pt : renderCopy) {
            final Pointf distanceFromCenter = Pointf.subtract(centerpoint, pt);

            pt.add(Pointf.multiply(distanceFromCenter, oldScale));
            pt.add(Pointf.multiply(Pointf.multiply(distanceFromCenter, -1f), scale));
        }

        renderPath = DrawUtil.createPath(renderCopy);

        setCollisionPath(renderPath);
        setBoundaries(renderPath);
    }

    @Override
    public void render(Graphics2D g) {
        if (!shouldRender()) return;

        Paint oldPaint = g.getPaint();

        g.setPaint(paint);

        if (shouldFill) {
            g.fill(renderPath);
        } else {
            g.draw(renderPath);
        }

        g.setPaint(oldPaint);
    }

    @Override
    public void destroy(Scene originScene) {
        points = null;
        renderPath = null;

        paint = null;
        shouldFill = false;

        scale = null;
        translation = null;
        rotation = 0;

        destroyTheRest(originScene);

    }

    /**
     * Sets the boundaries of the polygon, using the specified {@code Path2D.Float}.
     *
     * @param p The {@code Path2D.Float} which the boundaries will be based off of.
     */
    private void setBoundaries(Path2D.Float p) {
        super.setBounds(DrawUtil.createBox((Rectangle2D.Float) p.getBounds2D()));
    }

    /**
     * Checks for equality between the {@code Polygon2D} and the other specified.
     *
     * @param other The {@code Polygon2D} to check for equality against.
     * @return Whether the two {@code Polygon2D}s are equal.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Polygon2D polygon2D = (Polygon2D) other;

        return shouldFill == polygon2D.shouldFill
                && DrawUtil.paintEquals(paint, polygon2D.paint)
                && Objects.equals(translation, polygon2D.translation)
                && Objects.equals(scale, polygon2D.scale)
                && Maths.floatEquals(polygon2D.rotation, rotation)
                && Arrays.equals(points, polygon2D.points)
                && Arrays.equals(DrawUtil.pointsOfPath(renderPath), DrawUtil.pointsOfPath(polygon2D.renderPath));
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(renderPath, paint, shouldFill, rotation, scale, translation);
        result = 31 * result + Arrays.hashCode(points);
        return result;
    }

    @Override
    public String toString() {
        return "Polygon2D{" +
                "renderPath=" + renderPath +
                ", points=" + Arrays.toString(points) +
                ", paint=" + paint +
                ", paintFilled=" + shouldFill +
                ", rotation=" + rotation +
                ", scale=" + scale +
                ", translation=" + translation +
                '}';
    }
}
