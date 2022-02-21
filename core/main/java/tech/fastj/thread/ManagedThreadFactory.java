package tech.fastj.thread;

import java.util.concurrent.ThreadFactory;

public record ManagedThreadFactory(ManagedThreadExceptionHandler managedThreadExceptionHandler) implements ThreadFactory {

    @Override
    public ManagedThread newThread(Runnable runnable) {
        return new ManagedThread(managedThreadExceptionHandler, runnable);
    }
}
