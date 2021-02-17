package io.github.lucasstarsz.fastj.framework.graphics;

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
    TOP_LEFT(0),
    TOP_RIGHT(1),
    BOTTOM_RIGHT(2),
    BOTTOM_LEFT(3);

    public final int location;

    Boundary(int i) {
        location = i;
    }
}
