package io.github.lucasstarsz.fastj.engine.graphics.shapes;

import io.github.lucasstarsz.fastj.engine.graphics.Boundary;
import io.github.lucasstarsz.fastj.engine.graphics.Drawable;
import io.github.lucasstarsz.fastj.engine.io.Camera;
import io.github.lucasstarsz.fastj.engine.systems.game.Scene;
import io.github.lucasstarsz.fastj.engine.util.DrawUtil;
import io.github.lucasstarsz.fastj.engine.util.ImageUtil;
import io.github.lucasstarsz.fastj.engine.util.math.Pointf;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * {@code Drawable} subclass for drawing a polygon.
 *
 * @author Andrew Dey
 * @version 0.3.2a
 */
public class Polygon2D extends Drawable {

    private Path2D.Float renderPath;
    private Pointf[] points;

    private Color color;
    private boolean paintFilled;

    private float rotation;
    private Pointf scale;
    private Pointf translation;

    private BufferedImage originalImage, renderImage;
    private boolean hasImage;


    /**
     * {@code Polygon2D} constructor that takes in a set of points.
     * <p>
     * This constructor defaults the color to black, and both the {@code fill} and {@code show} booleans to {@code
     * true}.
     *
     * @param pts {@code Pointf} array that defines the points for the polygon.
     * @see io.github.lucasstarsz.fastj.engine.util.math.Pointf
     */
    public Polygon2D(Pointf[] pts) {
        this(pts, Color.black, true, true);
    }

    /**
     * {@code Polygon2D} constructor that takes in a set of points, a color, a fill variable, and a show variable.
     *
     * @param pts   {@code Pointf} array that defines the points for the polygon.
     * @param color {@code Color} variable that sets the color of the polygon.
     * @param fill  Boolean that determines whether the polygon should be filled, or only outlined.
     * @param show  Boolean that determines whether the polygon should be shown on screen.
     * @see io.github.lucasstarsz.fastj.engine.util.math.Pointf
     * @see Color
     */
    public Polygon2D(Pointf[] pts, Color color, boolean fill, boolean show) {
        super();
        points = pts;

        renderPath = createPath(points);
        setBoundaries(renderPath);
        hasImage = false;

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
     * @see io.github.lucasstarsz.fastj.engine.util.math.Pointf
     * @see Color
     */
    public Polygon2D(Pointf[] pts, Pointf setLocation, float setRotation, Pointf setScale, Color color, boolean fill, boolean show) {
        super();
        points = pts;

        renderPath = createPath(points);
        setBoundaries(renderPath);
        hasImage = false;

        scale = new Pointf(1);
        rotation = 0;
        translation = new Pointf(getBound(Boundary.TOP_LEFT));

        setTranslation(setLocation);
        rotate(setRotation);
        scale(setScale);

        setColor(color);
        setFilled(fill);

        setCollisionPath(renderPath);

        setShouldRender(show);
    }

    /**
     * {@code Polygon2D} constructor that takes in an array of points, a BufferedImage (which will be used as a texture
     * for the polygon), and a show variable.
     *
     * @param pts   {@code Pointf} array that defines the points for the polygon.
     * @param image {@code BufferedImage} to be used as a texture for the polygon.
     * @param show  Boolean that determines whether the polygon should be shown on screen.
     * @see io.github.lucasstarsz.fastj.engine.util.math.Pointf
     * @see BufferedImage
     */
    public Polygon2D(Pointf[] pts, BufferedImage image, boolean show) {
        this(pts, Color.black, true, show);

        originalImage = image;
        renderImage = ImageUtil.copyImage(image);
        hasImage = true;
    }

    /**
     * Gets the rendered {@code Path2D.Float} for this polygon.
     *
     * @return The {@code Path2D.Float} for this polygon.
     * @see Path2D.Float
     */
    public Path2D.Float getRenderPath() {
        return renderPath;
    }

    /**
     * Gets the rendered {@code BufferedImage} for this polygon.
     *
     * @return The {@code BufferedImage} for this polygon.
     * @see BufferedImage
     */
    public BufferedImage getRenderImage() {
        return renderImage;
    }

    /**
     * Gets the original points that were set for this polygon.
     *
     * @return The original set of points for this polygon, as a {@code Pointf[]}.
     * @see io.github.lucasstarsz.fastj.engine.util.math.Pointf
     */
    public Pointf[] getOriginalPoints() {
        return points;
    }

    /**
     * Gets the color set for this polygon.
     *
     * @return The {@code Color} set for this polygon.
     * @see Color
     */
    public Color getColor() {
        return color;
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
     * Gets the {@code Pointf} array associated with the current state of the polygon.
     *
     * @return The {@code Pointf} array associated with the current state of the polygon.
     * @see io.github.lucasstarsz.fastj.engine.util.math.Pointf
     */
    public Pointf[] getPoints() {
        return DrawUtil.pointsOfPath(renderPath);
    }

    /**
     * Gets the variable that determines whether the polygon has a texture.
     *
     * @return The boolean value that determines whether the polygon has a texture.
     */
    public boolean hasImage() {
        return hasImage;
    }

    /**
     * Sets the color for the polygon.
     *
     * @param newColor The {@code Color} to be used for the polygon.
     * @return This instance of the {@code Polygon2D}, for method chaining.
     * @see Color
     */
    public Polygon2D setColor(Color newColor) {
        color = newColor;
        return this;
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
     * Sets the image parameter for the object.
     *
     * @param img {@code BufferedImage} to set as the texture for the polygon.
     * @return This instance of the {@code Polygon2D}, for method chaining.
     * @see BufferedImage
     */
    public Polygon2D setImage(BufferedImage img) {
        originalImage = img;
        renderImage = ImageUtil.copyImage(originalImage);
        hasImage = true;

        return this;
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
     * @see io.github.lucasstarsz.fastj.engine.util.math.Pointf
     */
    public void modifyPoints(Pointf[] pts, boolean resetTranslation, boolean resetRotation, boolean resetScale) {
        points = pts;
        renderPath = createPath(points);

        if (resetTranslation) {
            translation.reset();
        }
        if (resetRotation) {
            rotation = 0;
        }
        if (resetScale) {
            scale.set(1, 1);
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

        renderPath = createPath(renderCopy);

        // TODO: Add working image scaling

        setCollisionPath(renderPath);
        setBoundaries(renderPath);
    }

    @Override
    public void render(Graphics2D g) {
        if (!shouldRender()) return;

        if (hasImage) {
            g.setPaint(new TexturePaint(renderImage, DrawUtil.createRectFromImage(renderImage, translation)));
        } else {
            g.setColor(color);
        }

        if (paintFilled) {
            g.fill(renderPath);
        } else {
            g.draw(renderPath);
        }
    }

    @Override
    public void renderAsGUIObject(Graphics2D g, Camera camera) {
        if (!shouldRender()) {
            return;
        }

        AffineTransform at = new AffineTransform();
        at.translate(-camera.getTranslation().x, -camera.getTranslation().y);

        Path2D.Float renderCopy = (Path2D.Float) renderPath.createTransformedShape(at);

        if (hasImage) {
            g.setPaint(new TexturePaint(renderImage, DrawUtil.createRectFromImage(renderImage, translation)));
        } else {
            g.setColor(color);
        }

        if (paintFilled) {
            g.fill(renderCopy);
        } else {
            g.draw(renderCopy);
        }
    }

    @Override
    public void destroy(Scene originScene) {
        points = null;
        renderPath = null;

        if (hasImage) {
            renderImage.flush();
            originalImage.flush();
        }

        renderImage = null;
        originalImage = null;
        hasImage = false;

        color = null;
        paintFilled = false;

        scale = null;
        translation = null;
        rotation = 0;

        destroyTheRest(originScene);

    }

    /**
     * Creates the {@code Path2D.Float} for the polygon, based on the specified {@code Pointf} array.
     *
     * @param pts The {@code Pointf} array which the {@code Path2D.Float} will be created from.
     * @return The resulting {@code Path2D.Float}.
     */
    private Path2D.Float createPath(Pointf[] pts) {
        Path2D.Float p = new Path2D.Float();

        p.moveTo(pts[0].x, pts[0].y);
        for (int i = 1; i < pts.length; i++) {
            p.lineTo(pts[i].x, pts[i].y);
        }
        p.closePath();

        return p;
    }

    /**
     * Sets the boundaries of the polygon, using the specified {@code Path2D.Float}.
     *
     * @param p The {@code Path2D.Float} which the boundaries will be based off of.
     */
    private void setBoundaries(Path2D.Float p) {
        super.setBounds(DrawUtil.createBox((Rectangle2D.Float) p.getBounds2D()));
    }
}
