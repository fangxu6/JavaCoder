package com.gougou.thread.readwritelock.lazy;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Cache<K, V> {
    final Map<K, V> m =
            new HashMap<>();
    final ReadWriteLock rwl =
            new ReentrantReadWriteLock();
    final Lock r = rwl.readLock();
    final Lock w = rwl.writeLock();

    V get(K key) {
        V v = null;
        //读缓存
        r.lock();   //1
        try {
            v = m.get(key); //2
        } finally {
            r.unlock(); //3
        }
        //缓存中存在，返回
        if (v != null) {    //4
            return v;
        }
        //缓存中不存在，查询数据库
        w.lock();   //5
        try {
            //再次验证
            //其他线程可能已经查询过数据库
            v = m.get(key);
            if (v == null) {
                //查询数据库
                //v = 省略代码无数;
                m.put(key, v);
            }
        } finally {
            w.unlock();
        }
        return v;
    }
    //原因是在高并发的场景下，有可能会有多线程竞争写锁。
    // 假设缓存是空的，没有缓存任何东西，如果此时有三个线程 T1、T2 和 T3 同时调用 get() 方法，
    // 并且参数 key 也是相同的。那么它们会同时执行到代码⑤处，
    // 但此时只有一个线程能够获得写锁，假设是线程 T1，
    // 线程 T1 获取写锁之后查询数据库并更新缓存，最终释放写锁。
    // 此时线程 T2 和 T3 会再有一个线程能够获取写锁，假设是 T2，
    // 如果不采用再次验证的方式，此时 T2 会再次查询数据库。
    // T2 释放写锁之后，T3 也会再次查询一次数据库。
    // 而实际上线程 T1 已经把缓存的值设置好了，T2、T3 完全没有必要再次查询数据库。
    // 所以，再次验证的方式，能够避免高并发场景下重复查询数据的问题。
}
