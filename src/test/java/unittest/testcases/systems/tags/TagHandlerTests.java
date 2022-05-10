package unittest.testcases.systems.tags;

import unittest.mock.systems.tags.MockTagHandler;
import unittest.mock.systems.tags.MockTaggableEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TagHandlerTests {
    @Test
    void checkGetEntitiesWithTag_shouldNotFail() {
        String tag = MockTaggableEntity.generateTag();
        MockTagHandler tagHandler = new MockTagHandler();
        MockTaggableEntity taggableEntityWithTag = new MockTaggableEntity().addTag(tag);
        MockTaggableEntity taggableEntityWithoutTag = new MockTaggableEntity();

        tagHandler.getTaggableEntities().add(taggableEntityWithTag);
        tagHandler.getTaggableEntities().add(taggableEntityWithoutTag);

        int expectedEntityCount = 1;

        assertEquals(expectedEntityCount, tagHandler.getAllWithTag(tag).size(), "The number of entities retrieved should match the expected count.");
        assertEquals(taggableEntityWithTag, tagHandler.getAllWithTag(tag).get(0), "The retrieved taggable entity should match the expected entity.");
        assertEquals(taggableEntityWithTag, tagHandler.getFirstWithTag(tag), "The retrieved taggable entity should match the expected entity.");
    }
}
