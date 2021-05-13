package io.github.lucasstarsz.fastj.example.bullethell;

import io.github.lucasstarsz.fastj.graphics.Display;

import io.github.lucasstarsz.fastj.systems.control.LogicManager;
import io.github.lucasstarsz.fastj.systems.control.Scene;

import io.github.lucasstarsz.fastj.example.bullethell.scenes.GameScene;

public class GameManager extends LogicManager {

    @Override
    public void setup(Display display) {
        Scene gameScene = new GameScene();
        this.addScene(gameScene);
        this.setCurrentScene(gameScene);
        this.loadCurrentScene();
    }
}
