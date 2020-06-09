package com.zhyen.base.thread_pool;

import com.zhyen.base.thread_pool.monitor.IMonitorThreadPool;
import com.zhyen.base.thread_pool.monitor.MonitorThreadPool;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义线程池
 * 使用要考虑到应用场景的需求，如何拒绝新来的请求任务？如何处理等待队列中的任务？如何处理正在执行的任务？
 * <p>
 * 1. 线程池所有方法解释
 * 2. 线程池暂停恢复功能
 * 3. 线程池监控
 * 4. 取消任务
 */
public class ZhyenThreadPool extends ThreadPoolExecutor {

    private static final String TAG = ZhyenThreadPool.class.getSimpleName();

    static {

    }

    //面对CPU密集型的任务,corePoolSize设置为CPU核心数+1，maxPoolSize设置为核心数的2倍
    //面对IO密集型任务,核心数乘以4倍作为核心线程数，然后核心数乘以5倍作为最大线程数
    private static final int DEFAULT_CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors() + 1;
    private static final int DEFAULT_MAX_POOL_SIZE = 2 * Runtime.getRuntime().availableProcessors() + 1;
    private static final int DEFAULT_KEEP_ALIVE_TIME = 60;
    private static TimeUnit DEFAULT_KEEP_ALIVE_UNIT = TimeUnit.SECONDS;
    //默认队列容量
    private static final int DEFAULT_CAPACITY = 20;
    private RejectedExecutionHandler defaultHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            monitorThreadPool.rejectedExecutionHandler(r, executor);
        }
    };


    public ThreadFactory defaultThreadFactory() {
        return new EventThreadFactory();
    }


    private class EventThreadFactory implements ThreadFactory {
        // 用AtomicInteger来为线程计数，每次加1
        private final AtomicInteger poolNumber = new AtomicInteger(1);
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        // 线程所属的线程组
        private final ThreadGroup group;
        // 线程名称
        private final String namePrefix;

        EventThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "zhyen-" +
                    poolNumber.getAndIncrement() +
                    "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    monitorThreadPool.uncaughtException(t, e);
                    e.printStackTrace();
                }
            });
            if (t.isDaemon())
                t.setDaemon(false);
            // 设置相同的线程优先级，避免线程池里的线程根据优先级争抢资源，保证任务的正常执行
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }

    private static ZhyenThreadPool zhyenThreadPool;
    /**
     * 用于控制线程池开始与停止
     */
    private boolean isPause = false;
    private ReentrantLock pauseLock = new ReentrantLock();
    private Condition unPaused = pauseLock.newCondition();

    /**
     * 用于保存带tag的任务 方便保存
     */
    private HashMap<Object, RunnableFuture> tagTask = new HashMap<>();
    //监控每个任务的时间
    private IMonitorThreadPool monitorThreadPool = new MonitorThreadPool();


    private ZhyenThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        monitorThreadPool.create(this);
    }

    public static synchronized ZhyenThreadPool getInstance() {
        if (zhyenThreadPool == null) {
            synchronized (ZhyenThreadPool.class) {
                if (zhyenThreadPool == null) {
                    zhyenThreadPool = new ZhyenThreadPool(DEFAULT_CORE_POOL_SIZE,
                            DEFAULT_MAX_POOL_SIZE,
                            DEFAULT_KEEP_ALIVE_TIME,
                            DEFAULT_KEEP_ALIVE_UNIT,
                            //无界队列
                            //new LinkedBlockingDeque<Runnable>(),
                            //有届队列
                            new ArrayBlockingQueue<Runnable>(DEFAULT_CAPACITY),
                            Executors.defaultThreadFactory(),
                            new DiscardPolicy()
                    );
                }
            }
        }
        return zhyenThreadPool;
    }
    //////////////////////////////////////提交任务系列//////////////////////////////////////


    /**
     * 自定义添加任务
     *
     * @param runnable
     * @param tag      标识
     */
    public void addTask(Runnable runnable, Object tag) {
        RunnableFuture<?> submit = (RunnableFuture<?>) submit(runnable);
        tagTask.put(tag, submit);
        
    }

    public boolean cancelTask(Object tag) {
        boolean cancel = false;
        Set<Object> keySet = tagTask.keySet();
        if (keySet.contains(tag)) {
            RunnableFuture future = tagTask.get(tag);
            if (future == null) {
                tagTask.remove(tag);
                return false;
            }
            if (!future.isCancelled()) {
                cancel = future.cancel(true);
                if (cancel) {
                    //当取消成功后，从本地Key集合中移除标识
                    tagTask.remove(tag);
                }
            } else {
                tagTask.remove(tag);
            }
        }
        return cancel;
    }


    /**
     * 执行一个Runnable对象
     * 核心逻辑 包括：添加任务到队列、执行run方法、拒绝策略、状态管理
     *
     * @param command
     */
    @Override
    public void execute(Runnable command) {
        monitorThreadPool.execute(command);
        super.execute(command);
    }

    /**
     * submit系列 后续会封装成  RunnableFuture 然后执行execute
     * <p>
     * submit 方法是非阻塞的，每次调用 submit 方法提交任务到线程池之后，会立即返回与任务相关联的 Future<V>，然后当前线程继续向后执行；
     *
     * @param task
     * @return
     */
    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(task);
    }

    /**
     * submit系列 后续会封装成  RunnableFuture 然后执行execute
     *
     * @param task
     * @param <T>
     * @return
     */
    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(task);
    }

    /**
     * submit系列 后续会封装成  RunnableFuture 然后执行execute
     *
     * @param task
     * @param result
     * @param <T>
     * @return
     */
    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return super.submit(task, result);
    }

    //////////////////////////////////////暂停\恢复线程池相关代码//////////////////////////////////////

    /**
     * 暂停线程池方法
     */
    public void pause() {
        monitorThreadPool.pause();
        pauseLock.lock();
        try {
            isPause = true;
        } finally {
            pauseLock.unlock();
        }
    }

    /**
     * 恢复线程池方法
     */
    public void resume() {
        monitorThreadPool.resume();
        pauseLock.lock();
        try {
            isPause = false;
            unPaused.signalAll();
        } finally {
            pauseLock.unlock();
        }
    }
//////////////////////////////////////钩子方法（暂停/恢复核心代码）//////////////////////////////////////

    /**
     * 任务执行前钩子方法
     *
     * @param t
     * @param r
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        pauseLock.lock();
        try {
            while (isPause) {
                unPaused.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            t.interrupt();
        } finally {
            monitorThreadPool.beforeExecute(t, r);
            pauseLock.unlock();
        }
    }


    /**
     * 任务执行后钩子方法
     *
     * @param r
     * @param t
     */
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        if (t == null && r instanceof Future<?>) {
            try {
                Object result = ((Future<?>) r).get();
            } catch (CancellationException ce) {
                t = ce;
            } catch (ExecutionException ee) {
                t = ee.getCause();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt(); // ignore/reset
            }
        }
        if (t != null)
            System.out.println(t);
        monitorThreadPool.afterExecute(r, t);
    }

    //////////////////////////////////////执行一堆任务//////////////////////////////////////

    /**
     * invokeAll 方法是阻塞的，只有当提交的多个任务都执行完毕之后，invokeAll 方法才会返回，
     * 执行结果会以List<Future<V>> 返回,
     * 该 List<Future<V>> 中的每个 Future<V> 是和提交任务时的 Collection<Callable<V>> 中的任务 Callable<V> 一 一对应的。
     * 带 timeout 参数的 invokeAll 就是设置一个超时时间，如果超过这个时间 invokeAll 中提交的所有任务还有没全部执行完，
     * 那么没有执行完的任务会被取消（中断），之后同样以一个 List<Future<V>> 返回执行的结果。
     *
     * @param tasks
     * @param <T>
     * @return
     * @throws InterruptedException
     */
    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        monitorThreadPool.invokeAll(tasks);
        return super.invokeAll(tasks);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        monitorThreadPool.invokeAll(tasks, timeout, unit);
        return super.invokeAll(tasks, timeout, unit);
    }

    //////////////////////////////////////执行一堆任务,返回第一个完成的任务//////////////////////////////////////

    /**
     * invokeAny 方法也是阻塞的，与 invokeAll 方法的不同之处在于，当所提交的一组任务中的任何一个任务完成之后，
     * invokeAny 方法便会返回（返回的结果便是那个已经完成的任务的返回值），而其他任务会被取消（中断）。
     *
     * @param tasks
     * @param <T>
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws ExecutionException, InterruptedException {
        monitorThreadPool.invokeAny(tasks);
        return super.invokeAny(tasks);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
        monitorThreadPool.invokeAny(tasks, timeout, unit);
        return super.invokeAny(tasks, timeout, unit);
    }


    //////////////////////////////////////关闭线程池(必须显式的关闭线程池，否则线程池不会自己关闭)//////////////////////////////////////

    /**
     * 停止线程池
     * 1. 先调用shutdown方法，线程池不会再接收任何新的任务，但是已经提交的任务还会继续执行，包括队列中的.
     * 2. 调用awaitTermination方法，这个方法可以设定线程池在关闭之前的最大超时时间，如果在超时时间结束之前线程池能够正常关闭，这个方法会返回true，否则，一旦超时，就会返回false。
     * 3. 通常来说我们不可能无限制地等待下去，因此需要我们事先预估一个合理的超时时间，然后去使用这个方法。
     * 4. 如果awaitTermination方法返回false，你又希望尽可能在线程池关闭之后再做其他资源回收工作，你可以考虑再调用一下shutdownNow方法，此时队列中所有尚未被处理的任务都会被丢弃，同时会设置线程池中每个线程的中断标志位。
     * 5. shutdownNow并不保证一定可以让正在运行的线程停止工作，除非提交给线程的任务能够正确响应中断。到了这一步，你可以考虑继续调用awaitTermination方法，或者你直接放弃，去做接下来要做的事情。
     *
     * @param timeout
     * @param unit
     */
    public void stop(long timeout, TimeUnit unit) {
        try {
            // shutdown只是起到通知的作用
            shutdown();
            if (!awaitTermination(timeout, unit)) {
                // 超时的时候向线程池中所有的线程发出中断(interrupted)。
                shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

        }
    }

    /**
     * 线程池是否已关闭
     *
     * @return
     */
    @Override
    public boolean isShutdown() {
        return super.isShutdown();
    }

    /**
     * shutdown 方法的作用是向线程池发送关闭的指令。
     * 一旦在线程池上调用 shutdown 方法之后，线程池便不能再接受新的任务；
     * 如果此时还向线程池提交任务，那么将会执行拒绝策略。
     * 之后线程池不会立刻关闭，直到之前已经提交到线程池中的所有任务（包括正在运行的任务和在队列中等待的任务）都已经处理完成，才会关闭。
     */
    @Override
    public void shutdown() {
        monitorThreadPool.shutdown();
        super.shutdown();
    }

    /**
     * 与 shutdown 不同，shutdownNow 会立即关闭线程池 —— 当前在线程池中运行的任务会全部被取消，然后返回线程池中所有正在等待的任务。
     *
     * @return
     */
    @Override
    public List<Runnable> shutdownNow() {
        monitorThreadPool.shutdownNow();
        return super.shutdownNow();
    }

    //////////////////////////////////////线程池已经关闭了//////////////////////////////////////

    /**
     * 线程池关闭后要执行的方法
     */
    @Override
    protected void terminated() {
        super.terminated();
        monitorThreadPool.terminated();
    }

    /**
     * 用来判断线程池是否已经关闭
     * 调用 awaitTermination 之后，在 timeout 时间内，如果线程池没有关闭，则阻塞当前线程，否则返回 true；
     * 当超过 timeout 的时间后，若线程池已经关闭则返回 true，否则返回 false。
     * <p>
     * 该方法一般这样使用：
     * 1. 任务全部提交完毕之后，我们调用 shutdown 方法向线程池发送关闭的指令；
     * 2. 然后我们通过 awaitTermination 来检测到线程池是否已经关闭，可以得知线程池中所有的任务是否已经执行完毕；
     * 3. 线程池执行完已经提交的所有任务，并将自己关闭；
     * 4. 调用 awaitTermination 方法的线程停止阻塞，并返回 true；
     *
     * @param timeout
     * @param unit
     * @return
     * @throws InterruptedException
     */
    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        monitorThreadPool.awaitTermination(timeout, unit);
        return super.awaitTermination(timeout, unit);
    }

    //////////////////////////////////////线程池监控方法//////////////////////////////////////


    /**
     * 线程池需要执行的任务数量。
     *
     * @return
     */
    @Override
    public long getTaskCount() {
        return super.getTaskCount();
    }

    /**
     * 线程池在运行过程中已完成的任务数量，小于或等于taskCount。
     * largestPoolSize：线程池曾经创建过的最大线程数量，通过这个数据可以知道线程池是否满过。如等于线程池的最大大小，则表示线程池曾经满了。
     *
     * @return
     */
    @Override
    public long getCompletedTaskCount() {
        return super.getCompletedTaskCount();
    }

    /**
     * 线程池的线程数量。如果线程池不销毁的话，池里的线程不会自动销毁，所以这个大小只增不减。
     *
     * @return
     */
    @Override
    public int getPoolSize() {
        return super.getPoolSize();
    }

    /**
     * 获取活动的线程数。
     *
     * @return
     */
    @Override
    public int getActiveCount() {
        return super.getActiveCount();
    }

    /**
     * 线程池曾经创建过的最大线程数量，通过这个数据可以知道线程池是否满过。
     * 如等于线程池的最大大小，则表示线程池曾经满了。
     *
     * @return
     */
    @Override
    public int getLargestPoolSize() {
        return super.getLargestPoolSize();
    }


    @Override
    public BlockingQueue<Runnable> getQueue() {
        return super.getQueue();
    }

    //////////////////////////////////////设置线程池构造中的参数//////////////////////////////////////

    @Override
    public int getCorePoolSize() {
        return super.getCorePoolSize();
    }


    /**
     * 设置核心线程数。 这将覆盖构造函数中设置的任何值。
     * 如果新值小于当前值，则多余的现有线程将在下次空闲时终止。
     * 如果更大，将在需要时启动新线程以执行任何排队的任务。
     * <p>
     * 如果 核心线程数 > 最大线程数 引发IEA的返回代码
     *
     * @param corePoolSize
     */
    @Override
    public void setCorePoolSize(int corePoolSize) {
        super.setCorePoolSize(corePoolSize);
        monitorThreadPool.setCorePoolSize(corePoolSize);
    }

    /**
     * Returns the maximum allowed number of threads.
     *
     * @return
     */
    @Override
    public int getMaximumPoolSize() {
        return super.getMaximumPoolSize();
    }

    /**
     * 设置允许的最大线程数。 这将覆盖构造函数中设置的任何值。 如果新值小于当前值，则多余的现有线程将在下次空闲时终止。
     *
     * @param maximumPoolSize
     */
    @Override
    public void setMaximumPoolSize(int maximumPoolSize) {
        super.setMaximumPoolSize(maximumPoolSize);
        monitorThreadPool.setMaximumPoolSize(maximumPoolSize);
    }

    @Override
    public long getKeepAliveTime(TimeUnit unit) {
        return super.getKeepAliveTime(unit);
    }

    @Override
    public void setKeepAliveTime(long time, TimeUnit unit) {
        super.setKeepAliveTime(time, unit);
        monitorThreadPool.setKeepAliveTime(time, unit);
    }

    @Override
    public RejectedExecutionHandler getRejectedExecutionHandler() {
        return super.getRejectedExecutionHandler();
    }

    @Override
    public void setRejectedExecutionHandler(RejectedExecutionHandler handler) {
        super.setRejectedExecutionHandler(handler);
        monitorThreadPool.setRejectedExecutionHandler(handler);
    }

    @Override
    public ThreadFactory getThreadFactory() {
        return super.getThreadFactory();
    }

    @Override
    public void setThreadFactory(ThreadFactory threadFactory) {
        super.setThreadFactory(threadFactory);
        monitorThreadPool.setThreadFactory(threadFactory);
    }

}