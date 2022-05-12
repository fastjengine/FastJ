package tech.fastj.gameloop.event;

public class GameEvent {

    private boolean isConsumed = false;

    public boolean isConsumed() {
        return isConsumed;
    }

    public void consume() {
        isConsumed = true;
    }
}
