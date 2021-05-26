package unittest.mock.systems.control;

import io.github.lucasstarsz.fastj.graphics.Display;

import io.github.lucasstarsz.fastj.systems.control.Scene;

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
