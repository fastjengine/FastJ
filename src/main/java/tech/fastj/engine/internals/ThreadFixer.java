package tech.fastj.engine.internals;

import tech.fastj.engine.FastJEngine;

import java.util.Arrays;

/**
 * Class that fixes inaccurate sleep time on Windows computers.
 *
 * @author Andrew Dey
 * @since 1.0.0
 */
public class ThreadFixer {
  private static boolean isDaemonRunning;

  private ThreadFixer() {
    throw new java.lang.IllegalStateException();
  }

  /** Starts a daemon thread, if one has not already been started. */
  public static void start() {
    if (!isDaemonRunning && System.getProperty("os.name").startsWith("Win")) {
      isDaemonRunning = true;

      Thread dThread =
          new Thread(
              () -> {
                while (true) {
                  try {
                    Thread.sleep(Long.MAX_VALUE);
                  } catch (InterruptedException exception) {
                    FastJEngine.warning(
                        "Interrupted while in daemon sleep: "
                            + exception.getMessage()
                            + Arrays.deepToString(exception.getStackTrace()));
                    Thread.currentThread().interrupt();
                  }
                }
              });
      dThread.setDaemon(true);
      dThread.start();
    }
  }
}
