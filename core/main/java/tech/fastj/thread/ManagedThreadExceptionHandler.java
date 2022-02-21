package tech.fastj.thread;

public record ManagedThreadExceptionHandler(ThreadManager manager) implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        manager.receivedException(thread, throwable);
    }
}
