package thread.loopquestion;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author fangxu
 * on date:2022/10/9
 */
public class LoopQuestion {
    public static void main(String[] args) {
        // 是否还有包子
        AtomicBoolean hasBun = new AtomicBoolean();

        // 包子铺老板
        new Thread(() -> {
            try {
                // 一直循环查看是否还有包子
                while (true) {
                    if (hasBun.get()) {
                        System.out.println("老板：检查一下是否还剩下包子...");
                        Thread.sleep(3000);
                    } else {
                        System.out.println("老板：没有包子了, 马上开始制作...");
                        Thread.sleep(1000);
                        System.out.println("老板：包子出锅咯....");
                        hasBun.set(true);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


        new Thread(() -> {
            System.out.println("小强：我要买包子...");
            try {
                // 每隔一段时间询问是否完成
                while (!hasBun.get()) {
                    System.out.println("小强：包子咋还没做好呢~");
                    Thread.sleep(3000);
                }
                System.out.println("小强：终于吃上包子了....");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
