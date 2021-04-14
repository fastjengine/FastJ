package io.github.lucasstarsz.fastj.graphics.ui;

import java.awt.AWTEvent;

/**
 * Class to ensure that an event only applies to a {@link UIElement} if the condition specified is met.
 *
 * @author Andrew Dey
 * @version 1.0.0
 */
public interface EventCondition {

    /**
     * The condition to check for before firing off event actions in a {@link UIElement}.
     *
     * @param event The event which caused the check to be run.
     * @return Whether or not the condition is met.
     */
    boolean condition(AWTEvent event);
}
