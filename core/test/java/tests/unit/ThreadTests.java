package tests.unit;

import tech.fastj.thread.ManagedThread;
import tech.fastj.thread.ManagedThreadExceptionHandler;
import tech.fastj.thread.ManagedThreadFactory;

import tests.mock.thread.SimpleThreadManager;
import tests.mock.thread.SingleExceptionThreadManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        } catch (InterruptedException exception) {
            fail("Executor service should not be interrupted.", exception);
        } finally {
            Throwable exceptionFromThread = singleExceptionThreadManager.getExceptionReceived();
            assertNotNull(exceptionFromThread, "Exception received should be a RuntimeException -- not a null value.");
            assertInstanceOf(RuntimeException.class, exceptionFromThread, "The received exception should be a RuntimeException.");
        }
    }

    @Test
    void checkDefaultThreadNameIncrements() {
        SimpleThreadManager threadManager = new SimpleThreadManager();
        ManagedThreadExceptionHandler exceptionHandler = new ManagedThreadExceptionHandler(threadManager);
        ManagedThread thread1 = new ManagedThread(exceptionHandler);
        ManagedThread thread2 = new ManagedThread(exceptionHandler);
        ManagedThread thread3 = new ManagedThread(exceptionHandler);

        int thread1Num = Integer.parseInt(thread1.getName().substring(ManagedThread.DefaultThreadPrefix.length()));
        int thread2Num = Integer.parseInt(thread2.getName().substring(ManagedThread.DefaultThreadPrefix.length()));
        int thread3Num = Integer.parseInt(thread3.getName().substring(ManagedThread.DefaultThreadPrefix.length()));

        assertTrue(thread1Num < thread2Num, "The first thread's increment number should be less than the second thread's increment number.");
        assertTrue(thread2Num < thread3Num, "The second thread's increment number should be less than the third thread's increment number.");
    }
}
