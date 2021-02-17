package io.github.lucasstarsz.fastj.framework.graphics.text;

import io.github.lucasstarsz.fastj.framework.CrashMessages;
import io.github.lucasstarsz.fastj.framework.graphics.Camera;
import io.github.lucasstarsz.fastj.framework.graphics.Drawable;
import io.github.lucasstarsz.fastj.framework.graphics.util.DrawUtil;
import io.github.lucasstarsz.fastj.framework.math.Pointf;
import io.github.lucasstarsz.fastj.framework.systems.game.Scene;

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
 * @see Drawable
 * @since 1.0.0
 */
public class Text2D extends Drawable {

    /** The default font used for drawing {@code Text2D} objects. */
    public static final Font DEFAULT_FONT = new Font("Segoe UI", Font.PLAIN, 12);

    private String text;
    private Color color;
    private Font font;
    private Pointf translation;
    private boolean hasMetrics;

    /**
     * {@code Text2D} Constructor.
     * <p>
     * This constructor only requires the text, and the location. It sets the color to black, the font to {@code
     * Text2D.DEFAULT_FONT}, and sets the {@code show} boolean to true.
     *
     * @param setText        Sets the displayed text.
     * @param setTranslation Sets the x and y location of the text.
     * @see io.github.lucasstarsz.fastj.framework.math.Pointf
     */
    public Text2D(String setText, Pointf setTranslation) {
        this(setText, setTranslation, Color.black, DEFAULT_FONT, true);
    }

    /**
     * {@code Text2D} Constructor.
     *
     * @param setText        Sets the displayed text.
     * @param setTranslation Sets the x and y location of the text.
     * @param setColor       Sets the text's color.
     * @param setFont        Sets the text's font.
     * @param show           Sets whether the text will be drawn to the screen.
     * @see io.github.lucasstarsz.fastj.framework.math.Pointf
     * @see Color
     * @see Font
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
     * Gets the {@code Color} of this {@code Text2D}.
     *
     * @return Returns the Color value for this Text2D.
     * @see Color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Gets the {@code Font} of this {@code Text2D}.
     *
     * @return Returns the specified Font value for this Text2D.
     * @see Font
     */
    public Font getFont() {
        return font;
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
     * Sets the {@code Color} for this {@code Text2D}.
     *
     * @param setColor The new {@code Color} value.
     * @return This instance of the {@code Text2D}, for method chaining.
     * @see Color
     */
    public Text2D setColor(Color setColor) {
        color = setColor;
        return this;
    }

    /**
     * Sets the {@code Font} for this {@code Text2D}.
     *
     * @param setFont The new {@code Font} value.
     * @return This instance of the {@code Text2D}, for method chaining.
     * @see Font
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
    public void renderAsGUIObject(Graphics2D g, Camera camera) {
        if (!shouldRender()) return;
        if (!hasMetrics) {
            setMetrics(g);
        }

        g.setFont(font);
        g.setColor(color);

        g.drawString(text, -camera.getTranslation().x + translation.x, -camera.getTranslation().y + translation.y);
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
        setCollisionPath((Path2D.Float) getCollisionPath().createTransformedShape(at));

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
     * @see Graphics2D
     */
    private void setMetrics(Graphics2D g) {
        hasMetrics = false;

        FontMetrics fm = g.getFontMetrics(font);
        int textHeight = fm.getHeight();
        int textAdvance = fm.stringWidth(text);

        final Rectangle2D.Float bounds = new Rectangle2D.Float(translation.x, translation.y, textHeight, textAdvance);
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
     * @see Rectangle2D.Float
     * @see Path2D.Float
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
