package tech.fastj.engine.config;

import tech.fastj.engine.FastJEngine;
import tech.fastj.engine.HWAccel;
import tech.fastj.graphics.display.Display;
import tech.fastj.graphics.display.FastJCanvas;
import tech.fastj.logging.LogLevel;
import tech.fastj.math.Point;

/**
 * Builder class to create {@link EngineConfig} instances.
 * <p>
 * An instance of the builder can be created using {@link EngineConfig#create()}.
 */
public class EngineConfigBuilder {

    private int targetFPS = FastJEngine.DefaultFPS;
    private int targetUPS = FastJEngine.DefaultUPS;

    private Point windowResolution = FastJEngine.DefaultWindowResolution.copy();
    private Point canvasResolution = FastJEngine.DefaultCanvasResolution.copy();

    private HWAccel hardwareAcceleration = FastJEngine.DefaultHardwareAcceleration;
    private ExceptionAction exceptionAction = FastJEngine.DefaultExceptionAction;
    private LogLevel logLevel = FastJEngine.DefaultLogLevel;

    EngineConfigBuilder() {
    }

    /**
     * Sets the target frames per second to be used.
     *
     * @param targetFPS The target FPS.
     * @return The builder instance for method chaining.
     */
    public EngineConfigBuilder withTargetFPS(int targetFPS) {
        this.targetFPS = targetFPS;
        return this;
    }

    /**
     * Sets the target updates per second to be used.
     *
     * @param targetUPS The target UPS.
     * @return The builder instance for method chaining.
     */
    public EngineConfigBuilder withTargetUPS(int targetUPS) {
        this.targetUPS = targetUPS;
        return this;
    }

    /**
     * Sets the {@link Display window} resolution to be used.
     *
     * @param windowResolution Thw window resolution.
     * @return The builder instance for method chaining.
     */
    public EngineConfigBuilder withWindowResolution(Point windowResolution) {
        this.windowResolution = windowResolution.copy();
        return this;
    }

    /**
     * Sets the {@link FastJCanvas canvas} resolution to be used.
     *
     * @param canvasResolution Thw canvas resolution.
     * @return The builder instance for method chaining.
     */
    public EngineConfigBuilder withCanvasResolution(Point canvasResolution) {
        this.canvasResolution = canvasResolution.copy();
        return this;
    }

    /**
     * Sets the {@link HWAccel hardware acceleration} to be used.
     *
     * @param hardwareAcceleration The hardware acceleration.
     * @return The builder instance for method chaining.
     */
    public EngineConfigBuilder withHardwareAcceleration(HWAccel hardwareAcceleration) {
        this.hardwareAcceleration = hardwareAcceleration;
        return this;
    }

    /**
     * Sets the {@link ExceptionAction exception action} to be used.
     *
     * @param exceptionAction The exception action.
     * @return The builder instance for method chaining.
     */
    public EngineConfigBuilder withExceptionAction(ExceptionAction exceptionAction) {
        this.exceptionAction = exceptionAction;
        return this;
    }

    /**
     * Sets the {@link LogLevel log level} to be used.
     *
     * @param logLevel The logging level.
     * @return The builder instance for method chaining.
     */
    public EngineConfigBuilder withLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    /**
     * Constructs an {@link EngineConfig} instance from the builder's set values.
     *
     * @return The new {@link EngineConfig} instance.
     */
    public EngineConfig build() {
        return new EngineConfig(
            targetFPS,
            targetUPS,
            windowResolution,
            canvasResolution,
            hardwareAcceleration,
            exceptionAction,
            logLevel
        );
    }
}
