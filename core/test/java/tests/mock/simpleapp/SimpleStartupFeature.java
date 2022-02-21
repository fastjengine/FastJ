package tests.mock.simpleapp;

import tech.fastj.App;

import tech.fastj.feature.StartupFeature;

public class SimpleStartupFeature implements StartupFeature {

    @Override
    public void startup(App app) {
        System.out.println("Simple startup feature was run.");
    }
}
