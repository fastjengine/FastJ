package tech.fastj.systems.behaviors;

import tech.fastj.graphics.game.GameObject;

import java.util.List;

/**
 * Interface denoting that the implementing classes directly interface with the {@link BehaviorManager} class for handling game objects'
 * {@link Behavior behaviors}.
 * <p>
 * <b>FOR IMPLEMENTORS:</b> In order for these methods to work you need to call
 * {@link BehaviorManager#addListenerList(BehaviorHandler)} upon construction.
 *
 * @author Andrew Dey
 * @since 1.5.0
 */
public interface BehaviorHandler {

    /**
     * Gets the behavior listeners assigned to the behavior handler.
     *
     * @return The behavior listeners of the behavior handler.
     */
    default List<GameObject> getBehaviorListeners() {
        return BehaviorManager.getList(this);
    }

    /**
     * Adds the specified behavior listener to the behavior handler.
     *
     * @param listener The behavior listener to add.
     */
    default void addBehaviorListener(GameObject listener) {
        BehaviorManager.addListener(this, listener);
    }

    /**
     * Removes the specified behavior listener from the behavior handler.
     *
     * @param listener The behavior listener to remove.
     */
    default void removeBehaviorListener(GameObject listener) {
        BehaviorManager.removeListener(this, listener);
    }

    /** Initializes all behavior listeners in the behavior handler. */
    default void initBehaviorListeners() {
        BehaviorManager.initBehaviorListeners(this);
    }

    /** Updates (fixed update) all behavior listeners in the behavior handler. */
    default void fixedUpdateBehaviorListeners() {
        BehaviorManager.fixedUpdateBehaviorListeners(this);
    }

    /** Updates all behavior listeners in the behavior handler. */
    default void updateBehaviorListeners() {
        BehaviorManager.updateBehaviorListeners(this);
    }

    /** Destroys all behaviors in the behavior handler, without removing them. */
    default void destroyBehaviorListeners() {
        BehaviorManager.destroyListenerList(this);
    }

    /** Removes all behavior listeners in the behavior handler. */
    default void clearBehaviorListeners() {
        BehaviorManager.clearListenerList(this);
    }
}
