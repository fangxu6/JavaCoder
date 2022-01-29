package singletonmock;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
    private AtomicLong id = new AtomicLong(0);
//    private IdGenerator instance = new IdGenerator();
    private static final IdGenerator instance = new IdGenerator();//饿汉式

    private IdGenerator(){};

    public static IdGenerator getInstance() {
        return instance;
    }

    public long getId(){
        return id.incrementAndGet();
    }
}
