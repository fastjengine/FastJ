package tech.fastj.engine.config;

import tech.fastj.engine.FastJEngine;
import tech.fastj.engine.HWAccel;
import tech.fastj.logging.LogLevel;
import tech.fastj.math.Point;
import tech.fastj.systems.control.LogicManager;

import java.util.Objects;

/**
 * Simple {@link FastJEngine#init(String, LogicManager, EngineConfig) Engine configuration} storage.
 * <p>
 * Comes with a {@link EngineConfigBuilder builder} class to provide easy usage: {@link #create()}.
 * <p>
 * All values are sanitized upon adding, to ensure if something is not correct it will be replaced with their respective default value from
 * {@link FastJEngine}.
 */
public record EngineConfig(int targetFPS, int targetUPS, Point windowResolution, Point canvasResolution, HWAccel hardwareAcceleration,
                           ExceptionAction exceptionAction, LogLevel logLevel) {

    public static final EngineConfig Default = EngineConfig.create().build();

    public EngineConfig(int targetFPS, int targetUPS, Point windowResolution, Point canvasResolution, HWAccel hardwareAcceleration,
                        ExceptionAction exceptionAction, LogLevel logLevel) {
        this.targetFPS = targetFPS > 0 ? targetFPS : FastJEngine.DefaultFPS;
        this.targetUPS = targetUPS > 0 ? targetUPS : FastJEngine.DefaultUPS;
        this.windowResolution = Objects.requireNonNullElse(windowResolution, FastJEngine.DefaultWindowResolution).copy();
        this.canvasResolution = Objects.requireNonNullElse(canvasResolution, FastJEngine.DefaultCanvasResolution).copy();
        this.hardwareAcceleration = Objects.requireNonNullElse(hardwareAcceleration, FastJEngine.DefaultHardwareAcceleration);
        this.exceptionAction = Objects.requireNonNullElse(exceptionAction, FastJEngine.DefaultExceptionAction);
        this.logLevel = Objects.requireNonNullElse(logLevel, FastJEngine.DefaultLogLevel);
    }

    public static EngineConfigBuilder create() {
        return new EngineConfigBuilder();
    }
}
