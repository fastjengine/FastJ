package tech.fastj.thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * TODO: Documentation
 */
public interface ThreadManager {

    /**
     * Handle what should happen when a ThreadManager receives an {@code exception} from the given {@code thread}.
     *
     * @param thread    The thread an exception was received from.
     * @param exception The exception received.
     * @return Should always return some form of Future -- {@link CompletableFuture#CompletableFuture()} is a good
     * option.
     */
    default Future<Void> receivedException(Thread thread, Throwable exception) {
        return new CompletableFuture<>();
    }
}
