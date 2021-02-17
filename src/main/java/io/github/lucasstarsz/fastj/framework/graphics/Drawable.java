package io.github.lucasstarsz.fastj.framework.graphics;

import io.github.lucasstarsz.fastj.framework.CrashMessages;
import io.github.lucasstarsz.fastj.framework.graphics.util.DrawUtil;
import io.github.lucasstarsz.fastj.framework.math.Pointf;
import io.github.lucasstarsz.fastj.framework.systems.behaviors.Behavior;
import io.github.lucasstarsz.fastj.framework.systems.game.Scene;
import io.github.lucasstarsz.fastj.framework.systems.tags.TaggableEntity;

import io.github.lucasstarsz.fastj.engine.FastJEngine;

import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * The abstract class to objects that can be drawn to a {@code Display}.
 * <p>
 * A {@code Drawable} is any object that can be drawn to a {@code Display}, and destroyed (freed from memory). A {@code
 * Drawable} utilizes components from the {@code Java2D} graphics API, allowing for an easy way to work with the
 * following:
 * <ul>
 * 		<li>Transformations through {@code AffineTransform}.</li>
 * 		<li>Rendering through {@code Graphics2D}.</li>
 * 		<li>Global (and soon, per-object) render quality settings through {@code RenderingHints}.</li>
 * </ul>
 * <p>
 * Besides being able to utilize {@code Java2D} components, A {@code Drawable} contains components
 * from some of FastJ's classes to organize your game into an easily managed system, such as:
 * <ul>
 * 		<li>A {@code Behavior} system that provides an easy way to modify objects on initialization, and each game update.</li>
 * 		<li>A {@code TaggableEntity} system that allows objects to be given tags, which provides an easy way to find/identify a large amount of objects at once.</li>
 * </ul>
 * <p>
 * In general, a {@code Drawable} will most often be at the base of whatever you decide to render
 * using this engine.
 *
 * @version 1.0.0
 * @see io.github.lucasstarsz.fastj.framework.io.Display
 * @see io.github.lucasstarsz.fastj.framework.systems.behaviors.Behavior
 * @see io.github.lucasstarsz.fastj.framework.systems.tags.TaggableEntity
 * @since 1.0.0
 */
public abstract class Drawable extends TaggableEntity {

    private static final String collisionErrorMessage = CrashMessages.theGameCrashed("a collision error.");

    private final UUID rawID;
    private final String id;
    private final List<Behavior> behaviors;
    protected Path2D.Float collisionPath;
    private boolean shouldRender;
    private Pointf[] boundaries;

    /** Constructs a {@code Drawable}, initializing its internal variables. */
    protected Drawable() {
        behaviors = new ArrayList<>();

        rawID = UUID.randomUUID();
        id = "DRAWABLE$" + getClass().getSimpleName() + "_" + rawID.toString();
    }

    /**
     * Renders the {@code Drawable} to the specified {@code Graphics2D} parameter.
     *
     * @param g {@code Graphics2D} parameter that the {@code Drawable} will be rendered to.
     * @see Graphics2D
     */
    public abstract void render(Graphics2D g);

    /**
     * Renders the {@code Drawable} to the parameter {@code Graphics2D} object, while avoiding the specified {@code
     * Camera}'s transformation.
     *
     * @param g      {@code Graphics2D} parameter that the {@code Drawable} will be rendered to.
     * @param camera {@code Camera} to help render at the correct position on the screen.
     * @see Graphics2D
     * @see Camera
     */
    public abstract void renderAsGUIObject(Graphics2D g, Camera camera);

    /**
     * Destroys all memory the {@code Drawable} uses.
     * <p>
     * This also removes any internal references that the {@code Drawable} may have.
     *
     * @param originScene The origin of this {@code Drawable}.
     * @see io.github.lucasstarsz.fastj.framework.systems.game.Scene
     */
    public abstract void destroy(Scene originScene);

    /**
     * Gets the collision path of the {@code Drawable}.
     *
     * @return The collision path of the {@code Drawable}, as a {@code Path2D.Float}.
     * @see Path2D.Float
     */
    public Path2D.Float getCollisionPath() {
        return collisionPath;
    }

    /**
     * Sets the collision path to the specified parameter.
     *
     * @param path {@code Path2D.Float} parameter that the collision path will be set to.
     * @see Path2D.Float
     */
    protected void setCollisionPath(Path2D.Float path) {
        collisionPath = path;
    }

    /**
     * Gets the {@code String} ID of the {@code Drawable}.
     *
     * @return String that represents the ID of the {@code Drawable}.
     */
    public String getID() {
        return id;
    }

    /**
     * Gets the raw {@code UUID} of the {@code Drawable}.
     *
     * @return The {@code UUID} that represents the raw ID of the {@code Drawable}.
     * @see UUID
     */
    public UUID getUUID() {
        return rawID;
    }

    /**
     * Gets the boundaries of the {@code Drawable}.
     * <p>
     * Bounds are in the same order as specified in the {@link io.github.lucasstarsz.fastj.framework.graphics.Boundary}.
     * <p>
     * If you're looking to get a specific bound, use {@code getBound(Boundary)} instead.
     *
     * @return The {@code Pointf} array that contains the bounds of the {@code Drawable}.
     * @see io.github.lucasstarsz.fastj.framework.math.Pointf
     */
    public Pointf[] getBounds() {
        return boundaries;
    }

    /**
     * Sets the boundaries of the {@code Drawable} to the specified {@code Pointf} array.
     * <p>
     * The specified array must be exactly 4 points. If there is any deviancy from this, the game will crash out with an
     * error specifying this.
     *
     * @param bounds The {@code Pointf} array that the boundaries of the {@code Drawable} will be set to.
     * @see io.github.lucasstarsz.fastj.framework.math.Pointf
     */
    protected void setBounds(Pointf[] bounds) {
        if (bounds.length != 4) {
            FastJEngine.error(CrashMessages.illegalAction(getClass()),
                    new IllegalArgumentException("The boundaries for a Drawable must only have 4 points."));
        }

        boundaries = bounds;
    }

    /**
     * Gets one of the boundaries of the {@code Drawable}, based on the specified {@code Boundary} parameter.
     *
     * @param boundary The requested {@code Boundary}.
     * @return The bound that corresponds with the specified {@code Boundary}.
     * @see io.github.lucasstarsz.fastj.framework.math.Pointf
     * @see io.github.lucasstarsz.fastj.framework.graphics.Boundary
     */
    public Pointf getBound(Boundary boundary) {
        return boundaries[boundary.location];
    }

    /**
     * Gets the center point of the {@code Drawable}.
     *
     * @return The center point, as a {@code Pointf}.
     * @see io.github.lucasstarsz.fastj.framework.math.Pointf
     */
    public Pointf getCenter() {
        return DrawUtil.centerOf(boundaries);
    }

    /**
     * Gets the list of {@code Behavior}s for the {@code Drawable}.
     *
     * @return The list of {@code Behavior}s that the {@code Drawable} has.
     * @see io.github.lucasstarsz.fastj.framework.systems.behaviors.Behavior
     */
    public List<Behavior> getBehaviors() {
        return behaviors;
    }

    /**
     * Gets the value that defines whether the {@code Drawable} should be rendered.
     *
     * @return Boolean value that defines whether the {@code Drawable} should be rendered.
     */
    public boolean shouldRender() {
        return shouldRender;
    }

    /**
     * Sets whether the {@code Drawable} should be rendered.
     *
     * @param shouldBeRendered Boolean parameter that defines whether the {@code Drawable} should be rendered.
     */
    public void setShouldRender(boolean shouldBeRendered) {
        shouldRender = shouldBeRendered;
    }

    /**
     * Determines whether or not two objects are colliding (intersection).
     *
     * @param obj The other {@code Drawable} that is being tested against this {@code Drawable}.
     * @return Boolean value that states whether the two {@code Drawable}s intersect.
     */
    public boolean collidesWith(Drawable obj) {
        Area otherObject, thisObject;

        try {
            otherObject = new Area(obj.collisionPath);
        } catch (NullPointerException e) {
            if (!FastJEngine.getLogicManager().isSwitchingScenes()) {
                FastJEngine.error(collisionErrorMessage, new NullPointerException("Collision path for Drawable with id: " + obj.id + " is null"));
            }
            return false;
        }

        try {
            thisObject = new Area(collisionPath);
        } catch (NullPointerException e) {
            if (!FastJEngine.getLogicManager().isSwitchingScenes()) {
                FastJEngine.error(collisionErrorMessage, new NullPointerException("Collision path for Drawable with id: " + id + " is null"));
            }
            return false;
        }

        otherObject.intersect(thisObject);
        return !otherObject.isEmpty();
    }

    /** Calls the {@code init} method of the {@code Drawable}'s behaviors. */
    public void initBehaviors() {
        List<Behavior> behaviorsCopy = new ArrayList<>(behaviors);
        for (Behavior behavior : behaviorsCopy) {
            behavior.init(this);
        }
    }

    /** Calls the {@code update} method of the {@code Drawable}'s behaviors. */
    public void updateBehaviors() {
        List<Behavior> behaviorsCopy = new ArrayList<>(behaviors);
        for (Behavior behavior : behaviorsCopy) {
            behavior.update(this);
        }
    }

    /** Calls the {@code destroy} method of the {@code Drawable}'s behaviors. */
    public void destroyAllBehaviors() {
        for (Behavior behavior : behaviors) {
            behavior.destroy();
        }
    }

    /** Clears the {@code Drawable}'s list of {@code Behavior}s. */
    public void clearAllBehaviors() {
        behaviors.clear();
    }

    /**
     * Adds the specified {@code Behavior} to the {@code Drawable}'s list of {@code Behavior}s.
     * <p>
     * {@code Behavior}s can be added as many times as needed.
     *
     * @param behavior {@code Behavior} parameter to be added.
     * @param origin   Scene that the {@code Drawable} will be added to, as a behavior listener.
     * @return the {@code Drawable} is returned for method chaining.
     * @see io.github.lucasstarsz.fastj.framework.systems.behaviors.Behavior
     * @see io.github.lucasstarsz.fastj.framework.systems.game.Scene
     */
    public Drawable addBehavior(Behavior behavior, Scene origin) {
        behaviors.add(behavior);
        origin.addBehaviorListener(this);

        return this;
    }

    /**
     * Removes the specified {@code Behavior} from the {@code Drawable}'s list of {@code Behavior}s.
     *
     * @param behavior    {@code Behavior} parameter to be removed from.
     * @param originScene Scene that, if the {@code Drawable} no longer has any Behaviors, the {@code Drawable} will be
     *                    removed from as a behavior listener.
     * @return the {@code Drawable} is returned for method chaining.
     * @see io.github.lucasstarsz.fastj.framework.systems.behaviors.Behavior
     * @see io.github.lucasstarsz.fastj.framework.systems.game.Scene
     */
    public Drawable removeBehavior(Behavior behavior, Scene originScene) {
        behaviors.remove(behavior);
        if (behaviors.size() == 0) originScene.removeBehaviorListener(this);

        return this;
    }

    /**
     * Adds the {@code Drawable} to the {@code Scene} parameter's list of game objects.
     *
     * @param origin {@code Scene} parameter that will add the {@code Drawable} to its list of game objects.
     * @return the {@code Drawable} is returned for method chaining.
     * @see io.github.lucasstarsz.fastj.framework.systems.game.Scene
     */
    public Drawable addAsGameObject(Scene origin) {
        origin.addGameObject(this);
        return this;
    }

    /**
     * Adds the {@code Drawable} to the {@code Scene} parameter's list of GUI objects.
     *
     * @param origin {@code Scene} parameter that will add the {@code Drawable} to its list of GUI objects.
     * @return the {@code Drawable} is returned for method chaining.
     * @see io.github.lucasstarsz.fastj.framework.systems.game.Scene
     */
    public Drawable addAsGUIObject(Scene origin) {
        origin.addGUIObject(this);
        return this;
    }

    /**
     * Translates the boundaries of the {@code Drawable} by the specified {@code Pointf}.
     *
     * @param translation {@code Pointf} that the boundaries of the {@code Drawable} will be moved by.
     * @see io.github.lucasstarsz.fastj.framework.math.Pointf
     */
    protected void translateBounds(Pointf translation) {
        for (Pointf bound : boundaries) {
            bound.add(translation);
        }
    }

    /**
     * Destroys the {@code Drawable}'s {@code Drawable} components, as well as any references the {@code Drawable} has
     * within the {@code Scene} parameter.
     *
     * @param origin {@code Scene} parameter that will have all references to this {@code Drawable} removed.
     * @see io.github.lucasstarsz.fastj.framework.systems.game.Scene
     */
    protected void destroyTheRest(Scene origin) {
        origin.removeGameObject(this);
        origin.removeGUIObject(this);

        origin.removeBehaviorListener(this);
        origin.removeTaggableEntity(this);

        destroyAllBehaviors();
        clearAllBehaviors();
        clearTags();

        if (collisionPath != null) {
            collisionPath.reset();
            collisionPath = null;
        }

        boundaries = null;
    }

    @Override
    public String toString() {
        return "Drawable{" +
                "rawID=" + rawID +
                ", id=" + id +
                ", shouldRender=" + shouldRender +
                ", boundaries=" + Arrays.toString(boundaries) +
                ", collisionPath=" + collisionPath +
                ", behaviors=" + behaviors +
                '}';
    }
}
