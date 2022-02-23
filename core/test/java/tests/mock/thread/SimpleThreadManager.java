package tests.mock.thread;

import tech.fastj.thread.ThreadManager;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class SimpleThreadManager implements ThreadManager {
    @Override
    public Future<Void> receivedException(Thread thread, Throwable exception) {
        return new CompletableFuture<>();
    }
}
