package tech.fastj.gameloop;

/**
 * The base of all {@link GameLoopState game loop states} for the {@link GameLoop game loop}.
 * <p>
 * These are the four core states for the game loop, and dictate the runtime order as follows:
 * <ol>
 *     <li>{@link CoreLoopState#EarlyUpdate Early Update}</li>
 *     <li>{@link CoreLoopState#FixedUpdate Fixed Update}</li>
 *     <li>{@link CoreLoopState#Update Update}</li>
 *     <li>{@link CoreLoopState#LateUpdate Late Update}</li>
 * </ol>
 * <p>
 * Furthermore, {@link GameLoopState game loop states} are defined to always be based on one of these loop states.
 * <p>
 * Refer to {@link GameLoop} for a complete explanation of game loop iteration.
 *
 * @author Andrew Dey
 * @since 1.7.0
 */
public enum CoreLoopState {
    /**
     * State run at the beginning of every game loop iteration.
     * <p>
     * This is primarily used for updates before {@link #FixedUpdate fixed update}. This is also the first core loop state run per game loop
     * iteration.
     */
    EarlyUpdate,
    /**
     * State is run as many times as the {@link GameLoop#getTargetUPS() fixed updates per second} /
     * {@link GameLoop#getTargetFPS() updates per second} dictates, after {@link #EarlyUpdate early update}.
     * <p>
     * This is generally used for physics updates and simulations which must be performed as consistently as possible.
     */
    FixedUpdate,
    /**
     * State is run once to meet the {@link GameLoop#getTargetFPS() updates per second} definition, after
     * {@link #FixedUpdate fixed update}.
     * <p>
     * This is primarily used for frame-dependent updates, before rendering.
     */
    Update,
    /**
     * State is run once to meet the {@link GameLoop#getTargetFPS() updates per second} definition, after {@link #Update update}.
     * <p>
     * This is primarily used for frame-dependent updates, after rendering. This is also the last core loop state run per game loop
     * iteration.
     */
    LateUpdate
}
