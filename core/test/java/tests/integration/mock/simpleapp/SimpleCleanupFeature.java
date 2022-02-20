package tests.integration.mock.simpleapp;

import tech.fastj.App;
import tech.fastj.feature.CleanupFeature;

public class SimpleCleanupFeature implements CleanupFeature {

    @Override
    public void load(App app) {
        System.out.println("Simple cleanup feature loaded.");
    }

    @Override
    public void unload(App app) {
        System.out.println("Simple cleanup feature unloaded.");
    }
}
