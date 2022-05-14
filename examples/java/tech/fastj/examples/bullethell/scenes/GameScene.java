package tech.fastj.examples.bullethell.scenes;

import tech.fastj.engine.FastJEngine;
import tech.fastj.math.Maths;
import tech.fastj.math.Pointf;
import tech.fastj.math.Transform2D;
import tech.fastj.graphics.display.FastJCanvas;
import tech.fastj.graphics.game.GameObject;
import tech.fastj.graphics.game.Model2D;
import tech.fastj.graphics.game.Polygon2D;
import tech.fastj.graphics.game.Text2D;
import tech.fastj.graphics.util.DrawUtil;

import tech.fastj.input.keyboard.KeyboardActionListener;
import tech.fastj.input.keyboard.events.KeyboardStateEvent;
import tech.fastj.resources.models.ModelUtil;
import tech.fastj.systems.control.Scene;

import java.awt.Color;
import java.awt.Font;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import tech.fastj.examples.bullethell.scripts.EnemyMovement;
import tech.fastj.examples.bullethell.scripts.PlayerCannon;
import tech.fastj.examples.bullethell.scripts.PlayerController;
import tech.fastj.examples.bullethell.scripts.PlayerHealthBar;
import tech.fastj.examples.bullethell.util.FilePaths;
import tech.fastj.examples.bullethell.util.SceneNames;
import tech.fastj.examples.bullethell.util.Tags;

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
    public void load(FastJCanvas canvas) {
        playerMetadata = createPlayerMetaData();
        playerHealthBar = createPlayerHealthBar();
        PlayerHealthBar playerHealthBarScript = new PlayerHealthBar(playerMetadata, this);
        playerHealthBar.addBehavior(playerHealthBarScript, this)
                .<GameObject>addTag(Tags.PlayerHealthBar);


        PlayerController playerControllerScript = new PlayerController(3f, 3f);
        PlayerCannon playerCannonScript = new PlayerCannon(this);
        player = createPlayer();
        player.addBehavior(playerControllerScript, this)
                .addBehavior(playerCannonScript, this)
                .<GameObject>addTag(Tags.Player);


        // add game objects to the screen in order!
        drawableManager.addGameObject(player);
        drawableManager.addGameObject(playerHealthBar);
        drawableManager.addGameObject(playerMetadata);


        enemies = new ConcurrentHashMap<>();
        newWave();

        inputManager.addKeyboardActionListener(new KeyboardActionListener() {
            @Override
            public void onKeyRecentlyPressed(KeyboardStateEvent keyboardStateEvent) {
                switch (keyboardStateEvent.getKey()) {
                    case Q: {
                        FastJEngine.log("current bullet count: " + playerCannonScript.getBulletCount());
                        break;
                    }
                    case P: {
                        FastJEngine.log("current enemy count: " + enemies.size());
                        break;
                    }
                    case L: {
                        FastJEngine.log("printing enemy statuses...");
                        enemies.values().forEach(enemy -> FastJEngine.log("enemy {}: {}", enemy.getID(), enemy.toString()));
                    }
                }
            }
        });
    }

    @Override
    public void unload(FastJCanvas canvas) {
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
    public void fixedUpdate(FastJCanvas canvas) {
    }

    @Override
    public void update(FastJCanvas canvas) {
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
        enemy.addLateBehavior(new EnemyMovement(this), this);
        enemy.setTranslation(randomPosition);
        drawableManager.addGameObject(enemy);
        return enemy;
    }
}
