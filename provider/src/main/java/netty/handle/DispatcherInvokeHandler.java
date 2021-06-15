package netty.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.proto.demo.InvokeReqProto;
import service.ServiceDelegate;

/**
 * @author chen
 * @date 2021/6/16
 **/
public class DispatcherInvokeHandler extends SimpleChannelInboundHandler<InvokeReqProto> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, InvokeReqProto msg) throws Exception {
		ServiceDelegate.invoke(msg.getServiceName(), msg.getArgs());
	}
}
