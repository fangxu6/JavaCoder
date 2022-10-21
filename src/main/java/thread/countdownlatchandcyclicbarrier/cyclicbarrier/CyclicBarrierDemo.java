package main.java.thread.countdownlatchandcyclicbarrier.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * className:CyclicBarrierDemo
 * package:main.java.thread.countdownlatchandcyclicbarrier.cyclicbarrier
 * Description:
 *
 * @Date:2022/10/2018:12
 * @Author:fangxu6@gmail.com
 */
public class CyclicBarrierDemo {
    public void Check() {
/*
        // 订单队列
        Vector<P> pos;
        // 派送单队列
        Vector<D> dos;
        // 执行回调的线程池
        Executor executor =
                Executors.newFixedThreadPool(1);
        final CyclicBarrier barrier =
                new CyclicBarrier(2, () -> {
                    executor.execute(() -> check());
                });
                //上面在t1和t2执行完后执行barrier，这时候又开启了一个线程去执行check回调，防止耗时结束
                //因为执行完后才会进入下一次的t1和t2线程

        void check () {
            P p = pos.remove(0);
            D d = dos.remove(0);
            // 执行对账操作
            diff = check(p, d);
            // 差异写入差异库
            save(diff);
        }


        //这个是主线程
        void checkAll () {
            // 循环查询订单库
            Thread T1 = new Thread(() -> {
                while (存在未对账订单) {
                    // 查询订单库
                    pos.add(getPOrders());
                    // 等待
                    barrier.await();
                }
            });
            T1.start();
            // 循环查询运单库
            Thread T2 = new Thread(() -> {
                while (存在未对账订单) {
                    // 查询运单库
                    dos.add(getDOrders());
                    // 等待
                    barrier.await();
                }
            });
            T2.start();
        }
        //老师，最后checkAll（） 这里为什么new 了两个Thread  而不是使用线程池
        //作者回复: 反正也不会反复创建，用不用都没关系


 */
    }


}
