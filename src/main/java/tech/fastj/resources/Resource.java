package tech.fastj.resources;

import java.nio.file.Path;

public interface Resource<T> {
    ResourceState getResourceState();

    Path getPath();

    Resource<T> load();

    T get();

    void unload();
}
