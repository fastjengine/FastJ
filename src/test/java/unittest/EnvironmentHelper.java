package unittest;

import tech.fastj.engine.FastJEngine;

import tech.fastj.systems.audio.AudioManager;

import unittest.mock.systems.control.MockRunnableSimpleManager;

import java.awt.GraphicsEnvironment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvironmentHelper {

    private static final Logger Log = LoggerFactory.getLogger(EnvironmentHelper.class);

    public static final boolean IsEnvironmentHeadless = headlessTest();
    public static final boolean DoesEnvironmentSupportAudioOutput = audioOutputTest();

    private static boolean headlessTest() {
        /* Currently, we don't have all-OS access to non-headless computers with GitHub Actions.
         *
         * If a test uses functions that are only available in non-headless mode, this
         * (EnvironmentHelper#IsEnvironmentHeadless) can be used to ensure the test is not run -- a
         * HeadlessException would occur otherwise. */

        boolean isHeadless = GraphicsEnvironment.isHeadless();
        String headlessResult = isHeadless
                                ? "headless. Well that's unfortunate... this device is running in headless mode, so some display-related tests cannot be run."
                                : "not headless. All display-related tests will be run.";

        Log.info("This testing environment is... {}", headlessResult);

        return isHeadless;
    }

    private static boolean audioOutputTest() {
        /* Currently, we don't have all-OS access to computers with audio support.
         * (See: javax.sound.sampled.AudioSystem#getClip)
         *
         * If a test uses functions that require audio output support, this
         * (EnvironmentHelper#DoesEnvironmentSupportAudioOutput) can be used to ensure the test is
         * not run -- a HeadlessException would occur otherwise. */

        boolean hasAudioOutput = AudioManager.isOutputSupported();
        String audioOutputResult = hasAudioOutput
                                   ? "supports audio. All audio-related tests will be run."
                                   : "does not support audio output. Well that's unfortunate... this device does not contain any audio output devices, so audio-related tests cannot be run.";

        Log.info("This testing environment... {}", audioOutputResult);

        return hasAudioOutput;
    }

    public static void runFastJWith(Runnable runnable) {
        FastJEngine.forceCloseGame();
        FastJEngine.init("For those sweet, sweet testing purposes", new MockRunnableSimpleManager(runnable));
        try {
            FastJEngine.run();
        } catch (NullPointerException ignored) {
            // Exception caught to prevent game window opening
        }
    }
}
