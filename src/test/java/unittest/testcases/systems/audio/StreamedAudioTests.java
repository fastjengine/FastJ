package unittest.testcases.systems.audio;

import tech.fastj.engine.FastJEngine;
import tech.fastj.systems.audio.AudioManager;
import tech.fastj.systems.audio.StreamedAudio;
import tech.fastj.systems.audio.state.PlaybackState;

import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.AfterAll;
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
    private static final URL TestAudioURL = Objects.requireNonNull(StreamedAudioTests.class.getClassLoader().getResource("test_audio.wav"));
    private static final AudioManager AudioManager = FastJEngine.getAudioManager();

    @BeforeAll
    public static void onlyRunIfAudioOutputIsSupported() {
        assumeTrue(EnvironmentHelper.DoesEnvironmentSupportAudioOutput);
    }

    @BeforeAll
    public static void initAudioManager() {
        FastJEngine.getAudioManager().init();
    }

    @AfterAll
    public static void resetAudioManager() {
        FastJEngine.getAudioManager().reset();
    }

    @Test
    void checkLoadStreamedAudioInstance_withPath_shouldMatchExpectedValues() {
        StreamedAudio audio = AudioManager.loadStreamedAudio(TestAudioPath);

        assertEquals(TestAudioPath, audio.getAudioPath(), "After loading the audio into memory, the gotten audio should have the same path object as the one used to load it in.");
        assertEquals(PlaybackState.Stopped, audio.getCurrentPlaybackState(), "After loading the audio into memory, the gotten audio should be in the \"stopped\" playback state.");
        assertEquals(PlaybackState.Stopped, audio.getPreviousPlaybackState(), "After loading the audio into memory, the gotten audio's previous playback state should also be \"stopped\".");
        assertEquals(0L, audio.getPlaybackPosition(), "After loading the audio into memory, the gotten audio should be at the very beginning with playback position.");
        assertNull(audio.getAudioEventListener()
            .getAudioOpenAction(), "After loading the audio into memory, the gotten audio's event listener should not contain an \"audio open\" event action.");
        assertNull(audio.getAudioEventListener()
            .getAudioStartAction(), "After loading the audio into memory, the gotten audio's event listener should not contain an \"audio start\" event action.");
        assertNull(audio.getAudioEventListener()
            .getAudioPauseAction(), "After loading the audio into memory, the gotten audio's event listener should not contain an \"audio pause\" event action.");
        assertNull(audio.getAudioEventListener()
            .getAudioResumeAction(), "After loading the audio into memory, the gotten audio's event listener should not contain an \"audio resume\" event action.");
        assertNull(audio.getAudioEventListener()
            .getAudioStopAction(), "After loading the audio into memory, the gotten audio's event listener should not contain an \"audio stop\" event action.");
        assertNull(audio.getAudioEventListener()
            .getAudioCloseAction(), "After loading the audio into memory, the gotten audio's event listener should not contain an \"audio close\" event action.");
    }

    @Test
    void checkLoadStreamedAudioInstance_withURL_shouldMatchExpectedValues() {
        StreamedAudio audio = AudioManager.loadStreamedAudio(TestAudioURL);

        assertTrue(audio.getAudioPath()
            .endsWith("test_audio.wav"), "After loading the audio into memory, the gotten audio should end with the same path to the audio object as the one used to load it in.");
        assertEquals(PlaybackState.Stopped, audio.getCurrentPlaybackState(), "After loading the audio into memory, the gotten audio should be in the \"stopped\" playback state.");
        assertEquals(PlaybackState.Stopped, audio.getPreviousPlaybackState(), "After loading the audio into memory, the gotten audio's previous playback state should also be \"stopped\".");
        assertEquals(0L, audio.getPlaybackPosition(), "After loading the audio into memory, the gotten audio should be at the very beginning with playback position.");
        assertNull(audio.getAudioEventListener()
            .getAudioOpenAction(), "After loading the audio into memory, the gotten audio's event listener should not contain an \"audio open\" event action.");
        assertNull(audio.getAudioEventListener()
            .getAudioStartAction(), "After loading the audio into memory, the gotten audio's event listener should not contain an \"audio start\" event action.");
        assertNull(audio.getAudioEventListener()
            .getAudioPauseAction(), "After loading the audio into memory, the gotten audio's event listener should not contain an \"audio pause\" event action.");
        assertNull(audio.getAudioEventListener()
            .getAudioResumeAction(), "After loading the audio into memory, the gotten audio's event listener should not contain an \"audio resume\" event action.");
        assertNull(audio.getAudioEventListener()
            .getAudioStopAction(), "After loading the audio into memory, the gotten audio's event listener should not contain an \"audio stop\" event action.");
        assertNull(audio.getAudioEventListener()
            .getAudioCloseAction(), "After loading the audio into memory, the gotten audio's event listener should not contain an \"audio close\" event action.");
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
        assertEquals(PlaybackState.Playing, audio.getCurrentPlaybackState(), "After playing the audio, the gotten audio should be in the \"playing\" playback state.");
        assertEquals(PlaybackState.Stopped, audio.getPreviousPlaybackState(), "After playing the audio, the gotten audio's previous playback state should be \"stopped\".");
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
        assertEquals(PlaybackState.Paused, audio.getCurrentPlaybackState(), "After pausing the audio, the gotten audio should be in the \"paused\" playback state.");
        assertEquals(PlaybackState.Playing, audio.getPreviousPlaybackState(), "After pausing the audio, the gotten audio's previous playback state should be \"playing\".");
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
        assertEquals(PlaybackState.Playing, audio.getCurrentPlaybackState(), "After resuming the audio, the gotten audio should be in the \"playing\" playback state.");
        assertEquals(PlaybackState.Paused, audio.getPreviousPlaybackState(), "After resuming the audio, the gotten audio's previous playback state should be \"paused\".");
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
        assertEquals(PlaybackState.Stopped, audio.getCurrentPlaybackState(), "After stopping the audio, the gotten audio should be in the \"stopped\" playback state.");
        assertEquals(PlaybackState.Playing, audio.getPreviousPlaybackState(), "After stopping the audio, the gotten audio's previous playback state should be \"playing\" because its last state was not paused.");
    }

    @Test
    void checkGetAudioAfterUnloading() {
        StreamedAudio audio = AudioManager.loadStreamedAudio(TestAudioPath);
        assertNotNull(AudioManager.getStreamedAudio(audio.getID()), "The audio should have been loaded into the audio manager successfully.");

        AudioManager.unloadStreamedAudio(audio.getID());
        assertNull(AudioManager.getStreamedAudio(audio.getID()), "After unloading the audio, it should not be present in the audio manager.");
    }

    @Test
    void checkGetAudioAfterUnloading_withMultipleAudioFiles() {
        StreamedAudio[] streamedAudios = new StreamedAudio[4];
        streamedAudios[0] = AudioManager.loadStreamedAudio(TestAudioPath);
        streamedAudios[1] = AudioManager.loadStreamedAudio(TestAudioURL);
        streamedAudios[2] = AudioManager.loadStreamedAudio(TestAudioPath);
        streamedAudios[3] = AudioManager.loadStreamedAudio(TestAudioURL);

        for (StreamedAudio streamedAudio : streamedAudios) {
            assertNotNull(AudioManager.getStreamedAudio(streamedAudio.getID()), "The audio should have been loaded into the audio manager successfully.");
        }

        AudioManager.unloadStreamedAudio(streamedAudios[0].getID(), streamedAudios[1].getID(), streamedAudios[2].getID(), streamedAudios[3].getID());

        for (StreamedAudio streamedAudio : streamedAudios) {
            assertNull(AudioManager.getStreamedAudio(streamedAudio.getID()), "After unloading the audio, it should not be present in the audio manager.");
        }
    }
}
