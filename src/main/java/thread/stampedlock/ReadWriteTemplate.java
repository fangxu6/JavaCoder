package thread.stampedlock;

import java.util.concurrent.locks.StampedLock;

/**
 * className:ReadTemplate
 * package:thread.stampedlock
 * Description:
 *
 * @Date:2022/10/1921:39
 * @Author:fangxu6@gmail.com
 */
public class ReadWriteTemplate {
    //StampedLock读模板
    void readTemplate() {
        final StampedLock sl = new StampedLock();


        // 乐观读
        long stamp = sl.tryOptimisticRead();
        // 读入方法局部变量
        //......
        // 校验stamp
        if (!sl.validate(stamp)) {
            // 升级为悲观读锁
            stamp = sl.readLock();
            try {
                // 读入方法局部变量
                //.....
            } finally {
                //释放悲观读锁
                sl.unlockRead(stamp);
            }
        }
        //使用方法局部变量执行业务操作
        //......
    }

    //写模板
    void writeTemplate() {
        StampedLock sl = new StampedLock();

        long stamp = sl.writeLock();
        try {
            // 写共享变量
            //  ......
        } finally {
            sl.unlockWrite(stamp);
        }
    }
}
