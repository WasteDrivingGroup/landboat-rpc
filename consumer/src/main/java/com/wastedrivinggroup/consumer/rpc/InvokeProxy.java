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
public class InvokeProxy implements InvocationHandler {

	private static final InvokeProxy INSTANCE = new InvokeProxy();

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		final String methodName = method.getName();
		//
		if ("toString".equals(methodName) || "hashCode".equals(methodName) || "equals".equals(methodName)) {
			return method.invoke(proxy, args);
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
		return createProxy(Thread.currentThread().getContextClassLoader(), interfaces);
	}

	@SuppressWarnings("unchecked")
	public static <T> T createProxy(ClassLoader classLoader, Class<T> interfaces) {
		if (!interfaces.isInterface()) {
			throw new IllegalArgumentException("Can only be applied to interface");
		}
		// 直接使用线程上下文类加载器
		return (T) Proxy.newProxyInstance(classLoader, new Class[]{interfaces}, INSTANCE);
	}

}
