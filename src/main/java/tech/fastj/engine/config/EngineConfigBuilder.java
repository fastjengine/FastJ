package tech.fastj.engine.config;

import tech.fastj.engine.FastJEngine;
import tech.fastj.engine.HWAccel;
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

    public EngineConfigBuilder withTargetFPS(int targetFPS) {
        this.targetFPS = targetFPS;
        return this;
    }

    public EngineConfigBuilder withTargetUPS(int targetUPS) {
        this.targetUPS = targetUPS;
        return this;
    }

    public EngineConfigBuilder withWindowResolution(Point windowResolution) {
        this.windowResolution = windowResolution.copy();
        return this;
    }

    public EngineConfigBuilder withCanvasResolution(Point canvasResolution) {
        this.canvasResolution = canvasResolution.copy();
        return this;
    }

    public EngineConfigBuilder withHardwareAcceleration(HWAccel hardwareAcceleration) {
        this.hardwareAcceleration = hardwareAcceleration;
        return this;
    }

    public EngineConfigBuilder withExceptionAction(ExceptionAction exceptionAction) {
        this.exceptionAction = exceptionAction;
        return this;
    }

    public EngineConfigBuilder withLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
        return this;
    }

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
