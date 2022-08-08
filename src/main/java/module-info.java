import tech.fastj.resources.ResourceManager;

/**
 * The FastJ Library in its entirety.
 * <p>
 * FastJ relies on Java 11's <a href="https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/module-summary.html"
 * target="_blank">java.desktop</a> module, using java2d to display output. Logging capabilities are provided by
 * <a href="https://www.slf4j.org/" target="_blank">SLF4J</a>.
 * <p>
 * This game library is split into several parts:
 * <ol>
 *     <li>{@link tech.fastj.engine} -- The package containing the base of the game engine.</li>
 *     <li>{@link tech.fastj.graphics} -- Game objects and other graphical content.</li>
 *     <li>{@link tech.fastj.input} -- User input and events via keyboard &amp; mouse.</li>
 *     <li>{@link tech.fastj.logging} -- FastJ's logging system, abstracted over
 *          <a href="https://www.slf4j.org/" target="_blank">SLF4J</a>.</li>
 *     <li>{@link tech.fastj.math} -- FastJ's supplementary mathematics and vector library.</li>
 *     <li>{@link tech.fastj.resources} -- FastJ's {@link ResourceManager resource manager}, model loading system, image
 *          system, and other file-related content.</li>
 *     <li>{@link tech.fastj.systems} -- Audio, behaviors, and other miscellaneous features of FastJ.</li>
 * </ol>
 * <p>
 * For more information on the source code, check out
 * <a href="https://github.com/fastjengine/FastJ" target="_blank">the github repository</a>.
 * <p>
 * For more information on FastJ itself, check out <a href="https://fastj.tech" target="_blank">FastJ's website</a>.
 */
module fastj.library {
    requires transitive java.desktop;
    requires transitive org.slf4j;

    exports tech.fastj.engine;
    exports tech.fastj.engine.config;
    exports tech.fastj.logging;
    exports tech.fastj.math;

    exports tech.fastj.graphics;
    exports tech.fastj.graphics.dialog;
    exports tech.fastj.graphics.display;
    exports tech.fastj.graphics.game;
    exports tech.fastj.graphics.gradients;
    exports tech.fastj.graphics.textures;
    exports tech.fastj.graphics.ui;
    exports tech.fastj.graphics.ui.elements;
    exports tech.fastj.graphics.util;

    exports tech.fastj.input;
    exports tech.fastj.input.keyboard;
    exports tech.fastj.input.keyboard.events;
    exports tech.fastj.input.mouse;
    exports tech.fastj.input.mouse.events;

    exports tech.fastj.resources;
    exports tech.fastj.resources.files;
    exports tech.fastj.resources.images;
    exports tech.fastj.resources.models;

    exports tech.fastj.systems.audio;
    exports tech.fastj.systems.audio.state;
    exports tech.fastj.systems.behaviors;
    exports tech.fastj.systems.control;
    exports tech.fastj.systems.collections;
    exports tech.fastj.systems.execution;
    exports tech.fastj.systems.tags;
    exports tech.fastj.animation;
    exports tech.fastj.animation.sprite;
    exports tech.fastj.animation.event;
    exports tech.fastj.animation.sprite.event;
    exports tech.fastj.gameloop;
    exports tech.fastj.gameloop.event;
}
