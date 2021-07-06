package tech.fastj.example.bullethell.scenes;

import tech.fastj.engine.FastJEngine;
import tech.fastj.math.Maths;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.Display;
import tech.fastj.graphics.Transform2D;
import tech.fastj.graphics.game.GameObject;
import tech.fastj.graphics.game.Model2D;
import tech.fastj.graphics.game.Polygon2D;
import tech.fastj.graphics.game.Text2D;
import tech.fastj.graphics.util.DrawUtil;
import tech.fastj.graphics.util.ModelUtil;

import tech.fastj.systems.control.Scene;
import tech.fastj.systems.input.keyboard.KeyboardActionListener;
import tech.fastj.systems.input.keyboard.Keys;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import tech.fastj.example.bullethell.scripts.EnemyMovement;
import tech.fastj.example.bullethell.scripts.PlayerCannon;
import tech.fastj.example.bullethell.scripts.PlayerController;
import tech.fastj.example.bullethell.scripts.PlayerHealthBar;
import tech.fastj.example.bullethell.util.FilePaths;
import tech.fastj.example.bullethell.util.SceneNames;
import tech.fastj.example.bullethell.util.Tags;

public class GameScene extends Scene {

    private Model2D player;
    private Text2D playerMetadata;
    private Polygon2D playerHealthBar;

    private Map<String, Model2D> enemies;
    private int enemyCount = 0;
    private int wave = 0;

    public GameScene() {
        super(SceneNames.GameSceneName);
    }

    @Override
    public void load(Display display) {
        playerMetadata = createPlayerMetaData();
        playerHealthBar = createPlayerHealthBar();
        PlayerHealthBar playerHealthBarScript = new PlayerHealthBar(playerMetadata, this);
        playerHealthBar.addBehavior(playerHealthBarScript, this)
                .<GameObject>addTag(Tags.PlayerHealthBar, this);


        PlayerController playerControllerScript = new PlayerController(5f, 3f);
        PlayerCannon playerCannonScript = new PlayerCannon(this);
        player = createPlayer();
        player.addBehavior(playerControllerScript, this)
                .addBehavior(playerCannonScript, this)
                .<GameObject>addTag(Tags.Player, this);


        // add game objects to the screen in order!
        drawableManager.addGameObject(player);
        drawableManager.addGameObject(playerHealthBar);
        drawableManager.addGameObject(playerMetadata);


        enemies = new HashMap<>();
        newWave();

        inputManager.addKeyboardActionListener(new KeyboardActionListener() {
            @Override
            public void onKeyRecentlyPressed(KeyEvent keyEvent) {
                switch (keyEvent.getKeyCode()) {
                    case Keys.Q: {
                        FastJEngine.log("current bullet count: " + playerCannonScript.getBulletCount());
                        break;
                    }
                    case Keys.P: {
                        FastJEngine.log("current enemy count: " + enemies.size());
                        break;
                    }
                    case Keys.L: {
                        FastJEngine.log("printing enemy statuses...");
                        enemies.values().forEach(FastJEngine::log);
                    }
                }
            }
        });
    }

    @Override
    public void unload(Display display) {
        if (player != null) {
            player.destroy(this);
            player = null;
        }

        if (playerMetadata != null) {
            playerMetadata.destroy(this);
            playerMetadata = null;
        }

        if (playerHealthBar != null) {
            playerHealthBar.destroy(this);
            playerHealthBar = null;
        }

        if (enemies != null) {
            enemies.forEach((id, enemy) -> enemy.destroy(this));
            enemies.clear();
        }

        enemyCount = 0;
    }

    @Override
    public void update(Display display) {
    }

    public void enemyDied(Model2D enemy) {
        if (enemies.remove(enemy.getID()) == enemy) {
            enemy.destroy(this);
            enemyCount--;

            if (enemyCount == 0) {
                newWave();
            }
        }
    }

    public int getWaveNumber() {
        return wave;
    }

    private Text2D createPlayerMetaData() {
        return Text2D.create("Health: 100")
                .withFont(new Font("Consolas", Font.BOLD, 16))
                .withTransform(new Pointf(27.5f, 40f), Transform2D.DefaultRotation, Transform2D.DefaultScale)
                .build();
    }

    private Polygon2D createPlayerHealthBar() {
        Pointf playerHealthBarMeshLocation = new Pointf(25f, 40f);
        Pointf playerHealthBarMeshSize = new Pointf(100f, 20f);
        Pointf[] playerHealthBarMesh = DrawUtil.createBox(playerHealthBarMeshLocation, playerHealthBarMeshSize);

        return Polygon2D.create(playerHealthBarMesh)
                .withFill(Color.green)
                .build();
    }

    private Model2D createPlayer() {
        return Model2D.fromPolygons(ModelUtil.loadModel(Path.of(FilePaths.PathToResources + "player.psdf")));
    }

    private void newWave() {
        wave++;
        enemyCount = calculateEnemyCount(wave);
        for (int i = 0; i < enemyCount; i++) {
            Model2D enemy = createEnemy();
            enemy.initBehaviors();
            enemies.put(enemy.getID(), enemy);
        }

        FastJEngine.log("New wave of " + enemyCount + " enemies!");
    }

    private int calculateEnemyCount(int wave) {
        return (int) Math.max(wave * (Math.sqrt(wave) / 2), 3);
    }

    private Model2D createEnemy() {
        Pointf randomPosition = new Pointf(
                Maths.random(-500f, 1780f),
                Maths.randomAtEdge(Maths.random(-500f, -250f), Maths.random(970f, 1220f))
        );

        Model2D enemy = Model2D.fromPolygons(ModelUtil.loadModel(Path.of(FilePaths.PathToResources + "enemy.psdf")));
        enemy.addBehavior(new EnemyMovement(this), this);
        enemy.setTranslation(randomPosition);
        drawableManager.addGameObject(enemy);
        return enemy;
    }
}
