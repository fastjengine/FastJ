package unittest.testcases.engine.config;

import tech.fastj.engine.FastJEngine;
import tech.fastj.engine.HWAccel;
import tech.fastj.engine.config.EngineConfig;
import tech.fastj.engine.config.ExceptionAction;
import tech.fastj.logging.LogLevel;
import tech.fastj.math.Maths;
import tech.fastj.math.Point;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EngineConfigTests {

    @Test
    void checkEngineConfigDefaults_shouldMatchEngineDefaults() {
        assertEquals(FastJEngine.DefaultFPS, EngineConfig.Default.targetFPS(), "The default engine config FPS should match the default FPS.");
        assertEquals(FastJEngine.DefaultUPS, EngineConfig.Default.targetUPS(), "The default engine config UPS should match the default UPS.");
        assertEquals(FastJEngine.DefaultWindowResolution, EngineConfig.Default.windowResolution(), "The default engine config window resolution should match the default window resolution.");
        assertEquals(FastJEngine.DefaultCanvasResolution, EngineConfig.Default.internalResolution(), "The default engine config internal resolution should match the default canvas resolution.");
        assertEquals(FastJEngine.DefaultHardwareAcceleration, EngineConfig.Default.hardwareAcceleration(), "The default engine config hardware acceleration should match the default hardware acceleration.");
        assertEquals(FastJEngine.DefaultExceptionAction, EngineConfig.Default.exceptionAction(), "The default engine config exception action should match the default exception action.");
        assertEquals(FastJEngine.DefaultLogLevel, EngineConfig.Default.logLevel(), "The default engine config log level should match the default log level.");
    }

    @Test
    void checkEngineConfigCreation_shouldMatchGeneratedValues() {
        int fps = Maths.randomInteger(1, 100);
        int ups = Maths.randomInteger(1, 100);
        Point windowResolution = new Point(Maths.randomInteger(1, 1920), Maths.randomInteger(1, 1080));
        Point internalResolution = new Point(Maths.randomInteger(1, 1920), Maths.randomInteger(1, 1080));
        HWAccel hardwareAcceleration = HWAccel.values()[Maths.randomInteger(0, HWAccel.values().length - 1)];
        ExceptionAction exceptionAction = ExceptionAction.values()[Maths.randomInteger(0, ExceptionAction.values().length - 1)];
        LogLevel logLevel = LogLevel.values()[Maths.randomInteger(0, LogLevel.values().length - 1)];

        EngineConfig engineConfig = EngineConfig.create()
                .withTargetFPS(fps)
                .withTargetUPS(ups)
                .withWindowResolution(windowResolution)
                .withInternalResolution(internalResolution)
                .withHardwareAcceleration(hardwareAcceleration)
                .withExceptionAction(exceptionAction)
                .withLogLevel(logLevel)
                .build();

        assertEquals(fps, engineConfig.targetFPS(), "The engine config FPS should match the randomly generated FPS.");
        assertEquals(ups, engineConfig.targetUPS(), "The engine config UPS should match the randomly generated UPS.");
        assertEquals(windowResolution, engineConfig.windowResolution(), "The engine config window resolution should match the randomly generated window resolution.");
        assertEquals(internalResolution, engineConfig.internalResolution(), "The engine config internal resolution should match the randomly generated internal resolution.");
        assertEquals(hardwareAcceleration, engineConfig.hardwareAcceleration(), "The engine config hardware acceleration should match the randomly generated hardware acceleration.");
        assertEquals(exceptionAction, engineConfig.exceptionAction(), "The engine config exception action should match the randomly generated exception action.");
        assertEquals(logLevel, engineConfig.logLevel(), "The engine config log level should match the randomly generated log level.");
    }
}
