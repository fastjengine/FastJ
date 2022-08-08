package tech.fastj.systems.execution;

import tech.fastj.gameloop.event.Event;

public class RunLaterEvent extends Event {

    private final Runnable runLater;

    public RunLaterEvent(Runnable runLater) {
        this.runLater = runLater;
    }

    public Runnable getRunLater() {
        return runLater;
    }
}
