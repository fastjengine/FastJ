package tech.fastj.graphics.game;

import tech.fastj.math.Pointf;
import tech.fastj.graphics.Transform2D;

import java.awt.Font;
import java.awt.Paint;
import java.util.Objects;

/** A builder class for creating {@link Text2D} objects. */
public class Text2DBuilder {

    private final String text;
    private final boolean shouldRender;

    private Paint fillPaint = Text2D.DefaultFill;
    private Font font = Text2D.DefaultFont;

    private Pointf translation = Transform2D.DefaultTranslation.copy();
    private float rotation = Transform2D.DefaultRotation;
    private Pointf scale = Transform2D.DefaultScale.copy();

    /**
     * {@code Text2DBuilder} constructor, taking in a {@code String} of text and a {@code shouldRender} boolean.
     *
     * @param text         The {@code String} of text to use for the resulting {@code Text2D}.
     * @param shouldRender The "should render" {@code boolean} to use for the resulting {@code Text2D}.
     */
    Text2DBuilder(String text, boolean shouldRender) {
        this.text = Objects.requireNonNull(text, "The text must not be null.");
        this.shouldRender = shouldRender;
    }

    /**
     * Sets the builder's fill paint value.
     *
     * @param fillPaint The fill {@code Paint} to be used in the resulting {@code Text2D}.
     * @return The {@code Text2DBuilder}, for method chaining.
     */
    public Text2DBuilder withFill(Paint fillPaint) {
        this.fillPaint = Objects.requireNonNull(fillPaint, "The fill must not be null.");
        return this;
    }

    /**
     * Sets the builder's font paint value.
     *
     * @param font The {@code Font} to be used in the resulting {@code Text2D}.
     * @return The {@code Text2DBuilder}, for method chaining.
     */
    public Text2DBuilder withFont(Font font) {
        this.font = Objects.requireNonNull(font, "The font must not be null.");
        return this;
    }

    /**
     * Sets the builder's transformation (translation, rotation, scale) values.
     *
     * @param translation The translation {@code Pointf} to be used in the resulting {@code Text2D}.
     * @param rotation    The rotation {@code float} to be used in the resulting {@code Text2D}.
     * @param scale       The scale {@code Pointf} to be used int he resulting {@code Text2D}.
     * @return The {@code Text2DBuilder}, for method chaining.
     */
    public Text2DBuilder withTransform(Pointf translation, float rotation, Pointf scale) {
        this.translation = Objects.requireNonNull(translation, "The translation value must not be null.");
        this.scale = Objects.requireNonNull(scale, "The scale value must not be null.");
        if (Float.isNaN(rotation)) {
            throw new NumberFormatException("The rotation value must not be NaN.");
        }
        this.rotation = rotation;
        return this;
    }

    /**
     * Creates a new {@link Text2D} object, using the data provided by earlier method calls.
     *
     * @return The resulting {@code Text2D}.
     */
    public Text2D build() {
        return (Text2D) new Text2D(text)
                .setFill(fillPaint)
                .setFont(font)
                .setShouldRender(shouldRender)
                .setTransform(translation, rotation, scale);
    }
}
