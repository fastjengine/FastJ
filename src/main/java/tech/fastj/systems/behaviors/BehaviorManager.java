package tech.fastj.systems.behaviors;

import tech.fastj.graphics.game.GameObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to manage behavior listeners for all {@link BehaviorHandler}s.
 *
 * @author Andrew Dey
 * @version 1.0.0
 */
public class BehaviorManager {

    private static final Map<BehaviorHandler, List<GameObject>> BehaviorListenerLists = new HashMap<>();

    private BehaviorManager() {
        throw new java.lang.IllegalStateException();
    }

    /**
     * Gets the specified list of behavior listeners aliased to the specified {@link BehaviorHandler}.
     *
     * @param behaviorHandler The {@code BehaviorHandler} to get the list of behavior listeners for.
     * @return The list of behavior listeners.
     */
    public static List<GameObject> getList(BehaviorHandler behaviorHandler) {
        return BehaviorListenerLists.get(behaviorHandler);
    }

    /**
     * Adds the specified behavior listener to the list aliased to the specified {@link BehaviorHandler}.
     *
     * @param behaviorHandler The {@code BehaviorHandler} used as the alias to add the specified behavior listener to.
     * @param listener        The behavior listener to add.
     */
    public static void addListener(BehaviorHandler behaviorHandler, GameObject listener) {
        if (!BehaviorListenerLists.get(behaviorHandler).contains(listener)) {
            BehaviorListenerLists.get(behaviorHandler).add(listener);
        }
    }

    /**
     * Removes the specified behavior from the list aliased to the specified {@link BehaviorHandler}.
     *
     * @param behaviorHandler The {@code BehaviorHandler} used as the alias to remove the specified behavior listener
     *                        from.
     * @param listener        The behavior listener to remove.
     */
    public static void removeListener(BehaviorHandler behaviorHandler, GameObject listener) {
        BehaviorListenerLists.get(behaviorHandler).remove(listener);
    }

    /**
     * Adds an alias for the specified {@link BehaviorHandler}, if one does not already exist.
     *
     * @param behaviorHandler The {@code BehaviorHandler} to add a new alias for.
     */
    public static void addListenerList(BehaviorHandler behaviorHandler) {
        if (!BehaviorListenerLists.containsKey(behaviorHandler)) {
            BehaviorListenerLists.put(behaviorHandler, new ArrayList<>());
        }
    }

    /**
     * Removes the list aliased to the specified {@link BehaviorHandler}, and all elements inside.
     *
     * @param behaviorHandler The {@code BehaviorHandler} to remove the alias for.
     */
    public static void removeListenerList(BehaviorHandler behaviorHandler) {
        BehaviorListenerLists.remove(behaviorHandler);
    }

    /**
     * Removes all elements from the list aliased to the specified {@link BehaviorHandler}.
     *
     * @param behaviorHandler The {@code BehaviorHandler} used as the alias to remove all behavior listeners.
     */
    public static void clearListenerList(BehaviorHandler behaviorHandler) {
        BehaviorListenerLists.get(behaviorHandler).clear();
    }

    /**
     * Initializes the behavior listeners aliased to the specified {@link BehaviorHandler}.
     *
     * @param behaviorHandler The {@code BehaviorHandler} used as the alias to initialize the behavior listeners for.
     */
    public static void initBehaviorListeners(BehaviorHandler behaviorHandler) {
        for (GameObject listener : BehaviorListenerLists.get(behaviorHandler)) {
            listener.initBehaviors();
        }
    }

    /**
     * Updates the behavior listeners aliased to the specified {@link BehaviorHandler}.
     *
     * @param behaviorHandler The {@code BehaviorHandler} used as the alias to update the behavior listeners for.
     */
    public static void updateBehaviorListeners(BehaviorHandler behaviorHandler) {
        for (GameObject listener : BehaviorListenerLists.get(behaviorHandler)) {
            listener.updateBehaviors();
        }
    }

    /** Resets the behavior manager entirely. */
    public static void reset() {
        for (List<GameObject> list : BehaviorListenerLists.values()) {
            list.clear();
        }
        BehaviorListenerLists.clear();
    }
}
