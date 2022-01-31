/**
 * Gradient creation system, to give {@link tech.fastj.graphics.game.Polygon2D polygons} and other supporting game
 * objects/ui elements gradients as their {@link java.awt.Paint fill 'color'}.
 *
 * <ul>
 *     <li>{@link tech.fastj.graphics.gradients.Gradients#linearGradient(tech.fastj.math.Pointf, tech.fastj.math.Pointf) General linear gradient creation}</li>
 *     <li>{@link tech.fastj.graphics.gradients.Gradients#linearGradient(tech.fastj.graphics.Drawable, tech.fastj.graphics.Boundary, tech.fastj.graphics.Boundary) Linear gradient} creation for a {@link tech.fastj.graphics.Drawable}</li>
 *     <li>{@link tech.fastj.graphics.gradients.Gradients#radialGradient(tech.fastj.math.Pointf, float) General radial gradient creation}</li>
 *     <li>{@link tech.fastj.graphics.gradients.Gradients#radialGradient(tech.fastj.graphics.Drawable) Radial gradient creation} for a {@link tech.fastj.graphics.Drawable}</li>
 *     <li>{@link tech.fastj.graphics.game.Polygon2D#setFill(java.awt.Paint) Setting a gradient as a Polygon2D's paint}</li>
 * </ul>
 * <p>
 * The {@link tech.fastj.graphics.gradients.Gradients Gradients} class also supports random gradient creation. Give it a look!
 */
package tech.fastj.graphics.gradients;