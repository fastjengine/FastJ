package unittest.testcases.systems.control;

import tech.fastj.graphics.Drawable;
import tech.fastj.graphics.display.Camera;
import tech.fastj.graphics.game.GameObject;

import tech.fastj.systems.behaviors.BehaviorManager;
import tech.fastj.systems.control.Scene;
import tech.fastj.systems.tags.TagManager;

import org.junit.jupiter.api.Test;
import unittest.mock.graphics.MockDrawable;
import unittest.mock.graphics.MockGameObject;
import unittest.mock.systems.control.MockEmptyScene;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SceneTests {

    @Test
    void checkSceneCreation() {
        Scene scene = new MockEmptyScene();

        assertEquals(Camera.Default, scene.getCamera(), "The created scene's Camera should match the default camera.");
        assertFalse(scene.isInitialized(), "Upon creation, the scene should not be initialized.");
        assertNotNull(TagManager.getEntityList(scene), "Upon creation, the scene should be added to the tag manager's map of taggable entity lists.");
        assertNotNull(BehaviorManager.getList(scene), "Upon creation, the scene should be added to the behavior manager's map of taggable entity lists.");
    }

    @Test
    void checkSceneInitialization() {
        Scene scene = new MockEmptyScene();
        scene.setInitialized(true);
        assertTrue(scene.isInitialized(), "After initializing the scene, the scene should be initialized.");
    }

    @Test
    void checkSceneResetting() {
        Scene scene = new MockEmptyScene();

        scene.getCamera().rotate(10f);
        Drawable taggableEntity = new MockDrawable();
        scene.addTaggableEntity(taggableEntity);
        GameObject gameObject = new MockGameObject();
        scene.addBehaviorListener(gameObject);

        scene.reset();

        assertEquals(Camera.Default, scene.getCamera(), "After resetting the scene, its Camera should match the default camera.");
        assertFalse(scene.isInitialized(), "After resetting the scene, the scene should not be initialized.");
        assertEquals(0, TagManager.getEntityList(scene).size(), "After resetting the scene, the tag manager should contain no taggable entities for it.");
        assertEquals(0, BehaviorManager.getList(scene).size(), "After resetting the scene, the behavior manager should contain no behavior listeners for it.");
    }
}
