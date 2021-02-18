package io.github.lucasstarsz.fastj.game;

import io.github.lucasstarsz.fastj.framework.render.Display;
import io.github.lucasstarsz.fastj.framework.systems.game.LogicManager;

import io.github.lucasstarsz.fastj.game.scenes.GameScene;

import java.awt.RenderingHints;

public class GameManager extends LogicManager {

    @Override
    public void setup(Display display) {
        display.modifyRenderSettings(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GameScene k = new GameScene("Game");
        this.addScene(k);
        this.setCurrentScene(k);
        this.loadCurrentScene();
    }
}
