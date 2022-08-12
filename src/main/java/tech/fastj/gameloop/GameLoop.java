package tech.fastj.gameloop;

import tech.fastj.gameloop.event.Event;
import tech.fastj.gameloop.event.EventHandler;
import tech.fastj.gameloop.event.EventObserver;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

/**
 * Game loop made up of <i>game states</i>, such that you can create and configure your own custom game loop.
 * <p>
 * This game loop's states are two levels deep: {@link CoreLoopState core game loop states}, and
 * {@link GameLoopState general game loop states}.
 * <h2>Game Loop States</h2>
 * The {@link CoreLoopState core game loop states} dictate when that state would be run (see {@code Game Loop Iteration Order} below).
 * <p>
 * The {@link GameLoopState general game loop states} are much more specific, and are what you create to add to the game loop. These are
 * backed by {@link GameLoopState#getCoreLoopState() core loop states}, but also contain a {@link GameLoopState#getPriority() priority}
 * which determines their specific order when running. You may refer to the documentation for {@link CoreLoopState} and
 * {@link GameLoopState} for more information on how they work.
 * <h2>Game Loop Iteration Order</h2>
 * The game loop, when run, will attempt to iterate and spend {@code 1000/{@link #getTargetFPS() target fps}} milliseconds per iteration.
 * This way, a relatively consistent frame rate can be achieved.
 * <p>
 * Below is a diagram of what happens during iteration.
 * <ol>
 *     <li>Check to determine whether to continue running -- see {@link #GameLoop(Predicate, Predicate)}</li>
 *     <li>{@link CoreLoopState#EarlyUpdate Early Update} -- runs once at the beginning of every game loop iteration.</li>
 *     <li>
 *         {@link CoreLoopState#FixedUpdate Fixed Update} -- runs
 *         {@code {@link #getTargetUPS() target ups}/{@link #getTargetFPS() target fps}} times per game loop iteration.
 *     </li>
 *     <li>
 *         {@link CoreLoopState#Update Update} -- runs once per game loop iteration after fixed update to achieve
 *         {@link #getTargetFPS() the target fps}, where rendering is often the last task.
 *     </li>
 *     <li>
 *         {@link CoreLoopState#LateUpdate Late Update} -- runs once per game loop iteration after update, primarily for post-rendering
 *         tasks.
 *     </li>
 *     <li>Check to determine whether to perform manual time-syncing -- see {@link #GameLoop(Predicate, Predicate)}</li>
 * </ol>
 * <h2>Events System</h2>
 * The game loop can also receive and fire {@link Event events}. An event is data acting as a snapshot regarding some action performed.
 * These events can then be processed by {@link EventListener event listeners}.
 * <p>
 * Refer to the documentation of {@link Event} and {@link EventListener} to learn how to set them up.
 * <h2>Using the Events System</h2>
 * The game loop can be utilized to send events that can be fired immediately, or after the given
 * {@link CoreLoopState}/{@link GameLoopState} -- essentially, after the given state next finishes running.
 * <ul>
 *     <li>{@link #fireEvent(Event) Firing an event immediately}</li>
 *     <li>{@link #fireEvent(Event, CoreLoopState) Firing an event after a core loop state finishes}</li>
 *     <li>{@link #fireEvent(Event, GameLoopState) Firing an event after a game loop state finishes}</li>
 * </ul>
 *
 * @author Andrew Dey
 * @since 1.7.0
 */
public class GameLoop implements Runnable {

    /** The default {@link #getTargetFPS() target frames per second}. */
    public static final int DefaultFPS = 60;

    /** The default {@link #getTargetUPS() target updates per second}. */
    public static final int DefaultUPS = 30;

    /** Empty {@link GameLoopState game loop state}, for resetting the game loop. */
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

    private final Map<CoreLoopState, Queue<Event>> nextCoreEvents = Map.of(
        CoreLoopState.EarlyUpdate, new ArrayDeque<>(),
        CoreLoopState.FixedUpdate, new ArrayDeque<>(),
        CoreLoopState.Update, new ArrayDeque<>(),
        CoreLoopState.LateUpdate, new ArrayDeque<>()
    );
    private final Map<GameLoopState, Queue<Event>> nextEvents;

    private final Map<Class<? extends Event>, List<EventObserver<? extends Event>>> gameEventObservers;
    private final Map<Class<? extends Event>, EventHandler<? extends Event, ? extends EventObserver<? extends Event>>> gameEventHandlers;
    private final Map<Class<? extends Event>, Class<? extends Event>> classAliases;

    private GameLoopState currentGameLoopState;
    private volatile boolean isRunning;

    private final AtomicReference<Float> fixedUpdateInterval;
    private int targetFPS;
    private int targetUPS;

    /**
     * Constructs a game loop with the given predicates for controlling game state.
     *
     * @param shouldRun  Check during iteration for whether the game loop should stop running.
     * @param shouldSync Check during iteration for whether the game loop should perform manual sync.
     */
    public GameLoop(Predicate<GameLoop> shouldRun, Predicate<GameLoop> shouldSync) {
        deltaTimer = new Timer();
        fixedDeltaTimer = new Timer();

        this.runCondition = Objects.requireNonNull(shouldRun);
        this.syncCondition = Objects.requireNonNull(shouldSync);

        isRunning = false;
        nextLoopStates = new ArrayDeque<>();
        nextEvents = new HashMap<>();
        gameEventObservers = new HashMap<>();
        gameEventHandlers = new HashMap<>();
        classAliases = new HashMap<>();
        currentGameLoopState = NoState;

        fixedUpdateInterval = new AtomicReference<>();
        setTargetFPS(DefaultFPS);
        setTargetUPS(DefaultUPS);
    }

    /**
     * Adds the given {@link GameLoopState game loop states} as soon as possible.
     *
     * @param gameLoopStates The game loop states to add.
     */
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

    /**
     * Adds the given {@link GameLoopState game loop state} as soon as possible.
     *
     * @param gameLoopState The game loop state to add.
     */
    public void addGameLoopState(GameLoopState gameLoopState) {
        if (isRunning) {
            synchronized (nextLoopStates) {
                nextLoopStates.add(gameLoopState);
            }
        } else {
            this.gameLoopStates.get(gameLoopState.getCoreLoopState()).add(gameLoopState);
        }
    }

    /**
     * Adds the given event observer which observes the given class.
     *
     * @param eventObserver The {@link EventObserver event observer}
     * @param eventClass    The class the event observer observes.
     * @param <T>           The type of {@link Event event} observed.
     */
    public <T extends Event> void addEventObserver(EventObserver<T> eventObserver, Class<T> eventClass) {
        synchronized (gameEventObservers) {
            if (!gameEventObservers.containsKey(eventClass)) {
                gameEventObservers.put(eventClass, new ArrayList<>());
            }
            gameEventObservers.get(eventClass).add(eventObserver);
        }
    }

    /**
     * Removes the given event observer which observed the given class.
     *
     * @param eventObserver The {@link EventObserver event observer}
     * @param eventClass    The class the event observer observed.
     * @param <T>           The type of {@link Event event} observed.
     */
    public <T extends Event> void removeEventObserver(EventObserver<T> eventObserver, Class<T> eventClass) {
        if (!gameEventObservers.containsKey(eventClass)) {
            return;
        }
        synchronized (gameEventObservers) {
            gameEventObservers.get(eventClass).remove(eventObserver);
        }
    }

    /**
     * Adds the given event handler, to handle events received for the given event class.
     *
     * @param gameEventHandler The {@link EventHandler event handler} to add.
     * @param eventClass       The class the event handler handles.
     * @param <T>              The type of {@link Event} handled.
     * @param <V>              The type of the event handler, based on the event type.
     */
    public <T extends Event, V extends EventHandler<T, EventObserver<T>>> void addEventHandler(V gameEventHandler, Class<T> eventClass) {
        synchronized (gameEventHandlers) {
            gameEventHandlers.put(eventClass, gameEventHandler);
        }
    }

    /**
     * Removes the event handler for the given class.
     *
     * @param eventClass The class the event handler handled.
     * @param <T>        The type of {@link Event} handled.
     */
    public <T extends Event> void removeEventHandler(Class<T> eventClass) {
        synchronized (gameEventHandlers) {
            gameEventHandlers.remove(eventClass);
        }
    }

    /**
     * Adds a class alias for the given class.
     * <p>
     * A class alias is used to generalize a given class to another class. This is often paired with {@link EventHandler event handlers}, to
     * account for all needed event classes at once.
     *
     * @param originalClass The original class to alias from.
     * @param aliasedClass  The class to alias to.
     * @param <S>           The type of the class alias.
     * @param <T>           The type of the class being aliased, which extends the class alias type.
     */
    public <S extends Event, T extends S> void addClassAlias(Class<T> originalClass, Class<S> aliasedClass) {
        classAliases.put(originalClass, aliasedClass);
    }

    /**
     * Removes the class alias for the given class.
     * <p>
     * A class alias is used to generalize a given class to another class. This is often paired with {@link EventHandler event handlers}, to
     * account for all needed event classes at once.
     *
     * @param originalClass The original class to alias from.
     * @param <S>           The type of the class alias.
     * @param <T>           The type of the class being aliased, which extends the class alias type.
     */
    public <S extends Event, T extends S> void removeClassAlias(Class<T> originalClass) {
        classAliases.remove(originalClass);
    }

    /**
     * {@return the class alias of the given class}
     * <p>
     * A class alias is used to generalize a given class to another class. This is often paired with {@link EventHandler event handlers}, to
     * account for all needed event classes at once.
     *
     * @param originalClass The class to get the alias for.
     * @param <T>           The type of the class to get the alias for.
     */
    public <T extends Event> Class<? extends Event> getClassAlias(Class<T> originalClass) {
        return classAliases.get(originalClass);
    }

    /** {@return whether the game loop is running} */
    public boolean isRunning() {
        return isRunning;
    }

    /** {@return the game loop's current target for frames per second} */
    public int getTargetFPS() {
        return targetFPS;
    }

    /** {@return the game loop's current target for updates per second.} */
    public int getTargetUPS() {
        return targetUPS;
    }

    /**
     * {@return the amount of time passed (in {@link TimeUnit#MILLISECONDS milliseconds}) since the last {@link CoreLoopState#Update
     * update}}
     */
    public float getDeltaTime() {
        return deltaTimer.getDeltaTime();
    }

    /**
     * {@return the amount of time passed (in {@link TimeUnit#MILLISECONDS milliseconds}) since the last {@link CoreLoopState#FixedUpdate
     * fixed update}}
     */
    public float getFixedDeltaTime() {
        return fixedDeltaTimer.getDeltaTime();
    }

    /**
     * Set the game loop's target frames per second.
     * <p>
     * If the game loop {@link #isRunning() is running}, this method will not do anything.
     *
     * @param fps The new target FPS.
     */
    public void setTargetFPS(int fps) {
        if (!isRunning) {
            if (fps < 1) {
                throw new IllegalArgumentException("FPS amount must be at least 1.");
            }
            this.targetFPS = fps;
        }
    }

    /**
     * Set the game loop's target updates per second.
     * <p>
     * If the game loop {@link #isRunning() is running}, this method will not do anything.
     *
     * @param ups The new target UPS.
     */
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

    /**
     * {@return the {@link EventObserver event observers} for the given event class}
     *
     * @param eventClass The event class to get event observers for.
     * @param <T>        The type of {@link Event}
     */
    public <T extends Event> List<EventObserver<? extends Event>> getEventObservers(Class<T> eventClass) {
        return gameEventObservers.getOrDefault(eventClass, List.of());
    }

    /**
     * {@return the {@link EventHandler event handler} for the given event class}
     *
     * @param eventClass The event class to get the event handler for.
     * @param <T>        The type of {@link Event}
     * @param <V>        The type of {@link EventObserver}
     */
    @SuppressWarnings("unchecked")
    public <T extends Event, V extends EventObserver<T>> EventHandler<T, V> getEventHandler(Class<T> eventClass) {
        return (EventHandler<T, V>) gameEventHandlers.get(eventClass);
    }

    /** {@return all of the game loop's states} */
    public Map<CoreLoopState, Set<GameLoopState>> getGameLoopStates() {
        return gameLoopStates;
    }

    /** {@return the set of all game loop states, in their correct order} */
    public Set<GameLoopState> getGameLoopStatesOrdered() {
        TreeSet<GameLoopState> result = new TreeSet<>();
        result.addAll(gameLoopStates.get(CoreLoopState.EarlyUpdate));
        result.addAll(gameLoopStates.get(CoreLoopState.FixedUpdate));
        result.addAll(gameLoopStates.get(CoreLoopState.Update));
        result.addAll(gameLoopStates.get(CoreLoopState.LateUpdate));
        return result;
    }

    /**
     * {@return the currently active game loop state}
     * <p>
     * This corresponds to the game loop state currently being operated on {@link #run() when the game loop is running}. If the game loop
     * has finished its last state in an iteration, or the game loop is not running, the returned value will be {@link #NoState}.
     */
    public GameLoopState getCurrentGameLoopState() {
        return currentGameLoopState;
    }

    /**
     * Fires the given event immediately.
     *
     * @param event The event to fire.
     * @param <T>   The class of the {@link Event event}.
     */
    @SuppressWarnings("unchecked")
    public <T extends Event> void fireEvent(T event) {
        Class<T> eventClass = (Class<T>) event.getClass();
        tryFireEvent(event, eventClass);

        Class<Event> classAlias = (Class<Event>) classAliases.get(eventClass);
        if (classAlias != null) {
            tryFireEvent(event, classAlias);
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends Event> void tryFireEvent(T event, Class<T> eventClass) {
        var gameEventHandler = (EventHandler<T, EventObserver<T>>) gameEventHandlers.get(eventClass);
        if (gameEventHandler != null) {
            ((EventHandler) gameEventHandler).handleEvent(gameEventObservers.get(eventClass), event);
            return;
        }

        List<EventObserver<? extends Event>> eventObservers = gameEventObservers.get(eventClass);
        if (eventObservers == null) {
            gameEventObservers.put(eventClass, new ArrayList<>());
            eventObservers = gameEventObservers.get(eventClass);
        }

        if (gameEventObservers.get(eventClass).isEmpty()) {
            return;
        }

        for (var gameEventObserver : eventObservers) {
            ((EventObserver<T>) gameEventObserver).eventReceived(event);
        }
    }

    /**
     * Fires the given event as soon as the given {@link GameLoopState game loop state} next finishes running.
     *
     * @param event      The event to fire.
     * @param whenToFire The game loop state to wait for, before firing the event.
     * @param <T>        The class of the {@link Event event}.
     */
    public <T extends Event> void fireEvent(T event, GameLoopState whenToFire) {
        synchronized (nextEvents) {
            if (nextEvents.get(whenToFire) == null) {
                nextEvents.put(whenToFire, new ArrayDeque<>());
            }
            nextEvents.get(whenToFire).add(event);
        }
    }

    /**
     * Fires the given event as soon as the given {@link CoreLoopState core loop state} next finishes running.
     *
     * @param event      The event to fire.
     * @param whenToFire The core loop state to wait for, before firing the event.
     * @param <T>        The class of the {@link Event event}.
     */
    public <T extends Event> void fireEvent(T event, CoreLoopState whenToFire) {
        synchronized (nextCoreEvents) {
            nextCoreEvents.get(whenToFire).add(event);
        }
    }

    /** Runs the game loop, setting {@link #isRunning()} to {@code true}. */
    @Override
    public synchronized void run() {
        isRunning = true;

        float elapsedTime;
        float elapsedFixedTime;
        float accumulator = 0f;

        // start timers fresh
        deltaTimer.init();
        fixedDeltaTimer.init();

        while (runCondition.test(this)) {
            elapsedTime = deltaTimer.evalDeltaTime();
            accumulator += elapsedTime;

            if (!nextLoopStates.isEmpty()) {
                synchronized (nextLoopStates) {
                    for (GameLoopState nextLoopState : nextLoopStates) {
                        gameLoopStates.get(nextLoopState.getCoreLoopState()).add(nextLoopState);
                        synchronized (nextEvents) {
                            nextEvents.computeIfAbsent(nextLoopState, gameLoopState -> new ArrayDeque<>());
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
            fireNextEvents(gameLoopState);
        }
    }

    private void fireNextCoreEvents(CoreLoopState coreLoopState) {
        synchronized (nextCoreEvents) {
            fireNextEvents(nextCoreEvents.get(coreLoopState));
        }
    }

    private void fireNextEvents(GameLoopState gameLoopState) {
        synchronized (nextEvents) {
            fireNextEvents(nextEvents.get(gameLoopState));
        }
    }

    private void fireNextEvents(Queue<? extends Event> gameEvents) {
        if (gameEvents == null || gameEvents.isEmpty()) {
            return;
        }

        while (!gameEvents.isEmpty()) {
            Event nextEvent = gameEvents.poll();
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

    /** Resets the entire game loop, resetting and removing all of its state and clearing the event system. */
    public void reset() {
        for (Set<GameLoopState> loopStates : gameLoopStates.values()) {
            loopStates.clear();
        }
        nextLoopStates.clear();
        for (Queue<Event> events : nextCoreEvents.values()) {
            events.clear();
        }

        currentGameLoopState = NoState;

        clearEventSystem();
        setTargetFPS(DefaultFPS);
        setTargetUPS(DefaultUPS);
    }

    /** Clears the game loop's events, observers, and handlers. */
    public void clearEventSystem() {
        nextEvents.clear();
        gameEventObservers.clear();
        gameEventHandlers.clear();
    }
}
