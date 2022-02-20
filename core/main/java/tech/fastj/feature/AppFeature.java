package tech.fastj.feature;


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
}
