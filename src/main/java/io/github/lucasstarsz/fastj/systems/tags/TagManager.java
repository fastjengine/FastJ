package io.github.lucasstarsz.fastj.systems.tags;

import io.github.lucasstarsz.fastj.graphics.Drawable;

import io.github.lucasstarsz.fastj.systems.control.Scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class to manage tags and taggable entities for all game scenes.
 *
 * @author Andrew Dey
 * @version 1.0.0
 */
public class TagManager {

    private static final List<String> MasterTagList = new ArrayList<>();
    private static final Map<Scene, List<Drawable>> EntityLists = new HashMap<>();

    /**
     * Gets the list of taggable entities at the specified {@code Scene}.
     *
     * @param scene The scene to get the list of taggable entities from.
     * @return The list of taggable entities, as a {@code List<Drawable>}.
     */
    public static List<Drawable> getEntityList(Scene scene) {
        return EntityLists.get(scene);
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
     * Adds the specified taggable entity to the list of taggable entities for the specified scene.
     * <p>
     * The taggable entity is only added if the specified scene does not already contain the specified taggable entity.
     *
     * @param scene          The {@code Scene} which the taggable entity will be aliased with.
     * @param taggableEntity The {@code Drawable} to add.
     */
    public static void addTaggableEntity(Scene scene, Drawable taggableEntity) {
        if (!EntityLists.get(scene).contains(taggableEntity)) {
            EntityLists.get(scene).add(taggableEntity);
        }
    }

    /**
     * Removes the specified taggable entity from the list of taggable entities for the specified scene.
     *
     * @param scene          The {@code Scene} that the taggable entity is aliased with.
     * @param taggableEntity The {@code Drawable} to remove.
     */
    public static void removeTaggableEntity(Scene scene, Drawable taggableEntity) {
        EntityLists.get(scene).remove(taggableEntity);
    }

    /**
     * Adds the specified {@code Scene} as an alias to store a list of taggable entities for.
     * <p>
     * The specified {@code Scene} is only added if it is not already in the tag manager.
     *
     * @param scene The scene to add.
     */
    public static void addTaggableEntityList(Scene scene) {
        if (!EntityLists.containsKey(scene)) {
            EntityLists.put(scene, new ArrayList<>());
        }
    }

    /**
     * Removes the list of taggable entities aliased to the specified {@code Scene}.
     *
     * @param scene The scene to remove.
     */
    public static void removeTaggableEntityList(Scene scene) {
        EntityLists.remove(scene);
    }

    /**
     * Gets all taggable entities in the specified {@code Scene} with the specified tag.
     *
     * @param scene The scene to search through.
     * @param tag   The tag to search for.
     * @return A list of taggable entities that have the specified tag.
     */
    public static List<Drawable> getAllInListWithTag(Scene scene, String tag) {
        return EntityLists.get(scene).stream()
                .filter(obj -> obj.hasTag(tag))
                .collect(Collectors.toList());
    }

    /**
     * Gets all taggable entities from all {@code Scene}s with the specified tag.
     *
     * @param tag The tag to search for.
     * @return A list of taggable entities that have the specified tag.
     */
    public static List<Drawable> getAllWithTag(String tag) {
        return EntityLists.values().parallelStream()
                .flatMap(List::parallelStream)
                .filter(obj -> obj.hasTag(tag))
                .collect(Collectors.toList());
    }

    /**
     * Clears the taggable entity list aliased to the specified scene.
     *
     * @param scene The scene to clear the list of taggable entities for.
     */
    public static void clearEntityList(Scene scene) {
        EntityLists.get(scene).clear();
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
