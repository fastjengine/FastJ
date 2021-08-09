package tech.fastj.example.audio;

import tech.fastj.systems.audio.AudioManager;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        /* FastJ Audio */

        /* FastJ's audio engine provides a quick and easy way to play and control audio.
         * It contains a few audio types, and deals with a few common situations:
         * - load audio into memory, or stream audio from a file
         * - play/pause/resume/stop options
         * - events for each audio action (audio stream open/close, play/pause/resume/stop)
         * - (memory-loaded audio only) looping and playback position options
         * - (streamed audio only) gain/pan/balance/mute controls
         *
         * As you can see, there's a lot to cover! So instead, I'll be covering the most basic of
         * operations: simply playing a sound file. */


        /* Simple Audio Playing */

        /* AudioManager.playSound
         * It takes in a Path object, which is just the filepath to your sound file. */

        AudioManager.playSound(Path.of("src/example/resources/sound/test_audio.wav"));
    }
}
