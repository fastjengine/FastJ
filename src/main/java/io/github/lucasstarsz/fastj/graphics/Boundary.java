package io.github.lucasstarsz.fastj.graphics;

/**
 * Defines aliases for the order in which {@code Drawable} boundaries are ordered in an array.
 * <p>
 * Boundaries are specified in the following way:
 * <ul>
 *     <li>TOP_LEFT: array index 0</li>
 *     <li>TOP_RIGHT: array index 1</li>
 *     <li>BOTTOM_RIGHT: array index 2</li>
 *     <li>BOTTOM_LEFT: array index 3</li>
 * </ul>
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
