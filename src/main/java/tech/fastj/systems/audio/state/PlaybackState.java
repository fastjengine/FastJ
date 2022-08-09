package tech.fastj.systems.audio.state;

import tech.fastj.systems.audio.Audio;

/**
 * The different playback states an {@link Audio} instance can be in.
 *
 * @author Andrew Dey
 * @since 1.5.0
 */
public enum PlaybackState {
    /** {@link Audio} state signifying the audio is emitting sound. */
    Playing,
    /** {@link Audio} state signifying the audio is withheld from emitting sound, but still ready to resume. */
    Paused,
    /** {@link Audio} state signifying the audio is not emitting sound. */
    Stopped
}