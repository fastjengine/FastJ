package tech.fastj.systems.control;

import tech.fastj.graphics.Drawable;
import tech.fastj.graphics.display.Camera;
import tech.fastj.input.InputManager;
import tech.fastj.systems.behaviors.BehaviorHandler;
import tech.fastj.systems.tags.TagHandler;

public interface GameHandler extends BehaviorHandler, TagHandler<Drawable> {

    DrawableManager drawableManager();

    InputManager inputManager();

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
