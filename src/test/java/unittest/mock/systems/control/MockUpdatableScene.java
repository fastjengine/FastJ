package unittest.mock.systems.control;

import tech.fastj.graphics.display.FastJCanvas;

import tech.fastj.systems.control.Scene;

import java.util.UUID;

public abstract class MockUpdatableScene extends Scene {

    public MockUpdatableScene() {
        super(UUID.randomUUID().toString());
    }

    @Override
    public void load(FastJCanvas canvas) {

    }

    @Override
    public void unload(FastJCanvas canvas) {

    }

    @Override
    public abstract void update(FastJCanvas canvas);
}
