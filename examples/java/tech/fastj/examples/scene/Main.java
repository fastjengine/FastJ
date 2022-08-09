package tech.fastj.examples.scene;

import tech.fastj.engine.FastJEngine;

public class Main {
    public static void main(String[] args) {
        /* Scenes in FastJ */

        /* In FastJ, scenes provide primary support as a way to implement multiple different
         * stages in your game -- levels, distinct menus, and other important parts of your game.
         *
         * All scenes are controlled from a SceneManager.
         * We can create a scene manager by defining an implementation for one, and using it when
         * initializing FastJ.
         *
         * Please read into the other classes, GameManager.java, FirstScene.java, and
         * SecondScene.java, to learn about scene switching.
         */

        GameManager gameManager = new GameManager();
        FastJEngine.init("Scenes in FastJ", gameManager);
        FastJEngine.run();
    }
}
