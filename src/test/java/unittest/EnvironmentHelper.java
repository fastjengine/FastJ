package unittest;

import tech.fastj.engine.FastJEngine;

import tech.fastj.systems.audio.AudioManager;

import unittest.mock.systems.control.MockRunnableSimpleManager;

import java.awt.GraphicsEnvironment;

public class EnvironmentHelper {

  public static final boolean IsEnvironmentHeadless = headlessTest();
  public static final boolean DoesEnvironmentSupportAudioOutput = audioOutputTest();

  private static boolean headlessTest() {
    // because idk how to run github actions in non-headless mode
    boolean isHeadless = GraphicsEnvironment.isHeadless();

    System.out.println(
        "This testing environment is... "
            + (isHeadless
                ? "headless. Well that's unfortunate... this device is running in headless mode, so some tests cannot be conducted."
                : "not headless. Good."));

    return isHeadless;
  }

  private static boolean audioOutputTest() {
    boolean hasAudioOutput = AudioManager.isOutputSupported();

    System.out.println(
        "This testing environment... "
            + (hasAudioOutput
                ? "supports audio. Good."
                : "does not support audio output. Well that's unfortunate... this device does not contain any audio output devices, so audio-related tests cannot be conducted."));

    return hasAudioOutput;
  }

  public static void runFastJWith(Runnable runnable) {
    FastJEngine.forceCloseGame();
    FastJEngine.init(
        "For those sweet, sweet testing purposes", new MockRunnableSimpleManager(runnable));
    try {
      FastJEngine.run();
    } catch (NullPointerException ignored) {
      // Exception caught to prevent game window opening
    }
  }
}
