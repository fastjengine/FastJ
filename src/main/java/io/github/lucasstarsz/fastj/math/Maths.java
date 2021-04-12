package io.github.lucasstarsz.fastj.math;

import java.util.Random;

/**
 * Small utility class that provides a few methods for processing numbers.
 * <p>
 * This might have more use in the future.
 *
 * @author Andrew Dey
 * @version 1.0.0
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
        double trueMax = Math.max(min, max);
        double trueMin = max == trueMax ? min : max;

        return (Math.random() * (trueMax - trueMin)) + trueMin;
    }

    /**
     * Generates a random number within the specified range, then snaps it to the edge it is closest to.
     *
     * @param leftEdge  The leftmost edge.
     * @param rightEdge The rightmost edge.
     * @return The edge that the random number is closest to.
     */
    public static double randomAtEdge(double leftEdge, double rightEdge) {
        double trueLeftEdge = Math.min(leftEdge, rightEdge);
        double trueRightEdge = leftEdge == trueLeftEdge ? rightEdge : leftEdge;

        return rand.nextInt(2) == 1 ? trueLeftEdge : trueRightEdge;
    }

    /**
     * Snaps the specified number to the edge it is closest to.
     *
     * If the two edges are equidistant, then the right edge will be selected.
     *
     * @param num       The number to be compared.
     * @param leftEdge  The leftmost edge.
     * @param rightEdge The rightmost edge.
     * @return The edge that the number is closest to.
     */
    public static double snap(double num, double leftEdge, double rightEdge) {
        double trueLeftEdge = Math.min(leftEdge, rightEdge);
        double trueRightEdge = leftEdge == trueLeftEdge ? rightEdge : leftEdge;

        return ((num - leftEdge) < (rightEdge - num)) ? trueLeftEdge : trueRightEdge;
    }

    /**
     * Finds the magnitude of the specified {@code x} and {@code y} values.
     *
     * @param x The x value.
     * @param y The y value.
     * @return The magnitude, as a {@code double} value.
     */
    public static double magnitude(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Finds the magnitude of the {@code Pointf} based on its {@code x} and {@code y} coordinates.
     *
     * @param p The {@code Pointf} to find the magnitude of.
     * @return The magnitude, as a {@code double} value.
     */
    public static double magnitude(Pointf p) {
        return Math.sqrt(p.x * p.x + p.y * p.y);
    }

    /**
     * Finds the magnitude of the {@code Point} based on its {@code x} and {@code y} coordinates.
     *
     * @param p The {@code Point} to find the magnitude of.
     * @return The magnitude, as a {@code double} value.
     */
    public static double magnitude(Point p) {
        return Math.sqrt(p.x * p.x + p.y * p.y);
    }
}
