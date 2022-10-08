package weixin;

import java.util.concurrent.*;

/**
 * className:ProducerConsumer1
 * package:weixin
 * Description:
 *
 * @Date:2022/9/200:00
 * @Author:fangxu6@gmail.com
 */
public abstract class ProducerConsumer<T> {
    private final Executor executor;

    private final BlockingQueue<T> blockingQueue;

    public ProducerConsumer(Executor executor) {
        this.executor = executor;
        this.blockingQueue = new LinkedBlockingQueue<>();
    }

    public void start() {
        executor.execute(this::produce);
        executor.execute(this::consume);
    }

    abstract void produce();

    abstract void consume();

    protected void produceInner(T item) {
        blockingQueue.add(item);
    }

    protected T consumeInner() {
        try {
            return blockingQueue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

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

    public static void main(String[] args) {

        ProducerConsumer<Integer> integerProducerConsumer;
        integerProducerConsumer = new ProducerConsumer<Integer>(Executors.newFixedThreadPool(2)) {
            @Override
            void produce() {

            }

            @Override
            void consume() {

            }
        };
    }
}
