package io.github.lucasstarsz.fastj.example.scenes;

import io.github.lucasstarsz.fastj.engine.FastJEngine;
import io.github.lucasstarsz.fastj.math.Pointf;

import io.github.lucasstarsz.fastj.graphics.DrawUtil;
import io.github.lucasstarsz.fastj.graphics.game.GameObject;
import io.github.lucasstarsz.fastj.graphics.game.Polygon2D;
import io.github.lucasstarsz.fastj.graphics.ui.UIElement;
import io.github.lucasstarsz.fastj.graphics.ui.elements.Button;

import io.github.lucasstarsz.fastj.systems.behaviors.Behavior;
import io.github.lucasstarsz.fastj.systems.control.Scene;
import io.github.lucasstarsz.fastj.graphics.Display;

import io.github.lucasstarsz.fastj.example.customscripts.PlayerScript;

import java.awt.Color;
import java.awt.Font;

/** The game scene, where all the action happens! */
public class GameScene extends Scene {

    private GameObject player;
    private UIElement button;

    /**
     * Constructs a scene with the specified name.
     *
     * @param setName The name to set the scene's name to.
     */
    public GameScene(String setName) {
        super(setName);
    }

    @Override
    public void load(Display display) {
        // Creating the player

        /* Our player needs a mesh of points that define what it looks like on the screen -- we'll create that here
         * using a helper class called DrawUtil. DrawUtil contains many different methods that help you along in
         * creating simpler meshes of points.
         *
         * In this case, we're using the `createBox` method that creates an array of four points in the shape of a
         * square. It takes three parameters:
         * - an x value (defines the x location of our square)
         * - a y value (defines the y location of our square)
         * - and a size value, defining the resulting size of our square. */
        Pointf[] boxPoints = DrawUtil.createBox(0f, 0f, 50f);

        /* Next up, we're working with a special feature of FastJ: behaviors! A Behavior is a specific set of code that
         * operates on a game object (like the player we're creating), allowing you to create custom code that only gets
         * executed on the game object it is attached to.
         *
         * For our player, we're going to employ the use of a PlayerScript included in the example code. This script
         * only works with player movement, but it is sufficient for now. Here, we create an instance of that script to
         * use for later. */
        PlayerScript playerScript = new PlayerScript(5f);

        /* We're also going to add another script to our player -- this is a default script included in FastJ itself --
         * it's in the game library's code! It operates on the game object by rotating it however many degrees we
         * specify. For now, one degree of rotation will do. */
        Behavior rotationScript = Behavior.simpleRotation(1f);

        /* With all that out of the way, we can create our player! */
        player = new Polygon2D(boxPoints) // the Polygon2D class is for game objects that are polygons, so it makes sense to use for our square player.
                .setColor(Color.RED) // Just to show it off, we'll set the player's color to red.
                .addBehavior(playerScript, this) // Then, we'll add our two behaviors -- the player script...
                .addBehavior(rotationScript, this) // ...and the rotation script. The second param is the scene itself -- read the docs for more info.
                .addAsGameObject(this); // and lastly, we need to add the game object to the scene.
        /* And that's it! */

        // Creating the button

        /* First, we'll want to set the button's location... */
        Pointf buttonLocation = new Pointf(100f, 100f);
        /* ...give the button a definitive size... */
        Pointf buttonSize = new Pointf(100f, 30f);
        /* ...and change the default font to our own. */
        Font buttonFont = new Font("Segoe UI", Font.PLAIN, 20);

        /* Now, we can create our button! */
        button = new Button(this, buttonLocation, buttonSize) // creates the initial instance of the button
                .setText("Click me") // sets the button's text
                .setFont(buttonFont) // sets the button's font
                .setOnAction(action -> FastJEngine.log("I'm a button! You've clicked me!"));
        /* And in the `setOnAction` method above, we set up an action for when the left mouse button clicks the button
         * we created. In this case, the game engine logs the message: "I'm a button! You've clicked me!"
         *
         * `FastJEngine.log` is this game engine's way of logging information, like you would with `System.out.println`. */
    }

    /**
     * When the scene unloads (this happens whenever we switch scenes or the game gets exited) we need to unload, or
     * destroy, the components of the scene.
     *
     * @param display The {@code Display} that the game renders to.
     */
    @Override
    public void unload(Display display) {
        player.destroy(this);
        button.destroy(this);
    }

    /**
     * One way we can update the scene's state.
     *
     * @param display The {@code Display} that the game renders to.
     */
    @Override
    public void update(Display display) {
    }
}
