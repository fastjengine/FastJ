package unittest.testcases.engine;

import tech.fastj.engine.FastJEngine;
import tech.fastj.graphics.display.Display;

import tech.fastj.systems.control.SimpleManager;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import unittest.EnvironmentHelper;

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
                FastJEngine.runAfterUpdate(() -> ranAfterUpdate.set(true));
            }

            @Override
            public void update(Display display) {
            }

            @Override
            public void render(Display display) {
            }
        });

        Executors.newSingleThreadScheduledExecutor().schedule(FastJEngine::forceCloseGame, 1, TimeUnit.SECONDS);
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
                FastJEngine.runAfterRender(() -> ranAfterRender.set(true));
            }

            @Override
            public void update(Display display) {
            }

            @Override
            public void render(Display display) {
            }
        });

        Executors.newSingleThreadScheduledExecutor().schedule(FastJEngine::forceCloseGame, 1, TimeUnit.SECONDS);
        FastJEngine.run();

        assertTrue(ranAfterRender.get(), "After one render completes, the ranAfterRender boolean should have been set to true.");
    }
}
