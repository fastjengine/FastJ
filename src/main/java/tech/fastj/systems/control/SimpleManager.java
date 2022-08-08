package tech.fastj.systems.control;

import tech.fastj.graphics.Drawable;
import tech.fastj.graphics.display.Camera;
import tech.fastj.graphics.display.FastJCanvas;
import tech.fastj.input.InputManager;
import tech.fastj.systems.behaviors.BehaviorManager;

import java.util.List;

/**
 * The manager which allows for control over a game with a single scene.
 *
 * @author Andrew Dey
 * @since 1.5.0
 */
public abstract class SimpleManager implements LogicManager, GameHandler {

    private final Camera camera;

    /**
     * Input manager instance for the simple manager -- it controls the scene's received events.
     * @deprecated Public access to this field will be removed soon -- please use {@link GameHandler#inputManager()} instead.
     */
    @Deprecated(forRemoval = true)
    public final InputManager inputManager;

    /**
     * Drawable manager instance for the simple manager -- it controls the scene's game objects and ui elements.
     * @deprecated Public access to this field will be removed soon -- please use {@link GameHandler#drawableManager()} instead.
     */
    @Deprecated(forRemoval = true)
    public final DrawableManager drawableManager;

    /** Initializes the contents of the {@code SimpleManager}. */
    protected SimpleManager() {
        camera = new Camera();

        inputManager = new InputManager();
        drawableManager = new DrawableManager();

        BehaviorManager.addListenerList(this);
    }

    @Override
    public List<Drawable> getTaggableEntities() {
        return drawableManager.getDrawablesList();
    }

    @Override
    public void initBehaviors() {
        this.initBehaviorListeners();
    }

    @Override
    public void processKeysDown() {
        inputManager.fireKeysDown();
    }

    @Override
    public void fixedUpdateBehaviors() {
        this.fixedUpdateBehaviorListeners();
    }

    @Override
    public void updateBehaviors() {
        this.updateBehaviorListeners();
    }

    @Override
    public InputManager inputManager() {
        return inputManager;
    }

    @Override
    public DrawableManager drawableManager() {
        return drawableManager;
    }

    /**
     * Gets the {@code Camera} of the manager.
     *
     * @return The manager's camera.
     */
    @Override
    public Camera getCamera() {
        return camera;
    }

    /**
     * Renders the contents of the {@code DrawableManager} to the {@code FastJCanvas}.
     *
     * @param canvas The {@code FastJCanvas} that the game renders to.
     */
    @Override
    public void render(FastJCanvas canvas) {
        canvas.render(
                drawableManager.getGameObjects(),
                drawableManager.getUIElements(),
                camera
        );
    }

    @Override
    public void reset() {
        this.destroyBehaviorListeners();
        drawableManager.reset(this);
        this.clearBehaviorListeners();
        drawableManager.clearAllLists();
        inputManager.reset();
        camera.reset();
    }
}
