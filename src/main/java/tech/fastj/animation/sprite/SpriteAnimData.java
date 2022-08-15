package tech.fastj.animation.sprite;

import tech.fastj.animation.AnimationData;
import tech.fastj.graphics.game.Sprite2D;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public class SpriteAnimData implements AnimationData<Sprite2D, SpriteAnimData> {
    private final String animationName;
    private final SpriteAnimStyle spriteAnimStyle;

    private final int firstFrame;
    private final int lastFrame;
    private final Map<Predicate<Sprite2D>, SpriteAnimData> nextPossibleAnimations;

    public SpriteAnimData(String animationName, SpriteAnimStyle spriteAnimStyle, int firstFrame, int lastFrame) {
        this.animationName = Objects.requireNonNull(animationName);
        this.spriteAnimStyle = spriteAnimStyle;
        this.firstFrame = firstFrame;
        this.lastFrame = lastFrame;
        this.nextPossibleAnimations = new LinkedHashMap<>();
    }

    @Override
    public Map<Predicate<Sprite2D>, SpriteAnimData> getNextPossibleAnimations() {
        return nextPossibleAnimations;
    }

    @Override
    public String getAnimationName() {
        return animationName;
    }

    public SpriteAnimStyle getAnimationStyle() {
        return spriteAnimStyle;
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
        SpriteAnimData animationData = (SpriteAnimData) other;
        return firstFrame == animationData.firstFrame
            && lastFrame == animationData.lastFrame
            && spriteAnimStyle == animationData.spriteAnimStyle
            && Objects.equals(animationName, animationData.animationName)
            && Objects.equals(nextPossibleAnimations, animationData.nextPossibleAnimations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animationName, spriteAnimStyle, firstFrame, lastFrame, nextPossibleAnimations);
    }
}
