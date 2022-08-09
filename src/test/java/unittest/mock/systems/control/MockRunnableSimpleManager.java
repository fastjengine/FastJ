package unittest.mock.systems.control;

import tech.fastj.engine.FastJEngine;
import tech.fastj.graphics.display.FastJCanvas;
import tech.fastj.systems.control.SimpleManager;

public class MockRunnableSimpleManager extends SimpleManager {

    private final Runnable runnable;

    public MockRunnableSimpleManager(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void init(FastJCanvas canvas) {
        runnable.run();
        FastJEngine.forceCloseGame();
    }
}
