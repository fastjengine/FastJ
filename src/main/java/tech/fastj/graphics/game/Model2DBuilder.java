package tech.fastj.graphics.game;

import tech.fastj.math.Pointf;
import tech.fastj.graphics.Transform2D;

import java.util.Objects;

/** A builder class for creating {@link Model2D} objects. */
public class Model2DBuilder {

    private final Polygon2D[] polygons;
    private final boolean shouldRender;

    private Pointf translation = Transform2D.DefaultTranslation.copy();
    private float rotation = Transform2D.DefaultRotation;
    private Pointf scale = Transform2D.DefaultScale.copy();

    /**
     * {@code Model2DBuilder} constructor, taking in an array of polygons and a {@code shouldRender} boolean.
     *
     * @param polygons     The {@code Polygon2D[]} of polygons to use for the resulting {@code Model2D}.
     * @param shouldRender The "should render" {@code boolean} to use for the resulting {@code Model2D}.
     */
    Model2DBuilder(Polygon2D[] polygons, boolean shouldRender) {
        this.polygons = Objects.requireNonNull(polygons, "The polygon array should not be null.");
        this.shouldRender = shouldRender;
    }

    /**
     * Sets the builder's transformation (translation, rotation, scale) values.
     *
     * @param translation The translation {@code Pointf} to be used in the resulting {@code Model2D}.
     * @param rotation    The rotation {@code float} to be used in the resulting {@code Model2D}.
     * @param scale       The scale {@code Pointf} to be used in the resulting {@code Model2D}.
     * @return The {@code Model2DBuilder}, for method chaining.
     */
    public Model2DBuilder withTransform(Pointf translation, float rotation, Pointf scale) {
        this.translation = Objects.requireNonNull(translation, "The translation value must not be null.");
        this.scale = Objects.requireNonNull(scale, "The scale value must not be null.");
        if (Float.isNaN(rotation)) {
            throw new NumberFormatException("The rotation value must not be NaN.");
        }
        this.rotation = rotation;
        return this;
    }

    /**
     * Creates a new {@link Model2D} object, using the data provided by earlier method calls.
     *
     * @return The resulting {@code Model2D}.
     */
    public Model2D build() {
        return (Model2D) new Model2D(polygons)
                .setShouldRender(shouldRender)
                .setTransform(translation, rotation, scale);
    }
}
