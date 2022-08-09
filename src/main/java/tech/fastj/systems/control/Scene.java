package tech.fastj.systems.control;

import tech.fastj.engine.FastJEngine;
import tech.fastj.graphics.Drawable;
import tech.fastj.graphics.display.Camera;
import tech.fastj.graphics.display.FastJCanvas;
import tech.fastj.input.InputManager;
import tech.fastj.systems.behaviors.BehaviorManager;

import java.util.List;

/**
 * Class containing the logic for a specific section, or scene, of a game.
 * <p>
 * A {@code SceneManager} of any game made with FastJ can store many scenes. Through this, the user can divide their
 * game into different sections.
 *
 * @author Andrew Dey
 * @since 1.0.0
 */
public abstract class Scene implements GameHandler {

    private final String sceneName;
    private final Camera camera;

    /**
     * Input manager instance for the scene -- it controls the scene's received events.
     *
     * @deprecated Public access to this field will be removed soon -- please use {@link GameHandler#inputManager()} instead.
     */
    @Deprecated(forRemoval = true)
    public final InputManager inputManager;

    /**
     * Drawable manager instance for the scene -- it controls the scene's game objects and ui elements.
     *
     * @deprecated Public access to this field will be removed soon -- please use {@link GameHandler#drawableManager()} instead.
     */
    @Deprecated(forRemoval = true)
    public final DrawableManager drawableManager;

    private boolean isInitialized;

    /**
     * Constructs a scene with the specified name.
     *
     * @param setName The name to set the scene's name to.
     */
    protected Scene(String setName) {
        sceneName = setName;
        camera = new Camera();

        inputManager = new InputManager();
        drawableManager = new DrawableManager();

        BehaviorManager.addListenerList(this);
    }

    /**
     * Loads the scene into an initialized state.
     * <p>
     * This method is best used for initializing any variables necessary at the beginning of displaying a scene.
     *
     * @param canvas The {@code FastJCanvas} that the game renders to.
     */
    public void load(FastJCanvas canvas) {
    }

    /**
     * Unloads the scene into an uninitialized state.
     * <p>
     * This method is best used for destroying or and nullifying any variables used in the game.
     *
     * @param canvas The {@code FastJCanvas} that the game renders to.
     */
    public void unload(FastJCanvas canvas) {
    }

    /**
     * Updates the scene's state during game state updates.
     * <p>
     * This method is called on the current scene every time the engine updates its state.
     *
     * @param canvas The {@code FastJCanvas} that the game renders to.
     */
    public void fixedUpdate(FastJCanvas canvas) {
    }

    /**
     * Updates the scene's state during input receiving, before rendering.
     * <p>
     * This method is called on the current scene every time the engine updates its state.
     *
     * @param canvas The {@code FastJCanvas} that the game renders to.
     */
    public void update(FastJCanvas canvas) {

    }

    /**
     * Gets the name of the scene.
     *
     * @return The name of the scene.
     */
    public String getSceneName() {
        return sceneName;
    }

    /**
     * Gets the camera of the scene.
     *
     * @return The camera of the scene.
     */
    @Override
    public Camera getCamera() {
        return camera;
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
     * Gets the value that specifies whether the scene is initialized.
     *
     * @return The boolean that specifies whether the scene is initialized.
     */
    public boolean isInitialized() {
        return isInitialized;
    }

    /**
     * Sets whether the scene is initialized.
     *
     * @param initialized The value that determines whether the scene is initialized.
     */
    public void setInitialized(boolean initialized) {
        isInitialized = initialized;
    }

    @Override
    public List<Drawable> getTaggableEntities() {
        return drawableManager.getDrawablesList();
    }

    void generalLoad(FastJCanvas canvas) {
        inputManager.load();
        load(canvas);

        setInitialized(true);
    }

    void generalUnload(FastJCanvas canvas) {
        inputManager.unload();
        unload(canvas);
        drawableManager.reset(this);

        setInitialized(false);
    }

    /* Reset */

    /** Removes all elements from the scene. */
    public void clearAllLists() {
        drawableManager.clearAllLists();
        clearBehaviorListeners();
    }

    /** Resets the scene's state entirely. */
    @Override
    public void reset() {
        generalUnload(FastJEngine.getCanvas());

        clearAllLists();
        inputManager.reset();
        camera.reset();
    }
}
