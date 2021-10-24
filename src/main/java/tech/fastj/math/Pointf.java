package tech.fastj.math;

import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

/**
 * Class that defines a point in 2D space, using floats.
 *
 * @author Andrew Dey
 * @since 1.0.0
 */
public class Pointf {

    /** {@code Pointf} representing the origin as a {@code float}: {@code (0f, 0f)}. */
    public static final Pointf Origin = new Pointf();

    /** The x value of the {@link Pointf}. */
    public float x;
    /** The y value of the {@link Pointf}. */
    public float y;

    /** {@code Pointf} constructor, where its x and y values are set to zero. */
    public Pointf() {
        this(0f, 0f);
    }

    /**
     * {@code Pointf} constructor, where the values for the newly created {@code Pointf} are set to the float
     * parameter's values.
     *
     * @param xy Value to set the x and y of this {@code Pointf} to.
     */
    public Pointf(float xy) {
        this(xy, xy);
    }

    /**
     * {@code Pointf} constructor, where the values for the newly created {@code Pointf} are set to the parameter {@code
     * Pointf}'s values.
     *
     * @param p {@code Pointf} to set the x and y of this {@code Pointf} to.
     */
    public Pointf(Pointf p) {
        this(p.x, p.y);
    }

    /**
     * {@code Pointf} constructor, where the values for the newly created {@code Pointf} are set to the parameter {@code
     * Point}'s values.
     *
     * @param p {@code Point} to set the x and y of this {@code Pointf} to.
     */
    public Pointf(Point p) {
        this(p.x, p.y);
    }

    /**
     * {@code Pointf} constructor, where the values for the newly created {@code Pointf} are set to the specified x and
     * y parameters.
     *
     * @param xVal Value to define the x coordinate for this {@code Pointf} object.
     * @param yVal Value to define the y coordinate for this {@code Pointf} object.
     */
    public Pointf(float xVal, float yVal) {
        x = xVal;
        y = yVal;
    }

    /**
     * Static method to add two {@code Pointf}s (from the parameters specified) together, and return a new {@code
     * Pointf} object.
     *
     * @param p1 The first {@code Pointf} used for addition.
     * @param p2 The second {@code Pointf} used for addition.
     * @return Returns a new {@code Pointf} with coordinates equal to the added values from the two {@code Pointf}s.
     */
    public static Pointf add(Pointf p1, Pointf p2) {
        return new Pointf(p1.x + p2.x, p1.y + p2.y);
    }

    /**
     * Static method to add a {@code Pointf} object by a float value, and return a new {@code Pointf}.
     *
     * @param p The {@code Pointf} used for addition.
     * @param f float value used for addition.
     * @return Returns a new {@code Pointf} with coordinates equal to the added values from the {@code Pointf} and the
     * float value.
     */
    public static Pointf add(Pointf p, float f) {
        return new Pointf(p.x + f, p.y + f);
    }

    /**
     * Static method to subtract two Points (from the parameters specified) together, and return a new {@code Pointf}
     * object.
     *
     * @param p1 The first {@code Pointf} used for subtraction; the {@code Pointf} acting as the first value in
     *           subtraction.
     * @param p2 The second {@code Pointf} used for subtraction; the {@code Pointf} acting as the second value in
     *           subtraction.
     * @return Returns a new {@code Pointf} with coordinates equal to the subtracted values from the two {@code
     * Pointf}s.
     */
    public static Pointf subtract(Pointf p1, Pointf p2) {
        return new Pointf(p1.x - p2.x, p1.y - p2.y);
    }

    /**
     * Static method to subtract a {@code Pointf} object by a float value, and return a new {@code Pointf}.
     *
     * @param p The {@code Pointf} used for subtraction; the {@code Pointf} acting as the first value in subtraction.
     * @param f float value used for subtraction; the second value used in subtraction.
     * @return Returns a new {@code Pointf} with coordinates equal to the subtracted values from the {@code Pointf} and
     * the float value.
     */
    public static Pointf subtract(Pointf p, float f) {
        return new Pointf(p.x - f, p.y - f);
    }

    /**
     * Static method to multiply two {@code Pointf} objects (from the parameters specified) together, and return a new
     * {@code Pointf}.
     *
     * @param p1 The first {@code Pointf} used for multiplication.
     * @param p2 The second {@code Pointf} used for multiplication.
     * @return Returns a new {@code Pointf} with coordinates equal to the multiplied values from the two {@code
     * Pointf}s.
     */
    public static Pointf multiply(Pointf p1, Pointf p2) {
        return new Pointf(p1.x * p2.x, p1.y * p2.y);
    }

    /**
     * Static method to multiply a {@code Pointf} object by a float value, and return a new {@code Pointf}.
     *
     * @param p The {@code Pointf} used for multiplication.
     * @param f float value used for multiplication.
     * @return Returns a new {@code Pointf} with coordinates equal to the multiplied values from the {@code Pointf} and
     * the float value.
     */
    public static Pointf multiply(Pointf p, float f) {
        return new Pointf(p.x * f, p.y * f);
    }

    /**
     * Static method to divide two {@code Pointf} objects (from the parameters specified) together, and return a new
     * {@code Pointf}.
     *
     * @param p1 The first {@code Pointf} used for division; the {@code Pointf} acting as the first value in division.
     * @param p2 The second {@code Pointf} used for division; the {@code Pointf} acting as the second value in
     *           division.
     * @return Returns a new {@code Pointf} with coordinates equal to the divided values from the two {@code Pointf}s.
     */
    public static Pointf divide(Pointf p1, Pointf p2) {
        return new Pointf(p1.x / p2.x, p1.y / p2.y);
    }

    /**
     * Static method to divide a {@code Pointf} object by a float value, and return a new {@code Pointf}.
     *
     * @param p The {@code Pointf} used for division; the {@code Pointf} acting as the first value in division.
     * @param f float value used for division; the second value used in division.
     * @return Returns a new {@code Pointf} with coordinates equal to the divided values from the {@code Pointf} and the
     * float value.
     */
    public static Pointf divide(Pointf p, float f) {
        return new Pointf(p.x / f, p.y / f);
    }

    /**
     * Calculates and returns the dot product of the two specified {@code Pointf}s.
     *
     * @param p  The first of two {@code Pointf}s to calculate the dot product with.
     * @param p1 The second of two {@code Pointf}s to calculate the dot product with.
     * @return The calculated dot product.
     */
    public static float dot(Pointf p, Pointf p1) {
        return (p.x * p1.x) + (p.y * p1.y);
    }

    /**
     * Calculates and returns the cross product of the two specified {@code Pointf}s.
     * <p>
     * Unlike calculating the cross product of two 3-dimensional vectors, this cross product calculates as if the two
     * {@code Pointf}s were 3-dimensional vectors with Z values implicitly set to {@code 0f}.
     *
     * @param p  The first of two {@code Pointf}s to calculate the cross product with.
     * @param p1 The second of two {@code Pointf}s to calculate the cross product with.
     * @return The calculated cross product.
     */
    public static float cross(Pointf p, Pointf p1) {
        return (p.x * p1.y) - (p.y * p1.x);
    }

    /**
     * Creates a rotated version of the {@code Pointf} based on the provided angle.
     * <p>
     * This rotation method rotates about the origin, {@code (0, 0)}. If you need to rotate about a point that is not
     * the origin, use {@link #rotate(float, Pointf)}.
     *
     * @param p     The point to rotate.
     * @param angle The angle to rotate by, in degrees.
     * @return A rotated version of the original {@code Pointf}.
     */
    public static Pointf rotate(Pointf p, float angle) {
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
     * {@link #rotate(Pointf, float)}.
     *
     * @param p      The point to rotate.
     * @param angle  The angle to rotate by, in degrees.
     * @param center The point to rotate around.
     * @return A rotated version of the original {@code Pointf}.
     */
    public static Pointf rotate(Pointf p, float angle, Pointf center) {
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
     * Calculates and returns the signed angle between the specified {@code Pointf}s.
     *
     * @param p  The first of two {@code Pointf}s to calculate the angle with.
     * @param p1 The second of two {@code Pointf}s to calculate the angle with.
     * @return The calculated angle, in radians.
     */
    public static float signedAngle(Pointf p, Pointf p1) {
        float dotProduct = (p.x * p1.x) + (p.y * p1.y);
        float determinant = (p.x * p1.y) - (p.y * p1.x);

        return (float) Math.atan2(determinant, dotProduct);
    }

    /**
     * Calculates and returns the angle between the specified {@code Pointf}s.
     *
     * @param p  The first of two {@code Pointf}s to calculate the angle with.
     * @param p1 The second of two {@code Pointf}s to calculate the angle with.
     * @return The calculated angle, in radians.
     */
    public static float angle(Pointf p, Pointf p1) {
        float dotProduct = (p.x * p1.x) + (p.y * p1.y);
        float magnitudeProduct = (float) Math.sqrt((p.x * p.x) + (p.y * p.y)) * (float) Math.sqrt((p1.x * p1.x) + (p1.y * p1.y));

        return (float) Math.acos(dotProduct / magnitudeProduct);
    }

    /**
     * Creates a new {@link Pointf}, applying to it the linear interpolation of the two {@link Pointf}s specified.
     *
     * @param p  The starting value.
     * @param p1 The ending value.
     * @param t  The interpolation value to work with (preferably within a range of 0.0 to 1.0). This value will be used
     *           for linear interpolation of the {@code Pointf}'s {@code x} and {@code y} values.
     * @return A new {@code Pointf}, linearly interpolated as specified.
     * @see Maths#lerp(float, float, float)
     * @since 1.5.0
     */
    public static Pointf lerp(Pointf p, Pointf p1, float t) {
        return new Pointf(
                Maths.lerp(p.x, p1.x, t),
                Maths.lerp(p.y, p1.y, t)
        );
    }

    /**
     * Creates a new {@link Pointf}, applying to it the linear interpolation of the two {@link Pointf}s specified.
     *
     * @param p  The starting value.
     * @param p1 The ending value.
     * @param t1 The first interpolation value to work with (preferably within a range of 0.0 to 1.0). This value will
     *           be used for linear interpolation of the {@code Pointf}'s {@code x} value.
     * @param t2 The second interpolation value to work with (preferably within a range of 0.0 to 1.0). This value will
     *           be used for linear interpolation of the {@code Pointf}'s {@code y} value.
     * @return A new {@code Pointf}, linearly interpolated as specified.
     * @see Maths#lerp(float, float, float)
     * @since 1.5.0
     */
    public static Pointf lerp(Pointf p, Pointf p1, float t1, float t2) {
        return new Pointf(
                Maths.lerp(p.x, p1.x, t1),
                Maths.lerp(p.y, p1.y, t2)
        );
    }

    /**
     * Calculates the linear interpolation values based on the provided {@code p} and {@code p1} {@code Pointf} range,
     * and the value {@code v}.
     *
     * @param p  The starting value.
     * @param p1 The ending value.
     * @param v  The value representing the "result" of linear interpolation between the two {@code Pointf}s. This value
     *           will be used to calculate inverse linear interpolation for the {@code Pointf}s' {@code x} and {@code y}
     *           values.
     * @return An array of floats containing the resulting inverse linear interpolations of the {@code Pointf}s' {@code
     * x} and {@code y} values.
     * @see Maths#inverseLerp(float, float, float)
     * @since 1.5.0
     */
    public static float[] inverseLerp(Pointf p, Pointf p1, float v) {
        return new float[]{
                Maths.inverseLerp(p.x, p1.x, v),
                Maths.inverseLerp(p.y, p1.y, v)
        };
    }

    /**
     * Calculates the linear interpolation values based on the provided {@code p} and {@code p1} {@code Pointf} range,
     * and the values {@code v1} and {@code v2}.
     *
     * @param p  The starting value.
     * @param p1 The ending value.
     * @param v1 The first value representing the "result" of linear interpolation between the two {@code Pointf}s. This
     *           value will be used to calculate inverse linear interpolation for the {@code Pointf}s' {@code x} value.
     * @param v2 The second value representing the "result" of linear interpolation between the two {@code Pointf}s.
     *           This value will be used to calculate inverse linear interpolation for the {@code Pointf}s' {@code y}
     *           value.
     * @return An array of floats containing the resulting inverse linear interpolations of the {@code Pointf}s' {@code
     * x} and {@code y} values.
     * @see Maths#inverseLerp(float, float, float)
     * @since 1.5.0
     */
    public static float[] inverseLerp(Pointf p, Pointf p1, float v1, float v2) {
        return new float[]{
                Maths.inverseLerp(p.x, p1.x, v1),
                Maths.inverseLerp(p.y, p1.y, v2)
        };
    }

    /**
     * Sets the x and y values of this {@code Pointf} object to the values specified.
     *
     * @param newX {@code float} value used to set the new x value for this {@code Pointf} object.
     * @param newY {@code float} value used to set the new y value for this {@code Pointf} object.
     */
    public void set(float newX, float newY) {
        x = newX;
        y = newY;
    }

    /**
     * Sets the x and y values of this {@code Pointf} object to the values specified.
     * 
     * @param xy {@code float} value used to set the new x and y values for this {@code Pointf} object.
     */
    public void set(float xy) {
        x = xy;
        y = xy;
    }

    /**
     * Determines whether a specified Rectangle2D object intersects with this {@code Pointf} object.
     *
     * @param metrics Rectangle2D object used to check for intersection between this {@code Pointf} object and it.
     * @return Returns a boolean value that defines whether the Rectangle2D and this {@code Pointf} object intersect.
     */
    public boolean intersects(Rectangle2D metrics) {
        if (metrics.getX() <= x && x <= (metrics.getX() + metrics.getWidth())) {
            return (metrics.getY()) <= y && y <= (metrics.getY() + metrics.getHeight());
        }
        return false;
    }

    /**
     * Determines whether a specified Path2D object intersects with this {@code Pointf} object.
     *
     * @param path Path2D object used to check for intersection between this {@code Pointf} object and it.
     * @return Returns a boolean value that defines whether the Path2D and this {@code Pointf} object intersect.
     */
    public boolean intersects(Path2D path) {
        return Path2D.contains(path.getPathIterator(null), x, y, 1, 1);
    }

    /**
     * Gets a copy of the {@code Pointf}, and returns the copy.
     *
     * @return The new {@code Pointf}.
     */
    public Pointf copy() {
        return new Pointf(x, y);
    }

    /** Resets this {@code Pointf}'s x and y values to zero. */
    public void reset() {
        x = 0;
        y = 0;
    }

    /**
     * Adds the values of this {@code Pointf} to the specified {@code Pointf}, and returns a new {@code Pointf} with the
     * modified values.
     *
     * @param p {@code Pointf} to add this {@code Pointf} to.
     * @return Returns this {@code Pointf}, with the modified values.
     */
    public Pointf add(Pointf p) {
        x += p.x;
        y += p.y;

        return this;
    }

    /**
     * Adds the values of this {@code Pointf} to the specified float value, and returns a new {@code Pointf} with the
     * modified values.
     *
     * @param f Value to add this {@code Pointf} to.
     * @return Returns this {@code Pointf}, with the modified values.
     */
    public Pointf add(float f) {
        x += f;
        y += f;

        return this;
    }

    /**
     * Subtracts the values of this {@code Pointf} by the specified {@code Pointf}, and returns a new {@code Pointf}
     * with the modified values.
     *
     * @param p {@code Pointf} to subtract this {@code Pointf} by.
     * @return Returns this {@code Pointf}, with the modified values.
     */
    public Pointf subtract(Pointf p) {
        x -= p.x;
        y -= p.y;

        return this;
    }

    /**
     * Subtracts the values of this {@code Pointf} by the specified float value, and returns a new {@code Pointf} with
     * the modified values.
     *
     * @param f Value to subtract this {@code Pointf} by.
     * @return Returns this {@code Pointf}, with the modified values.
     */
    public Pointf subtract(float f) {
        x -= f;
        y -= f;

        return this;
    }

    /**
     * Multiplies the values of this {@code Pointf} by the specified {@code Pointf}, and returns a new {@code Pointf}
     * with the modified values.
     *
     * @param p {@code Pointf} to multiply this {@code Pointf} by.
     * @return Returns this {@code Pointf}, with the modified values.
     */
    public Pointf multiply(Pointf p) {
        x *= p.x;
        y *= p.y;

        return this;
    }

    /**
     * Multiplies the values of this {@code Pointf} by the specified float value, and returns a new {@code Pointf} with
     * the modified values.
     *
     * @param f Value to multiply this {@code Pointf} by.
     * @return Returns this {@code Pointf}, with the modified values.
     */
    public Pointf multiply(float f) {
        x *= f;
        y *= f;

        return this;
    }

    /**
     * Divides the values of this {@code Pointf} by the specified {@code Pointf}, and returns a new {@code Pointf} with
     * the modified values.
     *
     * @param p {@code Pointf} to divide this {@code Pointf} by.
     * @return Returns this {@code Pointf}, with the modified values.
     */
    public Pointf divide(Pointf p) {
        x /= p.x;
        y /= p.y;

        return this;
    }

    /**
     * Divides the values of this {@code Pointf} by the specified float value, and returns a new {@code Pointf} with the
     * modified values.
     *
     * @param f Value to divide this {@code Pointf} by.
     * @return Returns this {@code Pointf}, with the modified values.
     */
    public Pointf divide(float f) {
        x /= f;
        y /= f;

        return this;
    }

    /**
     * Rotates the {@code Pointf} based on the provided angle.
     * <p>
     * This rotation method rotates about the origin, {@code (0, 0)}. If you need to rotate about a point that is not
     * the origin, use {@link #rotate(float, Pointf)}.
     *
     * @param angle The angle to rotate by, in degrees.
     * @return The {@code Pointf} with the rotated values.
     */
    public Pointf rotate(float angle) {
        float angleInRadians = (float) Math.toRadians(angle);
        float sineOfAngle = (float) Math.sin(angleInRadians);
        float cosineOfAngle = (float) Math.cos(angleInRadians);

        float rotatedX = (x * cosineOfAngle) + (y * sineOfAngle);
        float rotatedY = (-x * sineOfAngle) + (y * cosineOfAngle);
        x = rotatedX;
        y = rotatedY;

        return this;
    }

    /**
     * Rotates the {@code Pointf} based on the provided angle and center point.
     * <p>
     * This rotation method rotates about the specified {@code center}. If you need to rotate about the origin, use
     * {@link #rotate(float)}.
     *
     * @param angle  The angle to rotate by, in degrees.
     * @param center The point to rotate around.
     * @return The {@code Pointf} with the rotated values.
     */
    public Pointf rotate(float angle, Pointf center) {
        float angleInRadians = (float) Math.toRadians(angle);
        float sineOfAngle = (float) Math.sin(angleInRadians);
        float cosineOfAngle = (float) Math.cos(angleInRadians);

        x -= center.x;
        y -= center.y;

        float rotatedX = (x * cosineOfAngle) + (y * sineOfAngle);
        float rotatedY = (-x * sineOfAngle) + (y * cosineOfAngle);

        x = rotatedX + center.x;
        y = rotatedY + center.y;

        return this;
    }

    /**
     * Calculates and returns the square magnitude of the {@code Pointf}.
     *
     * @return The square magnitude of the {@code Pointf}'s {@link #x} and {@link #y} values.
     */
    public float squareMagnitude() {
        return (x * x) + (y * y);
    }

    /**
     * Calculates and returns the {@code length}, or {@code magnitude}, of the {@code Pointf}.
     *
     * @return The magnitude, calculated using floating-point versions of the {@code Pointf}'s {@link #x} and {@link #y}
     * values.
     */
    public float magnitude() {
        return (float) Math.sqrt((x * x) + (y * y));
    }

    /**
     * Creates a normalized version of the {@code Pointf}.
     * <p>
     * This method does not modify the contents of the original {@code Pointf}.
     *
     * @return A normalized version of the {@code Pointf}.
     */
    public Pointf normalized() {
        float magnitude = (float) Math.sqrt((x * x) + (y * y));

        if (magnitude == 0f) {
            return Pointf.Origin.copy();
        }

        float normalizedX = x / magnitude;
        float normalizedY = y / magnitude;
        return new Pointf(normalizedX, normalizedY);
    }

    /**
     * Compares the {@code Pointf} with a {@link Point}, and returns whether their {@code x} and {@code y} values are
     * equal.
     *
     * @param other The {@code Point} to compare against.
     * @return Whether the two's {@code x} and {@code y} values are equal.
     */
    public boolean equalsPoint(Point other) {
        return Maths.floatEquals(other.x, x) && Maths.floatEquals(other.y, y);
    }

    /**
     * Compares two {@code Pointf}s, and returns whether they are equal.
     *
     * @param other The {@code Pointf} to check for equality against.
     * @return Whether the {@code Pointf}s are equal.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Pointf pointf = (Pointf) other;
        return Maths.floatEquals(pointf.x, x) && Maths.floatEquals(pointf.y, y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Formats the {@code Pointf}'s coordinates in a {@code String}.
     *
     * @return The coordinates of the {@code Pointf}, as a {@code String}.
     */
    @Override
    public String toString() {
        return "Pointf{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
