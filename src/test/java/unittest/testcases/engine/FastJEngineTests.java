package unittest.testcases.engine;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import tech.fastj.engine.FastJEngine;

import tech.fastj.graphics.display.Display;

import tech.fastj.systems.control.SimpleManager;

import unittest.EnvironmentHelper;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FastJEngineTests {

  @BeforeAll
  public static void onlyRunIfNotHeadless() {
    assumeFalse(EnvironmentHelper.IsEnvironmentHeadless);
  }

  @Test
  void checkRunAfterUpdate() {
    AtomicBoolean ranAfterUpdate = new AtomicBoolean();
    FastJEngine.init(
        "yeet",
        new SimpleManager() {
          @Override
          public void init(Display display) {
            FastJEngine.runAfterUpdate(
                () -> {
                  ranAfterUpdate.set(true);
                  FastJEngine.forceCloseGame();
                });
          }

          @Override
          public void update(Display display) {}

          @Override
          public void render(Display display) {}
        });
    FastJEngine.run();

    assertTrue(
        ranAfterUpdate.get(),
        "After one update completes, the ranAfterUpdate boolean should have been set to true.");
  }

  @Test
  void checkRunAfterRender() {
    AtomicBoolean ranAfterRender = new AtomicBoolean();
    FastJEngine.init(
        "yeet",
        new SimpleManager() {
          @Override
          public void init(Display display) {
            FastJEngine.runAfterRender(
                () -> {
                  ranAfterRender.set(true);
                  FastJEngine.forceCloseGame();
                });
          }

          @Override
          public void update(Display display) {}

          @Override
          public void render(Display display) {}
        });
    FastJEngine.run();

    assertTrue(
        ranAfterRender.get(),
        "After one render completes, the ranAfterRender boolean should have been set to true.");
  }
}
