package io.github.lucasstarsz.fastj.framework.ui;

import io.github.lucasstarsz.fastj.framework.io.mouse.MouseActionListener;
import io.github.lucasstarsz.fastj.framework.render.Camera;
import io.github.lucasstarsz.fastj.framework.render.Drawable;
import io.github.lucasstarsz.fastj.framework.systems.game.Scene;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * A drawable to be used as UI.
 */
public abstract class UIElement extends Drawable implements MouseActionListener {

    private final List<Consumer<MouseEvent>> onActionEvents;
    private EventCondition onActionCondition;

    protected UIElement(Scene origin) {
        onActionEvents = new ArrayList<>();
        origin.addMouseActionListener(this);
    }

    protected void setOnActionCondition(EventCondition condition) {
        onActionCondition = condition;
    }

    public void receivedOnActionEvent(MouseEvent event) {
        if (onActionCondition.condition(event)) {
            onActionEvents.forEach(action -> action.accept(event));
        }
    }

    public UIElement setOnAction(Consumer<MouseEvent> action) {
        onActionEvents.clear();
        onActionEvents.add(action);
        return this;
    }

    public UIElement addOnAction(Consumer<MouseEvent> action) {
        onActionEvents.add(action);
        return this;
    }

    /**
     * Renders the {@code Drawable} to the parameter {@code Graphics2D} object, while avoiding the specified {@code
     * Camera}'s transformation.
     *
     * @param g      {@code Graphics2D} parameter that the {@code Drawable} will be rendered to.
     * @param camera {@code Camera} to help render at the correct position on the screen.
     */
    public abstract void renderAsGUIObject(Graphics2D g, Camera camera);

    @Override
    protected void destroyTheRest(Scene origin) {
        super.destroyTheRest(origin);

        origin.removeGUIObject(this);
        origin.removeMouseActionListener(this);
    }

    @Override
    public void onMousePressed(MouseEvent mouseEvent) {
        receivedOnActionEvent(mouseEvent);
    }
}
