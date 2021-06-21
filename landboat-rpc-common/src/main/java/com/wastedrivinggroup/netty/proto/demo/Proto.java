package com.wastedrivinggroup.netty.proto.demo;

import io.netty.buffer.ByteBuf;

import java.io.Serializable;

/**
 * @author chen
 * @date 2021/5/3
 **/
public interface Proto extends Serializable {

    /**
     * 获取请求体长度
     *
     * @return 请求体长度
     */
    int getBodyLength();

    /**
     * 编码
     *
     * @param buf 写入
     */
    void encode(ByteBuf buf);

    /**
     * 解码
     *
     * @param buf 读取
     * @return eg. {@link InvokeReqProto}
     */
    Proto decode(ByteBuf buf);
}
