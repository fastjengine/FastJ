package unittest.testcases.engine;

import tech.fastj.engine.FastJEngine;
import tech.fastj.graphics.display.FastJCanvas;

import tech.fastj.systems.control.SimpleManager;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import unittest.EnvironmentHelper;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

class FastJEngineTests {

    @BeforeAll
    public static void onlyRunIfNotHeadless() {
        assumeFalse(EnvironmentHelper.IsEnvironmentHeadless);
    }

    @Test
    void checkRunAfterUpdate() {
        AtomicBoolean ranAfterUpdate = new AtomicBoolean();
        FastJEngine.init("yeet", new SimpleManager() {
            @Override
            public void init(FastJCanvas canvas) {
                FastJEngine.runAfterUpdate(() -> {
                    ranAfterUpdate.set(true);
                    FastJEngine.forceCloseGame();
                });
            }

            @Override
            public void update(FastJCanvas canvas) {
            }

            @Override
            public void render(FastJCanvas canvas) {
            }
        });
        FastJEngine.run();

        assertTrue(ranAfterUpdate.get(), "After one update completes, the ranAfterUpdate boolean should have been set to true.");
    }

    @Test
    void checkRunAfterRender() {
        AtomicBoolean ranAfterRender = new AtomicBoolean();
        FastJEngine.init("yeet", new SimpleManager() {
            @Override
            public void init(FastJCanvas canvas) {
                FastJEngine.runAfterRender(() -> {
                    ranAfterRender.set(true);
                    FastJEngine.forceCloseGame();
                });
            }

            @Override
            public void update(FastJCanvas canvas) {
            }

            @Override
            public void render(FastJCanvas canvas) {
            }
        });
        FastJEngine.run();

        assertTrue(ranAfterRender.get(), "After one render completes, the ranAfterRender boolean should have been set to true.");
    }
}
