package com.gougou.thread.future_23;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class BoilingWater {
    class T1Task implements Callable<String> {
        FutureTask<String> ft2;

        // T1任务需要T2任务的FutureTask
        T1Task(FutureTask<String> ft2) {
            this.ft2 = ft2;
        }

        @Override
        public String call() throws Exception {
            System.out.println("T1:洗水壶...");
            TimeUnit.SECONDS.sleep(1);

            System.out.println("T1:烧开水...");
            TimeUnit.SECONDS.sleep(15);
            // 获取T2线程的茶叶
            String tf = ft2.get();
            System.out.println("T1:拿到茶叶:" + tf);

            System.out.println("T1:泡茶...");
            return "上茶:" + tf;
        }
    }
    // T2Task需要执行的任务:
// 洗茶壶、洗茶杯、拿茶叶
    class T2Task implements Callable<String> {
        @Override
        public String call() throws Exception {
            System.out.println("T2:洗茶壶...");
            TimeUnit.SECONDS.sleep(1);

            System.out.println("T2:洗茶杯...");
            TimeUnit.SECONDS.sleep(2);

            System.out.println("T2:拿茶叶...");
            TimeUnit.SECONDS.sleep(1);
            return "龙井";
        }
    }
    public void boiling() {

// 创建任务T2的FutureTask
        FutureTask<String> ft2
                = new FutureTask<>(new T2Task());
// 创建任务T1的FutureTask
        FutureTask<String> ft1
                = new FutureTask<>(new T1Task(ft2));
// 线程T1执行任务ft1
        Thread T1 = new Thread(ft1);
        T1.start();
// 线程T2执行任务ft2
        Thread T2 = new Thread(ft2);
        T2.start();
// 等待线程T1执行结果
        try {
            System.out.println(ft1.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

// T1Task需要执行的任务：
// 洗水壶、烧开水、泡茶

//// 一次执行结果：
//        T1:洗水壶...
//        T2:洗茶壶...
//        T1:烧开水...
//        T2:洗茶杯...
//        T2:拿茶叶...
//        T1:拿到茶叶:龙井
//        T1:泡茶...
//        上茶:龙井
    }

    public static void main(String[] args) {
        BoilingWater bw = new BoilingWater();
        bw.boiling();
    }
}
