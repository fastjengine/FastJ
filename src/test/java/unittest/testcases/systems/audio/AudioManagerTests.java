package unittest.testcases.systems.audio;

import tech.fastj.engine.FastJEngine;
import tech.fastj.systems.audio.AudioManager;
import tech.fastj.systems.audio.MemoryAudio;
import tech.fastj.systems.audio.StreamedAudio;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.UUID;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import unittest.EnvironmentHelper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class AudioManagerTests {
    private static final AudioManager GeneralAudioManager = FastJEngine.getAudioManager();

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
    void checkMemoryAudioLoading_ofAllTypes_onSinglePaths() {
        MemoryAudio memoryAudio = GeneralAudioManager.loadMemoryAudio(AudioTypes.Wav.path());
        assertNotNull(GeneralAudioManager.getMemoryAudio(memoryAudio.getID()), "Loading the audio file into memory should cause it to be stored in the audio manager.");

        memoryAudio = GeneralAudioManager.loadMemoryAudio(AudioTypes.Mp3.path());
        assertNotNull(GeneralAudioManager.getMemoryAudio(memoryAudio.getID()), "Loading the audio file into memory should cause it to be stored in the audio manager.");

        memoryAudio = GeneralAudioManager.loadMemoryAudio(AudioTypes.Ogg.path());
        assertNotNull(GeneralAudioManager.getMemoryAudio(memoryAudio.getID()), "Loading the audio file into memory should cause it to be stored in the audio manager.");
    }

    @Test
    void checkMemoryAudioLoading_ofAllTypes_withMultiplePaths() {
        MemoryAudio[] memoryAudios = GeneralAudioManager.loadMemoryAudio(
            AudioTypes.Wav.path(),
            AudioTypes.Mp3.path(),
            AudioTypes.Ogg.path()
        );

        for (MemoryAudio memoryAudio : memoryAudios) {
            assertNotNull(GeneralAudioManager.getMemoryAudio(memoryAudio.getID()), "Loading the audio file into memory should cause it to be stored in the audio manager.");
        }
    }

    @Test
    void checkMemoryAudioLoading_ofAllTypes_onSingleURLs() {
        MemoryAudio memoryAudio = GeneralAudioManager.loadMemoryAudio(AudioTypes.Wav.url());
        assertNotNull(GeneralAudioManager.getMemoryAudio(memoryAudio.getID()), "Loading the audio file into memory should cause it to be stored in the audio manager.");

        memoryAudio = GeneralAudioManager.loadMemoryAudio(AudioTypes.Mp3.url());
        assertNotNull(GeneralAudioManager.getMemoryAudio(memoryAudio.getID()), "Loading the audio file into memory should cause it to be stored in the audio manager.");

        memoryAudio = GeneralAudioManager.loadMemoryAudio(AudioTypes.Ogg.url());
        assertNotNull(GeneralAudioManager.getMemoryAudio(memoryAudio.getID()), "Loading the audio file into memory should cause it to be stored in the audio manager.");
    }

    @Test
    void checkMemoryAudioLoading_ofAllTypes_withMultipleURLs() {
        MemoryAudio[] memoryAudios = GeneralAudioManager.loadMemoryAudio(
            AudioTypes.Wav.url(),
            AudioTypes.Mp3.url(),
            AudioTypes.Ogg.url()
        );

        for (MemoryAudio memoryAudio : memoryAudios) {
            assertNotNull(GeneralAudioManager.getMemoryAudio(memoryAudio.getID()), "Loading the audio file into memory should cause it to be stored in the audio manager.");
        }
    }

    @Test
    void tryMemoryAudioLoading_withInvalidFilePath() {
        Path testAudioPath = Path.of(UUID.randomUUID().toString());
        Throwable exception = assertThrows(IllegalStateException.class, () -> GeneralAudioManager.loadMemoryAudio(testAudioPath));
        Throwable underlyingException = exception.getCause();
        assertEquals(FileNotFoundException.class, underlyingException.getClass(), "The underlying exception's class should match the expected exception's class.");
    }

    @Test
    void tryMemoryAudioLoading_withInvalidFileURL() throws MalformedURLException {
        URL testAudioPath = new URL("file:///" + UUID.randomUUID());
        Throwable exception = assertThrows(IllegalStateException.class, () -> GeneralAudioManager.loadMemoryAudio(testAudioPath));
        Throwable underlyingException = exception.getCause();
        assertEquals(FileNotFoundException.class, underlyingException.getClass(), "The underlying exception's class should match the expected exception's class.");
    }

    @Test
    void tryMemoryAudioLoading_ofUnsupportedAudioFormat() {
        Path testAudioPath = AudioTypes.Flac.path();

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> GeneralAudioManager.loadMemoryAudio(testAudioPath));
        assertTrue(exception.getMessage()
            .endsWith(".flac is of an unsupported file format \"flac\"."), "Upon reading an unsupported audio file format, an error should be thrown.");
        Throwable underlyingException = exception.getCause();
        assertEquals(UnsupportedAudioFileException.class, underlyingException.getClass(), "The underlying exception's class should match the expected exception's class.");
    }

    @Test
    void tryMemoryAudioLoading_withURL_withUnsupportedAudioFormat() {
        URL testAudioURL = AudioManagerTests.class.getClassLoader().getResource("test_audio.flac");

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> GeneralAudioManager.loadMemoryAudio(testAudioURL));
        assertTrue(exception.getMessage()
            .endsWith("test_audio.flac is of an unsupported file format \"flac\"."), "Upon reading an unsupported audio file format, an error should be thrown.");
        Throwable underlyingException = exception.getCause();
        assertEquals(UnsupportedAudioFileException.class, underlyingException.getClass(), "The underlying exception's class should match the expected exception's class.");
    }

    @Test
    void checkStreamedAudioLoading_ofAllTypes_onSinglePaths() {
        StreamedAudio streamedAudio = GeneralAudioManager.loadStreamedAudio(AudioTypes.Wav.path());
        assertNotNull(GeneralAudioManager.getStreamedAudio(streamedAudio.getID()), "Loading the audio file into memory should cause it to be stored in the audio manager.");

        streamedAudio = GeneralAudioManager.loadStreamedAudio(AudioTypes.Mp3.path());
        assertNotNull(GeneralAudioManager.getStreamedAudio(streamedAudio.getID()), "Loading the audio file into memory should cause it to be stored in the audio manager.");

        streamedAudio = GeneralAudioManager.loadStreamedAudio(AudioTypes.Ogg.path());
        assertNotNull(GeneralAudioManager.getStreamedAudio(streamedAudio.getID()), "Loading the audio file into memory should cause it to be stored in the audio manager.");
    }

    @Test
    void checkStreamedAudioLoading_ofAllTypes_withMultiplePaths() {
        StreamedAudio[] streamedAudios = GeneralAudioManager.loadStreamedAudio(
            AudioTypes.Wav.path(),
            AudioTypes.Mp3.path(),
            AudioTypes.Ogg.path()
        );

        for (StreamedAudio streamedAudio : streamedAudios) {
            assertNotNull(GeneralAudioManager.getStreamedAudio(streamedAudio.getID()), "Loading the audio file into memory should cause it to be stored in the audio manager.");
        }
    }

    @Test
    void checkStreamedAudioLoading_ofAllTypes_onSingleURLs() {
        StreamedAudio streamedAudio = GeneralAudioManager.loadStreamedAudio(AudioTypes.Wav.url());
        assertNotNull(GeneralAudioManager.getStreamedAudio(streamedAudio.getID()), "Loading the audio file into memory should cause it to be stored in the audio manager.");

        streamedAudio = GeneralAudioManager.loadStreamedAudio(AudioTypes.Mp3.url());
        assertNotNull(GeneralAudioManager.getStreamedAudio(streamedAudio.getID()), "Loading the audio file into memory should cause it to be stored in the audio manager.");

        streamedAudio = GeneralAudioManager.loadStreamedAudio(AudioTypes.Ogg.url());
        assertNotNull(GeneralAudioManager.getStreamedAudio(streamedAudio.getID()), "Loading the audio file into memory should cause it to be stored in the audio manager.");
    }

    @Test
    void checkStreamedAudioLoading_ofAllTypes_withMultipleURLs() {
        StreamedAudio[] streamedAudios = GeneralAudioManager.loadStreamedAudio(
            AudioTypes.Wav.url(),
            AudioTypes.Mp3.url(),
            AudioTypes.Ogg.url()
        );

        for (StreamedAudio streamedAudio : streamedAudios) {
            assertNotNull(GeneralAudioManager.getStreamedAudio(streamedAudio.getID()), "Loading the audio file into memory should cause it to be stored in the audio manager.");
        }
    }

    @Test
    void tryStreamedAudioLoading_withPath_withIncorrectFilePath() {
        Path invalid_testAudioPath = Path.of(UUID.randomUUID().toString());
        Throwable exception = assertThrows(IllegalStateException.class, () -> GeneralAudioManager.loadStreamedAudio(invalid_testAudioPath));
        Throwable underlyingException = exception.getCause();
        assertEquals(FileNotFoundException.class, underlyingException.getClass(), "The underlying exception's class should match the expected exception's class.");
    }

    @Test
    void tryStreamedAudioLoading_withURL_withIncorrectFilePath() throws MalformedURLException {
        URL invalid_testAudioURL = new URL("file:///" + UUID.randomUUID());
        Throwable exception = assertThrows(IllegalStateException.class, () -> GeneralAudioManager.loadStreamedAudio(invalid_testAudioURL));
        Throwable underlyingException = exception.getCause();
        assertEquals(FileNotFoundException.class, underlyingException.getClass(), "The underlying exception's class should match the expected exception's class.");
    }

    @Test
    void tryStreamedAudioLoading_withPath_withUnsupportedAudioFormat() {
        Path testAudioPath = Path.of("src/test/resources/test_audio.flac");

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> GeneralAudioManager.loadStreamedAudio(testAudioPath));
        assertTrue(exception.getMessage()
            .endsWith("test_audio.flac is of an unsupported file format \"flac\"."), "Upon reading an unsupported audio file format, an error should be thrown.");
        Throwable underlyingException = exception.getCause();
        assertEquals(UnsupportedAudioFileException.class, underlyingException.getClass(), "The underlying exception's class should match the expected exception's class.");
    }

    @Test
    void tryStreamedAudioLoading_withURL_withUnsupportedAudioFormat() {
        URL testAudioURL = AudioManagerTests.class.getClassLoader().getResource("test_audio.flac");

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> GeneralAudioManager.loadStreamedAudio(testAudioURL));
        assertTrue(exception.getMessage()
            .endsWith("test_audio.flac is of an unsupported file format \"flac\"."), "Upon reading an unsupported audio file format, an error should be thrown.");
        Throwable underlyingException = exception.getCause();
        assertEquals(UnsupportedAudioFileException.class, underlyingException.getClass(), "The underlying exception's class should match the expected exception's class.");
    }

    @Test
    void checkPlaySound_ofAllTypes_withPath() {
        assertDoesNotThrow(() -> AudioManager.playSound(AudioTypes.Wav.path()));
        assertDoesNotThrow(() -> AudioManager.playSound(AudioTypes.Mp3.path()));
        assertDoesNotThrow(() -> AudioManager.playSound(AudioTypes.Ogg.path()));
    }

    @Test
    void checkPlaySound_ofAllTypes_withURL() {
        assertDoesNotThrow(() -> AudioManager.playSound(AudioTypes.Wav.url()));
        assertDoesNotThrow(() -> AudioManager.playSound(AudioTypes.Mp3.url()));
        assertDoesNotThrow(() -> AudioManager.playSound(AudioTypes.Ogg.url()));
    }
}
