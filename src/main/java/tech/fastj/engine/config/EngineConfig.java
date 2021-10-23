package tech.fastj.engine.config;

import tech.fastj.engine.HWAccel;
import tech.fastj.logging.LogLevel;
import tech.fastj.math.Point;

import java.util.Objects;

public class EngineConfig {

    public static final EngineConfig Default = EngineConfig.create().build();

    private final int targetFPS;
    private final int targetUPS;

    private final Point windowResolution;
    private final Point internalResolution;

    private final HWAccel hardwareAcceleration;

    private final ExceptionAction exceptionAction;
    private final LogLevel logLevel;

    EngineConfig(int targetFPS, int targetUPS, Point windowResolution, Point internalResolution, HWAccel hardwareAcceleration, ExceptionAction exceptionAction, LogLevel logLevel) {
        this.targetFPS = targetFPS;
        this.targetUPS = targetUPS;
        this.windowResolution = windowResolution;
        this.internalResolution = internalResolution;
        this.hardwareAcceleration = hardwareAcceleration;
        this.exceptionAction = exceptionAction;
        this.logLevel = logLevel;
    }

    public static EngineConfigBuilder create() {
        return new EngineConfigBuilder();
    }

    public int targetFPS() {
        return targetFPS;
    }

    public int targetUPS() {
        return targetUPS;
    }

    public Point windowResolution() {
        return windowResolution;
    }

    public Point internalResolution() {
        return internalResolution;
    }

    public HWAccel hardwareAcceleration() {
        return hardwareAcceleration;
    }

    public ExceptionAction exceptionAction() {
        return exceptionAction;
    }

    public LogLevel logLevel() {
        return logLevel;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        EngineConfig engineConfig = (EngineConfig) object;
        return targetFPS == engineConfig.targetFPS
                && targetUPS == engineConfig.targetUPS
                && windowResolution.equals(engineConfig.windowResolution)
                && internalResolution.equals(engineConfig.internalResolution)
                && hardwareAcceleration == engineConfig.hardwareAcceleration
                && exceptionAction == engineConfig.exceptionAction
                && logLevel == engineConfig.logLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                targetFPS,
                targetUPS,
                windowResolution,
                internalResolution,
                hardwareAcceleration,
                exceptionAction,
                logLevel
        );
    }

    @Override
    public String toString() {
        return "EngineSettings{" +
                "targetFPS=" + targetFPS +
                ", targetUPS=" + targetUPS +
                ", windowResolution=" + windowResolution +
                ", internalResolution=" + internalResolution +
                ", hardwareAcceleration=" + hardwareAcceleration +
                ", exceptionAction=" + exceptionAction +
                ", logLevel=" + logLevel +
                '}';
    }
}
