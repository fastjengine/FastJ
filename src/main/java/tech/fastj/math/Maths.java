package tech.fastj.math;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Secondary mathematics class to provide useful utility methods dealing primarily with {@code float}s.
 *
 * @author Andrew Dey
 * @since 1.0.0
 */
public class Maths {

    /** The desired floating point precision for FastJ. */
    public static final float FloatPrecision = 0.0005f;

    private Maths() {
        throw new java.lang.IllegalStateException();
    }

    /**
     * {@return a random {@code float} value within the specified min and max limits}
     *
     * @param min The minimum number possible.
     * @param max The maximum number possible.
     */
    public static float random(float min, float max) {
        if (min >= max) {
            throw new IllegalArgumentException("The minimum must be less than the maximum.");
        }

        return ThreadLocalRandom.current().nextFloat() * (max - min) + min;
    }

    /**
     * {@return a random integer number within the specified min and max limits}
     * <p>
     * Unlike the other random generator methods, this method <b>includes</b> the max number as a possibility.
     *
     * @param min The minimum number possible, inclusive.
     * @param max The maximum number possible, inclusive.
     */
    public static int randomInteger(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("The minimum must be less than the maximum.");
        }

        // nextInt(...) excludes the max number as a possibility...
        // as such, I extend the range here.
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    /** {@return a random boolean value} */
    public static boolean randomBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    /**
     * {@return the edge that the random number (within range of the edges) is closest to}
     *
     * @param leftEdge  The leftmost edge.
     * @param rightEdge The rightmost edge.
     */
    public static float randomAtEdge(float leftEdge, float rightEdge) {
        if (leftEdge >= rightEdge) {
            throw new IllegalArgumentException("The left edge must be less than the right edge.");
        }

        return ThreadLocalRandom.current().nextBoolean() ? leftEdge : rightEdge;
    }

    /**
     * {@return the edge that the specified number is closest to}
     * <p>
     * If the two edges are equidistant from the {@code num}, then the right edge will be returned.
     *
     * @param num       The number to be compared.
     * @param leftEdge  The leftmost edge.
     * @param rightEdge The rightmost edge.
     */
    public static float snap(float num, float leftEdge, float rightEdge) {
        if (leftEdge >= rightEdge) {
            throw new IllegalArgumentException("The left edge must be less than the right edge.");
        }

        return ((num - leftEdge) < (rightEdge - num)) ? leftEdge : rightEdge;
    }

    /**
     * {@return the magnitude of the specified {@code x} and {@code y} values}.
     *
     * @param x The x value.
     * @param y The y value.
     */
    public static float magnitude(float x, float y) {
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * {@return whether the two {@code float}s are 'equal' (if their difference is less than {@link #FloatPrecision})}
     *
     * @param a The first {@code float}.
     * @param b The second {@code float}.
     */
    public static boolean floatEquals(float a, float b) {
        return Math.abs(a - b) < FloatPrecision;
    }

    /**
     * {@return a linear interpolation between {@code f} and {@code f1}, by the value {@code t}}
     * <p>
     * <b>Notes</b>
     * <p>
     * If {@code t} is within a range of {@code 0.0f} to {@code 1.0f}, then the returned value will be within the range of {@code f} and
     * {@code f1}. Otherwise, it will be outside of that range.
     * <p>
     * The returned value can also be used to calculate {@code t} when used alongside {@code f} and {@code f1} in
     * {@link #inverseLerp(float, float, float) an inverse linear interpolation}.
     *
     * @param f  The starting value.
     * @param f1 The ending value.
     * @param t  The interpolation value to evaluate {@code f} and {@code f1} on.
     * @author <a href="https://github.com/YeffyCodeGit" target="_blank">YeffyCodeGit</a>
     * @since 1.4.0
     */
    public static float lerp(float f, float f1, float t) {
        return (1f - t) * f + t * f1;
    }

    /**
     * {@return the inverse linear interpolation of {@code v} for the range {@code f} to {@code f1}}
     * <p>
     * The returned value can also be used to calculate {@code v} in {@link #lerp(float, float, float) a linear interpolation}.
     *
     * @param f  The starting value.
     * @param f1 The ending value.
     * @param v  The result of linear interpolation on {@code f} and {@code f1}.
     * @author <a href="https://github.com/YeffyCodeGit" target="_blank">YeffyCodeGit</a>
     * @since 1.5.0
     */
    public static float inverseLerp(float f, float f1, float v) {
        return (v - f) / (f1 - f);
    }

    /**
     * {@return the number, clamped within the range of the minimum and maximum numbers.}
     *
     * @param num The number to clamp.
     * @param min The minimum number.
     * @param max The maximum number.
     * @since 1.5.0
     */
    public static float clamp(float num, float min, float max) {
        if (min >= max) {
            throw new IllegalArgumentException("The minimum must be less than the maximum.");
        }
        return Math.min(Math.max(num, min), max);
    }

    /**
     * {@return the number, clamped within the range of the minimum and maximum numbers.}
     *
     * @param num The number to clamp.
     * @param min The minimum number.
     * @param max The maximum number.
     * @since 1.5.0
     */
    public static int clamp(int num, int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("The minimum must be less than the maximum.");
        }
        return Math.min(Math.max(num, min), max);
    }

    /**
     * Scales the provided number {@code num} on a scale of {@code 0.0} to {@code 1.0} based on the provided {@code minimum} and
     * {@code maximum} values.
     *
     * @param num The number to normalize.
     * @param min The minimum value in a range of possible values for {@code num}.
     * @param max The maximum value in a range of possible values for {@code num}.
     * @return The normalized version of {@code num}, on a scale of {@code 0.0} to {@code 1.0}.
     * @since 1.5.0
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
     * Scales a normalized number {@code normalizedNum} on a scale of {@code min} to {@code max}, based on the provided {@code min} and
     * {@code max} values.
     *
     * @param normalizedNum The number to de-normalize.
     * @param min           The minimum value in a range of possible values for the result of the method.
     * @param max           The maximum value in a range of possible values for the result of the method.
     * @return The denormalized version of {@code normalizedNum}, on a scale of {@code min} to {@code max}.
     * @since 1.5.0
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
}
