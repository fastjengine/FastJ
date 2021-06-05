package tech.fastj.graphics.util.gradients;

import tech.fastj.math.Pointf;
import tech.fastj.graphics.Boundary;
import tech.fastj.graphics.Drawable;

import java.awt.GradientPaint;
import java.awt.LinearGradientPaint;
import java.awt.RadialGradientPaint;

/** Class to streamline working with the {@link GradientPaint} class (and its implementations) within FastJ. */
public class Gradients {

    /** The amount of colors allowed in a single {@link LinearGradientBuilder} or {@link RadialGradientBuilder}. */
    public static final int ColorLimit = 8;

    /**
     * Gets a builder instance for creating a {@link LinearGradientPaint}.
     *
     * @return A {@link LinearGradientPaint} builder.
     */
    public static LinearGradientBuilder linearGradient(Drawable drawable, Boundary start, Boundary end) {
        return LinearGradientBuilder.builder(drawable, start, end);
    }

    /**
     * Gets a builder instance for creating a {@link LinearGradientPaint}.
     *
     * @return A {@link LinearGradientPaint} builder.
     */
    public static LinearGradientBuilder linearGradient(Pointf start, Pointf end) {
        return LinearGradientBuilder.builder(start, end);
    }

    /**
     * Gets a builder instance for creating a {@link RadialGradientPaint}.
     *
     * @return A {@link RadialGradientPaint} builder.
     */
    public static RadialGradientBuilder radialGradient() {
        return RadialGradientBuilder.builder();
    }
}
