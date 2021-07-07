/**
 * The FastJ Library in its entirety.
 * <p>
 * FastJ does not use any external dependencies -- it relies entirely on Java 11's {@code java.desktop} module, using
 * java2d to display output.
 * <p>
 * This game library is split into 5 parts:
 * <ol>
 *     <li>{@link tech.fastj.engine} -- The package containing the base of the game engine.</li>
 *     <li>{@link tech.fastj.graphics} -- Game objects and other graphical content.</li>
 *     <li>{@link tech.fastj.input} -- User input via keyboard/mouse.</li>
 *     <li>{@link tech.fastj.math} -- FastJ's supplementary mathematics and vector library.</li>
 *     <li>{@link tech.fastj.systems} -- Audio, behaviors, and other miscellaneous features of FastJ.</li>
 * </ol>
 * <p>
 * For more information, check out
 * <a href="https://github.com/fastjengine/FastJ" target="_blank">the github repository.</a>
 */
module fastj.library {
    requires transitive java.desktop;

    exports tech.fastj.engine;
    exports tech.fastj.math;

    exports tech.fastj.graphics;
    exports tech.fastj.graphics.display;
    exports tech.fastj.graphics.game;
    exports tech.fastj.graphics.gradients;
    exports tech.fastj.graphics.io;
    exports tech.fastj.graphics.ui;
    exports tech.fastj.graphics.ui.elements;
    exports tech.fastj.graphics.util;

    exports tech.fastj.input;
    exports tech.fastj.input.keyboard;
    exports tech.fastj.input.mouse;

    exports tech.fastj.systems.audio;
    exports tech.fastj.systems.behaviors;
    exports tech.fastj.systems.control;
    exports tech.fastj.systems.tags;
}
