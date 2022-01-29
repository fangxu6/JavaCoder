package singletonlock;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Logger1 {
    private FileWriter writer;

    public Logger1() throws IOException {
        File file = new File("/Users/wangzheng/log.txt");
        writer = new FileWriter(file, true); //true表示追加写入
    }

    public void log(String message) throws IOException {
        synchronized(Logger.class) { // 类级别的锁
            writer.write(message);
        }
    }
}