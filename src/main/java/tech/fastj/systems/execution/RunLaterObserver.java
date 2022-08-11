package tech.fastj.systems.execution;

import tech.fastj.engine.FastJEngine;
import tech.fastj.gameloop.event.EventObserver;

/**
 * Simple observer for {@link FastJEngine#runLater(Runnable) running actions later in FastJ}.
 *
 * @author Andrew Dey
 * @since 1.7.0
 */
public class RunLaterObserver implements EventObserver<RunLaterEvent> {
    @Override
    public void eventReceived(RunLaterEvent event) {
        event.getRunLater().run();
    }
}
