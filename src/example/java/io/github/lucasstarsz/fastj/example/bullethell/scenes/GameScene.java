package io.github.lucasstarsz.fastj.example.bullethell.scenes;

import io.github.lucasstarsz.fastj.engine.FastJEngine;
import io.github.lucasstarsz.fastj.math.Maths;
import io.github.lucasstarsz.fastj.math.Pointf;
import io.github.lucasstarsz.fastj.graphics.Display;
import io.github.lucasstarsz.fastj.graphics.DrawUtil;
import io.github.lucasstarsz.fastj.graphics.game.GameObject;
import io.github.lucasstarsz.fastj.graphics.game.Model2D;
import io.github.lucasstarsz.fastj.graphics.game.Polygon2D;
import io.github.lucasstarsz.fastj.graphics.game.Text2D;

import io.github.lucasstarsz.fastj.systems.control.Scene;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import io.github.lucasstarsz.fastj.example.bullethell.scripts.PlayerCannon;
import io.github.lucasstarsz.fastj.example.bullethell.scripts.EnemyMovement;
import io.github.lucasstarsz.fastj.example.bullethell.scripts.PlayerController;
import io.github.lucasstarsz.fastj.example.bullethell.scripts.PlayerHealthBar;
import io.github.lucasstarsz.fastj.example.bullethell.util.Tags;

public class GameScene extends Scene {

    private Model2D player;
    private Text2D playerMetadata;
    private Polygon2D playerHealthBar;

    private List<Model2D> enemies;
    private int enemyCount = 0;
    private int wave = 0;

    public GameScene() {
        super("Game Scene");
    }

    @Override
    public void load(Display display) {
        Pointf centerOfDisplay = display.getInternalResolution().asPointf().divide(2f);


        playerMetadata = createPlayerMetaData();
        playerMetadata.addAsGameObject(this);


        PlayerHealthBar playerHealthBarScript = new PlayerHealthBar(playerMetadata);
        playerHealthBar = createPlayerHealthBar();
        playerHealthBar.addBehavior(playerHealthBarScript, this)
                .<GameObject>addTag(Tags.PlayerHealthBar, this)
                .addAsGameObject(this);


        PlayerController playerControllerScript = new PlayerController(5f, 3f);
        PlayerCannon playerCannonScript = new PlayerCannon(this);
        player = createPlayer(centerOfDisplay);
        player.addBehavior(playerControllerScript, this)
                .addBehavior(playerCannonScript, this)
                .<GameObject>addTag(Tags.Player, this)
                .addAsGameObject(this);

        enemies = new ArrayList<>();
        newWave();
    }

    @Override
    public void unload(Display display) {
        player.destroy(this);
        player = null;

        playerMetadata.destroy(this);
        playerMetadata = null;

        playerHealthBar.destroy(this);
        playerHealthBar = null;

        enemies.forEach(enemy -> enemy.destroy(this));
        enemies.clear();
        enemies = null;

        enemyCount = 0;
        wave = 0;
    }

    @Override
    public void update(Display display) {
    }

    public void enemyDied(GameObject enemy) {
        if (enemies.remove((Model2D) enemy)) {
            FastJEngine.log(enemies.size());
            enemy.destroy(this);
            enemyCount--;

            if (enemyCount == 0) {
                newWave();
                FastJEngine.log(enemies.size());
            }
        }
    }

    private Text2D createPlayerMetaData() {
        return new Text2D("Health: 100", new Pointf(25f));
    }

    private Polygon2D createPlayerHealthBar() {
        Pointf playerHealthBarMeshLocation = new Pointf(25f, 40f);
        Pointf playerHealthBarMeshSize = new Pointf(100f, 20f);
        Pointf[] playerHealthBarMesh = DrawUtil.createBox(playerHealthBarMeshLocation, playerHealthBarMeshSize);

        return new Polygon2D(playerHealthBarMesh, Color.green, true, true);
    }

    private Model2D createPlayer(Pointf centerOfDisplay) {
        Pointf[] bodyMesh = DrawUtil.createBox(Pointf.subtract(centerOfDisplay, new Pointf(30f)), 60f);
        Polygon2D playerBody = new Polygon2D(bodyMesh);

        Pointf[] cannonMesh = DrawUtil.createBox(Pointf.subtract(centerOfDisplay, new Pointf(10f, 50f)), new Pointf(20f, 50f));
        Polygon2D playerCannon = new Polygon2D(cannonMesh, Color.red.darker(), true, true);

        Pointf[] cannonConnectionMesh = {
                Pointf.subtract(centerOfDisplay, new Pointf(10f, 25f)),
                Pointf.subtract(centerOfDisplay, new Pointf(15f, 20f)),
                Pointf.subtract(centerOfDisplay, new Pointf(15f, 0f)),

                Pointf.add(centerOfDisplay, new Pointf(-5f, 15f)),
                Pointf.add(centerOfDisplay, new Pointf(5f, 15f)),

                Pointf.add(centerOfDisplay, new Pointf(15f, 0f)),
                Pointf.add(centerOfDisplay, new Pointf(15f, -20f)),
                Pointf.add(centerOfDisplay, new Pointf(10f, -25f))
        };
        Polygon2D playerCannonConnection = new Polygon2D(cannonConnectionMesh, Color.red.darker().darker(), true, true);

        return new Model2D(new Polygon2D[]{playerBody, playerCannon, playerCannonConnection});
    }

    private void newWave() {
        wave++;
        enemyCount = calculateEnemyCount(wave);
        for (int i = 0; i < enemyCount; i++) {
            Model2D enemy = createEnemy();
            enemy.initBehaviors();
            enemies.add(enemy);
        }

        FastJEngine.log("New wave of " + enemyCount + " enemies!");
    }

    private int calculateEnemyCount(int wave) {
        return (int) Math.max(wave * (Math.sqrt(wave) / 2), 3);
    }

    private Model2D createEnemy() {
        Pointf[] enemyBodyMesh = DrawUtil.createBox(0f, 0f, 50f);
        Polygon2D enemyBody = new Polygon2D(enemyBodyMesh, Color.cyan, true, true);

        Pointf[] enemyLeftDetailMesh = {
                new Pointf(10f, 5f),
                new Pointf(20f, 25f),
                new Pointf(10f, 25f)
        };
        Polygon2D enemyLeftDetail = new Polygon2D(enemyLeftDetailMesh);

        Pointf[] enemyRightDetailMesh = {
                new Pointf(40f, 45f),
                new Pointf(30f, 25f),
                new Pointf(40f, 25f)
        };
        Polygon2D enemyRightDetail = new Polygon2D(enemyRightDetailMesh);

        Polygon2D[] enemyParts = {
                enemyBody,
                enemyLeftDetail,
                enemyRightDetail
        };
        Pointf randomPosition = new Pointf(
                Maths.random(-500f, 1780f),
                Maths.randomAtEdge(-500f, 1220f)
        );

        return (Model2D) new Model2D(enemyParts)
                .setTranslation(randomPosition)
                .addBehavior(new EnemyMovement(this), this)
                .addAsGameObject(this);
    }
}
