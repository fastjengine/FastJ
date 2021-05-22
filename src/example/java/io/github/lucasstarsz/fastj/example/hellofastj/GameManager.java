package io.github.lucasstarsz.fastj.example.hellofastj;

import io.github.lucasstarsz.fastj.example.hellofastj.scenes.MainScene;
import io.github.lucasstarsz.fastj.example.helloworld.scenes.GameScene;
import io.github.lucasstarsz.fastj.graphics.Display;
import io.github.lucasstarsz.fastj.systems.control.SceneManager;

import java.awt.*;

public class GameManager extends SceneManager {
    @Override
    public void init(Display display) {
        // Enables anti-aliasing for all objects in the game
        display.modifyRenderSettings(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Create a new instance of our scene
        MainScene scene = new MainScene("Hello, FastJ!");

        // Add the scene to the SceneManager
        this.addScene(scene);

        // Set the scene as the current scene
        this.setCurrentScene(scene);

        // Tell the logic manager to load our scene
        this.loadCurrentScene();
    }
}
