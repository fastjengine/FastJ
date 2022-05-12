package unittest.mock.gameloop.event;

import tech.fastj.gameloop.event.GameEvent;

public class MockEvent implements GameEvent {

    private boolean isConsumed = false;

    @Override
    public boolean isConsumed() {
        return isConsumed;
    }

    @Override
    public void consume() {
        isConsumed = true;
    }
}
