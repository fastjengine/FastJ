package tech.fastj.gameloop;

import tech.fastj.gameloop.event.GameEvent;
import tech.fastj.gameloop.event.GameEventHandler;
import tech.fastj.gameloop.event.GameEventObserver;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class GameLoop implements Runnable {

    public static final int DefaultFPS = 60;
    public static final int DefaultUPS = 30;

    public static final GameLoopState NoState = new GameLoopState(
            CoreLoopState.EarlyUpdate,
            0,
            (gameLoopState, deltaTime) -> {}
    );

    private final Timer deltaTimer;
    private final Timer fixedDeltaTimer;

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

    private final Map<Class<? extends GameEvent>, List<GameEventObserver<? extends GameEvent>>> gameEventObservers;
    private final Map<Class<? extends GameEvent>, GameEventHandler<? extends GameEvent, ? extends GameEventObserver<? extends GameEvent>>> gameEventHandlers;
    private final Map<Class<? extends GameEvent>, Class<? extends GameEvent>> classAliases;

    private GameLoopState currentGameLoopState;
    private volatile boolean isRunning;

    private final AtomicReference<Float> fixedUpdateInterval;
    private int targetFPS;
    private int targetUPS;

    public GameLoop(Predicate<GameLoop> shouldRun, Predicate<GameLoop> shouldSync) {
        deltaTimer = new Timer();
        fixedDeltaTimer = new Timer();

        this.runCondition = Objects.requireNonNull(shouldRun);
        this.syncCondition = Objects.requireNonNull(shouldSync);

        isRunning = false;
        nextLoopStates = new ArrayDeque<>();
        nextGameEvents = new HashMap<>();
        gameEventObservers = new HashMap<>();
        gameEventHandlers = new HashMap<>();
        classAliases = new HashMap<>();
        currentGameLoopState = NoState;

        fixedUpdateInterval = new AtomicReference<>();
        setTargetFPS(DefaultFPS);
        setTargetUPS(DefaultUPS);
    }

    public void addGameLoopStates(GameLoopState... gameLoopStates) {
        if (isRunning) {
            synchronized (nextLoopStates) {
                nextLoopStates.addAll(Arrays.asList(gameLoopStates));
            }
        } else {
            for (GameLoopState gameLoopState : gameLoopStates) {
                this.gameLoopStates.get(gameLoopState.getCoreLoopState()).add(gameLoopState);
            }
        }
    }

    public void addGameLoopState(GameLoopState gameLoopState) {
        if (isRunning) {
            synchronized (nextLoopStates) {
                nextLoopStates.add(gameLoopState);
            }
        } else {
            this.gameLoopStates.get(gameLoopState.getCoreLoopState()).add(gameLoopState);
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

    public <T extends GameEvent, V extends GameEventHandler<T, GameEventObserver<T>>> void addEventHandler(V gameEventHandler, Class<T> eventClass) {
        synchronized (gameEventHandlers) {
            gameEventHandlers.put(eventClass, gameEventHandler);
        }
    }

    public <T extends GameEvent> void removeEventHandler(Class<T> eventClass) {
        synchronized (gameEventHandlers) {
            gameEventHandlers.remove(eventClass);
        }
    }

    public <S extends GameEvent, T extends S> void addClassAlias(Class<T> originalClass, Class<S> aliasedClass) {
        classAliases.put(originalClass, aliasedClass);
    }

    public <S extends GameEvent, T extends S> void removeClassAlias(Class<T> originalClass) {
        classAliases.remove(originalClass);
    }

    public <T extends GameEvent> Class<? extends GameEvent> getClassAlias(Class<T> originalClass) {
        return classAliases.get(originalClass);
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

    public void setTargetFPS(int fps) {
        if (!isRunning) {
            if (fps < 1) {
                throw new IllegalArgumentException("FPS amount must be at least 1.");
            }
            this.targetFPS = fps;
        }
    }

    public void setTargetUPS(int ups) {
        if (!isRunning) {
            if (ups < 1) {
                throw new IllegalArgumentException("UPS amount must be at least 1.");
            }
            this.targetUPS = ups;
            synchronized (fixedUpdateInterval) {
                fixedUpdateInterval.set(1f / targetUPS);
            }
        }
    }

    public <T extends GameEvent> List<GameEventObserver<? extends GameEvent>> getGameEventObservers(Class<T> eventClass) {
        return gameEventObservers.getOrDefault(eventClass, List.of());
    }

    @SuppressWarnings("unchecked")
    public <T extends GameEvent, V extends GameEventObserver<T>> GameEventHandler<T, V> getGameEventHandler(Class<T> eventClass) {
        return (GameEventHandler<T, V>) gameEventHandlers.get(eventClass);
    }

    public Map<CoreLoopState, Set<GameLoopState>> getGameLoopStates() {
        return gameLoopStates;
    }

    public Set<GameLoopState> getGameLoopStatesOrdered() {
        TreeSet<GameLoopState> result = new TreeSet<>();
        result.addAll(gameLoopStates.get(CoreLoopState.EarlyUpdate));
        result.addAll(gameLoopStates.get(CoreLoopState.FixedUpdate));
        result.addAll(gameLoopStates.get(CoreLoopState.Update));
        result.addAll(gameLoopStates.get(CoreLoopState.LateUpdate));
        return result;
    }

    public GameLoopState getCurrentGameLoopState() {
        return currentGameLoopState;
    }

    @SuppressWarnings("unchecked")
    public <T extends GameEvent> void fireEvent(T event) {
        Class<T> eventClass = (Class<T>) event.getClass();
        tryFireEvent(event, eventClass);


        Class<GameEvent> classAlias = (Class<GameEvent>) classAliases.get(eventClass);
        if (classAlias != null) {
            tryFireEvent(event, classAlias);
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends GameEvent> void tryFireEvent(T event, Class<T> eventClass) {
        var gameEventHandler = (GameEventHandler<T, GameEventObserver<T>>) gameEventHandlers.get(eventClass);
        if (gameEventHandler != null) {
            ((GameEventHandler) gameEventHandler).handleEvent(gameEventObservers.get(eventClass), event);
            return;
        }

        List<GameEventObserver<? extends GameEvent>> gameEventObserverList = gameEventObservers.get(eventClass);
        if (gameEventObserverList == null) {
            gameEventObservers.put(eventClass, new ArrayList<>());
            gameEventObserverList = gameEventObservers.get(eventClass);
        }

        if (gameEventObservers.get(eventClass).isEmpty()) {
            return;
        }

        for (var gameEventObserver : gameEventObserverList) {
            ((GameEventObserver<T>) gameEventObserver).eventReceived(event);
        }
    }

    public <T extends GameEvent> void fireEvent(T event, GameLoopState whenToFire) {
        synchronized (nextGameEvents) {
            if (nextGameEvents.get(whenToFire) == null) {
                nextGameEvents.put(whenToFire, new ArrayDeque<>());
            }
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

        while (runCondition.test(this)) {
            elapsedTime = deltaTimer.evalDeltaTime();
            accumulator += elapsedTime;

            if (!nextLoopStates.isEmpty()) {
                synchronized (nextLoopStates) {
                    for (GameLoopState nextLoopState : nextLoopStates) {
                        gameLoopStates.get(nextLoopState.getCoreLoopState()).add(nextLoopState);
                        synchronized (nextGameEvents) {
                            nextGameEvents.computeIfAbsent(nextLoopState, gameLoopState -> new ArrayDeque<>());
                        }
                    }
                    nextLoopStates.clear();
                }
            }

            runGameLoopStates(CoreLoopState.EarlyUpdate, elapsedTime);
            fireNextCoreEvents(CoreLoopState.EarlyUpdate);

            while (accumulator >= fixedUpdateInterval.get()) {
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
            currentGameLoopState = gameLoopState;
            gameLoopState.accept(elapsedFixedTime);
            fireNextGameEvents(gameLoopState);
        }
    }

    private void fireNextCoreEvents(CoreLoopState coreLoopState) {
        synchronized (nextCoreEvents) {
            fireNextEvents(nextCoreEvents.get(coreLoopState));
        }
    }

    private void fireNextGameEvents(GameLoopState gameLoopState) {
        synchronized (nextGameEvents) {
            fireNextEvents(nextGameEvents.get(gameLoopState));
        }
    }

    private void fireNextEvents(Queue<? extends GameEvent> gameEvents) {
        if (gameEvents == null || gameEvents.isEmpty()) {
            return;
        }

        while (!gameEvents.isEmpty()) {
            GameEvent nextEvent = gameEvents.poll();
            fireEvent(nextEvent);
        }
    }

    private void sync() {
        final float updateInterval = 1f / targetFPS;
        final double endTime = deltaTimer.getLastTimestamp() + updateInterval;
        final double currentTime = deltaTimer.getCurrentTime();
        if (currentTime < endTime) {
            try {
                TimeUnit.MILLISECONDS.sleep((long) ((endTime - currentTime) * 1000L));
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void reset() {
        for (Set<GameLoopState> loopStates : gameLoopStates.values()) {
            loopStates.clear();
        }
        nextLoopStates.clear();
        for (Queue<GameEvent> gameEvents : nextCoreEvents.values()) {
            gameEvents.clear();
        }

        currentGameLoopState = NoState;

        clear();
        setTargetFPS(DefaultFPS);
        setTargetUPS(DefaultUPS);
    }

    public void clear() {
        nextGameEvents.clear();
        gameEventObservers.clear();
        gameEventHandlers.clear();
    }
}
