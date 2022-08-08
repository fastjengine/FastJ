package tech.fastj.animation.sprite;

import tech.fastj.animation.AnimationData;
import tech.fastj.animation.AnimationStyle;
import tech.fastj.graphics.game.Sprite2D;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public class SpriteAnimationData implements AnimationData {
    private final String animationName;
    private final AnimationStyle animationStyle;

    private final int firstFrame;
    private final int lastFrame;
    private final Map<Predicate<Sprite2D>, SpriteAnimationData> nextPossibleAnimations;

    public SpriteAnimationData(String animationName, AnimationStyle animationStyle, int firstFrame, int lastFrame) {
        this.animationName = Objects.requireNonNull(animationName);
        this.animationStyle = animationStyle;
        this.firstFrame = firstFrame;
        this.lastFrame = lastFrame;
        this.nextPossibleAnimations = new LinkedHashMap<>();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<Predicate<Sprite2D>, SpriteAnimationData> getNextPossibleAnimations() {
        return nextPossibleAnimations;
    }

    @Override
    public String getAnimationName() {
        return animationName;
    }

    public AnimationStyle getAnimationStyle() {
        return animationStyle;
    }

    public int getFirstFrame() {
        return firstFrame;
    }

    public int getLastFrame() {
        return lastFrame;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        SpriteAnimationData animationData = (SpriteAnimationData) other;
        return firstFrame == animationData.firstFrame
                && lastFrame == animationData.lastFrame
                && animationStyle == animationData.animationStyle
                && Objects.equals(animationName, animationData.animationName)
                && Objects.equals(nextPossibleAnimations, animationData.nextPossibleAnimations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animationName, animationStyle, firstFrame, lastFrame, nextPossibleAnimations);
    }
}
