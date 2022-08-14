package tech.fastj.graphics.game;

import tech.fastj.graphics.gradients.Gradients;
import tech.fastj.math.Pointf;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.RadialGradientPaint;
import java.util.Objects;

/** A builder class for creating {@link Light2D} objects. */
public class Light2DBuilder {
    private final boolean shouldRender;
    private final Pointf location;
    private final Pointf size;

    private AlphaComposite alphaComposite = AlphaComposite.SrcOver;
    private RadialGradientPaint gradientPaint;

    Light2DBuilder(Pointf location, Pointf size, boolean shouldRender) {
        this.location = location;
        this.size = size;
        this.shouldRender = shouldRender;
    }

    public Light2DBuilder withGradient(RadialGradientPaint gradientPaint) {
        this.gradientPaint = Objects.requireNonNull(gradientPaint, "The radial gradient paint should not be null.");
        return this;
    }

    public Light2DBuilder withGradient(Color centerColor, Color outerColor) {
        Objects.requireNonNull(centerColor, "The center color should not be null.");
        Objects.requireNonNull(outerColor, "The outer color should not be null.");
        this.gradientPaint = Gradients.radialGradient(Pointf.add(location, size.copy().divide(2f)), evaluateRadius())
            .withColors(centerColor, outerColor)
            .build();
        return this;
    }

    public Light2DBuilder withAlphaComposite(AlphaComposite alphaComposite) {
        this.alphaComposite = Objects.requireNonNull(alphaComposite, "The alpha composite should not be null.");
        return this;
    }

    /**
     * Creates a new {@link Light2D} object, using the data provided by earlier method calls.
     *
     * @return The resulting {@code Light2D}.
     */
    public Light2D build() {
        return (Light2D) new Light2D(location, size, gradientPaint, alphaComposite)
            .setShouldRender(shouldRender);
    }

    private float evaluateRadius() {
        Pointf horizontalSide = Pointf.subtract(Pointf.add(location, size.x, 0f), location);
        Pointf verticalSide = Pointf.subtract(Pointf.add(location, 0f, size.y), location);

        Pointf smallestSide = horizontalSide.squareMagnitude() < verticalSide.squareMagnitude() ? horizontalSide : verticalSide;
        return smallestSide.magnitude() / 2.0f;
    }
}
