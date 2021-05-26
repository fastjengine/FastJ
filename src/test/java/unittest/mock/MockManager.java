package unittest.mock;

import io.github.lucasstarsz.fastj.engine.FastJEngine;
import io.github.lucasstarsz.fastj.graphics.Display;

import io.github.lucasstarsz.fastj.systems.control.SimpleManager;

public class MockManager extends SimpleManager {

    private final Runnable runnable;

    public MockManager(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void init(Display display) {
        runnable.run();
        FastJEngine.forceCloseGame();
    }

    @Override
    public void update(Display display) {
    }
}
