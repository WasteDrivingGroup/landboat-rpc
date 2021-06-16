package com.wastedrivinggroup.consumer.netty.proto.encoder;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.MessageToMessageEncoder;
import com.wastedrivinggroup.consumer.netty.proto.demo.InvokeReqProto;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * {@link Gson} 的编码器
 * <p>
 * 由 {@link MessageToMessageEncoder#write(ChannelHandlerContext, Object, ChannelPromise)} } 之类的 writed 方法触发
 *
 * @author chen
 * @date 2021/5/3
 **/
public class GsonEncoder extends MessageToMessageEncoder<Object> {

	private final Gson gson = new Gson();

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
		// JSON 序列化
		final String content = gson.toJson(msg);
		// 获取内存
		final ByteBuf byteBuf = ctx.alloc().directBuffer();
		// 写入
		byteBuf.writeBytes(content.getBytes(StandardCharsets.UTF_8));
		out.add(byteBuf);
	}
}
