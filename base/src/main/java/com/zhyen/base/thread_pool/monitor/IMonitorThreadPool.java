package com.zhyen.base.thread_pool.monitor;

import com.zhyen.base.thread_pool.ZhyenThreadPool;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public interface IMonitorThreadPool {

    void create(ZhyenThreadPool zhyenThreadPool);

    void execute(Runnable command);

    void pause();

    void resume();

    void beforeExecute(Thread t, Runnable r);

    void afterExecute(Runnable r, Throwable t);

    void shutdown();

    void shutdownNow();

    void terminated();

    void awaitTermination(long timeout, TimeUnit unit);

    void rejectedExecutionHandler(Runnable r, ThreadPoolExecutor executor);

    /**
     * 上报对服务器
     */
    void report();

    /**
     * 拿到服务器数据修改配置
     */
    void modify();

    /**
     * 统计
     *
     * @param startTime
     * @param zhyenThreadPool
     */
    void statistics(Date startTime, ZhyenThreadPool zhyenThreadPool);

    void setCorePoolSize(int corePoolSize);

    void setMaximumPoolSize(int maximumPoolSize);


    void setKeepAliveTime(long time, TimeUnit unit);

    void setThreadFactory(ThreadFactory threadFactory);

    void setRejectedExecutionHandler(RejectedExecutionHandler handler);


    <T> void invokeAny(Collection<? extends Callable<T>> tasks);

    <T> void invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit);


    <T> void invokeAll(Collection<? extends Callable<T>> tasks);

    <T> void invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit);

    void uncaughtException(Thread t, Throwable e);
}
