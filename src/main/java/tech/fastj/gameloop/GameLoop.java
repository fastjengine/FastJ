package tech.fastj.gameloop;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import tech.fastj.gameloop.event.GameEvent;
import tech.fastj.gameloop.event.GameEventObserver;

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

    private final Predicate<GameLoop> exitCondition;
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

    private final Map<Class<? extends GameEvent>, List<GameEventObserver<GameEvent>>> gameEventObservers;

    private GameLoopState currentGameLoopState;
    private boolean isRunning;

    public GameLoop(Predicate<GameLoop> exit, Predicate<GameLoop> sync, int fps, int ups) {
        deltaTimer = new Timer();
        fixedDeltaTimer = new Timer();

        this.exitCondition = Objects.requireNonNull(exit);
        this.syncCondition = Objects.requireNonNull(sync);

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

    public void addGameLoopState(GameLoopState... gameLoopStates) {
        if (isRunning) {
            synchronized (nextLoopStates) {
                nextLoopStates.addAll(Arrays.asList(gameLoopStates));
            }
        } else {
            for (GameLoopState gameLoopState : gameLoopStates) {
                this.gameLoopStates.get(gameLoopState.getBaseLoopState()).add(gameLoopState);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends GameEvent> void addEventObserver(GameEventObserver<T> gameEventObserver, Class<T> eventClass) {
        synchronized (gameEventObservers) {
            if (!gameEventObservers.containsKey(eventClass)) {
                gameEventObservers.put(eventClass, new ArrayList<>());
            }
            gameEventObservers.get(eventClass).add((GameEventObserver<GameEvent>) gameEventObserver);
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

    public <T extends GameEvent> void fireEvent(T event) {
        for (var gameEventObserver : gameEventObservers.get(event.getClass())) {
            gameEventObserver.eventReceived(event);
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

        while (exitCondition.test(this)) {
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

    private void runGameLoopStates(CoreLoopState fixedUpdate, float elapsedFixedTime) {
        for (GameLoopState gameLoopState : gameLoopStates.get(fixedUpdate)) {
            currentGameLoopState = gameLoopState;
            gameLoopState.accept(elapsedFixedTime);
        }
    }

    private void fireNextCoreEvents(CoreLoopState coreLoopState) {
        synchronized (nextCoreEvents) {
            Queue<GameEvent> gameEvents = nextCoreEvents.get(coreLoopState);
            while (!gameEvents.isEmpty()) {
                GameEvent nextEvent = gameEvents.poll();
                for (var gameEventObserver : gameEventObservers.get(nextEvent.getClass())) {
                    gameEventObserver.eventReceived(nextEvent);
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
