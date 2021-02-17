module FastJ.main {
    requires java.desktop;

    exports io.github.lucasstarsz.fastj.engine;

    exports io.github.lucasstarsz.fastj.engine.graphics;
    exports io.github.lucasstarsz.fastj.engine.graphics.shapes;
    exports io.github.lucasstarsz.fastj.engine.graphics.text;

    exports io.github.lucasstarsz.fastj.engine.io;
    exports io.github.lucasstarsz.fastj.engine.io.listeners;

    exports io.github.lucasstarsz.fastj.engine.systems.game;
    exports io.github.lucasstarsz.fastj.engine.systems.behaviors;
    exports io.github.lucasstarsz.fastj.engine.systems.tags;
    exports io.github.lucasstarsz.fastj.engine.systems.input;

    exports io.github.lucasstarsz.fastj.engine.util;
    exports io.github.lucasstarsz.fastj.engine.util.math;
}