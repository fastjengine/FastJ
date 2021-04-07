package io.github.lucasstarsz.fastj.math;

import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

/**
 * Class that defines a point in 2D space, using integers.
 *
 * @author Andrew Dey
 * @version 1.0.0
 */
public class Point {

    public static final Point origin = new Point();

    /** The x value of the {@link Point}. */
    public int x;
    /** The y value of the {@link Point}. */
    public int y;

    /** {@code Point} constructor, where its x and y values are set to zero. */
    public Point() {
        this(0, 0);
    }

    /**
     * {@code Point} constructor, where the values for the newly created {@code Point} are set to the integer
     * parameter's values.
     *
     * @param xy Value to set the x and y of this {@code Point} to.
     */
    public Point(int xy) {
        this(xy, xy);
    }

    /**
     * {@code Point} constructor, where the values for the newly created {@code Point} are set to the parameter {@code
     * Point}'s values.
     *
     * @param p {@code Point} to set the x and y of this {@code Point} to.
     */
    public Point(Point p) {
        this(p.x, p.y);
    }

    /**
     * {@code Point} constructor, where the values for the newly created {@code Point} are set to the specified x and y
     * parameters.
     *
     * @param xVal Value to define the x coordinate for this {@code Point} object.
     * @param yVal Value to define the y coordinate for this {@code Point} object.
     */
    public Point(int xVal, int yVal) {
        x = xVal;
        y = yVal;
    }

    /**
     * Static method used to add two {@code Point}s (from the parameters specified) together, and return a new {@code
     * Point} object.
     *
     * @param p1 The first {@code Point} used for addition.
     * @param p2 The second {@code Point} used for addition.
     * @return Returns a new {@code Point} with coordinates equal to the added values from the two {@code Point}s.
     */
    public static Point add(Point p1, Point p2) {
        return new Point(p1.x + p2.x, p1.y + p2.y);
    }

    /**
     * Static method used to add a {@code Point} object by an integer value, and return a new {@code Point}.
     *
     * @param p The {@code Point} used for addition.
     * @param i Integer value used for addition.
     * @return Returns a new {@code Point} with coordinates equal to the added values from the {@code Point} and the
     * integer value.
     */
    public static Point add(Point p, int i) {
        return new Point(p.x + i, p.y + i);
    }

    /**
     * Static method used to subtract two Points (from the parameters specified) together, and return a new {@code
     * Point} object.
     *
     * @param p1 The first {@code Point} used for subtraction; the {@code Point} acting as the first value in
     *           subtraction.
     * @param p2 The second {@code Point} used for subtraction; the {@code Point} acting as the second value in
     *           subtraction.
     * @return Returns a new {@code Point} with coordinates equal to the subtracted values from the two {@code Point}s.
     */
    public static Point subtract(Point p1, Point p2) {
        return new Point(p1.x - p2.x, p1.y - p2.y);
    }

    /**
     * Static method used to subtract a {@code Point} object by an integer value, and return a new {@code Point}.
     *
     * @param p The {@code Point} used for subtraction; the {@code Point} acting as the first value in subtraction.
     * @param i Integer value used for subtraction; the second value used in subtraction.
     * @return Returns a new {@code Point} with coordinates equal to the subtracted values from the {@code Point} and
     * the integer value.
     */
    public static Point subtract(Point p, int i) {
        return new Point(p.x - i, p.y - i);
    }

    /**
     * Static method used to multiply two {@code Point} objects (from the parameters specified) together, and return a
     * new {@code Point}.
     *
     * @param p1 The first {@code Point} used for multiplication.
     * @param p2 The second {@code Point} used for multiplication.
     * @return Returns a new {@code Point} with coordinates equal to the multiplied values from the two {@code Point}s.
     */
    public static Point multiply(Point p1, Point p2) {
        return new Point(p1.x * p2.x, p1.y * p2.y);
    }

    /**
     * Static method used to multiply a {@code Point} object by an integer value, and return a new {@code Point}.
     *
     * @param p The {@code Point} used for multiplication.
     * @param i Integer value used for multiplication.
     * @return Returns a new {@code Point} with coordinates equal to the multiplied values from the {@code Point} and
     * the integer value.
     */
    public static Point multiply(Point p, int i) {
        return new Point(p.x * i, p.y * i);
    }

    /**
     * Static method used to divide two {@code Point} objects (from the parameters specified) together, and return a new
     * {@code Point}.
     *
     * @param p1 The first {@code Point} used for division; the {@code Point} acting as the first value in division.
     * @param p2 The second {@code Point} used for division; the {@code Point} acting as the second value in division.
     * @return Returns a new {@code Point} with coordinates equal to the divided values from the two {@code Point}s.
     */
    public static Point divide(Point p1, Point p2) {
        return new Point(p1.x / p2.x, p1.y / p2.y);
    }

    /**
     * Static method used to divide a {@code Point} object by an integer value, and return a new {@code Point}.
     *
     * @param p The {@code Point} used for division; the {@code Point} acting as the first value in division.
     * @param i Integer value used for division; the second value used in division.
     * @return Returns a new {@code Point} with coordinates equal to the divided values from the {@code Point} and the
     * integer value.
     */
    public static Point divide(Point p, int i) {
        return new Point(p.x / i, p.y / i);
    }

    /**
     * Converts the specified {@code Point} to a {@code Pointf} object.
     *
     * @param pt {@code Point} to be converted.
     * @return Returns a {@code Pointf} created using the values from the input {@code Point}.
     */
    public static Pointf toPointf(Point pt) {
        return new Pointf(pt.x, pt.y);
    }

    /**
     * Sets the x and y values of this {@code Point} object to the values specified.
     *
     * @param newX int value used to set the new x value for this {@code Point} object.
     * @param newY int value used to set the new y value for this {@code Point} object.
     */
    public void set(int newX, int newY) {
        x = newX;
        y = newY;
    }

    /**
     * Determines whether a specified Rectangle2D object intersects with this {@code Point} object.
     *
     * @param metrics Rectangle2D object used to check for intersection between this {@code Point} object and it.
     * @return Returns a boolean value that defines whether the Rectangle2D and this {@code Point} object intersect.
     */
    public boolean intersects(Rectangle2D metrics) {
        if (metrics.getX() <= x && x <= (metrics.getX() + metrics.getWidth())) {
            return (metrics.getY()) <= y && y <= (metrics.getY() + metrics.getHeight());
        }
        return false;
    }

    /**
     * Determines whether a specified Path2D object intersects with this {@code Point} object.
     *
     * @param path Path2D object used to check for intersection between this {@code Point} object and it.
     * @return Returns a boolean value that defines whether the Path2D and this {@code Point} object intersect.
     */
    public boolean intersects(Path2D path) {
        return Path2D.intersects(path.getPathIterator(null), x, y, 1, 1);
    }

    /**
     * Gets a copy of the {@code Point}, and returns the copy.
     *
     * @return The new {@code Point}.
     */
    public Point copy() {
        return new Point(x, y);
    }

    /** Resets this {@code Point}'s values to zero. */
    public void reset() {
        x = 0;
        y = 0;
    }

    /**
     * Adds the values of this {@code Point} to the specified {@code Point}, and returns a new {@code Point} with the
     * modified values.
     *
     * @param p {@code Point} to add this {@code Point} to.
     * @return Returns this {@code Point}, with the modified values.
     */
    public Point add(Point p) {
        x += p.x;
        y += p.y;

        return this;
    }

    /**
     * Adds the values of this {@code Point} to the specified integer value, and returns a new {@code Point} with the
     * modified values.
     *
     * @param f Value to add this {@code Point} to.
     * @return Returns this {@code Point}, with the modified values.
     */
    public Point add(int f) {
        x += f;
        y += f;

        return this;
    }

    /**
     * Subtracts the values of this {@code Point} by the specified {@code Point}, and returns a new {@code Point} with
     * the modified values.
     *
     * @param p {@code Point} to subtract this {@code Point} by.
     * @return Returns this {@code Point}, with the modified values.
     */
    public Point subtract(Point p) {
        x -= p.x;
        y -= p.y;

        return this;
    }

    /**
     * Subtracts the values of this {@code Point} by the specified integer value, and returns a new {@code Point} with
     * the modified values.
     *
     * @param f Value to subtract this {@code Point} by.
     * @return Returns this {@code Point}, with the modified values.
     */
    public Point subtract(int f) {
        x -= f;
        y -= f;

        return this;
    }

    /**
     * Multiplies the values of this {@code Point} by the specified {@code Point}, and returns a new {@code Point} with
     * the modified values.
     *
     * @param p {@code Point} to multiply this {@code Point} by.
     * @return Returns this {@code Point}, with the modified values.
     */
    public Point multiply(Point p) {
        x *= p.x;
        y *= p.y;

        return this;
    }

    /**
     * Multiplies the values of this {@code Point} by the specified integer value, and returns a new {@code Point} with
     * the modified values.
     *
     * @param f Value to multiply this {@code Point} by.
     * @return Returns this {@code Point}, with the modified values.
     */
    public Point multiply(int f) {
        x *= f;
        y *= f;

        return this;
    }

    /**
     * Divides the values of this {@code Point} by the specified {@code Point}, and returns a new {@code Point} with the
     * modified values.
     *
     * @param p {@code Point} to divide this {@code Point} by.
     * @return Returns this {@code Point}, with the modified values.
     */
    public Point divide(Point p) {
        x /= p.x;
        y /= p.y;

        return this;
    }

    /**
     * Divides the values of this {@code Point} by the specified integer value, and returns a new {@code Point} with the
     * modified values.
     *
     * @param f Value to divide this {@code Point} by.
     * @return Returns this {@code Point}, with the modified values.
     */
    public Point divide(int f) {
        x /= f;
        y /= f;

        return this;
    }

    /**
     * Returns this {@code Point} as a {@code Pointf} object, without modification.
     *
     * @return The {@code Pointf} created using the values from this {@code Point}.
     */
    public Pointf asPointf() {
        return new Pointf(x, y);
    }

    /**
     * Compares the {@code Point} with a {@link Pointf}, and returns whether their {@code x} and {@code y} values are
     * equal.
     *
     * @param other The {@code Pointf} to compare against.
     * @return Whether the two's {@code x} and {@code y} values are equal.
     */
    public boolean equalsPointf(Pointf other) {
        return Float.compare(other.x, (float) x) == 0 && Float.compare(other.y, (float) y) == 0;
    }

    /**
     * Compares two {@code Point}s, and returns whether they are equal.
     *
     * @param other The {@code Point} to check for equality against.
     * @return Whether the {@code Point}s are equal.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Point point = (Point) other;
        return x == point.x && y == point.y;
    }

    /**
     * Gets a hash code for the {@code Point} based on its {@link #x} and {@link #y} values.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Returns this {@code Point}'s coordinates as a String.
     *
     * @return The coordinates of this Point as a String.
     */
    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
