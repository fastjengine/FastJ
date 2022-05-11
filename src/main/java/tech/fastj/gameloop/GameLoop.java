package tech.fastj.gameloop;

import java.util.ArrayDeque;
import java.util.Arrays;
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

    private final Predicate<GameLoop> exitCondition;
    private final Predicate<GameLoop> syncCondition;

    private final Map<CoreLoopState, Set<GameLoopState>> gameLoopStates = Map.of(
            CoreLoopState.EarlyUpdate, new TreeSet<>(),
            CoreLoopState.FixedUpdate, new TreeSet<>(),
            CoreLoopState.Update, new TreeSet<>(),
            CoreLoopState.LateUpdate, new TreeSet<>()
    );

    private final Queue<GameLoopState> nextLoopStates;

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
                    }
                    nextLoopStates.clear();
                }
            }

            for (GameLoopState gameLoopState : gameLoopStates.get(CoreLoopState.EarlyUpdate)) {
                currentGameLoopState = gameLoopState;
                gameLoopState.accept(elapsedTime);
            }

            while (accumulator >= updateInterval) {
                elapsedFixedTime = fixedDeltaTimer.evalDeltaTime();

                for (GameLoopState gameLoopState : gameLoopStates.get(CoreLoopState.FixedUpdate)) {
                    currentGameLoopState = gameLoopState;
                    gameLoopState.accept(elapsedFixedTime);
                }

                accumulator -= elapsedFixedTime;
            }


            for (GameLoopState gameLoopState : gameLoopStates.get(CoreLoopState.Update)) {
                currentGameLoopState = gameLoopState;
                gameLoopState.accept(elapsedTime);
            }

            for (GameLoopState gameLoopState : gameLoopStates.get(CoreLoopState.LateUpdate)) {
                currentGameLoopState = gameLoopState;
                gameLoopState.accept(elapsedTime);
            }

            currentGameLoopState = NoState;

            if (syncCondition.test(this)) {
                sync();
            }
        }

        isRunning = false;
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
