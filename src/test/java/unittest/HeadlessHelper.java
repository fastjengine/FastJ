package unittest;

import io.github.lucasstarsz.fastj.engine.FastJEngine;

import io.github.lucasstarsz.fastj.systems.control.Scene;
import io.github.lucasstarsz.fastj.graphics.Display;

import java.awt.GraphicsEnvironment;

import unittest.mock.MockManager;

public class HeadlessHelper {

    public static final boolean isEnvironmentHeadless = headlessTest();

    private static boolean headlessTest() {
        // because idk how to run github actions in non-headless mode
        boolean isHeadless = GraphicsEnvironment.isHeadless();

        System.out.println(
                "This testing environment is... " + (isHeadless
                        ? "headless. Well that's unfortunate... this device isn't running in headless mode, so some tests cannot be conducted."
                        : "not headless. Good."
                )
        );

        return isHeadless;
    }

    public static void runFastJWith(Runnable runnable) {
        FastJEngine.init("For those sweet, sweet testing purposes", new MockManager(new Scene("") {
            @Override
            public void load(Display display) {
                runnable.run();
            }

            @Override
            public void unload(Display display) {
            }

            @Override
            public void update(Display display) {
                FastJEngine.closeGame();
            }
        }));

        FastJEngine.run();
    }
}
