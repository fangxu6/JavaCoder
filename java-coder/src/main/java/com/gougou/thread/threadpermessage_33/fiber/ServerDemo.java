package com.gougou.thread.threadpermessage_33.fiber;


import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.locks.LockSupport;

/**
 * className:ServerDemo
 * package:thread.threadpermessage_33.fiber
 * Description:
 *
 * @Date:2022/11/2012:18
 * @Author:fangxu6@gmail.com
 */
public class ServerDemo {
    public static void main(String[] args) throws IOException {

        final ServerSocketChannel ssc =
                ServerSocketChannel.open().bind(
                        new InetSocketAddress(8080));
//处理请求
        try{
            while (true) {
                // 接收请求
                final SocketChannel sc =
                        ssc.accept();
                new Thread(()->{    //new Thread =>Fiber.schedule 但是Fiber需要jdk18及以上
                    try {
                        // 读Socket
                        ByteBuffer rb = ByteBuffer
                                .allocateDirect(1024);
                        sc.read(rb);
                        //模拟处理请求
                        LockSupport.parkNanos(2000*1000000);
                        // 写Socket
                        ByteBuffer wb =
                                (ByteBuffer)rb.flip();
                        sc.write(wb);
                        // 关闭Socket
                        sc.close();
                    } catch(Exception e){
//                        throw new UncheckedIOException(e);
                    }
                });
            }//while
        }finally{
            ssc.close();
        }
    }
}
