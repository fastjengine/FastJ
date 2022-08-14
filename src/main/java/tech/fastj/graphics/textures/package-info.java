/**
 * Texture creation system, to give {@link tech.fastj.graphics.game.Polygon2D polygons} and other supporting game objects/ui elements
 * textures/images as their {@link java.awt.Paint fill 'color'}.
 * <p>
 * Textures are based on {@link java.awt.image.BufferedImage images}.
 *
 * <ul>
 *     <li>{@link tech.fastj.graphics.textures.Textures#create(java.nio.file.Path, java.awt.geom.Rectangle2D) Creating a texture} from an image in a file path</li>
 *     <li>{@link tech.fastj.graphics.textures.Textures#create(java.awt.image.BufferedImage, java.awt.geom.Rectangle2D) Creating a texture} from a {@link java.awt.image.BufferedImage}</li>
 *     <li>{@link tech.fastj.graphics.game.Polygon2D#setFill(java.awt.Paint) Setting a texture as a Polygon2D's paint}</li>
 * </ul>
 */
package tech.fastj.graphics.textures;