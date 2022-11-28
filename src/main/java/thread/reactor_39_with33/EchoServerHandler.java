package thread.reactor_39_with33;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * className:EchoServerHandler
 * package:thread.reactor_39_with33
 * Description:
 *
 * @Date:2022/11/2822:03
 * @Author:fangxu6@gmail.com
 */
//socket连接处理器
class EchoServerHandler extends
        ChannelInboundHandlerAdapter {
    //处理读事件
    @Override
    public void channelRead(
            ChannelHandlerContext ctx, Object msg) {
        ctx.write(msg);
    }

    //处理读完成事件
    @Override
    public void channelReadComplete(
            ChannelHandlerContext ctx) {
        ctx.flush();
    }

    //处理异常事件
    @Override
    public void exceptionCaught(
            ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
