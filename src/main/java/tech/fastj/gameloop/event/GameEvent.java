package tech.fastj.gameloop.event;

public class GameEvent {

    private boolean isConsumed = false;
    private final long timestamp = System.nanoTime();

    public boolean isConsumed() {
        return isConsumed;
    }

    public void consume() {
        isConsumed = true;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
