import tech.fastj.gameloop.GameLoop;
import tech.fastj.gameloop.GameLoopState;
import tech.fastj.gameloop.event.Event;
import tech.fastj.graphics.game.GameObject;
import tech.fastj.graphics.ui.UIElement;
import tech.fastj.input.keyboard.Keyboard;
import tech.fastj.input.mouse.Mouse;
import tech.fastj.resources.ResourceManager;
import tech.fastj.systems.audio.AudioManager;
import tech.fastj.systems.audio.MemoryAudio;
import tech.fastj.systems.audio.StreamedAudio;
import tech.fastj.systems.behaviors.Behavior;
import tech.fastj.systems.control.SceneManager;
import tech.fastj.systems.control.SimpleManager;

/**
 * The FastJ Library in its entirety.
 * <p>
 * FastJ relies on Java's
 * <a href="https://docs.oracle.com/en/java/javase/18/docs/api/java.desktop/module-summary.html" target="_blank">java.desktop</a> module,
 * using java2d to display output. Logging capabilities are provided by <a href="https://www.slf4j.org/" target="_blank">SLF4J</a>.
 * <p>
 * Core Content:
 * <ul>
 *     <li>{@link tech.fastj.engine Engine Base} -- Core and configuration tools for FastJ.</li>
 *     <li>
 *         {@link tech.fastj.gameloop Game Loop} -- {@link GameLoop} with highly customizable {@link GameLoopState loop states}, and an
 *         {@link Event event system}.
 *     </li>
 *     <li>
 *         {@link tech.fastj.systems.control Game Logic} -- Game logic handling ({@link SimpleManager single "scene"} and
 *         {@link SceneManager multi-scene}).
 *     </li>
 *     <li>
 *         {@link tech.fastj.graphics Graphics} -- {@link GameObject Game objects}, {@link UIElement ui},
 *         {@link tech.fastj.graphics.display display}, {@link tech.fastj.graphics.dialog dialogs}, and other graphical content.
 *     </li>
 *     <li>{@link tech.fastj.input Input} -- User input and events via {@link Keyboard keyboard} &amp; {@link Mouse mouse}.</li>
 *     <li>
 *         {@link tech.fastj.resources Resources} -- FastJ's {@link ResourceManager resource manager}, model loading system, image system,
 *         and other file-related content.
 *     </li>
 *     <li>
 *         {@link tech.fastj.logging Logging System} -- FastJ's logging system, abstracted over
 *         <a href="https://www.slf4j.org/" target="_blank">SLF4J</a>.
 *     </li>
 * </ul>
 * Specialized Tools:
 * <ul>
 *     <li>
 *         {@link tech.fastj.animation Animation} -- Extendable animation engine with support for
 *         {@link tech.fastj.animation.sprite sprite animation}.
 *     </li>
 *     <li>{@link tech.fastj.systems.behaviors Behaviors} -- Extending game object capabilities with {@link Behavior behaviors}.</li>
 *     <li>{@link tech.fastj.systems.tags Tags} -- FastJ's tagging system.</li>
 *     <li>
 *         {@link tech.fastj.systems.audio Audio} -- {@link MemoryAudio In-memory} and {@link StreamedAudio file-streamed} audio, with
 *         {@link AudioManager tools to load, unload, and tag instances}.
 *     </li>
 * </ul>
 * Supplementary Libraries &amp; Abstractions:
 * <ul>
 *     <li>{@link tech.fastj.math Math} -- FastJ's supplementary mathematics, transformations, and "vector" library.</li>
 *     <li>{@link tech.fastj.systems.collections Collections} -- Miscellaneous collections and other data-holding structures.</li>
 *     <li>{@link tech.fastj.systems.execution Execution} -- Extra definitions for thread pooling and delayed execution.</li>
 * </ul>
 * <p>
 * For more information, check out
 * <ul>
 *     <li><a href="https://github.com/fastjengine/FastJ" target="_blank">FastJ's source code on GitHub</a></li>
 *     <li><a href="https://fastj.tech" target="_blank">FastJ's website</a></li>
 * </ul>
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

    exports tech.fastj.physics;
}
