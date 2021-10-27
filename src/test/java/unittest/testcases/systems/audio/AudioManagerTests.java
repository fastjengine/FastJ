package unittest.testcases.systems.audio;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import tech.fastj.systems.audio.AudioManager;
import tech.fastj.systems.audio.MemoryAudio;
import tech.fastj.systems.audio.StreamedAudio;

import unittest.EnvironmentHelper;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.UUID;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AudioManagerTests {

  private static final Path TestAudioPath = Path.of("src/test/resources/test_audio.wav");
  private static final URL TestAudioURL =
      MemoryAudioTests.class.getClassLoader().getResource("test_audio.wav");

  @BeforeAll
  public static void onlyRunIfAudioOutputIsSupported() {
    assumeTrue(EnvironmentHelper.DoesEnvironmentSupportAudioOutput);
  }

  @Test
  void checkMemoryAudioLoading_withPath_withWAVFormatAudio() {
    MemoryAudio memoryAudio = AudioManager.loadMemoryAudio(TestAudioPath);
    assertNotNull(
        AudioManager.getMemoryAudio(memoryAudio.getID()),
        "Loading the audio file into memory should cause it to be stored in the audio manager.");
  }

  @Test
  void checkMemoryAudioLoading_withPaths_withWAVFormatAudio_andMultiplePaths() {
    MemoryAudio[] memoryAudios =
        AudioManager.loadMemoryAudio(TestAudioPath, TestAudioPath, TestAudioPath);

    for (MemoryAudio memoryAudio : memoryAudios) {
      assertNotNull(
          AudioManager.getMemoryAudio(memoryAudio.getID()),
          "Loading the audio file into memory should cause it to be stored in the audio manager.");
    }
  }

  @Test
  void checkMemoryAudioLoading_withURL_withWAVFormatAudio() {
    MemoryAudio memoryAudio = AudioManager.loadMemoryAudio(TestAudioURL);
    assertNotNull(
        AudioManager.getMemoryAudio(memoryAudio.getID()),
        "Loading the audio file into memory should cause it to be stored in the audio manager.");
  }

  @Test
  void checkMemoryAudioLoading_withURLs_withWAVFormatAudio_andMultiplePaths() {
    MemoryAudio[] memoryAudios =
        AudioManager.loadMemoryAudio(TestAudioURL, TestAudioURL, TestAudioURL);

    for (MemoryAudio memoryAudio : memoryAudios) {
      assertNotNull(
          AudioManager.getMemoryAudio(memoryAudio.getID()),
          "Loading the audio file into memory should cause it to be stored in the audio manager.");
    }
  }

  @Test
  void tryMemoryAudioLoading_withInvalidFilePath() {
    Path testAudioPath = Path.of(UUID.randomUUID().toString());
    Throwable exception =
        assertThrows(
            IllegalStateException.class, () -> AudioManager.loadMemoryAudio(testAudioPath));
    Throwable underlyingException = exception.getCause();
    assertEquals(
        FileNotFoundException.class,
        underlyingException.getClass(),
        "The underlying exception's class should match the expected exception's class.");
  }

  @Test
  void tryMemoryAudioLoading_withInvalidFileURL() throws MalformedURLException {
    URL testAudioPath = new URL("file:///" + UUID.randomUUID());
    Throwable exception =
        assertThrows(
            IllegalStateException.class, () -> AudioManager.loadMemoryAudio(testAudioPath));
    Throwable underlyingException = exception.getCause();
    assertEquals(
        FileNotFoundException.class,
        underlyingException.getClass(),
        "The underlying exception's class should match the expected exception's class.");
  }

  @Test
  void tryMemoryAudioLoading_withPath_withUnsupportedAudioFormat() {
    Path testAudioPath = Path.of("src/test/resources/test_audio.flac");

    Throwable exception =
        assertThrows(
            IllegalStateException.class, () -> AudioManager.loadMemoryAudio(testAudioPath));
    Throwable underlyingException = exception.getCause();
    assertEquals(
        UnsupportedAudioFileException.class,
        underlyingException.getClass(),
        "The underlying exception's class should match the expected exception's class.");
    assertEquals(
        underlyingException.getMessage(),
        testAudioPath.toAbsolutePath() + " is of an unsupported file format \"flac\".",
        "Upon reading an unsupported audio file format, an error should be thrown.");
  }

  @Test
  void tryMemoryAudioLoading_withURL_withUnsupportedAudioFormat() {
    URL testAudioURL = AudioManagerTests.class.getClassLoader().getResource("test_audio.flac");

    Throwable exception =
        assertThrows(IllegalStateException.class, () -> AudioManager.loadMemoryAudio(testAudioURL));
    Throwable underlyingException = exception.getCause();
    assertEquals(
        UnsupportedAudioFileException.class,
        underlyingException.getClass(),
        "The underlying exception's class should match the expected exception's class.");
    assertTrue(
        underlyingException
            .getMessage()
            .endsWith("test_audio.flac is of an unsupported file format \"flac\"."),
        "Upon reading an unsupported audio file format, an error should be thrown.");
  }

  @Test
  void checkStreamedAudioLoading_withPath_withWAVFormatAudio() {
    StreamedAudio streamedAudio = AudioManager.loadStreamedAudio(TestAudioPath);
    assertNotNull(
        AudioManager.getStreamedAudio(streamedAudio.getID()),
        "Loading the audio file into memory should cause it to be stored in the audio player.");
  }

  @Test
  void checkStreamedAudioLoading_withPaths_withWAVFormatAudio_andMultiplePaths() {
    StreamedAudio[] memoryAudios =
        AudioManager.loadStreamedAudio(TestAudioPath, TestAudioPath, TestAudioPath);

    for (StreamedAudio memoryAudio : memoryAudios) {
      assertNotNull(
          AudioManager.getStreamedAudio(memoryAudio.getID()),
          "Loading the audio file into memory should cause it to be stored in the audio manager.");
    }
  }

  @Test
  void checkStreamedAudioLoading_withURL_withWAVFormatAudio() {
    StreamedAudio streamedAudio = AudioManager.loadStreamedAudio(TestAudioURL);
    assertNotNull(
        AudioManager.getStreamedAudio(streamedAudio.getID()),
        "Loading the audio file into memory should cause it to be stored in the audio player.");
  }

  @Test
  void checkStreamedAudioLoading_withURLs_withWAVFormatAudio_andMultiplePaths() {
    StreamedAudio[] memoryAudios =
        AudioManager.loadStreamedAudio(TestAudioURL, TestAudioURL, TestAudioURL);

    for (StreamedAudio memoryAudio : memoryAudios) {
      assertNotNull(
          AudioManager.getStreamedAudio(memoryAudio.getID()),
          "Loading the audio file into memory should cause it to be stored in the audio manager.");
    }
  }

  @Test
  void tryStreamedAudioLoading_withPath_withIncorrectFilePath() {
    Path invalid_testAudioPath = Path.of(UUID.randomUUID().toString());
    Throwable exception =
        assertThrows(
            IllegalStateException.class,
            () -> AudioManager.loadStreamedAudio(invalid_testAudioPath));
    Throwable underlyingException = exception.getCause();
    assertEquals(
        FileNotFoundException.class,
        underlyingException.getClass(),
        "The underlying exception's class should match the expected exception's class.");
  }

  @Test
  void tryStreamedAudioLoading_withURL_withIncorrectFilePath() throws MalformedURLException {
    URL invalid_testAudioURL = new URL("file:///" + UUID.randomUUID());
    Throwable exception =
        assertThrows(
            IllegalStateException.class,
            () -> AudioManager.loadStreamedAudio(invalid_testAudioURL));
    Throwable underlyingException = exception.getCause();
    assertEquals(
        FileNotFoundException.class,
        underlyingException.getClass(),
        "The underlying exception's class should match the expected exception's class.");
  }

  @Test
  void tryStreamedAudioLoading_withPath_withUnsupportedAudioFormat() {
    Path testAudioPath = Path.of("src/test/resources/test_audio.flac");

    Throwable exception =
        assertThrows(
            IllegalStateException.class, () -> AudioManager.loadStreamedAudio(testAudioPath));
    Throwable underlyingException = exception.getCause();
    assertEquals(
        UnsupportedAudioFileException.class,
        underlyingException.getClass(),
        "The underlying exception's class should match the expected exception's class.");
    assertEquals(
        underlyingException.getMessage(),
        testAudioPath.toAbsolutePath() + " is of an unsupported file format \"flac\".",
        "Upon reading an unsupported audio file format, an error should be thrown.");
  }

  @Test
  void tryStreamedAudioLoading_withURL_withUnsupportedAudioFormat() {
    URL testAudioURL = AudioManagerTests.class.getClassLoader().getResource("test_audio.flac");

    Throwable exception =
        assertThrows(
            IllegalStateException.class, () -> AudioManager.loadStreamedAudio(testAudioURL));
    Throwable underlyingException = exception.getCause();
    assertEquals(
        UnsupportedAudioFileException.class,
        underlyingException.getClass(),
        "The underlying exception's class should match the expected exception's class.");
    assertTrue(
        underlyingException
            .getMessage()
            .endsWith("test_audio.flac is of an unsupported file format \"flac\"."),
        "Upon reading an unsupported audio file format, an error should be thrown.");
  }

  @Test
  void checkPlaySound_withPath() {
    assertDoesNotThrow(() -> AudioManager.playSound(TestAudioPath));
  }

  @Test
  void checkPlaySound_withURL() {
    assertDoesNotThrow(() -> AudioManager.playSound(TestAudioURL));
  }
}
