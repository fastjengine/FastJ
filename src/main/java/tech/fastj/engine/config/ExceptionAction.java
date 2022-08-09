package tech.fastj.engine.config;

import tech.fastj.engine.FastJEngine;

/**
 * {@link EngineConfig Engine configuration} determining what the {@link FastJEngine} should do if an exception occurs while running.
 *
 * @author Andrew Dey
 * @since 1.6.0
 */
public enum ExceptionAction {
    /** If the engine receives an exception, it will be logged and thrown, to be handled by the programmer running it. */
    Throw,
    /** If the engine receives an exception, it will be logged. The program will continue running, if possible. */
    LogError,
    /** If the engine receives an exception, it will be disregarded. The program will continue running, if possible. */
    Nothing
}
