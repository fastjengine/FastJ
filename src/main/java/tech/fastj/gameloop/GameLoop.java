package tech.fastj.gameloop;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class GameLoop implements Runnable {

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

    public GameLoop(Predicate<GameLoop> exit, Predicate<GameLoop> sync, int fps, int ups, GameLoopState... gameLoopStates) {
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

        for (GameLoopState gameLoopState : gameLoopStates) {
            this.gameLoopStates.get(gameLoopState.getBaseLoopState()).add(gameLoopState);
        }
    }

    @Override
    public synchronized void run() {
        float elapsedTime;
        float elapsedFixedTime;
        float accumulator = 0f;
        float updateInterval = 1f / targetUPS;

        Set<GameLoopState> earlyUpdateLoopStates = gameLoopStates.get(CoreLoopState.EarlyUpdate);
        Set<GameLoopState> fixedUpdateLoopStates = gameLoopStates.get(CoreLoopState.FixedUpdate);
        Set<GameLoopState> updateLoopStates = gameLoopStates.get(CoreLoopState.Update);
        Set<GameLoopState> lateUpdateLoopStates = gameLoopStates.get(CoreLoopState.LateUpdate);

        while (exitCondition.test(this)) {
            elapsedTime = deltaTimer.evalDeltaTime();
            accumulator += elapsedTime;

            for (GameLoopState gameLoopState : earlyUpdateLoopStates) {
                gameLoopState.accept(elapsedTime);
            }

            while (accumulator >= updateInterval) {
                elapsedFixedTime = fixedDeltaTimer.evalDeltaTime();

                for (GameLoopState gameLoopState : fixedUpdateLoopStates) {
                    gameLoopState.accept(elapsedFixedTime);
                }

                accumulator -= elapsedFixedTime;
            }


            for (GameLoopState gameLoopState : updateLoopStates) {
                gameLoopState.accept(elapsedTime);
            }
            for (GameLoopState gameLoopState : lateUpdateLoopStates) {
                gameLoopState.accept(elapsedTime);
            }

            if (syncCondition.test(this)) {
                sync();
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
