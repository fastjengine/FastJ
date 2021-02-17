package io.github.lucasstarsz.fastj.framework.systems.behaviors;

import io.github.lucasstarsz.fastj.framework.graphics.GameObject;
import io.github.lucasstarsz.fastj.framework.systems.game.Scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to manage behavior listeners for all scenes.
 *
 * @author Andrew Dey
 * @version 1.0.0
 */
public class BehaviorManager {

    private static final Map<Scene, List<GameObject>> behaviorListenerLists = new HashMap<>();

    /**
     * Gets the specified list of behavior listeners aliased to the specified {@code Scene}.
     *
     * @param scene The {@code Scene} to get the list of behavior listeners for.
     * @return The list of behavior listeners.
     */
    public static List<GameObject> getList(Scene scene) {
        return behaviorListenerLists.get(scene);
    }

    /**
     * Adds the specified behavior listener to the list aliased to the specified {@code Scene}.
     *
     * @param scene    The {@code Scene} used as the alias to add the specified behavior listener to.
     * @param listener The behavior listener to add.
     */
    public static void addListener(Scene scene, GameObject listener) {
        if (!behaviorListenerLists.get(scene).contains(listener)) {
            behaviorListenerLists.get(scene).add(listener);
        }
    }

    /**
     * Removes the specified behavior from the list aliased to the specified {@code Scene}.
     *
     * @param scene    The {@code Scene} used as the alias to remove the specified behavior listener from.
     * @param listener The behavior listener to remove.
     */
    public static void removeListener(Scene scene, GameObject listener) {
        behaviorListenerLists.get(scene).remove(listener);
    }

    /**
     * Adds an alias for the specified {@code Scene}, if one does not already exist.
     *
     * @param scene The {@code Scene} to add a new alias for.
     */
    public static void addListenerList(Scene scene) {
        if (!behaviorListenerLists.containsKey(scene)) {
            behaviorListenerLists.put(scene, new ArrayList<>());
        }
    }

    /**
     * Removes the list aliased to the specified {@code Scene}, and all elements inside.
     *
     * @param scene The {@code Scene} to remove the alias for.
     */
    public static void removeListenerList(Scene scene) {
        behaviorListenerLists.remove(scene);
    }

    /**
     * Removes all elements from the list aliased to the specified {@code Scene}.
     *
     * @param scene The {@code Scene} used as the alias to remove all behavior listeners.
     */
    public static void clearListenerList(Scene scene) {
        behaviorListenerLists.get(scene).clear();
    }

    /**
     * Initializes the behavior listeners aliased to the specified {@code Scene}.
     *
     * @param scene The {@code Scene} used as the alias to initialize the behavior listeners for.
     */
    public static void initBehaviorListeners(Scene scene) {
        List<GameObject> listenerCopy = new ArrayList<>(behaviorListenerLists.get(scene));
        for (GameObject listener : listenerCopy) {
            listener.initBehaviors();
        }
    }

    /**
     * Updates the behavior listeners aliased to the specified {@code Scene}.
     *
     * @param scene The {@code Scene} used as the alias to update the behavior listeners for.
     */
    public static void updateBehaviorListeners(Scene scene) {
        List<GameObject> listenerCopy = new ArrayList<>(behaviorListenerLists.get(scene));
        for (GameObject listener : listenerCopy) {
            listener.updateBehaviors();
        }
    }

    /** Resets the behavior manager entirely. */
    public static void reset() {
        for (List<GameObject> list : behaviorListenerLists.values()) {
            list.clear();
        }
        behaviorListenerLists.clear();
    }
}
