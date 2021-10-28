package unittest.testcases.logging;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import tech.fastj.engine.FastJEngine;

import tech.fastj.logging.LogLevel;
import unittest.EnvironmentHelper;
import unittest.mock.systems.control.MockEmptySimpleManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class LogLevelTests {

    @BeforeAll
    public static void checkLogLevelOrder_shouldMatchSLF4J() {
        /* The original order for the log levels should always match the following:
         * Error, Warning, Info, Debug, Trace */

        assertEquals(-1, LogLevel.Error.compareTo(LogLevel.Warn), "The \"" + LogLevel.Error + "\" log level should compare to the \"" + LogLevel.Warn + "\" log level as lower.");
        assertEquals(-1, LogLevel.Warn.compareTo(LogLevel.Info), "The \"" + LogLevel.Warn + "\" log level should compare to the \"" + LogLevel.Info + "\" log level as lower.");
        assertEquals(-1, LogLevel.Info.compareTo(LogLevel.Debug), "The \"" + LogLevel.Info + "\" log level should compare to the \"" + LogLevel.Debug + "\" log level as lower.");
        assertEquals(-1, LogLevel.Debug.compareTo(LogLevel.Trace), "The \"" + LogLevel.Debug + "\" log level should compare to the \"" + LogLevel.Trace + "\" log level as lower.");
    }

    @Test
    void checkSetFastJEngineLogLevel() {
        assumeFalse(EnvironmentHelper.IsEnvironmentHeadless);

        FastJEngine.init("Logging Test", new MockEmptySimpleManager());
        assertSame(FastJEngine.DefaultLogLevel, FastJEngine.getLogLevel(), "FastJEngine's initial log level should match its default log level.");

        for (int i = 0; i < LogLevel.values().length; i++) {
            FastJEngine.configureLogging(LogLevel.values()[i]);
            assertSame(LogLevel.values()[i], FastJEngine.getLogLevel(), "FastJEngine's log level \"" + FastJEngine.getLogLevel() + "\" should match the set log level \"" + LogLevel.values()[i] + "\".");
        }

        FastJEngine.forceCloseGame();
    }
}
