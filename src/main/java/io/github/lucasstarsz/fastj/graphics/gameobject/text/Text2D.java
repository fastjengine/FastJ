package io.github.lucasstarsz.fastj.graphics.gameobject.text;

import io.github.lucasstarsz.fastj.graphics.DrawUtil;
import io.github.lucasstarsz.fastj.graphics.gameobject.GameObject;
import io.github.lucasstarsz.fastj.math.Pointf;
import io.github.lucasstarsz.fastj.systems.control.Scene;

import io.github.lucasstarsz.fastj.engine.CrashMessages;
import io.github.lucasstarsz.fastj.engine.FastJEngine;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

/**
 * {@code Drawable} subclass for drawing text.
 *
 * @author Andrew Dey
 * @version 1.0.0
 */
public class Text2D extends GameObject {

    /** {@link Color} representing the default color value of {@code (0, 0, 0)}. */
    public static final Color DefaultColor = Color.black;
    /** {@link Font} representing the default font of {@code Segoe UI Plain, 12px}. */
    public static final Font DefaultFont = new Font("Segoe UI", Font.PLAIN, 12);
    /** {@code boolean} representing the default "should render" value of {@code true}. */
    public static final boolean DefaultShow = true;

    private String text;
    private Color color;
    private Font font;
    private Pointf translation;
    private boolean hasMetrics;

    /**
     * {@code Text2D} Constructor that takes in a string of text and a location.
     * <p>
     * This constructor defaults the color to {@link #DefaultColor}, the font to {@link #DefaultFont}, and sets the
     * {@code show} boolean to {@link #DefaultShow}.
     *
     * @param setText        Sets the displayed text.
     * @param setTranslation Sets the x and y location of the text.
     */
    public Text2D(String setText, Pointf setTranslation) {
        this(setText, setTranslation, DefaultColor, DefaultFont, DefaultShow);
    }

    /**
     * {@code Text2D} Constructor that takes in a string of text, a location, a color, a font, and a show variable.
     *
     * @param setText        Sets the displayed text.
     * @param setTranslation Sets the x and y location of the text.
     * @param setColor       Sets the text's color.
     * @param setFont        Sets the text's font.
     * @param show           Sets whether the text will be drawn to the screen.
     */
    public Text2D(String setText, Pointf setTranslation, Color setColor, Font setFont, boolean show) {
        translation = new Pointf(setTranslation);

        text = setText;
        font = setFont;

        setColor(setColor);
        setShouldRender(show);

        setMetrics(FastJEngine.getDisplay().getGraphics());
    }

    /**
     * Gets the displayed text of this {@code Text2D}.
     *
     * @return Returns a String that contains the text displayed.
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text for this {@code Text2D}.
     *
     * @param setText The new text value.
     * @return This instance of the {@code Text2D}, for method chaining.
     */
    public Text2D setText(String setText) {
        text = setText;
        setMetrics(FastJEngine.getDisplay().getGraphics());

        return this;
    }

    /**
     * Gets the {@code Color} of this {@code Text2D}.
     *
     * @return Returns the Color value for this Text2D.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the {@code Color} for this {@code Text2D}.
     *
     * @param setColor The new {@code Color} value.
     * @return This instance of the {@code Text2D}, for method chaining.
     */
    public Text2D setColor(Color setColor) {
        color = setColor;
        return this;
    }

    /**
     * Gets the {@code Font} of this {@code Text2D}.
     *
     * @return Returns the specified Font value for this Text2D.
     */
    public Font getFont() {
        return font;
    }

    /**
     * Sets the {@code Font} for this {@code Text2D}.
     *
     * @param setFont The new {@code Font} value.
     * @return This instance of the {@code Text2D}, for method chaining.
     */
    public Text2D setFont(Font setFont) {
        font = setFont;
        setMetrics(FastJEngine.getDisplay().getGraphics());

        return this;
    }

    @Override
    public Pointf getTranslation() {
        return translation;
    }

    @Override
    public Pointf getScale() {
        return new Pointf(1f);
    }

    @Override
    public float getRotation() {
        return 0f;
    }

    @Override
    public void render(Graphics2D g) {
        if (!shouldRender()) return;
        if (!hasMetrics) {
            setMetrics(g);
        }

        g.setFont(font);
        g.setColor(color);

        g.drawString(text, translation.x, translation.y);
    }

    @Override
    public void destroy(Scene originScene) {
        text = null;
        color = null;
        font = null;
        translation = null;

        super.destroyTheRest(originScene);
    }

    @Override
    public void translate(Pointf translationMod) {
        translation.add(translationMod);

        AffineTransform at = AffineTransform.getTranslateInstance(translationMod.x, translationMod.y);
        setCollisionPath(((Path2D.Float) getCollisionPath()).createTransformedShape(at));

        translateBounds(translationMod);
    }

    // TODO Add support for rotation and scaling

    @Override
    public void rotate(float rotationMod, Pointf centerpoint) {
        FastJEngine.error(
                CrashMessages.UNIMPLEMENTED_METHOD_ERROR.errorMessage,
                new UnsupportedOperationException(
                        "Text2D does not have any implementation for rotation as of yet."
                                + System.lineSeparator()
                                + "Check the github to confirm you are on the latest version, as that version may have more implemented features."
                )
        );
    }

    @Override
    public void scale(Pointf scaleMod, Pointf centerpoint) {
        FastJEngine.error(
                CrashMessages.UNIMPLEMENTED_METHOD_ERROR.errorMessage,
                new UnsupportedOperationException(
                        "Text2D does not have any implementation for scaling as of yet."
                                + System.lineSeparator()
                                + "As an alternative, you should increase the font size of the current Font for the object."
                                + System.lineSeparator()
                                + "Check the github to confirm you are on the latest version, as that version may have more implemented features."
                )
        );
    }

    /**
     * Sets up the necessary boundaries for creating the metrics for this {@code Text2D}.
     * <p>
     * This also sets the resulting metrics as the collision path for this {@code Text2D}.
     *
     * @param g {@code Graphics2D} object that the {@code Text2D} is rendered on.
     */
    private void setMetrics(Graphics2D g) {
        hasMetrics = false;

        FontMetrics fm = g.getFontMetrics(font);
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();

        final Rectangle2D.Float bounds = new Rectangle2D.Float(translation.x, translation.y, textWidth, textHeight);
        setBounds(DrawUtil.createBox(bounds));

        setCollisionPath(createMetricsPath(bounds));

        g.dispose();
        hasMetrics = true;
    }

    /**
     * Gets a {@code Path2D.Float} that is based on the parameter {@code Rectangle2D.Float}.
     *
     * @param rect The rectangle which the result {@code Path2D.Float} is based on.
     * @return The newly created {@code Path2D.Float}.
     */
    private Path2D.Float createMetricsPath(Rectangle2D.Float rect) {
        Path2D.Float result = new Path2D.Float();

        result.moveTo(rect.x, rect.y);
        result.lineTo(rect.x + rect.width, rect.y);
        result.lineTo(rect.x + rect.width, rect.y + rect.height);
        result.lineTo(rect.x, rect.y + rect.height);
        result.closePath();

        return result;
    }
}
