/**
 * FastJ's display and canvas system.
 *
 * <ul>
 *     <li>{@link tech.fastj.graphics.display.Display Base display interface} for all FastJ displays.</li>
 *     <li>{@link tech.fastj.graphics.display.SimpleDisplay SimpleDisplay} - The default display implementation. </li>
 *     <li>{@link tech.fastj.graphics.display.FastJCanvas The core rendering canvas} FastJ uses.</li>
 * </ul>
 * <p>
 * If you would like to create and use your own display system, create a class implementing
 * {@link tech.fastj.graphics.display.Display}, and add it to the engine on initialization:
 * {@link tech.fastj.engine.FastJEngine#setCustomDisplay(tech.fastj.graphics.display.Display)}.
 */
package tech.fastj.graphics.display;