package unittest.testcases.systems.audio;

import tech.fastj.systems.audio.AudioManager;
import tech.fastj.systems.audio.PlaybackState;
import tech.fastj.systems.audio.StreamedAudio;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import unittest.EnvironmentHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class StreamedAudioTests {

    private static final Path TestAudioPath = Path.of("src/test/resources/test_audio.wav");

    @BeforeAll
    public static void onlyRunIfAudioOutputIsSupported() {
        assumeTrue(EnvironmentHelper.DoesEnvironmentSupportAudioOutput);
    }

    @Test
    void checkLoadStreamedAudioInstance_shouldMatchExpectedValues() {
        StreamedAudio audio = AudioManager.loadStreamedAudioInstance(TestAudioPath);

        assertEquals(TestAudioPath, audio.getAudioPath(), "After loading the audio into memory, the gotten audio should have the same path object as the one used to load it in.");
        assertEquals(PlaybackState.Stopped, audio.getCurrentPlaybackState(), "After loading the audio into memory, the gotten audio should be in the \"stopped\" playback state.");
        assertEquals(PlaybackState.Stopped, audio.getPreviousPlaybackState(), "After loading the audio into memory, the gotten audio's previous playback state should also be \"stopped\".");
        assertEquals(0L, audio.getPlaybackPosition(), "After loading the audio into memory, the gotten audio should be at the very beginning with playback position.");
    }

    @Test
    void checkGetAudioAfterUnloading() {
        StreamedAudio audio = AudioManager.loadStreamedAudioInstance(TestAudioPath);
        assertNotNull(AudioManager.getStreamedAudio(audio.getID()), "The audio should have been loaded into the audio manager successfully.");

        AudioManager.unloadStreamedAudioInstance(audio.getID());
        assertNull(AudioManager.getStreamedAudio(audio.getID()), "After unloading the audio, it should not be present in the audio manager.");
    }
}
