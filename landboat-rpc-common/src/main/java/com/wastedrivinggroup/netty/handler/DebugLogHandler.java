package com.wastedrivinggroup.netty.handler;

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
		if (log.isDebugEnabled()) {
			log.debug("channel inactive");
		}
		super.channelInactive(ctx);
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("new channel connect");
		}
		super.channelRegistered(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("rpc com.wastedrivinggroup.consumer.netty read data:[msg:{}] \n", msg.toString());
		}
		super.channelRead(ctx, msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("rpc com.wastedrivinggroup.consumer.netty read complete");
		}
		super.channelReadComplete(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("rpc com.wastedrivinggroup.consumer.netty throw new exception,[detail:{}] \n", cause.toString());
		}
		super.exceptionCaught(ctx, cause);
	}

	@Override
	public void read(ChannelHandlerContext ctx) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("rpc read something");
		}
		super.read(ctx);
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("rpc write something");
		}
		super.write(ctx, msg, promise);
	}
}
