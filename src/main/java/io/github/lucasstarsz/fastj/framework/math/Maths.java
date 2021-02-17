package io.github.lucasstarsz.fastj.framework.math;

import java.util.Random;

/**
 * Small utility class that provides a few methods for processing numbers.
 * <p>
 * This might have more use in the future.
 *
 * @author Andrew Dey
 * @version 1.0.0
 * @since 1.0.0
 */
public class Maths {

    private static final Random rand = new Random();

    /**
     * Generates a random number within the specified min and max limits.
     *
     * @param min The minimum number possible.
     * @param max The maximum number possible.
     * @return Randomized double value within the range of the specified parameters.
     */
    public static double random(double min, double max) {
        return (Math.random() * (max - min)) + min;
    }

    /**
     * Generates a random number within the specified range, then snaps it to the edge it is closest to.
     *
     * @param leftEdge  The leftmost edge.
     * @param rightEdge The rightmost edge.
     * @return The edge that the random number is closest to.
     */
    public static double randomAtEdge(double leftEdge, double rightEdge) {
        return rand.nextInt(2) == 1 ? leftEdge : rightEdge;
    }

    /**
     * Snaps the specified number to the edge it is closest to.
     *
     * @param num       The number to be compared.
     * @param leftEdge  The leftmost edge.
     * @param rightEdge The rightmost edge.
     * @return The edge that the number is closest to.
     */
    public static double snap(double num, double leftEdge, double rightEdge) {
        return ((num - leftEdge) < (rightEdge - num)) ? leftEdge : rightEdge;
    }
}
