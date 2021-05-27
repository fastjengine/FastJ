package tech.fastj.example.oldhelloworld;

import tech.fastj.graphics.Display;

import tech.fastj.systems.control.SceneManager;

import java.awt.RenderingHints;

import tech.fastj.example.oldhelloworld.scenes.GameScene;

/**
 * Manages the game's overall state (in the background).
 * <p>
 * A {@code LogicManager} is used to setup scenes, display settings, etc.
 */
public class GameManager extends SceneManager {

    /**
     * Sets up the game manager with the needed scenes, display settings, etc.
     *
     * @param display The {@code Display} that the game renders to.
     */
    @Override
    public void init(Display display) {
        /* Globally enables anti-aliasing for all objects in the game. */
        display.modifyRenderSettings(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        /* Our scene! */
        GameScene k = new GameScene("Game");
        /* We need to add the scene... */
        this.addScene(k);
        /* ...set it as the current scene of the logic manager... */
        this.setCurrentScene(k);
        /* ...and finally tell the logic manager to load our scene. */
        this.loadCurrentScene();
    }
}
