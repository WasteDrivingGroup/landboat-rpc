package com.wastedrivinggroup.consumer.rpc;

import com.wastedrivinggroup.consumer.netty.ChannelHolder;
import com.wastedrivinggroup.consumer.netty.ServiceNameBuilder;
import com.wastedrivinggroup.consumer.netty.proto.demo.InvokeReqProto;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author chen
 * @date 2021/6/16
 **/
@Slf4j
public class InvokeProxyHandler implements InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		final String methodName = method.getName();
		if (methodName.equals("toString")) {
			return new Object();
		}
		ChannelHolder.getInstance().getChannel().writeAndFlush(wrapInvokeReq(method, args));
		return 1;
	}

	private InvokeReqProto wrapInvokeReq(Method method, Object[] args) {
		return new InvokeReqProto()
				.setServiceName(ServiceNameBuilder.buildServiceName(method))
				.setArgs(args);

	}

	public static <T> T createProxy(Class<T> interfaces) {
		// 直接使用线程上下文类加载器
		return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{interfaces}, new InvokeProxyHandler());
	}

}
