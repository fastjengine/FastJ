package io.github.lucasstarsz.fastj.engine;

import io.github.lucasstarsz.fastj.framework.CrashMessages;
import io.github.lucasstarsz.fastj.framework.render.Display;
import io.github.lucasstarsz.fastj.framework.io.keyboard.Keyboard;
import io.github.lucasstarsz.fastj.framework.io.mouse.Mouse;
import io.github.lucasstarsz.fastj.framework.math.Point;
import io.github.lucasstarsz.fastj.framework.systems.behaviors.BehaviorManager;
import io.github.lucasstarsz.fastj.framework.systems.game.LogicManager;
import io.github.lucasstarsz.fastj.framework.systems.tags.TagManager;

import io.github.lucasstarsz.fastj.engine.internals.ThreadFixer;
import io.github.lucasstarsz.fastj.engine.internals.Timer;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The main control hub of the game engine.
 * <p>
 * This class contains the methods needed to create and start a game using the FastJ Game Engine. Using this, you'll
 * have access to the engine's features in their full force.
 * <p>
 * <br>
 * <a href="https://github.com/lucasstarsz/FastJ-Engine">The FastJ Game Engine</a>
 *
 * @author Andrew Dey
 * @version 1.0.0
 */
public class FastJEngine {

    // default engine values
    public static final int DEFAULT_FPS = Display.getDefaultMonitorRefreshRate();
    public static final int DEFAULT_UPS = 60;
    public static final Point DEFAULT_WINDOW_RESOLUTION = new Point(1280, 720);
    public static final Point DEFAULT_INTERNAL_RESOLUTION = new Point(1280, 720);

    // engine speed variables
    private static int targetFPS;
    private static int targetUPS;

    // FPS counting
    private static Timer timer;
    private static int[] fpsLog;
    private static int drawFrames;
    private static int totalFPS;
    private static int fpsIndex;
    private static ScheduledExecutorService fpsLogger;

    // HW acceleration
    private static HWAccel hwAccel;

    // Display/Logic
    private static Display display;
    private static LogicManager gameManager;

    // Check values
    private static boolean isRunning;

    /**
     * Initializer for the game engine.
     * <p>
     * This initializes the game engine with the specified title and logic manager. Other values are set to their
     * respective default values.
     * <p>
     * The other default values for the game engine are as follows:
     * <ul>
     * 		<li>Default target FPS: The refresh rate of the default monitor.</li>
     * 		<li>Default target UPS: 60 Updates per Second.</li>
     * 		<li>Default window resolution: {@code 1280 * 720 (720p)} </li>
     * 		<li>Default internal game resolution: {@code 1280 * 720 (720p)}</li>
     * 		<li>Default hardware acceleration: {@code HWAccel.DEFAULT}</li>
     * </ul>
     *
     * @param gameTitle   Sets the title of the game window, or Display.
     * @param gameManager LogicManager object that the engine will call methods from; this is where the user's game
     *                    methods are operated from.
     */
    public static void init(String gameTitle, LogicManager gameManager) {
        init(gameTitle, gameManager, DEFAULT_FPS, DEFAULT_UPS, DEFAULT_WINDOW_RESOLUTION, DEFAULT_INTERNAL_RESOLUTION, HWAccel.DEFAULT);
    }

    /**
     * Initializer for the game engine.
     *
     * @param gameTitle            Sets the title of the game window.
     * @param gameManager          Sets the engine's game manager.
     * @param fps                  Value that defines how many times the game should be rendered per second.
     * @param ups                  Value that defines how many times the game should be updated per second.
     * @param windowResolution     Sets the game's window resolution.
     * @param internalResolution   Sets the game's internal resolution. (This is the defined size of the game's canvas.
     *                             As a result, the content is scaled to fit the size of the {@code windowResolution}).
     * @param hardwareAcceleration Defines the type of hardware acceleration to use for the game.
     */
    public static void init(String gameTitle, LogicManager gameManager, int fps, int ups, Point windowResolution, Point internalResolution, HWAccel hardwareAcceleration) {
        runningCheck();

        FastJEngine.gameManager = gameManager;
        display = new Display(gameTitle, windowResolution, internalResolution);
        timer = new Timer();

        fpsLog = new int[100];
        Arrays.fill(fpsLog, -1);

        configure(fps, ups, windowResolution, internalResolution, hardwareAcceleration);
    }

    /**
     * Configures the game's FPS (Frames Per Second), UPS (Updates Per Second), viewer resolution, internal resolution,
     * and hardware acceleration.
     *
     * @param fps                  Value that defines how many times the game should be rendered per second.
     * @param ups                  Value that defines how many times the game should be updated per second.
     * @param windowResolution     Sets the game's window resolution.
     * @param internalResolution   Sets the game's internal resolution.
     * @param hardwareAcceleration Defines the type of hardware acceleration to use for the game.
     */
    public static void configure(int fps, int ups, Point windowResolution, Point internalResolution, HWAccel hardwareAcceleration) {
        runningCheck();

        configureViewerResolution(windowResolution);
        configureInternalResolution(internalResolution);
        configureHardwareAcceleration(hardwareAcceleration);
        setTargetFPS(fps);
        setTargetUPS(ups);
    }

    /**
     * Configures the game's window resolution.
     *
     * @param windowResolution The resolution which the user's window will be set to.
     */
    public static void configureViewerResolution(Point windowResolution) {
        runningCheck();

        if ((windowResolution.x | windowResolution.y) < 1) {
            error(CrashMessages.CONFIGURATION_ERROR.errorMessage, new IllegalArgumentException("Resolution values must be at least 1."));
        }

        display.setViewerResolution(windowResolution);
    }

    /**
     * Configures the game's internal resolution.
     * <p>
     * This sets the size of the game's drawing canvas. As a result, the content displayed on the canvas will be scaled
     * to fit the size of the {@code windowResolution}.
     *
     * @param internalResolution Point value to set the game's internal window resolution.
     */
    public static void configureInternalResolution(Point internalResolution) {
        runningCheck();

        if ((internalResolution.x | internalResolution.y) < 1) {
            error(CrashMessages.CONFIGURATION_ERROR.errorMessage, new IllegalArgumentException("internal resolution values must be at least 1."));
        }

        display.setInternalResolution(internalResolution);
    }

    /**
     * Attempts to set the hardware acceleration type of this game engine to the specified parameter.
     * <p>
     * If the parameter specified is not supported by the user's computer, then the hardware acceleration will be set to
     * none, by default.
     *
     * @param acceleration Defines the type of hardware acceleration to use for the game.
     */
    public static void configureHardwareAcceleration(HWAccel acceleration) {
        runningCheck();

        if (acceleration.equals(HWAccel.DIRECT3D)) {
            if (System.getProperty("os.name").startsWith("Win")) {
                HWAccel.setHardwareAcceleration(HWAccel.DIRECT3D);
                hwAccel = acceleration;
            } else {
                warning("This OS doesn't support Direct3D hardware acceleration. Configuration will be left at default.");
                configureHardwareAcceleration(HWAccel.DEFAULT);
            }
        } else {
            HWAccel.setHardwareAcceleration(acceleration);
            hwAccel = acceleration;
        }
    }

    /**
     * Checks if the engine is currently running. If it is, crash the game.
     */
    public static void runningCheck() {
        if (isRunning) {
            error(CrashMessages.CALLED_AFTER_RUN_ERROR.errorMessage, new IllegalStateException("This method cannot be called after the game begins running."));
        }
    }

    /**
     * Gets the {@link Display} object associated with the game engine.
     *
     * @return The {@link Display} object associated with the game engine.
     */
    public static Display getDisplay() {
        return display;
    }

    /**
     * Gets the logic manager associated with the game engine.
     *
     * @return The {@link io.github.lucasstarsz.fastj.framework.systems.game.LogicManager} object associated with the
     * game engine.
     */
    public static LogicManager getLogicManager() {
        return gameManager;
    }

    /**
     * Gets the hardware acceleration currently enabled for the game engine.
     *
     * @return Returns the HWAccelType that defines what hardware acceleration, or lack thereof, is currently being used
     * for the game engine.
     */
    public static HWAccel getHardwareAcceleration() {
        return hwAccel;
    }

    /**
     * Gets the target FPS for this instance of the game engine.
     *
     * @return The target FPS for this instance of the game engine.
     */
    public static int getTargetFPS() {
        return targetFPS;
    }

    /**
     * Sets the game's target FPS (Frames rendered per second).
     *
     * @param fps Integer value to set the game's target FPS.
     */
    public static void setTargetFPS(int fps) {
        if (fps < 1) {
            error(CrashMessages.CONFIGURATION_ERROR.errorMessage, new IllegalArgumentException("FPS amount must be at least 1."));
        }
        targetFPS = fps;
    }

    /**
     * Gets the target UPS for this instance of the game engine.
     *
     * @return The target UPS for this instance of the game engine.
     */
    public static int getTargetUPS() {
        return targetUPS;
    }

    /**
     * Sets the game's UPS (Updates per second).
     *
     * @param ups Integer value to set the game's target UPS.
     */
    public static void setTargetUPS(int ups) {
        if (ups < 1) {
            error(CrashMessages.CONFIGURATION_ERROR.errorMessage, new IllegalArgumentException("UPS amount must be at least 1."));
        }
        targetUPS = ups;
    }

    /**
     * Gets the value that defines whether the engine is running.
     *
     * @return The boolean that defines whether the engine is running.
     */
    public static boolean isRunning() {
        return isRunning;
    }

    /**
     * Gets the FPS-based value of the parameter specified.
     * <p>
     * The types of information are as follows:
     * <ul>
     * 		<li>FPSValue.CURRENT - gets the last recorded FPS value.</li>
     * 		<li>FPSValue.AVERAGE - gets the average FPS, based on the recorded FPS values.</li>
     * 		<li>FPSValue.HIGHEST - gets the highest recorded FPS value.</li>
     * 		<li>FPSValue.LOWEST - gets the lowest recorded FPS value.</li>
     * 		<li>FPSValue.ONE_PERCENT_LOW - gets the average FPS of the lowest 1% of all recorded FPS values.</li>
     * </ul>
     *
     * @param dataType {@link FPSValue} parameter that specifies the information being requested.
     * @return Double value, based on the information requested.
     */
    public static double getFPSData(FPSValue dataType) {
        int[] validFPSVals = Arrays.copyOfRange(fpsLog, 0, Math.min(fpsLog.length, fpsIndex));

        switch (dataType) {
            case CURRENT:
                return (fpsLog[fpsIndex % 100] != -1) ? fpsLog[fpsIndex % 100] : 0;
            case AVERAGE:
                return (double) totalFPS / (double) fpsIndex;
            case HIGHEST:
                return Arrays.stream(validFPSVals).reduce(Integer::max).orElse(-1);
            case LOWEST:
                return Arrays.stream(validFPSVals).reduce(Integer::min).orElse(-1);
            case ONE_PERCENT_LOW:
                return Arrays.stream(validFPSVals).sorted()
                        .limit(Math.max(1L, (long) (validFPSVals.length * 0.01)))
                        .average().orElse(-1d);
            default:
                throw new IllegalStateException("Unexpected value: " + dataType);
        }
    }

    /**
     * Runs the game.
     */
    public static void run() {
        initEngine();
        gameLoop();
    }

    /**
     * Closes the game.
     */
    public static void closeGame() {
        display.close();
    }

    /**
     * Logs the specified message, using {@code System.out.println}.
     *
     * @param <T>     This allows for any type of message.
     * @param message The message to log.
     */
    public static <T> void log(T message) {
        System.out.println("INFO: " + message);
    }

    /**
     * Logs the specified warning message, using {@code System.err.println}.
     *
     * @param <T>            This allows for any type of warning message.
     * @param warningMessage The warning to log.
     */
    public static <T> void warning(T warningMessage) {
        System.err.println("WARNING: " + warningMessage);
    }

    /**
     * Logs the specified error message, then force closes the game.
     *
     * @param <T>          This allows for any type of error message.
     * @param errorMessage The error to log.
     * @param exception    The exception that caused a need for this method call.
     */
    public static <T> void error(T errorMessage, Exception exception) {
        System.err.println("ERROR: " + errorMessage);
        exception.printStackTrace();
        System.exit(-1);
    }

    /**
     * Initializes the game engine's components.
     */
    private static void initEngine() {
        runningCheck();
        isRunning = true;

        ThreadFixer.start();
        display.init();
        gameManager.setup(display);

        timer.init();
        fpsLogger = Executors.newSingleThreadScheduledExecutor();
        fpsLogger.scheduleWithFixedDelay(() -> {
            FastJEngine.logFPS(drawFrames);
            drawFrames = 0;
        }, 1, 1, TimeUnit.SECONDS);

        System.gc(); // yes, I really gc before starting.
        display.open();
    }

    /**
     * As the heart of the engine, this updates and renders the game.
     */
    private static void gameLoop() {
        float elapsedTime;
        float accumulator = 0f;
        float interval = 1f / targetUPS;

        while (!display.isClosed()) {
            elapsedTime = timer.getElapsedTime();
            accumulator += elapsedTime;

            while (accumulator >= interval) {
                gameManager.update(display);
                accumulator -= interval;
            }

            gameManager.render(display);
            drawFrames++;

            if (!display.isFullscreen()) sync();
        }

        exit();
    }

    /**
     * Syncs the game engine frame rate.
     * <p>
     * This provides a, for lack of better term, "jank" way of emulating V-Sync when the game engine is not running in
     * fullscreen mode.
     */
    private static void sync() {
        final float loopSlot = 1f / targetFPS;
        final double endTime = timer.getLastLoopTime() + loopSlot;
        final double currentTime = timer.getTime();
        if (currentTime < endTime) {
            try {
                TimeUnit.MILLISECONDS.sleep((long) ((endTime - currentTime) * 1000L));
            } catch (InterruptedException ignored) {
            }
        }
    }

    /**
     * Gracefully exits the application.
     */
    private static void exit() {
        isRunning = false;
        fpsLogger.shutdown();
        gameManager.reset();

        Mouse.reset();
        Keyboard.reset();
        BehaviorManager.reset();
        TagManager.reset();

        System.exit(0);
    }

    /**
     * Logs the current frames rendered, displaying it on the {@code Display} if necessary.
     *
     * @param frames The count of frames rendered.
     */
    private static void logFPS(int frames) {
        if (display.isShowingFPSInTitle()) {
            display.setDisplayedTitle(String.format("%s | FPS: %d", display.getTitle(), frames));
        }

        storeFPS(frames);
    }

    /**
     * Stores the specified frames rendered values in the engine's FPS log.
     *
     * @param frames The count of frames rendered.
     */
    private static void storeFPS(int frames) {
        fpsLog[fpsIndex % 100] = frames;
        fpsIndex++;
        totalFPS += frames;
    }
}
