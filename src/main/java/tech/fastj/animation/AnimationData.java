package tech.fastj.animation;

import java.util.Map;
import java.util.function.Predicate;

public interface AnimationData {
    String getAnimationName();

    <T extends Animated<AnimationData>> Map<Predicate<T>, AnimationData> getNextPossibleAnimations();
}
