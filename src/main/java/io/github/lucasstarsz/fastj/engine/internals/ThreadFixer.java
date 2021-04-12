package io.github.lucasstarsz.fastj.engine.internals;

/**
 * Class to create a daemon thread that fixes inaccurate sleep time on Windows computers.
 *
 * @author Andrew Dey
 * @version 1.0.0
 */
public class ThreadFixer {
    private static boolean IsDaemonRunning;

    /** Starts a daemon thread, if one has not already been started. */
    public static void start() {
        if (!IsDaemonRunning && System.getProperty("os.name").startsWith("Win")) {
            IsDaemonRunning = true;

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
