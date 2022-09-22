package weixin;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class NumberProducerConsumer extends ProducerConsumer<Integer> {

    private final Supplier<Integer> numberGenerator;

    private final Consumer<Integer> numberConsumer;

    public NumberProducerConsumer(Executor executor,
                                  Supplier<Integer> numberGenerator,
                                  Consumer<Integer> numberConsumer) {
        super(executor);
        this.numberGenerator = numberGenerator;
        this.numberConsumer = numberConsumer;
    }

    @Override
    void produce() {
        for (int i = 0; i < 10; i++) {
            produceInner(i + numberGenerator.get());
        }
    }

    @Override
    void consume() {
        while (true) {
            Integer result = consumeInner();
            numberConsumer.accept(result);
        }
    }

    public void producerConsumer() {
        new NumberProducerConsumer(Executors.newFixedThreadPool(2),
                () -> ThreadLocalRandom.current().nextInt(100),
                System.out::println).start();
    }

    public static void main(String[] args) {
        NumberProducerConsumer pc = new NumberProducerConsumer(Runnable::run, () -> 0, i -> {
            System.out.println(i);        });
        pc.producerConsumer();
    }
}