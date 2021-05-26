package unittest.testcases.systems.control;

import io.github.lucasstarsz.fastj.systems.control.Scene;
import io.github.lucasstarsz.fastj.systems.control.SceneManager;

import org.junit.jupiter.api.Test;
import unittest.mock.systems.control.MockNameSettingScene;
import unittest.mock.systems.control.MockEmptyScene;
import unittest.mock.systems.control.MockSceneManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SceneManagerTests {

    @Test
    void checkSceneManagerCreation() {
        SceneManager sceneManager = new MockSceneManager();

        assertEquals(0, sceneManager.getScenes().size(), "After creating the manager, it should not contain any scenes.");
        assertNull(sceneManager.getCurrentScene(), "After creating the manager, the current scene should not yet have been set.");
        assertFalse(sceneManager.isSwitchingScenes(), "After creating the manager, it should not be switching scenes.");
    }

    @Test
    void checkSceneManagerSceneAdding() {
        SceneManager sceneManager = new MockSceneManager();

        Scene scene = new MockEmptyScene();
        sceneManager.addScene(scene);

        assertEquals(1, sceneManager.getScenes().size(), "After adding a scene to the manager, it should contain one scene.");
    }

    @Test
    void trySceneManagerSceneAdding_withNameThatAlreadyExists() {
        SceneManager sceneManager = new MockSceneManager();

        String sceneName = "having two scenes with the same name should throw an exception";
        Scene nameSettingScene1 = new MockNameSettingScene(sceneName);
        Scene nameSettingScene2 = new MockNameSettingScene(sceneName);

        sceneManager.addScene(nameSettingScene1);

        Throwable exception = assertThrows(IllegalStateException.class, () -> sceneManager.addScene(nameSettingScene2));

        String expectedExceptionMessage = "The scene name \"" + sceneName + "\" is already in use."
                + System.lineSeparator()
                + "Scenes added: [" + sceneName + "]";
        assertEquals(expectedExceptionMessage, exception.getCause().getMessage(), "The exception message should match the expected exception message.");
    }

    @Test
    void checkSceneManagerSceneGetting_bySceneName() {
        SceneManager sceneManager = new MockSceneManager();

        Scene scene = new MockEmptyScene();
        sceneManager.addScene(scene);

        assertEquals(scene, sceneManager.getScene(scene.getSceneName()), "After adding a scene to the manager, getting that scene by its name should return the original scene.");
    }

    @Test
    void trySceneManagerGetScene_withSceneNameThatDoesNotExist() {
        SceneManager sceneManager = new MockSceneManager();

        String sceneName = "trying to get a scene with a scene name that doesn't exist should throw an exception";
        Throwable exception = assertThrows(IllegalStateException.class, () -> sceneManager.getScene(sceneName));

        String expectedExceptionMessage = "A scene with the name: \"" + sceneName + "\" hasn't been added!";
        assertEquals(expectedExceptionMessage, exception.getCause().getMessage(), "The exception message should match the expected exception message.");
    }

    @Test
    void checkSceneManagerSceneRemoving_bySceneObject() {
        SceneManager sceneManager = new MockSceneManager();

        Scene scene = new MockEmptyScene();
        sceneManager.addScene(scene);
        sceneManager.removeScene(scene);

        assertEquals(0, sceneManager.getScenes().size(), "After removing the scene from the manager, it should not contain any scenes.");
    }

    @Test
    void checkSceneManagerSceneRemoving_bySceneName() {
        SceneManager sceneManager = new MockSceneManager();

        Scene scene = new MockEmptyScene();
        sceneManager.addScene(scene);
        sceneManager.removeScene(scene.getSceneName());

        assertEquals(0, sceneManager.getScenes().size(), "After removing the scene from the manager, it should not contain any scenes.");
    }

    @Test
    void trySceneManagerSceneRemoving_bySceneName_withSceneNameThatDoesNotExist() {
        SceneManager sceneManager = new MockSceneManager();

        String sceneName = "trying to remove a scene with a scene name that doesn't exist should throw an exception";
        Throwable exception = assertThrows(IllegalStateException.class, () -> sceneManager.removeScene(sceneName));

        String expectedExceptionMessage = "A scene with the name: \"" + sceneName + "\" hasn't been added!";
        assertEquals(expectedExceptionMessage, exception.getCause().getMessage(), "The exception message should match the expected exception message.");
    }

    @Test
    void checkSceneManagerCurrentSceneSetting_bySceneObject() {
        SceneManager sceneManager = new MockSceneManager();

        Scene scene = new MockEmptyScene();
        sceneManager.addScene(scene);
        sceneManager.setCurrentScene(scene);

        assertEquals(scene, sceneManager.getCurrentScene(), "After setting the manager's current scene, getting the current scene should return the same scene.");
    }

    @Test
    void checkSceneManagerCurrentSceneSetting_bySceneName() {
        SceneManager sceneManager = new MockSceneManager();

        Scene scene = new MockEmptyScene();
        sceneManager.addScene(scene);
        sceneManager.setCurrentScene(scene.getSceneName());

        assertEquals(scene, sceneManager.getCurrentScene(), "After setting the manager's current scene, getting the current scene should return the same scene.");
    }

    @Test
    void trySceneManagerSetCurrentScene_bySceneName_withSceneNameThatDoesNotExist() {
        SceneManager sceneManager = new MockSceneManager();

        String sceneName = "trying to set the current scene with a scene name that doesn't exist should throw an exception";
        Throwable exception = assertThrows(IllegalStateException.class, () -> sceneManager.setCurrentScene(sceneName));

        String expectedExceptionMessage = "A scene with the name: \"" + sceneName + "\" hasn't been added!";
        assertEquals(expectedExceptionMessage, exception.getCause().getMessage(), "The exception message should match the expected exception message.");
    }
}
