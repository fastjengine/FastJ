package tech.fastj.logging;

import org.slf4j.event.Level;

public enum LogLevel {
    Trace(Level.TRACE),
    Debug(Level.DEBUG),
    Info(Level.INFO),
    Warn(Level.WARN),
    Error(Level.ERROR);

    public final Level slf4jLevel;

    LogLevel(Level slf4jLevel) {
        this.slf4jLevel = slf4jLevel;
    }
}
