package unittest.mock.systems.control;

import tech.fastj.graphics.display.FastJCanvas;

import tech.fastj.systems.control.Scene;
import tech.fastj.systems.control.SceneManager;

public class MockSceneManager extends SceneManager {

    public MockSceneManager() {
    }

    public MockSceneManager(Scene currentScene, Scene... otherScenes) {
        this.addScene(currentScene);
        this.setCurrentScene(currentScene);
        for (Scene otherScene : otherScenes) {
            this.addScene(otherScene);
        }
    }

    @Override
    public void init(FastJCanvas canvas) {
        if (getCurrentScene() != null) {
            loadCurrentScene();
        }
    }
}
