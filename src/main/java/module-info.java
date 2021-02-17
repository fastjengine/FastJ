/**
 * The FastJ Game Library in its entirety.
 * <p>
 * This game library is split into two main parts: the framework with which to build games, and the engine running it.
 * The library does not use any external dependencies -- it relies only on Java 15's {@code java.desktop} module, mainly
 * using AWT and Swing to display output.
 * <p>
 * For more information, check out
 * <a href="https://github.com/lucasstarsz/FastJ-Engine" target="_blank">the github repository.</a>
 */
module fastj.engine {
    requires java.desktop;

    exports io.github.lucasstarsz.fastj.engine;

    exports io.github.lucasstarsz.fastj.framework;

    exports io.github.lucasstarsz.fastj.framework.graphics;
    exports io.github.lucasstarsz.fastj.framework.graphics.shapes;
    exports io.github.lucasstarsz.fastj.framework.graphics.text;
    exports io.github.lucasstarsz.fastj.framework.graphics.util;

    exports io.github.lucasstarsz.fastj.framework.io;
    exports io.github.lucasstarsz.fastj.framework.io.keyboard;
    exports io.github.lucasstarsz.fastj.framework.io.mouse;

    exports io.github.lucasstarsz.fastj.framework.math;

    exports io.github.lucasstarsz.fastj.framework.systems.game;
    exports io.github.lucasstarsz.fastj.framework.systems.behaviors;
    exports io.github.lucasstarsz.fastj.framework.systems.tags;
    exports io.github.lucasstarsz.fastj.framework.systems.input;
}