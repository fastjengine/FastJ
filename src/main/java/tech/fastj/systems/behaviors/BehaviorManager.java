package tech.fastj.systems.behaviors;

import tech.fastj.graphics.game.GameObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class to manage behavior listeners for all {@link BehaviorHandler}s.
 *
 * @author Andrew Dey
 * @since 1.0.0
 */
public class BehaviorManager {

    private static final Map<BehaviorHandler, Map<String, GameObject>> BehaviorListenerLists = new ConcurrentHashMap<>();

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
        return new ArrayList<>(BehaviorListenerLists.get(behaviorHandler).values());
    }

    /**
     * Adds the specified behavior listener to the list aliased to the specified {@link BehaviorHandler}.
     *
     * @param behaviorHandler The {@code BehaviorHandler} used as the alias to add the specified behavior listener to.
     * @param listener        The behavior listener to add.
     */
    public static void addListener(BehaviorHandler behaviorHandler, GameObject listener) {
        if (!BehaviorListenerLists.get(behaviorHandler).containsKey(listener.getID())) {
            BehaviorListenerLists.get(behaviorHandler).put(listener.getID(), listener);
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
        BehaviorListenerLists.get(behaviorHandler).remove(listener.getID());
    }

    /**
     * Adds an alias for the specified {@link BehaviorHandler}, if one does not already exist.
     *
     * @param behaviorHandler The {@code BehaviorHandler} to add a new alias for.
     */
    public static void addListenerList(BehaviorHandler behaviorHandler) {
        if (!BehaviorListenerLists.containsKey(behaviorHandler)) {
            BehaviorListenerLists.put(behaviorHandler, new ConcurrentHashMap<>());
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
     * Destroys all behaviors from the list aliased to the specified {@link BehaviorHandler}, without removing them.
     *
     * @param behaviorHandler The {@code BehaviorHandler} used as the alias to destroy all behavior listeners.
     */
    public static void destroyListenerList(BehaviorHandler behaviorHandler) {
        for (GameObject listener : BehaviorListenerLists.get(behaviorHandler).values()) {
            listener.destroyAllBehaviors();
        }
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
        for (GameObject listener : BehaviorListenerLists.get(behaviorHandler).values()) {
            listener.initBehaviors();
        }
    }

    /**
     * Updates the behavior listeners aliased to the specified {@link BehaviorHandler}.
     *
     * @param behaviorHandler The {@code BehaviorHandler} used as the alias to update the behavior listeners for.
     */
    public static void updateBehaviorListeners(BehaviorHandler behaviorHandler) {
        for (GameObject listener : BehaviorListenerLists.get(behaviorHandler).values()) {
            listener.updateBehaviors();
        }
    }

    /** Resets the behavior manager entirely. */
    public static void reset() {
        for (Map<String, GameObject> map : BehaviorListenerLists.values()) {
            map.clear();
        }
        BehaviorListenerLists.clear();
    }
}
