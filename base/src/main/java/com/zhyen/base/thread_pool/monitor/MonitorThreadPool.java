package com.zhyen.base.thread_pool.monitor;

import com.zhyen.base.thread_pool.ZhyenThreadPool;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 实现线程池的监控
 */
public class MonitorThreadPool implements IMonitorThreadPool {

    private ZhyenThreadPool threadPool;
    private static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
    private ConcurrentHashMap<String, Date> taskTime = new ConcurrentHashMap<>();

    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    private StringBuilder strBuilder = new StringBuilder();

    @Override
    public void create(ZhyenThreadPool zhyenThreadPool) {
        this.threadPool = zhyenThreadPool;
        String time = dateFormat.format(new Date());

        log("创建线程池 " + time + "对象:" + threadPool.toString());
    }

    @Override
    public void execute(Runnable command) {
        log(dateFormat.format(new Date()) + " 对象 :" + command.hashCode());
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void beforeExecute(Thread t, Runnable r) {
        taskTime.put(String.valueOf(r.hashCode()), new Date());
    }

    @Override
    public void afterExecute(Runnable r, Throwable t) {
        Date stopDate = new Date();
        Date startTime = taskTime.remove(String.valueOf(r.hashCode()));
        if (startTime == null) {
            log(r.hashCode() + " date是空");
            return;
        }
        long useTime = stopDate.getTime() - startTime.getTime();
        strBuilder.append("-任务-").append(r.hashCode()).append(System.getProperty("line.separator"))
                .append("-耗时-").append(useTime).append(System.getProperty("line.separator"))
                .append("-核心线程数-").append(threadPool.getCorePoolSize()).append(System.getProperty("line.separator"))
                .append("-最大线程数-").append(threadPool.getMaximumPoolSize()).append(System.getProperty("line.separator"))
                .append("-线程存活时间-").append(threadPool.getKeepAliveTime(TimeUnit.SECONDS)).append(System.getProperty("line.separator"))
                .append("-TaskCount-").append(threadPool.getTaskCount()).append(System.getProperty("line.separator"))
                .append("-PoolSize-").append(threadPool.getPoolSize()).append(System.getProperty("line.separator"))
                .append("-CompletedTaskCount-").append(threadPool.getCompletedTaskCount()).append(System.getProperty("line.separator"))
                .append("-ActiveCount-").append(threadPool.getActiveCount()).append(System.getProperty("line.separator"))
                .append("-LargestPoolSize-").append(threadPool.getLargestPoolSize()).append(System.getProperty("line.separator"))
        ;
        if (t != null) {
            strBuilder.append("-错误信息-").append(t.getMessage()).append(System.getProperty("line.separator"));
        }

        log(strBuilder.toString());
    }

    @Override
    public void shutdown() {
        log("shutdown");
    }

    @Override
    public void shutdownNow() {
        log(dateFormat.format(new Date()) + "shutdownNow");
    }

    @Override
    public void terminated() {
        log(dateFormat.format(new Date()) + "terminated");
    }

    @Override
    public void awaitTermination(long timeout, TimeUnit unit) {

    }

    @Override
    public void rejectedExecutionHandler(Runnable r, ThreadPoolExecutor executor) {
        log("执行拒绝策略了");
    }

    @Override
    public void report() {

    }

    @Override
    public void modify() {

    }

    @Override
    public void statistics(Date startTime, ZhyenThreadPool zhyenThreadPool) {

    }

    @Override
    public void setCorePoolSize(int corePoolSize) {

    }

    @Override
    public void setMaximumPoolSize(int maximumPoolSize) {

    }

    @Override
    public void setKeepAliveTime(long time, TimeUnit unit) {

    }

    @Override
    public void setThreadFactory(ThreadFactory threadFactory) {

    }

    @Override
    public void setRejectedExecutionHandler(RejectedExecutionHandler handler) {

    }

    @Override
    public <T> void invokeAny(Collection<? extends Callable<T>> tasks) {

    }

    @Override
    public <T> void invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) {

    }

    @Override
    public <T> void invokeAll(Collection<? extends Callable<T>> tasks) {

    }

    @Override
    public <T> void invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) {

    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {

    }

    private void log(String msg) {
        System.out.println(msg);
    }
}
