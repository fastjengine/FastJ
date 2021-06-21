package tech.fastj.graphics;

import tech.fastj.engine.CrashMessages;
import tech.fastj.engine.FastJEngine;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.game.GameObject;
import tech.fastj.graphics.ui.UIElement;
import tech.fastj.graphics.util.DrawUtil;

import tech.fastj.systems.control.Scene;
import tech.fastj.systems.tags.TaggableEntity;

import java.awt.geom.Path2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.Arrays;
import java.util.UUID;

/**
 * The abstract class to objects that can be drawn to a {@code Display}.
 * <p>
 * A {@code Drawable} is any object that can be drawn to a {@code Display}, and destroyed (freed from memory).
 *
 * @author Andrew Dey
 * @version 1.0.0
 */
public abstract class Drawable extends TaggableEntity {

    private static final String GameObjectErrorMessage = CrashMessages.theGameCrashed("a game object error.");
    private static final String UiElementErrorMessage = CrashMessages.theGameCrashed("a ui element error.");

    private final UUID rawID;
    private final String id;

    /** The shape defining where the Drawable collides. */
    protected Path2D.Float collisionPath;
    private boolean shouldRender;
    private Pointf[] boundaries;
    protected final Transform2D transform;

    /** Constructs a {@code Drawable}, initializing its internal variables. */
    protected Drawable() {
        rawID = UUID.randomUUID();
        id = "DRAWABLE$" + getClass().getSimpleName() + "_" + rawID;
        transform = new Transform2D();
    }

    /**
     * Destroys all memory the {@code Drawable} uses.
     * <p>
     * This also removes any internal references that the {@code Drawable} may have.
     *
     * @param originScene The origin of this {@code Drawable}.
     */
    public abstract void destroy(Scene originScene);

    /**
     * Gets the collision path of the {@code Drawable}.
     *
     * @return The collision path of the {@code Drawable}, as a {@code Path2D.Float}.
     */
    public Path2D.Float getCollisionPath() {
        return collisionPath;
    }

    /**
     * Sets the collision path to the specified parameter.
     *
     * @param path {@code Path2D.Float} parameter that the collision path will be set to.
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
     */
    public UUID getUUID() {
        return rawID;
    }

    /**
     * Gets the boundaries of the {@code Drawable}.
     * <p>
     * Bounds are in the same order as specified in the {@link Boundary}.
     * <p>
     * If you're looking to get a specific bound, use {@code getBound(Boundary)} instead.
     *
     * @return The {@code Pointf} array that contains the bounds of the {@code Drawable}.
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
     */
    public Pointf getBound(Boundary boundary) {
        return boundaries[boundary.location];
    }

    /**
     * Gets the center point of the {@code Drawable}.
     *
     * @return The center point, as a {@code Pointf}.
     */
    public Pointf getCenter() {
        return DrawUtil.centerOf(boundaries);
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
     * @return The {@code Drawable}, for method chaining.
     */
    public Drawable setShouldRender(boolean shouldBeRendered) {
        shouldRender = shouldBeRendered;
        return this;
    }

    /**
     * Determines whether or not two objects are colliding (intersection).
     *
     * @param obj The other {@code Drawable} that is being tested against this {@code Drawable}.
     * @return Boolean value that states whether the two {@code Drawable}s intersect.
     */
    public boolean collidesWith(Drawable obj) {
        Area thisObject = new Area(collisionPath);
        Area otherObject = new Area(obj.collisionPath);

        otherObject.intersect(thisObject);
        return !otherObject.isEmpty();
    }

    /**
     * Gets the {@code Drawable}'s translation.
     *
     * @return A {@code Pointf} that represents the current translation of the {@code Drawable}.
     */
    public Pointf getTranslation() {
        return transform.getTranslation();
    }

    /**
     * Sets the {@code Drawable}'s translation to the specified value.
     *
     * @param setTranslation {@code Pointf} parameter that the {@code Drawable}'s translation will be set to.
     * @return The {@code Drawable}, for method chaining.
     */
    public Drawable setTranslation(Pointf setTranslation) {
        transform.setTranslation(setTranslation);
        return this;
    }

    /**
     * Gets the {@code Drawable}'s rotation.
     *
     * @return A float that represents the current rotation of the {@code Drawable}.
     */
    public float getRotation() {
        return transform.getRotation();
    }

    /**
     * Sets the {@code Drawable}'s rotation to the specified value.
     *
     * @param setRotation float parameter that the {@code Drawable}'s rotation will be set to.
     * @return The {@code Drawable}, for method chaining.
     */
    public Drawable setRotation(float setRotation) {
        transform.setRotation(setRotation);
        return this;
    }

    /**
     * Gets the {@code Drawable}'s scale.
     *
     * @return A {@code Pointf} that represents the current scale of the object.
     */
    public Pointf getScale() {
        return transform.getScale();
    }

    /**
     * Sets the {@code Drawable}'s scale to the specified value.
     *
     * @param setScale {@code Pointf} parameter that the {@code Drawable}'s scale will be set to.
     * @return The {@code Drawable}, for method chaining.
     */
    public Drawable setScale(Pointf setScale) {
        transform.setScale(setScale);
        return this;
    }

    /**
     * Translates the {@code Drawable}'s position by the specified translation.
     *
     * @param translationMod {@code Pointf} parameter that the {@code Drawable}'s x and y location will be translated
     *                       by.
     */
    public void translate(Pointf translationMod) {
        transform.translate(translationMod);
    }

    /**
     * Rotates the {@code Drawable} in the direction of the specified rotation, about the specified center point.
     *
     * @param rotationMod float parameter that the {@code Drawable} will be rotated by.
     * @param centerpoint {@code Pointf} parameter that the {@code Drawable} will be rotated about.
     */
    public void rotate(float rotationMod, Pointf centerpoint) {
        transform.rotate(rotationMod, centerpoint);
    }

    /**
     * Scales the {@code Drawable} in by the amount specified in the specified scale, from the specified center point.
     *
     * @param scaleMod    {@code Pointf} parameter that the {@code Drawable}'s width and height will be scaled by.
     * @param centerpoint {@code Pointf} parameter that the {@code Drawable} will be scaled about.
     */
    public void scale(Pointf scaleMod, Pointf centerpoint) {
        transform.scale(scaleMod, centerpoint);
    }

    /**
     * Gets the rotation, normalized to be within a range of {@code (-360, 360)}.
     *
     * @return The normalized rotation.
     */
    public float getRotationWithin360() {
        return transform.getRotationWithin360();
    }

    /**
     * Gets the entire transformation of the {@code Drawable}.
     *
     * @return The transformation, as an {@link AffineTransform}.
     */
    public AffineTransform getTransformation() {
        return transform.getAffineTransform();
    }

    /**
     * Rotates the {@code Drawable} in the direction of the specified rotation, about its center.
     *
     * @param rotationMod float parameter that the {@code Drawable} will be rotated by.
     */
    public void rotate(float rotationMod) {
        rotate(rotationMod, getCenter());
    }

    /**
     * Scales the {@code Drawable} in by the amount specified in the specified scale, about its center.
     *
     * @param scaleXY float parameter that the {@code Drawable} will be scaled by, acting as both the x and y values.
     */
    public void scale(float scaleXY) {
        scale(new Pointf(scaleXY), getCenter());
    }

    /**
     * Scales the {@code Drawable} in by the amount specified in the specified scale, about its center.
     *
     * @param scaleMod {@code Pointf} parameter that the {@code Drawable} will be scaled by, based on its x and y
     *                 values.
     */
    public void scale(Pointf scaleMod) {
        scale(scaleMod, getCenter());
    }

    /**
     * Adds the {@code Drawable} to the {@code Scene} parameter's list of game objects.
     *
     * @param origin {@code Scene} parameter that will add the {@code Drawable} to its list of game objects.
     * @return the {@code Drawable} is returned for method chaining.
     */
    public GameObject addAsGameObject(Scene origin) {
        if (this instanceof GameObject) {
            origin.drawableManager.addGameObject((GameObject) this);
            return (GameObject) this;
        } else {
            FastJEngine.error(GameObjectErrorMessage, new IllegalStateException("Cannot add non-game object as a game object."));
            return null;
        }
    }

    /**
     * Adds the {@code Drawable} to the {@code Scene} parameter's list of GUI objects.
     *
     * @param origin {@code Scene} parameter that will add the {@code Drawable} to its list of GUI objects.
     * @return the {@code Drawable} is returned for method chaining.
     */
    public UIElement addAsGUIObject(Scene origin) {
        if (this instanceof UIElement) {
            origin.drawableManager.addUIElement((UIElement) this);
            return (UIElement) this;
        } else {
            FastJEngine.error(UiElementErrorMessage, new IllegalStateException("Cannot add non-ui object as a ui object."));
            return null;
        }
    }

    /**
     * Translates the boundaries of the {@code Drawable} by the specified {@code Pointf}.
     *
     * @param translation {@code Pointf} that the boundaries of the {@code Drawable} will be moved by.
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
     */
    protected void destroyTheRest(Scene origin) {
        origin.removeTaggableEntity(this);
        clearTags();

        collisionPath = null;
        boundaries = null;
    }

    @Override
    public String toString() {
        return "Drawable{" +
                "rawID=" + rawID +
                ", id='" + id + '\'' +
                ", collisionPath=" + collisionPath +
                ", shouldRender=" + shouldRender +
                ", boundaries=" + Arrays.toString(boundaries) +
                '}';
    }
}
