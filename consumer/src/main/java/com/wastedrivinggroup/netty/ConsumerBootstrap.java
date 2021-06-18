package com.wastedrivinggroup.netty;

import com.wastedrivinggroup.netty.client.AbstractNettyClientBootstrap;
import com.wastedrivinggroup.netty.handler.DebugLogHandler;
import com.wastedrivinggroup.netty.handler.ResponseReceiveHandler;
import com.wastedrivinggroup.netty.proto.decoder.GsonDecoder;
import com.wastedrivinggroup.netty.proto.demo.InvokeRespProto;
import com.wastedrivinggroup.netty.proto.encoder.GsonEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chen
 * @date 2021/5/2
 **/
@Slf4j
public class ConsumerBootstrap extends AbstractNettyClientBootstrap {

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
						ch.pipeline().addLast(new GsonDecoder<>(InvokeRespProto.class));
						ch.pipeline().addLast(new GsonEncoder());
						ch.pipeline().addLast(new ResponseReceiveHandler());
						// 对响应数据进行编码，主要是将User对象序列化为json
						ch.pipeline().addLast(new DebugLogHandler());
					}
				});
		ChannelHolder.getInstance().setChannel(bootstrap.connect("localhost", 8888).sync().channel());
	}
}
