package tech.fastj.systems.audio.state;

import tech.fastj.systems.audio.Audio;

/**
 * Enum containing the different states an {@link Audio} instance can be in.
 *
 * @author Andrew Dey
 * @since 1.5.0
 */
public enum PlaybackState {
    /** {@link Audio} state when the audio is emitting sound. */
    Playing,
    /** {@link Audio} state when the audio is held from emitting sound, but still ready to resume playing. */
    Paused,
    /** {@link Audio} state when the audio is not emitting sound. */
    Stopped
}