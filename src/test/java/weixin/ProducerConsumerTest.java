package weixin;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.junit.Assert.assertTrue;

/**
 * className:ProducerConsumer
 * package:weixin
 * Description:
 *
 * @Date:2022/9/1923:42
 * @Author:fangxu6@gmail.com
 */
public class ProducerConsumerTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);

    }

    @Test
    public void testG() {
        ProducerConsumerFunction pc = new ProducerConsumerFunction();
        Supplier<Integer> result = pc.g(() -> 1);
        assert result.get() == 2;
    }

    @Test
    public void testProducerConsumerInner() {
        ProducerConsumerFunction pc = new ProducerConsumerFunction();
        pc.producerConsumerInner(Runnable::run,
                (Consumer<Consumer<Integer>>) producer -> {
                    producer.accept(1);
                    producer.accept(2);
                },
                consumer -> {
                    assert consumer.get() == 1;
                    assert consumer.get() == 2;
                });
    }

    @Test
    public void testProducerConsumerAbCls() {
//        ProducerConsumer1 pc = new ProducerConsumer1();
        new ProducerConsumer<Integer>(Runnable::run) {
            @Override
            void produce() {
                produceInner(1);
                produceInner(2);
            }

            @Override
            void consume() {
                assert consumeInner() == 1;
                assert consumeInner() == 2;
            }
        }.start();
    }

    @Test
    public void testProducerConsumerInner2() {
        AtomicInteger expectI = new AtomicInteger();
//        ProducerConsumer pc = new NumberProducerConsumer
        new NumberProducerConsumer(Runnable::run, () -> 0, i -> {
            assert i == expectI.getAndIncrement();
        }).start();
        assert expectI.get() == 10;
    }
}
