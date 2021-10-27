package tech.fastj.graphics.gradients;

import java.awt.Paint;

/**
 * The top-level interface for all gradient builders in the package {@link
 * tech.fastj.graphics.util}.
 */
public interface GradientBuilder {
  /**
   * Builds a {@code MultipleGradientPaint} object based on the data provided by other methods in
   * the builder.
   *
   * @return The resulting {@code MultipleGradientPaint} object.
   */
  Paint build();
}
