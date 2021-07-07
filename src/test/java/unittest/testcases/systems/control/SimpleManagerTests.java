package unittest.testcases.systems.control;

import tech.fastj.graphics.Drawable;
import tech.fastj.graphics.display.Camera;
import tech.fastj.graphics.game.GameObject;

import tech.fastj.systems.behaviors.BehaviorManager;
import tech.fastj.systems.control.SimpleManager;
import tech.fastj.systems.tags.TagManager;

import org.junit.jupiter.api.Test;
import unittest.mock.graphics.MockDrawable;
import unittest.mock.graphics.MockGameObject;
import unittest.mock.systems.control.MockEmptySimpleManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SimpleManagerTests {

    @Test
    void checkSimpleManagerCreation() {
        SimpleManager simpleManager = new MockEmptySimpleManager();

        assertEquals(Camera.Default, simpleManager.getCamera(), "The created manager's Camera should match the default camera.");
        assertNotNull(TagManager.getEntityList(simpleManager), "Upon creation, the manager should be added to the tag manager's map of taggable entity lists.");
        assertNotNull(BehaviorManager.getList(simpleManager), "Upon creation, the manager should be added to the behavior manager's map of taggable entity lists.");
    }

    @Test
    void checkSimpleManagerResetting() {
        SimpleManager simpleManager = new MockEmptySimpleManager();

        simpleManager.getCamera().rotate(10f);
        Drawable taggableEntity = new MockDrawable();
        simpleManager.addTaggableEntity(taggableEntity);
        GameObject gameObject = new MockGameObject();
        simpleManager.addBehaviorListener(gameObject);

        simpleManager.reset();

        assertEquals(Camera.Default, simpleManager.getCamera(), "After resetting the manager, its Camera should match the default camera.");
        assertEquals(0, TagManager.getEntityList(simpleManager).size(), "After resetting the manager, the tag manager should contain no taggable entities for it.");
        assertEquals(0, BehaviorManager.getList(simpleManager).size(), "After resetting the manager, the behavior manager should contain no behavior listeners for it.");
    }
}
