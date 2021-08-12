package tech.fastj.logging;

import tech.fastj.engine.FastJEngine;

import org.slf4j.LoggerFactory;

public class Log {

    public static void trace(String format, Object... args) {
        LoggerFactory.getLogger(FastJEngine.class).trace(format, args);
    }

    public static <T> void trace(Class<T> loggingClass, String format, Object... args) {
        LoggerFactory.getLogger(loggingClass).trace(format, args);
    }

    public static void debug(String format, Object... args) {
        LoggerFactory.getLogger(FastJEngine.class).debug(format, args);
    }

    public static <T> void debug(Class<T> loggingClass, String format, Object... args) {
        LoggerFactory.getLogger(loggingClass).debug(format, args);
    }

    public static void info(String format, Object... args) {
        LoggerFactory.getLogger(FastJEngine.class).info(format, args);
    }

    public static <T> void info(Class<T> loggingClass, String format, Object... args) {
        LoggerFactory.getLogger(loggingClass).info(format, args);
    }

    public static void warn(String format, Object... args) {
        LoggerFactory.getLogger(FastJEngine.class).warn(format, args);
    }

    public static <T> void warn(Class<T> loggingClass, String format, Object... args) {
        LoggerFactory.getLogger(loggingClass).warn(format, args);
    }

    public static void error(String message, Exception exception) {
        LoggerFactory.getLogger(FastJEngine.class).error(message, exception);
    }

    public static <T> void error(Class<T> loggingClass, String message, Exception exception) {
        LoggerFactory.getLogger(loggingClass).error(message, exception);
    }
}
