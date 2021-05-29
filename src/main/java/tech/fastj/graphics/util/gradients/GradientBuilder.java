package tech.fastj.graphics.util.gradients;

import tech.fastj.engine.CrashMessages;

import java.awt.MultipleGradientPaint;

/** The top-level interface for all gradient builders in the package {@link tech.fastj.graphics.util}. */
public interface GradientBuilder {

    /** Error message defining that a gradient creation error occurred. */
    String GradientCreationError = CrashMessages.theGameCrashed("a gradient creation error.");

    /**
     * Builds a {@code MultipleGradientPaint} object based on the data provided by other methods in the builder.
     *
     * @return The resulting {@code MultipleGradientPaint} object.
     */
    MultipleGradientPaint build();

    /**
     * Generates an array of linear intervals from {@code 0.0f} to {@code 1.0} inclusive, based on the size provided.
     *
     * @param size The size of the generated array.
     * @return The generated array.
     */
    default float[] generateIntervals(int size) {
        float[] intervals = new float[size];

        for (int i = 0; i < intervals.length; i++) {
            intervals[i] = (1f / (intervals.length - 1)) * (float) (i);
        }
        return intervals;
    }
}
