package unittest.testcases.systems.tags;

import tech.fastj.systems.tags.TaggableEntity;

import unittest.mock.systems.tags.MockTaggableEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaggableEntityTests {

    @Test
    void checkAddTagsToTaggableEntity_shouldBePresent() {
        String expectedTag1 = MockTaggableEntity.generateTag();
        String expectedTag2 = MockTaggableEntity.generateTag();
        String expectedTag3 = MockTaggableEntity.generateTag();
        int expectedTagAmount = 3;

        TaggableEntity entity = new MockTaggableEntity()
                .addTag(expectedTag1)
                .addTag(expectedTag2)
                .addTag(expectedTag3);

        assertEquals(expectedTagAmount, entity.getTags().size(), "There should be the correct number of tags present in the taggable entity.");
        assertTrue(entity.hasTag(expectedTag1), "The taggable entity should have the first tag it was assigned.");
        assertTrue(entity.hasTag(expectedTag2), "The taggable entity should have the second tag it was assigned.");
        assertTrue(entity.hasTag(expectedTag3), "The taggable entity should have the third tag it was assigned.");
    }

    @Test
    void checkRemoveTagsFromTaggableEntity_shouldNoLongerBePresent() {
        String expectedTag1 = MockTaggableEntity.generateTag();
        String expectedTag2 = MockTaggableEntity.generateTag();
        String expectedTag3 = MockTaggableEntity.generateTag();
        int expectedTagAmountAfterRemoval = 0;

        TaggableEntity entity = new MockTaggableEntity()
                .addTag(expectedTag1)
                .addTag(expectedTag2)
                .addTag(expectedTag3)
                .removeTag(expectedTag1)
                .removeTag(expectedTag2)
                .removeTag(expectedTag3);

        assertEquals(expectedTagAmountAfterRemoval, entity.getTags().size(), "There should be the correct number of tags present in the taggable entity.");
        assertFalse(entity.hasTag(expectedTag1), "The taggable entity should no longer have the first tag it was assigned.");
        assertFalse(entity.hasTag(expectedTag2), "The taggable entity should no longer have the second tag it was assigned.");
        assertFalse(entity.hasTag(expectedTag3), "The taggable entity should no longer have the third tag it was assigned.");
    }

    @Test
    void checkRemoveTagsFromTaggableEntity_withClearTags_shouldNoLongerBePresent() {
        String expectedTag1 = MockTaggableEntity.generateTag();
        String expectedTag2 = MockTaggableEntity.generateTag();
        String expectedTag3 = MockTaggableEntity.generateTag();
        int expectedTagAmountAfterRemoval = 0;

        TaggableEntity entity = new MockTaggableEntity()
                .addTag(expectedTag1)
                .addTag(expectedTag2)
                .addTag(expectedTag3);
        entity.clearTags();

        assertEquals(expectedTagAmountAfterRemoval, entity.getTags().size(), "There should be the correct number of tags present in the taggable entity.");
        assertFalse(entity.hasTag(expectedTag1), "The taggable entity should no longer have the first tag it was assigned.");
        assertFalse(entity.hasTag(expectedTag2), "The taggable entity should no longer have the second tag it was assigned.");
        assertFalse(entity.hasTag(expectedTag3), "The taggable entity should no longer have the third tag it was assigned.");
    }

    @Test
    void checkCompareTaggableEntities_shouldNotBeTheSame() {
        String sharedTag1 = "AaaaBbbb" + MockTaggableEntity.generateTag();
        String sharedTag2 = "bbbbbbbbbbbbc" + MockTaggableEntity.generateTag();
        String firstEndingTag = "f";
        String secondEndingTag = "g";

        TaggableEntity taggableEntity1 = new MockTaggableEntity()
                .addTag(sharedTag1)
                .addTag(sharedTag2)
                .addTag(firstEndingTag);

        TaggableEntity taggableEntity2 = new MockTaggableEntity()
                .addTag(sharedTag1)
                .addTag(sharedTag2)
                .addTag(secondEndingTag);

        int expectedComparisonDifference = String.CASE_INSENSITIVE_ORDER.compare(firstEndingTag, secondEndingTag);

        assertEquals(expectedComparisonDifference, taggableEntity1.compareTo(taggableEntity2), "The comparison should match the expected comparison outcome.");
        assertEquals(-expectedComparisonDifference, taggableEntity2.compareTo(taggableEntity1), "The comparison should match the negative version of the comparison outcome.");
    }

    @Test
    void checkCompareTaggableEntities_withDifferentTagCounts_shouldNotBeTheSame() {
        String tag1 = "AaaaBbbb" + MockTaggableEntity.generateTag();
        String tag2 = "bbbbbbbbbbbbc" + MockTaggableEntity.generateTag();

        TaggableEntity taggableEntity1 = new MockTaggableEntity()
                .addTag(tag1)
                .addTag(tag2);

        TaggableEntity taggableEntity2 = new MockTaggableEntity();

        int expectedComparisonDifference = Math.max(-1, Math.min(1, taggableEntity1.getTags().size() - taggableEntity2.getTags().size()));

        assertEquals(expectedComparisonDifference, taggableEntity1.compareTo(taggableEntity2), "The comparison should match the expected comparison outcome.");
        assertEquals(-expectedComparisonDifference, taggableEntity2.compareTo(taggableEntity1), "The comparison should match the negative version of the comparison outcome.");
    }

    @Test
    void checkCompareTaggableEntities_withExactSameTags_shouldBeTheSame() {
        String sharedTag1 = "AaaaBbbb" + MockTaggableEntity.generateTag();
        String sharedTag2 = "bbbbbbbbbbbbc" + MockTaggableEntity.generateTag();

        TaggableEntity taggableEntity1 = new MockTaggableEntity()
                .addTag(sharedTag1)
                .addTag(sharedTag2);

        TaggableEntity taggableEntity2 = new MockTaggableEntity()
                .addTag(sharedTag1)
                .addTag(sharedTag2);

        int expectedComparisonDifference = 0;

        assertEquals(expectedComparisonDifference, taggableEntity1.compareTo(taggableEntity2), "The comparison should match the expected comparison outcome.");
        assertEquals(expectedComparisonDifference, taggableEntity2.compareTo(taggableEntity1), "The comparison should match the negative version of the comparison outcome.");
        assertEquals(taggableEntity1, taggableEntity2, "The two taggable entities should evaluate to be equal.");
    }

    @Test
    void checkCompareTaggableEntities_withNoTags_shouldBeTheSame() {
        TaggableEntity taggableEntity1 = new MockTaggableEntity();
        TaggableEntity taggableEntity2 = new MockTaggableEntity();

        int expectedComparisonDifference = 0;

        assertEquals(expectedComparisonDifference, taggableEntity1.compareTo(taggableEntity2), "The comparison should match the expected comparison outcome.");
        assertEquals(expectedComparisonDifference, taggableEntity2.compareTo(taggableEntity1), "The comparison should match the negative version of the comparison outcome.");
        assertEquals(taggableEntity1, taggableEntity2, "The two taggable entities should evaluate to be equal.");
    }
}
