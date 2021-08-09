package tech.fastj.example.audio;

import tech.fastj.systems.audio.AudioManager;

import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        /* Simple FastJ Audio */

        /* FastJ's audio engine provides a simple way to play audio: AudioManager.playSound.
         * This is the method you'll want to use to play audio without any hassle -- it can be
         * called from anywhere, and will work to be as efficient as possible while playing.
         *
         * The method takes in a filepath -- the filepath to your sound file. A filepath can be
         * either a Path or a URL. */

        /* - A Path (java.nio.file.Path) is as what it implies -- a path to a file.
         *
         * This works best for situations where you don't need code portability -- with the
         * existence of Path.of("path/to/some/audio.wav"), you'll have little-to-no trouble
         * determining where the path leads.
         *
         * However, this comes at the cost of having a much harder time using the code in a
         * distributed format. Path resolves its paths based on where the program is called,
         * which most of the time does not work for programs that have shortcuts (like most
         * executables on Windows!) */
        AudioManager.playSound(Path.of("src/example/resources/sound/test_audio.wav"));


        /* For the sake of this example, I've put a second of sleep time after each audio sound.
         * This gives you a chance to hear the audio being played. */
        TimeUnit.SECONDS.sleep(1);


        /* Now, let's look at the second option: a URL.
         * - A URL (java.net.URL) is also a path to a file, but with slightly different rules and
         *   configuration.
         *
         * A URL takes a bit more to set up -- it requires a class loader, which you can get using:
         *
         *   YourClass.class.getClassLoader();
         *
         * The cool thing about class loaders, however, is that they manage resources much easier
         * than Path does when it comes to portable files. The usage below will work inside a
         * jarfile as well as outside one. */
        AudioManager.playSound(Main.class.getClassLoader().getResource("sound/test_audio.wav"));


        /* For the sake of this example, I've put a second of sleep time after each audio sound.
         * This gives you a chance to hear the audio being played. */
        TimeUnit.SECONDS.sleep(1);
    }
}
