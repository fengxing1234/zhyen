package com.zhyen.test.thread_pool;

import com.zhyen.test.TestMain;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo {
    private static final String TAG = ThreadPoolDemo.class.getSimpleName();

    private int index;

    public void cachedThreadPoolDemo() {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        Future<?> submit = cachedThreadPool.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(3000);
                return "A";
            }
        });

        try {
            String a = (String) submit.get();
            TestMain.log("a= " + a);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        String b = "B";
        cachedThreadPool.submit(new Runnable() {
            @Override
            public void run() {

            }
        }, b);
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
        //execute(cachedThreadPool);
    }

    public void fixedThreadPoolDemo() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        execute(fixedThreadPool);
    }

    public void singleThreadExecutorDemo() {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        execute(singleThreadExecutor);
    }

    public void scheduledThreadPoolDemo() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);
        //定时执行一次的任务，延迟1s后执行
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", delay 1s");
            }
        }, 1, TimeUnit.SECONDS);
        //周期性地执行任务，延迟2s后，每3s一次地周期性执行任务
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", every 3s");
            }
        }, 2, 3, TimeUnit.SECONDS);
    }

    private void execute(ExecutorService cachedThreadPool) {
        for (int i = 0; i < 5; i++) {
            final int index = i;
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    TestMain.log(Thread.currentThread().getName() + ", index=" + index);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

}
