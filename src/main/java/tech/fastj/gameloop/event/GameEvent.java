package tech.fastj.gameloop.event;

public interface GameEvent {

    boolean isConsumed();

    void consume();
}
