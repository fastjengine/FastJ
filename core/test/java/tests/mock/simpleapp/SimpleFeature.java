package tests.mock.simpleapp;

import tech.fastj.App;
import tech.fastj.feature.Feature;

public class SimpleFeature implements Feature {

    @Override
    public void load(App app) {
        System.out.println("Simple feature loaded.");
    }

    @Override
    public void unload(App app) {
        System.out.println("Simple feature unloaded.");
    }
}
