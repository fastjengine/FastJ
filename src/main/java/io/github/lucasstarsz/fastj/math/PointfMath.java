package io.github.lucasstarsz.fastj.math;

/**
 * Mathematics class which provides utility functions to deal with math with the {@code Pointf} class
 *
 * @author Adit Chakraborty
 * @version 1.0.0
 */
public class PointfMath {

    /**
     * Adds 2 {@code Pointf}'s together.
     *
     * @param a The first {@code Pointf}
     * @param b The second {@code Pointf}
     * @return The {@code Pointf} formed by the addition of a and b.
     */
    public static Pointf add(Pointf a, Pointf b) {
        return new Pointf(a.x + b.x, a.y + b.y);
    }

    /**
     * Subtracts 2 {@code Pointf}'s together.
     *
     * @param a The first {@code Pointf}
     * @param b The second {@code Pointf}
     * @return The {@code Pointf} formed by the subtraction of a and b.
     */
    public static Pointf subtract(Pointf a, Pointf b) {
        return new Pointf(a.x - b.x, a.y - b.y);
    }
}
