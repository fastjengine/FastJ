package io.github.lucasstarsz.fastj.example.bullethell.scripts;

import io.github.lucasstarsz.fastj.engine.FastJEngine;
import io.github.lucasstarsz.fastj.math.Pointf;
import io.github.lucasstarsz.fastj.graphics.DrawUtil;
import io.github.lucasstarsz.fastj.graphics.game.GameObject;
import io.github.lucasstarsz.fastj.graphics.game.Polygon2D;

import io.github.lucasstarsz.fastj.systems.behaviors.Behavior;
import io.github.lucasstarsz.fastj.systems.input.keyboard.Keyboard;
import io.github.lucasstarsz.fastj.systems.input.keyboard.Keys;

import java.util.Objects;

import io.github.lucasstarsz.fastj.example.bullethell.scenes.GameScene;

public class Cannon implements Behavior {

    private final GameScene gameScene;
    private static final int MaxPlayerBulletCount = 3;

    private static final Pointf BulletSize = new Pointf(5f);

    public Cannon(GameScene scene) {
        gameScene = Objects.requireNonNull(scene);
    }

    @Override
    public void init(GameObject obj) {
    }

    @Override
    public void update(GameObject obj) {
        if (Keyboard.isKeyDown(Keys.Space)) {
            FastJEngine.runAfterUpdate(() -> createBullet(obj));
        }
    }

    private void createBullet(GameObject player) {
        Pointf startingPoint = Pointf.add(player.getCenter(), new Pointf(0f, -50f));
        Pointf cannonFront = Pointf.rotate(startingPoint, 360f - Math.abs(player.getRotation()), player.getCenter());

        Pointf[] bulletMesh = DrawUtil.createBox(cannonFront, BulletSize);
        Polygon2D bullet = new Polygon2D(bulletMesh, DrawUtil.randomColorWithAlpha(), true, true);

        bullet.addBehavior(new BulletMovement(player.getRotation(), gameScene), gameScene);
        bullet.addTag("bullet", gameScene);
        bullet.addAsGameObject(gameScene);
        bullet.initBehaviors();
    }

    @Override
    public void destroy() {
    }
}
