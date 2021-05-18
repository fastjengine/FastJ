package io.github.lucasstarsz.fastj.systems.control;

import io.github.lucasstarsz.fastj.graphics.Drawable;
import io.github.lucasstarsz.fastj.graphics.game.GameObject;
import io.github.lucasstarsz.fastj.graphics.ui.UIElement;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Class to manage {@link Drawable} objects.
 *
 * @author Andrew Dey
 * @version 1.0.0
 */
public class DrawableManager {

    private final Map<String, GameObject> gameObjects;
    private final Map<String, UIElement> uiElements;

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
     * Gets the ui elements assigned to the manager.
     *
     * @return The ui elements of the scene.
     */
    public Map<String, UIElement> getUIElements() {
        return uiElements;
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
    public void addUIElement(UIElement guiObject) {
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
    public void removeUIElement(UIElement guiObject) {
        removeUIElement(guiObject.getID());
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

    /** Removes all game objects and ui elements from the manager. */
    public void clearAllLists() {
        clearGameObjects();
        clearUIElements();
    }
}
