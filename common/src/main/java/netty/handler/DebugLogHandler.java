package netty.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chen
 * @date 2021/5/2
 **/
@Slf4j
public class DebugLogHandler extends ChannelDuplexHandler {

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel inactive");
        super.channelInactive(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("new channel connect");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.printf("rpc netty read data:[msg:{%s}] \n", msg.toString());
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("rpc netty read complete");
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.printf("rpc netty throw new exception,[detail:{%s}] \n", cause.toString());
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        System.out.println("rpc read something");
        super.read(ctx);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("rpc write something");
        super.write(ctx, msg, promise);
    }
}
