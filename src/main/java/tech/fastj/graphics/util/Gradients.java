package tech.fastj.graphics.util;

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
    public static LinearGradientBuilder linearGradient() {
        return LinearGradientBuilder.builder();
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
