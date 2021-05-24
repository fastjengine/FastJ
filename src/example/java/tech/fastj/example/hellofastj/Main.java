package tech.fastj.example.hellofastj;

import tech.fastj.engine.FastJEngine;
import tech.fastj.graphics.Display;

import tech.fastj.systems.control.SimpleManager;

public class Main extends SimpleManager {

    @Override
    public void init(Display display) {
        // Empty -- this example does not make use of this method.
    }

    @Override
    public void update(Display display) {
        // Empty -- this example does not make use of this method.
    }

    public static void main(String[] args) {
        /* Hello, FastJ! */

        /* Welcome, travelers. it seems you've wandered far enough off the beaten path to find this
         * game engine. For that, I thank you for coming and I commend your efforts in finding us.
         * My name is Andrew, and I'll be your guide through these examples. It's a pleasure to
         * meet you!
         *
         * Introductions aside, FastJ's very simple to start off with. This entire source file is
         * all you'd need in order to create an empty screen for FastJ.
         *
         * "FastJEngine#init" initializes the game engine with a name (String) and a logic manager.
         * The name is fairly self-explanatory, and the logic manager is the class used to handle
         * all of the things you'd expect a game application to have:
         *
         * - Loading the Game Logic (handled in the "init" method)
         * - Receiving/Processing Input
         * - Updating the Game (handled in the "update" method)
         * - Rendering the Game
         * - Resetting the Game
         *
         * Note: This example has the Main class (this source file) also be a SimpleManager,
         * which is a type of LogicManager where you handle all the game loading and updating in
         * the manager itself.
         * As such, it's very easy to add an application entrypoint (the main method) here,
         * allowing us to contain an entire game in one source file. */

        FastJEngine.init("Hello, FastJ!", new Main());
        FastJEngine.run();
    }
}
