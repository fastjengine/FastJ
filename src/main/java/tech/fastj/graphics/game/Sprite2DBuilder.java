package tech.fastj.graphics.game;

import java.awt.image.BufferedImage;
import java.util.Objects;

public class Sprite2DBuilder {

    private final BufferedImage[] sprites;
    private int startingFrame = Sprite2D.DefaultStartingFrame;
    private int animationFPS = Sprite2D.DefaultAnimationFPS;
    private AnimationStyle animationStyle = Sprite2D.DefaultAnimationStyle;

    Sprite2DBuilder(BufferedImage[] sprites) {
        this.sprites = Objects.requireNonNull(sprites, "The array of image sprites must not be null.");
    }

    public Sprite2DBuilder withStartingFrame(int startingFrame) {
        if (startingFrame < 0) {
            throw new IllegalArgumentException("The starting frame value must not be less than 0.");
        }
        if (startingFrame >= sprites.length) {
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
        return new Sprite2D(sprites)
                .setCurrentFrame(startingFrame)
                .setAnimationFPS(animationFPS)
                .setAnimationStyle(animationStyle);
    }
}
