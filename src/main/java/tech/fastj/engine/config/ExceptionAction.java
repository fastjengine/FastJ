package tech.fastj.engine.config;

import tech.fastj.engine.FastJEngine;

/**
 * {@link EngineConfig Engine configuration} determining what the {@link FastJEngine} should do upon receiving an
 * exception.
 *
 * @author Andrew Dey
 * @since 1.6.0
 */
public enum ExceptionAction {
    /** If the engine receives an exception, it will be thrown out and should be handled by the programmer running it. */
    Throw,
    /** If the engine receives an exception, it will be logged. The program will continue running, if possible. */
    LogError,
    /** If the engine receives an exception, it will be disregarded. The program will continue running, if possible. */
    Nothing
}
