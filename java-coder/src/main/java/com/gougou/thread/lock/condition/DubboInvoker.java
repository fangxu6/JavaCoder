package com.gougou.thread.lock.condition;

/**
 * @author fangxu
 * on date:2022/10/16
 */
public class DubboInvoker {

//    //以下类只是示例，有很多问题
//
//    public class DubboInvoker{
//        Result doInvoke(Invocation inv){
//            // 下面这行就是源码中108行
//            // 为了便于展示，做了修改
//            return currentClient
//                    .request(inv, timeout)
//                    .get();
//        }
//    }
//
//    //Dubbo RPC异步转通步原理实现
//
//    // 创建锁与条件变量
//    private final Lock lock
//            = new ReentrantLock();
//    private final Condition done
//            = lock.newCondition();
//
//    // 调用方通过该方法等待结果
//    Object get(int timeout){
//        long start = System.nanoTime();
//        lock.lock();
//        try {
//            while (!isDone()) {
//                done.await(timeout);
//                long cur=System.nanoTime();
//                if (isDone() ||
//                        cur-start > timeout){
//                    break;
//                }
//            }
//        } finally {
//            lock.unlock();
//        }
//        if (!isDone()) {
//            throw new TimeoutException();
//        }
//        return returnFromResponse();
//    }
//    // RPC结果是否已经返回
//    boolean isDone() {
//        return response != null;
//    }
//    // RPC结果返回时调用该方法
//    private void doReceived(Response res) {
//        lock.lock();
//        try {
//            response = res;
//            if (done != null) {
//                done.signal();
//            }
//        } finally {
//            lock.unlock();
//        }
//    }
}
