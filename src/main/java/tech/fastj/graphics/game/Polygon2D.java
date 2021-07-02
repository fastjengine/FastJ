package tech.fastj.graphics.game;

import tech.fastj.math.Pointf;
import tech.fastj.graphics.Drawable;
import tech.fastj.graphics.RenderStyle;
import tech.fastj.graphics.util.DrawUtil;

import tech.fastj.systems.control.Scene;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
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

    /** {@link RenderStyle} representing the default render style as fill only, or {@link RenderStyle#Fill}. */
    public static final RenderStyle DefaultRenderStyle = RenderStyle.Fill;
    /** {@link Paint} representing the default fill paint value as the color black. */
    public static final Paint DefaultPaint = Color.black;
    /** {@link Stroke} representing the default outline stroke value as a 1px outline with sharp edges. */
    public static final BasicStroke DefaultOutlineStroke = new BasicStroke(1f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 1.0f);
    /** {@link Color} representing the default outline color value as the color black. */
    public static final Color DefaultOutlineColor = Color.black;

    private Pointf[] originalPoints;

    private RenderStyle renderStyle;
    private Paint fillPaint;
    private Color outlineColor;
    private BasicStroke outlineStroke;

    /**
     * {@code Polygon2D} constructor that takes in an array of points.
     * <p>
     * This constructor defaults the fill paint to {@link #DefaultPaint}, the outline stroke to {@link
     * #DefaultOutlineStroke}, the outline color to {@link #DefaultOutlineColor}, the render style to {@link
     * #DefaultRenderStyle}, and the {@code shouldRender} boolean to {@link Drawable#DefaultShouldRender}.
     *
     * @param points {@code Pointf} array that defines the points for the polygon.
     */
    Polygon2D(Pointf[] points) {
        originalPoints = points;
        setCollisionPath(DrawUtil.createPath(originalPoints));

        setFill(DefaultPaint);
        setOutlineStroke(DefaultOutlineStroke);
        setOutlineColor(DefaultOutlineColor);
        setRenderStyle(DefaultRenderStyle);
        setShouldRender(Drawable.DefaultShouldRender);
    }

    /**
     * Gets a {@link Polygon2DBuilder} instance while setting the eventual {@link Polygon2D}'s {@code points}, {@code
     * renderStyle}, and {@code shouldRender} fields.
     * <p>
     *
     * @param points {@code Pointf} array that defines the points for the {@code Polygon2D}.
     * @return A {@code Polygon2DBuilder} instance for creating a {@code Polygon2D}.
     */
    public static Polygon2DBuilder create(Pointf[] points) {
        return new Polygon2DBuilder(points, DefaultRenderStyle, Drawable.DefaultShouldRender);
    }

    /**
     * Gets a {@link Polygon2DBuilder} instance while setting the eventual {@link Polygon2D}'s {@code points} and {@code
     * shouldRender} fields.
     * <p>
     *
     * @param points       {@code Pointf} array that defines the points for the {@code Polygon2D}.
     * @param shouldRender {@code boolean} that defines whether the {@code Polygon2D} would be rendered to the screen.
     * @return A {@code Polygon2DBuilder} instance for creating a {@code Polygon2D}.
     */
    public static Polygon2DBuilder create(Pointf[] points, boolean shouldRender) {
        return new Polygon2DBuilder(points, DefaultRenderStyle, shouldRender);
    }

    /**
     * Gets a {@link Polygon2DBuilder} instance while setting the eventual {@link Polygon2D}'s {@code points} and {@code
     * renderStyle} fields.
     * <p>
     *
     * @param points      {@code Pointf} array that defines the points for the {@code Polygon2D}.
     * @param renderStyle {@code RenderStyle} that defines the render style for the {@code Polygon2D}.
     * @return A {@code Polygon2DBuilder} instance for creating a {@code Polygon2D}.
     */
    public static Polygon2DBuilder create(Pointf[] points, RenderStyle renderStyle) {
        return new Polygon2DBuilder(points, renderStyle, Drawable.DefaultShouldRender);
    }

    /**
     * Gets a {@link Polygon2DBuilder} instance while setting the eventual {@link Polygon2D}'s {@code points}, {@code
     * renderStyle}, and {@code shouldRender} fields.
     * <p>
     *
     * @param points       {@code Pointf} array that defines the points for the {@code Polygon2D}.
     * @param renderStyle  {@code RenderStyle} that defines the render style for the {@code Polygon2D}.
     * @param shouldRender {@code boolean} that defines whether the {@code Polygon2D} would be rendered to the screen.
     * @return A {@code Polygon2DBuilder} instance for creating a {@code Polygon2D}.
     */
    public static Polygon2DBuilder create(Pointf[] points, RenderStyle renderStyle, boolean shouldRender) {
        return new Polygon2DBuilder(points, renderStyle, shouldRender);
    }

    /**
     * Creates a {@code Polygon2D} from the specified points.
     *
     * @param points {@code Pointf} array that defines the points for the {@code Polygon2D}.
     * @return The resulting {@code Polygon2D}.
     */
    public static Polygon2D fromPoints(Pointf[] points) {
        return new Polygon2DBuilder(points, DefaultRenderStyle, Drawable.DefaultShouldRender).build();
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
    public Paint getFill() {
        return fillPaint;
    }

    public Color getOutlineColor() {
        return outlineColor;
    }

    public BasicStroke getOutlineStroke() {
        return outlineStroke;
    }

    public RenderStyle getRenderStyle() {
        return renderStyle;
    }

    /**
     * Sets the paint for this polygon.
     *
     * @param newPaint The {@code Paint} to be used for the polygon.
     * @return This instance of the {@code Polygon2D}, for method chaining.
     */
    public Polygon2D setFill(Paint newPaint) {
        fillPaint = Objects.requireNonNull(newPaint);
        return this;
    }

    public Polygon2D setOutlineColor(Color newOutlineColor) {
        outlineColor = newOutlineColor;
        return this;
    }

    public Polygon2D setOutlineStroke(BasicStroke newStroke) {
        outlineStroke = newStroke;
        return this;
    }

    public Polygon2D setOutline(BasicStroke newStroke, Color newOutlineColor) {
        outlineStroke = newStroke;
        outlineColor = newOutlineColor;
        return this;
    }

    public Polygon2D setRenderStyle(RenderStyle newRenderStyle) {
        renderStyle = newRenderStyle;
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
     * @param points           {@code Pointf} array that will replace the current points of the polygon.
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
        Stroke oldStroke = g.getStroke();

        g.transform(getTransformation());

        switch (renderStyle) {
            case Fill: {
                g.setPaint(fillPaint);
                g.fill(collisionPath);
                break;
            }
            case Outline: {
                g.setStroke(outlineStroke);
                g.setPaint(outlineColor);
                g.draw(collisionPath);
                break;
            }
            case FillAndOutline: {
                g.setPaint(fillPaint);
                g.fill(collisionPath);

                g.setStroke(outlineStroke);
                g.setPaint(outlineColor);
                g.draw(collisionPath);
                break;
            }
        }

        g.setStroke(oldStroke);
        g.setPaint(oldPaint);
        g.setTransform(oldTransform);
    }

    @Override
    public void destroy(Scene originScene) {
        originalPoints = null;

        renderStyle = null;
        fillPaint = null;
        outlineColor = null;
        outlineStroke = null;

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
        return Arrays.equals(originalPoints, polygon2D.originalPoints)
                && renderStyle == polygon2D.renderStyle
                && DrawUtil.paintEquals(fillPaint, polygon2D.fillPaint)
                && outlineColor.equals(polygon2D.outlineColor)
                && outlineStroke.equals(polygon2D.outlineStroke);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(renderStyle, fillPaint, outlineColor, outlineStroke);
        result = 31 * result + Arrays.hashCode(originalPoints);
        return result;
    }

    @Override
    public String toString() {
        return "Polygon2D{" +
                "originalPoints=" + Arrays.toString(originalPoints) +
                ", renderStyle=" + renderStyle +
                ", fillPaint=" + fillPaint +
                ", outlineColor=" + outlineColor +
                ", outlineStroke=" + outlineStroke +
                '}';
    }
}
