package io.github.lucasstarsz.fastj.engine.systems.behaviors;

import io.github.lucasstarsz.fastj.engine.graphics.Drawable;
import io.github.lucasstarsz.fastj.engine.systems.game.Scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to manage behavior listeners for all scenes.
 *
 * @author Andrew Dey
 * @version 0.3.2a
 */
public class BehaviorManager {

    private static final Map<Scene, List<Drawable>> behaviorListenerLists = new HashMap<>();

    /**
     * Gets the specified list of behavior listeners aliased to the specified {@code Scene}.
     *
     * @param scene The {@code Scene} to get the list of behavior listeners for.
     * @return The list of behavior listeners.
     * @see Drawable
     */
    public static List<Drawable> getList(Scene scene) {
        return behaviorListenerLists.get(scene);
    }

    /**
     * Adds the specified behavior listener to the list aliased to the specified {@code Scene}.
     *
     * @param scene    The {@code Scene} used as the alias to add the specified behavior listener to.
     * @param listener The behavior listener to add.
     * @see io.github.lucasstarsz.fastj.engine.systems.game.Scene
     * @see Drawable
     */
    public static void addListener(Scene scene, Drawable listener) {
        if (!behaviorListenerLists.get(scene).contains(listener)) {
            behaviorListenerLists.get(scene).add(listener);
        }
    }

    /**
     * Removes the specified behavior from the list aliased to the specified {@code Scene}.
     *
     * @param scene    The {@code Scene} used as the alias to remove the specified behavior listener from.
     * @param listener The behavior listener to remove.
     * @see io.github.lucasstarsz.fastj.engine.systems.game.Scene
     * @see Drawable
     */
    public static void removeListener(Scene scene, Drawable listener) {
        behaviorListenerLists.get(scene).remove(listener);
    }

    /**
     * Adds an alias for the specified {@code Scene}, if one does not already exist.
     *
     * @param scene The {@code Scene} to add a new alias for.
     * @see io.github.lucasstarsz.fastj.engine.systems.game.Scene
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
     * @see io.github.lucasstarsz.fastj.engine.systems.game.Scene
     */
    public static void removeListenerList(Scene scene) {
        behaviorListenerLists.remove(scene);
    }

    /**
     * Removes all elements from the list aliased to the specified {@code Scene}.
     *
     * @param scene The {@code Scene} used as the alias to remove all behavior listeners.
     * @see io.github.lucasstarsz.fastj.engine.systems.game.Scene
     */
    public static void clearListenerList(Scene scene) {
        behaviorListenerLists.get(scene).clear();
    }

    /**
     * Initializes the behavior listeners aliased to the specified {@code Scene}.
     *
     * @param scene The {@code Scene} used as the alias to initialize the behavior listeners for.
     * @see io.github.lucasstarsz.fastj.engine.systems.game.Scene
     */
    public static void initBehaviorListeners(Scene scene) {
        List<Drawable> listenerCopy = new ArrayList<>(behaviorListenerLists.get(scene));
        for (Drawable listener : listenerCopy) {
            listener.initBehaviors();
        }
    }

    /**
     * Updates the behavior listeners aliased to the specified {@code Scene}.
     *
     * @param scene The {@code Scene} used as the alias to update the behavior listeners for.
     * @see io.github.lucasstarsz.fastj.engine.systems.game.Scene
     */
    public static void updateBehaviorListeners(Scene scene) {
        List<Drawable> listenerCopy = new ArrayList<>(behaviorListenerLists.get(scene));
        for (Drawable listener : listenerCopy) {
            listener.updateBehaviors();
        }
    }

    /** Resets the behavior manager entirely. */
    public static void reset() {
        for (List<Drawable> list : behaviorListenerLists.values()) {
            list.clear();
        }
        behaviorListenerLists.clear();
    }
}
