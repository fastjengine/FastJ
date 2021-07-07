package tech.fastj.graphics.ui.elements;

import tech.fastj.engine.FastJEngine;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.display.Camera;
import tech.fastj.graphics.game.Text2D;
import tech.fastj.graphics.ui.UIElement;
import tech.fastj.graphics.util.DrawUtil;

import tech.fastj.systems.control.Scene;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import tech.fastj.input.mouse.Mouse;
import tech.fastj.input.mouse.MouseAction;
import tech.fastj.input.mouse.MouseButtons;

/**
 * A {@link UIElement} that can be assigned an action on left click.
 *
 * @author Andrew Dey
 * @version 1.0.0
 */
public class Button extends UIElement {

    /** The default location of a {@link Button}: (0, 0). */
    public static final Pointf DefaultLocation = Pointf.Origin.copy();
    /** The default size of a {@link Button}: (100f, 25f). */
    public static final Pointf DefaultSize = new Pointf(100f, 25f);

    private Paint paint;
    private Path2D.Float renderPath;
    private final Pointf location;

    private Font font;
    private String text = "";
    private Rectangle2D.Float textBounds;
    private boolean hasMetrics;

    /**
     * Constructs a button with a default location and size.
     *
     * @param origin The scene to add the button as a gui object to.
     */
    public Button(Scene origin) {
        this(origin, DefaultLocation, DefaultSize);
    }

    /**
     * Constructs a button with the specified location and initial size.
     *
     * @param origin      The scene to add the button as a gui object to.
     * @param location    The location to create the button at.
     * @param initialSize The initial size of the button, though the button will get larger if the text outgrows it.
     */
    public Button(Scene origin, Pointf location, Pointf initialSize) {
        super(origin);
        super.setOnActionCondition(event -> Mouse.interactsWith(Button.this, MouseAction.Press) && Mouse.isMouseButtonPressed(MouseButtons.Left));

        this.location = location;
        Pointf[] buttonCoords = DrawUtil.createBox(this.location, initialSize);

        renderPath = DrawUtil.createPath(buttonCoords);
        super.setCollisionPath(renderPath);

        this.setPaint(Color.cyan);
        this.setFont(Text2D.DefaultFont);

        setMetrics(FastJEngine.getDisplay().getGraphics());
    }

    /**
     * Gets the {@link Paint} object for the button.
     *
     * @return The Button's {@code Paint}.
     */
    public Paint getPaint() {
        return paint;
    }

    /**
     * Sets the {@link Paint} object for the button.
     *
     * @param paint The new paint for the button.
     * @return The {@link Button}, for method chaining.
     */
    public Button setPaint(Paint paint) {
        this.paint = paint;
        return this;
    }

    /**
     * Gets the text for the button.
     *
     * @return The Button's text.
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text for the button.
     *
     * @param text The new text for the button.
     * @return The {@link Button}, for method chaining.
     */
    public Button setText(String text) {
        this.text = text;
        setMetrics(FastJEngine.getDisplay().getGraphics());
        return this;
    }

    /**
     * Gets the {@link Font} object for the button.
     *
     * @return The Button's {@code Font}.
     */
    public Font getFont() {
        return font;
    }

    /**
     * Sets the {@link Font} for the button.
     *
     * @param font The new {@code Font} object for the button.
     * @return The {@link Button}, for method chaining.
     */
    public Button setFont(Font font) {
        this.font = font;
        setMetrics(FastJEngine.getDisplay().getGraphics());
        return this;
    }

    @Override
    public void renderAsGUIObject(Graphics2D g2, Camera camera) {
        Rectangle2D.Float renderCopy = (Rectangle2D.Float) renderPath.getBounds2D();
        renderCopy.x -= camera.getTranslation().x;
        renderCopy.y -= camera.getTranslation().y;

        g2.setPaint(paint);
        g2.fill(renderCopy);
        g2.setPaint(Color.black);
        g2.draw(renderCopy);

        if (!hasMetrics) {
            setMetrics(g2);
        }

        g2.setFont(font);
        g2.drawString(text, textBounds.x, textBounds.y);
    }

    @Override
    public void destroy(Scene originScene) {
        paint = null;
        renderPath = null;
    }

    /**
     * Sets up the necessary boundaries for creating text metrics, and aligns the text with the button.
     * <p>
     * If the text metrics show that the text does not fit in the button, the button will be resized to fit the text.
     *
     * @param g {@code Graphics2D} object that the {@code Text2D} is rendered on.
     */
    private void setMetrics(Graphics2D g) {
        hasMetrics = false;

        FontMetrics fm = g.getFontMetrics(font);

        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        Rectangle2D.Float renderPathBounds = (Rectangle2D.Float) renderPath.getBounds2D();

        textBounds = new Rectangle2D.Float(
                location.x + (renderPathBounds.width - textWidth) / 2f,
                location.y + textHeight,
                textWidth,
                textHeight
        );

        if (renderPathBounds.width < textBounds.width) {
            float diff = (textBounds.width - renderPathBounds.width) / 2f;
            renderPathBounds.width = textBounds.width;
            textBounds.x += diff;
        }

        if (renderPathBounds.height < textBounds.height) {
            renderPathBounds.height = textBounds.height;
        }

        g.dispose();
        hasMetrics = true;
    }
}
