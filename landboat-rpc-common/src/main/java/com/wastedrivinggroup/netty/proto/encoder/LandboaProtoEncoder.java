package com.wastedrivinggroup.netty.proto.encoder;

import com.wastedrivinggroup.netty.proto.demo.LandboatProto;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * TODO:
 * 自定义解码器
 *
 * @author chen
 * @date 2021/5/3
 **/
public class LandboaProtoEncoder extends MessageToByteEncoder<LandboatProto> {

	@Override
	protected void encode(ChannelHandlerContext ctx, LandboatProto msg, ByteBuf out) throws Exception {
		msg.encode(out);
	}
}
