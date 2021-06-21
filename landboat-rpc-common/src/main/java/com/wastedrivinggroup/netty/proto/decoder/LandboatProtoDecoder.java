package com.wastedrivinggroup.netty.proto.decoder;

import com.wastedrivinggroup.netty.proto.demo.LandboatProto;
import com.wastedrivinggroup.netty.proto.exception.BadProtoFormatException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author chen
 * @date 2021/5/3
 **/
public class LandboatProtoDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 检查请求头
        checkHeader(in);
        // TODO: 增加版本检查

        // 类型获取
        final LandboatProto.ProtoType type = getType(in);
    }

    /**
     * 获取类型
     * 类型为第二个byte的后四位
     *
     * @param buf {@link ByteBuf} 读入数据
     * @return 当前数据类型
     */
    private LandboatProto.ProtoType getType(ByteBuf buf) {
        return LandboatProto.ProtoType.valueOf((byte) (buf.getByte(1) & 0b00001111));
    }

    /**
     * 检查请求头，包含最小长度和魔数
     *
     * @param in {@link ByteBuf} 读入数据
     */
    private void checkHeader(ByteBuf in) {
        if (in.readableBytes() < LandboatProto.HEADER_MINIMUM_LENGTH) {
            throw new BadProtoFormatException(String.format("Readable bytes below minimum:[%s]", LandboatProto.HEADER_MINIMUM_LENGTH));
        }

        if ((in.getByte(0) & 0xF) != LandboatProto.MAGIC) {
            throw new BadProtoFormatException("Unknown magic");
        }
    }
}
