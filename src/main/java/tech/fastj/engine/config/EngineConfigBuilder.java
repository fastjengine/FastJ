package tech.fastj.engine.config;

import tech.fastj.engine.FastJEngine;
import tech.fastj.engine.HWAccel;
import tech.fastj.logging.LogLevel;
import tech.fastj.math.Point;

public class EngineConfigBuilder {

    private int targetFPS = FastJEngine.DefaultFPS;
    private int targetUPS = FastJEngine.DefaultUPS;

    private Point windowResolution = FastJEngine.DefaultWindowResolution.copy();
    private Point internalResolution = FastJEngine.DefaultInternalResolution.copy();

    private HWAccel hardwareAcceleration = HWAccel.Default;

    private ExceptionAction exceptionAction;
    private LogLevel logLevel;

    EngineConfigBuilder(ExceptionAction exceptionAction, LogLevel logLevel) {
        this.exceptionAction = exceptionAction;
        this.logLevel = logLevel;
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

    public EngineConfigBuilder withInternalResolution(Point internalResolution) {
        this.internalResolution = internalResolution.copy();
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
                internalResolution,
                hardwareAcceleration,
                exceptionAction,
                logLevel
        );
    }
}
