package tech.fastj.graphics.game;

import tech.fastj.resources.images.ImageResource;

import java.util.Objects;

public class Sprite2DBuilder {

    private final ImageResource spriteResource;

    private int startingFrame = Sprite2D.DefaultStartingFrame;
    private int animationFPS = Sprite2D.DefaultAnimationFPS;
    private int horizontalImageCount = Sprite2D.DefaultHorizontalImageCount;
    private int verticalImageCount = Sprite2D.DefaultVerticalImageCount;
    private AnimationStyle animationStyle = Sprite2D.DefaultAnimationStyle;

    Sprite2DBuilder(ImageResource spriteResource) {
        this.spriteResource = Objects.requireNonNull(spriteResource, "The sprite resource instance must not be null.");
    }

    public Sprite2DBuilder withImageCount(int horizontalImageCount, int verticalImageCount) {
        if (horizontalImageCount < 1) {
            throw new IllegalArgumentException("The given horizontal image count must not be less than 1.");
        }
        if (verticalImageCount < 1) {
            throw new IllegalArgumentException("The given vertical image count must not be less than 1.");
        }

        this.horizontalImageCount = horizontalImageCount;
        this.verticalImageCount = verticalImageCount;

        return this;
    }

    public Sprite2DBuilder withStartingFrame(int startingFrame) {
        if (startingFrame < 0) {
            throw new IllegalArgumentException("The starting frame value must not be less than 0.");
        }
        if (startingFrame >= (horizontalImageCount * verticalImageCount)) {
            throw new IllegalArgumentException("The starting frame value must not be more than the amount of sprite images.");
        }

        this.startingFrame = startingFrame;
        return this;
    }

    public Sprite2DBuilder withAnimationFPS(int animationFPS) {
        if (animationFPS < 1) {
            throw new IllegalArgumentException("The animation FPS must not be less than 1 FPS.");
        }

        this.animationFPS = animationFPS;
        return this;
    }

    public Sprite2DBuilder withAnimationStyle(AnimationStyle animationStyle) {
        this.animationStyle = Objects.requireNonNull(animationStyle, "The animation style must not be null.");
        return this;
    }

    public Sprite2D build() {
        return new Sprite2D(spriteResource, horizontalImageCount, verticalImageCount)
                .setCurrentFrame(startingFrame)
                .setAnimationFPS(animationFPS)
                .setAnimationStyle(animationStyle);
    }
}
