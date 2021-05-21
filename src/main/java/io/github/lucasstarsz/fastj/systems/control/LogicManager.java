package io.github.lucasstarsz.fastj.systems.control;

import io.github.lucasstarsz.fastj.engine.FastJEngine;
import io.github.lucasstarsz.fastj.graphics.Display;

import io.github.lucasstarsz.fastj.systems.input.InputManager;
import io.github.lucasstarsz.fastj.systems.input.keyboard.Keyboard;
import io.github.lucasstarsz.fastj.systems.input.mouse.Mouse;

import java.awt.event.InputEvent;

public interface LogicManager {

    /**
     * Initializes the logic manager.
     * <p>
     * This method is called after the engine has been set up, and the display has been created. As it is only called
     * once, it is the best place to set some initial settings that apply to the entire game.
     *
     * @param display The {@code Display} that the game renders to. Useful for applying display-related settings before
     *                the game starts.
     */
    void init(Display display);

    /**
     * Allows the logic manager to process all pending input events.
     *
     * @see Keyboard
     * @see Mouse
     * @see InputManager
     */
    void processInputEvents();

    /**
     * Allows the logic manager to take in an input event.
     * <p>
     * <b>FOR IMPLEMENTORS:</b> Input events taken in should be stored for later. When {@link #processInputEvents()} is
     * called, it will process all the events stored here.
     *
     * @param inputEvent The event taken in.
     */
    void receivedInputEvent(InputEvent inputEvent);

    /**
     * Allows the logic manager to update its game state once.
     * <p>
     * The {@code FastJEngine} attempts to call this method at most {@code FastJEngine#targetUPS} times a second. This
     * value can be changed using {@link FastJEngine#setTargetUPS(int)}.
     *
     * @param display The {@code Display} that the game renders to. Useful for checking certain attributes of the
     *                display while updating the game state.
     */
    void update(Display display);

    /**
     * Allows the logic manager to render irs game's current state to the screen.
     * <p>
     * The {@code FastJEngine} attempts to call this method at most {@code FastJEngine#targetFPS} times a second. This
     * value can be changed using {@link FastJEngine#setTargetFPS(int)}.
     *
     * @param display The {@code Display} that the game renders to.
     */
    void render(Display display);

    /**
     * Resets the logic manager entirely.
     * <p>
     * This method is called when the engine exits. Due to the game engine's mutability, it is preferred that all
     * resources of the game engine are removed gracefully.
     * <p>
     * <b>FOR IMPLEMENTORS:</b> By the end of this method call, the logic manager should have released all its
     * resources.
     */
    void reset();
}
