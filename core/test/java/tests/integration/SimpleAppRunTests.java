package tests.integration;

import org.junit.jupiter.api.Test;
import tech.fastj.App;
import tests.mock.simpleapp.SimpleApp;
import tests.mock.simpleapp.SimpleCleanupFeature;
import tests.mock.simpleapp.SimpleDependentFeature;
import tests.mock.simpleapp.SimpleFeature;
import tests.mock.simpleapp.SimpleGameLoopFeature;
import tests.mock.simpleapp.SimpleStartupFeature;
import tests.mock.thread.SimpleExceptionGameLoopFeature;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class SimpleAppRunTests {

    @Test
    void runEmptyApp_shouldNotFail() {
        assertDoesNotThrow(() -> App.create(SimpleApp.class)
                .build()
                .run()
        );
    }

    @Test
    void runApp_andFeature_shouldNotFail() {
        assertDoesNotThrow(() -> App.create(SimpleApp.class)
                .withFeature(SimpleFeature.class)
                .build()
                .run()
        );
    }

    @Test
    void runApp_andFeature_andDependentFeature_shouldNotFail() {
        assertDoesNotThrow(() -> App.create(SimpleApp.class)
                .withFeature(SimpleFeature.class)
                .withFeature(SimpleDependentFeature.class)
                .build()
                .run()
        );
    }

    @Test
    void runApp_andFeature_andDependentFeature_andCleanupFeature_andStartupFeature_shouldNotFail() {
        assertDoesNotThrow(() -> App.create(SimpleApp.class)
                .withFeature(SimpleFeature.class)
                .withFeature(SimpleDependentFeature.class)
                .withStartupFeature(SimpleStartupFeature.class)
                .withCleanupFeature(SimpleCleanupFeature.class)
                .build()
                .run()
        );
    }

    @Test
    void runApp_andFeature_andDependentFeature_andCleanupFeature_andStartupFeature_andGameLoopFeature_shouldNotFail() {
        assertDoesNotThrow(() -> App.create(SimpleApp.class)
                .withFeature(SimpleFeature.class)
                .withFeature(SimpleDependentFeature.class)
                .withFeature(SimpleGameLoopFeature.class)
                .withStartupFeature(SimpleStartupFeature.class)
                .withCleanupFeature(SimpleCleanupFeature.class)
                .build()
                .run()
        );
    }

    @Test
    void runApp_andExceptionThrowingGameLoopFeature_shouldPrint_butNotFail() {
        assertDoesNotThrow(() -> App.create(SimpleApp.class)
                .withFeature(SimpleExceptionGameLoopFeature.class)
                .build()
                .run()
        );
    }
}
