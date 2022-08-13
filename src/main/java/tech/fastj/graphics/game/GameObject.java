package tech.fastj.graphics.game;

import tech.fastj.graphics.Drawable;
import tech.fastj.systems.behaviors.Behavior;
import tech.fastj.systems.behaviors.BehaviorHandler;
import tech.fastj.systems.control.GameHandler;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A type of {@link Drawable} that can be easily transformed and otherwise manipulated.
 * <p>
 * This is one of the most useful backing classes in the engine. It contains the logic needed to transform objects that can be rendered to
 * the screen -- {@link Polygon2D shapes}, {@link Model2D models}, {@link Text2D text}, {@link Light2D light}, and
 * {@link Sprite2D sprites}.
 *
 * @author Andrew Dey
 * @since 1.0.0
 */
public abstract class GameObject extends Drawable {

    private final List<Behavior> behaviors;

    /** Initializes {@link GameObject} internals. */
    protected GameObject() {
        behaviors = new ArrayList<>();
    }

    /** {@return the gmae object's {@link Behavior behaviors}} */
    public List<Behavior> getBehaviors() {
        return behaviors;
    }

    /** Calls the {@link Behavior#init(GameObject)} method for each of the {@link GameObject game object}'s behaviors. */
    public void initBehaviors() {
        for (Behavior behavior : behaviors) {
            behavior.init(this);
        }
    }

    /** Calls the {@link Behavior#fixedUpdate(GameObject)} method for each of the {@link GameObject game object}'s behaviors. */
    public void fixedUpdateBehaviors() {
        for (Behavior behavior : behaviors) {
            behavior.fixedUpdate(this);
        }
    }

    /** Calls the {@link Behavior#update(GameObject)} method for each of the {@link GameObject game object}'s behaviors. */
    public void updateBehaviors() {
        for (Behavior behavior : behaviors) {
            behavior.update(this);
        }
    }

    /** Calls the {@link Behavior#destroy()} method for each of the {@link GameObject game object}'s behaviors. */
    public synchronized void destroyAllBehaviors() {
        for (Behavior behavior : behaviors) {
            behavior.destroy();
        }
    }

    /** Clears the {@link GameObject}'s list of {@link Behavior behaviors}. */
    public void clearAllBehaviors() {
        behaviors.clear();
    }

    /**
     * Adds the specified {@link Behavior behavior} to the {@link GameObject}'s list of {@link Behavior behaviors}.
     *
     * @param behavior        {@code Behavior} parameter to be added.
     * @param behaviorHandler Handler that the {@link GameObject game object} will be added to, as a behavior listener.
     * @return the {@link GameObject game object} is returned for method chaining.
     */
    public GameObject addBehavior(Behavior behavior, BehaviorHandler behaviorHandler) {
        behaviors.add(behavior);
        behaviorHandler.addBehaviorListener(this);

        return this;
    }

    /**
     * Adds the specified {@link Behavior} to the {@link GameObject game object}'s list of {@link Behavior behaviors}, and initializes it.
     * <p>
     * This does not check to make sure the game is running -- it only initializes the given behavior.
     *
     * @param behavior        {@code Behavior} parameter to be added.
     * @param behaviorHandler Handler that the {@link GameObject game object} will be added to, as a behavior listener.
     * @return the {@link GameObject game object} is returned for method chaining.
     */
    public GameObject addLateBehavior(Behavior behavior, BehaviorHandler behaviorHandler) {
        behaviors.add(behavior);
        behaviorHandler.addBehaviorListener(this);
        behavior.init(this);

        return this;
    }

    /**
     * Removes the specified {@link Behavior} from the {@link GameObject game object}'s list of {@link Behavior behaviors}.
     *
     * @param behavior        {@code Behavior} parameter to be removed from.
     * @param behaviorHandler Handler that, if the {@link GameObject game object} no longer has any Behaviors, the
     *                        {@link GameObject game object} will be removed from as a behavior listener.
     * @return the {@link GameObject game object} is returned for method chaining.
     */
    public GameObject removeBehavior(Behavior behavior, BehaviorHandler behaviorHandler) {
        behaviors.remove(behavior);
        if (behaviors.isEmpty()) {
            behaviorHandler.removeBehaviorListener(this);
        }

        return this;
    }

    /**
     * Renders the {@link GameObject game object} to the specified {@link Graphics2D} parameter.
     *
     * @param g The {@code Graphics2D} parameter to render the {@link GameObject game object} to.
     */
    public abstract void render(Graphics2D g);

    /**
     * Destroys all references of the {@link GameObject game object}'s behaviors and removes its references from the {@link GameHandler}.
     *
     * @param origin {@code GameHandler} parameter that will have all references to this {@link GameObject game object} removed.
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
