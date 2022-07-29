package tech.fastj.systems.control;

import tech.fastj.graphics.Drawable;
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

    /** Initializes a {@code DrawableManager}'s internals. */
    public DrawableManager() {
        gameObjects = new LinkedHashMap<>();
        uiElements = new LinkedHashMap<>();
    }

    /**
     * Gets the game objects assigned to the manager.
     *
     * @return The game objects of the scene.
     */
    public Map<String, GameObject> getGameObjects() {
        return gameObjects;
    }

    /**
     * Gets the game objects assigned to the manager.
     *
     * @return The game objects of the scene.
     */
    public List<GameObject> getGameObjectsList() {
        return new ArrayList<>(gameObjects.values());
    }

    /**
     * Gets the ui elements assigned to the manager.
     *
     * @return The ui elements of the scene.
     */
    public Map<String, UIElement<? extends InputActionEvent>> getUIElements() {
        return uiElements;
    }

    /**
     * Gets the ui elements assigned to the manager.
     *
     * @return The ui elements of the scene.
     */
    public List<UIElement<? extends InputActionEvent>> getUIElementsList() {
        return new ArrayList<>(uiElements.values());
    }

    /**
     * Gets the ui elements assigned to the manager.
     *
     * @return The ui elements of the scene.
     */
    public Map<String, Drawable> getDrawables() {
        Map<String, Drawable> result = new ConcurrentHashMap<>();
        result.putAll(gameObjects);
        result.putAll(uiElements);

        return result;
    }

    /**
     * Gets the ui elements assigned to the manager.
     *
     * @return The ui elements of the scene.
     */
    public List<Drawable> getDrawablesList() {
        List<Drawable> result = new ArrayList<>();
        result.addAll(gameObjects.values());
        result.addAll(uiElements.values());

        return result;
    }

    /* Game Objects */

    /**
     * Adds the specified game object.
     *
     * @param gameObject The game object to add.
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

    public void destroyGameObjects(SimpleManager manager) {
        for (GameObject gameObject : getGameObjectsList()) {
            gameObject.destroy(manager);
        }
    }

    public void destroyGameObjects(Scene scene) {
        for (GameObject gameObject : getGameObjectsList()) {
            gameObject.destroy(scene);
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
     * Adds the specified ui element.
     *
     * @param guiObject The ui element to add.
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

    public void destroyUIElements(SimpleManager manager) {
        for (UIElement<? extends InputActionEvent> uiElement : getUIElementsList()) {
            uiElement.destroy(manager);
        }
    }

    public void destroyUIElements(Scene scene) {
        for (UIElement<? extends InputActionEvent> uiElement : getUIElementsList()) {
            uiElement.destroy(scene);
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

    public void reset(Scene scene) {
        destroyGameObjects(scene);
        destroyUIElements(scene);

        clearAllLists();
    }

    public void reset(SimpleManager manager) {
        destroyGameObjects(manager);
        destroyUIElements(manager);

        clearAllLists();
    }

    /** Removes all game objects and ui elements from the manager. */
    public void clearAllLists() {
        clearGameObjects();
        clearUIElements();
    }
}
