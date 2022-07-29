package tech.fastj.systems.execution;


import tech.fastj.engine.FastJEngine;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

public class FastJScheduledThreadPool extends ScheduledThreadPoolExecutor {

    private boolean shouldCloseOnError;

    public FastJScheduledThreadPool(int corePoolSize) {
        super(corePoolSize);
    }

    public FastJScheduledThreadPool(int corePoolSize, ThreadFactory threadFactory) {
        super(corePoolSize, threadFactory);
    }

    public FastJScheduledThreadPool(int corePoolSize, RejectedExecutionHandler handler) {
        super(corePoolSize, handler);
    }

    public FastJScheduledThreadPool(int corePoolSize, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, threadFactory, handler);
    }

    public void setShouldCloseOnError(boolean shouldCloseOnError) {
        this.shouldCloseOnError = shouldCloseOnError;
    }

    @Override
    protected void afterExecute(Runnable runnable, Throwable throwable) {
        super.afterExecute(runnable, throwable);

        if (throwable == null && runnable instanceof Future<?> && ((Future<?>) runnable).isDone()) {
            try {
                Future<?> future = (Future<?>) runnable;

                if (future.isDone()) {
                    future.get();
                }
            } catch (CancellationException exception) {
                throwable = exception;
            } catch (ExecutionException exception) {
                throwable = exception.getCause();
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        }

        if (throwable != null) {
            FastJEngine.error("Error received while executing task", throwable);

            if (shouldCloseOnError) {
                FastJEngine.forceCloseGame();
            }
        }
    }
}