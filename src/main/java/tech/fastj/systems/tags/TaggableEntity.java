package tech.fastj.systems.tags;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * Abstract class that allows you to identify objects by tags.
 * <p>
 * A {@code TaggableEntity} is an entity that can be given tags to describe what it is, such as {@code "enemy"}, or {@code "player"}.
 * <p>
 * Each {@code TaggableEntity} can be added to a list of taggable entities for each {@code Scene}. From the scene, you can find all entities
 * with a certain tag, as well as finding the first entity with a tag.
 * <p>
 * This can be helpful for a few reasons:
 * <ul>
 * 		<li>Finding a wide range of objects with a specified tag.</li>
 * 		<li>Finding the first object with a specified tag.</li>
 * </ul>
 *
 * @author Andrew Dey
 * @since 1.0.0
 */
public abstract class TaggableEntity implements Comparable<TaggableEntity> {
    private final Set<String> tags = new TreeSet<>();

    /**
     * Gets the {@code TaggableEntity}'s list of tags.
     *
     * @return The list of tags.
     */
    public Set<String> getTags() {
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
     * @param <T> The return type, which must extend {@code TaggableEntity}. By default, the return type is {@code TaggableEntity}.
     * @param tag Tag to be added to the object's list of tags.
     * @return This instance of the {@code TaggableEntity} (or the specified type), for method chaining.
     */
    @SuppressWarnings("unchecked")
    public <T extends TaggableEntity> T addTag(String tag) {
        tags.add(tag);
        return (T) this;
    }

    /**
     * Removes the specified tag from the object's list of tags.
     * <p>
     * This can also remove this entity from the scene's list of tagged entities if the entity has no more tags left.
     *
     * @param <T> The return type, which must extend {@code TaggableEntity}. By default, the return type is {@code TaggableEntity}.
     * @param tag Tag to be removed from the object;s list of tags.
     * @return This instance of the {@code TaggableEntity} (or the specified type), for method chaining.
     */
    @SuppressWarnings("unchecked")
    public <T extends TaggableEntity> T removeTag(String tag) {
        tags.remove(tag);
        return (T) this;
    }

    /** Removes all tags from this object's tag list. */
    public void clearTags() {
        tags.clear();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        TaggableEntity taggableEntity = (TaggableEntity) other;
        return Objects.equals(tags, taggableEntity.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tags);
    }

    @Override
    public int compareTo(TaggableEntity other) {
        int tagsCount = tags.size();
        int otherTagsCount = other.tags.size();

        if (tagsCount == 0 && otherTagsCount == 0) {
            return 0;
        }

        Iterator<String> tagsIterator = tags.iterator();
        Iterator<String> otherTagsIterator = other.tags.iterator();

        String nextTag;
        String otherNextTag;
        while (tagsIterator.hasNext() && otherTagsIterator.hasNext()) {
            nextTag = tagsIterator.next();
            otherNextTag = otherTagsIterator.next();
            int result = String.CASE_INSENSITIVE_ORDER.compare(nextTag, otherNextTag);
            if (result != 0) {
                return result;
            }
        }

        if (tagsCount > otherTagsCount || tagsCount < otherTagsCount) {
            return Integer.compare(tagsCount, otherTagsCount);
        }

        return 0;
    }

    @Override
    public String toString() {
        return "TaggableEntity{" +
            "tags=" + tags +
            '}';
    }
}
