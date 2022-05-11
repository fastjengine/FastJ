package tech.fastj.gameloop.event;

public interface GameEventObserver<T extends GameEvent> {
    void eventReceived(T event);
}
