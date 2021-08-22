package tech.fastj.resources;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ResourceManager<T extends Resource<V>, V> {

    protected final Map<String, T> resourceStorage;
    protected final Map<Path, UUID> pathToUUIDMap;
    protected final Map<String, Path> idToPathMap;

    public ResourceManager() {
        resourceStorage = new ConcurrentHashMap<>();
        pathToUUIDMap = new ConcurrentHashMap<>();
        idToPathMap = new ConcurrentHashMap<>();
    }

    public abstract T createResource(Path resourcePath);

    public List<T> loadResource(Path... resourcePaths) {
        List<T> resources = new ArrayList<>();
        Arrays.stream(resourcePaths).parallel().forEach(path -> resources.add(loadResource(path)));

        return resources;
    }

    public T loadResource(Path resourcePath) {
        return loadResource(pathToId(resourcePath.toAbsolutePath()));
    }

    public T getResource(Path resourcePath) {
        return getResource(pathToId(resourcePath));
    }

    public T unloadResource(Path resourcePath) {
        return unloadResource(pathToId(resourcePath));
    }

    public void unloadAllResources() {
        resourceStorage.forEach((path, resource) -> resource.unload());
    }

    @SuppressWarnings("unchecked")
    private T loadResource(String resourceId) {
        return resourceStorage.compute(resourceId, (id, imageResource) -> {
            if (imageResource == null) {
                imageResource = createResource(idToPath(resourceId));
            }

            return (T) imageResource.load();
        });
    }

    private T getResource(String resourceId) {
        return resourceStorage.computeIfAbsent(resourceId, id -> loadResource(idToPath(resourceId)));
    }

    private T unloadResource(String resourceId) {
        return resourceStorage.computeIfPresent(resourceId, (id, imageResource) -> {
            imageResource.unload();
            return imageResource;
        });
    }

    private String pathToId(Path resourcePath) {
        UUID uuid = pathToUUIDMap.computeIfAbsent(resourcePath, path -> UUID.randomUUID());
        String id = resourcePath.toAbsolutePath() + uuid.toString();
        idToPathMap.put(id, resourcePath);
        return id;
    }

    private Path idToPath(String resourceId) {
        return idToPathMap.get(resourceId);
    }
}
