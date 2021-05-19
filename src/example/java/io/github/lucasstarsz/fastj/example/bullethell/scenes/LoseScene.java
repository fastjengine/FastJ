package io.github.lucasstarsz.fastj.example.bullethell.scenes;

import io.github.lucasstarsz.fastj.engine.FastJEngine;
import io.github.lucasstarsz.fastj.math.Pointf;
import io.github.lucasstarsz.fastj.graphics.Display;
import io.github.lucasstarsz.fastj.graphics.game.Text2D;

import io.github.lucasstarsz.fastj.systems.control.Scene;

import java.awt.Color;
import java.awt.Font;

import io.github.lucasstarsz.fastj.example.bullethell.util.SceneNames;

public class LoseScene extends Scene {

    private Text2D loseText;
    private Text2D deathInfo;

    public LoseScene() {
        super(SceneNames.LoseSceneName);
    }

    @Override
    public void load(Display display) {
        GameScene gameScene = (GameScene) FastJEngine.getLogicManager().getScene(SceneNames.GameSceneName);
        int waveNumber = gameScene.getWaveNumber();

        loseText = new Text2D("You Lost...", new Pointf(300f, 375f))
                .setFont(new Font("Consolas", Font.PLAIN, 96))
                .setColor(Color.red);
        deathInfo = new Text2D("You died on wave: " + waveNumber, new Pointf(500f, 400f))
                .setFont(new Font("Consolas", Font.PLAIN, 16));

        loseText.addAsGameObject(this);
        deathInfo.addAsGameObject(this);
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
