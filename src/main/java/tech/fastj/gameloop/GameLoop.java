package tech.fastj.gameloop;

import tech.fastj.gameloop.event.GameEvent;
import tech.fastj.gameloop.event.GameEventObserver;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class GameLoop implements Runnable {

    public static final GameLoopState NoState = new GameLoopState(
            CoreLoopState.EarlyUpdate,
            0,
            (gameLoopState, deltaTime) -> {}
    );

    private final Timer deltaTimer;
    private final Timer fixedDeltaTimer;

    private final int targetFPS;
    private final int targetUPS;

    private final Predicate<GameLoop> runCondition;
    private final Predicate<GameLoop> syncCondition;

    private final Map<CoreLoopState, Set<GameLoopState>> gameLoopStates = Map.of(
            CoreLoopState.EarlyUpdate, new TreeSet<>(),
            CoreLoopState.FixedUpdate, new TreeSet<>(),
            CoreLoopState.Update, new TreeSet<>(),
            CoreLoopState.LateUpdate, new TreeSet<>()
    );
    private final Queue<GameLoopState> nextLoopStates;

    private final Map<CoreLoopState, Queue<GameEvent>> nextCoreEvents = Map.of(
            CoreLoopState.EarlyUpdate, new ArrayDeque<>(),
            CoreLoopState.FixedUpdate, new ArrayDeque<>(),
            CoreLoopState.Update, new ArrayDeque<>(),
            CoreLoopState.LateUpdate, new ArrayDeque<>()
    );
    private final Map<GameLoopState, Queue<GameEvent>> nextGameEvents;

    private final Map<Class<? extends GameEvent>, List<GameEventObserver<?>>> gameEventObservers;

    private GameLoopState currentGameLoopState;
    private boolean isRunning;

    public GameLoop(Predicate<GameLoop> shouldRun, Predicate<GameLoop> shouldSync, int fps, int ups) {
        deltaTimer = new Timer();
        fixedDeltaTimer = new Timer();

        this.runCondition = Objects.requireNonNull(shouldRun);
        this.syncCondition = Objects.requireNonNull(shouldSync);

        if (fps < 1) {
            throw new IllegalArgumentException("FPS amount must be at least 1.");
        }
        this.targetFPS = fps;

        if (ups < 1) {
            throw new IllegalArgumentException("UPS amount must be at least 1.");
        }
        this.targetUPS = ups;

        isRunning = false;
        nextLoopStates = new ArrayDeque<>();
        nextGameEvents = new HashMap<>();
        gameEventObservers = new HashMap<>();
        currentGameLoopState = NoState;
    }

    public void addGameLoopStates(GameLoopState firstGameLoopState, GameLoopState... gameLoopStates) {
        if (isRunning) {
            synchronized (nextLoopStates) {
                nextLoopStates.add(firstGameLoopState);
                nextLoopStates.addAll(Arrays.asList(gameLoopStates));
            }
        } else {
            this.gameLoopStates.get(firstGameLoopState.getBaseLoopState()).add(firstGameLoopState);
            for (GameLoopState gameLoopState : gameLoopStates) {
                this.gameLoopStates.get(gameLoopState.getBaseLoopState()).add(gameLoopState);
            }
        }
    }

    public void addGameLoopState(GameLoopState gameLoopState) {
        if (isRunning) {
            synchronized (nextLoopStates) {
                nextLoopStates.add(gameLoopState);
            }
        } else {
            this.gameLoopStates.get(gameLoopState.getBaseLoopState()).add(gameLoopState);
        }
    }

    public <T extends GameEvent> void addEventObserver(GameEventObserver<T> gameEventObserver, Class<T> eventClass) {
        synchronized (gameEventObservers) {
            if (!gameEventObservers.containsKey(eventClass)) {
                gameEventObservers.put(eventClass, new ArrayList<>());
            }
            gameEventObservers.get(eventClass).add(gameEventObserver);
        }
    }

    public <T extends GameEvent> void removeEventObserver(GameEventObserver<T> gameEventObserver, Class<T> eventClass) {
        if (!gameEventObservers.containsKey(eventClass)) {
            return;
        }
        synchronized (gameEventObservers) {
            gameEventObservers.get(eventClass).remove(gameEventObserver);
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public int getTargetFPS() {
        return targetFPS;
    }

    public int getTargetUPS() {
        return targetUPS;
    }

    public Map<CoreLoopState, Set<GameLoopState>> getGameLoopStates() {
        return gameLoopStates;
    }

    public GameLoopState getCurrentGameLoopState() {
        return currentGameLoopState;
    }

    @SuppressWarnings("unchecked")
    public <T extends GameEvent> void fireEvent(T event) {
        for (var gameEventObserver : gameEventObservers.get(event.getClass())) {
            ((GameEventObserver<T>) gameEventObserver).eventReceived(event);
        }
    }

    public <T extends GameEvent> void fireEvent(T event, GameLoopState whenToFire) {
        synchronized (nextGameEvents) {
            nextGameEvents.get(whenToFire).add(event);
        }
    }

    public <T extends GameEvent> void fireEvent(T event, CoreLoopState whenToFire) {
        synchronized (nextCoreEvents) {
            nextCoreEvents.get(whenToFire).add(event);
        }
    }

    @Override
    public synchronized void run() {
        isRunning = true;

        float elapsedTime;
        float elapsedFixedTime;
        float accumulator = 0f;
        float updateInterval = 1f / targetUPS;

        while (runCondition.test(this)) {
            elapsedTime = deltaTimer.evalDeltaTime();
            accumulator += elapsedTime;

            if (!nextLoopStates.isEmpty()) {
                synchronized (nextLoopStates) {
                    for (GameLoopState nextLoopState : nextLoopStates) {
                        gameLoopStates.get(nextLoopState.getBaseLoopState()).add(nextLoopState);
                        synchronized (nextGameEvents) {
                            nextGameEvents.computeIfAbsent(nextLoopState, gameLoopState -> new ArrayDeque<>());
                        }
                    }
                    nextLoopStates.clear();
                }
            }

            runGameLoopStates(CoreLoopState.EarlyUpdate, elapsedTime);
            fireNextCoreEvents(CoreLoopState.EarlyUpdate);

            while (accumulator >= updateInterval) {
                elapsedFixedTime = fixedDeltaTimer.evalDeltaTime();

                runGameLoopStates(CoreLoopState.FixedUpdate, elapsedFixedTime);
                fireNextCoreEvents(CoreLoopState.FixedUpdate);

                accumulator -= elapsedFixedTime;
            }


            runGameLoopStates(CoreLoopState.Update, elapsedTime);
            fireNextCoreEvents(CoreLoopState.Update);

            runGameLoopStates(CoreLoopState.LateUpdate, elapsedTime);
            fireNextCoreEvents(CoreLoopState.LateUpdate);

            currentGameLoopState = NoState;

            if (syncCondition.test(this)) {
                sync();
            }
        }

        isRunning = false;
    }

    private void runGameLoopStates(CoreLoopState coreLoopState, float elapsedFixedTime) {
        for (GameLoopState gameLoopState : gameLoopStates.get(coreLoopState)) {
            System.out.println("running " + gameLoopState.toString() + " of " + coreLoopState);
            currentGameLoopState = gameLoopState;
            gameLoopState.accept(elapsedFixedTime);
        }
    }

    @SuppressWarnings("unchecked")
    private void fireNextCoreEvents(CoreLoopState coreLoopState) {
        synchronized (nextCoreEvents) {
            Queue<? extends GameEvent> gameEvents = nextCoreEvents.get(coreLoopState);
            while (!gameEvents.isEmpty()) {
                GameEvent nextEvent = gameEvents.poll();
                for (var gameEventObserver : gameEventObservers.get(nextEvent.getClass())) {
                    ((GameEventObserver<GameEvent>) gameEventObserver).eventReceived(nextEvent);
                }
            }
        }
    }

    private void sync() {
        final float loopSlot = 1f / targetFPS;
        final double endTime = deltaTimer.getLastTimestamp() + loopSlot;
        final double currentTime = deltaTimer.getCurrentTime();
        if (currentTime < endTime) {
            try {
                TimeUnit.MILLISECONDS.sleep((long) ((endTime - currentTime) * 1000L));
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
