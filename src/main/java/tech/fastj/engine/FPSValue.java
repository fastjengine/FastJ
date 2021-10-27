package tech.fastj.engine;

/** The different data options available when calling {@link FastJEngine#getFPSData}. */
public enum FPSValue {
  /** The most recently calculated frame count per second. */
  Current,
  /** The overall average frame count per second. */
  Average,
  /** The highest frame count recorded in a second. */
  Highest,
  /** The lowest frame count recorded in a second. */
  Lowest,
  /** The average of the lowest 1% recorded frame count. */
  OnePercentLow
}
