package unittest.mock;

import io.github.lucasstarsz.fastj.graphics.Display;

import io.github.lucasstarsz.fastj.systems.control.LogicManager;
import io.github.lucasstarsz.fastj.systems.control.Scene;

public class MockManager extends LogicManager {

    Scene scene;

    public MockManager(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void setup(Display display) {
        addScene(scene);
        setCurrentScene(scene);
        loadCurrentScene();
    }
}
