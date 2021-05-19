package io.github.lucasstarsz.fastj.example.bullethell.scripts;

import io.github.lucasstarsz.fastj.engine.FastJEngine;
import io.github.lucasstarsz.fastj.math.Pointf;
import io.github.lucasstarsz.fastj.graphics.DrawUtil;
import io.github.lucasstarsz.fastj.graphics.Drawable;
import io.github.lucasstarsz.fastj.graphics.game.GameObject;
import io.github.lucasstarsz.fastj.graphics.game.Polygon2D;

import io.github.lucasstarsz.fastj.systems.behaviors.Behavior;
import io.github.lucasstarsz.fastj.systems.input.keyboard.Keyboard;
import io.github.lucasstarsz.fastj.systems.input.keyboard.Keys;
import io.github.lucasstarsz.fastj.systems.tags.TagManager;

import java.awt.Color;
import java.util.Objects;

import io.github.lucasstarsz.fastj.example.bullethell.scenes.GameScene;
import io.github.lucasstarsz.fastj.example.bullethell.util.Tags;

public class Cannon implements Behavior {

    private static final Pointf BulletSize = new Pointf(5f);
    private static final int MaxBulletCount = 5;

    private final GameScene gameScene;
    private int bulletCount;

    public Cannon(GameScene scene) {
        gameScene = Objects.requireNonNull(scene);
    }

    @Override
    public void init(GameObject obj) {
        bulletCount = 0;
    }

    @Override
    public void update(GameObject obj) {
        if (Keyboard.isKeyRecentlyPressed(Keys.Space) && bulletCount < MaxBulletCount) {
            FastJEngine.runAfterUpdate(() -> createBullet(obj));
        }
    }

    private void createBullet(GameObject player) {
        Pointf startingPoint = Pointf.add(player.getCenter(), new Pointf(0f, -50f));
        float rotationAngle = 360f - Math.abs(player.getRotation());
        Pointf rotationPoint = player.getCenter();

        BulletMovement bulletMovementScript = new BulletMovement(gameScene, player.getRotation(), this);
        Pointf cannonFront = Pointf.rotate(startingPoint, rotationAngle, rotationPoint);
        Pointf[] bulletMesh = DrawUtil.createBox(cannonFront, BulletSize);

        Polygon2D bullet = new Polygon2D(bulletMesh, Color.red, true, true)
                .addBehavior(bulletMovementScript, gameScene)
                .addAsGameObject(gameScene)
                .addTag("bullet", gameScene);
        bullet.initBehaviors();

        bulletCount++;
    }

    void bulletDied() {
        bulletCount--;
    }

    @Override
    public void destroy() {
        for (Drawable bullet : TagManager.getAllWithTag(Tags.Bullet)) {
            bullet.destroy(gameScene);
        }

        bulletCount = 0;
    }
}
