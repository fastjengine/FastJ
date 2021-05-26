package unittest;

import io.github.lucasstarsz.fastj.engine.FastJEngine;

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
        FastJEngine.init("For those sweet, sweet testing purposes", new MockManager(runnable));
        try {
            FastJEngine.run();
        } catch (NullPointerException ignored) {
            // Exception caught to prevent game window opening
        }
    }
}
