package tests.integration;

import org.junit.jupiter.api.Test;
import tech.fastj.App;
import tests.integration.mock.SimpleGame;

class SimpleAppRunTests {

    @Test
    void runApp_withAllDefaults_shouldNotFail() {
        App.create(new SimpleGame())
                .build()
                .run();
    }
}
