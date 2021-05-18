package io.github.lucasstarsz.fastj.example.bullethell.scripts;

import io.github.lucasstarsz.fastj.engine.FastJEngine;
import io.github.lucasstarsz.fastj.math.Pointf;
import io.github.lucasstarsz.fastj.graphics.DrawUtil;
import io.github.lucasstarsz.fastj.graphics.game.GameObject;
import io.github.lucasstarsz.fastj.graphics.game.Polygon2D;

import io.github.lucasstarsz.fastj.systems.behaviors.Behavior;
import io.github.lucasstarsz.fastj.systems.input.keyboard.Keyboard;
import io.github.lucasstarsz.fastj.systems.input.keyboard.Keys;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.github.lucasstarsz.fastj.example.bullethell.scenes.GameScene;

public class Cannon implements Behavior {

    private final GameScene gameScene;
    private final List<Polygon2D> playerBullets;
    private static final int MaxPlayerBulletCount = 3;

    private static final Pointf BulletSize = new Pointf(5f);

    public Cannon(GameScene scene) {
        gameScene = Objects.requireNonNull(scene);
        playerBullets = new ArrayList<>(MaxPlayerBulletCount);
    }

    @Override
    public void init(GameObject obj) {
        playerBullets.clear();
    }

    @Override
    public void update(GameObject obj) {
        if (Keyboard.isKeyDown(Keys.Space)) {
            FastJEngine.runAfterUpdate(() -> createBullet(obj));
        }
    }

    private void createBullet(GameObject player) {
        Pointf cannonFront = player.getCenter().copy().rotate(player.getRotation(), player.getCenter());
        Pointf[] bulletMesh = DrawUtil.createBox(cannonFront, BulletSize);

        Polygon2D bullet = new Polygon2D(bulletMesh, Color.green, true, true);
        bullet.addBehavior(new BulletMovement(), gameScene);
        bullet.addAsGameObject(gameScene);
    }

    @Override
    public void destroy() {
        playerBullets.clear();
    }
}
