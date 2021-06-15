package netty.proto.decoder;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import netty.proto.demo.InvokeProto;

import java.util.List;

/**
 * {@link Gson} 解码器
 * <p>
 * {@link ByteBuf} 表示需要解码的类型,在 Pipeline 的 channelRead 事件中如果类型为 {@link ByteBuf} 都会经过该解码器
 *
 * @author chen
 * @date 2021/5/3
 **/
public class GsonDecoder extends MessageToMessageDecoder<ByteBuf> {

	private final Gson gson = new Gson();

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		byte[] bytes = new byte[msg.readableBytes()];
		msg.readBytes(bytes);
		out.add(gson.fromJson(new String(bytes), InvokeProto.class));
	}
}
