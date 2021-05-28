package tech.fastj.graphics.util;

import tech.fastj.engine.FastJEngine;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.Boundary;
import tech.fastj.graphics.Drawable;

import java.awt.Color;
import java.awt.LinearGradientPaint;

/** A builder class for creating {@link LinearGradientPaint} objects. */
public class LinearGradientBuilder implements GradientBuilder {

    private Pointf from;
    private Pointf to;

    private final Color[] colors;
    private int count;

    /** Initializes a {@code LinearGradientBuilder}'s internals. */
    LinearGradientBuilder() {
        colors = new Color[Gradients.ColorLimit];
    }

    /**
     * Sets the starting and ending points for the builder, based on the provided {@link Drawable} and {@link Boundary}
     * values.
     *
     * @param drawable The basis for what the {@code start} and {@code end} end positions evaluate to.
     * @param start    The starting boundary to create the gradient from.
     * @param end      The ending boundary to create the gradient from.
     * @return The {@code LinearGradientBuilder}, for method chaining.
     */
    public LinearGradientBuilder position(Drawable drawable, Boundary start, Boundary end) {
        if (start.equals(end)) {
            FastJEngine.error(
                    GradientBuilder.GradientCreationError,
                    new IllegalStateException(
                            "The starting and ending positions for a gradient must not be the same."
                                    + System.lineSeparator()
                                    + "Both positions evaluated to: " + start.name()
                    )
            );
        }

        this.from = drawable.getBound(start);
        this.to = drawable.getBound(end);

        return this;
    }

    /**
     * Sets the starting and ending points for the builder, based on the provided {@link Pointf} values.
     *
     * @param start The {@code Pointf} defining the starting point of the gradient.
     * @param end   The {@code Pointf} defining the ending point of the gradient.
     * @return The {@code LinearGradientBuilder}, for method chaining.
     */
    public LinearGradientBuilder position(Pointf start, Pointf end) {
        if (start.equals(end)) {
            FastJEngine.error(
                    GradientBuilder.GradientCreationError,
                    new IllegalStateException(
                            "The starting and ending positions for a gradient must not be the same."
                                    + System.lineSeparator()
                                    + "Both positions evaluated to: " + start
                    )
            );
        }

        this.from = start;
        this.to = end;

        return this;
    }

    /**
     * Adds a {@link Color} to the builder to be used in the resulting gradient.
     * <p>
     * The amount of colors used in a {@link LinearGradientBuilder} not exceed {@link Gradients#ColorLimit}.
     *
     * @param color The {@code Color} being added.
     * @return The {@code LinearGradientBuilder}, for method chaining.
     */
    public LinearGradientBuilder withColor(Color color) {
        if (count == Gradients.ColorLimit) {
            FastJEngine.error(GradientBuilder.GradientCreationError,
                    new IllegalStateException("Gradients cannot contain more than " + Gradients.ColorLimit + " colors.")
            );
        }

        colors[count] = color;
        count++;
        return this;
    }

    /**
     * Creates a new {@link LinearGradientPaint} object, using the data provided by other method calls.
     *
     * @return The resulting {@code LinearGradientPaint}.
     */
    @Override
    public LinearGradientPaint build() {
        float[] fractions = generateIntervals(colors.length);
        return new LinearGradientPaint(from.x, from.y, to.x, to.y, fractions, colors);
    }

    /**
     * Gets a new instance of a {@link LinearGradientBuilder}.
     *
     * @return The {@code LinearGradientBuilder} instance.
     */
    static LinearGradientBuilder builder() {
        return new LinearGradientBuilder();
    }
}
