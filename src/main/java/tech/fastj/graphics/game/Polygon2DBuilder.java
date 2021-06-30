package tech.fastj.graphics.game;

import tech.fastj.math.Pointf;
import tech.fastj.graphics.RenderStyle;
import tech.fastj.graphics.Transform2D;

import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;
import java.util.Objects;

public class Polygon2DBuilder {

    private final Pointf[] points;
    private final boolean shouldRender;
    private final RenderStyle renderStyle;

    private Paint fillPaint = Polygon2D.DefaultPaint;
    private Stroke outlineStroke = Polygon2D.DefaultOutlineStroke;
    private Color outlineColor = Polygon2D.DefaultOutlineColor;

    private Pointf translation = Transform2D.DefaultTranslation.copy();
    private float rotation = Transform2D.DefaultRotation;
    private Pointf scale = Transform2D.DefaultScale.copy();

    Polygon2DBuilder(Pointf[] points, RenderStyle renderStyle, boolean shouldRender) {
        this.points = Objects.requireNonNull(points, "The array of points must not be null.");
        this.renderStyle = Objects.requireNonNull(renderStyle, "The render style must not be null.");
        this.shouldRender = shouldRender;
    }

    public Polygon2DBuilder withFill(Paint fillPaint) {
        this.fillPaint = Objects.requireNonNull(fillPaint, "The fill must not be null.");
        return this;
    }

    public Polygon2DBuilder withOutline(Stroke outlineStroke, Color outlineColor) {
        this.outlineStroke = Objects.requireNonNull(outlineStroke, "The outline stroke must not be null.");
        this.outlineColor = Objects.requireNonNull(outlineColor, "The outline color must not be null.");
        return this;
    }

    public Polygon2DBuilder withTransform(Pointf translation, float rotation, Pointf scale) {
        this.translation = Objects.requireNonNull(translation, "The translation value must not be null.");
        this.scale = Objects.requireNonNull(scale, "The scale value must not be null.");
        if (Float.isNaN(rotation)) {
            throw new NumberFormatException("The rotation value must not be NaN.");
        }
        this.rotation = rotation;
        return this;
    }

    public Polygon2D build() {
        Polygon2D polygon2D = (Polygon2D) new Polygon2D(points)
                .setRenderStyle(renderStyle)
                .setShouldRender(shouldRender)
                .setTransform(translation, rotation, scale);

        switch (renderStyle) {
            case Fill: {
                return polygon2D.setFill(fillPaint);
            }
            case Outline: {
                return polygon2D.setOutline(outlineStroke, outlineColor);
            }
            case FillAndOutline: {
                return polygon2D.setFill(fillPaint).setOutline(outlineStroke, outlineColor);
            }
            default: {
                throw new IllegalStateException("Invalid render style: " + renderStyle.name());
            }
        }
    }
}
