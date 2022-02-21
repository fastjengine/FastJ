package tech.fastj.thread;

/**
 * TODO: Documentation
 */
public class ManagedThread extends Thread {

    public static final String DefaultThreadPrefix = "FastJ-Thread-";

    private final ThreadManager manager;

    /**
     * For naming anonymous threads.
     * <p>
     * In source: {@code ManagedThread#nextThreadNum()}
     */
    private static int threadInitNumber;

    public ManagedThread(ThreadManager manager) {
        super();
        this.manager = manager;
    }

    public ManagedThread(ThreadManager manager, Runnable target) {
        super(null, target, DefaultThreadPrefix + nextThreadNum(), 0);
        this.manager = manager;
    }

    public ManagedThread(ThreadManager manager, String name) {
        super(null, null, name, 0);
        this.manager = manager;
    }

    public ManagedThread(ThreadManager manager, Runnable target, String name) {
        super(null, target, name, 0);
        this.manager = manager;
    }

    @Override
    public synchronized void start() {
        try {
            super.start();
        } catch (Exception exception) {
            manager.receivedException(exception);
        }
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
