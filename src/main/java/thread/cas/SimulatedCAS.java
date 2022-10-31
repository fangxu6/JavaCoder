package thread.cas;

import java.util.concurrent.atomic.AtomicLong;

/**
 * className:SimulatedCAS
 * package:thread.cas
 * Description:
 *
 * @Date:2022/10/2523:43
 * @Author:fangxu6@gmail.com
 */

class SimulatedCAS{
    volatile int count;
    // 实现count+=1
    void addOne(){
        int newValue;
        do {
            newValue = count+1; //①
        }while(count !=
                cas(count,newValue)); //②
    }
    // 模拟实现CAS，仅用来帮助理解
    synchronized int cas(
            int expect, int newValue){
        // 读目前count的值
        int curValue = count;
        // 比较目前count值是否==期望值
        if(curValue == expect){
            // 如果是，则更新count的值
            count= newValue;
        }
        // 返回写入前的值
        return curValue;
    }

    void test(){
        AtomicLong atomicLong = new AtomicLong();
    }
}