package io.github.lucasstarsz.fastj.systems.control;

import io.github.lucasstarsz.fastj.graphics.Drawable;
import io.github.lucasstarsz.fastj.graphics.gameobject.GameObject;
import io.github.lucasstarsz.fastj.systems.render.Camera;
import io.github.lucasstarsz.fastj.systems.render.Display;
import io.github.lucasstarsz.fastj.systems.behaviors.BehaviorManager;
import io.github.lucasstarsz.fastj.systems.input.InputManager;
import io.github.lucasstarsz.fastj.systems.tags.TagManager;

import java.util.List;

/**
 * Class containing the logic for a specific section, or scene, of a game.
 * <p>
 * The {@code LogicManager} of any game made with FastJ can store many scenes. Through this, the user can divide their
 * game into different sections.
 *
 * @author Andrew Dey
 * @version 1.0.0
 */
public abstract class Scene {

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
     * Gets the behavior listeners assigned to the scene.
     *
     * @return The behavior listeners of the scene.
     */
    public List<GameObject> getBehaviorListeners() {
        return BehaviorManager.getList(this);
    }

    /**
     * Gets the taggable entities assigned to the scene.
     *
     * @return The taggable entities of the scene.
     */
    public List<Drawable> getTaggableEntities() {
        return TagManager.getEntityList(this);
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

    /**
     * Gets all taggable entities with the specified tag.
     *
     * @param tag The tag to check for.
     * @return A list of all taggable entities with the specified tag.
     */
    public List<Drawable> getAllWithTag(String tag) {
        return TagManager.getAllInListWithTag(this, tag);
    }

    /* Behavior Listeners */

    /**
     * Adds the specified behavior listener to the scene.
     *
     * @param listener The behavior listener to add.
     */
    public void addBehaviorListener(GameObject listener) {
        BehaviorManager.addListener(this, listener);
    }

    /**
     * Removes the specified behavior listener from the scene.
     *
     * @param listener The behavior listener to remove.
     */
    public void removeBehaviorListener(GameObject listener) {
        BehaviorManager.removeListener(this, listener);
    }

    /** Initializes all behavior listeners in the scene. */
    public void initBehaviorListeners() {
        BehaviorManager.initBehaviorListeners(this);
    }

    /** Updates all behavior listeners in the scene. */
    public void updateBehaviorListeners() {
        BehaviorManager.updateBehaviorListeners(this);
    }

    /** Removes all behavior listeners in the scene. */
    public void clearBehaviorListeners() {
        BehaviorManager.clearListenerList(this);
    }

    /* Taggable Entities */

    /**
     * Adds the specified taggable entity, only if it extends the {@code Drawable} class.
     *
     * @param entity The taggable entity to add.
     * @param <T>    The type of the taggable entity, which must extend the {@code Drawable} class.
     */
    public <T extends Drawable> void addTaggableEntity(T entity) {
        TagManager.addTaggableEntity(this, entity);
    }

    /**
     * Removes the specified taggable entity.
     *
     * @param entity The taggable entity to remove.
     */
    public void removeTaggableEntity(Drawable entity) {
        TagManager.removeTaggableEntity(this, entity);
    }

    /** Removes all taggable from the scene. */
    public void clearTaggableEntities() {
        TagManager.clearEntityList(this);
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
