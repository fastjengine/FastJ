package io.github.lucasstarsz.fastj.systems.control;

import io.github.lucasstarsz.fastj.graphics.Drawable;
import io.github.lucasstarsz.fastj.graphics.gameobject.GameObject;
import io.github.lucasstarsz.fastj.graphics.ui.UIElement;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/** Class to manage {@link Drawable} objects. */
public class DrawableManager {

    private final Map<String, GameObject> gameObjects;
    private final Map<String, UIElement> GUIObjects;

    /** Initializes a {@code DrawableManager}'s internals. */
    public DrawableManager() {
        gameObjects = new LinkedHashMap<>();
        GUIObjects = new LinkedHashMap<>();
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
     * Gets the gui objects assigned to the manager.
     *
     * @return The gui objects of the scene.
     */
    public Map<String, UIElement> getGUIObjects() {
        return GUIObjects;
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

    /* GUI Objects */

    /**
     * Adds the specified gui object.
     *
     * @param guiObject The gui object to add.
     */
    public void addGUIObject(UIElement guiObject) {
        GUIObjects.put(guiObject.getID(), guiObject);
    }

    /**
     * Removes the gui object with the specified ID.
     *
     * @param guiObjectID The id of the gui object to remove.
     */
    public void removeGUIObject(String guiObjectID) {
        GUIObjects.remove(guiObjectID);
    }

    /**
     * Removes the specified gui object.
     *
     * @param guiObject The gui object to remove.
     */
    public void removeGUIObject(UIElement guiObject) {
        removeGUIObject(guiObject.getID());
    }

    /** Removes any null values from the list of gui objects for the manager. */
    public void refreshGUIObjectList() {
        GUIObjects.entrySet().removeIf(Objects::isNull);
    }

    /** Removes all gui objects from the manager. */
    public void clearGUIObjects() {
        GUIObjects.clear();
    }

    /* reset */

    /** Removes all game objects and gui objects. */
    public void clearAllLists() {
        clearGUIObjects();
        clearGameObjects();
    }
}
