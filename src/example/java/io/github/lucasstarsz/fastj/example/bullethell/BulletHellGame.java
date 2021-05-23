package io.github.lucasstarsz.fastj.example.bullethell;

import io.github.lucasstarsz.fastj.engine.FastJEngine;
import io.github.lucasstarsz.fastj.graphics.Display;

import io.github.lucasstarsz.fastj.systems.control.SceneManager;

import java.awt.Color;

import io.github.lucasstarsz.fastj.example.bullethell.scenes.GameScene;
import io.github.lucasstarsz.fastj.example.bullethell.scenes.LoseScene;

public class BulletHellGame extends SceneManager {

    @Override
    public void init(Display display) {
        GameScene gameScene = new GameScene();
        this.addScene(gameScene);
        this.setCurrentScene(gameScene);
        this.loadCurrentScene();

        LoseScene loseScene = new LoseScene();
        this.addScene(loseScene);

        display.setBackgroundColor(Color.lightGray);
        display.showFPSInTitle(true);
        display.getJFrame().setResizable(false);
    }

    public static void main(String[] args) {
        FastJEngine.init("Simple Bullet Hell", new BulletHellGame());
        FastJEngine.run();
    }
}
