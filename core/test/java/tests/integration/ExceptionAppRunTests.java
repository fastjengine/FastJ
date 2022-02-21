package tests.integration;

import tech.fastj.App;

import tests.mock.thread.ObservableExceptionSavingApp;
import tests.mock.thread.SimpleExceptionGameLoopFeature;

import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class ExceptionAppRunTests {

    @Test
    void runApp_withExceptionThrowingGameLoopFeature_shouldNotFail() throws InterruptedException {
        Consumer<Throwable> onExceptionReceived = exception -> assertInstanceOf(RuntimeException.class, exception);
        ObservableExceptionSavingApp app = App.create(ObservableExceptionSavingApp.class)
                .withFeature(SimpleExceptionGameLoopFeature.class)
                .build();

        app.setObserver(onExceptionReceived);
        assertDoesNotThrow(app::run);

        while (!app.hasReceivedException()) {
            Thread.sleep(1);
        }
    }
}
