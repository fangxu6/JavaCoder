package com.gougou.thread.lock.fair;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockFairTest {
    static Lock lock = new ReentrantLock(true);
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                for (int j = 0; j < 2; j++) {
                    lock.lock();
                    System.out.println("当前线程：" + Thread.currentThread()
                            .getName());
                    lock.unlock();
                }
            }).start();
        }
    }
}

