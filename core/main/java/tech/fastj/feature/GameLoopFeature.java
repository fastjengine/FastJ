package tech.fastj.feature;

import tech.fastj.App;

/**
 * A general implementation requirement for a feature which contains some sort of game loop, or otherwise
 * event-based/time-based updating system which may not close for a long time.
 * <p>
 * First and foremost, the game loop feature should provide a looping mechanism to allow for event polling or other
 * related things -- {@link #gameLoop(App)}. This will be run on a separate thread.
 * <p>
 * The {@link App} will consider a loop to have "finished" when the {@link #gameLoop(App)} method returns.
 */
public interface GameLoopFeature extends Feature {

    void gameLoop(App app);
}
