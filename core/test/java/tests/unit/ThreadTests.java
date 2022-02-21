package tests.unit;

import org.junit.jupiter.api.Test;
import tech.fastj.thread.ManagedThreadExceptionHandler;
import tech.fastj.thread.ManagedThreadFactory;
import tests.mock.thread.SingleExceptionThreadManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

class ThreadTests {

    @Test
    void checkThreadManagerReceivesException() {
        SingleExceptionThreadManager singleExceptionThreadManager = new SingleExceptionThreadManager();
        ManagedThreadExceptionHandler managedThreadExceptionHandler = new ManagedThreadExceptionHandler(singleExceptionThreadManager);
        ManagedThreadFactory managedThreadFactory = new ManagedThreadFactory(managedThreadExceptionHandler);
        ExecutorService executorService = Executors.newCachedThreadPool(managedThreadFactory);

        try {
            executorService.execute(() -> {
                throw new RuntimeException();
            });

            // if it takes longer than a second, honey you need a new pc
            executorService.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            fail("Executor service should not be interrupted.");
        } finally {
            Throwable exceptionFromThread = singleExceptionThreadManager.getExceptionReceived();
            assertNotNull(exceptionFromThread, "Exception received should be a RuntimeException -- not a null value.");
            assertInstanceOf(RuntimeException.class, exceptionFromThread, "The received exception should be a RuntimeException.");
        }
    }
}
