package netty.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import netty.proto.demo.InvokeProto;

/**
 * @author chen
 * @date 2021/5/2
 **/
@Slf4j
public class InvokeHandle extends SimpleChannelInboundHandler<InvokeProto> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, InvokeProto msg) throws Exception {
		final String serviceName = msg.getServiceName();
	}
}
