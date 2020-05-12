package com.zhyen.base.design_mode.singleton;

/**
 * 单例模式
 */
public class Singleton {

    private static volatile Singleton singleton;

    private Singleton() {

    }

    /**
     * DCL 双检查锁机制
     *
     * @return
     */
    public static synchronized Singleton getInstance() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }

    /**
     * 静态内部类实现方式
     *
     * @return
     */
    public static Singleton getInstance2() {
        return SingletonHolder.mSingleton;
    }

    private static class SingletonHolder {
        private static final Singleton mSingleton = new Singleton();
    }
}
