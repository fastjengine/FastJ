package tech.fastj.feature;

import tech.fastj.App;

import java.util.Set;

/**
 * TODO: documentation
 *
 * @author Andrew Dey
 */
public interface AppFeature {

    default Set<Class<? extends AppFeature>> dependencies() {
        return Set.of();
    }

    void load(App app);

    void unload(App app);
}
