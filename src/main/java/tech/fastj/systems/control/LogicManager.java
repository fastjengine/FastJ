package tech.fastj.systems.control;

import tech.fastj.engine.FastJEngine;
import tech.fastj.graphics.display.FastJCanvas;
import tech.fastj.graphics.display.RenderSettings;

/**
 * The basis of top-level game structures in a FastJ game.
 * <p>
 * FastJ uses top-level game structures which implement {@link tech.fastj.systems.control.LogicManager} as the basis for initializing and
 * running a game.
 * <p>
 * Top-Level game managers provided in FastJ:
 * <ul>
 *     <li>{@link tech.fastj.systems.control.SimpleManager} -- directly contains game logic.</li>
 *     <li>
 *         {@link tech.fastj.systems.control.SceneManager Scene Manager} -- keeps game logic in
 *         {@link tech.fastj.systems.control.Scene scenes}, along with the ability to
 *         {@link tech.fastj.systems.control.SceneManager#switchScenes(java.lang.String) switch between scenes}.
 *     </li>
 * </ul>
 * <p>
 * <b>For Implementors</b>: This class defines the basic events and actions that all implementations need to address:
 * <ul>
 *     <li>{@link #init(FastJCanvas) Game} and {@link #initBehaviors() Behavior} Initialization</li>
 *     <li>{@link #processKeysDown() Input Processing}</li>
 *     <li>
 *         {@link #fixedUpdate(FastJCanvas) Fixed Update}/{@link #update(FastJCanvas) Render Update}, and
 *         {@link #fixedUpdateBehaviors() Fixed Behavior Update}/{@link #updateBehaviors() Render Behavior Update}
 *     </li>
 *     <li>{@link #render(FastJCanvas) Game Rendering}</li>
 *     <li>{@link #reset() Game Resetting}</li>
 * </ul>
 *
 * @author Andrew Dey
 * @since 1.5.0
 */
public interface LogicManager {

    /**
     * Initializes the logic manager.
     * <p>
     * This method is called after the engine has been set up, and the canvas has been created. This is the best place to set some initial
     * settings that apply to the entire game -- particularly {@link RenderSettings rendering settings}.
     *
     * @param canvas The {@link FastJCanvas} that the game renders to. Useful for applying canvas-related settings before the game starts.
     */
    default void init(FastJCanvas canvas) {
    }

    /** Initializes the logic manager's behaviors, being called after {@link #init(FastJCanvas) logic manager initialization}. */
    void initBehaviors();

    /** Allows the logic manager to process keys pressed down, being called before {@link #update(FastJCanvas) the update method call}. */
    void processKeysDown();

    /**
     * Allows the logic manager to update its game state -- content updated whenever the game renders -- once.
     * <p>
     * The {@link FastJEngine engine} attempts to run this method
     * {@link FastJEngine#getTargetUPS() as many times as specified for fixed update in the engine}. This update value can be
     * {@link FastJEngine#setTargetUPS(int) changed}.
     *
     * @param canvas The {@link FastJCanvas} that the game renders to. Useful for checking certain attributes of the canvas while updating
     *               the game state.
     */
    default void fixedUpdate(FastJCanvas canvas) {
    }

    /**
     * Allows the logic manager to update its fixed game state -- content updated whenever the game updates in physics once.
     * <p>
     * The {@link FastJEngine engine} attempts to run this method
     * {@link FastJEngine#getTargetFPS() as many times as specified for update in the engine}. This update value can be
     * {@link FastJEngine#setTargetFPS(int) changed}.
     *
     * @param canvas The {@link FastJCanvas} that the game renders to. Useful for checking certain attributes of the canvas while updating
     *               the game state.
     */
    default void update(FastJCanvas canvas) {
    }

    /** Updates the logic manager's behaviors, being called after {@link #fixedUpdate(FastJCanvas) the fixed update method call}. */
    void fixedUpdateBehaviors();

    /** Updates the logic manager's behaviors, being called after {@link #update(FastJCanvas) the update method call}. */
    void updateBehaviors();

    /**
     * Allows the logic manager to render its game's current state to the screen.
     * <p>
     * The {@code FastJEngine} attempts to call this method at most {@code FastJEngine#targetFPS} times a second. This update value can be
     * {@link FastJEngine#setTargetFPS(int) changed}.
     *
     * @param canvas The {@link FastJCanvas} that the game renders to.
     */
    void render(FastJCanvas canvas);

    /**
     * Resets the logic manager entirely.
     * <p>
     * This method is called when the engine exits. Due to the game engine's mutability, it is strongly recommended that all resources of
     * the game are closed and released.
     * <p>
     * <b>FOR IMPLEMENTORS:</b> By the end of this method call, the logic manager should have released all its resources.
     */
    void reset();
}