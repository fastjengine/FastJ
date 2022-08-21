package unittest.testcases.systems.audio;

import java.net.URL;
import java.nio.file.Path;

enum AudioTypes {
    Wav(".wav"),
    Mp3(".mp3"),
    Ogg(".ogg"),
    Flac(".flac");

    final String extension;

    AudioTypes(String extension) {
        this.extension = extension;
    }

    Path path() {
        return Path.of("src/test/resources/Test_Audio_Piano" + extension);
    }

    URL url() {
        return AudioTypes.class.getClassLoader().getResource("Test_Audio_Piano" + extension);
    }
}