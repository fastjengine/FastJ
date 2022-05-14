package unittest.testcases.gameloop;

import unittest.mock.gameloop.event.MockEvent;
import tech.fastj.gameloop.CoreLoopState;
import tech.fastj.gameloop.GameLoop;
import tech.fastj.gameloop.GameLoopState;
import tech.fastj.gameloop.event.GameEventHandler;
import tech.fastj.gameloop.event.GameEventObserver;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameLoopTests {

    private static final int FPS = 30;
    private static final int UPS = 30;

    @Test
    void checkGameLoopCreation_shouldSetAllParametersAsExpected() {
        GameLoop gameLoop = new GameLoop((gl) -> false, (gl) -> false);
        gameLoop.setTargetFPS(FPS);
        gameLoop.setTargetUPS(UPS);

        assertFalse(gameLoop.isRunning(), "The game loop should begin in a not-running state.");
        assertEquals(FPS, gameLoop.getTargetFPS(), "The game loop's target FPS should match the expected value.");
        assertEquals(UPS, gameLoop.getTargetUPS(), "The game loop's target UPS should match the expected value.");
        assertEquals(GameLoop.NoState, gameLoop.getCurrentGameLoopState(), "The beginning game loop state should be no state.");

        Map<CoreLoopState, Set<GameLoopState>> gameLoopStatesMap = gameLoop.getGameLoopStates();
        assertEquals(CoreLoopState.values().length, gameLoopStatesMap.size());
        for (Set<GameLoopState> gameLoopStates : gameLoopStatesMap.values()) {
            assertTrue(gameLoopStates.isEmpty(), "The game loop states set should start empty.");
        }
    }

    @Test
    void tryGameLoopCreation_withNullExitCondition() {
        assertThrows(NullPointerException.class, () -> new GameLoop(null, (gl) -> false));
    }

    @Test
    void tryGameLoopCreation_withNullSyncCondition() {
        assertThrows(NullPointerException.class, () -> new GameLoop((gl) -> false, null));
    }

    @Test
    void tryGameLoopCreation_withFPSLessThanOne() {
        GameLoop gameLoop = new GameLoop((gl) -> false, (gl) -> false);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> gameLoop.setTargetFPS(0));
        String expectedExceptionMessage = "FPS amount must be at least 1.";
        assertEquals(expectedExceptionMessage, exception.getMessage(), "The exception message should match the expected exception message.");
    }

    @Test
    void tryGameLoopCreation_withUPSLessThanOne() {
        GameLoop gameLoop = new GameLoop((gl) -> false, (gl) -> false);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> gameLoop.setTargetUPS(0));
        String expectedExceptionMessage = "UPS amount must be at least 1.";
        assertEquals(expectedExceptionMessage, exception.getMessage(), "The exception message should match the expected exception message.");
    }

    @Test
    void checkGameLoopStateWhileRunning_onAGameLoopState() {
        AtomicBoolean shouldRemainOpen = new AtomicBoolean(true);
        GameLoop gameLoop = new GameLoop((gl) -> shouldRemainOpen.get(), (gl) -> false);

        AtomicReference<GameLoopState> expectedCurrentGameLoopState = new AtomicReference<>();
        expectedCurrentGameLoopState.set(new GameLoopState(
                CoreLoopState.Update,
                1,
                (gl, deltaTime) -> {
                    assertEquals(expectedCurrentGameLoopState.get(), gameLoop.getCurrentGameLoopState(), "The expected current game loop should match the expected game loop.");
                    assertTrue(gameLoop.isRunning(), "The game loop should be running while in a game loop state.");
                }
        ));

        gameLoop.addGameLoopState(expectedCurrentGameLoopState.get());
        gameLoop.addGameLoopState(new GameLoopState(CoreLoopState.LateUpdate, 1, (gl, deltaTime) -> shouldRemainOpen.set(false)));
        gameLoop.run();
        assertEquals(GameLoop.NoState, gameLoop.getCurrentGameLoopState(), "After running, the current game loop state should match the default NoState.");
    }

    @Test
    void checkGameLoopRemovesEventObservers_shouldNotFail() {
        AtomicBoolean shouldRemainOpen = new AtomicBoolean(true);
        AtomicBoolean firedEvent = new AtomicBoolean();
        GameLoop gameLoop = new GameLoop((gl) -> shouldRemainOpen.get(), (gl) -> false);

        GameEventObserver<MockEvent> gameEventObserver = (event) -> firedEvent.set(true);
        gameLoop.addEventObserver(gameEventObserver, MockEvent.class);
        assertEquals(gameEventObserver, gameLoop.getGameEventObservers(MockEvent.class).get(0));
        assertEquals(1, gameLoop.getGameEventObservers(MockEvent.class).size());
        gameLoop.removeEventObserver(gameEventObserver, MockEvent.class);

        gameLoop.addGameLoopState(new GameLoopState(CoreLoopState.Update, 1, (gl, deltaTime) -> gameLoop.fireEvent(new MockEvent())));
        gameLoop.addGameLoopState(new GameLoopState(CoreLoopState.LateUpdate, 1, (gl, deltaTime) -> shouldRemainOpen.set(false)));

        gameLoop.run();
        assertFalse(firedEvent.get(), "The event should have been fired and received immediately.");
    }

    @Test
    void checkGameLoopRemovesEventHandlers_shouldNotFail() {
        AtomicBoolean shouldRemainOpen = new AtomicBoolean(true);
        AtomicBoolean firedEvent = new AtomicBoolean();
        GameLoop gameLoop = new GameLoop((gl) -> shouldRemainOpen.get(), (gl) -> false);

        GameEventHandler<MockEvent, GameEventObserver<MockEvent>> gameEventHandler = (eventObservers, event) -> firedEvent.set(true);
        gameLoop.addEventHandler(gameEventHandler, MockEvent.class);
        assertEquals(gameEventHandler, gameLoop.getGameEventHandler(MockEvent.class));
        gameLoop.removeEventHandler(MockEvent.class);

        gameLoop.addGameLoopState(new GameLoopState(CoreLoopState.Update, 1, (gl, deltaTime) -> gameLoop.fireEvent(new MockEvent())));
        gameLoop.addGameLoopState(new GameLoopState(CoreLoopState.LateUpdate, 1, (gl, deltaTime) -> shouldRemainOpen.set(false)));

        gameLoop.run();
        assertFalse(firedEvent.get(), "The event should have been fired and received immediately.");
    }

    @Test
    void checkGameLoopFiresEventsImmediately_shouldNotFail() {
        AtomicBoolean shouldRemainOpen = new AtomicBoolean(true);
        AtomicBoolean firedEvent = new AtomicBoolean();
        GameLoop gameLoop = new GameLoop((gl) -> shouldRemainOpen.get(), (gl) -> false);

        gameLoop.addEventObserver((event) -> firedEvent.set(true), MockEvent.class);
        gameLoop.addGameLoopStates(
                new GameLoopState(CoreLoopState.Update, 1, (gl, deltaTime) -> gameLoop.fireEvent(new MockEvent())),
                new GameLoopState(CoreLoopState.LateUpdate, 1, (gl, deltaTime) -> shouldRemainOpen.set(false))
        );

        gameLoop.run();
        assertTrue(firedEvent.get(), "The event should have been fired and received immediately.");
    }

    @Test
    void checkGameLoopFiresEventsOnCoreLoopState_shouldNotFail() {
        AtomicBoolean shouldRemainOpen = new AtomicBoolean(true);
        AtomicBoolean firedEvent = new AtomicBoolean();
        GameLoop gameLoop = new GameLoop((gl) -> shouldRemainOpen.get(), (gl) -> false);

        gameLoop.addEventObserver((event) -> firedEvent.set(true), MockEvent.class);
        gameLoop.addGameLoopStates(
                new GameLoopState(CoreLoopState.LateUpdate, 1, (gl, deltaTime) -> shouldRemainOpen.set(false)),
                new GameLoopState(CoreLoopState.Update, 1, (gl, deltaTime) -> gameLoop.fireEvent(new MockEvent(), CoreLoopState.LateUpdate))
        );

        gameLoop.run();
        assertTrue(firedEvent.get(), "The event should have been fired and received immediately.");
    }

    @Test
    void checkGameLoopFiresEventsOnGameLoopState_shouldNotFail() {
        AtomicBoolean shouldRemainOpen = new AtomicBoolean(true);
        AtomicBoolean firedEvent = new AtomicBoolean();
        GameLoop gameLoop = new GameLoop((gl) -> shouldRemainOpen.get(), (gl) -> false);

        gameLoop.addEventObserver((event) -> firedEvent.set(true), MockEvent.class);

        GameLoopState lateUpdateState = new GameLoopState(CoreLoopState.LateUpdate, 1, (gl, deltaTime) -> shouldRemainOpen.set(false));
        GameLoopState updateState = new GameLoopState(CoreLoopState.Update, 1, (gl, deltaTime) -> gameLoop.fireEvent(new MockEvent(), lateUpdateState));
        gameLoop.addGameLoopStates(updateState, lateUpdateState);

        gameLoop.run();
        assertTrue(firedEvent.get(), "The event should have been fired and received.");
    }

    @Test
    void checkGameLoopFiresEventsImmediately_onEventHandler_shouldNotFail() {
        AtomicBoolean shouldRemainOpen = new AtomicBoolean(true);
        AtomicBoolean firedEvent = new AtomicBoolean();
        GameLoop gameLoop = new GameLoop((gl) -> shouldRemainOpen.get(), (gl) -> false);

        gameLoop.addEventHandler((eventObservers, event) -> firedEvent.set(true), MockEvent.class);
        gameLoop.addGameLoopStates(
                new GameLoopState(CoreLoopState.Update, 1, (gl, deltaTime) -> gameLoop.fireEvent(new MockEvent())),
                new GameLoopState(CoreLoopState.LateUpdate, 1, (gl, deltaTime) -> shouldRemainOpen.set(false))
        );

        gameLoop.run();
        assertTrue(firedEvent.get(), "The event should have been fired and received immediately.");
    }

    @Test
    void checkGameLoopFiresEventsOnCoreLoopState_onEventHandler_shouldNotFail() {
        AtomicBoolean shouldRemainOpen = new AtomicBoolean(true);
        AtomicBoolean firedEvent = new AtomicBoolean();
        GameLoop gameLoop = new GameLoop((gl) -> shouldRemainOpen.get(), (gl) -> false);

        gameLoop.addEventHandler((eventObservers, event) -> firedEvent.set(true), MockEvent.class);
        gameLoop.addGameLoopStates(
                new GameLoopState(CoreLoopState.LateUpdate, 1, (gl, deltaTime) -> shouldRemainOpen.set(false)),
                new GameLoopState(CoreLoopState.Update, 1, (gl, deltaTime) -> gameLoop.fireEvent(new MockEvent(), CoreLoopState.LateUpdate))
        );

        gameLoop.run();
        assertTrue(firedEvent.get(), "The event should have been fired and received immediately.");
    }

    @Test
    void checkGameLoopFiresEventsOnGameLoopState_onEventHandler_shouldNotFail() {
        AtomicBoolean shouldRemainOpen = new AtomicBoolean(true);
        AtomicBoolean firedEvent = new AtomicBoolean();
        GameLoop gameLoop = new GameLoop((gl) -> shouldRemainOpen.get(), (gl) -> false);

        gameLoop.addEventHandler((eventObservers, event) -> firedEvent.set(true), MockEvent.class);

        GameLoopState lateUpdateState = new GameLoopState(CoreLoopState.LateUpdate, 1, (gl, deltaTime) -> shouldRemainOpen.set(false));
        GameLoopState updateState = new GameLoopState(CoreLoopState.Update, 1, (gl, deltaTime) -> gameLoop.fireEvent(new MockEvent(), lateUpdateState));
        gameLoop.addGameLoopStates(updateState, lateUpdateState);

        gameLoop.run();
        assertTrue(firedEvent.get(), "The event should have been fired and received.");
    }

    @Test
    void checkGameLoopResetsAllValues() {
        AtomicBoolean shouldRemainOpen = new AtomicBoolean(true);
        GameLoop gameLoop = new GameLoop((gl) -> shouldRemainOpen.get(), (gl) -> false);

        GameLoopState[] earlyUpdates = new GameLoopState[50];
        for (int i = 0; i < earlyUpdates.length; i++) {
            earlyUpdates[i] = new GameLoopState(CoreLoopState.EarlyUpdate, i, (gl, deltaTime) -> {});
        }
        GameLoopState[] fixedUpdates = new GameLoopState[50];
        for (int i = 0; i < fixedUpdates.length; i++) {
            fixedUpdates[i] = new GameLoopState(CoreLoopState.FixedUpdate, i, (gl, deltaTime) -> {});
        }
        GameLoopState[] updates = new GameLoopState[50];
        for (int i = 0; i < updates.length; i++) {
            updates[i] = new GameLoopState(CoreLoopState.Update, i, (gl, deltaTime) -> {});
        }
        GameLoopState[] lateUpdates = new GameLoopState[50];
        for (int i = 0; i < lateUpdates.length; i++) {
            lateUpdates[i] = new GameLoopState(CoreLoopState.LateUpdate, i, (gl, deltaTime) -> {});
        }
        gameLoop.addGameLoopStates(earlyUpdates);
        gameLoop.addGameLoopStates(fixedUpdates);
        gameLoop.addGameLoopStates(updates);
        gameLoop.addGameLoopStates(lateUpdates);
        gameLoop.addGameLoopState(new GameLoopState(CoreLoopState.LateUpdate, Integer.MAX_VALUE, (gl, deltaTime) -> shouldRemainOpen.set(false)));

        gameLoop.addEventHandler((eventObservers, event) -> {}, MockEvent.class);
        gameLoop.addEventObserver(event -> {}, MockEvent.class);

        gameLoop.reset();

        assertEquals(0, gameLoop.getGameEventObservers(MockEvent.class).size(), "After resetting, there should be no event observers.");
        assertNull(gameLoop.getGameEventHandler(MockEvent.class), "After resetting, there should be no event handler.");
        for (Set<GameLoopState> gameLoopStates : gameLoop.getGameLoopStates().values()) {
            assertEquals(0, gameLoopStates.size(), "After resetting, there should be no game loop states.");
        }

        assertEquals(GameLoop.DefaultFPS, gameLoop.getTargetFPS(), "After resetting, the target fps should match the expected FPS.");
        assertEquals(GameLoop.DefaultUPS, gameLoop.getTargetUPS(), "After resetting, the target ups should match the expected UPS.");
        assertEquals(GameLoop.NoState, gameLoop.getCurrentGameLoopState(), "After resetting, the current game loop state should match the default NoState.");
    }

    @Test
    void checkGameLoopClearsAllValues() {
        AtomicBoolean shouldRemainOpen = new AtomicBoolean(true);
        GameLoop gameLoop = new GameLoop((gl) -> shouldRemainOpen.get(), (gl) -> false);

        gameLoop.addEventHandler((eventObservers, event) -> {}, MockEvent.class);
        gameLoop.addEventObserver(event -> {}, MockEvent.class);

        gameLoop.clear();

        assertEquals(0, gameLoop.getGameEventObservers(MockEvent.class).size(), "After clearing, there should be no event observers.");
        assertNull(gameLoop.getGameEventHandler(MockEvent.class), "After clearing, there should be no event handler.");
    }
}
