package tech.fastj.engine.internals;

/**
 * Timer that accurately specifies time.
 * <p>
 * This class is based on Antonio Hernández Bejarano's Timer class: https://ahbejarano.gitbook.io/lwjglgamedev/
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
        lastTimestamp = getTime();
        deltaTime = 0f;
    }

    /**
     * Gets the current time, in nanoseconds.
     *
     * @return The current time (nanoseconds) as a double.
     */
    public double getTime() {
        return System.nanoTime() / 1_000_000_000d;
    }

    /**
     * Re-evaluates the last frame time, then returns the time passed since the last time evaluation.
     *
     * @return The time elapsed since the last time evaluation.
     */
    public float evalDeltaTime() {
        double time = getTime();
        deltaTime = (float) (time - lastTimestamp);
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
