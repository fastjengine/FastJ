package tech.fastj.graphics.util.gradients;

import tech.fastj.math.Pointf;
import tech.fastj.graphics.Boundary;
import tech.fastj.graphics.Drawable;

import java.awt.Color;
import java.awt.LinearGradientPaint;
import java.util.Arrays;
import java.util.Objects;

/** A builder class for creating {@link LinearGradientPaint} objects. */
public class LinearGradientBuilder implements GradientBuilder {

    private Pointf from;
    private Pointf to;

    private final Color[] colors;
    private int count;

    /** Initializes a {@code LinearGradientBuilder}'s internals. */
    LinearGradientBuilder(Drawable drawable, Boundary start, Boundary end) {
        colors = new Color[Gradients.ColorLimit];
        position(drawable, start, end);
    }

    /** Initializes a {@code LinearGradientBuilder}'s internals. */
    LinearGradientBuilder(Pointf start, Pointf end) {
        colors = new Color[Gradients.ColorLimit];
        position(start, end);
    }

    /**
     * Sets the starting and ending points for the builder, based on the provided {@link Drawable} and {@link Boundary}
     * values.
     *
     * @param drawable The basis for what the {@code start} and {@code end} end positions evaluate to.
     * @param start    The starting boundary to create the gradient from.
     * @param end      The ending boundary to create the gradient from.
     */
    private void position(Drawable drawable, Boundary start, Boundary end) {
        if (start.equals(end)) {
            throw new IllegalArgumentException(
                    "The starting and ending positions for a gradient must not be the same."
                            + System.lineSeparator()
                            + "Both positions evaluated to: " + start.name()
            );
        }

        this.from = drawable.getBound(start);
        this.to = drawable.getBound(end);
    }

    /**
     * Sets the starting and ending points for the builder, based on the provided {@link Pointf} values.
     *
     * @param start The {@code Pointf} defining the starting point of the gradient.
     * @param end   The {@code Pointf} defining the ending point of the gradient.
     */
    private void position(Pointf start, Pointf end) {
        if (start.equals(end)) {
            throw new IllegalArgumentException(
                    "The starting and ending positions for a gradient must not be the same."
                            + System.lineSeparator()
                            + "Both positions evaluated to: " + start
            );
        }

        this.from = start;
        this.to = end;
    }

    /**
     * Adds a {@link Color} to the builder to be used in the resulting gradient.
     * <p>
     * The amount of colors used in a {@link LinearGradientBuilder} cannot exceed {@link Gradients#ColorLimit}.
     *
     * @param color The {@code Color} being added.
     * @return The {@code LinearGradientBuilder}, for method chaining.
     */
    public LinearGradientBuilder withColor(Color color) {
        if (count >= Gradients.ColorLimit) {
            throw new IllegalStateException("Gradients cannot contain more than " + Gradients.ColorLimit + " colors.");
        }

        if (color == null) {
            throw new IllegalArgumentException("Gradients cannot contain null color values.");
        }

        colors[count] = color;
        count++;
        return this;
    }

    /**
     * Adds several {@link Color}s to the builder to be used in the resulting gradient.
     * <p>
     * The amount of colors used in a {@link LinearGradientBuilder} cannot exceed {@link Gradients#ColorLimit}.
     *
     * @param colors The {@code Color}s being added. This parameter must not cause the builder to increase over 8
     *               colors.
     * @return The {@code LinearGradientBuilder}, for method chaining.
     */
    public LinearGradientBuilder withColors(Color... colors) {
        if (count + colors.length > Gradients.ColorLimit) {
            throw new IllegalStateException("Gradients cannot contain more than " + Gradients.ColorLimit + " colors.");
        }

        if (Arrays.stream(colors).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Gradients cannot contain null color values.");
        }

        System.arraycopy(colors, count, this.colors, 0, count + colors.length);
        count += colors.length;
        return this;
    }

    /**
     * Creates a new {@link LinearGradientPaint} object, using the data provided by other method calls.
     *
     * @return The resulting {@code LinearGradientPaint}.
     */
    @Override
    public LinearGradientPaint build() {
        if (count < 2) {
            throw new IllegalStateException(
                    "Gradients must contain at least 2 colors."
                            + System.lineSeparator()
                            + "This gradient builder only contains the following gradients: " + Arrays.toString(Arrays.stream(colors).filter(Objects::nonNull).toArray())
            );
        }

        float[] fractions = generateIntervals(count);
        return new LinearGradientPaint(from.x, from.y, to.x, to.y, Arrays.copyOf(fractions, count), Arrays.copyOf(colors, count));
    }

    /**
     * Gets a new instance of a {@link LinearGradientBuilder}.
     *
     * @return The {@code LinearGradientBuilder} instance.
     */
    static LinearGradientBuilder builder(Pointf start, Pointf end) {
        return new LinearGradientBuilder(start, end);
    }

    /**
     * Gets a new instance of a {@link LinearGradientBuilder}.
     *
     * @return The {@code LinearGradientBuilder} instance.
     */
    static LinearGradientBuilder builder(Drawable drawable, Boundary start, Boundary end) {
        return new LinearGradientBuilder(drawable, start, end);
    }
}
