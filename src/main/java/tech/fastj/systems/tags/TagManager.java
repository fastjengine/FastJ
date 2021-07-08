package tech.fastj.systems.tags;

import tech.fastj.graphics.Drawable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class to manage tags and taggable entities for all {@link TagHandler}s.
 *
 * @author Andrew Dey
 * @version 1.0.0
 */
public class TagManager {

    private static final List<String> MasterTagList = new ArrayList<>();
    private static final Map<TagHandler, List<Drawable>> EntityLists = new HashMap<>();

    private TagManager() {
        throw new java.lang.IllegalStateException();
    }

    /**
     * Gets the list of taggable entities at the specified {@link TagHandler}.
     *
     * @param tagHandler The tag handler to get the list of taggable entities from.
     * @return The list of taggable entities, as a {@code List<Drawable>}.
     */
    public static List<Drawable> getEntityList(TagHandler tagHandler) {
        return EntityLists.get(tagHandler);
    }

    /**
     * Adds the specified tag to the master list of tags.
     *
     * @param tag The tag to add.
     */
    public static void addTagToMasterList(String tag) {
        if (!MasterTagList.contains(tag)) MasterTagList.add(tag);
    }

    /** Removes all the tags from the master list. */
    public static void clearTags() {
        MasterTagList.clear();
    }

    /**
     * Determines whether a tag is in the master list.
     *
     * @param tag The tag to check for.
     * @return The resultant boolean from checking whether the tag is in the master list.
     */
    public static boolean doesTagExist(String tag) {
        return MasterTagList.contains(tag);
    }

    /**
     * Adds the specified taggable entity to the list of taggable entities for the specified tag handler.
     * <p>
     * The taggable entity is only added if the specified tag handler does not already contain the specified taggable
     * entity.
     *
     * @param tagHandler     The {@link TagHandler} which the taggable entity will be aliased with.
     * @param taggableEntity The {@code Drawable} to add.
     */
    public static void addTaggableEntity(TagHandler tagHandler, Drawable taggableEntity) {
        if (!EntityLists.get(tagHandler).contains(taggableEntity)) {
            EntityLists.get(tagHandler).add(taggableEntity);
        }
    }

    /**
     * Removes the specified taggable entity from the list of taggable entities for the specified tag handler.
     *
     * @param tagHandler     The {@link TagHandler} that the taggable entity is aliased with.
     * @param taggableEntity The {@code Drawable} to remove.
     */
    public static void removeTaggableEntity(TagHandler tagHandler, Drawable taggableEntity) {
        EntityLists.get(tagHandler).remove(taggableEntity);
    }

    /**
     * Adds the specified {@link TagHandler} as an alias to store a list of taggable entities for.
     * <p>
     * The specified {@link TagHandler} is only added if it is not already in the tag manager.
     *
     * @param tagHandler The tag handler to add.
     */
    public static void addTaggableEntityList(TagHandler tagHandler) {
        if (!EntityLists.containsKey(tagHandler)) {
            EntityLists.put(tagHandler, new ArrayList<>());
        }
    }

    /**
     * Removes the list of taggable entities aliased to the specified {@link TagHandler}.
     *
     * @param tagHandler The tag handler to remove.
     */
    public static void removeTaggableEntityList(TagHandler tagHandler) {
        EntityLists.remove(tagHandler);
    }

    /**
     * Gets all taggable entities in the specified {@link TagHandler} with the specified tag.
     *
     * @param tagHandler The tag handler to search through.
     * @param tag        The tag to search for.
     * @return A list of taggable entities that have the specified tag.
     */
    public static List<Drawable> getAllInListWithTag(TagHandler tagHandler, String tag) {
        return EntityLists.get(tagHandler).stream()
                .filter(taggableEntity -> taggableEntity.hasTag(tag))
                .collect(Collectors.toList());
    }

    /**
     * Gets all taggable entities from all {@link TagHandler}s with the specified tag.
     *
     * @param tag The tag to search for.
     * @return A list of taggable entities that have the specified tag.
     */
    public static List<Drawable> getAllWithTag(String tag) {
        return EntityLists.values().parallelStream()
                .flatMap(List::parallelStream)
                .filter(taggableEntity -> taggableEntity.hasTag(tag))
                .collect(Collectors.toList());
    }

    /**
     * Clears the taggable entity list aliased to the specified tag handler.
     *
     * @param tagHandler The tag handler to clear the list of taggable entities for.
     */
    public static void clearEntityList(TagHandler tagHandler) {
        EntityLists.get(tagHandler).clear();
    }

    /** Wipes the {@code TagManager} of all aliases and tags. */
    public static void reset() {
        for (List<Drawable> entityList : EntityLists.values()) {
            entityList.clear();
        }
        EntityLists.clear();
        clearTags();
    }
}
