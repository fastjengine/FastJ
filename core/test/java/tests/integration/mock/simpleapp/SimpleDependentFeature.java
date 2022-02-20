package tests.integration.mock.simpleapp;

import tech.fastj.App;
import tech.fastj.feature.AppFeature;
import tech.fastj.feature.Feature;

import java.util.Set;

public class SimpleDependentFeature implements Feature {

    @Override
    public Set<Class<? extends AppFeature>> dependencies() {
        return Set.of(SimpleFeature.class);
    }

    @Override
    public void load(App app) {
        System.out.println("Simple dependent feature loaded.");
    }

    @Override
    public void unload(App app) {
        System.out.println("Simple dependent feature unloaded.");
    }
}
