/**
 * The FastJ Game Library in its entirety.
 * <p>
 * This game library is split into two main parts: the framework with which to build games (every package except {@code
 * engine}), and the engine running it (just package {@code engine}). FastJ does not use any external dependencies -- it
 * relies entirely on Java 15's {@code java.desktop} module, using AWT and Swing to display output.
 * <p>
 * For more information, check out
 * <a href="https://github.com/lucasstarsz/FastJ-Engine" target="_blank">the github repository.</a>
 */
module fastj.library {
    requires transitive java.desktop;

    exports io.github.lucasstarsz.fastj.engine;

    exports io.github.lucasstarsz.fastj.graphics;
    exports io.github.lucasstarsz.fastj.graphics.shapes;
    exports io.github.lucasstarsz.fastj.graphics.text;

    exports io.github.lucasstarsz.fastj.io.keyboard;
    exports io.github.lucasstarsz.fastj.io.mouse;

    exports io.github.lucasstarsz.fastj.math;

    exports io.github.lucasstarsz.fastj.render;

    exports io.github.lucasstarsz.fastj.systems.behaviors;
    exports io.github.lucasstarsz.fastj.systems.game;
    exports io.github.lucasstarsz.fastj.systems.tags;
    exports io.github.lucasstarsz.fastj.systems.input;

    exports io.github.lucasstarsz.fastj.ui;
    exports io.github.lucasstarsz.fastj.ui.elements;
}
