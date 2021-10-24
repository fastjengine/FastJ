package tech.fastj.examples.bullethell;

import tech.fastj.engine.FastJEngine;
import tech.fastj.graphics.display.FastJCanvas;
import tech.fastj.graphics.display.SimpleDisplay;
import tech.fastj.logging.LogLevel;

import tech.fastj.systems.control.SceneManager;

import java.awt.Color;

import tech.fastj.examples.bullethell.scenes.GameScene;
import tech.fastj.examples.bullethell.scenes.LoseScene;

public class Main extends SceneManager {

    @Override
    public void init(FastJCanvas canvas) {
        GameScene gameScene = new GameScene();
        this.addScene(gameScene);
        this.setCurrentScene(gameScene);
        this.loadCurrentScene();

        LoseScene loseScene = new LoseScene();
        this.addScene(loseScene);

        SimpleDisplay display = FastJEngine.getDisplay();
        canvas.setBackgroundColor(Color.lightGray);
//        display.showFPSInTitle(true);
        display.getWindow().setResizable(false);
    }

    public static void main(String[] args) {
        FastJEngine.init("Simple Bullet Hell", new Main());
        FastJEngine.configureLogging(LogLevel.Debug);
        FastJEngine.run();
    }
}
