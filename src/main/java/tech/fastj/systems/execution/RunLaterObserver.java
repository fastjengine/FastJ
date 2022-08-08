package tech.fastj.systems.execution;

import tech.fastj.gameloop.event.EventObserver;

public class RunLaterObserver implements EventObserver<RunLaterEvent> {
    @Override
    public void eventReceived(RunLaterEvent event) {
        event.getRunLater().run();
    }
}
