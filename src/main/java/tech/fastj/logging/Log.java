package tech.fastj.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Static class abstraction overtop {@link Logger SLF4J's logger}, providing formatted logging methods.
 *
 * @author Andrew Dey
 * @see Logger
 * @since 1.6.0
 */
public class Log {

    private Log() {
        throw new IllegalStateException();
    }

    private static final Logger BaseLog = LoggerFactory.getLogger(Log.class);

    /**
     * Logs the specified message at the {@link LogLevel#Trace trace} level.
     * <p>
     * Example usage: {@code Log.trace("This is a {}", "trace message"); }
     *
     * @param message    The formatted trace message to log.
     * @param formatting The arguments, if any, of the formatted message.
     */
    public static void trace(String message, Object... formatting) {
        BaseLog.trace(message, formatting);
    }

    /**
     * Logs the specified message at the {@link LogLevel#Trace trace} level using the logging instance of the specified
     * class.
     * <p>
     * Example usage: {@code Log.trace(MyClass.class, "This is a {}", "trace message"); }
     *
     * @param <T>          The type of the class to get the logging instance of.
     * @param loggingClass The class to get the logging instance of.
     * @param message      The formatted trace message to log.
     * @param formatting   The arguments, if any, of the formatted message.
     */
    public static <T> void trace(Class<T> loggingClass, String message, Object... formatting) {
        LoggerFactory.getLogger(loggingClass).trace(message, formatting);
    }

    /**
     * Logs the specified message at the {@link LogLevel#Debug debug} level.
     * <p>
     * Example usage: {@code Log.debug("This is a {}", "debug message"); }
     *
     * @param message    The formatted debug message to log.
     * @param formatting The arguments, if any, of the formatted message.
     */
    public static void debug(String message, Object... formatting) {
        BaseLog.debug(message, formatting);
    }

    /**
     * Logs the specified message at the {@link LogLevel#Debug debug} level using the logging instance of the specified
     * class.
     * <p>
     * Example usage: {@code Log.debug(MyClass.class, "This is a {}", "debug message"); }
     *
     * @param <T>          The type of the class to get the logging instance of.
     * @param loggingClass The class to get the logging instance of.
     * @param message      The formatted debug message to log.
     * @param formatting   The arguments, if any, of the formatted message.
     */
    public static <T> void debug(Class<T> loggingClass, String message, Object... formatting) {
        LoggerFactory.getLogger(loggingClass).debug(message, formatting);
    }

    /**
     * Logs the specified message at the {@link LogLevel#Info info} level.
     * <p>
     * Example usage: {@code Log.info("This is an {}", "info message"); }
     *
     * @param message    The formatted info message to log.
     * @param formatting The arguments, if any, of the formatted message.
     */
    public static void info(String message, Object... formatting) {
        BaseLog.info(message, formatting);
    }

    /**
     * Logs the specified message at the {@link LogLevel#Info info} level using the logging instance of the specified
     * class.
     * <p>
     * Example usage: {@code Log.info(MyClass.class, "This is an {}", "info message"); }
     *
     * @param <T>          The type of the class to get the logging instance of.
     * @param loggingClass The class to get the logging instance of.
     * @param message      The formatted info message to log.
     * @param formatting   The arguments, if any, of the formatted message.
     */
    public static <T> void info(Class<T> loggingClass, String message, Object... formatting) {
        LoggerFactory.getLogger(loggingClass).info(message, formatting);
    }

    /**
     * Logs the specified message at the {@link LogLevel#Warn warning} level.
     * <p>
     * Example usage: {@code Log.warn("This is a {}", "warning message"); }
     *
     * @param message    The formatted warning message to log.
     * @param formatting The arguments, if any, of the formatted warning.
     */
    public static void warn(String message, Object... formatting) {
        BaseLog.warn(message, formatting);
    }

    /**
     * Logs the specified message at the {@link LogLevel#Warn warning} level using the logging instance of the specified
     * class.
     * <p>
     * Example usage: {@code Log.warn(MyClass.class, "This is a {}", "warning message"); }
     *
     * @param <T>          The type of the class to get the logging instance of.
     * @param loggingClass The class to get the logging instance of.
     * @param message      The formatted warning message to log.
     * @param formatting   The arguments, if any, of the formatted warning.
     */
    public static <T> void warn(Class<T> loggingClass, String message, Object... formatting) {
        LoggerFactory.getLogger(loggingClass).warn(message, formatting);
    }

    /**
     * Logs the specified error message at the {@link LogLevel#Error error} level.
     * <p>
     * Example usage: {@code Log.error("This is an error message", new RuntimeException()); }
     *
     * @param message   The error message to log.
     * @param exception The {@code Exception} causing a need to log the error.
     */
    public static void error(String message, Exception exception) {
        BaseLog.error(message, exception);
    }


    /**
     * Logs the specified error message at the {@link LogLevel#Error error} level using the logging instance of the
     * specified class.
     * <p>
     * Example usage: {@code Log.error(MyClass.class, "This is an error message", new RuntimeException()); }
     *
     * @param <T>          The type of the class to get the logging instance of.
     * @param loggingClass The class to get the logging instance of.
     * @param message      The error message to log.
     * @param throwable    The {@code Throwable} causing a need to log the error.
     */
    public static <T> void error(Class<T> loggingClass, String message, Throwable throwable) {
        LoggerFactory.getLogger(loggingClass).error(message, throwable);
    }
}
