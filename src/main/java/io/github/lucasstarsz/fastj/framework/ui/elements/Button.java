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

public class Button extends UIElement {

    private Paint paint;
    private Rectangle2D.Float renderPath;
    private final Pointf translation;

    private Font font;
    private String text = "";
    private Rectangle2D.Float textBounds;
    private boolean hasMetrics;

    public Button(Scene origin) {
        this(origin, new Pointf(), new Pointf(100, 25));
    }

    public Button(Scene origin, Pointf translation, Pointf initialSize) {
        super(origin);
        super.setOnActionCondition(event -> Mouse.interactsWith(Button.this, MouseAction.PRESS) && Mouse.isMouseButtonPressed(MouseButtons.LEFT));

        this.translation = translation;
        Pointf[] buttonCoords = DrawUtil.createBox(this.translation, initialSize);
        renderPath = DrawUtil.createRect(buttonCoords);
        super.setCollisionPath(renderPath);
        super.setBounds(buttonCoords);

        this.setPaint(Color.cyan);
        this.setFont(Text2D.DEFAULT_FONT);

        setMetrics(FastJEngine.getDisplay().getGraphics());

        origin.addGUIObject(this);
    }

    public Paint getPaint() {
        return paint;
    }

    public Button setPaint(Paint paint) {
        this.paint = paint;
        return this;
    }

    public String getText() {
        return text;
    }

    public Button setText(String text) {
        this.text = text;
        setMetrics(FastJEngine.getDisplay().getGraphics());
        return this;
    }

    public Font getFont() {
        return font;
    }

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

        textBounds = new Rectangle2D.Float(
                translation.x + (renderPath.width - textWidth) / 2f,
                translation.y + textHeight,
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
