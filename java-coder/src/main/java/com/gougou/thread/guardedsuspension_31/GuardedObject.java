package com.gougou.thread.guardedsuspension_31;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

/**
 * className:GuardedObject
 * package:thread.guardedsuspension_31
 * Description:
 *
 * @Date:2022/11/1721:32
 * @Author:fangxu6@gmail.com
 */

class GuardedObject<T>{
    //受保护的对象
    T obj;
    final Lock lock =
            new ReentrantLock();
    final Condition done =
            lock.newCondition();
    final int timeout=1;
    //获取受保护对象
    T get(Predicate<T> p) {
        lock.lock();
        try {
            //MESA管程推荐写法
            while(!p.test(obj)){
                done.await(timeout,
                        TimeUnit.SECONDS);
            }
        }catch(InterruptedException e){
            throw new RuntimeException(e);
        }finally{
            lock.unlock();
        }
        //返回非空的受保护对象
        return obj;
    }
    //事件通知方法
    void onChanged(T obj) {
        lock.lock();
        try {
            this.obj = obj;
            done.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
