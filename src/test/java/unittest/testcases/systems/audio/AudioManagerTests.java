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
        MemoryAudio memoryAudio = AudioManager.loadMemoryAudio(TestAudioPath);
        assertNotNull(AudioManager.getMemoryAudio(memoryAudio.getID()), "Loading the audio file into memory should cause it to be stored in the audio manager.");
    }

    @Test
    void checkMemoryAudioLoading_withWAVFormatAudio_andMultiplePaths() {
        MemoryAudio[] memoryAudios = AudioManager.loadMemoryAudio(TestAudioPath, TestAudioPath, TestAudioPath);

        for (MemoryAudio memoryAudio : memoryAudios) {
            assertNotNull(AudioManager.getMemoryAudio(memoryAudio.getID()), "Loading the audio file into memory should cause it to be stored in the audio manager.");
        }
    }

    @Test
    void tryMemoryAudioLoading_withIncorrectFilePath() {
        Path testAudioPath = Path.of(UUID.randomUUID().toString());
        Throwable exception = assertThrows(IllegalStateException.class, () -> AudioManager.loadMemoryAudio(testAudioPath));
        Throwable underlyingException = exception.getCause();
        assertEquals(FileNotFoundException.class, underlyingException.getClass(), "The underlying exception's class should match the expected exception's class.");
    }

    @Test
    void tryMemoryAudioLoading_withUnsupportedAudioFormat() {
        Path testAudioPath = Path.of("src/test/resources/test_audio.flac");

        Throwable exception = assertThrows(IllegalStateException.class, () -> AudioManager.loadMemoryAudio(testAudioPath));
        Throwable underlyingException = exception.getCause();
        assertEquals(UnsupportedAudioFileException.class, underlyingException.getClass(), "The underlying exception's class should match the expected exception's class.");
        assertEquals(underlyingException.getMessage(), testAudioPath.toAbsolutePath() + " is of an unsupported file format \"flac\".", "Upon reading an unsupported audio file format, an error should be thrown.");
    }

    @Test
    void checkStreamedAudioLoading_withWAVFormatAudio() {
        StreamedAudio streamedAudio = AudioManager.loadStreamedAudio(TestAudioPath);
        assertNotNull(AudioManager.getStreamedAudio(streamedAudio.getID()), "Loading the audio file into memory should cause it to be stored in the audio player.");
    }

    @Test
    void checkStreamedAudioLoading_withWAVFormatAudio_andMultiplePaths() {
        StreamedAudio[] memoryAudios = AudioManager.loadStreamedAudio(TestAudioPath, TestAudioPath, TestAudioPath);

        for (StreamedAudio memoryAudio : memoryAudios) {
            assertNotNull(AudioManager.getStreamedAudio(memoryAudio.getID()), "Loading the audio file into memory should cause it to be stored in the audio manager.");
        }
    }

    @Test
    void tryStreamedAudioLoading_withIncorrectFilePath() {
        Path invalid_testAudioPath = Path.of(UUID.randomUUID().toString());
        Throwable exception = assertThrows(IllegalStateException.class, () -> AudioManager.loadStreamedAudio(invalid_testAudioPath));
        Throwable underlyingException = exception.getCause();
        assertEquals(FileNotFoundException.class, underlyingException.getClass(), "The underlying exception's class should match the expected exception's class.");
    }

    @Test
    void tryStreamedAudioLoading_withUnsupportedAudioFormat() {
        Path testAudioPath = Path.of("src/test/resources/test_audio.flac");

        Throwable exception = assertThrows(IllegalStateException.class, () -> AudioManager.loadStreamedAudio(testAudioPath));
        Throwable underlyingException = exception.getCause();
        assertEquals(UnsupportedAudioFileException.class, underlyingException.getClass(), "The underlying exception's class should match the expected exception's class.");
        assertEquals(underlyingException.getMessage(), testAudioPath.toAbsolutePath() + " is of an unsupported file format \"flac\".", "Upon reading an unsupported audio file format, an error should be thrown.");
    }
}
