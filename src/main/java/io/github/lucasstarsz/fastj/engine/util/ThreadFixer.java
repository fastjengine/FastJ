package io.github.lucasstarsz.fastj.engine.util;

/**
 * Class to create a daemon thread that fixes inaccurate sleep time on Windows computers.
 *
 * @author Andrew Dey
 * @version 0.3.2a
 */
public class ThreadFixer {
    private static boolean isStarted;

    /** Starts a daemon thread, if one has not already been started. */
    public static void start() {
        if (!isStarted && System.getProperty("os.name").startsWith("Win")) {
            isStarted = true;

            Thread dThread = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(Long.MAX_VALUE);
                    } catch (InterruptedException ignored) {
                    }
                }
            });
            dThread.setDaemon(true);
            dThread.start();
        }
    }
}
