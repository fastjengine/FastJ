/**
 * The FastJ Library in its entirety.
 * <p>
 * FastJ does not use any external dependencies -- it relies entirely on Java 11's {@code java.desktop} module, using
 * AWT and Swing to display output.
 * <p>
 * This game library is split into two main parts:
 * <ul>
 *     <li>the framework with which to build games (every package except {@link io.github.lucasstarsz.fastj.engine}), and</li>
 *     <li>the engine running it (just package {@code engine}).</li>
 * </ul>
 * <p>
 * For more information, check out
 * <a href="https://github.com/lucasstarsz/FastJ-Engine" target="_blank">the github repository.</a>
 */
module fastj.library {
    requires transitive java.desktop;

    exports io.github.lucasstarsz.fastj.engine;

    exports io.github.lucasstarsz.fastj.graphics;
    exports io.github.lucasstarsz.fastj.graphics.gameobject.shapes;
    exports io.github.lucasstarsz.fastj.graphics.gameobject.text;

    exports io.github.lucasstarsz.fastj.systems.input.keyboard;
    exports io.github.lucasstarsz.fastj.systems.input.mouse;

    exports io.github.lucasstarsz.fastj.math;

    exports io.github.lucasstarsz.fastj.systems.render;

    exports io.github.lucasstarsz.fastj.systems.behaviors;
    exports io.github.lucasstarsz.fastj.systems.control;
    exports io.github.lucasstarsz.fastj.systems.tags;
    exports io.github.lucasstarsz.fastj.systems.input;

    exports io.github.lucasstarsz.fastj.graphics.ui;
    exports io.github.lucasstarsz.fastj.graphics.ui.elements;
    exports io.github.lucasstarsz.fastj.graphics.gameobject;
}
