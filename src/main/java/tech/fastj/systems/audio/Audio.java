package tech.fastj.systems.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/** An audio object used for sound playback. */
public class Audio {

    /** Signifies that the audio should loop indefinitely when it finishes playing. */
    public static final int ContinuousLoop = Clip.LOOP_CONTINUOUSLY;
    /** Signifies that the audio should stop looping. */
    public static final int StopLooping = 0;

    /** Signifies that the audio should start its loop from the beginning of the audio track. */
    public static final int LoopFromStart = 0;
    /** Signifies that the audio should loop back once it reaches the end of the audio track. */
    public static final int LoopAtEnd = -1;

    private final Clip clip;
    private final AudioInputStream audioInputStream;
    private final AudioEventListener audioEventListener;
    private final Path audioPath;

    private int loopStart;
    private int loopEnd;
    private int loopCount;
    boolean shouldLoop;
    PlaybackState currentPlaybackState;
    PlaybackState previousPlaybackState;

    /**
     * Constructs the {@code Audio} object with the given path.
     *
     * @param audioPath The path of the audio to use.
     */
    Audio(Path audioPath) {
        this.audioPath = audioPath;
        loopStart = LoopFromStart;
        loopEnd = LoopAtEnd;

        clip = Objects.requireNonNull(AudioManager.newClip());
        audioEventListener = new AudioEventListener(this);
        audioInputStream = AudioManager.newAudioStream(audioPath);

        currentPlaybackState = PlaybackState.Stopped;
        previousPlaybackState = PlaybackState.Stopped;
    }

    /**
     * Gets the audio's backing {@link Clip} object.
     *
     * @return The audio's {@code Clip}.
     */
    public Clip getClip() {
        return clip;
    }

    /**
     * Gets the audio's {@link AudioInputStream} object.
     *
     * @return The audio's {@code AudioInputStream}.
     */
    public AudioInputStream getAudioInputStream() {
        return audioInputStream;
    }

    /**
     * Gets the audio's {@link AudioEventListener}.
     *
     * @return The audio's {@code AudioEventListener}.
     */
    public AudioEventListener getAudioEventListener() {
        return audioEventListener;
    }

    /**
     * Gets the audio's {@link Path}.
     *
     * @return The audio's file path.
     */
    public Path getAudioPath() {
        return audioPath;
    }

    /**
     * Gets the audio's current playback state.
     *
     * @return The audio current playback state.
     */
    public PlaybackState getCurrentPlaybackState() {
        return currentPlaybackState;
    }

    /**
     * Gets the audio's previous playback state.
     *
     * @return The audio's previous playback state.
     */
    public PlaybackState getPreviousPlaybackState() {
        return previousPlaybackState;
    }

    /**
     * Gets the audio's current playback position, in milliseconds.
     *
     * @return The audio's current playback position, in milliseconds.
     */
    public long getPlaybackPosition() {
        return TimeUnit.MILLISECONDS.convert(clip.getMicrosecondPosition(), TimeUnit.MILLISECONDS);
    }

    public int getLoopStart() {
        return loopStart;
    }

    public int getLoopEnd() {
        return loopEnd;
    }

    public int getLoopCount() {
        return loopCount;
    }

    public boolean shouldLoop() {
        return shouldLoop;
    }

    /**
     * Sets the playback position to the specified {@code playbackPosition} value, in milliseconds.
     *
     * @param playbackPosition The playback position to set to, in milliseconds.
     */
    public void setPlaybackPosition(long playbackPosition) {
        AudioManager.setAudioPlaybackPosition(this, playbackPosition);
    }

    /**
     * Sets the audio's loop points.
     *
     * @param loopStart The point to loop back to; the starting point for the audio loop.
     * @param loopEnd   The point at which to loop; the ending point for the audio loop.
     */
    public void setLoopPoints(int loopStart, int loopEnd) {
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
     *     <li>If you want to loop continuously, call this method with {@link Audio#ContinuousLoop} as the {@code loopCount} parameter.</li>
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

    public void setShouldLoop(boolean shouldLoop) {
        this.shouldLoop = shouldLoop;
    }

    /**
     * Changes the playback position by the specified {@code timeChange} value, in milliseconds.
     *
     * @param timeChange The time to change by, in milliseconds.
     */
    public void seek(long timeChange) {
        AudioManager.seekInAudio(this, timeChange);
    }

    /** Rewinds the clip to the beginning. */
    public void rewindToBeginning() {
        AudioManager.rewindAudioToBeginning(this);
    }

    /**
     * Starts playing audio's sound, if it was not previously playing.
     * <p>
     * Notes:
     * <ul>
     *     <li>To stop the audio's playback, use {@link Audio#stop()}.</li>
     *     <li>Starting the audio's playback calls an "audio start" event, which can be hooked into using {@link AudioEventListener#setAudioStartAction(Runnable)}.</li>
     *     <li>Starting the audio's playback calls an "audio open" event, which can be hooked into using {@link AudioEventListener#setAudioOpenAction(Runnable)}.</li>
     * </ul>
     */
    public void play() {
        AudioManager.playAudio(this);
    }

    /**
     * Pauses audio playback, if it was playing.
     * <p>
     * Notes:
     * <ul>
     *     <li>Starting the audio's playback calls an "audio pause" event, which can be hooked into using {@link AudioEventListener#setAudioPauseAction(Runnable)}.</li>
     * </ul>
     */
    public void pause() {
        AudioManager.pauseAudio(this);
    }

    /**
     * Resumes audio playback, if it was paused.
     * <p>
     * Notes:
     * <ul>
     *     <li>Starting the audio's playback calls an "audio resume" event, which can be hooked into using {@link AudioEventListener#setAudioResumeAction(Runnable)}.</li>
     * </ul>
     */
    public void resume() {
        AudioManager.resumeAudio(this);
    }

    /**
     * Stops the audio's sound output entirely.
     * <p>
     * Notes:
     * <ul>
     *     <li>To start the audio's playback again, use {@link Audio#play()}.</li>
     *     <li>Stopping the audio's playback calls an "audio stop" event, which can be hooked into using {@link AudioEventListener#setAudioStopAction(Runnable)}.</li>
     *     <li>Stopping the audio's playback calls an "audio close" event, which can be hooked into using {@link AudioEventListener#setAudioCloseAction(Runnable)}.</li>
     * </ul>
     */
    public void stop() {
        AudioManager.stopAudio(this);
    }

    public void stopLoopingNow() {
        clip.loop(StopLooping);
        shouldLoop = false;
    }

    @Override
    public String toString() {
        return "Audio{" +
                "audioPath=" + audioPath +
                ", currentPlaybackState=" + currentPlaybackState +
                ", previousPlaybackState=" + previousPlaybackState +
                ", currentPlaybackPosition=" + getPlaybackPosition() +
                ", clip=" + clip +
                ", audioInputStream=" + audioInputStream +
                ", audioEventListener=" + audioEventListener +
                '}';
    }
}
