package tech.fastj.example.sound;

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
         * As you can see, there's a lot to cover! I'll do my best to keep the length of this
         * example information reasonable. */


        /* Simple Audio Playing */

        /* Don't need much other than to play sound? Just use AudioManager.playSound(Path).
         * It takes in a Path object, which is just the filepath to your sound file. */

        AudioManager.playSound(Path.of("src/example/resources/sound/test_audio.wav"));


        /* Memory-Loaded Audio */

        /* I'll begin by explaining memory-loaded audio. This type of audio in FastJ is called
         * MemoryAudio.
         *
         * MemoryAudio is created using the audio manager using AudioManager#loadMemoryAudio(Path).
         * (The path object is the filepath to the sound file.)
         *
         * As seen from the list above, memory-loaded audio has the following perks over streamed
         * audio:
         * - looping controls (enable/disable looping, how many times to loop, what points to loop
         *   at)
         * - playback position controls (set explicit playback position, seek in audio, rewind to
         *   beginning)
         *
         * Let's start with looping controls. Using MemoryAudio#setShouldLoop, you can enable or
         * disable looping on that specific audio file. */
    }
}
