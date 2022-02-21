package tests.mock.thread;

import tech.fastj.App;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class ObservableExceptionSavingApp extends App {

    private Consumer<Throwable> observer;
    private boolean hasReceivedException;

    public void setObserver(Consumer<Throwable> observer) {
        this.observer = observer;
    }

    public boolean hasReceivedException() {
        return hasReceivedException;
    }

    @Override
    public Future<Void> receivedException(Thread thread, Throwable exception) {
        System.out.println("Received " + exception.getClass() + " from " + thread.getName());
        onReceivedException(exception);
        hasReceivedException = true;
        return new CompletableFuture<>();
    }

    private void onReceivedException(Throwable throwable) {
        observer.accept(throwable);
    }
}
