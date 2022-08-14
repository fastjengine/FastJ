package tech.fastj.examples.bullethell.scenes;

import tech.fastj.engine.FastJEngine;
import tech.fastj.examples.bullethell.util.SceneNames;
import tech.fastj.graphics.display.FastJCanvas;
import tech.fastj.graphics.game.Text2D;
import tech.fastj.math.Pointf;
import tech.fastj.math.Transform2D;
import tech.fastj.systems.control.Scene;
import tech.fastj.systems.control.SceneManager;

import java.awt.Color;
import java.awt.Font;

public class LoseScene extends Scene {

    public LoseScene() {
        super(SceneNames.LoseSceneName);
    }

    @Override
    public void load(FastJCanvas canvas) {
        GameScene gameScene = FastJEngine.<SceneManager>getLogicManager().getScene(SceneNames.GameSceneName);
        int waveNumber = gameScene.getWaveNumber();

        Text2D loseText = Text2D.create("You Lost...")
            .withFill(Color.red)
            .withFont(new Font("Consolas", Font.PLAIN, 96))
            .withTransform(new Pointf(300f, 375f), Transform2D.DefaultRotation, Transform2D.DefaultScale)
            .build();
        Text2D deathInfo = Text2D.create("You died on wave: " + waveNumber)
            .withFont(new Font("Consolas", Font.PLAIN, 16))
            .withTransform(new Pointf(500f, 400f), Transform2D.DefaultRotation, Transform2D.DefaultScale)
            .build();

        drawableManager().addGameObject(loseText);
        drawableManager().addGameObject(deathInfo);
    }
}
