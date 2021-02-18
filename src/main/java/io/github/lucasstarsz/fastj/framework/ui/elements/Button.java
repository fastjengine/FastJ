package io.github.lucasstarsz.fastj.framework.ui.elements;

import io.github.lucasstarsz.fastj.framework.graphics.text.Text2D;
import io.github.lucasstarsz.fastj.framework.io.mouse.Mouse;
import io.github.lucasstarsz.fastj.framework.io.mouse.MouseAction;
import io.github.lucasstarsz.fastj.framework.io.mouse.MouseButtons;
import io.github.lucasstarsz.fastj.framework.math.Pointf;
import io.github.lucasstarsz.fastj.framework.render.Camera;
import io.github.lucasstarsz.fastj.framework.render.util.DrawUtil;
import io.github.lucasstarsz.fastj.framework.systems.game.Scene;
import io.github.lucasstarsz.fastj.framework.ui.UIElement;

import io.github.lucasstarsz.fastj.engine.FastJEngine;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * A {@link UIElement} that can be assigned an action on left click.
 */
public class Button extends UIElement {

    /** The default location of a {@link Button}: (0, 0). */
    public static final Pointf DEFAULT_LOCATION = new Pointf();
    /** The default size of a {@link Button}: (100f, 25f). */
    public static final Pointf DEFAULT_SIZE = new Pointf(100f, 25f);

    private Paint paint;
    private Rectangle2D.Float renderPath;
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
        this(origin, DEFAULT_LOCATION, DEFAULT_SIZE);
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
        super.setOnActionCondition(event -> Mouse.interactsWith(Button.this, MouseAction.PRESS) && Mouse.isMouseButtonPressed(MouseButtons.LEFT));

        this.location = location;
        Pointf[] buttonCoords = DrawUtil.createBox(this.location, initialSize);
        super.setBounds(buttonCoords);

        renderPath = DrawUtil.createRect(buttonCoords);
        super.setCollisionPath(renderPath);

        this.setPaint(Color.cyan);
        this.setFont(Text2D.DEFAULT_FONT);

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
        Rectangle2D.Float renderCopy = (Rectangle2D.Float) renderPath.clone();
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

        textBounds = new Rectangle2D.Float(
                location.x + (renderPath.width - textWidth) / 2f,
                location.y + textHeight,
                textWidth,
                textHeight
        );

        if (renderPath.width < textBounds.width) {
            float diff = (textBounds.width - renderPath.width) / 2f;
            renderPath.width = textBounds.width;
            textBounds.x += diff;
        }

        if (renderPath.height < textBounds.height) {
            renderPath.height = textBounds.height;
        }

        g.dispose();
        hasMetrics = true;
    }
}
