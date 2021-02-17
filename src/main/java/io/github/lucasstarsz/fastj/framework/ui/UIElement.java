package io.github.lucasstarsz.fastj.framework.ui;

import io.github.lucasstarsz.fastj.framework.render.Drawable;

import java.awt.AWTEvent;
import java.util.List;
import java.util.function.Consumer;

/**
 * A drawable to be used as UI.
 */
public abstract class UIElement extends Drawable {
    private List<Consumer<AWTEvent>> onActionEvents;
    private EventCondition onActionCondition;

    public void receivedOnActionEvent(AWTEvent event) {
        if (onActionCondition.condiiton(event)) {
            onActionEvents.forEach(action -> action.accept(event));
        }
    }

    public void setOnAction(Consumer<AWTEvent> action) {
        onActionEvents.clear();
        onActionEvents.add(action);
    }

    public void addOnAction(Consumer<AWTEvent> action) {
        onActionEvents.add(action);
    }
}
