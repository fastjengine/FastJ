package tech.fastj.thread;

import java.util.concurrent.ThreadFactory;

public class ManagedThreadFactory implements ThreadFactory {

    private final ThreadManager threadManager;

    public ManagedThreadFactory(ThreadManager threadManager) {
        this.threadManager = threadManager;
    }

    @Override
    public ManagedThread newThread(Runnable runnable) {
        return new ManagedThread(threadManager, runnable);
    }
}
