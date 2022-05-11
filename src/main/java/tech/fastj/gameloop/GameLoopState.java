package tech.fastj.gameloop;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class GameLoopState implements Comparable<GameLoopState>, Consumer<Float> {

    private final CoreLoopState coreLoopState;
    private final int subStatePriority;
    private final BiConsumer<GameLoopState, Float> loopStateAction;

    private boolean isActive;

    public GameLoopState(CoreLoopState coreLoopState, int priority, BiConsumer<GameLoopState, Float> loopStateAction) {
        this.coreLoopState = coreLoopState;
        this.subStatePriority = priority;
        this.loopStateAction = loopStateAction;
    }

    public CoreLoopState getBaseLoopState() {
        return coreLoopState;
    }

    public int getPriority() {
        return subStatePriority;
    }

    public boolean isActive() {
        return isActive;
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
                ", subStatePriority=" + subStatePriority +
                '}';
    }
}
