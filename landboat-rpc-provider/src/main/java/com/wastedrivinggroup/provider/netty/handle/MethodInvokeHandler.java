package com.wastedrivinggroup.provider.netty.handle;

import com.google.gson.Gson;
import com.wastedrivinggroup.netty.proto.demo.InvokeReqProto;
import com.wastedrivinggroup.netty.proto.demo.InvokeRespProto;
import com.wastedrivinggroup.provider.service.ServiceDelegate;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * 服务调用的处理类
 *
 * @author chen
 * @date 2021/6/16
 **/
@Slf4j
public class MethodInvokeHandler extends SimpleChannelInboundHandler<InvokeReqProto> {
	Gson gson = new Gson();

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, InvokeReqProto msg) throws Exception {
		if (log.isInfoEnabled()) {
			log.info("receive an invoke request,invoke Id:{},serviceName:{},args:{}", msg.getInvokeId(), msg.getServiceName(), Arrays.toString(msg.getArgs()));
		}
		// 实际调用得到结果
		final Object invoke = ServiceDelegate.invoke(msg.getServiceName(), msg.getArgs());
		// 包装结果
		ctx.writeAndFlush(new InvokeRespProto().setInvokeId(msg.getInvokeId()).setRet(gson.toJson(invoke)));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (log.isWarnEnabled()) {
			log.warn("捕获到未处理异常,throws:{}", cause.getMessage());
		}
		super.exceptionCaught(ctx, cause);
	}
}
