package thread.lock;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


class X {
    private final Lock rtl =
            new ReentrantLock();
    int value;
    public int get() {
        // 获取锁
        rtl.lock();         //②
        try {
            return value;
        } finally {
            // 保证锁能释放
            rtl.unlock();
        }
    }
    public void addOne() {
        // 获取锁
        rtl.lock();
        try {
            value = 1 + get(); //①//可重复获取
        } finally {
            // 保证锁能释放
            rtl.unlock();
        }
    }
}
