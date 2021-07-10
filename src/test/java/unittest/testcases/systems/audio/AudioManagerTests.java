package unittest.testcases.systems.audio;

import tech.fastj.systems.audio.AudioManager;
import tech.fastj.systems.audio.MemoryAudio;
import tech.fastj.systems.audio.StreamedAudio;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import unittest.EnvironmentHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class AudioManagerTests {

    private static final Path TestAudioPath = Path.of("src/test/resources/test_audio.wav");

    @BeforeAll
    public static void onlyRunIfAudioOutputIsSupported() {
        assumeTrue(EnvironmentHelper.DoesEnvironmentSupportAudioOutput);
    }

    @Test
    void checkMemoryAudioLoading_withWAVFormatAudio() {
        MemoryAudio memoryAudio = AudioManager.loadMemoryAudioInstance(TestAudioPath);
        assertNotNull(AudioManager.getMemoryAudio(memoryAudio.getID()), "Loading the audio file into memory should cause it to be stored in the audio manager.");
    }

    @Test
    void checkMemoryAudioLoading_withWAVFormatAudio_andMultiplePaths() {
        MemoryAudio[] memoryAudios = AudioManager.loadMemoryAudioInstances(TestAudioPath, TestAudioPath, TestAudioPath);

        for (MemoryAudio memoryAudio : memoryAudios) {
            assertNotNull(AudioManager.getMemoryAudio(memoryAudio.getID()), "Loading the audio file into memory should cause it to be stored in the audio manager.");
        }
    }

    @Test
    void tryMemoryAudioLoading_withIncorrectFilePath() {
        Path testAudioPath = Path.of(UUID.randomUUID().toString());
        Throwable exception = assertThrows(IllegalStateException.class, () -> AudioManager.loadMemoryAudioInstance(testAudioPath));
        Throwable underlyingException = exception.getCause();
        assertEquals(FileNotFoundException.class, underlyingException.getClass(), "The underlying exception's class should match the expected exception's class.");
    }

    @Test
    void tryMemoryAudioLoading_withUnsupportedAudioFormat() {
        Path testAudioPath = Path.of("src/test/resources/test_audio.flac");

        Throwable exception = assertThrows(IllegalStateException.class, () -> AudioManager.loadMemoryAudioInstance(testAudioPath));
        Throwable underlyingException = exception.getCause();
        assertEquals(UnsupportedAudioFileException.class, underlyingException.getClass(), "The underlying exception's class should match the expected exception's class.");
        assertEquals(underlyingException.getMessage(), testAudioPath.toAbsolutePath() + " seems to be of an unsupported file format.", "Upon reading an unsupported audio file format, an error should be thrown.");
    }

    @Test
    void checkStreamedAudioLoading_withWAVFormatAudio() {
        StreamedAudio streamedAudio = AudioManager.loadStreamedAudioInstance(TestAudioPath);
        assertNotNull(AudioManager.getMemoryAudio(streamedAudio.getID()), "Loading the audio file into memory should cause it to be stored in the audio player.");
    }

    @Test
    void checkStreamedAudioLoading_withWAVFormatAudio_andMultiplePaths() {
        StreamedAudio[] memoryAudios = AudioManager.loadStreamedAudioInstances(TestAudioPath, TestAudioPath, TestAudioPath);

        for (StreamedAudio memoryAudio : memoryAudios) {
            assertNotNull(AudioManager.getStreamedAudio(memoryAudio.getID()), "Loading the audio file into memory should cause it to be stored in the audio manager.");
        }
    }

    @Test
    void tryStreamedAudioLoading_withIncorrectFilePath() {
        Path invalid_testAudioPath = Path.of(UUID.randomUUID().toString());
        Throwable exception = assertThrows(IllegalStateException.class, () -> AudioManager.loadStreamedAudioInstances(invalid_testAudioPath));
        Throwable underlyingException = exception.getCause();
        assertEquals(FileNotFoundException.class, underlyingException.getClass(), "The underlying exception's class should match the expected exception's class.");
    }

    @Test
    void tryStreamedAudioLoading_withUnsupportedAudioFormat() {
        Path testAudioPath = Path.of("src/test/resources/test_audio.flac");

        Throwable exception = assertThrows(IllegalStateException.class, () -> AudioManager.loadStreamedAudioInstance(testAudioPath));
        Throwable underlyingException = exception.getCause();
        assertEquals(UnsupportedAudioFileException.class, underlyingException.getClass(), "The underlying exception's class should match the expected exception's class.");
        assertEquals(underlyingException.getMessage(), testAudioPath.toAbsolutePath() + " seems to be of an unsupported file format.", "Upon reading an unsupported audio file format, an error should be thrown.");
    }
}
