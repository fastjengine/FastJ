package io.github.lucasstarsz.fastj.engine;

/**
 * The different data options available when using {@code FastJEngine.getFPSData()}.
 */
public enum FPSValue {
    /** Enum constant defining the need to get information on the most recently calculated frame count per second. */
    CURRENT,
    /** Enum constant defining the need to get information on the overall average frame count per second. */
    AVERAGE,
    /** Enum constant defining the need to get information on the absolute highest frame count per second. */
    HIGHEST,
    /** Enum constant defining the need to get information on the absolute lowest frame count per second. */
    LOWEST,
    /** Enum constant defining the need to get information on the 1% low for the FPS. */
    ONE_PERCENT_LOW
}
