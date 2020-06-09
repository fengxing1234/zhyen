package com.zhyen.base.thread_pool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureTaskWrapper<T> extends FutureTask<T> {

    public FutureTaskWrapper(Callable<T> callable) {
        super(callable);
    }

    public FutureTaskWrapper(Runnable runnable, T result) {
        super(runnable, result);
    }

    /**
     * 用来返回和 Future 关联的任务的结果。
     * 带参数的 get 方法指定一个超时时间，在超时时间内该方法会阻塞当前线程，直到获得结果 。
     * <p>
     * 1. 如果在给定的超时时间内没有获得结果，那么便抛出 TimeoutException 异常；
     * 2. 或者执行的任务被取消（此时抛出 CancellationException 异常）；
     * 3. 或者执行任务时出错，即执行过程中出现异常（此时抛出 ExecutionException 异常）；
     * 4. 或者当前线程被中断（此时抛出 InterruptedException 异常 —— 注意，当前线程是指调用 get 方法的线程，而不是运行任务的线程）。
     *
     * @return 返回的结果
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Override
    public T get() throws ExecutionException, InterruptedException {
        return super.get();
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
        return super.get(timeout, unit);
    }

    /**
     * 该方法便可以用来（尝试）终止一个任务。(非阻塞方法)
     * 1. 如果任务运行之前调用了该方法，那么任务就不会被运行；
     * 2. 如果任务已经完成或者已经被取消，那么该方法方法不起作用；
     * 3. 如果任务正在运行，并且 cancel 传入参数为 true，那么便会去终止与 Future 关联的任务。
     * <p>
     * cancel(false) 只 取消已经提交但还没有被运行的任务（即任务就不会被安排运行）；
     * cancel(true) 会取消所有已经提交的任务，包括 正在等待的 和 正在运行的 任务。
     *
     * @param mayInterruptIfRunning
     * @return
     */
    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return super.cancel(mayInterruptIfRunning);
    }

    /**
     * 该方法是非阻塞的。在任务结束之前，如果任务被取消了，该方法返回 true，否则返回 false；如果任务已经完成，该方法则一直返回 false。
     *
     * @return
     */
    @Override
    public boolean isCancelled() {
        return super.isCancelled();
    }

    /**
     * isDone 方法：该方法同样是非阻塞的。如果任务已经结束（正常结束，或者被取消，或者执行出错），返回 true，否则返回 false。f
     * @return
     */
    @Override
    public boolean isDone() {
        return super.isDone();
    }
}
