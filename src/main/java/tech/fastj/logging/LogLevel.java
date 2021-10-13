package tech.fastj.logging;

import org.slf4j.event.Level;

/**
 * Abstraction overtop {@link Level SLF4J's logging level enum}.
 *
 * @author Andrew Dey
 * @see Level
 * @since 1.6.0
 */
public enum LogLevel {
    Error(Level.ERROR),
    Warn(Level.WARN),
    Info(Level.INFO),
    Debug(Level.DEBUG),
    Trace(Level.TRACE);

    public final Level slf4jLevel;

    LogLevel(Level slf4jLevel) {
        this.slf4jLevel = slf4jLevel;
    }
}
