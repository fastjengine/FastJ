package tech.fastj.math;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Secondary mathematics class to provide useful utility methods dealing primarily with {@code float}s.
 *
 * @author Andrew Dey
 * @version 1.0.0
 */
public class Maths {

    /** The desired floating point precision for FastJ. */
    public static final float FloatPrecision = 0.0005f;

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
     * @param min The minimum number possible, inclusive.
     * @param max The maximum number possible, inclusive.
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

    /**
     * Ensures the specified number is within the range of the minimum and maximum numbers.
     *
     * @param num The original number.
     * @param min The minimum number allowed.
     * @param max The maximum number allowed.
     * @return The number, within the range of the minimum and maximum numbers.
     */
    public static float withinRange(float num, float min, float max) {
        if (min >= max) {
            throw new IllegalArgumentException("The minimum must be less than the maximum.");
        }
        return Math.min(Math.max(num, min), max);
    }

    /**
     * Ensures the specified number is within the range of the minimum and maximum numbers.
     *
     * @param num The original number.
     * @param min The minimum number allowed.
     * @param max The maximum number allowed.
     * @return The number, within the range of the minimum and maximum numbers.
     */
    public static int withinIntegerRange(int num, int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("The minimum must be less than the maximum.");
        }
        return Math.min(Math.max(num, min), max);
    }

    /**
     * Scales the provided number {@code num} on a scale of {@code 0.0} to {@code 1.0} based on the provided {@code
     * minimum} and {@code maximum} values.
     *
     * @param num The number to normalize.
     * @param min The minimum value in a range of possible values for {@code num}.
     * @param max The maximum value in a range of possible values for {@code num}.
     * @return The normalized version of {@code num}, on a scale of {@code 0.0} to {@code 1.0}.
     */
    public static float normalize(float num, float min, float max) {
        if (num > max) {
            throw new IllegalArgumentException("The provided number must be less than the maximum number.");
        }
        if (num < min) {
            throw new IllegalArgumentException("The provided number must be more than the minimum number.");
        }

        return (num - min) / (max - min);
    }

    /**
     * Scales a normalized number {@code normalizedNum} on a scale of {@code min} to {@code max}, based on the provided
     * {@code min} and {@code max} values.
     *
     * @param normalizedNum The number to de-normalize.
     * @param min           The minimum value in a range of possible values for the result of the method.
     * @param max           The maximum value in a range of possible values for the result of the method.
     * @return The denormalized version of {@code normalizedNum}, on a scale of {@code min} to {@code max}.
     */
    public static float denormalize(float normalizedNum, float min, float max) {
        if (normalizedNum > 1.0f) {
            throw new IllegalArgumentException("The normalized number must be less than 1.0f.");
        }
        if (normalizedNum < 0.0f) {
            throw new IllegalArgumentException("The normalized number must be more than 0.0f.");
        }
        if (min > max) {
            throw new IllegalArgumentException("The minimum must be less than the maximum.");
        }

        return (normalizedNum * (max - min)) + min;
    }
    
    /**
     * Calculates a {@code float} based on the value between {@code startingVal} and {@code endingVal}.
     * 
     * @param startingVal Starting value.
     * @param endingVal Ending value.
     * @param value The value between the starting and ending values. 
     **/
    public static float inverseLerp(float start, float end, float value) {
        return (value - start) / (end - start);
    }
}
