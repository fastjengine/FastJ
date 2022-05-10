package tech.fastj.systems.tags;

import java.util.ArrayList;
import java.util.List;

/**
 * General implementation for a class which holds tags for a type of object.
 */
public interface TagHandler<T extends TaggableEntity> {

    /**
     * Gets the taggable entities assigned to the tag handler.
     *
     * @return The taggable entities of the tag handler.
     */
    List<T> getTaggableEntities();

    /**
     * Gets all taggable entities with the specified tag.
     *
     * @param tag The tag to check for.
     * @return A list of all taggable entities with the specified tag.
     */
    default List<T> getAllWithTag(String tag) {
        List<T> result = new ArrayList<>();
        for (T entity : getTaggableEntities()) {
            if (entity.hasTag(tag)) {
                result.add(entity);
            }
        }

        return result;
    }

    /**
     * Gets the first found taggable entity with the specified tag.
     *
     * @param tag The tag to check for.
     * @return A list of all taggable entities with the specified tag.
     */
    default T getFirstWithTag(String tag) {
        for (T entity : getTaggableEntities()) {
            if (entity.hasTag(tag)) {
                return entity;
            }
        }

        return null;
    }
}
