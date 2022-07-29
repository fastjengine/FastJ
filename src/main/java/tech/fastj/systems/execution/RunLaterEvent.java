package tech.fastj.systems.execution;

import tech.fastj.gameloop.event.GameEvent;

public class RunLaterEvent extends GameEvent {

    private final Runnable runLater;

    public RunLaterEvent(Runnable runLater) {
        this.runLater = runLater;
    }

    public Runnable getRunLater() {
        return runLater;
    }
}
