package tests.integration;

import org.junit.jupiter.api.Test;
import tech.fastj.App;
import tests.integration.mock.simpleapp.*;

class SimpleAppRunTests {

    @Test
    void runEmptyApp_shouldNotFail() {
        App.create(SimpleApp.class)
                .build()
                .run();
    }

    @Test
    void runApp_andFeature_shouldNotFail() {
        App.create(SimpleApp.class)
                .withFeature(SimpleFeature.class)
                .build()
                .run();
    }

    @Test
    void runApp_andFeature_andDependentFeature_shouldNotFail() {
        App.create(SimpleApp.class)
                .withFeature(SimpleFeature.class)
                .withFeature(SimpleDependentFeature.class)
                .build()
                .run();
    }

    @Test
    void runApp_andFeature_andDependentFeature_andCleanupFeature_andStartupFeature_shouldNotFail() {
        App.create(SimpleApp.class)
                .withFeature(SimpleFeature.class)
                .withFeature(SimpleDependentFeature.class)
                .withStartupFeature(SimpleStartupFeature.class)
                .withCleanupFeature(SimpleCleanupFeature.class)
                .build()
                .run();
    }
}
