package com.gougou.thread.lock.live.optimization;

import org.apache.commons.lang.math.RandomUtils;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

class Account {

    private int balance;

    private final Lock lock = new ReentrantLock();

    // 转账

    void transfer(Account tar, int amt) throws InterruptedException {

        boolean flag = true;
        //RandomUtils.nextLong();
        while (flag) {
            if (this.lock.tryLock(RandomUtils.nextLong(), NANOSECONDS)) {
                try {
                    if (tar.lock.tryLock(RandomUtils.nextLong(), NANOSECONDS)) {
                        try {
                            this.balance -= amt;
                            tar.balance += amt;
                            flag = false;
                        } finally {
                            tar.lock.unlock();
                        }
                    }//if
                } finally {
                    this.lock.unlock();
                }
            }//if
        }//while
    }//transfer
    //TODO 优化建议如果阻塞在tar.lock.tryLock上一段时间，this.lock是不能释放的。
}

