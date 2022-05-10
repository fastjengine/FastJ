package tech.fastj.systems.control;

import tech.fastj.graphics.Drawable;
import tech.fastj.graphics.display.Camera;
import tech.fastj.graphics.display.FastJCanvas;

import tech.fastj.input.InputManager;

import tech.fastj.systems.behaviors.BehaviorHandler;
import tech.fastj.systems.behaviors.BehaviorManager;
import tech.fastj.systems.tags.TagHandler;

import java.awt.event.InputEvent;
import java.util.List;

/**
 * The manager which allows for control over a game with a single scene.
 *
 * @author Andrew Dey
 * @since 1.5.0
 */
public abstract class SimpleManager implements LogicManager, BehaviorHandler, TagHandler<Drawable> {

    private final Camera camera;
    /** Input manager instance for the simple manager -- it controls the scene's received events. */
    public final InputManager inputManager;
    /** Drawable manager instance for the simple manager -- it controls the scene's game objects and ui elements. */
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

    /** Processes all stored input events. */
    @Override
    public void processInputEvents() {
        inputManager.processEvents();
    }

    @Override
    public void processKeysDown() {
        inputManager.fireKeysDown();
    }

    /** Stores the specified input event to be processed later ({@link #processInputEvents()}). */
    @Override
    public void receivedInputEvent(InputEvent inputEvent) {
        inputManager.receivedInputEvent(inputEvent);
    }

    @Override
    public void updateBehaviors() {
        this.updateBehaviorListeners();
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

    /**
     * Gets the {@code Camera} of the manager.
     *
     * @return The manager's camera.
     */
    public Camera getCamera() {
        return camera;
    }

    @Override
    public void reset() {
        this.destroyBehaviorListeners();
        drawableManager.destroyAllLists(this);
        this.clearBehaviorListeners();
        drawableManager.clearAllLists();
        inputManager.clearAllLists();
        camera.reset();
    }
}
