package com.zhyen.base.multi_thread;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedTest {

    // ------------------------- 悲观锁的调用方式 -------------------------
    public synchronized void testSynchronized() {
        // 操作同步资源
    }

    // 需要保证多个线程使用的是同一个锁
    private ReentrantLock reentrantLock = new ReentrantLock();

    public void testReentrant() {
        reentrantLock.lock();
        // 操作同步资源
        reentrantLock.unlock();
    }

    // ------------------------- 乐观锁的调用方式 -------------------------
    // 需要保证多个线程使用的是同一个AtomicInteger
    private AtomicInteger atomicInteger = new AtomicInteger();

    public void testAtomicInter() {
        atomicInteger.incrementAndGet();
    }
}
