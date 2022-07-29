package tech.fastj.systems.execution;

import tech.fastj.gameloop.event.GameEventObserver;

public class RunLaterObserver implements GameEventObserver<RunLaterEvent> {
    @Override
    public void eventReceived(RunLaterEvent event) {
        event.getRunLater().run();
    }
}
