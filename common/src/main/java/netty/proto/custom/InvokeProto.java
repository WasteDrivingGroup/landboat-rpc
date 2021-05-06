package netty.proto.custom;

import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

/**
 * 调用命令的
 *
 * @author chen
 * @date 2021/5/3
 **/
public class InvokeProto extends LandboatProto {

    /**
     * 调用id，异步调用时确定 {@link java.util.concurrent.Future}
     */
    private String invokeId;

    private String method;

    @Override
    public int getBodyLength() {
        return invokeId.getBytes(StandardCharsets.UTF_8).length + method.getBytes(StandardCharsets.UTF_8).length;
    }

    @Override
    public void encode(ByteBuf buf) {

    }

    @Override
    public InvokeProto decode(ByteBuf buf) {
        return null;
    }
}
