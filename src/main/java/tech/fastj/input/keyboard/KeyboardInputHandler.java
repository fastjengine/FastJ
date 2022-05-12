package tech.fastj.input.keyboard;

import tech.fastj.input.keyboard.events.KeyboardActionEvent;

import tech.fastj.gameloop.event.GameEventHandler;
import tech.fastj.gameloop.event.GameEventObserver;

import java.util.List;

public class KeyboardInputHandler implements GameEventHandler<KeyboardActionEvent, GameEventObserver<KeyboardActionEvent>> {

    @Override
    public void handleEvent(List<GameEventObserver<KeyboardActionEvent>> gameEventObservers, KeyboardActionEvent gameEvent) {
        for (GameEventObserver<KeyboardActionEvent> gameEventObserver : gameEventObservers) {
            gameEventObserver.eventReceived(gameEvent);
        }
    }
}
