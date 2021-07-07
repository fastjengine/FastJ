package tech.fastj.graphics.gradients;

import tech.fastj.math.Maths;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.Boundary;
import tech.fastj.graphics.Drawable;
import tech.fastj.graphics.util.DrawUtil;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.LinearGradientPaint;
import java.awt.RadialGradientPaint;
import java.util.Arrays;
import java.util.Objects;

/** Class to streamline working with the {@link GradientPaint} class (and its implementations) within FastJ. */
public class Gradients {

    /** The maximum amount of colors allowed in a single {@link LinearGradientBuilder} or {@link RadialGradientBuilder}. */
    public static final int MaximumColorCount = 8;

    /** The minimum amount of colors allowed in a single {@link LinearGradientBuilder} or {@link RadialGradientBuilder}. */
    public static final int MinimumColorCount = 2;

    /** The minimum radius allowed in a single {@link RadialGradientBuilder}, including the value of this constant. */
    public static final float MinimumRadius = 0.0f;

    /**
     * Gets a builder instance for creating a {@link LinearGradientPaint}.
     *
     * @param drawable The {@code Drawable} containing the start and endpoints as its boundaries.
     * @param start    The {@code Boundary} from which to start the gradient.
     * @param end      The {@code Boundary} at which to end the gradient.
     * @return A {@link LinearGradientPaint} builder.
     */
    public static LinearGradientBuilder linearGradient(Drawable drawable, Boundary start, Boundary end) {
        return LinearGradientBuilder.builder(drawable, start, end);
    }

    /**
     * Gets a builder instance for creating a {@link LinearGradientPaint}.
     *
     * @param start The {@code Pointf} from which to start the gradient.
     * @param end   The {@code Pointf} at which to end the gradient.
     * @return A {@link LinearGradientPaint} builder.
     */
    public static LinearGradientBuilder linearGradient(Pointf start, Pointf end) {
        return LinearGradientBuilder.builder(start, end);
    }

    /**
     * Gets a builder instance for creating a {@link RadialGradientPaint}.
     *
     * @param drawable The {@code Drawable} defining the centerpoint and radius to build the gradient with.
     * @return A {@link RadialGradientPaint} builder.
     */
    public static RadialGradientBuilder radialGradient(Drawable drawable) {
        return RadialGradientBuilder.builder(drawable);
    }

    /**
     * Gets a builder instance for creating a {@link RadialGradientPaint}.
     *
     * @param center The {@code Drawable} defining the centerpoint for the gradient.
     * @param radius The {@code float} defining the radius for the gradient.
     * @return A {@link RadialGradientPaint} builder.
     */
    public static RadialGradientBuilder radialGradient(Pointf center, float radius) {
        return RadialGradientBuilder.builder(center, radius);
    }

    /**
     * Generates a random linear gradient from the specified {@code drawable} and {@code start}/{@code end} values, with
     * random colors.
     *
     * @param drawable The {@link Drawable} to calculate start/end {@code Pointf}s.
     * @param start    The {@link Boundary} specifying the starting area for the gradient.
     * @param end      The {@link Boundary} specifying the ending area for the gradient.
     * @return The randomly generated {@link LinearGradientPaint}.
     */
    public static LinearGradientPaint randomLinearGradient(Drawable drawable, Boundary start, Boundary end) {
        LinearGradientBuilder linearGradientBuilder = linearGradient(drawable, start, end);

        int randomColorCount = Maths.randomInteger(MinimumColorCount, MaximumColorCount);
        for (int i = 0; i < randomColorCount; i++) {
            linearGradientBuilder.withColor(DrawUtil.randomColor());
        }

        return linearGradientBuilder.build();
    }

    /**
     * Generates a random linear gradient from the specified {@code drawable} and {@code start}/{@code end} values, with
     * random colors including random alpha values.
     *
     * @param drawable The {@link Drawable} to calculate start/end {@code Pointf}s.
     * @param start    The {@link Boundary} specifying the starting area for the gradient.
     * @param end      The {@link Boundary} specifying the ending area for the gradient.
     * @return The randomly generated {@link LinearGradientPaint}.
     */
    public static LinearGradientPaint randomLinearGradientWithAlpha(Drawable drawable, Boundary start, Boundary end) {
        LinearGradientBuilder linearGradientBuilder = linearGradient(drawable, start, end);

        int randomColorCount = Maths.randomInteger(MinimumColorCount, MaximumColorCount);
        for (int i = 0; i < randomColorCount; i++) {
            linearGradientBuilder.withColor(DrawUtil.randomColorWithAlpha());
        }

        return linearGradientBuilder.build();
    }

    /**
     * Generates a random linear gradient from the specified {@code start}/{@code end} values, with random colors.
     *
     * @param start The {@link Pointf} specifying the starting area for the gradient.
     * @param end   The {@link Pointf} specifying the ending area for the gradient.
     * @return The randomly generated {@link LinearGradientPaint}.
     */
    public static LinearGradientPaint randomLinearGradient(Pointf start, Pointf end) {
        LinearGradientBuilder linearGradientBuilder = linearGradient(start, end);

        int randomColorCount = Maths.randomInteger(MinimumColorCount, MaximumColorCount);
        for (int i = 0; i < randomColorCount; i++) {
            linearGradientBuilder.withColor(DrawUtil.randomColor());
        }

        return linearGradientBuilder.build();
    }

    /**
     * Generates a random linear gradient from the specified {@code start}/{@code end} values, with random colors
     * including random alpha values.
     *
     * @param start The {@link Pointf} specifying the starting area for the gradient.
     * @param end   The {@link Pointf} specifying the ending area for the gradient.
     * @return The randomly generated {@link LinearGradientPaint}.
     */
    public static LinearGradientPaint randomLinearGradientWithAlpha(Pointf start, Pointf end) {
        LinearGradientBuilder linearGradientBuilder = linearGradient(start, end);

        int randomColorCount = Maths.randomInteger(MinimumColorCount, MaximumColorCount);
        for (int i = 0; i < randomColorCount; i++) {
            linearGradientBuilder.withColor(DrawUtil.randomColorWithAlpha());
        }

        return linearGradientBuilder.build();
    }

    /**
     * Generates a random radial gradient from the specified {@code drawable}, with random colors.
     *
     * @param drawable The {@link Drawable} specifying the center and radius for the gradient.
     * @return The randomly generated {@link RadialGradientPaint}.
     */
    public static RadialGradientPaint randomRadialGradient(Drawable drawable) {
        RadialGradientBuilder radialGradientBuilder = radialGradient(drawable);

        int randomColorCount = Maths.randomInteger(MinimumColorCount, MaximumColorCount);
        for (int i = 0; i < randomColorCount; i++) {
            radialGradientBuilder.withColor(DrawUtil.randomColor());
        }

        return radialGradientBuilder.build();
    }

    /**
     * Generates a random radial gradient from the specified {@code drawable}, with random colors including random alpha
     * values.
     *
     * @param drawable The {@link Drawable} specifying the center and radius for the gradient.
     * @return The randomly generated {@link RadialGradientPaint}.
     */
    public static RadialGradientPaint randomRadialGradientWithAlpha(Drawable drawable) {
        RadialGradientBuilder radialGradientBuilder = radialGradient(drawable);

        int randomColorCount = Maths.randomInteger(MinimumColorCount, MaximumColorCount);
        for (int i = 0; i < randomColorCount; i++) {
            radialGradientBuilder.withColor(DrawUtil.randomColorWithAlpha());
        }

        return radialGradientBuilder.build();
    }

    /**
     * Generates a random radial gradient from the specified {@code center} and {@code radius} values, with random
     * colors.
     *
     * @param center The {@link Pointf} specifying the centerpoint for the gradient.
     * @param radius The {@code float} specifying the radius for the gradient.
     * @return The randomly generated {@link RadialGradientPaint}.
     */
    public static RadialGradientPaint randomRadialGradient(Pointf center, float radius) {
        RadialGradientBuilder radialGradientBuilder = radialGradient(center, radius);

        int randomColorCount = Maths.randomInteger(MinimumColorCount, MaximumColorCount);
        for (int i = 0; i < randomColorCount; i++) {
            radialGradientBuilder.withColor(DrawUtil.randomColor());
        }

        return radialGradientBuilder.build();
    }

    /**
     * Generates a random radial gradient from the specified {@code center} and {@code radius} values, with random
     * colors including random alpha values.
     *
     * @param center The {@link Pointf} specifying the centerpoint for the gradient.
     * @param radius The {@code float} specifying the radius for the gradient.
     * @return The randomly generated {@link RadialGradientPaint}.
     */
    public static RadialGradientPaint randomRadialGradientWithAlpha(Pointf center, float radius) {
        RadialGradientBuilder radialGradientBuilder = radialGradient(center, radius);

        int randomColorCount = Maths.randomInteger(MinimumColorCount, MaximumColorCount);
        for (int i = 0; i < randomColorCount; i++) {
            radialGradientBuilder.withColor(DrawUtil.randomColorWithAlpha());
        }

        return radialGradientBuilder.build();
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
        if (radius <= MinimumRadius) {
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
        if (count >= MaximumColorCount) {
            throw new IllegalStateException("Gradients cannot contain more than " + MaximumColorCount + " colors.");
        }
    }

    static void colorLimitCheck(int count, Color[] colors) {
        if (count + colors.length > MaximumColorCount) {
            throw new IllegalStateException("Gradients cannot contain more than " + MaximumColorCount + " colors.");
        }
    }

    static void minimumColorCheck(int count, Color[] colors) {
        if (count < MinimumColorCount) {
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
