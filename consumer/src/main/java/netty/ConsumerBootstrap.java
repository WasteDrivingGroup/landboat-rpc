package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import netty.client.AbstractNettyClientBootstrap;
import netty.handler.DebugLogHandler;
import netty.proto.demo.InvokeReqProto;
import netty.proto.decoder.GsonDecoder;
import netty.proto.encoder.GsonEncoder;

import java.util.Scanner;

/**
 * @author chen
 * @date 2021/5/2
 **/
public class ConsumerBootstrap extends AbstractNettyClientBootstrap {

	private Channel channel;

	@Override
	public void start() throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();

		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(group)
				.channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 2, 0, 2));
						// LengthFieldPrepender是一个编码器，主要是在响应字节数据前面添加字节长度字段
						ch.pipeline().addLast(new LengthFieldPrepender(2));
						// 对经过粘包和拆包处理之后的数据进行json反序列化，从而得到User对象
						ch.pipeline().addLast(new GsonDecoder());
						ch.pipeline().addLast(new GsonEncoder());
						// 对响应数据进行编码，主要是将User对象序列化为json
						ch.pipeline().addLast(new DebugLogHandler());
					}
				});
		final ChannelFuture sync = bootstrap.connect("localhost", 8888).addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if (!future.isSuccess()) {
					System.out.println("连接服务端失败");
					return;
				}
				channel = future.channel();
			}
		}).sync();

		final Scanner scanner = new Scanner(System.in);
		while (scanner.hasNext()) {
			final String s = scanner.nextLine();
			channel.writeAndFlush(new InvokeReqProto().setServiceName(s));
		}


	}
}
