package io.github.lucasstarsz.fastj.engine;

/** The different data options available when calling {@link FastJEngine#getFPSData}. */
public enum FPSValue {
    /** The most recently calculated frame count per second. */
    CURRENT,
    /** The overall average frame count per second. */
    AVERAGE,
    /** The highest frame count recorded in a second. */
    HIGHEST,
    /** The lowest frame count recorded in a second. */
    LOWEST,
    /** The average of the lowest 1% recorded frame count. */
    ONE_PERCENT_LOW
}
