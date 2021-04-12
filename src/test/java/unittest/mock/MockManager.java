package unittest.mock;

import io.github.lucasstarsz.fastj.render.Display;
import io.github.lucasstarsz.fastj.systems.game.LogicManager;
import io.github.lucasstarsz.fastj.systems.game.Scene;

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
