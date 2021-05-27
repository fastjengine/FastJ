package tech.fastj.engine.internals;

/**
 * Timer that accurately specifies time.
 * <p>
 * This class is based on Antonio Hernández Bejarano's Timer class: https://ahbejarano.gitbook.io/lwjglgamedev/
 * <p>
 * Didn't make too many changes.
 *
 * @author Andrew Dey
 * @version 1.0.0
 */
public class Timer {

    private double lastLoopTime;

    /** Initializes the Timer. */
    public void init() {
        lastLoopTime = getTime();
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
     * Re-evaluates the last loop time, then returns the time passed since the last loop.
     *
     * @return The time elapsed since the last loop time.
     */
    public float getElapsedTime() {
        double time = getTime();
        float elapsedTime = (float) (time - lastLoopTime);
        lastLoopTime = time;
        return elapsedTime;
    }

    /**
     * Gets the last loop time.
     *
     * @return The last loop time.
     */
    public double getLastLoopTime() {
        return lastLoopTime;
    }
}
