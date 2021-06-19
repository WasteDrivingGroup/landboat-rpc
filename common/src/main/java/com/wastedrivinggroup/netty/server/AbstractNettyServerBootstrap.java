package com.wastedrivinggroup.netty.server;

import com.wastedrivinggroup.netty.RpcBootstrap;
import com.wastedrivinggroup.netty.server.config.NettyServerConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 服务端的Netty启动类的基本实现
 *
 * @author chen
 * @date 2021/4/16
 **/
@Slf4j
public abstract class AbstractNettyServerBootstrap implements RpcBootstrap {

	private EventLoopGroup boosGroup;
	private EventLoopGroup workerGroup;
	private ServerBootstrap serverBootstrap;

	/**
	 * 提供服务端Channel处理
	 */
	protected abstract ChannelInitializer<NioServerSocketChannel> serverChannelInitializer();

	protected abstract ChannelInitializer<NioSocketChannel> channelInitializer();

	private void init() {
		NettyServerConfig.checkConfig();
		boosGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup(new RpcServerThreadFactory());
		serverBootstrap = new ServerBootstrap();
		// TODO: 配置中可以放更多的 Option
		serverBootstrap.group(boosGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 1024)
				.handler(serverChannelInitializer())
				.childOption(ChannelOption.SO_KEEPALIVE, true)
				.childOption(ChannelOption.TCP_NODELAY, true)
				.childHandler(channelInitializer());
	}

	@Override
	public void start() throws InterruptedException {
		init();
		serverBootstrap.bind(NettyServerConfig.getHost(), NettyServerConfig.getPort()).sync().addListener((ChannelFutureListener) future -> {
			if (future.isSuccess()) {
				if (log.isInfoEnabled()) {
					log.info("rpc server start success.");
				}
			}
		});
	}

	/**
	 * RPC 服务端线程
	 */
	static final class RpcServerThreadFactory implements ThreadFactory {

		static AtomicInteger threadCnt = new AtomicInteger();

		@Override
		public Thread newThread(Runnable runnable) {
			return new Thread(runnable, "RPC_SERVER_THREAD_" + threadCnt.incrementAndGet());
		}
	}
}
