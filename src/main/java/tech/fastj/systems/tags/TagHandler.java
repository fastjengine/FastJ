package tech.fastj.systems.tags;

import tech.fastj.graphics.Drawable;

import tech.fastj.systems.behaviors.BehaviorManager;

import java.util.List;

/**
 * Interface denoting that the implementing classes directly interface with the {@link BehaviorManager} class.
 * <p>
 * <b>FOR IMPLEMENTORS:</b> In order for these methods to work you need to call {@link
 * TagManager#addTaggableEntityList(TagHandler)} upon construction.
 */
public interface TagHandler {

    /**
     * Gets the taggable entities assigned to the tag handler.
     *
     * @return The taggable entities of the tag handler.
     */
    default List<Drawable> getTaggableEntities() {
        return TagManager.getEntityList(this);
    }

    /**
     * Gets all taggable entities with the specified tag.
     *
     * @param tag The tag to check for.
     * @return A list of all taggable entities with the specified tag.
     */
    default List<Drawable> getAllWithTag(String tag) {
        return TagManager.getAllInListWithTag(this, tag);
    }

    /**
     * Adds the specified taggable entity, only if it extends the {@code Drawable} class.
     *
     * @param entity The taggable entity to add.
     * @param <T>    The type of the taggable entity, which must extend the {@code Drawable} class.
     */
    default <T extends Drawable> void addTaggableEntity(T entity) {
        TagManager.addTaggableEntity(this, entity);
    }

    /**
     * Removes the specified taggable entity.
     *
     * @param entity The taggable entity to remove.
     */
    default void removeTaggableEntity(Drawable entity) {
        TagManager.removeTaggableEntity(this, entity);
    }

    /** Removes all taggable entities from the tag handler. */
    default void clearTaggableEntities() {
        TagManager.clearEntityList(this);
    }
}
