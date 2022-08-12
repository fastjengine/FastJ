package tech.fastj.gameloop;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * State to be run in {@link GameLoop the game loop} at its given {@link #getCoreLoopState() core loop state}.
 * <p>
 * A game loop state defines logic that should be run whenever the {@link GameLoop game loop} reaches it during runtime iteration. The order
 * for game loop states and when they are to be run is based on several factors:
 * <ol>
 *     <li>The {@link #getCoreLoopState() core loop state}</li>
 *     <li>Whether the state has {@link #hasEnginePriority() engine priority}</li>
 *     <li>The {@link #getPriority() general priority}</li>
 * </ol>
 *
 * @author Andrew Dey
 * @since 1.7.0
 */
public class GameLoopState implements Comparable<GameLoopState>, Consumer<Float> {

    /** The default engine priority for game loop states. */
    public static final boolean DefaultEnginePriority = false;

    private final CoreLoopState coreLoopState;
    private final boolean hasEnginePriority;
    private final int subStatePriority;
    private final BiConsumer<GameLoopState, Float> loopStateAction;

    private boolean isActive;

    /**
     * Constructs a game loop state, with a {@link #DefaultEnginePriority default engine priority}.
     *
     * @param coreLoopState   The core loop state defining when the game loop state should be run.
     * @param priority        The general priority defining when the game loop state should be run, within its core loop state.
     * @param loopStateAction The action to run for the loop state.
     */
    public GameLoopState(CoreLoopState coreLoopState, int priority, BiConsumer<GameLoopState, Float> loopStateAction) {
        this(coreLoopState, DefaultEnginePriority, priority, loopStateAction);
    }

    /**
     * Constructs a game loop state.
     *
     * @param coreLoopState   The core loop state defining when the game loop state should be run.
     * @param enginePriority  Whether the game loop state has engine priority.
     * @param priority        The general priority defining when the game loop state should be run, within its core loop state.
     * @param loopStateAction The action to run for the loop state.
     */
    public GameLoopState(CoreLoopState coreLoopState, boolean enginePriority, int priority, BiConsumer<GameLoopState, Float> loopStateAction) {
        this.coreLoopState = Objects.requireNonNull(coreLoopState);
        this.hasEnginePriority = enginePriority;
        this.subStatePriority = priority;
        this.loopStateAction = Objects.requireNonNull(loopStateAction);
    }

    /** {@return the core loop state defining when in the {@link GameLoop game loop} the game loop state should be run} */
    public CoreLoopState getCoreLoopState() {
        return coreLoopState;
    }

    /** {@return the general priority defining specifically when in the {@link GameLoop game loop} it should be run} */
    public int getPriority() {
        return subStatePriority;
    }

    /** {@return whether the game loop state has engine priority, affecting when in the {@link GameLoop game loop} it should be run} */
    public boolean hasEnginePriority() {
        return hasEnginePriority;
    }

    /**
     * {@return whether the game loop state is actively being used}.
     * <p>
     * This value is currently not used. Check back in a later engine version for improved support.
     */
    public boolean isActive() {
        return isActive;
    }

    /** {@return the action the game loop state will run when used} */
    public BiConsumer<GameLoopState, Float> getLoopStateAction() {
        return loopStateAction;
    }

    /**
     * Internal method used to run the loop state.
     *
     * @param deltaTime delta (or fixed delta) time from the {@link GameLoop game loop}.
     */
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
