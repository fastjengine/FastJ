package tech.fastj.example.bullethell;

import tech.fastj.engine.FastJEngine;
import tech.fastj.graphics.display.Display;

import tech.fastj.systems.control.SceneManager;

import java.awt.Color;

import tech.fastj.example.bullethell.scenes.GameScene;
import tech.fastj.example.bullethell.scenes.LoseScene;

public class Main extends SceneManager {

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
        FastJEngine.init("Simple Bullet Hell", new Main());
        FastJEngine.run();
    }
}
