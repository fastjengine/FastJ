package tech.fastj.systems.control;

import tech.fastj.graphics.Drawable;
import tech.fastj.graphics.display.FastJCanvas;
import tech.fastj.graphics.game.GameObject;
import tech.fastj.graphics.ui.UIElement;
import tech.fastj.input.InputActionEvent;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class to manage {@link Drawable} objects.
 *
 * @author Andrew Dey
 * @since 1.0.0
 */
public class DrawableManager {

    private final Map<String, GameObject> gameObjects;
    private final Map<String, UIElement<? extends InputActionEvent>> uiElements;

    /** Initializes a {@link DrawableManager}'s internals. */
    public DrawableManager() {
        gameObjects = new LinkedHashMap<>();
        uiElements = new LinkedHashMap<>();
    }

    /** {@return the game objects assigned to the manager, as a map} */
    public Map<String, GameObject> getGameObjects() {
        return gameObjects;
    }

    /** {@return the game objects assigned to the manager, wrapped as a list} */
    public List<GameObject> getGameObjectsList() {
        return new ArrayList<>(gameObjects.values());
    }

    /** {@return the ui elements assigned to the manager, as a map} */
    public Map<String, UIElement<? extends InputActionEvent>> getUIElements() {
        return uiElements;
    }

    /** {@return the ui elements assigned to the manager, wrapped as a list} */
    public List<UIElement<? extends InputActionEvent>> getUIElementsList() {
        return new ArrayList<>(uiElements.values());
    }

    /** {@return the game objects <b>and</b> ui elements assigned to the manager, as a map} */
    public Map<String, Drawable> getDrawables() {
        Map<String, Drawable> result = new ConcurrentHashMap<>();
        result.putAll(gameObjects);
        result.putAll(uiElements);

        return result;
    }

    /** {@return the game objects <b>and</b> ui elements assigned to the manager, as a list} */
    public List<Drawable> getDrawablesList() {
        List<Drawable> result = new ArrayList<>();
        result.addAll(gameObjects.values());
        result.addAll(uiElements.values());

        return result;
    }

    /* Game Objects */

    /**
     * Adds the specified {@link GameObject game object}.
     * <p>
     * Commonly used during initialization of a {@link SimpleManager#init(FastJCanvas) simple manager} or loading of a
     * {@link Scene#load(FastJCanvas) scene}, this method is used to allow the manager/scene to render the game object.
     * <ul>
     *     <li>Getting a {@link DrawableManager drawable manager} from a {@link SimpleManager#drawableManager() simple manager}</li>
     *     <li>Getting a {@link DrawableManager drawable manager} from a {@link Scene#drawableManager() scene}</li>
     * </ul>
     *
     * @param gameObject The {@link GameObject game object} to add.
     */
    public void addGameObject(GameObject gameObject) {
        gameObjects.put(gameObject.getID(), gameObject);
    }

    /**
     * Removes the game object with the specified ID.
     *
     * @param gameObjectID The id of the game object to remove.
     */
    public void removeGameObject(String gameObjectID) {
        gameObjects.remove(gameObjectID);
    }

    /**
     * Removes the specified game object.
     *
     * @param gameObject The game object to remove.
     */
    public void removeGameObject(GameObject gameObject) {
        removeGameObject(gameObject.getID());
    }

    /**
     * Destroys the game objects using the given {@link GameHandler}.
     *
     * @param gameHandler The game handler to destroy the game objects from.
     */
    public void destroyGameObjects(GameHandler gameHandler) {
        for (GameObject gameObject : getGameObjectsList()) {
            gameObject.destroy(gameHandler);
        }
    }

    /** Removes any null values from the list of game objects for the manager. */
    public void refreshGameObjectList() {
        gameObjects.entrySet().removeIf(Objects::isNull);
    }

    /** Removes all game objects from the manager. */
    public void clearGameObjects() {
        gameObjects.clear();
    }

    /* ui elements */

    /**
     * Adds the specified {@link UIElement ui element}.
     * <p>
     * Commonly used during initialization of a {@link SimpleManager#init(FastJCanvas) simple manager} or loading of a
     * {@link Scene#load(FastJCanvas) scene}, this method is used to allow the manager/scene to render the ui element.
     * <ul>
     *     <li>Getting a {@link DrawableManager drawable manager} from a {@link SimpleManager#drawableManager() simple manager}</li>
     *     <li>Getting a {@link DrawableManager drawable manager} from a {@link Scene#drawableManager() scene}</li>
     * </ul>
     *
     * @param guiObject The {@link UIElement ui element} to add.
     */
    public void addUIElement(UIElement<? extends InputActionEvent> guiObject) {
        uiElements.put(guiObject.getID(), guiObject);
    }

    /**
     * Removes the ui element with the specified ID.
     *
     * @param guiObjectID The id of the ui element to remove.
     */
    public void removeUIElement(String guiObjectID) {
        uiElements.remove(guiObjectID);
    }

    /**
     * Removes the specified ui element.
     *
     * @param guiObject The ui element to remove.
     */
    public void removeUIElement(UIElement<? extends InputActionEvent> guiObject) {
        removeUIElement(guiObject.getID());
    }

    /**
     * Destroys the ui elements using the given {@link GameHandler}.
     *
     * @param gameHandler The game handler to destroy the ui elements from.
     */
    public void destroyUIElements(GameHandler gameHandler) {
        for (UIElement<? extends InputActionEvent> uiElement : getUIElementsList()) {
            uiElement.destroy(gameHandler);
        }
    }

    /** Removes any null values from the list of ui elements for the manager. */
    public void refreshUIElementList() {
        uiElements.entrySet().removeIf(Objects::isNull);
    }

    /** Removes all ui elements from the manager. */
    public void clearUIElements() {
        uiElements.clear();
    }

    /* reset */

    /**
     * Resets the manager, destroying all its {@link #getGameObjects() game objects} <b>and</b> {@link #getUIElements() ui elements} using
     * the given {@link GameHandler}.
     *
     * @param gameHandler The game handler to destroy game objects and ui elements from.
     */
    public void reset(GameHandler gameHandler) {
        destroyGameObjects(gameHandler);
        destroyUIElements(gameHandler);

        clearAllLists();
    }

    /** Removes all game objects and ui elements from the manager, without destroying them. */
    public void clearAllLists() {
        clearGameObjects();
        clearUIElements();
    }
}
