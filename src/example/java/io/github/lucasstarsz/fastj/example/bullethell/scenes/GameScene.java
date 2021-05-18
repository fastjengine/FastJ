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

public class GameScene extends Scene {

    private Model2D player;
    private Text2D playerStats;


    public GameScene() {
        super("Game Scene");
    }

    @Override
    public void load(Display display) {
        playerStats = new Text2D("Rotation: 0.0, Location: Pointf(0f, 0f)", new Pointf(25f));
        playerStats.addAsGameObject(this);

        Pointf centerOfDisplay = display.getInternalResolution().asPointf().divide(2f);

        Pointf[] bodyMesh = DrawUtil.createBox(Pointf.subtract(centerOfDisplay, new Pointf(30f)), 60f);
        Polygon2D playerBody = new Polygon2D(bodyMesh);

        Pointf[] cannonMesh = DrawUtil.createBox(Pointf.subtract(centerOfDisplay, new Pointf(10f, 50f)), new Pointf(20f, 50f));
        Polygon2D playerCannon = new Polygon2D(cannonMesh, Color.red, true, true);

        player = new Model2D(new Polygon2D[]{playerBody, playerCannon});


        player.addBehavior(new PlayerController(5f, 3f, playerStats), this);
        player.addBehavior(new Cannon(this), this);
        player.addAsGameObject(this);
    }

    @Override
    public void unload(Display display) {
        player = null;
        playerStats = null;
    }

    @Override
    public void update(Display display) {

    }
}
