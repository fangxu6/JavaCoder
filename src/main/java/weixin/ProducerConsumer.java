package weixin;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class ProducerConsumer {
    public void producerConsumer() {
        BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>();
        Thread producerThread  = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                blockingQueue.add(i + ThreadLocalRandom.current().nextInt(100));
            }
        });
        Thread consumerThread = new Thread(() -> {
            try {
                while (true) {
                    Integer result = blockingQueue.take();
                    System.out.println(result);
                }
            } catch (InterruptedException ignore) {
            }
        });
        producerThread.start();
        consumerThread.start();
    }

    public static void main(String[] args) {
        ProducerConsumer pc = new ProducerConsumer();
        pc.producerConsumer();
    }
}
