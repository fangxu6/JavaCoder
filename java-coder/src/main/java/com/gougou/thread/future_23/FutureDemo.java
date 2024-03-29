package com.gougou.thread.future_23;

import javax.xml.transform.Result;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureDemo {

    // 提交Runnable任务及结果引用 Future submit(Runnable task, T result);
    public void FutureDemoMethod() {

        ExecutorService executor
                = Executors.newFixedThreadPool(1);
        // 创建Result对象r
//        Result r = new Result();
//        r.setAAA(a);
        // 提交任务
//        Future<Result> future =
//                executor.submit(new Task(r), r);
//        Result fr = future.get();
        // 下面等式成立
//        fr == = r;
//        fr.getAAA() == = a;
//        fr.getXXX() == = x;
    }

    class Task implements Runnable {
        Result r;

        //通过构造函数传入result
        Task(Result r) {
            this.r = r;
        }

        public void run() {
            //可以操作result
//            a = r.getAAA();
//            r.setXXX(x);
        }
    }
}
