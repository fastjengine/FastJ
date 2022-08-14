package unittest.testcases.systems.control;

import tech.fastj.graphics.display.Camera;
import tech.fastj.graphics.game.GameObject;
import tech.fastj.systems.behaviors.BehaviorManager;
import tech.fastj.systems.control.SimpleManager;

import org.junit.jupiter.api.Test;
import unittest.mock.graphics.MockGameObject;
import unittest.mock.systems.control.MockEmptySimpleManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SimpleManagerTests {

    @Test
    void checkSimpleManagerCreation() {
        SimpleManager simpleManager = new MockEmptySimpleManager();

        assertEquals(Camera.Default, simpleManager.getCamera(), "The created manager's Camera should match the default camera.");
        assertNotNull(BehaviorManager.getList(simpleManager), "Upon creation, the manager should be added to the behavior manager's map of taggable entity lists.");
    }

    @Test
    void checkSimpleManagerResetting() {
        SimpleManager simpleManager = new MockEmptySimpleManager();

        simpleManager.getCamera().rotate(10f);
        GameObject gameObject = new MockGameObject();
        simpleManager.drawableManager().addGameObject(gameObject);
        GameObject gameObject2 = new MockGameObject();
        simpleManager.addBehaviorListener(gameObject2);

        simpleManager.reset();

        assertEquals(Camera.Default, simpleManager.getCamera(), "After resetting the manager, its Camera should match the default camera.");
        assertEquals(0, simpleManager.getTaggableEntities()
            .size(), "After resetting the manager, the tag manager should contain no taggable entities for it.");
        assertEquals(0, BehaviorManager.getList(simpleManager)
            .size(), "After resetting the manager, the behavior manager should contain no behavior listeners for it.");
    }
}
