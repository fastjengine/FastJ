package unittest.testcases.gameloop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;
import tech.fastj.gameloop.CoreLoopState;
import tech.fastj.gameloop.GameLoop;
import tech.fastj.gameloop.GameLoopState;
import tech.fastj.gameloop.event.GameEventHandler;
import tech.fastj.gameloop.event.GameEventObserver;
import unittest.mock.gameloop.event.MockEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameLoopTests {

    private static final int FPS = 30;
    private static final int UPS = 30;

    @Test
    void checkGameLoopCreation_shouldSetAllParametersAsExpected() {
        GameLoop gameLoop = new GameLoop((gl) -> false, (gl) -> false, FPS, UPS);

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
        assertThrows(NullPointerException.class, () -> new GameLoop(null, (gl) -> false, FPS, UPS));
    }

    @Test
    void tryGameLoopCreation_withNullSyncCondition() {
        assertThrows(NullPointerException.class, () -> new GameLoop((gl) -> false, null, FPS, UPS));
    }

    @Test
    void tryGameLoopCreation_withFPSLessThanOne() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new GameLoop((gl) -> false, (gl) -> false, 0, UPS));
        String expectedExceptionMessage = "FPS amount must be at least 1.";
        assertEquals(expectedExceptionMessage, exception.getMessage(), "The exception message should match the expected exception message.");
    }

    @Test
    void tryGameLoopCreation_withUPSLessThanOne() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new GameLoop((gl) -> false, (gl) -> false, FPS, 0));
        String expectedExceptionMessage = "UPS amount must be at least 1.";
        assertEquals(expectedExceptionMessage, exception.getMessage(), "The exception message should match the expected exception message.");
    }

    @Test
    void checkGameLoopStateWhileRunning_onAGameLoopState() {
        AtomicBoolean shouldRemainOpen = new AtomicBoolean(true);
        GameLoop gameLoop = new GameLoop((gl) -> shouldRemainOpen.get(), (gl) -> false, FPS, UPS);

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
    }

    @Test
    void checkGameLoopRemovesEventObservers_shouldNotFail() {
        AtomicBoolean shouldRemainOpen = new AtomicBoolean(true);
        AtomicBoolean firedEvent = new AtomicBoolean();
        GameLoop gameLoop = new GameLoop((gl) -> shouldRemainOpen.get(), (gl) -> false, FPS, UPS);

        GameEventObserver<MockEvent> gameEventObserver = (event) -> firedEvent.set(true);
        gameLoop.addEventObserver(gameEventObserver, MockEvent.class);
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
        GameLoop gameLoop = new GameLoop((gl) -> shouldRemainOpen.get(), (gl) -> false, FPS, UPS);

        GameEventHandler<MockEvent, GameEventObserver<MockEvent>> gameEventHandler = (eventObservers, event) -> firedEvent.set(true);
        gameLoop.addEventHandler(gameEventHandler, MockEvent.class);
        gameLoop.removeEventHandler(MockEvent.class);

        gameLoop.addGameLoopState(new GameLoopState(CoreLoopState.Update, 1, (gl, deltaTime) -> gameLoop.fireEvent(new MockEvent())));
        gameLoop.addGameLoopState(new GameLoopState(CoreLoopState.LateUpdate, 1, (gl, deltaTime) -> shouldRemainOpen.set(false)));

        gameLoop.run();
        assertFalse(firedEvent.get(), "The event should have been fired and received immediately.");
    }

    @Test
    void checkGameLoopRunsAllGameLoopStates_inOrder_shouldNotFail() {
        AtomicBoolean shouldRemainOpen = new AtomicBoolean(true);
        List<GameLoopState> gameLoopStatesRun = new ArrayList<>();
        GameLoop gameLoop = new GameLoop((gl) -> shouldRemainOpen.get(), (gl) -> false, FPS, UPS);

        GameLoopState[] earlyUpdates = new GameLoopState[50];
        for (int i = 0; i < earlyUpdates.length; i++) {
            earlyUpdates[i] = new GameLoopState(CoreLoopState.EarlyUpdate, i, (gl, deltaTime) -> gameLoopStatesRun.add(gl));
        }
        GameLoopState[] fixedUpdates = new GameLoopState[50];
        for (int i = 0; i < fixedUpdates.length; i++) {
            fixedUpdates[i] = new GameLoopState(CoreLoopState.FixedUpdate, i, (gl, deltaTime) -> gameLoopStatesRun.add(gl));
        }
        GameLoopState[] updates = new GameLoopState[50];
        for (int i = 0; i < updates.length; i++) {
            updates[i] = new GameLoopState(CoreLoopState.Update, i, (gl, deltaTime) -> gameLoopStatesRun.add(gl));
        }
        GameLoopState[] lateUpdates = new GameLoopState[50];
        for (int i = 0; i < lateUpdates.length; i++) {
            lateUpdates[i] = new GameLoopState(CoreLoopState.LateUpdate, i, (gl, deltaTime) -> gameLoopStatesRun.add(gl));
        }
        gameLoop.addGameLoopStates(earlyUpdates);
        gameLoop.addGameLoopStates(fixedUpdates);
        gameLoop.addGameLoopStates(updates);
        gameLoop.addGameLoopStates(lateUpdates);
        gameLoop.addGameLoopState(new GameLoopState(CoreLoopState.LateUpdate, Integer.MAX_VALUE, (gl, deltaTime) -> shouldRemainOpen.set(false)));

        gameLoop.run();

        List<GameLoopState> sortedLoopStatesRun = new ArrayList<>(new TreeSet<>(gameLoopStatesRun));
        assertEquals(sortedLoopStatesRun, gameLoopStatesRun, "The game loop states should have been run in order.");
    }

    @Test
    void checkGameLoopFiresEventsImmediately_shouldNotFail() {
        AtomicBoolean shouldRemainOpen = new AtomicBoolean(true);
        AtomicBoolean firedEvent = new AtomicBoolean();
        GameLoop gameLoop = new GameLoop((gl) -> shouldRemainOpen.get(), (gl) -> false, FPS, UPS);

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
        GameLoop gameLoop = new GameLoop((gl) -> shouldRemainOpen.get(), (gl) -> false, FPS, UPS);

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
        GameLoop gameLoop = new GameLoop((gl) -> shouldRemainOpen.get(), (gl) -> false, FPS, UPS);

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
        GameLoop gameLoop = new GameLoop((gl) -> shouldRemainOpen.get(), (gl) -> false, FPS, UPS);

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
        GameLoop gameLoop = new GameLoop((gl) -> shouldRemainOpen.get(), (gl) -> false, FPS, UPS);

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
        GameLoop gameLoop = new GameLoop((gl) -> shouldRemainOpen.get(), (gl) -> false, FPS, UPS);

        gameLoop.addEventHandler((eventObservers, event) -> firedEvent.set(true), MockEvent.class);

        GameLoopState lateUpdateState = new GameLoopState(CoreLoopState.LateUpdate, 1, (gl, deltaTime) -> shouldRemainOpen.set(false));
        GameLoopState updateState = new GameLoopState(CoreLoopState.Update, 1, (gl, deltaTime) -> gameLoop.fireEvent(new MockEvent(), lateUpdateState));
        gameLoop.addGameLoopStates(updateState, lateUpdateState);

        gameLoop.run();
        assertTrue(firedEvent.get(), "The event should have been fired and received.");
    }
}
