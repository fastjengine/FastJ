package tech.fastj.graphics.game;

import tech.fastj.math.Pointf;
import tech.fastj.graphics.RenderStyle;
import tech.fastj.graphics.Transform2D;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.util.Objects;

/** A builder class for creating {@link Polygon2D} objects. */
public class Polygon2DBuilder {

    private final Pointf[] points;
    private final boolean shouldRender;
    private final RenderStyle renderStyle;

    private Paint fillPaint = Polygon2D.DefaultFill;
    private BasicStroke outlineStroke = Polygon2D.DefaultOutlineStroke;
    private Color outlineColor = Polygon2D.DefaultOutlineColor;

    private Pointf translation = Transform2D.DefaultTranslation.copy();
    private float rotation = Transform2D.DefaultRotation;
    private Pointf scale = Transform2D.DefaultScale.copy();

    /**
     * {@code Polygon2DBuilder} constructor, taking in a set of points, a render style, and a {@code shouldRender}
     * boolean.
     *
     * @param points       The {@code Pointf} array of mesh points to use for the resulting {@code Polygon2D}.
     * @param renderStyle  The {@code RenderStyle} to use for the resulting {@code Polygon2D}.
     * @param shouldRender The "should render" {@code boolean} to use for the resulting {@code Polygon2D}.
     */
    Polygon2DBuilder(Pointf[] points, RenderStyle renderStyle, boolean shouldRender) {
        this.points = Objects.requireNonNull(points, "The array of points must not be null.");
        this.renderStyle = Objects.requireNonNull(renderStyle, "The render style must not be null.");
        this.shouldRender = shouldRender;
    }

    /**
     * Sets the builder's fill paint value.
     *
     * @param fillPaint The fill {@code Paint} to be used in the resulting {@code Polygon2D}.
     * @return The {@code Polygon2DBuilder}, for method chaining.
     */
    public Polygon2DBuilder withFill(Paint fillPaint) {
        this.fillPaint = Objects.requireNonNull(fillPaint, "The fill must not be null.");
        return this;
    }

    /**
     * Sets the builder's outline stroke and outline color values.
     *
     * @param outlineStroke The outline {@code BasicStroke} to be used in the resulting {@code Polygon2D}.
     * @param outlineColor  The outline {@code Color} to be used in the resulting {@code Polygon2D}.
     * @return The {@code Polygon2DBuilder}, for method chaining.
     */
    public Polygon2DBuilder withOutline(BasicStroke outlineStroke, Color outlineColor) {
        this.outlineStroke = Objects.requireNonNull(outlineStroke, "The outline stroke must not be null.");
        this.outlineColor = Objects.requireNonNull(outlineColor, "The outline color must not be null.");
        return this;
    }

    /**
     * Sets the builder's transformation (translation, rotation, scale) values.
     *
     * @param translation The translation {@code Pointf} to be used in the resulting {@code Polygon2D}.
     * @param rotation    The rotation {@code float} to be used in the resulting {@code Polygon2D}.
     * @param scale       The scale {@code Pointf} to be used int he resulting {@code Polygon2D}.
     * @return The {@code Polygon2DBuilder}, for method chaining.
     */
    public Polygon2DBuilder withTransform(Pointf translation, float rotation, Pointf scale) {
        this.translation = Objects.requireNonNull(translation, "The translation value must not be null.");
        this.scale = Objects.requireNonNull(scale, "The scale value must not be null.");
        if (Float.isNaN(rotation)) {
            throw new NumberFormatException("The rotation value must not be NaN.");
        }
        this.rotation = rotation;
        return this;
    }

    /**
     * Creates a new {@link Polygon2D} object, using the data provided by earlier method calls.
     *
     * @return The resulting {@code Polygon2D}.
     */
    public Polygon2D build() {
        return (Polygon2D) new Polygon2D(points)
                .setOutlineStroke(outlineStroke)
                .setOutlineColor(outlineColor)
                .setRenderStyle(renderStyle)
                .setFill(fillPaint)
                .setShouldRender(shouldRender)
                .setTransform(translation, rotation, scale);
    }
}
