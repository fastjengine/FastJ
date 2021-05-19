package io.github.lucasstarsz.fastj.example.bullethell.scenes;

import io.github.lucasstarsz.fastj.math.Pointf;
import io.github.lucasstarsz.fastj.graphics.Display;
import io.github.lucasstarsz.fastj.graphics.DrawUtil;
import io.github.lucasstarsz.fastj.graphics.game.Model2D;
import io.github.lucasstarsz.fastj.graphics.game.Polygon2D;
import io.github.lucasstarsz.fastj.graphics.game.Text2D;

import io.github.lucasstarsz.fastj.systems.control.Scene;

import java.awt.Color;

import io.github.lucasstarsz.fastj.example.bullethell.scripts.Cannon;
import io.github.lucasstarsz.fastj.example.bullethell.scripts.PlayerController;
import io.github.lucasstarsz.fastj.example.bullethell.scripts.PlayerHealthBar;

public class GameScene extends Scene {

    private Model2D player;
    private Text2D playerMetadata;
    private Polygon2D playerHealthBar;

    public GameScene() {
        super("Game Scene");
    }

    @Override
    public void load(Display display) {
        Pointf centerOfDisplay = display.getInternalResolution().asPointf().divide(2f);

        playerMetadata = new Text2D("Rotation: 0.0, Location: Pointf{0.0, 0.0}", new Pointf(25f));
        playerMetadata.addAsGameObject(this);

        PlayerController playerControllerScript = new PlayerController(5f, 3f, playerMetadata);
        Cannon playerCannonScript = new Cannon(this);

        player = createPlayer(centerOfDisplay);
        player.addBehavior(playerControllerScript, this)
                .addBehavior(playerCannonScript, this)
                .addAsGameObject(this);

        Pointf playerHealthBarMeshLocation = new Pointf(25f, 40f);
        Pointf playerHealthBarMeshSize = new Pointf(100f, 20f);
        Pointf[] playerHealthBarMesh = DrawUtil.createBox(playerHealthBarMeshLocation, playerHealthBarMeshSize);
        playerHealthBar = (Polygon2D) new Polygon2D(playerHealthBarMesh)
                .setColor(Color.green)
                .addBehavior(new PlayerHealthBar(), this)
                .addAsGameObject(this);
    }

    @Override
    public void unload(Display display) {
        player = null;
        playerMetadata = null;
    }

    @Override
    public void update(Display display) {
    }

    private Model2D createPlayer(Pointf centerOfDisplay) {
        Pointf[] bodyMesh = DrawUtil.createBox(Pointf.subtract(centerOfDisplay, new Pointf(30f)), 60f);
        Polygon2D playerBody = new Polygon2D(bodyMesh);

        Pointf[] cannonMesh = DrawUtil.createBox(Pointf.subtract(centerOfDisplay, new Pointf(10f, 50f)), new Pointf(20f, 50f));
        Polygon2D playerCannon = new Polygon2D(cannonMesh, Color.red.darker(), true, true);

        Pointf[] cannonConnectionMesh = {
                Pointf.subtract(centerOfDisplay, new Pointf(10f, 25f)),
                Pointf.subtract(centerOfDisplay, new Pointf(15f, 20f)),
                Pointf.subtract(centerOfDisplay, new Pointf(15f, 0f)),

                Pointf.add(centerOfDisplay, new Pointf(-5f, 15f)),
                Pointf.add(centerOfDisplay, new Pointf(5f, 15f)),

                Pointf.add(centerOfDisplay, new Pointf(15f, 0f)),
                Pointf.add(centerOfDisplay, new Pointf(15f, -20f)),
                Pointf.add(centerOfDisplay, new Pointf(10f, -25f))
        };
        Polygon2D playerCannonConnection = new Polygon2D(cannonConnectionMesh, Color.red.darker().darker(), true, true);

        return new Model2D(new Polygon2D[]{playerBody, playerCannon, playerCannonConnection});
    }
}
