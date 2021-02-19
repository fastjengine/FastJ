package io.github.lucasstarsz.fastj.systems.tags;

import io.github.lucasstarsz.fastj.graphics.Drawable;
import io.github.lucasstarsz.fastj.systems.game.Scene;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class that allows you to use tags.
 * <p>
 * A {@code TaggableEntity} is an entity that can be given tags to describe what it is, such as {@code "enemy"}, or
 * {@code "player"}.
 * <p>
 * Each {@code TaggableEntity} can be added to a list of taggable entities for each {@code Scene}. From the scene, you
 * can find all entities with a certain tag, as well as finding the first entity with a tag.
 * <p>
 * This can be helpful for a few reasons:
 * <ul>
 * 		<li>Finding a wide range of objects with a specified tag.</li>
 * 		<li>Finding the first object with a specified tag.</li>
 * </ul>
 *
 * @author Andrew Dey
 * @version 1.0.0
 */
public abstract class TaggableEntity {
    List<String> tags = new ArrayList<>();

    /**
     * Gets the list of tags.
     *
     * @return The list of tags.
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * Determines whether this object has the specified tag.
     *
     * @param tag The tag to check for.
     * @return Boolean that determines whether or not this object has the specified tag.
     */
    public boolean hasTag(String tag) {
        return tags.contains(tag);
    }

    /**
     * Adds the specified tag to the object's list of tags.
     *
     * @param <T>         The return type, which must extend {@code TaggableEntity}. By default, the return type is
     *                    {@code TaggableEntity}.
     * @param tag         Tag to be added to the object's list of tags.
     * @param originScene Scene that the object will be added to, as a {@code TaggableEntity}.
     * @return This instance of the {@code TaggableEntity} (or the specified type), for method chaining.
     */
    @SuppressWarnings("unchecked")
    public <T extends TaggableEntity> T addTag(String tag, Scene originScene) {
        if (!tags.contains(tag)) {
            tags.add(tag);
            TagManager.addTagToMasterList(tag);

            if (this instanceof Drawable) {
                originScene.addTaggableEntity((Drawable) this);
            }
        }

        return (T) this;
    }

    /**
     * Removes the specified tag from the object's list of tags.
     * <p>
     * This can also remove this entity from the scene's list of tagged entities if the entity has no more tags left.
     *
     * @param <T>         The return type, which must extend {@code TaggableEntity}. By default, the return type is
     *                    {@code TaggableEntity}.
     * @param tag         Tag to be removed from the object;s list of tags.
     * @param originScene Scene which this object, if it no longer has any tags, will be removed from as a {@code
     *                    TaggableEntity}.
     * @return This instance of the {@code TaggableEntity} (or the specified type), for method chaining.
     */
    @SuppressWarnings("unchecked")
    public <T extends TaggableEntity> T removeTag(String tag, Scene originScene) {
        tags.remove(tag);

        if (tags.size() == 0 && this instanceof Drawable) {
            originScene.removeTaggableEntity((Drawable) this);
        }

        return (T) this;
    }

    /** Removes all tags from this object's tag list. */
    public void clearTags() {
        tags.clear();
    }
}
