package unittest.testcases.gameloop;

import tech.fastj.gameloop.CoreLoopState;
import tech.fastj.gameloop.GameLoopState;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class GameLoopStateTests {

    private int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, Math.min(max, Integer.MAX_VALUE - 1) + 1);
    }

    private boolean randomBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    private CoreLoopState randomCoreLoopState() {
        return CoreLoopState.values()[randomInt(0, CoreLoopState.values().length - 1)];
    }

    @Test
    void checkCreateGameLoopState_withoutEnginePriority_shouldMatchExpectedValues() {
        CoreLoopState randomCoreLoopState = randomCoreLoopState();
        int randomPriority = randomInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
        BiConsumer<GameLoopState, Float> stateAction = (gameLoopState, deltaTime) -> {};

        GameLoopState gameLoopState = new GameLoopState(randomCoreLoopState, randomPriority, stateAction);
        assertEquals(randomCoreLoopState, gameLoopState.getCoreLoopState(), "The game loop state's core loop state should match the expected random.");
        assertEquals(GameLoopState.DefaultEnginePriority, gameLoopState.hasEnginePriority(), "The game loop state's engine priority should match the default.");
        assertEquals(randomPriority, gameLoopState.getPriority(), "The game loop state's priority should match the expected random.");
        assertEquals(stateAction, gameLoopState.getLoopStateAction(), "The game loop state action should match the expected action.");
        assertFalse(gameLoopState.isActive(), "The game loop state should not begin active.");
    }

    @Test
    void checkCreateGameLoopState_withEnginePriority_shouldMatchExpectedValues() {
        CoreLoopState randomCoreLoopState = randomCoreLoopState();
        boolean randomEnginePriority = randomBoolean();
        int randomPriority = randomInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
        BiConsumer<GameLoopState, Float> stateAction = (gameLoopState, deltaTime) -> {};

        GameLoopState gameLoopState = new GameLoopState(randomCoreLoopState, randomEnginePriority, randomPriority, stateAction);
        assertEquals(randomCoreLoopState, gameLoopState.getCoreLoopState(), "The game loop state's core loop state should match the expected random.");
        assertEquals(randomEnginePriority, gameLoopState.hasEnginePriority(), "The game loop state's engine priority should match the expected random.");
        assertEquals(randomPriority, gameLoopState.getPriority(), "The game loop state's priority should match the expected random.");
        assertEquals(stateAction, gameLoopState.getLoopStateAction(), "The game loop state action should match the expected action.");
        assertFalse(gameLoopState.isActive(), "The game loop state should not begin active.");
    }
}
