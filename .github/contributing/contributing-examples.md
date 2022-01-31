# Contributing Examples

## Picking an Example Topic
Head to [FastJ issue #20](https://github.com/fastjengine/FastJ/issues/20) to find examples that need creating!


## Setting Up the Example
- Inside of the `examples` subproject, create a package inside `tech.fastj.examples` regarding your example program.
- Inside of your new package, create a class called `Main.java`. **It is extremely important that your example's main class is named Main.java.** Without this, you will not be able to run the example through the examples:run task.

## Writing the Example
- Follow FastJ's [code contribution convention guide](https://github.com/fastjengine/FastJ/blob/main/.github/contributing/contributing-code.md) while writing your example -- deviations from this style guide will result in PR checks failing.
- Explain the process! Your audience is likely students and other programmers with minimal java experience, if any. Your program should be covering:
    - How the feature being covered works as a whole
    - What it provides in terms of usefulness
    - The different components of the feature, and how to use each one (with both code and comments)
- Don't have the user normally run code that will crash with an exception. Not only does this mean to cheeck your code, but also to leave explanatory code that would cause an exception, _commented_. Let the reader uncomment the code for themselves if they would like to test and see what happens in real time.
- Explain code you wrote if it's not directly used:
    ```java
    /* For the sake of this example, ... */
    ```
- Leave methods that don't pertain to the engine usefulness with a single comment:
    ```java
    // Empty -- this example does not make use of this method.
    ```

A decent example of all of these concepts combined: (from `tech.fastj.example.audio`)
```java
package tech.fastj.examples.audio;

import tech.fastj.engine.FastJEngine;

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
        FastJEngine.log("Now playing: test_audio.wav from file Path");
        AudioManager.playSound(Path.of("resources/sound/test_audio.wav"));


        /* For the sake of this example, I've put a second of sleep time after each audio sound.
         * This gives you a chance to hear the audio being played. */
        TimeUnit.SECONDS.sleep(1);


        /* Now, let's look at the second option: a URL.
         * - A URL (java.net.URL) is also a path to a file, but with slightly different rules and
         *   configuration.
         *
         * A URL takes a bit more to set up in our case -- it requires a class loader, which you
         * can get using:
         *
         *   YourClass.class.getClassLoader();
         *
         * The cool thing about class loaders, however, is that they manage resources much easier
         * than Path does when it comes to portable files. The usage below will work inside a
         * jarfile as well as outside one. */
        FastJEngine.log("Now playing: test_audio.wav from URL");
        AudioManager.playSound(Main.class.getClassLoader().getResource("sound/test_audio.wav"));


        /* For the sake of this example, I've put a second of sleep time after each audio sound.
         * This gives you a chance to hear the audio being played. */
        TimeUnit.SECONDS.sleep(1);
    }
}
```
