package weixin;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Supplier;

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

    public int f() {
        return ThreadLocalRandom.current().nextInt(100) + 1;
    }

    //update 1. 随机数解决方法
    public Supplier<Integer> g(Supplier<Integer> integerSupplier) {
        return () -> integerSupplier.get() + 1;
    }
    //实际业务中可以稍微简化一下高阶函数的表达， g 的返回的函数既然每次都会被立即执行，
    // 那我们就不返回函数了，直接将逻辑写在方法中，这样也是可测试的：
    public int g2(Supplier<Integer> integerSupplier) {
        return integerSupplier.get() + 1;
    }

    //update 2. 职责重构
    public <T> void  producerConsumerInner(Consumer<Consumer<T>> producer,
                                           Consumer<Supplier<T>> consumer) {
        BlockingQueue<T> blockingQueue = new LinkedBlockingQueue<>();
        new Thread(() -> producer.accept(blockingQueue::add)).start();
        new Thread(() -> consumer.accept(() -> {
            try {
                return blockingQueue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        })).start();
    }

    //updat2 2.1 异步线程
    public <T> void  producerConsumerInner(Executor executor,
                                           Consumer<Consumer<T>> producer,
                                           Consumer<Supplier<T>> consumer) {
        BlockingQueue<T> blockingQueue = new LinkedBlockingQueue<>();
        executor.execute(() -> producer.accept(blockingQueue::add));
        executor.execute(() -> consumer.accept(() -> {
            try {
                return blockingQueue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    public static void main(String[] args) {
        ProducerConsumer pc = new ProducerConsumer();
//        pc.producerConsumer();
        System.out.println(pc.f());

        Supplier<Integer> supplier =()->1;
        System.out.println(supplier.get());
        Supplier<Integer> supplier1 = pc.g(supplier);
        System.out.println(supplier1.get());
    }


}
