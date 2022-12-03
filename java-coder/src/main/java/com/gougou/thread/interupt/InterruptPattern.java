package com.gougou.thread.interupt;

/**
 * @author fangxu
 * on date:2022/10/12
 */
public class InterruptPattern {
    public static void main(String[] args) {
        while (!Thread.currentThread().isInterrupted())
//        while (!Thread.currentThread().islnterrupted() && more work to do)
        {
            //do more work
        }

//        没有任何语言方面的需求要求一个被中断的线程应该终止。中断一个线程不过是引起它
//        的注意。被中断的线程可以决定如何响应中断。某些线程是如此重要以至于应该处理完异常
//        后， 继续执行， 而不理会中断。但是，更普遍的情况是，线程将简单地将中断作为一个终止
//        的请求。这种线程的run 方法具有如下形式：

        Runnable r = () -> {
            try {
                while (!Thread.currentThread().isInterrupted() && true )
                //while (!Thread.currentThread().isInterrupted0 && more work to do)
                {
                    //do more work
                }
//            } catch (InterruptedException e) {
            } catch (Exception e) {
// thread was interr叩ted during sleep or wait
            } finally {
                //cleanup, if required
            }
//      exiting the run method terminates the thread
        };
    }
}
