package tests.mock.thread;

import tech.fastj.thread.ThreadManager;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class SingleExceptionThreadManager implements ThreadManager {

    private Throwable exceptionReceived;

    public Throwable getExceptionReceived() {
        return exceptionReceived;
    }

    @Override
    public Future<Void> receivedException(Thread thread, Throwable exception) {
        this.exceptionReceived = exception;
        return new CompletableFuture<>();
    }
}
