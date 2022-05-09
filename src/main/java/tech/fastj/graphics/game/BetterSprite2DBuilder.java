package tech.fastj.graphics.game;

import tech.fastj.engine.FastJEngine;

import tech.fastj.math.Pointf;
import tech.fastj.math.Transform2D;

import tech.fastj.resources.images.ImageResource;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import tech.fastj.animation.sprite.SpriteAnimationData;

public class BetterSprite2DBuilder {

    private final ImageResource spriteResource;
    private final Map<String, SpriteAnimationData> animationDataMap;
    private final boolean shouldRender;

    private int startingFrame = Sprite2D.DefaultStartingFrame;
    private int animationFPS = Sprite2D.DefaultAnimationFPS;
    private int horizontalImageCount = Sprite2D.DefaultHorizontalImageCount;
    private int verticalImageCount = Sprite2D.DefaultVerticalImageCount;
    private String startingAnimation = "";
    private boolean startPaused;

    private Pointf translation = Transform2D.DefaultTranslation.copy();
    private float rotation = Transform2D.DefaultRotation;
    private Pointf scale = Transform2D.DefaultScale.copy();

    BetterSprite2DBuilder(ImageResource spriteResource, boolean shouldRender) {
        this.spriteResource = Objects.requireNonNull(spriteResource, "The sprite resource instance must not be null.");
        this.shouldRender = shouldRender;
        this.animationDataMap = new LinkedHashMap<>();
    }

    public BetterSprite2DBuilder withImageCount(int horizontalImageCount, int verticalImageCount) {
        if (horizontalImageCount < 1) {
            throw new IllegalArgumentException("The given horizontal image count must be at least 1.");
        }
        if (verticalImageCount < 1) {
            throw new IllegalArgumentException("The given vertical image count must be at least 1.");
        }

        this.horizontalImageCount = horizontalImageCount;
        this.verticalImageCount = verticalImageCount;

        return this;
    }

    public BetterSprite2DBuilder withStartingFrame(int startingFrame) {
        if (startingFrame < 0) {
            throw new IllegalArgumentException("The starting frame value must be at least 0.");
        }
        if (startingFrame >= (horizontalImageCount * verticalImageCount)) {
            throw new IllegalArgumentException("The starting frame value must not be more than the amount of sprite images.");
        }

        this.startingFrame = startingFrame;
        return this;
    }

    public BetterSprite2DBuilder withAnimationFPS(int animationFPS) {
        if (animationFPS < 1) {
            throw new IllegalArgumentException("The animation FPS must be at least 1 FPS.");
        }

        this.animationFPS = animationFPS;
        return this;
    }

    public BetterSprite2DBuilder withStartingAnimation(String startingAnimation) {
        this.startingAnimation = Objects.requireNonNull(startingAnimation, "The current animation should not be null.");
        return this;
    }

    public BetterSprite2DBuilder startPaused(boolean startPaused) {
        this.startPaused = startPaused;
        return this;
    }

    public BetterSprite2DBuilder withAnimationData(SpriteAnimationData animationData) {
        assert animationData != null;
        assert animationData.getAnimationName() != null;

        animationDataMap.put(animationData.getAnimationName(), animationData);
        return this;
    }

    /**
     * Sets the builder's transformation (translation, rotation, scale) values.
     *
     * @param translation The translation {@code Pointf} to be used in the resulting {@code Polygon2D}.
     * @param rotation    The rotation {@code float} to be used in the resulting {@code Polygon2D}.
     * @param scale       The scale {@code Pointf} to be used int he resulting {@code Polygon2D}.
     * @return The {@code BetterSprite2DBuilder}, for method chaining.
     */
    public BetterSprite2DBuilder withTransform(Pointf translation, float rotation, Pointf scale) {
        this.translation = Objects.requireNonNull(translation, "The translation value must not be null.");
        this.scale = Objects.requireNonNull(scale, "The scale value must not be null.");
        if (Float.isNaN(rotation)) {
            throw new NumberFormatException("The rotation value must not be NaN.");
        }
        this.rotation = rotation;
        return this;
    }

    public Sprite2D build() {
        Sprite2D sprite2D = (Sprite2D) new Sprite2D(spriteResource, horizontalImageCount, verticalImageCount, animationDataMap)
                .setCurrentFrame(startingFrame)
                .setAnimationFPS(animationFPS)
                .setCurrentAnimation(startingAnimation)
                .setTransform(translation, rotation, scale)
                .setShouldRender(shouldRender);

        FastJEngine.getAnimationEngine(Sprite2D.class).addAnimated(sprite2D);
        if (!startPaused) {
            FastJEngine.runAfterUpdate(() -> sprite2D.setPaused(false));
        }

        return sprite2D;
    }
}
