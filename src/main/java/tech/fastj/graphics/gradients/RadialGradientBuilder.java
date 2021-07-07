package tech.fastj.graphics.gradients;

import tech.fastj.math.Pointf;
import tech.fastj.graphics.Boundary;
import tech.fastj.graphics.Drawable;

import java.awt.Color;
import java.awt.RadialGradientPaint;
import java.util.Arrays;

/** A builder class for creating {@link RadialGradientPaint} objects. */
public class RadialGradientBuilder implements GradientBuilder {

    private Pointf center;
    private float radius;

    private final Color[] colors;
    private int count;

    /** Initializes a {@code RadialGradientBuilder}'s internals. */
    RadialGradientBuilder(Drawable drawable) {
        colors = new Color[Gradients.MaximumColorCount];
        position(drawable);
    }

    /** Initializes a {@code RadialGradientBuilder}'s internals. */
    RadialGradientBuilder(Pointf center, float radius) {
        colors = new Color[Gradients.MaximumColorCount];
        position(center, radius);
    }

    /**
     * Sets the centerpoint and radius for the builder, based on the provided {@link Drawable}.
     *
     * @param drawable The drawable to get the center and radius from.
     */
    private void position(Drawable drawable) {
        this.center = drawable.getCenter();

        Pointf drawableSize = Pointf.subtract(this.center, drawable.getBound(Boundary.TopLeft));
        this.radius = (float) Math.hypot(drawableSize.x, drawableSize.y);
    }

    /**
     * Sets the centerpoint and radius for the builder, based on the provided {@link Pointf} and float values.
     *
     * @param center The {@code Pointf} defining the centerpoint for the gradient.
     * @param radius The {@code float} defining the radius for the gradient.
     */
    private void position(Pointf center, float radius) {
        Gradients.minimumRadiusValueCheck(radius);

        this.center = center;
        this.radius = radius;
    }

    /**
     * Adds a {@link Color} to the builder to be used in the resulting gradient.
     * <p>
     * The amount of colors used in a {@link RadialGradientBuilder} not exceed {@link Gradients#MaximumColorCount}.
     *
     * @param color The {@code Color} being added.
     * @return The {@code LinearGradientBuilder}, for method chaining.
     */
    public RadialGradientBuilder withColor(Color color) {
        Gradients.colorLimitCheck(count);
        Gradients.nullColorCheck(color);

        colors[count] = color;
        count++;
        return this;
    }

    /**
     * Adds several {@link Color}s to the builder to be used in the resulting gradient.
     * <p>
     * The amount of colors used in a {@link RadialGradientBuilder} cannot exceed {@link Gradients#MaximumColorCount}.
     *
     * @param colors The {@code Color}s being added. This parameter must not cause the builder to increase over 8
     *               colors.
     * @return The {@code RadialGradientBuilder}, for method chaining.
     */
    public RadialGradientBuilder withColors(Color... colors) {
        Gradients.colorLimitCheck(count, colors);
        Gradients.nullColorCheck(colors);

        System.arraycopy(colors, count, this.colors, 0, count + colors.length);
        count += colors.length;
        return this;
    }

    /**
     * Creates a new {@link RadialGradientPaint} object, using the data provided by other method calls.
     *
     * @return The resulting {@code RadialGradientPaint}.
     */
    @Override
    public RadialGradientPaint build() {
        Gradients.minimumColorCheck(count, colors);

        float[] fractions = Gradients.generateIntervals(count);
        return new RadialGradientPaint(center.x, center.y, radius, Arrays.copyOf(fractions, count), Arrays.copyOf(colors, count));
    }

    /**
     * Gets a new instance of a {@link RadialGradientBuilder}.
     *
     * @return The {@code RadialGradientBuilder} instance.
     */
    static RadialGradientBuilder builder(Pointf center, float radius) {
        return new RadialGradientBuilder(center, radius);
    }

    /**
     * Gets a new instance of a {@link RadialGradientBuilder}.
     *
     * @return The {@code RadialGradientBuilder} instance.
     */
    static RadialGradientBuilder builder(Drawable drawable) {
        return new RadialGradientBuilder(drawable);
    }
}
