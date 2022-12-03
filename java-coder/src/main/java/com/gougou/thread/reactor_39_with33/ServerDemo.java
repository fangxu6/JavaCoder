package com.gougou.thread.reactor_39_with33;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


/**
 * className:EchoServerHandler
 * package:thread.reactor_39_with33
 * Description:
 *
 * @Date:2022/11/2822:01
 * @Author:fangxu6@gmail.com
 */

//事件处理器
public class ServerDemo {
    public static void main(String[] args) {


        final EchoServerHandler serverHandler
                = new EchoServerHandler();
        //boss线程组
        EventLoopGroup bossGroup
                = new NioEventLoopGroup(1);
        //worker线程组
        EventLoopGroup workerGroup
                = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(serverHandler);
                        }
                    });
            //bind服务端端口
            ChannelFuture f = b.bind(9090).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //终止工作线程组
            workerGroup.shutdownGracefully();
            //终止boss线程组
            bossGroup.shutdownGracefully();
        }
    }


}