package tech.fastj.thread;

/**
 * TODO: Documentation
 */
public class ManagedThread extends Thread {

    public static final String DefaultThreadPrefix = "FastJ-Thread-";

    /**
     * For naming anonymous threads.
     * <p>
     * In source: {@code ManagedThread#nextThreadNum()}
     */
    private static int threadInitNumber;

    private final ManagedThreadExceptionHandler managedThreadExceptionHandler;

    public ManagedThread(ManagedThreadExceptionHandler managedThreadExceptionHandler) {
        super(DefaultThreadPrefix + nextThreadNum());
        this.managedThreadExceptionHandler = managedThreadExceptionHandler;
        setUncaughtExceptionHandler(managedThreadExceptionHandler);
    }

    public ManagedThread(ManagedThreadExceptionHandler managedThreadExceptionHandler, Runnable target) {
        super(target, DefaultThreadPrefix + nextThreadNum());
        this.managedThreadExceptionHandler = managedThreadExceptionHandler;
        setUncaughtExceptionHandler(managedThreadExceptionHandler);
    }

    public ManagedThread(ManagedThreadExceptionHandler managedThreadExceptionHandler, String name) {
        super(name);
        this.managedThreadExceptionHandler = managedThreadExceptionHandler;
        setUncaughtExceptionHandler(managedThreadExceptionHandler);
    }

    public ManagedThread(ManagedThreadExceptionHandler managedThreadExceptionHandler, Runnable target, String name) {
        super(target, name);
        this.managedThreadExceptionHandler = managedThreadExceptionHandler;
        setUncaughtExceptionHandler(managedThreadExceptionHandler);
    }

    @Override
    public void run() {
        setUncaughtExceptionHandler(managedThreadExceptionHandler);
        System.out.println("start");
        super.run();
    }

    /**
     * Increment default thread numbering.
     *
     * @return The next number to use in thread numbering.
     */
    private static synchronized int nextThreadNum() {
        return threadInitNumber++;
    }
}
