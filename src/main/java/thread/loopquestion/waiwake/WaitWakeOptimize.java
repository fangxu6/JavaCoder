package thread.loopquestion.waiwake;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author fangxu
 * on date:2022/10/9
 */
public class WaitWakeOptimize {
    public static void main(String[] args) {
        // 是否还有包子
        AtomicBoolean hasBun = new AtomicBoolean();
        // 锁对象
        Object lockObject = new Object();

        // 包子铺老板
        new Thread(() -> {
            try {
                while (true) {
                    synchronized (lockObject) {
                        if (hasBun.get()) {
                            System.out.println("老板：包子够卖了，打一把王者荣耀");
                            lockObject.wait();
                        } else {
                            System.out.println("老板：没有包子了, 马上开始制作...");
                            Thread.sleep(3000);
                            System.out.println("老板：包子出锅咯....");
                            hasBun.set(true);
                            // 通知等待的食客
                            lockObject.notifyAll();
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


        new Thread(() -> {
            System.out.println("小强：我要买包子...");
            try {
                synchronized (lockObject) {
                    if (!hasBun.get()) {
                        System.out.println("小强：看一下有没有做好， 看公众号cruder有没有新文章");
                        lockObject.wait();
                    } else {
                        System.out.println("小强：包子终于做好了，我要吃光它们....");
                        hasBun.set(false);
                        lockObject.notifyAll();
                        System.out.println("小强：一口气把店里包子吃光了， 快快乐乐去板砖了~~");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
