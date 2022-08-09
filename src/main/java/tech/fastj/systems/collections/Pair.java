package tech.fastj.systems.collections;

import tech.fastj.graphics.util.PointsAndAlts;

import java.util.Objects;

/**
 * <b>DEPRECATED</b> 2-tuple generic non-nullable record for storing 2 values.
 * <p>
 * This implementation does not provide comparisons or well-established, veritable solutions for tuples. It only provides the necessities to
 * easily return two values at once. If you would like a well-made tuple system, go use the Apache Commons libraries.
 *
 * @param <L> The type for the {@link #left() left parameter} of the pair.
 * @param <R> The type for the {@link #right() right parameter} of the pair.
 * @deprecated This class will be removed in a future version of FastJ. For usages related to points and alternate indexes, use
 * {@link PointsAndAlts}.
 */
@Deprecated(forRemoval = true)
public record Pair<L, R>(L left, R right) {

    /**
     * Constructs a {@link Pair} created from the two given values.
     *
     * @param left  The left value.
     * @param right The right value.
     * @deprecated This class will be removed in a future version of FastJ. For usages related to points and alternate indexes, use
     * {@link PointsAndAlts}.
     */
    @Deprecated(forRemoval = true)
    public Pair(L left, R right) {
        this.left = Objects.requireNonNull(left);
        this.right = Objects.requireNonNull(right);
    }

    /**
     * {@return a {@link Pair} created from the two given values}
     *
     * @param left  The left value.
     * @param right The right value.
     * @param <L>   The type for the {@link #left() left parameter} of the pair.
     * @param <R>   The type for the {@link #right() right parameter} of the pair.
     * @deprecated This class will be removed in a future version of FastJ. For usages related to points and alternate indexes, use
     * {@link PointsAndAlts}.
     */
    @Deprecated(forRemoval = true)
    public static <L, R> Pair<L, R> of(L left, R right) {
        return new Pair<>(left, right);
    }
}
