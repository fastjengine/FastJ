package tech.fastj.example.bullethell.scenes;

import tech.fastj.engine.FastJEngine;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.Display;
import tech.fastj.graphics.game.Text2D;

import tech.fastj.systems.control.Scene;
import tech.fastj.systems.control.SceneManager;

import java.awt.Color;
import java.awt.Font;

import tech.fastj.example.bullethell.util.SceneNames;

public class LoseScene extends Scene {

    private Text2D loseText;
    private Text2D deathInfo;

    public LoseScene() {
        super(SceneNames.LoseSceneName);
    }

    @Override
    public void load(Display display) {
        GameScene gameScene = FastJEngine.<SceneManager>getLogicManager().getScene(SceneNames.GameSceneName);
        int waveNumber = gameScene.getWaveNumber();

        loseText = new Text2D("You Lost...", new Pointf(300f, 375f))
                .setFont(new Font("Consolas", Font.PLAIN, 96))
                .setColor(Color.red);
        deathInfo = new Text2D("You died on wave: " + waveNumber, new Pointf(500f, 400f))
                .setFont(new Font("Consolas", Font.PLAIN, 16));

        drawableManager.addGameObject(loseText);
        drawableManager.addGameObject(deathInfo);
    }

    @Override
    public void unload(Display display) {
        loseText.destroy(this);
        deathInfo.destroy(this);
    }

    @Override
    public void update(Display display) {

    }
}
