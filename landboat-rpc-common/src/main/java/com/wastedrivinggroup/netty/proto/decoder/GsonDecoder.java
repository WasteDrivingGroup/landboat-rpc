package com.wastedrivinggroup.netty.proto.decoder;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * {@link Gson} 解码器
 * <p>
 * {@link ByteBuf} 表示需要解码的类型,在 Pipeline 的 channelRead 事件中如果类型为 {@link ByteBuf} 都会经过该解码器
 *
 * @author chen
 * @date 2021/5/3
 **/
@Slf4j
public class GsonDecoder<E> extends MessageToMessageDecoder<ByteBuf> {

	private final Class<E> targetClass;
	private final Gson gson;

	public GsonDecoder(Class<E> targetClass) {
		gson = new Gson();
		this.targetClass = targetClass;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		byte[] bytes = new byte[msg.readableBytes()];
		msg.readBytes(bytes);
		final E ans = gson.fromJson(new String(bytes), targetClass);
		if (log.isInfoEnabled()) {
			log.info("decode class,class name:{},data:{}", targetClass.getSimpleName(), ans.toString());
		}
		out.add(ans);
	}
}
