package io.github.lucasstarsz.fastj.graphics.shapes;

import io.github.lucasstarsz.fastj.graphics.Boundary;
import io.github.lucasstarsz.fastj.graphics.DrawUtil;
import io.github.lucasstarsz.fastj.graphics.GameObject;
import io.github.lucasstarsz.fastj.math.Maths;
import io.github.lucasstarsz.fastj.math.Pointf;
import io.github.lucasstarsz.fastj.systems.game.Scene;

import java.awt.Color;
import java.awt.Graphics2D;
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

    /** {@link Color} representing the default color value of {@code (0, 0, 0)}. */
    public static final Color defaultColor = Color.black;
    /** {@code boolean} representing the default "should fill" value of {@code true}. */
    public static final boolean defaultFill = true;
    /** {@code boolean} representing the default "should render" value of {@code true}. */
    public static final boolean defaultShow = true;

    private Path2D.Float renderPath;
    private Pointf[] points;

    private Color color;
    private boolean paintFilled;

    private float rotation;
    private Pointf scale;
    private Pointf translation;


    /**
     * {@code Polygon2D} constructor that takes in a set of points.
     * <p>
     * This constructor defaults the color to black, and both the {@code fill} and {@code show} booleans to {@code
     * true}.
     *
     * @param pts {@code Pointf} array that defines the points for the polygon.
     */
    public Polygon2D(Pointf[] pts) {
        this(pts, defaultColor, defaultFill, defaultShow);
    }

    /**
     * {@code Polygon2D} constructor that takes in a set of points, a color, a fill variable, and a show variable.
     *
     * @param pts   {@code Pointf} array that defines the points for the polygon.
     * @param color {@code Color} variable that sets the color of the polygon.
     * @param fill  Boolean that determines whether the polygon should be filled, or only outlined.
     * @param show  Boolean that determines whether the polygon should be shown on screen.
     */
    public Polygon2D(Pointf[] pts, Color color, boolean fill, boolean show) {
        super();
        points = pts;

        renderPath = DrawUtil.createPath(points);
        setBoundaries(renderPath);

        scale = new Pointf(1);
        rotation = 0;
        translation = new Pointf(getBound(Boundary.TOP_LEFT));

        setColor(color);
        setFilled(fill);

        setCollisionPath(renderPath);
        setShouldRender(show);
    }

    /**
     * {@code Polygon2D} constructor that takes in a set of points, a color, fill variable, a show variable, and the
     * translation, rotation, and scale of the polygon.
     *
     * @param pts         {@code Pointf} array that defines the points for the polygon.
     * @param setLocation {@code Pointf} to set the initial location of the polygon.
     * @param setRotation {@code Pointf} to set the initial rotation of the polygon.
     * @param setScale    {@code Pointf} to set the initial scale of the polygon.
     * @param color       {@code Color} variable that sets the color of the polygon.
     * @param fill        Boolean that determines whether the polygon should be filled, or only outlined.
     * @param show        Boolean that determines whether the polygon should be shown on screen.
     */
    public Polygon2D(Pointf[] pts, Pointf setLocation, float setRotation, Pointf setScale, Color color, boolean fill, boolean show) {
        super();
        points = pts;

        renderPath = DrawUtil.createPath(points);
        setBoundaries(renderPath);

        scale = new Pointf(1);
        rotation = 0;
        translation = new Pointf(getBound(Boundary.TOP_LEFT));

        setTranslation(setLocation);
        setRotation(setRotation);
        setScale(setScale);

        setColor(color);
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
     * Gets the color set for this polygon.
     *
     * @return The {@code Color} set for this polygon.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color for the polygon.
     *
     * @param newColor The {@code Color} to be used for the polygon.
     * @return This instance of the {@code Polygon2D}, for method chaining.
     */
    public Polygon2D setColor(Color newColor) {
        color = newColor;
        return this;
    }

    /**
     * Gets the fill boolean for this polygon.
     *
     * @return The boolean variable for this polygon, which determines if the polygon should be filled, or only
     * outlined.
     */
    public boolean isFilled() {
        return paintFilled;
    }

    /**
     * Sets the fill boolean for the object.
     *
     * @param fill Boolean to determine if the polygon should be filled, or only outlined.
     * @return This instance of the {@code Polygon2D}, for method chaining.
     */
    public Polygon2D setFilled(boolean fill) {
        paintFilled = fill;
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
            translation.set(GameObject.defaultTranslation.x, GameObject.defaultTranslation.y);
        }
        if (resetRotation) {
            rotation = GameObject.defaultRotation;
        }
        if (resetScale) {
            scale.set(GameObject.defaultScale.x, GameObject.defaultScale.y);
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

        // TODO: Add working image rotation

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

        // TODO: Add working image scaling

        setCollisionPath(renderPath);
        setBoundaries(renderPath);
    }

    @Override
    public void render(Graphics2D g) {
        if (!shouldRender()) return;

        g.setColor(color);

        if (paintFilled) {
            g.fill(renderPath);
        } else {
            g.draw(renderPath);
        }
    }

    @Override
    public void destroy(Scene originScene) {
        points = null;
        renderPath = null;

        color = null;
        paintFilled = false;

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
        Polygon2D otherPolygon2D = (Polygon2D) other;

        return paintFilled == otherPolygon2D.paintFilled
                && Objects.equals(color, otherPolygon2D.color)
                && Objects.equals(translation, otherPolygon2D.translation)
                && Objects.equals(scale, otherPolygon2D.scale)
                && Math.abs(otherPolygon2D.rotation - rotation) < Maths.FloatPrecision
                && Arrays.equals(points, otherPolygon2D.points)
                && Arrays.equals(DrawUtil.pointsOfPath(renderPath), DrawUtil.pointsOfPath(otherPolygon2D.renderPath));
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(renderPath, color, paintFilled, rotation, scale, translation);
        result = 31 * result + Arrays.hashCode(points);
        return result;
    }
}
