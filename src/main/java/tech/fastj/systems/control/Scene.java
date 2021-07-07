package tech.fastj.systems.control;

import tech.fastj.graphics.display.Camera;
import tech.fastj.graphics.display.Display;

import tech.fastj.systems.behaviors.BehaviorHandler;
import tech.fastj.systems.behaviors.BehaviorManager;
import tech.fastj.systems.tags.TagHandler;
import tech.fastj.systems.tags.TagManager;

import tech.fastj.input.InputManager;

/**
 * Class containing the logic for a specific section, or scene, of a game.
 * <p>
 * A {@code SceneManager} of any game made with FastJ can store many scenes. Through this, the user can divide their
 * game into different sections.
 *
 * @author Andrew Dey
 * @version 1.0.0
 */
public abstract class Scene implements BehaviorHandler, TagHandler {

    private final String sceneName;
    private final Camera camera;

    /** Input manager instance for the scene -- it controls the scene's received events. */
    public final InputManager inputManager;
    /** Drawable manager instance for the scene -- it controls the scene's game objects and ui elements. */
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

        TagManager.addTaggableEntityList(this);
        BehaviorManager.addListenerList(this);
    }

    /**
     * Loads the scene into an initialized state.
     * <p>
     * This method is best used for initializing any variables necessary at the beginning of displaying a scene.
     *
     * @param display The {@code Display} that the game renders to.
     */
    public abstract void load(Display display);

    /**
     * Unloads the scene into an uninitialized state.
     * <p>
     * This method is best used for destroying or and nullifying any variables used in the game.
     *
     * @param display The {@code Display} that the game renders to.
     */
    public abstract void unload(Display display);

    /**
     * Updates the scene's state.
     * <p>
     * This method is called on the current scene every time the engine updates its state.
     *
     * @param display The {@code Display} that the game renders to.
     */
    public abstract void update(Display display);

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
    public Camera getCamera() {
        return camera;
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

    /* Reset */

    /** Removes all elements from the scene. */
    public void clearAllLists() {
        drawableManager.clearAllLists();
        inputManager.clearAllLists();
        this.clearBehaviorListeners();
        this.clearTaggableEntities();
    }

    /** Resets the scene's state entirely. */
    public void reset() {
        this.setInitialized(false);
        this.clearAllLists();
        camera.reset();
    }
}
