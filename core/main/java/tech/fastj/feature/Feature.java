package tech.fastj.feature;

import tech.fastj.App;

/**
 * TODO: Documentation
 *
 * @author Andrew Dey
 */
public interface Feature extends AppFeature {

    void load(App app);

    void unload(App app);
}
