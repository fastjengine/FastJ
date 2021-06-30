package tech.fastj.graphics.game;

import tech.fastj.engine.FastJEngine;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.Drawable;

import tech.fastj.systems.control.Scene;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

/**
 * {@code Drawable} subclass for drawing text.
 *
 * @author Andrew Dey
 * @version 1.0.0
 */
public class Text2D extends GameObject {

    /** {@link Paint} representing the default color value of {@code (0, 0, 0)}. */
    public static final Paint DefaultPaint = Color.black;
    /** {@link Font} representing the default font of {@code Segoe UI Plain, 12px}. */
    public static final Font DefaultFont = new Font("Segoe UI", Font.PLAIN, 12);

    private String text;
    private Paint color;
    private Font font;
    private boolean hasMetrics;

    /**
     * {@code Text2D} Constructor that takes in a string of text.
     * <p>
     * This constructor defaults the color to {@link #DefaultPaint}, the font to {@link #DefaultFont}, and sets the
     * {@code show} boolean to {@link Drawable#DefaultShouldRender}.
     *
     * @param setText Sets the displayed text.
     */
    public Text2D(String setText) {
        this(setText, DefaultPaint, DefaultFont, Drawable.DefaultShouldRender);
    }

    /**
     * {@code Text2D} Constructor that takes in a string of text and an initial translation.
     * <p>
     * This constructor defaults the color to {@link #DefaultPaint}, the font to {@link #DefaultFont}, and sets the
     * {@code shouldRender} boolean to {@link Drawable#DefaultShouldRender}.
     *
     * @param setText        Sets the displayed text.
     * @param setTranslation Sets the initial x and y translation of the text.
     */
    public Text2D(String setText, Pointf setTranslation) {
        this(setText, DefaultPaint, DefaultFont, Drawable.DefaultShouldRender);
        setTranslation(setTranslation);
    }

    /**
     * {@code Text2D} Constructor that takes in a string of text, a color, a font, and a shouldRender variable.
     *
     * @param setText      Sets the displayed text.
     * @param setPaint     Sets the text's color.
     * @param setFont      Sets the text's font.
     * @param shouldRender Sets whether the text will be drawn to the screen.
     */
    public Text2D(String setText, Paint setPaint, Font setFont, boolean shouldRender) {
        text = setText;
        font = setFont;

        setPaint(setPaint);
        setFont(setFont);
        setShouldRender(shouldRender);
    }

    /**
     * {@code Text2D} Constructor that takes in a string of text, a translation, a color, a font, and a shouldRender
     * variable.
     *
     * @param setText        Sets the displayed text.
     * @param setTranslation Sets the initial x and y translation of the text.
     * @param setPaint       Sets the text's color.
     * @param setFont        Sets the text's font.
     * @param shouldRender   Sets whether the text will be drawn to the screen.
     */
    public Text2D(String setText, Pointf setTranslation, Paint setPaint, Font setFont, boolean shouldRender) {
        this(setText, setPaint, setFont, shouldRender);
        setTranslation(setTranslation);
    }

    /**
     * {@code Text2D} Constructor that takes in a string of text, a translation/rotation/scale, a color, a font, and a
     * shouldRender variable.
     *
     * @param setText        Sets the displayed text.
     * @param setTranslation Sets the initial x and y translation of the text.
     * @param setRotation    Sets the initial rotation of the text.
     * @param setScale       Sets the initial scale of the text.
     * @param setPaint       Sets the text's color.
     * @param setFont        Sets the text's font.
     * @param shouldRender   Sets whether the text will be drawn to the screen.
     */
    public Text2D(String setText, Pointf setTranslation, float setRotation, Pointf setScale, Paint setPaint, Font setFont, boolean shouldRender) {
        text = setText;
        font = setFont;

        setTranslation(setTranslation);
        setRotation(setRotation);
        setScale(setScale);

        setPaint(setPaint);
        setFont(setFont);
        setShouldRender(shouldRender);
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
     * Gets the {@code Paint} of this {@code Text2D}.
     *
     * @return Returns the Paint value for this Text2D.
     */
    public Paint getPaint() {
        return color;
    }

    /**
     * Sets the {@code Paint} for this {@code Text2D}.
     *
     * @param setPaint The new {@code Paint} value.
     * @return This instance of the {@code Text2D}, for method chaining.
     */
    public Text2D setPaint(Paint setPaint) {
        color = setPaint;
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
    public void render(Graphics2D g) {
        if (!shouldRender()) {
            return;
        }

        if (!hasMetrics) {
            setMetrics(g);
        }

        AffineTransform oldTransform = (AffineTransform) g.getTransform().clone();
        Font oldFont = g.getFont();
        Paint oldPaint = g.getPaint();

        g.transform(getTransformation());
        g.setFont(font);
        g.setPaint(color);

        g.drawString(text, Pointf.Origin.x, font.getSize2D());

        g.setTransform(oldTransform);
        g.setFont(oldFont);
        g.setPaint(oldPaint);
    }

    @Override
    public void destroy(Scene originScene) {
        text = null;
        color = null;
        font = null;
        hasMetrics = false;

        super.destroyTheRest(originScene);
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

        Pointf translation = getTranslation();
        final Rectangle2D.Float bounds = new Rectangle2D.Float(translation.x, translation.y, textWidth, textHeight);

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Text2D text2D = (Text2D) o;
        return Objects.equals(text, text2D.text)
                && Objects.equals(color, text2D.color)
                && Objects.equals(font, text2D.font);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), text, color, font, hasMetrics);
    }
}
