package io.github.lucasstarsz.fastj.systems.audio;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public abstract class SimpleAudioListener implements LineListener {
    @Override
    public abstract void update(LineEvent event);
}
