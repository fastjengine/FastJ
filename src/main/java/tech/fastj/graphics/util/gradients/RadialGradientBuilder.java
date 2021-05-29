package tech.fastj.graphics.util.gradients;

import tech.fastj.engine.FastJEngine;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.Boundary;
import tech.fastj.graphics.Drawable;

import java.awt.Color;
import java.awt.RadialGradientPaint;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** A builder class for creating {@link RadialGradientPaint} objects. */
public class RadialGradientBuilder implements GradientBuilder {

    private Pointf center;
    private float radius;

    private final List<Color> colors;
    private int count;

    /** Initializes a {@code RadialGradientBuilder}'s internals. */
    RadialGradientBuilder() {
        colors = new ArrayList<>(Gradients.ColorLimit);
    }

    /**
     * Sets the centerpoint and radius for the builder, based on the provided {@link Drawable}.
     *
     * @param drawable The drawable to get the center and radius from.
     * @return The {@code RadialGradientBuilder}, for method chaining.
     */
    public RadialGradientBuilder position(Drawable drawable) {
        this.center = drawable.getCenter();

        Pointf drawableSize = Pointf.subtract(this.center, drawable.getBound(Boundary.TopLeft));
        this.radius = (float) Math.hypot(drawableSize.x, drawableSize.y);

        return this;
    }

    /**
     * Sets the centerpoint and radius for the builder, based on the provided {@link Pointf} and float values.
     *
     * @param center The {@code Pointf} defining the centerpoint for the gradient.
     * @param radius The {@code float} defining the radius for the gradient.
     * @return The {@code LinearGradientBuilder}, for method chaining.
     */
    public RadialGradientBuilder position(Pointf center, float radius) {
        if (radius <= 0f) {
            FastJEngine.error(
                    GradientBuilder.GradientCreationError,
                    new IllegalStateException("The radius for a gradient must be larger than 0.")
            );
        }
        this.center = center;
        this.radius = radius;

        return this;
    }

    /**
     * Adds a {@link Color} to the builder to be used in the resulting gradient.
     * <p>
     * The amount of colors used in a {@link RadialGradientBuilder} not exceed {@link Gradients#ColorLimit}.
     *
     * @param color The {@code Color} being added.
     * @return The {@code LinearGradientBuilder}, for method chaining.
     */
    public RadialGradientBuilder withColor(Color color) {
        if (count == Gradients.ColorLimit) {
            FastJEngine.error(GradientBuilder.GradientCreationError,
                    new IllegalStateException("Gradients cannot contain more than " + Gradients.ColorLimit + " colors.")
            );
        }

        colors.add(color);
        count++;
        return this;
    }

    /**
     * Creates a new {@link RadialGradientPaint} object, using the data provided by other method calls.
     *
     * @return The resulting {@code RadialGradientPaint}.
     */
    @Override
    public RadialGradientPaint build() {
        if (count < 2) {
            FastJEngine.error(
                    GradientBuilder.GradientCreationError,
                    new IllegalStateException(
                            "Gradients must contain at least 2 colors."
                                    + System.lineSeparator()
                                    + "This gradient builder only contains the following gradients: " + colors
                    )
            );
        }

        float[] fractions = generateIntervals(colors.size());
        System.out.println(center);
        System.out.println(radius);
        System.out.println(Arrays.toString(fractions));
        System.out.println(colors);
        return new RadialGradientPaint(center.x, center.y, radius, fractions, colors.toArray(new Color[0]));
    }

    /**
     * Gets a new instance of a {@link RadialGradientBuilder}.
     *
     * @return The {@code RadialGradientBuilder} instance.
     */
    static RadialGradientBuilder builder() {
        return new RadialGradientBuilder();
    }
}
