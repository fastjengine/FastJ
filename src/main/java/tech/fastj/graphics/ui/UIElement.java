package tech.fastj.graphics.ui;

import tech.fastj.graphics.Drawable;
import tech.fastj.graphics.display.Camera;
import tech.fastj.input.InputActionEvent;
import tech.fastj.systems.control.GameHandler;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * A drawable to be used as UI.
 *
 * @author Andrew Dey
 * @since 1.0.0
 */
public abstract class UIElement<T extends InputActionEvent> extends Drawable {

    protected final List<Consumer<T>> onActionEvents;
    protected EventCondition onActionCondition;

    /**
     * Instantiates the {@code UIElement}'s internals, and adds it to the origin scene as a ui element.
     *
     * @param origin The game handler which this UIElement is tied to.
     */
    protected UIElement(GameHandler origin) {
        onActionEvents = new ArrayList<>();
        origin.drawableManager().addUIElement(this);
    }

    /**
     * Sets the "onAction" condition, the determinant of whether the ui elements' {@code onAction} events will be fired.
     *
     * @param condition The condition to set.
     */
    protected void setOnActionCondition(EventCondition condition) {
        onActionCondition = condition;
    }

    /**
     * Sets the UIElement's {@code onAction} event to the specified action.
     *
     * @param action The action to set.
     * @return The {@code UIElement}, for method chaining.
     */
    public UIElement<T> setOnAction(Consumer<T> action) {
        onActionEvents.clear();
        onActionEvents.add(action);
        return this;
    }

    /**
     * Adds the specified action to the UIElement's {@code onAction} events.
     *
     * @param action The action to add.
     * @return The {@code UIElement}, for method chaining.
     */
    public UIElement<T> addOnAction(Consumer<T> action) {
        onActionEvents.add(action);
        return this;
    }

    /**
     * Renders the {@code UIElement} to the parameter {@code Graphics2D} object, aligning with the window by rendering at the inverse
     * translation of the specified {@code Camera}.
     *
     * @param g      {@code Graphics2D} parameter that the {@code UIElement} will be rendered to.
     * @param camera {@code Camera} to help render at the correct position on the screen.
     */
    public final void renderAsGUIObject(Graphics2D g, Camera camera) {
        AffineTransform oldTransform = (AffineTransform) g.getTransform().clone();
        try {
            g.transform(camera.getTransformation().createInverse());
        } catch (NoninvertibleTransformException exception) {
            throw new IllegalStateException(
                "Couldn't create an inverse transform of " + camera.getTransformation(),
                exception
            );
        }

        render(g);
        g.setTransform(oldTransform);
    }

    /**
     * Renders the {@code UIElement} to the parameter {@code Graphics2D} object, pre-aligned with the window after transforming the graphics
     * transform to the current game {@code Camera}.
     *
     * @param g {@code Graphics2D} parameter that the {@code UIElement} will be rendered to.
     */
    public abstract void render(Graphics2D g);

    /**
     * Removes the {@code UIElement}'s references in the specified scene as a GUI object.
     *
     * @param origin {@code GameHandler} parameter that will have all references to this {@code UIElement} removed.
     */
    @Override
    protected void destroyTheRest(GameHandler origin) {
        super.destroyTheRest(origin);
        origin.drawableManager().removeUIElement(this);
    }
}
