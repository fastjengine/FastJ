package tech.fastj.gameloop;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class GameLoopState implements Comparable<GameLoopState>, Consumer<Float> {

    public static final boolean DefaultEnginePriority = false;

    private final CoreLoopState coreLoopState;
    private final boolean hasEnginePriority;
    private final int subStatePriority;
    private final BiConsumer<GameLoopState, Float> loopStateAction;

    private boolean isActive;

    public GameLoopState(CoreLoopState coreLoopState, int priority, BiConsumer<GameLoopState, Float> loopStateAction) {
        this(coreLoopState, DefaultEnginePriority, priority, loopStateAction);
    }

    public GameLoopState(CoreLoopState coreLoopState, boolean enginePriority, int priority, BiConsumer<GameLoopState, Float> loopStateAction) {
        this.coreLoopState = Objects.requireNonNull(coreLoopState);
        this.hasEnginePriority = enginePriority;
        this.subStatePriority = priority;
        this.loopStateAction = Objects.requireNonNull(loopStateAction);
    }

    public CoreLoopState getCoreLoopState() {
        return coreLoopState;
    }

    public int getPriority() {
        return subStatePriority;
    }

    public boolean hasEnginePriority() {
        return hasEnginePriority;
    }

    public boolean isActive() {
        return isActive;
    }

    public BiConsumer<GameLoopState, Float> getLoopStateAction() {
        return loopStateAction;
    }

    @Override
    public synchronized void accept(Float deltaTime) {
        isActive = true;
        loopStateAction.accept(this, deltaTime);
        isActive = false;
    }

    @Override
    public int compareTo(GameLoopState other) {
        int comparison = coreLoopState.compareTo(other.coreLoopState);
        if (comparison != 0) {
            return comparison;
        }

        if (hasEnginePriority && !other.hasEnginePriority) {
            return -1;
        }
        if (!hasEnginePriority && other.hasEnginePriority) {
            return 1;
        }

        return Integer.compare(subStatePriority, other.subStatePriority);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        GameLoopState gameLoopState = (GameLoopState) other;
        return subStatePriority == gameLoopState.subStatePriority
                && hasEnginePriority == gameLoopState.hasEnginePriority
                && coreLoopState == gameLoopState.coreLoopState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(coreLoopState, subStatePriority);
    }

    @Override
    public String toString() {
        return "GameLoopState{" +
                "baseLoopState=" + coreLoopState +
                ", hasEnginePriority=" + hasEnginePriority +
                ", subStatePriority=" + subStatePriority +
                '}';
    }
}
