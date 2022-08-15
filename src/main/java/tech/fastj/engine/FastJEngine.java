package tech.fastj.engine;

import tech.fastj.animation.Animated;
import tech.fastj.animation.AnimationData;
import tech.fastj.animation.AnimationEngine;
import tech.fastj.animation.sprite.SpriteAnimEngine;
import tech.fastj.engine.config.EngineConfig;
import tech.fastj.engine.config.ExceptionAction;
import tech.fastj.engine.internals.ThreadFixer;
import tech.fastj.gameloop.CoreLoopState;
import tech.fastj.gameloop.GameLoop;
import tech.fastj.gameloop.GameLoopState;
import tech.fastj.graphics.display.Display;
import tech.fastj.graphics.display.DisplayState;
import tech.fastj.graphics.display.FastJCanvas;
import tech.fastj.graphics.display.SimpleDisplay;
import tech.fastj.graphics.game.Sprite2D;
import tech.fastj.graphics.util.DisplayUtil;
import tech.fastj.input.keyboard.Keyboard;
import tech.fastj.input.mouse.Mouse;
import tech.fastj.logging.Log;
import tech.fastj.logging.LogLevel;
import tech.fastj.math.Point;
import tech.fastj.resources.Resource;
import tech.fastj.resources.ResourceManager;
import tech.fastj.resources.images.ImageResource;
import tech.fastj.resources.images.ImageResourceManager;
import tech.fastj.systems.audio.AudioManager;
import tech.fastj.systems.audio.StreamedAudioPlayer;
import tech.fastj.systems.behaviors.Behavior;
import tech.fastj.systems.behaviors.BehaviorManager;
import tech.fastj.systems.control.LogicManager;
import tech.fastj.systems.execution.FastJScheduledThreadPool;
import tech.fastj.systems.execution.RunLaterEvent;
import tech.fastj.systems.execution.RunLaterObserver;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The main control hub of the game engine.
 * <p>
 * This class contains the methods needed to initialize and run a game using the FastJ Game Engine. With this, you'll have access to the
 * engine's features in their full force.
 * <p>
 * <a href="https://github.com/fastjengine/FastJ">The FastJ Game Engine</a>
 *
 * @author Andrew Dey
 * @since 1.0.0
 */
public class FastJEngine {

    /** Default engine value for "frames per second" of {@code 60} or the monitor's refresh rate. */
    public static final int DefaultFPS;

    /** Default engine value for "updates per second" of {@code 60}. */
    public static final int DefaultUPS = 60;

    /** Default engine value for the window resolution of the {@link Display} of {@code 1280*720}. */
    public static final Point DefaultWindowResolution = new Point(1280, 720);

    /** Default engine value for the window resolution of the {@link Display} of {@code 1280*720}. */
    public static final Point DefaultCanvasResolution = new Point(1280, 720);

    /** Default engine hardware acceleration, {@link HWAccel#Default} */
    public static final HWAccel DefaultHardwareAcceleration = HWAccel.Default;

    /** Default engine exception action, {@link ExceptionAction#Throw} */
    public static final ExceptionAction DefaultExceptionAction = ExceptionAction.Throw;

    /** Default engine log level, {@link LogLevel#Info} */
    public static final LogLevel DefaultLogLevel = LogLevel.Info;

    // engine speed variables
    private static int targetFPS;
    private static int targetUPS;

    // Timings
    private static int[] fpsLog;
    private static int drawFrames;
    private static int totalFPS;
    private static int fpsLogIndex;
    private static ScheduledExecutorService fpsLogger;

    // HW acceleration
    private static HWAccel hwAccel;

    // Display/Logic
    private static String title;
    private static Display display;
    private static FastJCanvas canvas;
    private static LogicManager gameManager;

    private static Point windowResolution;
    private static Point canvasResolution;

    // Check values
    private static boolean isRunning;
    private static LogLevel logLevel = DefaultLogLevel;
    private static ExceptionAction exceptionAction;

    // Resources
    private static final Map<Class<Resource<?>>, ResourceManager<Resource<?>, ?>> ResourceManagers = new ConcurrentHashMap<>();

    // Animation
    private static final Map<Class<Animated<?, ?>>, AnimationEngine<?, ?>> AnimationEngines = new ConcurrentHashMap<>();

    // Game Loop
    /** Fixed Update loop state definition. */
    public static final GameLoopState GeneralFixedUpdate = new GameLoopState(
        CoreLoopState.FixedUpdate,
        1,
        (gameLoopState, fixedDeltaTime) -> gameManager.fixedUpdate(canvas)
    );

    /** Fixed Update loop state definition for {@link Behavior behvaiors}. */
    public static final GameLoopState BehaviorFixedUpdate = new GameLoopState(
        CoreLoopState.FixedUpdate,
        2,
        (gameLoopState, fixedDeltaTime) -> gameManager.fixedUpdateBehaviors()
    );

    /** Update loop state for processing general input. Currently unused. */
    public static final GameLoopState ProcessInputEvents = new GameLoopState(
        CoreLoopState.Update,
        0,
        (gameLoopState, deltaTime) -> {}
    );

    /** Update loop state for {@link LogicManager#processKeysDown() processing input keys pressed down}. */
    public static final GameLoopState ProcessKeysDown = new GameLoopState(
        CoreLoopState.Update,
        1,
        (gameLoopState, deltaTime) -> gameManager.processKeysDown()
    );

    /** Update loop state definition. */
    public static final GameLoopState GeneralUpdate = new GameLoopState(
        CoreLoopState.Update,
        2,
        (gameLoopState, deltaTime) -> gameManager.update(canvas)
    );

    /** Update loop state definition for {@link Behavior behaviors}. */
    public static final GameLoopState BehaviorUpdate = new GameLoopState(
        CoreLoopState.Update,
        3,
        (gameLoopState, deltaTime) -> gameManager.updateBehaviors()
    );

    /** Update loop state definition for {@link AnimationEngine#stepAnimations(float) stepping animations forward}. */
    public static final GameLoopState AnimationStep = new GameLoopState(
        CoreLoopState.Update,
        4,
        (gameLoopState, deltaTime) -> {
            for (AnimationEngine<?, ?> animationEngine : AnimationEngines.values()) {
                animationEngine.stepAnimations(deltaTime);
            }
        }
    );

    /** Update loop state definition for {@link LogicManager#render(FastJCanvas) rendering}. */
    public static final GameLoopState GeneralRender = new GameLoopState(
        CoreLoopState.LateUpdate,
        Integer.MAX_VALUE - 1,
        (gameLoopState, deltaTime) -> {
            gameManager.render(canvas);
            drawFrames++;
        }
    );

    private static final GameLoop GameLoop = new GameLoop(
        loop -> display != null && display.getWindow().isVisible(),
        loop -> display != null && display.getDisplayState() != DisplayState.FullScreen
    );

    private static final RunLaterObserver RunLaterObserver = new RunLaterObserver();

    // Audio
    private static final AudioManager AudioManager = new AudioManager();

    private FastJEngine() {
        throw new java.lang.IllegalStateException();
    }

    static {
        int fps;
        try {
            fps = DisplayUtil.getDefaultMonitorRefreshRate();

            if (fps < 1) {
                warning("Environment is not headless but monitor refresh rate was less than 1, will default FPS to 60.");
                fps = 60;
            }
        } catch (HeadlessException exception) {
            warning("Environment is headless, will default FPS to 60.");
            fps = 60;
        }
        DefaultFPS = fps;
        AudioManager.init();

        addDefaultResourceManagers();
        addDefaultAnimationEngines();

        initGameLoop();
    }

    private static void initGameLoop() {
        GameLoop.addGameLoopStates(GeneralFixedUpdate, BehaviorFixedUpdate);
        GameLoop.addGameLoopStates(ProcessInputEvents, ProcessKeysDown, GeneralUpdate, BehaviorUpdate, AnimationStep);
        GameLoop.addGameLoopStates(GeneralRender);
    }

    /**
     * Initializes the game engine with the specified title and logic manager.
     * <p>
     * Other values are set to their respective default values, inside {@link EngineConfig#Default}.
     *
     * @param gameTitle   The title to be used for the {@link Display} window.
     * @param gameManager The {@link LogicManager} instance to be controlled by the engine.
     */
    public static void init(String gameTitle, LogicManager gameManager) {
        init(gameTitle, gameManager, EngineConfig.Default);
    }

    /**
     * Initializes the game engine with the specified title, logic manager, and an engine configuration.
     *
     * @param gameTitle    The title to be used for the {@link Display} window.
     * @param gameManager  The {@link LogicManager} instance to be controlled by the engine.
     * @param engineConfig A {@link EngineConfig} containing configuration for the target FPS, UPS, window resolution, canvas cesolution,
     *                     hardware acceleration, and action upon exceptions.
     */
    public static void init(String gameTitle, LogicManager gameManager, EngineConfig engineConfig) {
        runningCheck();

        FastJEngine.gameManager = gameManager;
        FastJEngine.title = gameTitle;

        fpsLog = new int[100];
        Arrays.fill(fpsLog, -1);
        fpsLogger = new FastJScheduledThreadPool(1);

        setTargetFPS(engineConfig.targetFPS());
        setTargetUPS(engineConfig.targetUPS());
        configureWindowResolution(engineConfig.windowResolution());
        configureCanvasResolution(engineConfig.canvasResolution());
        configureHardwareAcceleration(engineConfig.hardwareAcceleration());
        configureExceptionAction(engineConfig.exceptionAction());
        configureLogging(engineConfig.logLevel());
    }

    /**
     * Initializes the game engine with the specified title, logic manager, and other options.
     *
     * @param gameTitle            The title to be used for the {@link Display} window.
     * @param gameManager          The {@link LogicManager} instance to be controlled by the engine.
     * @param fps                  The FPS (frames per second) target for the engine to reach.
     * @param ups                  The UPS (updates per second) target for the engine to reach.
     * @param windowResolution     The game's window resolution.
     * @param canvasResolution     The game's canvas resolution. (This is the defined size of the game's canvas. As a result, the content is
     *                             scaled to fit the size of the {@code windowResolution}).
     * @param hardwareAcceleration Defines the type of hardware acceleration to use for the game.
     * @param exceptionAction      Defines what the engine should do upon receiving an exception.
     * @deprecated As of 1.6.0, replaced by {@link #init(String, LogicManager, EngineConfig)} which makes use of
     * {@link EngineConfig an engine configuration}.
     */
    @Deprecated
    public static void init(String gameTitle, LogicManager gameManager, int fps, int ups, Point windowResolution, Point canvasResolution, HWAccel hardwareAcceleration, ExceptionAction exceptionAction) {
        EngineConfig engineConfig = EngineConfig.create()
            .withTargetFPS(fps)
            .withTargetUPS(ups)
            .withWindowResolution(windowResolution)
            .withCanvasResolution(canvasResolution)
            .withHardwareAcceleration(hardwareAcceleration)
            .withExceptionAction(exceptionAction)
            .build();

        init(gameTitle, gameManager, engineConfig);
    }

    private static void addDefaultResourceManagers() {
        addResourceManager(new ImageResourceManager(), ImageResource.class);
    }

    private static void addDefaultAnimationEngines() {
        addAnimationEngine(new SpriteAnimEngine(), Sprite2D.class);
    }

    /**
     * Configures the game's FPS (Frames Per Second), UPS (Updates Per Second), window resolution, canvas resolution, and hardware
     * acceleration.
     *
     * @param fps                  The FPS (frames per second) target for the engine to reach.
     * @param ups                  The UPS (updates per second) target for the engine to reach.
     * @param windowResolution     The game's window resolution.
     * @param canvasResolution     The game's canvas resolution. (This is the defined size of the game's canvas. As a result, the content is
     *                             scaled to fit the size of the {@code windowResolution}).
     * @param hardwareAcceleration Defines the type of hardware acceleration to use for the game.
     */
    public static void configure(int fps, int ups, Point windowResolution, Point canvasResolution, HWAccel hardwareAcceleration) {
        runningCheck();

        configureWindowResolution(windowResolution);
        configureCanvasResolution(canvasResolution);
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
            error(
                CrashMessages.ConfigurationError.errorMessage,
                new IllegalArgumentException("Resolution values must be at least 1.")
            );
        }

        FastJEngine.windowResolution = windowResolution;
    }

    /**
     * Configures the game's canvas resolution.
     * <p>
     * This sets the size of the game's drawing canvas. As a result, the content displayed on the canvas will be scaled to fit the size of
     * the {@code windowResolution}.
     *
     * @param canvasResolution The game's canvas resolution. (This is the defined size of the game's canvas. As a result, the content is
     *                         scaled to fit the size of the {@code windowResolution}).
     */
    public static void configureCanvasResolution(Point canvasResolution) {
        runningCheck();

        if ((canvasResolution.x | canvasResolution.y) < 1) {
            error(
                CrashMessages.ConfigurationError.errorMessage,
                new IllegalArgumentException("canvas resolution values must be at least 1.")
            );
        }

        FastJEngine.canvasResolution = canvasResolution;
    }

    /**
     * Configures the engine's {@link LogLevel log level}.
     * <p>
     * This sets the level of logging for the entire engine. The amount of content logged from the engine becomes dependent on the level of
     * logging set.
     * <p>
     * By default, the logging level is {@link LogLevel#Info}.
     *
     * @param logLevel The level to set the logging for the engine to.
     */
    public static void configureLogging(LogLevel logLevel) {
        runningCheck();

        FastJEngine.logLevel = Objects.requireNonNull(logLevel, "The given log level must not be null.");
    }

    /**
     * {@return whether the engine is logging at least at the specified {@link LogLevel}}
     *
     * @param comparisonLevel The {@link LogLevel log level} to test against.
     */
    public static boolean isLogging(LogLevel comparisonLevel) {
        return logLevel.ordinal() >= comparisonLevel.ordinal();
    }

    /** {@return the engine's {@link LogLevel log level}} */
    public static LogLevel getLogLevel() {
        return logLevel;
    }

    /**
     * Attempts to set the hardware acceleration type of this game engine to the specified parameter.
     * <p>
     * If the parameter specified is not supported by the user's computer, then the hardware acceleration will be set to use the computer's
     * defaults.
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
            warning(
                "This OS doesn't support {} hardware acceleration. Configuration will be left at default.",
                hardwareAcceleration.name()
            );
            HWAccel.setHardwareAcceleration(HWAccel.Default);
            hwAccel = HWAccel.Default;
        }
    }

    private static boolean isSystemSupportingHA(HWAccel hardwareAcceleration) {
        return System.getProperty("os.name").startsWith(switch (hardwareAcceleration) {
            case Direct3D -> "Win";
            case X11 -> "Linux";
            case Metal -> "Mac";
            default -> "";
        });
    }

    /**
     * Checks if the engine is currently running -- if it is, crash the game.
     * <p>
     * This method is usually for the purpose of ensuring certain methods aren't called while the game engine is running.
     */
    public static void runningCheck() {
        if (isRunning) {
            error(
                CrashMessages.CalledAfterRunError.errorMessage,
                new IllegalStateException("This method cannot be called after the game begins running.")
            );
        }
    }

    /**
     * Gets the {@link FastJCanvas} associated with the game engine.
     *
     * @return The game engine's canvas instance.
     */
    public static FastJCanvas getCanvas() {
        return canvas;
    }

    /**
     * Gets the {@link Display} object associated with the game engine.
     *
     * @param <T> The type of the display.
     * @return The game engine's display instance.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Display> T getDisplay() {
        return (T) display;
    }

    /**
     * Gets the {@link LogicManager} associated with the game engine.
     *
     * @param <T> The type of the logic manager being retrieved. This type must match the actual type of the retrieved logic manager, and
     *            must always extend {@link LogicManager}.
     * @return The logic manager.
     */
    @SuppressWarnings("unchecked")
    public static <T extends LogicManager> T getLogicManager() {
        return (T) gameManager;
    }

    /**
     * Gets the hardware acceleration currently enabled for the game engine.
     *
     * @return Returns the HWAccelType that defines what hardware acceleration, or lack thereof, is currently being used for the game
     * engine.
     */
    public static HWAccel getHardwareAcceleration() {
        return hwAccel;
    }

    /** {@return the last recorded delta time from the {@link GameLoop game loop}} */
    public static float getDeltaTime() {
        return GameLoop.getDeltaTime();
    }

    /** {@return the last recorded fixed delta time from the {@link GameLoop game loop}} */
    public static float getFixedDeltaTime() {
        return GameLoop.getDeltaTime();
    }

    /** {@return the engine's {@link GameLoop game loop}} */
    public static GameLoop getGameLoop() {
        return GameLoop;
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
        GameLoop.setTargetFPS(targetFPS);
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
        GameLoop.setTargetUPS(targetUPS);
    }

    /**
     * Configures what the engine should do with exceptions, should they occur.
     * <p>
     * Note that in all situations, the game engine will be closed via {@link FastJEngine#forceCloseGame()} beforehand.
     *
     * @param exceptionAction The {@link ExceptionAction} to set how exceptions should be handled.
     * @since 1.5.0
     */
    public static void configureExceptionAction(ExceptionAction exceptionAction) {
        FastJEngine.exceptionAction = exceptionAction;
    }

    /**
     * Gets the engine's currently defined {@link ExceptionAction exception action}.
     *
     * @return The exception action instance.
     */
    public static ExceptionAction getExceptionAction() {
        return exceptionAction;
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
        int validFPSIndex = fpsLogIndex == -1 ? 0 : fpsLogIndex;
        int[] validFPSValues = Arrays.copyOfRange(
            fpsLog,
            0,
            Math.min(fpsLog.length, validFPSIndex)
        );

        return switch (dataType) {
            case Current -> (fpsLog[validFPSIndex % 100] != -1) ? fpsLog[fpsLogIndex % 100] : 0;
            case Average -> (double) totalFPS / (double) fpsLogIndex;
            case Highest -> Arrays.stream(validFPSValues).reduce(Integer::max).orElse(-1);
            case Lowest -> Arrays.stream(validFPSValues).reduce(Integer::min).orElse(-1);
            case OnePercentLow -> Arrays.stream(validFPSValues)
                .sorted()
                .limit(Math.max(1L, (long) (validFPSValues.length * 0.01)))
                .average()
                .orElse(-1d);
        };
    }

    /**
     * Adds the specified {@link ResourceManager resource manager} to the engine's mapping of resource managers.
     *
     * @param resourceManager The resource manager to add.
     * @param resourceClass   The class of the resource that the resource manager uses.
     * @param <U>             The raw resource type.
     * @param <V>             The type of the resource class.
     * @param <T>             The type of the resource manager.
     */
    @SuppressWarnings("unchecked")
    public static <U, V extends Resource<U>, T extends ResourceManager<V, U>> void addResourceManager(T resourceManager, Class<V> resourceClass) {
        ResourceManagers.put((Class<Resource<?>>) resourceClass, (ResourceManager<Resource<?>, ?>) resourceManager);
    }

    /**
     * {@return the resource manager which uses the specified class}
     *
     * @param resourceClass The class of the resource that the resource manager uses.
     * @param <U>           The raw resource type.
     * @param <V>           The type of the resource class.
     * @param <T>           The type of the resource manager.
     * @throws IllegalStateException if no resource manager is found for the specified resource class.
     */
    @SuppressWarnings("unchecked")
    public static <U, V extends Resource<U>, T extends ResourceManager<V, U>> T getResourceManager(Class<V> resourceClass) {
        return (T) ResourceManagers.computeIfAbsent((Class<Resource<?>>) resourceClass, rClass -> {
            throw new IllegalStateException("No resource manager was added for the resource type \"" + resourceClass.getTypeName() + "\".");
        });
    }

    /**
     * Adds the specified {@link AnimationEngine animation engine} to the engine's mapping of animation engines.
     *
     * @param animationEngine The animation engine to add.
     * @param animationClass  The class of the animated type that the animation engine uses.
     * @param <TD>            The type of the animation data used by the animated type.
     * @param <T>             The animated type.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Animated<T, TD>, TD extends AnimationData<T, TD>> void addAnimationEngine(AnimationEngine<T, TD> animationEngine, Class<T> animationClass) {
        AnimationEngines.put((Class<Animated<?, ?>>) animationClass, animationEngine);
    }

    /**
     * {@return the animation engine which uses the specified class}
     *
     * @param animationClass The class of the animated type that the animation engine uses.
     * @param <TD>           The type of the animation data used by the animated type.
     * @param <T>            The animated type.
     * @throws IllegalStateException if no animation engine is found for the specified animation class.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Animated<T, TD>, TD extends AnimationData<T, TD>> AnimationEngine<T, TD> getAnimationEngine(Class<T> animationClass) {
        return (AnimationEngine<T, TD>) AnimationEngines.computeIfAbsent((Class<Animated<?, ?>>) animationClass, aClass -> {
            throw new IllegalStateException("No animation engine was added for the animation type \"" + animationClass.getTypeName() + "\".");
        });
    }

    /** {@return the engine's {@link AudioManager audio manager}} */
    public static AudioManager getAudioManager() {
        return AudioManager;
    }

    /**
     * Allows you to specify a custom {@link Display display}.
     *
     * @param display The custom display.
     * @param <T>     The type of the custom display.
     */
    public static <T extends Display> void setCustomDisplay(T display) {
        FastJEngine.display = display;
    }

    /**
     * Runs the game.
     * <p>
     * If some error is thrown during the game engine's running time, the engine will throw the exception up to here, and will be handled
     * based on the {@link #configureExceptionAction(ExceptionAction) configured exception action}.
     *
     * @throws IllegalStateException if the engine is configured to use {@link ExceptionAction#Throw}, and an error is thrown. This
     *                               exception wraps the actual exception.
     */
    public static void run() {
        try {
            initEngine();
            GameLoop.run();
            exit();
        } catch (Exception exception) {
            Log.error(exception.getMessage(), exception);

            switch (exceptionAction) {
                case Throw -> {
                    FastJEngine.forceCloseGame();
                    throw new IllegalStateException(exception);
                }
                case LogError -> FastJEngine.forceCloseGame();
                case Nothing -> {
                }
            }
        }
    }

    /**
     * Closes the game gracefully, without closing the JVM instance.
     * <p>
     * This method is useful for closing the game engine in normal cases, like exiting the game naturally. This should be your go-to method
     * call for closing the game.
     */
    public static void closeGame() {
        display.close();
    }

    /**
     * Closes the game forcefully, without closing the JVM instance.
     * <p>
     * This method is useful for closing the game engine in special cases, such as if rendering has not yet started, or when a fatal error
     * occurs that prevents the game from functioning properly. It attempts to close the game as soon as possible, without waiting for the
     * next game update/render to be finished.
     *
     * @since 1.5.0
     */
    public static void forceCloseGame() {
        if (display != null) {
            display.close();
        }
        exit();
    }

    /**
     * Logs the specified message at the {@link LogLevel#Trace trace} level.
     *
     * @param message    The formatted message to log.
     * @param formatting The arguments, if any, of the formatted message.
     * @see Log#trace(String, Object...)
     */
    public static void trace(String message, Object... formatting) {
        Log.trace(FastJEngine.class, message, formatting);
    }

    /**
     * Logs the specified message at the {@link LogLevel#Debug debug} level.
     *
     * @param message    The formatted message to log.
     * @param formatting The arguments, if any, of the formatted message.
     * @see Log#debug(String, Object...)
     */
    public static void debug(String message, Object... formatting) {
        Log.debug(FastJEngine.class, message, formatting);
    }

    /**
     * Logs the specified message at the {@link LogLevel#Info info} level.
     *
     * @param message    The formatted message to log.
     * @param formatting The arguments, if any, of the formatted message.
     * @see Log#info(String, Object...)
     */
    public static void log(String message, Object... formatting) {
        Log.info(FastJEngine.class, message, formatting);
    }

    /**
     * Logs the specified message at the {@link LogLevel#Warn warning} level.
     *
     * @param warningMessage The formatted warning to log.
     * @param formatting     The arguments, if any, of the formatted warning.
     * @see Log#warn(String, Object...)
     */
    public static void warning(String warningMessage, Object... formatting) {
        Log.warn(FastJEngine.class, warningMessage, formatting);
    }

    /**
     * Forcefully closes the game, then throws the error specified with the error message.
     * <p>
     * This logs the specified error message at the {@link LogLevel#Error error} level.
     *
     * @param errorMessage The error message to log.
     * @param throwable    The exception that caused a need for this method call.
     * @see Log#error(String, Exception)
     */
    public static void error(String errorMessage, Throwable throwable) {
        FastJEngine.forceCloseGame();
        Log.error(FastJEngine.class, errorMessage, throwable);
    }

    /**
     * Runs the specified action after the game engine finishes the next {@link CoreLoopState#FixedUpdate fixed update} state it enters.
     * <p>
     * This method runs {@link #runLater(Runnable, CoreLoopState)}, with a default {@link CoreLoopState} parameter of
     * {@link CoreLoopState#FixedUpdate}.
     *
     * @param action Disposable action to be run after the game engine finishes its next {@link CoreLoopState#FixedUpdate fixed update}
     *               state.
     * @since 1.7.0
     */
    public static void runLater(Runnable action) {
        runLater(action, CoreLoopState.FixedUpdate);
    }

    /**
     * Runs the specified action after the game engine finishes the next {@link CoreLoopState coreLoopState} it enters.
     * <p>
     * This method serves the purpose of running certain necessary actions for a game that wouldn't be easily possible otherwise, such as
     * {@link FastJEngine#closeGame() closing the game} from a non-main thread.
     *
     * @param action        Disposable action to be run after the game engine finishes its {@code coreLoopState} state.
     * @param coreLoopState The {@link CoreLoopState core game loop state} after which the {@code action} will be run.
     * @since 1.7.0
     */
    public static void runLater(Runnable action, CoreLoopState coreLoopState) {
        GameLoop.fireEvent(new RunLaterEvent(action), coreLoopState);
    }

    /** Initializes the game engine's internals. */
    private static void initEngine() {
        if (isLogging(LogLevel.Info)) {
            log("Initializing FastJ...");
        }

        runningCheck();
        isRunning = true;

        System.setProperty("sun.awt.noerasebackground", "true");
        Toolkit.getDefaultToolkit().setDynamicLayout(false);
        ThreadFixer.start();

        GameLoop.setTargetFPS(targetFPS);
        GameLoop.setTargetUPS(targetUPS);

        GameLoop.addEventObserver(RunLaterObserver, RunLaterEvent.class);

        AudioManager.init();

        if (display == null) {
            display = new SimpleDisplay(title, windowResolution);
        }
        canvas = new FastJCanvas(display, canvasResolution);
        canvas.init();

        gameManager.init(canvas);
        gameManager.initBehaviors();

        fpsLogIndex = -1;
        fpsLogger.scheduleWithFixedDelay(() -> {
            FastJEngine.logFPS(drawFrames);
            drawFrames = 0;
        }, 1, 1, TimeUnit.SECONDS);

        System.gc(); // yes, I really gc before starting.
        display.open();

        if (isLogging(LogLevel.Info)) {
            log("FastJ initialization complete. Enjoy your stay with FastJ!");
        }
    }

    /** Removes all resources created by the game engine, and resets its internals. */
    private static void exit() {
        if (fpsLogger != null) {
            if (isLogging(LogLevel.Debug)) {
                FastJEngine.debug(
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
        AudioManager.init();
        StreamedAudioPlayer.reset();
        BehaviorManager.reset();

        ResourceManagers.forEach(((resourceClass, resourceResourceManager) -> resourceResourceManager.unloadAllResources()));
        ResourceManagers.clear();

        // engine speed variables
        targetFPS = 0;
        targetUPS = 0;

        // FPS counting
        fpsLog = null;
        fpsLogger = null;
        drawFrames = 0;
        totalFPS = 0;
        fpsLogIndex = -1;

        // HW acceleration
        hwAccel = null;

        // Display/Logic
        display = null;
        gameManager = null;

        // game loop
        GameLoop.reset();
        initGameLoop();

        // Check values
        isRunning = false;

        // Helpful? Debatable. Do I care? Not yet....
        System.gc();
    }

    /**
     * Logs the current frames rendered.
     *
     * @param frames The count of frames rendered.
     */
    private static void logFPS(int frames) {
        if (FastJEngine.isLogging(LogLevel.Debug)) {
            Log.debug("Frames rendered: {}", frames);
        }

        storeFPS(frames);
    }

    /**
     * Stores the specified frames value in the engine's FPS log.
     *
     * @param frames The count of frames rendered.
     */
    private static void storeFPS(int frames) {
        fpsLog[(fpsLogIndex + 1) % 100] = frames;
        fpsLogIndex++;
        totalFPS += frames;
    }
}
