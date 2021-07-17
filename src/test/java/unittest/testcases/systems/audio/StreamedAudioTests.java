package unittest.testcases.systems.audio;

import tech.fastj.systems.audio.AudioManager;
import tech.fastj.systems.audio.StreamedAudio;
import tech.fastj.systems.audio.state.PlaybackState;

import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import unittest.EnvironmentHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class StreamedAudioTests {

    private static final Path TestAudioPath = Path.of("src/test/resources/test_audio.wav");

    @BeforeAll
    public static void onlyRunIfAudioOutputIsSupported() {
        assumeTrue(EnvironmentHelper.DoesEnvironmentSupportAudioOutput);
    }

    @Test
    void checkLoadStreamedAudioInstance_shouldMatchExpectedValues() {
        StreamedAudio audio = AudioManager.loadStreamedAudio(TestAudioPath);

        assertEquals(TestAudioPath, audio.getAudioPath(), "After loading the audio into memory, the gotten audio should have the same path object as the one used to load it in.");
        assertEquals(PlaybackState.Stopped, audio.getCurrentPlaybackState(), "After loading the audio into memory, the gotten audio should be in the \"stopped\" playback state.");
        assertEquals(PlaybackState.Stopped, audio.getPreviousPlaybackState(), "After loading the audio into memory, the gotten audio's previous playback state should also be \"stopped\".");
        assertEquals(0L, audio.getPlaybackPosition(), "After loading the audio into memory, the gotten audio should be at the very beginning with playback position.");
    }

    @Test
    void checkPlayStreamedAudio_shouldTriggerOpenAndStartEvents() throws InterruptedException {
        StreamedAudio audio = AudioManager.loadStreamedAudio(TestAudioPath);
        AtomicBoolean audioOpenEventBoolean = new AtomicBoolean(false);
        AtomicBoolean audioStartEventBoolean = new AtomicBoolean(false);
        audio.getAudioEventListener().setAudioOpenAction(audioEvent -> audioOpenEventBoolean.set(true));
        audio.getAudioEventListener().setAudioStartAction(audioEvent -> audioStartEventBoolean.set(true));

        audio.play();
        TimeUnit.MILLISECONDS.sleep(20);

        assertTrue(audioOpenEventBoolean.get(), "After playing the audio, the \"audio open\" event action should have been triggered.");
        assertTrue(audioStartEventBoolean.get(), "After playing the audio, the \"audio start\" event action should have been triggered.");
    }

    @Test
    void checkPauseStreamedAudio_shouldTriggerPauseAndStopEvents() throws InterruptedException {
        StreamedAudio audio = AudioManager.loadStreamedAudio(TestAudioPath);
        AtomicBoolean audioPauseEventBoolean = new AtomicBoolean(false);
        AtomicBoolean audioStopEventBoolean = new AtomicBoolean(false);
        audio.getAudioEventListener().setAudioPauseAction(audioEvent -> audioPauseEventBoolean.set(true));
        audio.getAudioEventListener().setAudioStopAction(audioEvent -> audioStopEventBoolean.set(true));

        audio.play();
        TimeUnit.MILLISECONDS.sleep(20);
        audio.pause();
        TimeUnit.MILLISECONDS.sleep(20);

        assertTrue(audioPauseEventBoolean.get(), "After pausing the audio, the \"audio pause\" event action should have been triggered.");
        assertTrue(audioStopEventBoolean.get(), "After pausing the audio, the \"audio stop\" event action should have been triggered.");
    }

    @Test
    void checkResumeStreamedAudio_shouldTriggerStartAndResumeEvents() throws InterruptedException {
        StreamedAudio audio = AudioManager.loadStreamedAudio(TestAudioPath);
        AtomicBoolean audioStartEventBoolean = new AtomicBoolean(true);
        AtomicBoolean audioResumeEventBoolean = new AtomicBoolean(false);
        audio.getAudioEventListener().setAudioStartAction(audioEvent -> audioStartEventBoolean.set(!audioStartEventBoolean.get()));
        audio.getAudioEventListener().setAudioResumeAction(audioEvent -> audioResumeEventBoolean.set(true));

        audio.play();
        TimeUnit.MILLISECONDS.sleep(20);
        audio.pause();
        TimeUnit.MILLISECONDS.sleep(20);
        audio.resume();
        TimeUnit.MILLISECONDS.sleep(20);

        assertTrue(audioResumeEventBoolean.get(), "After resuming the audio, the \"audio resume\" event action should have been triggered.");
        assertTrue(audioStartEventBoolean.get(), "After resuming the audio, the \"audio start\" event action should have been triggered.");
    }

    @Test
    void checkStopStreamedAudio_shouldTriggerStopAndCloseEvents() throws InterruptedException {
        StreamedAudio audio = AudioManager.loadStreamedAudio(TestAudioPath);
        AtomicBoolean audioCloseEventBoolean = new AtomicBoolean(false);
        AtomicBoolean audioStopEventBoolean = new AtomicBoolean(false);
        audio.getAudioEventListener().setAudioCloseAction(audioEvent -> audioCloseEventBoolean.set(true));
        audio.getAudioEventListener().setAudioStopAction(audioEvent -> audioStopEventBoolean.set(true));

        audio.play();
        TimeUnit.MILLISECONDS.sleep(20);
        audio.stop();
        TimeUnit.MILLISECONDS.sleep(20);

        assertTrue(audioCloseEventBoolean.get(), "After stopping the audio, the \"audio close\" event action should have been triggered.");
        assertTrue(audioStopEventBoolean.get(), "After stopping the audio, the \"audio stop\" event action should have been triggered.");
    }

    @Test
    void checkGetAudioAfterUnloading() {
        StreamedAudio audio = AudioManager.loadStreamedAudio(TestAudioPath);
        assertNotNull(AudioManager.getStreamedAudio(audio.getID()), "The audio should have been loaded into the audio manager successfully.");

        AudioManager.unloadStreamedAudio(audio.getID());
        assertNull(AudioManager.getStreamedAudio(audio.getID()), "After unloading the audio, it should not be present in the audio manager.");
    }
}
