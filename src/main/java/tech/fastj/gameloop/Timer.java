package tech.fastj.gameloop;

/**
 * Timekeeping class, primarily used to track the time between the previous and current game frames.
 * <p>
 * This class is based on Antonio Hernández Bejarano's Timer class:
 * <a href="https://ahbejarano.gitbook.io/lwjglgamedev/">https://ahbejarano.gitbook.io/lwjglgamedev/</a>
 * <p>
 * Didn't make too many changes.
 *
 * @author Andrew Dey
 * @since 1.0.0
 */
public class Timer {

    private double lastTimestamp;
    private float deltaTime;

    /** Initializes the Timer. */
    public void init() {
        lastTimestamp = 0.0;
        deltaTime = 0f;
    }

    /**
     * Gets the current time, in nanoseconds.
     *
     * @return The current time (nanoseconds) as a double.
     */
    public double getCurrentTime() {
        return System.nanoTime() / 1_000_000_000d;
    }

    /**
     * Re-evaluates the last frame time, then returns the time passed since the last time evaluation.
     *
     * @return The time elapsed since the last time evaluation.
     */
    public float evalDeltaTime() {
        double time = getCurrentTime();
        deltaTime = (float) (lastTimestamp == 0 ? 0 : time - lastTimestamp);
        lastTimestamp = time;
        return deltaTime;
    }

    /**
     * @return The time elapsed from the second-to-last to the last time evaluation.
     */
    public float getDeltaTime() {
        return deltaTime;
    }

    /**
     * Gets the time of when a frame was last rendered.
     *
     * @return The time when a frame was last rendered.
     */
    public double getLastTimestamp() {
        return lastTimestamp;
    }
}
