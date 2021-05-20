package io.github.lucasstarsz.fastj.math;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Secondary mathematics class to provide useful utility methods dealing primarily with {@code float}s.
 *
 * @author Andrew Dey
 * @version 1.0.0
 */
public class Maths {

    /** The desired floating point precision for FastJ. */
    public static final float FloatPrecision = 0.0001f;

    private Maths() {
        throw new java.lang.IllegalStateException();
    }

    /**
     * Generates a random float number within the specified min and max limits.
     *
     * @param min The minimum number possible.
     * @param max The maximum number possible.
     * @return Randomized float value within the range of the specified parameters.
     */
    public static float random(float min, float max) {
        if (min >= max) {
            throw new IllegalArgumentException("The minimum must be less than the maximum.");
        }

        return ThreadLocalRandom.current().nextFloat() * (max - min) + min;
    }

    /**
     * Generates a random integer number within the specified min and max limits.
     * <p>
     * Unlike the other random generator methods, this method <b>includes</b> the max number as a possibility.
     *
     * @param min The minimum number possible.
     * @param max The maximum number possible.
     * @return Randomized integer value within the range of the specified parameters, including both the min and max as
     * possible values.
     */
    public static int randomInteger(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("The minimum must be less than the maximum.");
        }

        // nextInt(...) excludes the max number as a possibility...
        // as such, I extend the range here.
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    /**
     * Generates a random boolean value.
     *
     * @return The randomized boolean value.
     */
    public static boolean randomBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    /**
     * Generates a random number within the specified range, then snaps it to the edge it is closest to.
     *
     * @param leftEdge  The leftmost edge.
     * @param rightEdge The rightmost edge.
     * @return The edge that the random number is closest to.
     */
    public static float randomAtEdge(float leftEdge, float rightEdge) {
        if (leftEdge >= rightEdge) {
            throw new IllegalArgumentException("The left edge must be less than the right edge.");
        }

        return ThreadLocalRandom.current().nextBoolean() ? leftEdge : rightEdge;
    }

    /**
     * Snaps the specified number to the edge it is closest to.
     * <p>
     * If the two edges are equidistant from the {@code num}, then the right edge will be returned.
     *
     * @param num       The number to be compared.
     * @param leftEdge  The leftmost edge.
     * @param rightEdge The rightmost edge.
     * @return The edge that the number is closest to.
     */
    public static float snap(float num, float leftEdge, float rightEdge) {
        if (leftEdge >= rightEdge) {
            throw new IllegalArgumentException("The left edge must be less than the right edge.");
        }

        return ((num - leftEdge) < (rightEdge - num)) ? leftEdge : rightEdge;
    }

    /**
     * Finds the magnitude of the specified {@code x} and {@code y} values.
     *
     * @param x The x value.
     * @param y The y value.
     * @return The magnitude, as a {@code float} value.
     */
    public static float magnitude(float x, float y) {
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * Checks for 'equality' between two floating point values through ensuring their difference is less than the
     * defined float precision value {@link #FloatPrecision}.
     *
     * @param a The first {@code float}.
     * @param b The second {@code float}.
     * @return Whether the two are 'equal' (if their difference is less than {@link #FloatPrecision}).
     */
    public static boolean floatEquals(float a, float b) {
        return Math.abs(a - b) < FloatPrecision;
    }

    /**
     * Linearly interpolates between x and y, by the interpolation point t.
     *
     * @param x The starting value.
     * @param y The end value.
     * @param t The interpolation value to work with (preferably within a range of 0.0 to 1.0).
     * @return The lerped value
     */
    public static float lerp(float x, float y, float t) {
        return (1f - t) * x + t * y;
    }
}
