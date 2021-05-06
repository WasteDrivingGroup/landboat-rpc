package netty.server;

import com.google.common.base.Preconditions;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.RpcBootstrap;
import netty.server.config.ServerConfig;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.NumberUtils;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 服务端的Netty启动类的基本实现
 *
 * @author chen
 * @date 2021/4/16
 **/
public abstract class AbstractNettyServerBootstrap implements RpcBootstrap {

    private EventLoopGroup boosGroup;
    private EventLoopGroup workerGroup;
    private ServerBootstrap serverBootstrap;

    /**
     * 获取相关配置
     *
     * @return {@link ServerConfig}
     */
    protected abstract ServerConfig getConfig();

    /**
     * 提供服务端Channel处理
     */
    protected abstract ChannelInitializer<NioServerSocketChannel> serverChannelInitializer();

    protected abstract ChannelInitializer<NioSocketChannel> channelInitializer();

    private void init() {
        final ServerConfig config = getConfig();
        Preconditions.checkNotNull(config, "ServerConfig is null,check that and restart");

        boosGroup = new NioEventLoopGroup();
        if (NumberUtils.positive(config.getWorkerCnt())) {
            workerGroup = new NioEventLoopGroup(config.getWorkerCnt(), new RpcServerThreadFactory());
        } else {
            workerGroup = new NioEventLoopGroup(new RpcServerThreadFactory());
        }

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
        serverBootstrap.bind(getConfig().getHost(), getConfig().getPort()).sync().addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                System.out.println("rpc server start success.");
            }
        });
    }

    /**
     * RPC 服务端线程
     */
    static final class RpcServerThreadFactory implements ThreadFactory {

        static AtomicInteger threadCnt = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "RPC_SERVER_THREAD_" + threadCnt.incrementAndGet());
        }
    }
}
