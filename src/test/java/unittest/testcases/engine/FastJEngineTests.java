package unittest.testcases.engine;

import tech.fastj.engine.FastJEngine;
import tech.fastj.engine.HWAccel;
import tech.fastj.engine.config.EngineConfig;
import tech.fastj.engine.config.ExceptionAction;
import tech.fastj.graphics.display.Display;

import tech.fastj.systems.control.SimpleManager;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import unittest.EnvironmentHelper;
import unittest.mock.systems.control.MockEmptySimpleManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FastJEngineTests {

    @BeforeAll
    public static void onlyRunIfNotHeadless() {
        assumeFalse(EnvironmentHelper.IsEnvironmentHeadless);
    }

    @Test
    @Order(0)
    void checkRunAfterUpdate() {
        AtomicBoolean ranAfterUpdate = new AtomicBoolean();
        FastJEngine.init("yeet", new SimpleManager() {
            @Override
            public void init(Display display) {
                FastJEngine.runAfterUpdate(() -> {
                    ranAfterUpdate.set(true);
                    FastJEngine.forceCloseGame();
                });
            }

            @Override
            public void update(Display display) {
            }

            @Override
            public void render(Display display) {
            }
        });

        FastJEngine.configureExceptionAction(ExceptionAction.LogError);
        FastJEngine.run();

        assertTrue(ranAfterUpdate.get(), "After one update completes, the ranAfterUpdate boolean should have been set to true.");
    }

    @Test
    @Order(1)
    void checkRunAfterRender() {
        AtomicBoolean ranAfterRender = new AtomicBoolean();
        FastJEngine.init("yeet", new SimpleManager() {
            @Override
            public void init(Display display) {
                FastJEngine.runAfterRender(() -> {
                    ranAfterRender.set(true);
                    FastJEngine.forceCloseGame();
                });
            }

            @Override
            public void update(Display display) {
            }

            @Override
            public void render(Display display) {
            }
        });

        FastJEngine.configureExceptionAction(ExceptionAction.LogError);
        FastJEngine.run();

        assertTrue(ranAfterRender.get(), "After one render completes, the ranAfterRender boolean should have been set to true.");
    }

    @Test
    @Order(2)
    void checkEngineInitConfig_shouldMatchDefault() {
        String gameTitle = UUID.randomUUID().toString();

        FastJEngine.init(gameTitle, new MockEmptySimpleManager());

        assertEquals(gameTitle, FastJEngine.getDisplay().getTitle(), "The display title should match the original title.");
        assertEquals(EngineConfig.Default.targetFPS(), FastJEngine.getTargetFPS(), "The engine config FPS should match the default FPS.");
        assertEquals(EngineConfig.Default.targetUPS(), FastJEngine.getTargetUPS(), "The engine config UPS should match the default UPS.");
        assertEquals(EngineConfig.Default.windowResolution(), FastJEngine.getDisplay().getWindowResolution(), "The engine config window resolution should match the default window resolution.");
        assertEquals(EngineConfig.Default.internalResolution(), FastJEngine.getDisplay().getInternalResolution(), "The engine config internal resolution should match the default internal resolution.");
        assertEquals(EngineConfig.Default.hardwareAcceleration(), FastJEngine.getHardwareAcceleration(), "The engine config hardware acceleration should match the default hardware acceleration.");
        assertEquals(EngineConfig.Default.exceptionAction(), FastJEngine.getExceptionAction(), "The engine config exception action should match the default exception action.");
        assertEquals(EngineConfig.Default.logLevel(), FastJEngine.getLogLevel(), "The engine config log level should match the default log level.");
    }
}
