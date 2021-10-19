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
    /** Level of logging used for reporting errors, exceptions, and other mishaps. */
    Error(Level.ERROR),
    /** Level of logging used for reporting suspicious/unexpected results which can cause issues. */
    Warn(Level.WARN),
    /** Level of logging used for reporting general/often-needed information and statuses. */
    Info(Level.INFO),
    /** Level of logging used for reporting extra details during a program's execution. */
    Debug(Level.DEBUG),
    /** Level of logging used for reporting the deepest needed details and information during a program's execution. */
    Trace(Level.TRACE);

    /** The enum value's corresponding {@link Level SLF4J level enum}. */
    public final Level slf4jLevel;

    LogLevel(Level slf4jLevel) {
        this.slf4jLevel = slf4jLevel;
    }
}
