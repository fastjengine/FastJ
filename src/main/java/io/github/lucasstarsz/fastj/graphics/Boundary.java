package io.github.lucasstarsz.fastj.graphics;

/**
 * Defines aliases for the order in which {@code Drawable} boundaries are ordered in an array.
 * <p>
 * Boundaries are specified in the following way:
 * <ul>
 *     <li>{@link #TOP_LEFT}: array index 0</li>
 *     <li>{@link #TOP_RIGHT}: array index 1</li>
 *     <li>{@link #BOTTOM_RIGHT}: array index 2</li>
 *     <li>{@link #BOTTOM_LEFT}: array index 3</li>
 * </ul>
 *
 * @author Andrew Dey
 * @version 1.0.0
 */
public enum Boundary {
    /** Top left boundary -- array index 0. */
    TOP_LEFT(0),
    /** Top right boundary -- array index 1. */
    TOP_RIGHT(1),
    /** Bottom right boundary -- array index 2. */
    BOTTOM_RIGHT(2),
    /** Bottom left boundary -- array index 3. */
    BOTTOM_LEFT(3);

    /** The array index corresponding to the enum constant. */
    public final int location;

    Boundary(int i) {
        location = i;
    }
}
