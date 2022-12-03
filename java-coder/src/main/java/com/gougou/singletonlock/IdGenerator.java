package com.gougou.singletonlock;


import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
    private AtomicLong id = new AtomicLong(0);
    private static final IdGenerator instance = new IdGenerator();//饿汉式
    private IdGenerator() {}
    public static IdGenerator getInstance() {
        return instance;
    }

    public static String generateTransactionId() {
        return 1 + "";
    }

    public long getId() {
        return id.incrementAndGet();
    }
}