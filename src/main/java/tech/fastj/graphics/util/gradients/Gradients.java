package tech.fastj.graphics.util.gradients;

import tech.fastj.math.Pointf;
import tech.fastj.graphics.Boundary;
import tech.fastj.graphics.Drawable;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.LinearGradientPaint;
import java.awt.RadialGradientPaint;
import java.util.Arrays;
import java.util.Objects;

/** Class to streamline working with the {@link GradientPaint} class (and its implementations) within FastJ. */
public class Gradients {

    /** The amount of colors allowed in a single {@link LinearGradientBuilder} or {@link RadialGradientBuilder}. */
    public static final int ColorLimit = 8;

    /**
     * Gets a builder instance for creating a {@link LinearGradientPaint}.
     *
     * @return A {@link LinearGradientPaint} builder.
     */
    public static LinearGradientBuilder linearGradient(Drawable drawable, Boundary start, Boundary end) {
        return LinearGradientBuilder.builder(drawable, start, end);
    }

    /**
     * Gets a builder instance for creating a {@link LinearGradientPaint}.
     *
     * @return A {@link LinearGradientPaint} builder.
     */
    public static LinearGradientBuilder linearGradient(Pointf start, Pointf end) {
        return LinearGradientBuilder.builder(start, end);
    }

    /**
     * Gets a builder instance for creating a {@link RadialGradientPaint}.
     *
     * @return A {@link RadialGradientPaint} builder.
     */
    public static RadialGradientBuilder radialGradient(Drawable drawable) {
        return RadialGradientBuilder.builder(drawable);
    }

    /**
     * Gets a builder instance for creating a {@link RadialGradientPaint}.
     *
     * @return A {@link RadialGradientPaint} builder.
     */
    public static RadialGradientBuilder radialGradient(Pointf center, float radius) {
        return RadialGradientBuilder.builder(center, radius);
    }

    static void samePointfCheck(Pointf start, Pointf end) {
        if (start.equals(end)) {
            throw new IllegalArgumentException(
                    "The starting and ending positions for a gradient must not be the same."
                            + System.lineSeparator()
                            + "Both positions evaluated to: " + start
            );
        }
    }

    static void sameBoundaryCheck(Boundary start, Boundary end) {
        if (start.equals(end)) {
            throw new IllegalArgumentException(
                    "The starting and ending positions for a gradient must not be the same."
                            + System.lineSeparator()
                            + "Both positions evaluated to: " + start.name()
            );
        }
    }

    static void minimumRadiusValueCheck(float radius) {
        if (radius <= 0f) {
            throw new IllegalArgumentException("The radius for a gradient must be larger than 0.");
        }
    }

    static void nullColorCheck(Color color) {
        if (color == null) {
            throw new IllegalArgumentException("Gradients cannot contain null color values.");
        }
    }

    static void nullColorCheck(Color[] colors) {
        if (Arrays.stream(colors).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Gradients cannot contain null color values.");
        }
    }

    static void colorLimitCheck(int count) {
        if (count >= Gradients.ColorLimit) {
            throw new IllegalStateException("Gradients cannot contain more than " + Gradients.ColorLimit + " colors.");
        }
    }

    static void colorLimitCheck(int count, Color[] colors) {
        if (count + colors.length > Gradients.ColorLimit) {
            throw new IllegalStateException("Gradients cannot contain more than " + Gradients.ColorLimit + " colors.");
        }
    }

    static void minimumColorCheck(int count, Color[] colors) {
        if (count < 2) {
            throw new IllegalStateException(
                    "Gradients must contain at least 2 colors."
                            + System.lineSeparator()
                            + "This gradient builder only contains the following gradients: " + Arrays.toString(Arrays.stream(colors).filter(Objects::nonNull).toArray())
            );
        }
    }

    /**
     * Generates an array of linear intervals from {@code 0.0f} to {@code 1.0} inclusive, based on the size provided.
     *
     * @param size The size of the generated array.
     * @return The generated array.
     */
    static float[] generateIntervals(int size) {
        float[] intervals = new float[size];

        for (int i = 0; i < intervals.length; i++) {
            intervals[i] = (1f / (intervals.length - 1)) * (float) (i);
        }
        return intervals;
    }
}
