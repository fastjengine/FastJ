package unittest.testcases.systems.audio;

import tech.fastj.engine.FastJEngine;
import tech.fastj.math.Maths;
import tech.fastj.systems.audio.AudioManager;
import tech.fastj.systems.audio.MemoryAudio;
import tech.fastj.systems.audio.state.PlaybackState;

import unittest.EnvironmentHelper;

import java.net.URL;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class MemoryAudioTests {

    private static final Path TestAudioPath = Path.of("src/test/resources/test_audio.wav");
    private static final URL TestAudioURL = MemoryAudioTests.class.getClassLoader().getResource("test_audio.wav");
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
    void checkLoadMemoryAudioInstance_withPath_shouldMatchExpectedValues() {
        MemoryAudio audio = AudioManager.loadMemoryAudio(TestAudioPath);

        assertEquals(TestAudioPath, audio.getAudioPath(), "After loading the audio into memory, the gotten audio should have the same path object as the one used to load it in.");
        assertEquals(PlaybackState.Stopped, audio.getCurrentPlaybackState(), "After loading the audio into memory, the gotten audio should be in the \"stopped\" playback state.");
        assertEquals(PlaybackState.Stopped, audio.getPreviousPlaybackState(), "After loading the audio into memory, the gotten audio's previous playback state should also be \"stopped\".");
        assertEquals(0L, audio.getPlaybackPosition(), "After loading the audio into memory, the gotten audio should be at the very beginning with playback position.");
        assertNull(audio.getAudioEventListener().getAudioOpenAction(), "After loading the audio into memory, the gotten audio's event listener should not contain an \"audio open\" event action.");
        assertNull(audio.getAudioEventListener().getAudioStartAction(), "After loading the audio into memory, the gotten audio's event listener should not contain an \"audio start\" event action.");
        assertNull(audio.getAudioEventListener().getAudioPauseAction(), "After loading the audio into memory, the gotten audio's event listener should not contain an \"audio pause\" event action.");
        assertNull(audio.getAudioEventListener().getAudioResumeAction(), "After loading the audio into memory, the gotten audio's event listener should not contain an \"audio resume\" event action.");
        assertNull(audio.getAudioEventListener().getAudioStopAction(), "After loading the audio into memory, the gotten audio's event listener should not contain an \"audio stop\" event action.");
        assertNull(audio.getAudioEventListener().getAudioCloseAction(), "After loading the audio into memory, the gotten audio's event listener should not contain an \"audio close\" event action.");
    }

    @Test
    void checkLoadMemoryAudioInstance_withURL_shouldMatchExpectedValues() {
        MemoryAudio audio = AudioManager.loadMemoryAudio(TestAudioURL);

        assertTrue(audio.getAudioPath().endsWith("test_audio.wav"), "After loading the audio into memory, the gotten audio should end with the same path to the audio object as the one used to load it in.");
        assertEquals(PlaybackState.Stopped, audio.getCurrentPlaybackState(), "After loading the audio into memory, the gotten audio should be in the \"stopped\" playback state.");
        assertEquals(PlaybackState.Stopped, audio.getPreviousPlaybackState(), "After loading the audio into memory, the gotten audio's previous playback state should also be \"stopped\".");
        assertEquals(0L, audio.getPlaybackPosition(), "After loading the audio into memory, the gotten audio should be at the very beginning with playback position.");
        assertNull(audio.getAudioEventListener().getAudioOpenAction(), "After loading the audio into memory, the gotten audio's event listener should not contain an \"audio open\" event action.");
        assertNull(audio.getAudioEventListener().getAudioStartAction(), "After loading the audio into memory, the gotten audio's event listener should not contain an \"audio start\" event action.");
        assertNull(audio.getAudioEventListener().getAudioPauseAction(), "After loading the audio into memory, the gotten audio's event listener should not contain an \"audio pause\" event action.");
        assertNull(audio.getAudioEventListener().getAudioResumeAction(), "After loading the audio into memory, the gotten audio's event listener should not contain an \"audio resume\" event action.");
        assertNull(audio.getAudioEventListener().getAudioStopAction(), "After loading the audio into memory, the gotten audio's event listener should not contain an \"audio stop\" event action.");
        assertNull(audio.getAudioEventListener().getAudioCloseAction(), "After loading the audio into memory, the gotten audio's event listener should not contain an \"audio close\" event action.");
    }

    @Test
    void checkSetLoopPoints() {
        MemoryAudio audio = AudioManager.loadMemoryAudio(TestAudioPath);

        int expectedLoopStart = MemoryAudio.LoopFromStart;
        int expectedLoopEnd = MemoryAudio.LoopAtEnd;
        audio.setLoopPoints(expectedLoopStart, expectedLoopEnd);

        assertEquals(expectedLoopStart, audio.getLoopStart(), "The audio loop points should have been set.");
        assertEquals(expectedLoopEnd, audio.getLoopEnd(), "The audio loop points should have been set.");
        assertTrue(audio.shouldLoop(), "Setting the loop points should cause the audio to need to loop.");
    }

    @Test
    void trySetLoopPoints_butLoopEndIsLessThanNegativeOne() {
        MemoryAudio audio = AudioManager.loadMemoryAudio(TestAudioPath);

        int expectedLoopStart = MemoryAudio.LoopFromStart;
        int invalidLoopEnd = -1337;

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> audio.setLoopPoints(expectedLoopStart, invalidLoopEnd));
        String expectedExceptionMessage = "The endpoint for the loop should not be less than -1.";
        assertEquals(expectedExceptionMessage, exception.getMessage(), "The expected error message should match the actual error message.");
    }

    @Test
    void trySetLoopPoints_butLoopStartIsLargerThanLoopEnd() {
        MemoryAudio audio = AudioManager.loadMemoryAudio(TestAudioPath);

        int invalidLoopStart = 37;
        int invalidLoopEnd = 13;

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> audio.setLoopPoints(invalidLoopStart, invalidLoopEnd));
        String expectedExceptionMessage = "The loop starting point should be less than or equal to the loop ending point.";
        assertEquals(expectedExceptionMessage, exception.getMessage(), "The expected error message should match the actual error message.");
    }

    @Test
    void checkSetLoopCount_toRandomInteger() {
        MemoryAudio audio = AudioManager.loadMemoryAudio(TestAudioPath);

        int randomLoopCount = Maths.randomInteger(13, 37);
        audio.setLoopCount(randomLoopCount);

        assertEquals(randomLoopCount, audio.getLoopCount(), "The audio loop count should be set.");
        assertTrue(audio.shouldLoop(), "Setting the loop to a value that is not \"Audio.StopLooping\" should cause the audio to need to loop.");
    }

    @Test
    void checkSetLoopCount_toAudioStopLooping() {
        MemoryAudio audio = AudioManager.loadMemoryAudio(TestAudioPath);

        int expectedLoopCount = MemoryAudio.StopLooping;
        audio.setLoopCount(expectedLoopCount);

        assertEquals(expectedLoopCount, audio.getLoopCount(), "The audio loop count should be set.");
        assertFalse(audio.shouldLoop(), "Setting the loop to \"Audio.StopLooping\" should cause the audio to not need to loop.");
    }

    @Test
    void checkSetLoopCount_toContinuousLoop() {
        MemoryAudio audio = AudioManager.loadMemoryAudio(TestAudioPath);

        int expectedLoopCount = MemoryAudio.ContinuousLoop;
        audio.setLoopCount(expectedLoopCount);

        assertEquals(expectedLoopCount, audio.getLoopCount(), "The audio loop count should be set.");
        assertTrue(audio.shouldLoop(), "Setting the loop to \"Audio.ContinuousLoop\" should cause the audio to need to loop.");
    }

    @Test
    void trySetLoopCount_toInvalidValue() {
        MemoryAudio audio = AudioManager.loadMemoryAudio(TestAudioPath);

        int invalidLoopCount = -13;

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> audio.setLoopCount(invalidLoopCount));
        String expectedExceptionMessage = "The loop count should not be less than -1.";
        assertEquals(expectedExceptionMessage, exception.getMessage(), "The expected error message should match the actual error message.");
    }

    @Test
    void checkSetShouldLoopToFalse_whenLoopCountSaysToLoopContinuously() {
        MemoryAudio audio = AudioManager.loadMemoryAudio(TestAudioPath);

        int expectedLoopCount = MemoryAudio.ContinuousLoop;
        boolean expectedShouldLoop = false;

        audio.setLoopCount(expectedLoopCount);
        audio.setShouldLoop(expectedShouldLoop);

        assertEquals(expectedLoopCount, audio.getLoopCount(), "The audio loop count should be set.");
        assertEquals(expectedShouldLoop, audio.shouldLoop(), "Setting the loop to \"Audio.ContinuousLoop\" then changing the \"shouldLoop\" variable to false should cause the audio to not need to loop.");
    }

    @Test
    void checkSetPlaybackPosition() throws InterruptedException {
        MemoryAudio audio = AudioManager.loadMemoryAudio(TestAudioPath);
        long expectedPlaybackPosition = Maths.randomInteger(1, 5) * 100L;

        audio.setPlaybackPosition(expectedPlaybackPosition);
        assertEquals(expectedPlaybackPosition, audio.getPlaybackPosition(), "The audio playback position should be set.");

        audio.getAudioEventListener().setAudioStartAction(audioEvent -> {
            audio.stop();
            assertEquals(
                    0L,
                    audio.getPlaybackPosition(),
                    "After the audio playback is stopped, the playback position should be at the beginning."
            );
        });

        audio.play();
        TimeUnit.SECONDS.sleep(2);
    }

    @Test
    void checkPlayMemoryAudio_shouldTriggerOpenAndStartEvents() throws InterruptedException {
        MemoryAudio audio = AudioManager.loadMemoryAudio(TestAudioPath);
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
    void checkPauseMemoryAudio_shouldTriggerPauseAndStopEvents() throws InterruptedException {
        MemoryAudio audio = AudioManager.loadMemoryAudio(TestAudioPath);
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
        assertTrue(audioStopEventBoolean.get(), "After pausing the audio, the \"audio stop\" event action should have been triggered.");
        assertEquals(PlaybackState.Paused, audio.getCurrentPlaybackState(), "After pausing the audio, the gotten audio should be in the \"paused\" playback state.");
        assertEquals(PlaybackState.Playing, audio.getPreviousPlaybackState(), "After pausing the audio, the gotten audio's previous playback state should be \"playing\".");
    }

    @Test
    void checkResumeMemoryAudio_shouldTriggerStartAndResumeEvents() throws InterruptedException {
        MemoryAudio audio = AudioManager.loadMemoryAudio(TestAudioPath);
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
    void checkStopMemoryAudio_shouldTriggerStopAndCloseEvents() throws InterruptedException {
        MemoryAudio audio = AudioManager.loadMemoryAudio(TestAudioPath);
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
    void checkStopLoopingNow_whilePlayingAudio() throws InterruptedException {
        MemoryAudio audio = AudioManager.loadMemoryAudio(TestAudioURL);
        audio.play();
        TimeUnit.MILLISECONDS.sleep(20);
        audio.stopLoopingNow();

        assertEquals(MemoryAudio.StopLooping, audio.getLoopCount(), "After being told to stop looping, the audio file's loop count should match the \"stop looping value\".");
    }

    @Test
    void checkGetAudioAfterUnloading() {
        MemoryAudio audio = AudioManager.loadMemoryAudio(TestAudioPath);
        assertNotNull(AudioManager.getMemoryAudio(audio.getID()), "The audio should have been loaded into the audio manager successfully.");

        AudioManager.unloadMemoryAudio(audio.getID());
        assertNull(AudioManager.getMemoryAudio(audio.getID()), "After unloading the audio, it should not be present in the audio manager.");
    }

    @Test
    void checkGetAudioAfterUnloading_withMultipleAudioFiles() {
        MemoryAudio[] memoryAudios = new MemoryAudio[4];
        memoryAudios[0] = AudioManager.loadMemoryAudio(TestAudioPath);
        memoryAudios[1] = AudioManager.loadMemoryAudio(TestAudioURL);
        memoryAudios[2] = AudioManager.loadMemoryAudio(TestAudioPath);
        memoryAudios[3] = AudioManager.loadMemoryAudio(TestAudioURL);

        for (MemoryAudio memoryAudio : memoryAudios) {
            assertNotNull(AudioManager.getMemoryAudio(memoryAudio.getID()), "The audio should have been loaded into the audio manager successfully.");
        }

        AudioManager.unloadMemoryAudio(memoryAudios[0].getID(), memoryAudios[1].getID(), memoryAudios[2].getID(), memoryAudios[3].getID());

        for (MemoryAudio memoryAudio : memoryAudios) {
            assertNull(AudioManager.getMemoryAudio(memoryAudio.getID()), "After unloading the audio, it should not be present in the audio manager.");
        }
    }
}
