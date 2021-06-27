package tech.fastj.graphics.game;

import tech.fastj.math.Pointf;
import tech.fastj.graphics.util.DrawUtil;

import tech.fastj.systems.control.Scene;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.AffineTransform;
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

    private Pointf[] originalPoints;

    private Paint paint;
    private boolean shouldFill;

    /**
     * {@code Polygon2D} constructor that takes in a set of points.
     * <p>
     * This constructor defaults the paint to {@link #DefaultPaint}, the fill to {@link #DefaultFill}, and sets the
     * {@code show} boolean to {@link #DefaultShow}.
     *
     * @param points {@code Pointf} array that defines the points for the polygon.
     */
    public Polygon2D(Pointf[] points) {
        this(points, DefaultPaint, DefaultFill, DefaultShow);
    }

    /**
     * {@code Polygon2D} constructor that takes in a set of points, a paint, a fill variable, and a show variable.
     *
     * @param points   {@code Pointf} array that defines the points for the polygon.
     * @param paint {@code Paint} variable that sets the paint of the polygon.
     * @param fill  Boolean that determines whether the polygon should be filled, or only outlined.
     * @param show  Boolean that determines whether the polygon should be shown on screen.
     */
    public Polygon2D(Pointf[] points, Paint paint, boolean fill, boolean show) {
        originalPoints = points;

        setCollisionPath(DrawUtil.createPath(originalPoints));

        setPaint(paint);
        setFilled(fill);

        setShouldRender(show);
    }

    /**
     * {@code Polygon2D} constructor that takes in a set of points, a paint variable, fill variable, a show variable,
     * and the translation, rotation, and scale of the polygon.
     *
     * @param points            {@code Pointf} array that defines the points for the polygon.
     * @param setTranslation {@code Pointf} to set the initial translation of the polygon.
     * @param setRotation    {@code Pointf} to set the initial rotation of the polygon.
     * @param setScale       {@code Pointf} to set the initial scale of the polygon.
     * @param paint          {@code Paint} variable that sets the paint of the polygon.
     * @param fill           Boolean that determines whether the polygon should be filled, or only outlined.
     * @param show           Boolean that determines whether the polygon should be shown on screen.
     */
    public Polygon2D(Pointf[] points, Pointf setTranslation, float setRotation, Pointf setScale, Paint paint, boolean fill, boolean show) {
        originalPoints = points;

        setCollisionPath(DrawUtil.createPath(originalPoints));

        setTranslation(setTranslation);
        setRotation(setRotation);
        setScale(setScale);

        setPaint(paint);
        setFilled(fill);

        setShouldRender(show);
    }

    /**
     * Gets the original points that were set for this polygon.
     *
     * @return The original set of points for this polygon, as a {@code Pointf[]}.
     */
    public Pointf[] getOriginalPoints() {
        return originalPoints;
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
        paint = Objects.requireNonNull(newPaint);
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
        return DrawUtil.pointsOfPath(transformedCollisionPath);
    }

    /**
     * Replaces the current point array with the parameter point array.
     * <p>
     * This does not reset the rotation, scale, or location of the original, unless specified with the second, third,
     * and fourth parameters.
     *
     * @param points              {@code Pointf} array that will replace the current points of the polygon.
     * @param resetTranslation Boolean to determine if the translation should be reset.
     * @param resetRotation    Boolean to determine if the rotation should be reset.
     * @param resetScale       Boolean to determine if the scale should be reset.
     */
    public void modifyPoints(Pointf[] points, boolean resetTranslation, boolean resetRotation, boolean resetScale) {
        originalPoints = points;

        if (resetTranslation && resetRotation && resetScale) {
            transform.reset();
        } else {
            if (resetTranslation) {
                transform.resetTranslation();
            }
            if (resetRotation) {
                transform.resetRotation();
            }
            if (resetScale) {
                transform.resetScale();
            }
        }

        setCollisionPath(DrawUtil.createPath(originalPoints));
    }

    @Override
    public void render(Graphics2D g) {
        if (!shouldRender()) {
            return;
        }

        AffineTransform oldTransform = (AffineTransform) g.getTransform().clone();
        Paint oldPaint = g.getPaint();

        g.transform(getTransformation());
        g.setPaint(paint);

        if (shouldFill) {
            g.fill(collisionPath);
        } else {
            g.draw(collisionPath);
        }

        g.setTransform(oldTransform);
        g.setPaint(oldPaint);
    }

    @Override
    public void destroy(Scene originScene) {
        originalPoints = null;

        paint = null;
        shouldFill = false;

        destroyTheRest(originScene);
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
                && Arrays.equals(originalPoints, polygon2D.originalPoints)
                && DrawUtil.paintEquals(paint, polygon2D.paint);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(paint, shouldFill);
        result = 31 * result + Arrays.hashCode(originalPoints);
        return result;
    }

    @Override
    public String toString() {
        return "Polygon2D{" +
                "renderPath=" + Arrays.toString(getPoints()) +
                ", points=" + Arrays.toString(originalPoints) +
                ", paint=" + paint +
                ", shouldFill=" + shouldFill +
                ", rotation=" + getRotation() +
                ", scale=" + getScale() +
                ", translation=" + getTranslation() +
                '}';
    }
}
