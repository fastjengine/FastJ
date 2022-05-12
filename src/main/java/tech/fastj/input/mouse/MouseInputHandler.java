package tech.fastj.input.mouse;

import tech.fastj.input.mouse.events.MouseActionEvent;

import tech.fastj.gameloop.event.GameEventHandler;
import tech.fastj.gameloop.event.GameEventObserver;

import java.util.List;

public class MouseInputHandler implements GameEventHandler<MouseActionEvent, GameEventObserver<MouseActionEvent>> {

    @Override
    public void handleEvent(List<GameEventObserver<MouseActionEvent>> gameEventObservers, MouseActionEvent mouseActionEvent) {
        for (GameEventObserver<MouseActionEvent> gameEventObserver : gameEventObservers) {
            gameEventObserver.eventReceived(mouseActionEvent);
        }
    }
}
