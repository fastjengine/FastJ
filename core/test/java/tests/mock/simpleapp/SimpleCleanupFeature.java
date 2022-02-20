package tests.mock.simpleapp;

import tech.fastj.App;

import tech.fastj.feature.CleanupFeature;

public class SimpleCleanupFeature implements CleanupFeature {

    @Override
    public void cleanup(App app) {
        System.out.println("Simple cleanup feature was run.");
    }
}
