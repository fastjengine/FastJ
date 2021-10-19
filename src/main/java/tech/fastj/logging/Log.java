package tech.fastj.logging;

import tech.fastj.engine.FastJEngine;

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

    private static final Logger FastJEngineLog = LoggerFactory.getLogger(FastJEngine.class);

    /**
     * Logs the specified message at the {@link LogLevel#Trace trace} level.
     *
     * @param message    The formatted message to log.
     * @param formatting The arguments, if any, of the formatted message.
     */
    public static void trace(String message, Object... formatting) {
        FastJEngineLog.trace(message, formatting);
    }

    /**
     * Logs the specified message at the {@link LogLevel#Trace trace} level using the logging instance of the specified
     * class.
     *
     * @param <T>          The type of the class to get the logging instance of.
     * @param loggingClass The class to get the logging instance of.
     * @param message      The formatted message to log.
     * @param formatting   The arguments, if any, of the formatted message.
     */
    public static <T> void trace(Class<T> loggingClass, String message, Object... formatting) {
        LoggerFactory.getLogger(loggingClass).trace(message, formatting);
    }

    /**
     * Logs the specified message at the {@link LogLevel#Debug debug} level.
     *
     * @param message    The formatted message to log.
     * @param formatting The arguments, if any, of the formatted message.
     */
    public static void debug(String message, Object... formatting) {
        FastJEngineLog.debug(message, formatting);
    }

    /**
     * Logs the specified message at the {@link LogLevel#Debug debug} level.
     *
     * @param <T>          The type of the class to get the logging instance of.
     * @param loggingClass The class to get the logging instance of.
     * @param message      The formatted message to log.
     * @param formatting   The arguments, if any, of the formatted message.
     */
    public static <T> void debug(Class<T> loggingClass, String message, Object... formatting) {
        LoggerFactory.getLogger(loggingClass).debug(message, formatting);
    }

    /**
     * Logs the specified message at the {@link LogLevel#Info info} level.
     *
     * @param message    The formatted message to log.
     * @param formatting The arguments, if any, of the formatted message.
     */
    public static void info(String message, Object... formatting) {
        FastJEngineLog.info(message, formatting);
    }

    /**
     * Logs the specified message at the {@link LogLevel#Info info} level.
     *
     * @param <T>          The type of the class to get the logging instance of.
     * @param loggingClass The class to get the logging instance of.
     * @param message      The formatted message to log.
     * @param formatting   The arguments, if any, of the formatted message.
     */
    public static <T> void info(Class<T> loggingClass, String message, Object... formatting) {
        LoggerFactory.getLogger(loggingClass).info(message, formatting);
    }

    /**
     * Logs the specified message at the {@link LogLevel#Warn warning} level.
     *
     * @param message    The formatted warning to log.
     * @param formatting The arguments, if any, of the formatted warning.
     */
    public static void warn(String message, Object... formatting) {
        FastJEngineLog.warn(message, formatting);
    }

    /**
     * Logs the specified message at the {@link LogLevel#Warn warning} level.
     *
     * @param <T>          The type of the class to get the logging instance of.
     * @param loggingClass The class to get the logging instance of.
     * @param message      The formatted warning to log.
     * @param formatting   The arguments, if any, of the formatted warning.
     */
    public static <T> void warn(Class<T> loggingClass, String message, Object... formatting) {
        LoggerFactory.getLogger(loggingClass).warn(message, formatting);
    }

    /**
     * Forcefully closes the game, then throws the error specified with the error message.
     * <p>
     * This logs the specified error message at the {@link LogLevel#Error error} level.
     * <p>
     * Example usage: {@code Log.error("This is a Runtime Exception : ", new RuntimeException()); }
     *
     * @param message   The Message to be logged
     * @param exception The {@code Exception} causing a need to log the error.
     */
    public static void error(String message, Exception exception) {
        FastJEngineLog.error(message, exception);
    }


    /**
     * Forcefully closes the game, then throws the error specified with the error message.
     * <p>
     * This logs the specified error message at the {@link LogLevel#Error error} level.
     * <p>
     * Example usage: {@code Log.error(MyClass.class, "This is a Runtime Exception : ", new RuntimeException()); }
     *
     * @param <T>          The type of the class to get the logging instance of.
     * @param loggingClass The class to get the logging instance of.
     * @param message   The Message to be logged
     * @param exception The {@code Exception} causing a need to log the error.
     */
    public static <T> void error(Class<T> loggingClass, String message, Exception exception) {
        LoggerFactory.getLogger(loggingClass).error(message, exception);
    }
}
