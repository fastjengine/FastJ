package tech.fastj.systems.collections;

import java.util.Objects;

/**
 * 2-tuple generic class.
 * <p>
 * This implementation does not provide comparisons or well-established, veritable solutions for tuples. It only
 * provides the necessities to easily return two values at once. If you would like a well-made tuple system, go use
 * Apache Commons' libraries.
 *
 * @param <L> The type for the left parameter of the pair.
 * @param <R> The type for the right parameter of the pair.
 */
public class Pair<L, R> {

    private final L left;
    private final R right;

    public Pair(L left, R right) {
        this.left = Objects.requireNonNull(left);
        this.right = Objects.requireNonNull(right);
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Pair<?, ?> pair = (Pair<?, ?>) other;
        return left.equals(pair.left) && right.equals(pair.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    public static <L, R> Pair<L, R> of(L left, R right) {
        return new Pair<>(left, right);
    }
}
