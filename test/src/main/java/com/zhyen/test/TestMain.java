package com.zhyen.test;


import com.zhyen.base.thread_pool.ZhyenThreadPool;
import com.zhyen.test.class_loader.ClassLoader;
import com.zhyen.test.thread_pool.ThreadPoolDemo;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class TestMain {

    public static void main(String[] arg) {
        //testClassLoader();
        //testThreadPoolDemo();
        testThreadPool();
        //testThreadPool();
    }

    private static void testThreadPool() {

        final ZhyenThreadPool threadPool = ZhyenThreadPool.getInstance();

//        log("开始吧。。。。。");
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(5000);
//                    log("暂停");
//                    log("isTerminated = " + threadPool.isTerminated());
//                    threadPool.pause();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                try {
//                    Thread.sleep(15000);
//                    log("恢复");
//                    threadPool.resume();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();


        for (int i = 0; i < 100; i++) {
            final int index = i;
            Future<Integer> submit = threadPool.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    Thread.sleep(5000);
                    log("执行内容");
                    return index;
                }
            });
//            try {
//                Integer integer = submit.get();
//                log("submit get = " + integer.toString());
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        log("结束");
        log("isShutdown = " + threadPool.isShutdown());
        log("isTerminated = " + threadPool.isTerminated());
    }

    private static void testThreadPoolDemo() {
        int processors = Runtime.getRuntime().availableProcessors();
        log("线程 = " + processors);
        log("---------------------CacheThreadPool--------------------------");
        ThreadPoolDemo threadPoolDemo = new ThreadPoolDemo();
        threadPoolDemo.cachedThreadPoolDemo();
        log("---------------------FixedThreadPool--------------------------");
        threadPoolDemo.fixedThreadPoolDemo();
        log("---------------------singleThreadPool--------------------------");
        threadPoolDemo.singleThreadExecutorDemo();
        log("---------------------scheduledThreadPool--------------------------");
        threadPoolDemo.scheduledThreadPoolDemo();
    }

    private static void testClassLoader() {
        ClassLoader classLoader = new ClassLoader();
        try {
            String loadClass = classLoader.loadClass("冯星");
            log("最后的结果:" + loadClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void log(String msg) {
        System.out.println(msg);
    }
}
