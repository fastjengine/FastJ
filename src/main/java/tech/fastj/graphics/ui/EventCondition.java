package tech.fastj.graphics.ui;

import tech.fastj.input.InputActionEvent;

/**
 * Class to ensure that an event only applies to a {@link UIElement} if the condition specified is met.
 *
 * @author Andrew Dey
 * @since 1.0.0
 */
public interface EventCondition {

    /**
     * The condition to check for before firing off event actions in a {@link UIElement}.
     *
     * @param event The event which caused the check to be run.
     * @return Whether the condition is met.
     */
    boolean condition(InputActionEvent event);
}
