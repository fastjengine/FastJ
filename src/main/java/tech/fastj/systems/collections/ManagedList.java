package tech.fastj.systems.collections;

import tech.fastj.systems.execution.FastJScheduledThreadPool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * A form of {@link ArrayList} whose actions are managed through a {@link ScheduledExecutorService}.
 * <p>
 * This style of list provides a reliable way to stop a list's iteration as quickly as possible -- especially in the event of an infinite
 * loop. This is achievable by having the list's iterative actions run through an internal {@link ScheduledExecutorService} instance.
 * <p>
 * When the list's manager is {@link #shutdown() shut down}, the manager-related methods can no longer be used. However, the standard
 * {@link List} methods can still be accessed as normal.
 * <p>
 * <b>This form of list is not meant for quick actions -- it is <i>very slow</i>.</b> Besides that, it is fairly
 * useful. It provides:
 * <ul>
 *     <li>the actions of a {@link List}</li>
 *     <li>some actions of a {@link ScheduledExecutorService}</li>
 *     <li>a few extra actions related to list iteration:
 *          <ul>
 *              <li>{@link #iterate(Consumer)}</li>
 *              <li>{@link #scheduleIterate(Consumer, long, TimeUnit)}</li>
 *              <li>{@link #scheduledIterateAtFixedRate(Consumer, long, long, TimeUnit)}</li>
 *              <li>{@link #scheduledIterateWithFixedDelay(Consumer, long, long, TimeUnit)}</li>
 *          </ul>
 *     </li>
 * </ul>
 *
 * @param <E> The type of the list's contained elements.
 * @see List
 * @see ArrayList
 * @see ScheduledExecutorService
 */
public class ManagedList<E> implements List<E> {

    private final ArrayList<E> list;
    private ScheduledExecutorService listManager;

    /**
     * A constructor matching {@link ArrayList#ArrayList()}.
     *
     * @see ArrayList#ArrayList()
     */
    public ManagedList() {
        list = new ArrayList<>();
        listManager = new FastJScheduledThreadPool(1);
    }

    /**
     * A constructor matching {@link ArrayList#ArrayList(int)}.
     *
     * @see ArrayList#ArrayList(int)
     */
    public ManagedList(int initialCapacity) {
        list = new ArrayList<>(initialCapacity);
        listManager = new FastJScheduledThreadPool(1);
    }

    /**
     * A constructor matching {@link ArrayList#ArrayList(Collection)}.
     *
     * @see ArrayList#ArrayList(Collection)
     */
    public ManagedList(Collection<? extends E> collection) {
        list = new ArrayList<>(collection);
        listManager = new FastJScheduledThreadPool(1);
    }

    /**
     * {@link #shutdownNow() Shuts down} the list's manager, and creates a new one. If the manager was previously shut down, this is the
     * recommended way to restore it.
     */
    public List<Runnable> resetManager() {
        List<Runnable> remnants = shutdownNow();
        listManager = new FastJScheduledThreadPool(1);
        return remnants;
    }

    /** See {@link ExecutorService#shutdown()} */
    public void shutdown() {
        listManager.shutdown();
    }

    /** @return See {@link ExecutorService#shutdownNow()} */
    public List<Runnable> shutdownNow() {
        return listManager.shutdownNow();
    }

    /** @return See {@link ExecutorService#isShutdown()} */
    public boolean isShutdown() {
        return listManager.isShutdown();
    }

    /** @return See {@link ExecutorService#isTerminated()} */
    public boolean isTerminated() {
        return listManager.isTerminated();
    }

    /**
     * Runs the given action on the list immediately.
     *
     * @param action The action to run on the list.
     * @throws IllegalStateException if an {@link InterruptedException} or {@link ExecutionException} is received. In the case of
     *                               {@link InterruptedException}, it will be wrapped. With {@link ExecutionException}, its underlying
     *                               exception will be wrapped.
     * @see ExecutorService#submit(Runnable)
     */
    public void run(Consumer<ArrayList<E>> action) {
        if (listManager.isShutdown() || listManager.isTerminated()) {
            return;
        }

        try {
            listManager.submit(() -> action.accept(list)).get();
        } catch (RejectedExecutionException exception) {
            if (listManager.isShutdown() || listManager.isTerminated()) {
                return;
            }

            throw new IllegalStateException(exception);
        } catch (ExecutionException exception) {
            throw new IllegalStateException(exception.getCause());
        } catch (InterruptedException exception) {
            throw new IllegalStateException(exception);
        }
    }

    /**
     * Iterates over the given list immediately, where the given action is applied to each element of the list.
     *
     * @param action The action to run on the list's elements.
     * @throws IllegalStateException if an {@link InterruptedException} or {@link ExecutionException} is received. In the case of
     *                               {@link InterruptedException}, it will be wrapped. With {@link ExecutionException}, its underlying
     *                               exception will be wrapped.
     * @see ExecutorService#submit(Runnable)
     */
    public void iterate(Consumer<E> action) {
        if (listManager.isShutdown() || listManager.isTerminated()) {
            return;
        }

        try {
            listManager.submit(() -> {
                for (E element : list) {
                    action.accept(element);
                }
            }).get();
        } catch (RejectedExecutionException exception) {
            if (listManager.isShutdown() || listManager.isTerminated()) {
                return;
            }

            throw new IllegalStateException(exception);
        } catch (ExecutionException exception) {
            throw new IllegalStateException(exception.getCause());
        } catch (InterruptedException exception) {
            throw new IllegalStateException(exception);
        }
    }

    /**
     * Schedules a one-shot task to modify the list, becoming enabled after the given delay.
     *
     * @param action The action to run on the list.
     * @param delay  The time from now to the task's delayed execution.
     * @param unit   The time unit of the {@code delay} parameter.
     * @return a {@link ScheduledFuture} representing pending completion of the task and whose {@link ScheduledFuture#get()} method will
     * return {@code null} upon completion.
     * @throws RejectedExecutionException if the task cannot be scheduled for execution
     * @throws NullPointerException       if command or unit is null
     */
    public ScheduledFuture<?> schedule(Consumer<ArrayList<E>> action, long delay, TimeUnit unit) {
        ScheduledFuture<?> scheduledFuture = null;

        try {
            return listManager.schedule(() -> action.accept(list), delay, unit);
        } catch (RejectedExecutionException exception) {
            if (!listManager.isShutdown() && !listManager.isTerminated()) {
                throw new IllegalStateException(exception.getCause());
            }
        }

        return scheduledFuture;
    }

    /**
     * Schedules a one-shot task to iterate over the list, becoming enabled after the given delay.
     *
     * @param action The action to run on the list's elements.
     * @param delay  The time from now to the task's delayed execution.
     * @param unit   The time unit of the {@code delay} parameter.
     * @return a {@link ScheduledFuture} representing pending completion of the task and whose {@link ScheduledFuture#get()} method will
     * return {@code null} upon completion.
     * @throws RejectedExecutionException if the task cannot be scheduled for execution
     * @throws NullPointerException       if command or unit is null
     * @see ScheduledExecutorService#schedule(Runnable, long, TimeUnit)
     */
    public ScheduledFuture<?> scheduleIterate(Consumer<E> action, long delay, TimeUnit unit) {
        ScheduledFuture<?> scheduledFuture = null;

        try {
            return listManager.schedule(
                () -> {
                    for (E element : list) {
                        action.accept(element);
                    }
                },
                delay,
                unit
            );
        } catch (RejectedExecutionException exception) {
            if (!listManager.isShutdown() && !listManager.isTerminated()) {
                throw new IllegalStateException(exception.getCause());
            }
        }

        return scheduledFuture;
    }

    /**
     * Schedules a recurring task to modify the list, after the given initial delay.
     *
     * @param action       The action to run on the list.
     * @param initialDelay The time to delay first execution
     * @param period       The period between successive executions
     * @param unit         The time unit of the initialDelay and period parameters
     * @return A {@link ScheduledFuture} representing pending completion of the series of repeated tasks.  The future's {@link Future#get()}
     * method will never return normally, and will throw an exception upon task cancellation or abnormal termination of a task execution.
     * @throws RejectedExecutionException if the task cannot be scheduled for execution
     * @throws NullPointerException       if command or unit is null
     * @throws IllegalArgumentException   if period less than or equal to zero
     * @see ScheduledExecutorService#scheduleAtFixedRate(Runnable, long, long, TimeUnit)
     */
    public ScheduledFuture<?> scheduleAtFixedRate(Consumer<ArrayList<E>> action, long initialDelay, long period, TimeUnit unit) {
        ScheduledFuture<?> scheduledFuture = null;

        try {
            return listManager.scheduleAtFixedRate(() -> action.accept(list), initialDelay, period, unit);
        } catch (RejectedExecutionException exception) {
            if (!listManager.isShutdown() && !listManager.isTerminated()) {
                throw new IllegalStateException(exception.getCause());
            }
        }

        return scheduledFuture;
    }

    /**
     * Schedules a recurring task to iterate over the list, after the given initial delay.
     *
     * @param action       The action to run on the list's elements.
     * @param initialDelay The time to delay first execution.
     * @param period       The period between successive executions.
     * @param unit         The time unit of the initialDelay and period parameters.
     * @return A {@link ScheduledFuture} representing pending completion of the series of repeated tasks.  The future's {@link Future#get()}
     * method will never return normally, and will throw an exception upon task cancellation or abnormal termination of a task execution.
     * @throws RejectedExecutionException if the task cannot be scheduled for execution.
     * @throws NullPointerException       if command or unit is null.
     * @throws IllegalArgumentException   if period less than or equal to zero.
     * @see ScheduledExecutorService#scheduleAtFixedRate(Runnable, long, long, TimeUnit)
     */
    public ScheduledFuture<?> scheduledIterateAtFixedRate(Consumer<E> action, long initialDelay, long period, TimeUnit unit) {
        ScheduledFuture<?> scheduledFuture = null;

        try {
            scheduledFuture = listManager.scheduleAtFixedRate(() -> {
                    for (E element : list) {
                        action.accept(element);
                    }
                },
                initialDelay,
                period,
                unit
            );
        } catch (RejectedExecutionException exception) {
            if (!listManager.isShutdown() && !listManager.isTerminated()) {
                throw new IllegalStateException(exception.getCause());
            }
        }

        return scheduledFuture;
    }

    /**
     * Schedules a task to modify the list, with a delay after each task run, after the given initial delay.
     *
     * @param action       The action to run on the list.
     * @param initialDelay The time to delay first execution.
     * @param delay        The delay between the termination of one execution and the commencement of the next.
     * @param unit         The time unit of the initialDelay and delay parameters.
     * @return a {@link ScheduledFuture} representing pending completion of the series of repeated tasks.  The future's {@link Future#get()}
     * method will never return normally, and will throw an exception upon task cancellation or abnormal termination of a task execution.
     * @throws RejectedExecutionException if the task cannot be scheduled for execution.
     * @throws NullPointerException       if command or unit is null.
     * @throws IllegalArgumentException   if delay less than or equal to zero.
     * @see ScheduledExecutorService#scheduleWithFixedDelay(Runnable, long, long, TimeUnit)
     */
    public ScheduledFuture<?> scheduleWithFixedDelay(Consumer<ArrayList<E>> action, long initialDelay, long delay, TimeUnit unit) {
        ScheduledFuture<?> scheduledFuture = null;

        try {
            scheduledFuture = listManager.scheduleWithFixedDelay(() -> action.accept(list), initialDelay, delay, unit);
        } catch (RejectedExecutionException exception) {
            if (!listManager.isShutdown() && !listManager.isTerminated()) {
                throw new IllegalStateException(exception.getCause());
            }
        }

        return scheduledFuture;
    }

    /**
     * Schedules a task to iterate over the list, with a delay after each task run, after the given initial delay.
     *
     * @param action       The action to run on the list's elements.
     * @param initialDelay The time to delay first execution.
     * @param delay        The delay between the termination of one execution and the commencement of the next.
     * @param unit         The time unit of the initialDelay and delay parameters.
     * @return a {@link ScheduledFuture} representing pending completion of the series of repeated tasks.  The future's {@link Future#get()}
     * method will never return normally, and will throw an exception upon task cancellation or abnormal termination of a task execution.
     * @throws RejectedExecutionException if the task cannot be scheduled for execution.
     * @throws NullPointerException       if command or unit is null.
     * @throws IllegalArgumentException   if delay less than or equal to zero.
     * @see ScheduledExecutorService#scheduleWithFixedDelay(Runnable, long, long, TimeUnit)
     */
    public ScheduledFuture<?> scheduledIterateWithFixedDelay(Consumer<E> action, long initialDelay, long delay, TimeUnit unit) {
        ScheduledFuture<?> scheduledFuture = null;

        try {
            scheduledFuture = listManager.scheduleWithFixedDelay(
                () -> {
                    for (E element : list) {
                        action.accept(element);
                    }
                },
                initialDelay,
                delay,
                unit
            );
        } catch (RejectedExecutionException exception) {
            if (!listManager.isShutdown() && !listManager.isTerminated()) {
                throw new IllegalStateException(exception.getCause());
            }
        }

        return scheduledFuture;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return list.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return list.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return list.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public E set(int index, E element) {
        return list.set(index, element);
    }

    @Override
    public void add(int index, E element) {
        list.add(index, element);
    }

    @Override
    public E remove(int index) {
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }
}
