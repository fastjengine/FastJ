package tests.mock.simpleapp;

import tech.fastj.App;

import tech.fastj.feature.StartupFeature;

public class SimpleStartupFeature implements StartupFeature {

    @Override
    public void load(App app) {
        System.out.println("Simple startup feature loaded.");
    }

    @Override
    public void unload(App app) {
        System.out.println("Simple startup feature unloaded.");
    }
}
