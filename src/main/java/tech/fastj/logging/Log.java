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

    private static final Logger FastJEngineLog = LoggerFactory.getLogger(FastJEngine.class);

    public static void trace(String format, Object... args) {
        FastJEngineLog.trace(format, args);
    }

    public static <T> void trace(Class<T> loggingClass, String format, Object... args) {
        LoggerFactory.getLogger(loggingClass).trace(format, args);
    }

    public static void debug(String format, Object... args) {
        FastJEngineLog.debug(format, args);
    }

    public static <T> void debug(Class<T> loggingClass, String format, Object... args) {
        LoggerFactory.getLogger(loggingClass).debug(format, args);
    }

    public static void info(String format, Object... args) {
        FastJEngineLog.info(format, args);
    }

    public static <T> void info(Class<T> loggingClass, String format, Object... args) {
        LoggerFactory.getLogger(loggingClass).info(format, args);
    }

    public static void warn(String format, Object... args) {
        FastJEngineLog.warn(format, args);
    }

    public static <T> void warn(Class<T> loggingClass, String format, Object... args) {
        LoggerFactory.getLogger(loggingClass).warn(format, args);
    }

    /**
     * Logs an error
     * @param message The Message to be logged
     * @param exception The Exception caused, for eg. Runtime Exception, etc.
     */
    public static void error(String message, Exception exception) {
        FastJEngineLog.error(message, exception);
    }

    public static <T> void error(Class<T> loggingClass, String message, Exception exception) {
        LoggerFactory.getLogger(loggingClass).error(message, exception);
    }
}
