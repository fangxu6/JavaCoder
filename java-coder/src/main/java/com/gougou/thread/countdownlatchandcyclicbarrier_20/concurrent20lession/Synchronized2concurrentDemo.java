package com.gougou.thread.countdownlatchandcyclicbarrier_20.concurrent20lession;

import java.util.*;

/**
 * className:Synchronized2concurrentDemo
 * package:main.java.thread.concurrent20lession
 * Description:
 *
 * @Date:2022/10/2119:20
 * @Author:fangxu6@gmail.com
 */
public class Synchronized2concurrentDemo {
    public static void main(String[] args) {
        //同步容器转并发容器
        List list = Collections.
                synchronizedList(new ArrayList());
        Set set = Collections.
                synchronizedSet(new HashSet());
        Map map = Collections.
                synchronizedMap(new HashMap());
    }


    //在容器领域一个容易被忽视的“坑”是用迭代器遍历容器，
    //组合操作往往隐藏着竞态条件问题，即便每个操作都能保证原子性，也并不能保证组合操作的原子性，这个一定要注意。
    void worngMethod() {

        List list = Collections.
                synchronizedList(new ArrayList());
        Iterator i = list.iterator();
        while (i.hasNext()) {
//            foo(i.next());
            i.next();
        }
    }

    // =>
    void rightMethod() {

        List list = Collections.
                synchronizedList(new ArrayList());
        synchronized (list) {
            Iterator i = list.iterator();
            while (i.hasNext()) {
//                foo(i.next());
                i.next();
            }
        }
    }
}
