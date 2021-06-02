package unittest.testcases.systems.audio;

import tech.fastj.systems.audio.AudioManager;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import unittest.EnvironmentHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class AudioManagerTests {

    @BeforeAll
    public static void onlyRunIfAudioOutputIsSupported() {
        assumeTrue(EnvironmentHelper.DoesEnvironmentSupportAudioOutput);
    }

    @Test
    void checkAudioLoading_withWAVFormatAudio() {
        Path testAudioPath = Path.of("src/test/resources/test_audio.wav");
        AudioManager.loadAudio(testAudioPath);

        assertNotNull(AudioManager.getAudio(testAudioPath), "Loading the audio file into memory should cause it to be stored in the audio manager.");
    }

    @Test
    void tryAudioLoading_withIncorrectFilePath() {
        Path testAudioPath = Path.of(UUID.randomUUID().toString());
        Throwable exception = assertThrows(IllegalStateException.class, () -> AudioManager.loadAudio(testAudioPath));
        Throwable underlyingException = exception.getCause();
        assertEquals(FileNotFoundException.class, underlyingException.getClass(), "The underlying exception's class should match the expected exception's class.");
    }

    @Test
    void tryAudioLoading_withUnsupportedAudioFormat() {
        Path testAudioPath = Path.of("src/test/resources/test_audio.flac");

        Throwable exception = assertThrows(IllegalStateException.class, () -> AudioManager.loadAudio(testAudioPath));
        Throwable underlyingException = exception.getCause();
        assertEquals(UnsupportedAudioFileException.class, underlyingException.getClass(), "The underlying exception's class should match the expected exception's class.");
        assertEquals(underlyingException.getMessage(), testAudioPath.toAbsolutePath() + " seems to be of an unsupported file format.", "Upon reading an unsupported audio file format, an error should be thrown.");
        assertNull(AudioManager.getAudio(testAudioPath), "The audio should not have been loaded into memory.");
    }
}
