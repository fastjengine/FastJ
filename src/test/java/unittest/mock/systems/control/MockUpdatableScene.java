package unittest.mock.systems.control;

import tech.fastj.graphics.display.Display;

import tech.fastj.systems.control.Scene;

import java.util.UUID;

public abstract class MockUpdatableScene extends Scene {

    public MockUpdatableScene() {
        super(UUID.randomUUID().toString());
    }

    @Override
    public void load(Display display) {

    }

    @Override
    public void unload(Display display) {

    }

    @Override
    public abstract void update(Display display);
}
