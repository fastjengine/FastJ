package tech.fastj.example.keyboard;

import tech.fastj.engine.FastJEngine;
import tech.fastj.graphics.display.Display;

import tech.fastj.systems.control.SimpleManager;

import tech.fastj.input.keyboard.Keyboard;
import tech.fastj.input.keyboard.KeyboardActionListener;
import tech.fastj.input.keyboard.KeyboardStateEvent;
import tech.fastj.input.keyboard.KeyboardTypedEvent;
import tech.fastj.input.keyboard.Keys;

public class Main extends SimpleManager {

    @Override
    public void init(Display display) {
        /* Keyboard */

        /* Keyboard controls in FastJ can be achieved through a few different methods.
         * - Creating a KeyboardActionListener to do actions when a key is pressed/released/etc
         *     - key listeners can be added to the manager at any time. With that said, most use cases would add the
         *       listener when the game is being loaded.
         * - Polling the Keyboard class directly for key state
         *     - This tactic works best in methods called often -- such as the update method.
         *
         * For this example, we'll work with the KeyboardActionListener in the init method, and working with polling in
         * the update method. */


        /* KeyboardActionListener */

        /* The KeyboardActionListener class is designed to set up actions depending on keyboard actions from the player.
         * It has 4 main methods:
         *
         * - onKeyDown: this method is called when a key is held down.
         * - onKeyRecentlyPressed: this method is called when a key is first pressed.
         * - onKeyReleased: this method is called when a key is released.
         * - onKeyTyped: this method is called when a key is typed -- first pressed or held down.
         *
         * To demonstrate each method, I've chosen to log whenever any of the methods is called. Run the program to see
         * this in action. */

        inputManager.addKeyboardActionListener(new KeyboardActionListener() {
            @Override
            public void onKeyDown() {
                FastJEngine.log("Key(s) held down");
            }

            @Override
            public void onKeyRecentlyPressed(KeyboardStateEvent keyEvent) {
                FastJEngine.log("Key " + keyEvent.getKeyName() + " pressed.");
            }

            @Override
            public void onKeyReleased(KeyboardStateEvent keyEvent) {
                FastJEngine.log("Key " + keyEvent.getKeyName() + " released.");
            }

            @Override
            public void onKeyTyped(KeyboardTypedEvent keyEvent) {
                FastJEngine.log("Key " + keyEvent.getKeyName() + " typed.");
            }
        });
    }

    @Override
    public void update(Display display) {
        /* Polling Keyboard for key state */

        /* The Keyboard class has a plethora of methods for checking a key's state.
         *
         * - Keyboard#isKeyDown: check whether a key is held down.
         * - Keyboard#isKeyRecentlyPressed: check whether a key was recently pressed.
         * - Keyboard#isKeyRecentlyReleased: check whether a key was recently released.
         * - Keyboard#getLastKeyPressed: get the string representation of the last key pressed.
         *
         * To demonstrate each of these, I've added if statements to check if the key "W" is held, pressed, released, or is the last key pressed.
         * Furthermore, I've added if statements to demonstrate the following:
         * - the left shift key is held down
         * - the right control key is recently pressed
         * - the numpad 4 key is recently released
         *
         * The Keys class represents the possible keys on the keyboard -- it's what we use to specify keys we want to check the state of.
         * Make sure to use it when checking for a key's state.
         *  */

        if (Keyboard.isKeyDown(Keys.W)) {
            FastJEngine.log("W key is held down");
        }

        if (Keyboard.isKeyRecentlyPressed(Keys.W)) {
            FastJEngine.log("W key was pressed");
        }

        if (Keyboard.isKeyRecentlyReleased(Keys.W)) {
            FastJEngine.log("W key was released");
        }

        if ("W".equals(Keyboard.getLastKeyPressed())) {
            FastJEngine.log("Last key pressed was W");
        }
    }

    public static void main(String[] args) {
        FastJEngine.init("Hello, Keyboard Controls!", new Main());
        FastJEngine.run();
    }
}
