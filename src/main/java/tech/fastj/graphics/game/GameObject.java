package tech.fastj.graphics.game;

import tech.fastj.graphics.Drawable;
import tech.fastj.systems.behaviors.Behavior;
import tech.fastj.systems.behaviors.BehaviorHandler;
import tech.fastj.systems.collections.ManagedList;
import tech.fastj.systems.control.GameHandler;

import java.awt.Graphics2D;
import java.util.List;
import java.util.Objects;

/**
 * A type of {@link Drawable} that can be easily transformed and otherwise manipulated.
 * <p>
 * The {@code GameObject} class is one of the most useful backing classes in the engine. It contains the logic needed to
 * transform objects that can be rendered to the screen -- polygons, models, and (still a work in progress) text.
 *
 * @author Andrew Dey
 * @since 1.0.0
 */
public abstract class GameObject extends Drawable {

    private final ManagedList<Behavior> behaviors;

    /** Initializes {@link GameObject} internals. */
    protected GameObject() {
        behaviors = new ManagedList<>();
    }

    /**
     * Gets the {@code GameObject}'s list of {@link Behavior}s.
     *
     * @return The list of {@code Behavior}s.
     */
    public List<Behavior> getBehaviors() {
        return behaviors;
    }

    /** Calls the {@link Behavior#init(GameObject)} method for each of the {@code GameObject}'s behaviors. */
    public void initBehaviors() {
        behaviors.run(list -> {
            for (Behavior behavior : list) {
                behavior.init(this);
            }
        });
    }

    /** Calls the {@link Behavior#fixedUpdate(GameObject)} method for each of the {@code GameObject}'s behaviors. */
    public void fixedUpdateBehaviors() {
        behaviors.run(list -> {
            for (Behavior behavior : list) {
                behavior.fixedUpdate(this);
            }
        });
    }

    /** Calls the {@link Behavior#update(GameObject)} method for each of the {@code GameObject}'s behaviors. */
    public void updateBehaviors() {
        behaviors.run(list -> {
            for (Behavior behavior : list) {
                behavior.update(this);
            }
        });
    }

    /** Calls the {@link Behavior#destroy()} method for each of the {@code GameObject}'s behaviors. */
    public synchronized void destroyAllBehaviors() {
        behaviors.shutdownNow();
        for (Behavior behavior : behaviors) {
            behavior.destroy();
        }
    }

    /** Clears the {@code GameObject}'s list of {@code Behavior}s. */
    public void clearAllBehaviors() {
        behaviors.clear();
    }

    /**
     * Adds the specified {@link Behavior} to the {@code GameObject}'s list of {@code Behavior}s.
     * <p>
     * {@code Behavior}s can be added as many times as needed.
     *
     * @param behavior        {@code Behavior} parameter to be added.
     * @param behaviorHandler Handler that the {@code GameObject} will be added to, as a behavior listener.
     * @return the {@code GameObject} is returned for method chaining.
     */
    public GameObject addBehavior(Behavior behavior, BehaviorHandler behaviorHandler) {
        behaviors.add(behavior);
        behaviorHandler.addBehaviorListener(this);

        return this;
    }

    /**
     * Adds the specified {@link Behavior} to the {@code GameObject}'s list of {@code Behavior}s, and initializes it.
     * <p>
     * This does not check to make sure the game is running -- it only initializes the given behavior.
     * <p>
     * {@code Behavior}s can be added as many times as needed.
     *
     * @param behavior        {@code Behavior} parameter to be added.
     * @param behaviorHandler Handler that the {@code GameObject} will be added to, as a behavior listener.
     * @return the {@code GameObject} is returned for method chaining.
     */
    public GameObject addLateBehavior(Behavior behavior, BehaviorHandler behaviorHandler) {
        behaviors.add(behavior);
        behaviorHandler.addBehaviorListener(this);
        behavior.init(this);

        return this;
    }

    /**
     * Removes the specified {@link Behavior} from the {@code GameObject}'s list of {@code Behavior}s.
     *
     * @param behavior        {@code Behavior} parameter to be removed from.
     * @param behaviorHandler Handler that, if the {@code GameObject} no longer has any Behaviors, the
     *                        {@code GameObject} will be removed from as a behavior listener.
     * @return the {@code GameObject} is returned for method chaining.
     */
    public GameObject removeBehavior(Behavior behavior, BehaviorHandler behaviorHandler) {
        behaviors.remove(behavior);
        if (behaviors.isEmpty()) {
            behaviorHandler.removeBehaviorListener(this);
        }

        return this;
    }

    /**
     * Renders the {@code GameObject} to the specified {@link Graphics2D} parameter.
     *
     * @param g The {@code Graphics2D} parameter to render the {@code GameObject} to.
     */
    public abstract void render(Graphics2D g);

    /**
     * Destroys all references of the {@code GameObject}'s behaviors and removes its references from the {@link GameHandler}.
     *
     * @param origin {@code GameHandler} parameter that will have all references to this {@code GameObject} removed.
     */
    @Override
    protected void destroyTheRest(GameHandler origin) {
        super.destroyTheRest(origin);

        origin.drawableManager().removeGameObject(this);
        origin.removeBehaviorListener(this);

        destroyAllBehaviors();
        clearAllBehaviors();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        GameObject gameObject = (GameObject) other;
        return behaviors.equals(gameObject.behaviors) && transform.equals(gameObject.transform);
    }

    @Override
    public int hashCode() {
        return Objects.hash(behaviors, transform);
    }

    @Override
    public String toString() {
        return "GameObject{" +
                "behaviors=" + behaviors +
                ", transform=" + transform +
                '}';
    }
}
