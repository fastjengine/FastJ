package tech.fastj.systems.control;

import tech.fastj.graphics.Drawable;
import tech.fastj.graphics.display.Camera;
import tech.fastj.input.InputManager;
import tech.fastj.systems.behaviors.BehaviorHandler;
import tech.fastj.systems.tags.TagHandler;

/**
 * Interface defining the general aspects any FastJ game should have.
 *
 * @author Andrew Dey
 * @since 1.7.0
 */
public interface GameHandler extends BehaviorHandler, TagHandler<Drawable> {

    /** {@return the game handler's {@link DrawableManager drawable manager}} */
    DrawableManager drawableManager();

    /** {@return the game handler's {@link InputManager input manager}} */
    InputManager inputManager();

    /** {@return the game handler's {@link Camera game camera}} */
    Camera getCamera();

    /**
     * Resets the game handler entirely.
     * <p>
     * This method is called when the engine exits. Due to the game engine's mutability, it is preferred that all resources of the game
     * engine are removed gracefully.
     * <p>
     * <b>FOR IMPLEMENTORS:</b> By the end of this method call, the game handler should have released all its
     * resources.
     */
    void reset();
}
