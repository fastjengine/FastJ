package tech.fastj.systems.execution;

import tech.fastj.engine.FastJEngine;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

/**
 * Simple thread pool extension for logging/handling possible exceptions coming from thread pools in FastJ.
 *
 * @author Andrew Dey
 * @since 1.7.0
 */
public class FastJScheduledThreadPool extends ScheduledThreadPoolExecutor {

    private boolean shouldCloseOnError;

    /**
     * Constructor matching {@link ScheduledThreadPoolExecutor#ScheduledThreadPoolExecutor(int)}.
     *
     * @param corePoolSize see {@link ScheduledThreadPoolExecutor#ScheduledThreadPoolExecutor(int)}
     */
    public FastJScheduledThreadPool(int corePoolSize) {
        super(corePoolSize);
    }

    /**
     * Constructor matching {@link ScheduledThreadPoolExecutor#ScheduledThreadPoolExecutor(int, ThreadFactory)}.
     *
     * @param corePoolSize  see first parameter of {@link ScheduledThreadPoolExecutor#ScheduledThreadPoolExecutor(int, ThreadFactory)}
     * @param threadFactory see second parameter of {@link ScheduledThreadPoolExecutor#ScheduledThreadPoolExecutor(int, ThreadFactory)}
     */
    public FastJScheduledThreadPool(int corePoolSize, ThreadFactory threadFactory) {
        super(corePoolSize, threadFactory);
    }

    /**
     * Constructor matching {@link ScheduledThreadPoolExecutor#ScheduledThreadPoolExecutor(int, RejectedExecutionHandler)}.
     *
     * @param corePoolSize see first parameter of
     *                     {@link ScheduledThreadPoolExecutor#ScheduledThreadPoolExecutor(int, RejectedExecutionHandler)}
     * @param handler      see second parameter of
     *                     {@link ScheduledThreadPoolExecutor#ScheduledThreadPoolExecutor(int, RejectedExecutionHandler)}
     */
    public FastJScheduledThreadPool(int corePoolSize, RejectedExecutionHandler handler) {
        super(corePoolSize, handler);
    }

    /**
     * Constructor matching {@link ScheduledThreadPoolExecutor#ScheduledThreadPoolExecutor(int, ThreadFactory, RejectedExecutionHandler)}.
     *
     * @param corePoolSize  see first parameter of
     *                      {@link ScheduledThreadPoolExecutor#ScheduledThreadPoolExecutor(int, RejectedExecutionHandler)}
     * @param threadFactory see second parameter of
     *                      {@link ScheduledThreadPoolExecutor#ScheduledThreadPoolExecutor(int, ThreadFactory, RejectedExecutionHandler)}
     * @param handler       see third parameter of
     *                      {@link ScheduledThreadPoolExecutor#ScheduledThreadPoolExecutor(int, ThreadFactory, RejectedExecutionHandler)}
     */
    public FastJScheduledThreadPool(int corePoolSize, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, threadFactory, handler);
    }

    /**
     * Sets whether the engine should close if this thread pool receives an error in one of its running threads.
     * <p>
     * <b>This method is currently useless</b>. {@link FastJEngine#error(String, Throwable)} will <b>always</b> force close the game
     * engine. Limitations will be overcome in a later version of FastJ.
     *
     * @param shouldCloseOnError whether to close the game engine.
     */
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
            try {
                FastJEngine.error("Error received while executing task", throwable);

                if (shouldCloseOnError) {
                    FastJEngine.forceCloseGame();
                }

                throw new IllegalStateException(throwable);
            } finally {
                Thread.currentThread().interrupt();
            }

            throw new IllegalStateException(throwable);
        }
    }
}