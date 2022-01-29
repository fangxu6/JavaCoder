package singletonlock;


import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator2 {
    private AtomicLong id = new AtomicLong(0);
    private static IdGenerator2 instance;
    private IdGenerator2() {}
    public static synchronized IdGenerator2 getInstance() {
        if (instance == null) {//懒汉式
            instance = new IdGenerator2();
        }
        return instance;
    }
    public long getId() {
        return id.incrementAndGet();
    }
}
