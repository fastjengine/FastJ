/**
 * FastJ game objects.
 *
 * <ul>
 *     <li>{@link tech.fastj.graphics.game.GameObject} - the abstract class base for all game objects.</li>
 *     <li>{@link tech.fastj.graphics.game.Polygon2D} renders single polygons.</li>
 *     <li>{@link tech.fastj.graphics.game.Model2D} renders 2D models (multiple polygons in tandem).</li>
 *     <li>{@link tech.fastj.graphics.game.Text2D} renders text.</li>
 *     <li>{@link tech.fastj.graphics.game.Sprite2D} renders sprites and simple sprite animations.</li>
 * </ul>
 * <p>
 * Controlling {@link tech.fastj.graphics.game.GameObject GameObjects} can be simplified with the use of
 * {@link tech.fastj.systems.behaviors.Behavior Behaviors}, which provide an easy way to control the state of a given
 * game object.
 * <ul>
 *     <li>{@link tech.fastj.graphics.game.GameObject#addBehavior(tech.fastj.systems.behaviors.Behavior, tech.fastj.systems.behaviors.BehaviorHandler) Adding a behavior}</li>
 *     <li>{@link tech.fastj.graphics.game.GameObject#removeBehavior(tech.fastj.systems.behaviors.Behavior, tech.fastj.systems.behaviors.BehaviorHandler) Removing a behavior}</li>
 *     <li>{@link tech.fastj.systems.behaviors.Behavior#simpleRotation(float) Simple rotation behavior}</li>
 *     <li>{@link tech.fastj.systems.behaviors.Behavior#simpleScale(tech.fastj.math.Pointf) Simple scaling behavior}</li>
 *     <li>{@link tech.fastj.systems.behaviors.Behavior#simpleTranslation(tech.fastj.math.Pointf) Simple translation behavior}</li>
 * </ul>
 */
package tech.fastj.graphics.game;