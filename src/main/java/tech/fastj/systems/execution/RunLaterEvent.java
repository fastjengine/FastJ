package tech.fastj.systems.execution;

import tech.fastj.engine.FastJEngine;
import tech.fastj.gameloop.CoreLoopState;
import tech.fastj.gameloop.event.Event;

/**
 * Event for {@link FastJEngine#runLater(Runnable) running actions later in FastJ}.
 *
 * @author Andrew Dey
 * @since 1.7.0
 */
public class RunLaterEvent extends Event {

    private final Runnable runLater;

    /**
     * Constructor for a {@link RunLaterEvent}.
     * <p>
     * When trying to {@link FastJEngine#runLater(Runnable) run an action later in FastJ}, you usually do not need to create the event
     * yourself. The method calls {@link FastJEngine#runLater(Runnable)} and {@link FastJEngine#runLater(Runnable, CoreLoopState)} both
     * handle this for you.
     *
     * @param runLater The {@link Runnable task} the event was created to run.
     */
    public RunLaterEvent(Runnable runLater) {
        this.runLater = runLater;
    }

    /** {@return the {@link Runnable task} the event was created to run} */
    public Runnable getRunLater() {
        return runLater;
    }
}
