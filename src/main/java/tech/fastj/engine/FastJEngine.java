package tech.fastj.engine;

import tech.fastj.engine.config.EngineConfig;
import tech.fastj.engine.config.ExceptionAction;
import tech.fastj.engine.internals.ThreadFixer;
import tech.fastj.engine.internals.Timer;
import tech.fastj.math.Point;
import tech.fastj.graphics.display.Display;
import tech.fastj.graphics.util.DisplayUtil;

import tech.fastj.input.keyboard.Keyboard;
import tech.fastj.input.mouse.Mouse;
import tech.fastj.systems.audio.AudioManager;
import tech.fastj.systems.audio.StreamedAudioPlayer;
import tech.fastj.systems.behaviors.BehaviorManager;
import tech.fastj.systems.control.LogicManager;
import tech.fastj.systems.tags.TagManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import tech.fastj.logging.Log;

/**
 * The main control hub of the game engine.
 * <p>
 * This class contains the methods needed to initialize and run a game using the FastJ Game Engine. With this, you'll
 * have access to the engine's features in their full force.
 * <p>
 * <a href="https://github.com/fastjengine/FastJ">The FastJ Game Engine</a>
 *
 * @author Andrew Dey
 * @since 1.0.0
 */
public class FastJEngine {

    /** Default engine value for "frames per second" of at least {@code 60}, depending on the monitor's refresh rate. */
    public static final int DefaultFPS = Math.max(DisplayUtil.getDefaultMonitorRefreshRate(), 60);

    /** Default engine value for "updates per second" of {@code 60}. */
    public static final int DefaultUPS = 60;

    /** Default engine value for the window resolution of the {@link Display} of {@code 1280*720}. */
    public static final Point DefaultWindowResolution = new Point(1280, 720);

    /** Default engine value for the window resolution of the {@link Display} of {@code 1280*720}. */
    public static final Point DefaultInternalResolution = new Point(1280, 720);

    public static final ExceptionAction DefaultExceptionAction = ExceptionAction.Throw;

    // engine speed variables
    private static int targetFPS;
    private static int targetUPS;

    // FPS counting
    private static Timer timer;
    private static int[] fpsLog;
    private static int drawFrames;
    private static int totalFPS;
    private static int fpsLogIndex;
    private static ScheduledExecutorService fpsLogger;

    // HW acceleration
    private static HWAccel hwAccel;

    // Display/Logic
    private static Display display;
    private static LogicManager gameManager;

    // Check values
    private static boolean isRunning;
    private static boolean isDebug;
    private static ExceptionAction exceptionAction;

    // Late-running actions
    private static final List<Runnable> AfterUpdateList = new ArrayList<>();
    private static final List<Runnable> AfterRenderList = new ArrayList<>();

    private FastJEngine() {
        throw new java.lang.IllegalStateException();
    }

    /**
     * Initializes the game engine with the specified title and logic manager.
     * <p>
     * Other values are set to their respective default values. These are as follows:
     * <ul>
     * 		<li>Default target FPS: {@link #DefaultFPS}</li>
     * 		<li>Default target UPS: {@link #DefaultUPS}</li>
     * 		<li>Default window resolution: {@link #DefaultWindowResolution}</li>
     * 		<li>Default internal game resolution: {@link #DefaultInternalResolution}</li>
     * 		<li>Default hardware acceleration: {@link HWAccel#Default}</li>
     * 	    <li>Default exception action: {@link #DefaultExceptionAction}</li>
     * </ul>
     *
     * @param gameTitle   The title to be used for the {@link Display} window.
     * @param gameManager The {@link LogicManager} instance to be controlled by the engine.
     */
    public static void init(String gameTitle, LogicManager gameManager) {
        init(
                gameTitle,
                gameManager,
                DefaultFPS,
                DefaultUPS,
                DefaultWindowResolution,
                DefaultInternalResolution,
                HWAccel.Default,
                DefaultExceptionAction
        );
    }

    /**
     * Initializes the game engine with the specified title, logic manager, and an engine configuration.
     *
     * @param gameTitle    The title to be used for the {@link Display} window.
     * @param gameManager  The {@link LogicManager} instance to be controlled by the engine.
     * @param engineConfig A {@link EngineConfig} containing configuration for the target FPS, UPS, window resolution,
     *                     internal resolution, hardware acceleration, and action upon exceptions.
     */
    public static void init(String gameTitle, LogicManager gameManager, EngineConfig engineConfig) {
        init(
                gameTitle,
                gameManager,
                engineConfig.targetFPS(),
                engineConfig.targetUPS(),
                engineConfig.windowResolution(),
                engineConfig.internalResolution(),
                engineConfig.hardwareAcceleration(),
                engineConfig.exceptionAction()
        );
    }

    /**
     * Initializes the game engine with the specified title, logic manager, and other options.
     *
     * @param gameTitle            The title to be used for the {@link Display} window.
     * @param gameManager          The {@link LogicManager} instance to be controlled by the engine.
     * @param fps                  The FPS (frames per second) target for the engine to reach.
     * @param ups                  The UPS (updates per second) target for the engine to reach.
     * @param windowResolution     The game's window resolution.
     * @param internalResolution   The game's internal resolution. (This is the defined size of the game's canvas. As a
     *                             result, the content is scaled to fit the size of the {@code windowResolution}).
     * @param hardwareAcceleration Defines the type of hardware acceleration to use for the game.
     * @param exceptionAction      Defines what the engine should do upon receiving an exception.
     */
    public static void init(String gameTitle, LogicManager gameManager, int fps, int ups, Point windowResolution, Point internalResolution, HWAccel hardwareAcceleration, ExceptionAction exceptionAction) {
        runningCheck();

        FastJEngine.gameManager = gameManager;
        display = new Display(gameTitle, windowResolution, internalResolution);
        timer = new Timer();

        fpsLog = new int[100];
        Arrays.fill(fpsLog, -1);
        fpsLogger = Executors.newSingleThreadScheduledExecutor();

        configure(fps, ups, windowResolution, internalResolution, hardwareAcceleration);
        setExceptionAction(exceptionAction);
    }

    /**
     * Configures the game's FPS (Frames Per Second), UPS (Updates Per Second), window resolution, internal resolution,
     * and hardware acceleration.
     *
     * @param fps                  The FPS (frames per second) target for the engine to reach.
     * @param ups                  The UPS (updates per second) target for the engine to reach.
     * @param windowResolution     The game's window resolution.
     * @param internalResolution   The game's internal resolution. (This is the defined size of the game's canvas. As a
     *                             result, the content is scaled to fit the size of the {@code windowResolution}).
     * @param hardwareAcceleration Defines the type of hardware acceleration to use for the game.
     */
    public static void configure(int fps, int ups, Point windowResolution, Point internalResolution, HWAccel hardwareAcceleration) {
        runningCheck();

        configureWindowResolution(windowResolution);
        configureInternalResolution(internalResolution);
        configureHardwareAcceleration(hardwareAcceleration);
        setTargetFPS(fps);
        setTargetUPS(ups);
    }

    /**
     * Configures the game's window resolution.
     *
     * @param windowResolution The game's window resolution.
     */
    public static void configureWindowResolution(Point windowResolution) {
        runningCheck();

        if ((windowResolution.x | windowResolution.y) < 1) {
            error(CrashMessages.ConfigurationError.errorMessage, new IllegalArgumentException("Resolution values must be at least 1."));
        }

        display.setWindowResolution(windowResolution);
    }

    /**
     * Configures the game's internal resolution.
     * <p>
     * This sets the size of the game's drawing canvas. As a result, the content displayed on the canvas will be scaled
     * to fit the size of the {@code windowResolution}.
     *
     * @param internalResolution The game's internal resolution. (This is the defined size of the game's canvas. As a
     *                           result, the content is scaled to fit the size of the {@code windowResolution}).
     */
    public static void configureInternalResolution(Point internalResolution) {
        runningCheck();

        if ((internalResolution.x | internalResolution.y) < 1) {
            error(CrashMessages.ConfigurationError.errorMessage, new IllegalArgumentException("internal resolution values must be at least 1."));
        }

        display.setInternalResolution(internalResolution);
    }

    public static void configureDebugging(boolean shouldDebug) {
        runningCheck();

        isDebug = shouldDebug;
    }

    public static boolean isDebugging() {
        return isDebug;
    }

    /**
     * Attempts to set the hardware acceleration type of this game engine to the specified parameter.
     * <p>
     * If the parameter specified is not supported by the user's computer, then the hardware acceleration will be set to
     * use the computer's defaults.
     * <p>
     * <b>Setting hardware acceleration after starting the game engine, <i>regardless</i> of if the game engine is
     * restarted, will NOT have an effect on the game.</b> It must be changed <i>before</i> the engine is started.
     *
     * @param hardwareAcceleration Defines the type of hardware acceleration to use for the game.
     */
    public static void configureHardwareAcceleration(HWAccel hardwareAcceleration) {
        runningCheck();

        if (isSystemSupportingHA(hardwareAcceleration)) {
            HWAccel.setHardwareAcceleration(hardwareAcceleration);
            hwAccel = hardwareAcceleration;
        } else {
            warning("This OS doesn't support %s hardware acceleration. Configuration will be left at default.", hardwareAcceleration.name());
            HWAccel.setHardwareAcceleration(HWAccel.Default);
            hwAccel = HWAccel.Default;
        }
    }

    private static boolean isSystemSupportingHA(HWAccel hardwareAcceleration) {
        if (hardwareAcceleration.equals(HWAccel.Direct3D)) {
            return System.getProperty("os.name").startsWith("Win");
        } else if (hardwareAcceleration.equals(HWAccel.X11)) {
            return System.getProperty("os.name").startsWith("Linux");
        }
        return true;
    }

    /**
     * Checks if the engine is currently running -- if it is, crash the game.
     * <p>
     * This method is usually for the purpose of ensuring certain methods aren't called while the game engine is
     * running.
     */
    public static void runningCheck() {
        if (isRunning) {
            error(CrashMessages.CalledAfterRunError.errorMessage, new IllegalStateException("This method cannot be called after the game begins running."));
        }
    }

    /**
     * Gets the {@link Display} object associated with the game engine.
     *
     * @return The game engine's display instance.
     */
    public static Display getDisplay() {
        return display;
    }

    /**
     * Gets the {@link LogicManager} associated with the game engine.
     *
     * @param <T> The type of the logic manager being retrieved. This type must match the actual type of the retrieved
     *            logic manager, and must always extend {@link LogicManager}.
     * @return The logic manager.
     */
    @SuppressWarnings("unchecked")
    public static <T extends LogicManager> T getLogicManager() {
        return (T) gameManager;
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
     * Gets the engine's current target FPS.
     *
     * @return The target FPS.
     */
    public static int getTargetFPS() {
        return targetFPS;
    }

    /**
     * Sets the engine's target FPS.
     *
     * @param fps The target FPS to set to.
     */
    public static void setTargetFPS(int fps) {
        if (fps < 1) {
            error(CrashMessages.ConfigurationError.errorMessage, new IllegalArgumentException("FPS amount must be at least 1."));
        }
        targetFPS = fps;
    }

    /**
     * Gets the engine's current target UPS.
     *
     * @return The target UPS.
     */
    public static int getTargetUPS() {
        return targetUPS;
    }

    /**
     * Sets the engine's target UPS.
     *
     * @param ups The target UPS to set to.
     */
    public static void setTargetUPS(int ups) {
        if (ups < 1) {
            error(CrashMessages.ConfigurationError.errorMessage, new IllegalArgumentException("UPS amount must be at least 1."));
        }
        targetUPS = ups;
    }

    /**
     * Sets what the engine should do with exceptions, should they occur.
     * <p>
     * Note that in all situations, the game engine will be closed via {@link FastJEngine#forceCloseGame()} beforehand.
     *
     * @param exceptionAction The {@link ExceptionAction} to set how exceptions should be handled.
     * @since 1.5.0
     */
    public static void setExceptionAction(ExceptionAction exceptionAction) {
        FastJEngine.exceptionAction = exceptionAction;
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
     * 		<li>{@link FPSValue#Current} - gets the last recorded FPS value.</li>
     * 		<li>{@link FPSValue#Average} - gets the average FPS, based on the recorded FPS values.</li>
     * 		<li>{@link FPSValue#Highest} - gets the highest recorded FPS value.</li>
     * 		<li>{@link FPSValue#Lowest} - gets the lowest recorded FPS value.</li>
     * 		<li>{@link FPSValue#OnePercentLow} - gets the average FPS of the lowest 1% of all recorded FPS values.</li>
     * </ul>
     *
     * @param dataType {@link FPSValue} parameter that specifies the information being requested.
     * @return Double value, based on the information requested.
     */
    public static double getFPSData(FPSValue dataType) {
        int[] validFPSValues = Arrays.copyOfRange(fpsLog, 0, Math.min(fpsLog.length, fpsLogIndex));

        switch (dataType) {
            case Current:
                return (fpsLog[fpsLogIndex % 100] != -1) ? fpsLog[fpsLogIndex % 100] : 0;
            case Average:
                return (double) totalFPS / (double) fpsLogIndex;
            case Highest:
                return Arrays.stream(validFPSValues).reduce(Integer::max).orElse(-1);
            case Lowest:
                return Arrays.stream(validFPSValues).reduce(Integer::min).orElse(-1);
            case OnePercentLow:
                return Arrays.stream(validFPSValues).sorted()
                        .limit(Math.max(1L, (long) (validFPSValues.length * 0.01)))
                        .average().orElse(-1d);
            default:
                throw new IllegalStateException("Unexpected value: " + dataType);
        }
    }

    /** Runs the game. */
    public static void run() {
        initEngine();

        try {
            gameLoop();
        } catch (Exception exception) {
            FastJEngine.forceCloseGame();

            switch (exceptionAction) {
                case Throw: {
                    throw exception;
                }
                case LogError: {
                    Log.error(exception.getMessage(), exception);
                    break;
                }
                case Nothing: {
                    break;
                }
            }
        }
    }

    /**
     * Closes the game gracefully, without closing the JVM instance.
     * <p>
     * This method is useful for closing the game engine in normal cases, like exiting the game naturally. This should
     * be your go-to method call for closing the game.
     */
    public static void closeGame() {
        display.close();
    }

    /**
     * Closes the game forcefully, without closing the JVM instance.
     * <p>
     * This method is useful for closing the game engine in special cases, such as if rendering has not yet started, or
     * when a fatal error occurs that prevents the game from functioning properly. It attempts to close the game as soon
     * as possible, without waiting for the next game update/render to be finished.
     *
     * @since 1.5.0
     */
    public static void forceCloseGame() {
        if (display != null && display.isReady()) {
            display.close();
        }
        exit();
    }

    /**
     * Logs the specified message.
     *
     * @param message The message to log.
     * @param formatting The Message Formatting
     */
    public static void log(String message, Object... formatting) {
        Log.info(message, formatting);
    }

    /**
     * Logs the specified warning message.
     *
     * @param warningMessage The warning to log.
     * @param formatting The Message Formatting.
     */
    public static void warning(String warningMessage, Object... formatting) {
        Log.warn(warningMessage, formatting);
    }

    /**
     * Forcefully closes the game, then throws the error specified with the error message.
     *
     * @param errorMessage The error message to log.
     * @param exception    The exception that caused a need for this method call.
     */
    public static void error(String errorMessage, Exception exception) {
        FastJEngine.forceCloseGame();
        Log.error(errorMessage, exception);
        throw new IllegalStateException("ERROR: " + errorMessage, exception);
    }

    /**
     * Runs the specified action after the game engine's next {@link LogicManager#update(Display) fixedUpdate} call.
     * <p>
     * This method serves the purpose of running certain necessary actions for a game that wouldn't be easily possible
     * otherwise, such as adding a game object to a scene while in an {@link LogicManager#update(Display)} call.
     *
     * @param action Disposable action to be run after the next {@link LogicManager#update(Display)} call.
     * @since 1.4.0
     */
    public static void runAfterUpdate(Runnable action) {
        AfterUpdateList.add(action);
    }

    /**
     * Runs the specified action after the game engine's next {@link LogicManager#render(Display) render} call.
     * <p>
     * This method serves the purpose of running certain necessary actions for a game that wouldn't be easily possible
     * otherwise, such as adding a game object to a scene while in an {@link LogicManager#update(Display)} call.
     *
     * @param action Disposable action to be run after the next {@link LogicManager#render(Display)} call.
     * @since 1.5.0
     */
    public static void runAfterRender(Runnable action) {
        AfterRenderList.add(action);
    }

    /** Initializes the game engine's components. */
    private static void initEngine() {
        if (isDebug) {
            log("Initializing FastJ...");
        }

        runningCheck();
        isRunning = true;

        ThreadFixer.start();
        display.init();
        gameManager.init(display);
        gameManager.initBehaviors();

        timer.init();
        fpsLogger.scheduleWithFixedDelay(() -> {
            FastJEngine.logFPS(drawFrames);
            drawFrames = 0;
        }, 1, 1, TimeUnit.SECONDS);

        System.gc(); // yes, I really gc before starting.
        display.open();

        if (isDebug) {
            log("FastJ initialization complete. Enjoy your stay with FastJ!");
        }
    }

    /** Runs the game loop -- the heart of the engine. */
    private static void gameLoop() {
        float elapsedTime;
        float accumulator = 0f;
        float updateInterval = 1f / targetUPS;

        while (!display.isClosed()) {
            elapsedTime = timer.getElapsedTime();
            accumulator += elapsedTime;

            while (accumulator >= updateInterval) {
                gameManager.update(display);
                gameManager.updateBehaviors();

                if (!AfterUpdateList.isEmpty()) {
                    for (Runnable action : AfterUpdateList) {
                        action.run();
                    }
                    AfterUpdateList.clear();
                }

                accumulator -= updateInterval;
            }

            gameManager.processInputEvents();
            gameManager.processKeysDown();
            gameManager.render(display);
            if (!AfterRenderList.isEmpty()) {
                for (Runnable action : AfterRenderList) {
                    action.run();
                }
                AfterRenderList.clear();
            }
            drawFrames++;

            if (!display.isFullscreen()) {
                sync();
            }
        }

        exit();
    }

    /**
     * Syncs the game engine frame rate.
     * <p>
     * This provides, for a lack of better terms, "jank" way of emulating V-Sync when the game engine is not running in
     * fullscreen mode.
     */
    private static void sync() {
        final float loopSlot = 1f / targetFPS;
        final double endTime = timer.getLastLoopTime() + loopSlot;
        final double currentTime = timer.getTime();
        if (currentTime < endTime) {
            try {
                TimeUnit.MILLISECONDS.sleep((long) ((endTime - currentTime) * 1000L));
            } catch (InterruptedException exception) {
                FastJEngine.warning("Interrupted while syncing game frame rate: " + exception.getMessage() + Arrays.deepToString(exception.getStackTrace()));
                Thread.currentThread().interrupt();
            }
        }
    }

    /** Removes all resources created by the game engine. */
    private static void exit() {
        if (fpsLogger != null) {
            if (isDebug) {
                FastJEngine.log(
                        "{}{}|---- FPS Results ----|{}{}Average FPS: {}{}Highest Frame Count: {}{}Lowest Frame Count: {}{}One Percent Low: {}",
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        getFPSData(FPSValue.Average),
                        System.lineSeparator(),
                        getFPSData(FPSValue.Highest),
                        System.lineSeparator(),
                        getFPSData(FPSValue.Lowest),
                        System.lineSeparator(),
                        getFPSData(FPSValue.OnePercentLow)
                );
            }

            fpsLogger.shutdownNow();
        }
        if (gameManager != null) {
            gameManager.reset();
        }

        Mouse.stop();
        Keyboard.stop();
        AudioManager.reset();
        StreamedAudioPlayer.reset();
        BehaviorManager.reset();
        TagManager.reset();
        AfterUpdateList.clear();
        AfterRenderList.clear();

        // engine speed variables
        targetFPS = 0;
        targetUPS = 0;

        // FPS counting
        timer = null;
        fpsLog = null;
        fpsLogger = null;
        drawFrames = 0;
        totalFPS = 0;
        fpsLogIndex = 0;

        // HW acceleration
        hwAccel = null;

        // Display/Logic
        display = null;
        gameManager = null;

        // Check values
        isRunning = false;

        // Helpful? Debatable. Do I care? Not yet....
        System.gc();
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

        if (FastJEngine.isDebugging()) {
            FastJEngine.log("Frames rendered: {}", frames);
        }

        storeFPS(frames);
    }

    /**
     * Stores the specified frames value in the engine's FPS log.
     *
     * @param frames The count of frames rendered.
     */
    private static void storeFPS(int frames) {
        fpsLog[fpsLogIndex % 100] = frames;
        fpsLogIndex++;
        totalFPS += frames;
    }
}
