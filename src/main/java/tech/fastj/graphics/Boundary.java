package tech.fastj.graphics;

/**
 * Defines aliases for the order in which {@code Drawable} boundaries are ordered in an array.
 *
 * <p>Boundaries are specified in the following way:
 *
 * <ul>
 *   <li>{@link #TopLeft}: array index 0
 *   <li>{@link #TopRight}: array index 1
 *   <li>{@link #BottomRight}: array index 2
 *   <li>{@link #BottomLeft}: array index 3
 * </ul>
 *
 * @author Andrew Dey
 * @since 1.0.0
 */
public enum Boundary {
  /** Top left boundary -- array index 0. */
  TopLeft(0),
  /** Top right boundary -- array index 1. */
  TopRight(1),
  /** Bottom right boundary -- array index 2. */
  BottomRight(2),
  /** Bottom left boundary -- array index 3. */
  BottomLeft(3);

  /** The array index corresponding to the enum constant. */
  public final int location;

  Boundary(int i) {
    location = i;
  }
}
