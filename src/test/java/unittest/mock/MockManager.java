package unittest.mock;

import io.github.lucasstarsz.fastj.graphics.Display;

import io.github.lucasstarsz.fastj.systems.control.SceneManager;
import io.github.lucasstarsz.fastj.systems.control.Scene;

public class MockManager extends SceneManager {

    Scene scene;

    public MockManager(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void init(Display display) {
        addScene(scene);
        setCurrentScene(scene);
        loadCurrentScene();
    }
}
