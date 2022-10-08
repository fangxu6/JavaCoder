package weixin;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public void producerConsumer() {
        new ProducerConsumer<Integer>(Executors.newFixedThreadPool(2)) {
            @Override
            void produce() {
                for (int i = 0; i < 10; i++) {
                    produceInner(i + ThreadLocalRandom.current().nextInt(100));
                }
            }

            @Override
            void consume() {
                while (true) {
                    Integer result = consumeInner();
                    System.out.println(result);
                }
            }
        }.start();
    }
}
