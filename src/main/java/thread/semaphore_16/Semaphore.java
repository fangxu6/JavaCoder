package thread.semaphore_16;

import java.util.Queue;

/**
 * @author fangxu
 * on date:2022/10/18
 */

class Semaphore{
    // 计数器
    int count;
    // 等待队列
    Queue queue;
    // 初始化操作
    Semaphore(int c){
        this.count=c;
    }
    //
    void down(){
        // acquire()
        //down()：计数器的值减 1；如果此时计数器的值小于 0，则当前线程将被阻塞，否则当前线程可以继续执行。 <0阻塞 >=0继续运行
        this.count--;
        if(this.count<0){
            //将当前线程插入等待队列
            //阻塞当前线程
                //semWait
        }
    }
    void up(){
        //release()
        //up()：计数器的值加 1；如果此时计数器的值小于或者等于 0，则唤醒等待队列中的一个线程，并将其从等待队列中移除  <=0
        this.count++;
        if(this.count<=0) {
            //移除等待队列中的某个线程T
            //唤醒线程T
                //semSignal
        }
    }
}
