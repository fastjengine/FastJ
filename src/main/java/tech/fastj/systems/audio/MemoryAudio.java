package tech.fastj.systems.audio;

import tech.fastj.systems.audio.state.PlaybackState;

import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;

/**
 * An audio object used for sound playback.
 * <p>
 * This type of {@link Audio} requires that the audio is loaded into memory before accessing.
 * <p>
 * In addition to {@link Audio the controls all Audio types in FastJ support}, {@code MemoryAudio} supports the following:
 * <ul>
 *     <li>
 *         Looping Controls ({@link #setLoopCount(int) Loop count}, {@link #setLoopPoints(float, float) Loop points},
 *         {@link #stopLoopingNow() Loop stopping})
 *     </li>
 *     <li>Playback Position Controls ({@link #seek(long) Seeking}, {@link #rewindToBeginning() Rewinding})</li>
 * </ul>
 *
 * @author Andrew Dey
 * @since 1.5.0
 */
public class MemoryAudio extends Audio {

    /**
     * Signifies that the audio should loop indefinitely when it finishes playing.
     * <p>
     * Made for use with {@link MemoryAudio#setLoopCount(int)}.
     */
    public static final int ContinuousLoop = Clip.LOOP_CONTINUOUSLY;

    /**
     * Signifies that the audio should stop looping.
     * <p>
     * Made for use with {@link MemoryAudio#setLoopCount(int)}.
     */
    public static final int StopLooping = 0;

    /**
     * Signifies that the audio should start its loop from the beginning of the audio track.
     * <p>
     * Made for use with {@link MemoryAudio#setLoopPoints(float, float)}, as the first parameter.
     */
    public static final int LoopFromStart = 0;

    /**
     * Signifies that the audio should loop back once it reaches the end of the audio track.
     * <p>
     * Made for use with {@link MemoryAudio#setLoopPoints(float, float)}, as the second parameter.
     */
    public static final int LoopAtEnd = -1;

    private final AudioEventListener audioEventListener;
    private final Clip clip;
    private final AudioInputStream audioInputStream;

    private float loopStart;
    private float loopEnd;
    private int loopCount;
    private boolean shouldLoop;
    private long nextPlaybackPosition;

    /**
     * Constructs the {@code MemoryAudio} object with the given path.
     *
     * @param audioPath The path of the audio to use.
     */
    MemoryAudio(Path audioPath) {
        super(audioPath, UUID.randomUUID().toString());

        loopStart = LoopFromStart;
        loopEnd = LoopAtEnd;
        clip = Objects.requireNonNull(AudioManager.newClip());
        audioInputStream = Objects.requireNonNull(AudioManager.newAudioStream(audioPath));
        audioEventListener = new AudioEventListener(this);
        currentPlaybackState = PlaybackState.Stopped;
        previousPlaybackState = PlaybackState.Stopped;
    }

    /**
     * Constructs the {@code MemoryAudio} object with the given URL.
     *
     * @param audioPath The path of the audio to use.
     */
    MemoryAudio(URL audioPath) {
        super(AudioManager.pathFromURL(audioPath), UUID.randomUUID().toString());

        loopStart = LoopFromStart;
        loopEnd = LoopAtEnd;
        clip = Objects.requireNonNull(AudioManager.newClip());

        String urlPath = audioPath.getPath();
        String urlProtocol = audioPath.getProtocol();

        if (urlPath.startsWith(urlProtocol) || urlPath.startsWith("file:///")) {
            audioInputStream = Objects.requireNonNull(AudioManager.newAudioStream(audioPath));
        } else {
            audioInputStream = Objects.requireNonNull(AudioManager.newAudioStream(this.audioPath));
        }

        audioEventListener = new AudioEventListener(this);
        currentPlaybackState = PlaybackState.Stopped;
        previousPlaybackState = PlaybackState.Stopped;
    }

    /**
     * Gets the audio's starting point for looping, on a scale of {@code 0.0} to {@code 1.0}.
     *
     * @return The audio's loop start point on a scale of {@code 0.0} to {@code 1.0}.
     */
    public float getLoopStart() {
        return loopStart;
    }

    /**
     * Gets the audio's ending point for looping, on a scale of {@code 0.0} to {@code 1.0}.
     *
     * @return The audio's loop end point on a scale of {@code 0.0} to {@code 1.0}.
     */
    public float getLoopEnd() {
        return loopEnd;
    }

    /**
     * Gets the audio's loop count -- the amount of times the audio should loop.
     *
     * @return The amount of times the audio should loop.
     */
    public int getLoopCount() {
        return loopCount;
    }

    /**
     * Gets whether the audio should be looping.
     * <p>
     * Notes:
     * <ul>
     *     <li>This can be affected by {@link MemoryAudio#setShouldLoop(boolean)}, {@link MemoryAudio#setLoopPoints(float, float)},
     *     and {@link MemoryAudio#setLoopCount(int)} (in most circumstances).</li>
     * </ul>
     *
     * @return Whether the audio should be looping.
     */
    public boolean shouldLoop() {
        return shouldLoop;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public Path getAudioPath() {
        return audioPath;
    }

    @Override
    public PlaybackState getCurrentPlaybackState() {
        return currentPlaybackState;
    }

    @Override
    public PlaybackState getPreviousPlaybackState() {
        return previousPlaybackState;
    }

    @Override
    public AudioEventListener getAudioEventListener() {
        return audioEventListener;
    }

    @Override
    public AudioInputStream getAudioInputStream() {
        return audioInputStream;
    }

    /**
     * Gets the audio's backing {@link Clip} object.
     *
     * @return The audio's {@code Clip}.
     */
    @Override
    public Clip getAudioSource() {
        return clip;
    }

    @Override
    public long getPlaybackPosition() {
        if (clip.isOpen()) {
            return super.getPlaybackPosition();
        } else {
            return nextPlaybackPosition;
        }
    }

    /**
     * Sets the playback position to the specified {@code playbackPosition} value, in milliseconds.
     *
     * @param playbackPosition The playback position to set to, in milliseconds.
     */
    public void setPlaybackPosition(long playbackPosition) {
        if (clip.isOpen()) {
            MemoryAudioPlayer.setAudioPlaybackPosition(this, playbackPosition);
        } else {
            this.nextPlaybackPosition = playbackPosition;
        }
    }

    /**
     * Directly sets whether the audio should loop, overriding changes made through {@link MemoryAudio#setLoopCount(int)} and
     * {@link MemoryAudio#setLoopPoints(float, float)}.
     * <p>
     * Notes:
     * <ul>
     *     <li>If the loop count (check using {@link MemoryAudio#getLoopCount()}) is set to {@code 0} or {@link MemoryAudio#StopLooping},
     *     calling this method will have no effect on whether the audio will loop.</li>
     * </ul>
     *
     * @param shouldLoop Whether the audio should loop.
     */
    public void setShouldLoop(boolean shouldLoop) {
        this.shouldLoop = shouldLoop;
    }

    /**
     * Sets the audio's loop points.
     * <p>
     * Notes:
     * <ul>
     *     <li>Both {@code loopStart} and {@code loopEnd} should be on a scale of {@code 0.0} to {@code 1.0}.</li>
     *     <li>Calling this method automatically tells the audio to loop (you can check this using {@link MemoryAudio#shouldLoop()}).
     *     See {@link MemoryAudio#setLoopCount(int)} and {@link MemoryAudio#setShouldLoop(boolean)} for ways to disable looping.</li>
     * </ul>
     *
     * @param loopStart The point to loop back to; the starting point for the audio loop. This value is on a scale of {@code 0.0} to
     *                  {@code 1.0}.
     * @param loopEnd   The point at which to loop; the ending point for the audio loop. This value is on a scale of {@code 0.0} to
     *                  {@code 1.0}.
     */
    public void setLoopPoints(float loopStart, float loopEnd) {
        if (loopEnd < -1) {
            throw new IllegalArgumentException("The endpoint for the loop should not be less than -1.");
        }
        if (loopStart > loopEnd && loopStart != LoopFromStart && loopEnd != LoopAtEnd) {
            throw new IllegalArgumentException("The loop starting point should be less than or equal to the loop ending point.");
        }

        this.loopStart = loopStart;
        this.loopEnd = loopEnd;
        shouldLoop = true;
    }

    /**
     * Sets the amount of times the audio will loop.
     * <p>
     * Notes:
     * <ul>
     *     <li>If you want to loop continuously, call this method with {@link MemoryAudio#ContinuousLoop} as the {@code loopCount} parameter.</li>
     *     <li>If you want to disable looping, call this method with {@link MemoryAudio#StopLooping} as the {@code loopCount} parameter.</li>
     * </ul>
     *
     * @param loopCount The amount of times to loop.
     */
    public void setLoopCount(int loopCount) {
        if (loopCount < -1) {
            throw new IllegalArgumentException("The loop count should not be less than -1.");
        }

        this.loopCount = loopCount;
        shouldLoop = (this.loopCount != StopLooping);
    }

    /** Immediately stops the audio's looping, setting whether the audio should loop immediately to {@code false}. */
    public void stopLoopingNow() {
        clip.loop(StopLooping);
        shouldLoop = false;
    }

    /**
     * Changes the playback position by the specified {@code timeChange} value, in milliseconds.
     *
     * @param timeChange The time to change by, in milliseconds.
     */
    public void seek(long timeChange) {
        MemoryAudioPlayer.seekInAudio(this, timeChange);
    }

    /** Rewinds the clip to the beginning. */
    public void rewindToBeginning() {
        MemoryAudioPlayer.rewindAudioToBeginning(this);
    }

    /**
     * See {@link Audio#play()}.
     * <p>
     * Notes:
     * <ul>
     *     <li>If the audio was set to loop (check using {@link MemoryAudio#shouldLoop()}) then calling this method will cause it to loop.</li>
     * </ul>
     */
    @Override
    public void play() {
        MemoryAudioPlayer.playAudio(this, nextPlaybackPosition);
    }

    @Override
    public void pause() {
        MemoryAudioPlayer.pauseAudio(this);
    }

    @Override
    public void resume() {
        MemoryAudioPlayer.resumeAudio(this, nextPlaybackPosition);
    }

    @Override
    public void stop() {
        nextPlaybackPosition = MemoryAudioPlayer.stopAudio(this);
        long other = TimeUnit.MILLISECONDS.convert(clip.getMicrosecondLength(), TimeUnit.MICROSECONDS);

        if (nextPlaybackPosition >= other) {
            nextPlaybackPosition = 0L;
        }
    }

    @Override
    public String toString() {
        return "MemoryAudio{" +
            "audioPath=" + audioPath +
            ", id='" + id + '\'' +
            ", audioInputStream=" + audioInputStream +
            ", clip=" + clip +
            ", loopStart=" + loopStart +
            ", loopEnd=" + loopEnd +
            ", loopCount=" + loopCount +
            ", shouldLoop=" + shouldLoop +
            ", audioEventListener=" + audioEventListener +
            ", currentPlaybackState=" + currentPlaybackState +
            ", previousPlaybackState=" + previousPlaybackState +
            '}';
    }
}
