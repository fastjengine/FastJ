package tech.fastj.math;

import java.awt.Dimension;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

/**
 * Class that defines a point in 2D space, using integers.
 *
 * @author Andrew Dey
 * @since 1.0.0
 */
public class Point {

    /** {@code Point} representing the origin as an {@code int}: {@code (0, 0)}. */
    public static final Point Origin = new Point();

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
     * Static method to add two {@code Point}s (from the parameters specified) together, and return a new {@code Point}
     * object.
     *
     * @param p1 The first {@code Point} used for addition.
     * @param p2 The second {@code Point} used for addition.
     * @return Returns a new {@code Point} with coordinates equal to the added values from the two {@code Point}s.
     */
    public static Point add(Point p1, Point p2) {
        return new Point(p1.x + p2.x, p1.y + p2.y);
    }

    /**
     * Static method to add a {@code Point} object by an integer value, and return a new {@code Point}.
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
     * Static method to subtract two Points (from the parameters specified) together, and return a new {@code Point}
     * object.
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
     * Static method to subtract a {@code Point} object by an integer value, and return a new {@code Point}.
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
     * Static method to multiply two {@code Point} objects (from the parameters specified) together, and return a new
     * {@code Point}.
     *
     * @param p1 The first {@code Point} used for multiplication.
     * @param p2 The second {@code Point} used for multiplication.
     * @return Returns a new {@code Point} with coordinates equal to the multiplied values from the two {@code Point}s.
     */
    public static Point multiply(Point p1, Point p2) {
        return new Point(p1.x * p2.x, p1.y * p2.y);
    }

    /**
     * Static method to multiply a {@code Point} object by an integer value, and return a new {@code Point}.
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
     * Static method to divide two {@code Point} objects (from the parameters specified) together, and return a new
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
     * Static method to divide a {@code Point} object by an integer value, and return a new {@code Point}.
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
     * Calculates and returns the dot product of the two specified {@code Point}s.
     *
     * @param p  The first of two {@code Point}s to calculate the dot product with.
     * @param p1 The second of two {@code Point}s to calculate the dot product with.
     * @return The calculated dot product.
     */
    public static int dot(Point p, Point p1) {
        return (p.x * p1.x) + (p.y * p1.y);
    }

    /**
     * Calculates and returns the cross product of the two specified {@code Point}s.
     * <p>
     * Unlike calculating the cross product of two 3-dimensional vectors, this cross product calculates as if the two
     * {@code Point}s were 3-dimensional vectors with Z values implicitly set to {@code 0}.
     *
     * @param p  The first of two {@code Point}s to calculate the cross product with.
     * @param p1 The second of two {@code Point}s to calculate the cross product with.
     * @return The calculated cross product.
     */
    public static int cross(Point p, Point p1) {
        return (p.x * p1.y) - (p.y * p1.x);
    }

    /**
     * Creates a rotated version of the {@code Point} based on the provided angle.
     * <p>
     * This rotation method uses integer versions of the {@code Point}'s {@link #x} and {@link #y} values, and returns
     * the {@code Point} version of the calculation. If you want the rotation calculated with floating-point math, use
     * {@link Point#rotate(Point, float)} instead.
     *
     * @param p     The point to rotate.
     * @param angle The angle to rotate by, in degrees.
     * @return A rotated version of the original {@code Point}.
     */
    public static Point integerRotate(Point p, int angle) {
        float angleInRadians = (float) Math.toRadians(angle);
        float sineOfAngle = (float) Math.sin(angleInRadians);
        float cosineOfAngle = (float) Math.cos(angleInRadians);

        int rotatedX = (int) ((p.x * cosineOfAngle) + (p.y * sineOfAngle));
        int rotatedY = (int) ((-p.x * sineOfAngle) + (p.y * cosineOfAngle));
        return new Point(rotatedX, rotatedY);
    }

    /**
     * Creates a rotated version of the {@code Pointf} based on the provided angle and center point.
     * <p>
     * This rotation method rotates about the specified {@code center}. If you need to rotate about the origin, use
     * {@link #rotate(Point, float)}.
     *
     * @param p      The point to rotate.
     * @param angle  The angle to rotate by, in degrees.
     * @param center The point to rotate about.
     * @return A rotated version of the original {@code Pointf}.
     */
    public static Point integerRotate(Point p, float angle, Point center) {
        float angleInRadians = (float) Math.toRadians(angle);
        float sineOfAngle = (float) Math.sin(angleInRadians);
        float cosineOfAngle = (float) Math.cos(angleInRadians);

        int translatedX = p.x - center.x;
        int translatedY = p.y - center.y;

        int rotatedX = (int) ((translatedX * cosineOfAngle) + (translatedY * sineOfAngle));
        int rotatedY = (int) ((-translatedX * sineOfAngle) + (translatedY * cosineOfAngle));

        return new Point(rotatedX + center.x, rotatedY + center.y);
    }

    /**
     * Creates a rotated version of the {@code Point} based on the provided angle.
     * <p>
     * This rotation method uses floating-point versions of the {@code Point}'s {@link #x} and {@link #y} values, and
     * returns the {@code Pointf} version of the calculation. If you want the rotation calculated with integer math, use
     * {@link Point#integerRotate(Point, int)} instead.
     *
     * @param p     The point to rotate.
     * @param angle The angle to rotate by, in degrees.
     * @return A rotated version of the original {@code Point}, as a {@code Pointf}.
     */
    public static Pointf rotate(Point p, float angle) {
        float angleInRadians = (float) Math.toRadians(angle);
        float sineOfAngle = (float) Math.sin(angleInRadians);
        float cosineOfAngle = (float) Math.cos(angleInRadians);

        float rotatedX = (p.x * cosineOfAngle) + (p.y * sineOfAngle);
        float rotatedY = (-p.x * sineOfAngle) + (p.y * cosineOfAngle);
        return new Pointf(rotatedX, rotatedY);
    }

    /**
     * Creates a rotated version of the {@code Pointf} based on the provided angle and center point.
     * <p>
     * This rotation method rotates about the specified {@code center}. If you need to rotate about the origin, use
     * {@link #rotate(Point, float)}.
     *
     * @param p      The point to rotate.
     * @param angle  The angle to rotate by, in degrees.
     * @param center The point to rotate around.
     * @return A rotated version of the original {@code Pointf}.
     */
    public static Pointf rotate(Point p, float angle, Pointf center) {
        float angleInRadians = (float) Math.toRadians(angle);
        float sineOfAngle = (float) Math.sin(angleInRadians);
        float cosineOfAngle = (float) Math.cos(angleInRadians);

        float translatedX = p.x - center.x;
        float translatedY = p.y - center.y;

        float rotatedX = (translatedX * cosineOfAngle) + (translatedY * sineOfAngle);
        float rotatedY = (-translatedX * sineOfAngle) + (translatedY * cosineOfAngle);

        return new Pointf(rotatedX + center.x, rotatedY + center.y);
    }

    /**
     * Calculates and returns the signed angle between the specified {@code Point}s.
     *
     * @param p  The first of two {@code Point}s to calculate the angle with.
     * @param p1 The second of two {@code Point}s to calculate the angle with.
     * @return The calculated angle, in radians.
     */
    public static float signedAngle(Point p, Point p1) {
        float dotProduct = (float) (p.x * p1.x) + (float) (p.y * p1.y);
        float determinant = (float) (p.x * p1.y) - (float) (p.y * p1.x);

        return (float) Math.atan2(determinant, dotProduct);
    }

    /**
     * Calculates and returns the angle between the specified {@code Point}s.
     *
     * @param p  The first of two {@code Point}s to calculate the angle with.
     * @param p1 The second of two {@code Point}s to calculate the angle with.
     * @return The calculated angle, in radians.
     */
    public static float angle(Point p, Point p1) {
        float dotProduct = (float) (p.x * p1.x) + (float) (p.y * p1.y);
        float magnitudeProduct = (float) Math.sqrt((float) (p.x * p.x) + (float) (p.y * p.y)) * (float) Math.sqrt((float) (p1.x * p1.x) + (float) (p1.y * p1.y));

        return (float) Math.acos(dotProduct / magnitudeProduct);
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
     * Rotates the {@code Point} based on the provided angle.
     * <p>
     * This rotation method uses integer versions of the {@code Point}'s {@link #x} and {@link #y} values, and returns
     * the {@code Point} version of the calculation.
     *
     * @param angle The angle to rotate by, in degrees.
     * @return The {@code Point} with the rotated values.
     */
    public Point rotate(int angle) {
        float angleInRadians = (float) Math.toRadians(angle);
        float sineOfAngle = (float) Math.sin(angleInRadians);
        float cosineOfAngle = (float) Math.cos(angleInRadians);

        float rotatedX = (x * cosineOfAngle) +  (y * sineOfAngle);
        float rotatedY = (-x * sineOfAngle) + (y * cosineOfAngle);
        x = (int) rotatedX;
        y = (int) rotatedY;

        return this;
    }

    /**
     * Rotates the {@code Point} based on the provided angle and center point.
     * <p>
     * This rotation method rotates about the specified {@code center}. If you need to rotate about the origin, use
     * {@link #rotate(int)}.
     *
     * @param angle  The angle to rotate by, in degrees.
     * @param center The point to rotate about.
     * @return The {@code Pointf} with the rotated values.
     */
    public Point rotate(float angle, Point center) {
        float angleInRadians = (float) Math.toRadians(angle);
        float sineOfAngle = (float) Math.sin(angleInRadians);
        float cosineOfAngle = (float) Math.cos(angleInRadians);

        x -= center.x;
        y -= center.y;

        float rotatedX = ((float) x * cosineOfAngle) + ((float) y * sineOfAngle);
        float rotatedY = ((float) -x * sineOfAngle) + ((float) y * cosineOfAngle);

        x = (int) (rotatedX + center.x);
        y = (int) (rotatedY + center.y);

        return this;
    }

    /**
     * Calculates and returns the {@code length}, or {@code magnitude}, of the {@code Point}.
     * <p>
     * This magnitude method uses integer versions of the {@code Point}'s {@link #x} and {@link #y} values, and returns
     * the {@code int} version of the calculation. If you want the magnitude calculated with floating-point math, use
     * {@link #magnitude()} instead.
     *
     * @return The magnitude, calculated using floating-point versions of the {@code Point}'s {@link #x} and {@link #y}
     * values.
     */
    public int integerMagnitude() {
        return (int) Math.sqrt((double) (x * x) + (double) (y * y));
    }

    /**
     * Calculates and returns the {@code length}, or {@code magnitude}, of the {@code Point}.
     * <p>
     * This magnitude method uses floating-point versions of the {@code Point}'s {@link #x} and {@link #y} values, and
     * returns the {@code float} version of the calculation. If you want the magnitude calculated with integer math, use
     * {@link #integerMagnitude()} instead.
     *
     * @return The magnitude, calculated using floating-point versions of the {@code Point}'s {@link #x} and {@link #y}
     * values.
     */
    public float magnitude() {
        return (float) Math.sqrt(((float) x * (float) x) + ((float) y * (float) y));
    }

    /**
     * Calculates and returns the square magnitude of the {@code Point}.
     *
     * @return The square magnitude of the {@code Point}'s {@link #x} and {@link #y} values.
     */
    public int squareMagnitude() {
        return (x * x) + (y * y);
    }

    /**
     * Creates a normalized version of the {@code Point} using integer division.
     * <p>
     * This method does not modify the contents of the original {@code Point}. If the desired result requires a {@code
     * Pointf} or floating-point division, consider using {@link #normalized()} instead.
     *
     * @return A normalized version of the {@code Point}.
     */
    public Point integerNormalized() {
        int magnitude = (int) Math.sqrt((double) (x * x) + (double) (y * y));

        if (magnitude == 0) {
            return Point.Origin.copy();
        }

        int normalizedX = x / magnitude;
        int normalizedY = y / magnitude;
        return new Point(normalizedX, normalizedY);
    }

    /**
     * Creates a normalized version of the {@code Point}, as a {@code Pointf}, using floating-point division.
     * <p>
     * This method does not modify the contents of the original {@code Point}. If the desired result requires a {@code
     * Point} or integer division, consider using {@link #integerNormalized()} instead.
     *
     * @return A normalized version of the {@code Point}, as a {@code Pointf}.
     */
    public Pointf normalized() {
        float magnitude = (float) Math.sqrt(((float) x * (float) x) + ((float) y * (float) y));

        if (magnitude == 0f) {
            return Pointf.Origin.copy();
        }

        float normalizedX = (float) x / magnitude;
        float normalizedY = (float) y / magnitude;
        return new Pointf(normalizedX, normalizedY);
    }

    /**
     * Creates a {@link Pointf} version of the {@code Point}.
     *
     * @return The {@code Point} created.
     */
    public Pointf asPointf() {
        return new Pointf(x, y);
    }

    /**
     * Creates a {@link Dimension} version of the {@code Point}.
     *
     * @return The {@code Dimension} created.
     */
    public Dimension asDimension() {
        return new Dimension(x, y);
    }

    /**
     * Compares the {@code Point} with a {@link Pointf}, and returns whether their {@code x} and {@code y} values are
     * equal.
     *
     * @param other The {@code Pointf} to compare against.
     * @return Whether the two's {@code x} and {@code y} values are equal.
     */
    public boolean equalsPointf(Pointf other) {
        return Maths.floatEquals(other.x, (float) x) && Maths.floatEquals(other.y, (float) y);
    }

    /**
     * Compares the {@code Point} with a {@link Dimension}, and returns whether the {@code Point}'s {@code x} and {@code
     * y} values are equal to the {@code Dimension}'s {@code width} and {@code height} values, respectively.
     *
     * @param other The {@code Dimension} to compare against.
     * @return Whether the two's {@code x}/{@code width} and {@code y}/{@code height} values are equal.
     */
    public boolean equalsDimension(Dimension other) {
        return x == other.width && y == other.height;
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
     * Formats the {@code Point}'s coordinates in a {@code String}.
     *
     * @return The coordinates of the {@code Point}, as a {@code String}.
     */
    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
