package com.wastedrivinggroup.consumer.rpc;

import com.google.gson.Gson;
import com.wastedrivinggroup.netty.ChannelHolder;
import com.wastedrivinggroup.netty.ServiceNameBuilder;
import com.wastedrivinggroup.netty.proto.demo.InvokeReqProto;
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

	Gson gson = new Gson();

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		final String methodName = method.getName();
		if ("toString".equals(methodName) || "hashCode".equals(methodName) || "equals".equals(methodName)) {
			return method.invoke(proxy, args);
		}
		// 封装请求
		final InvokeReqProto req = wrapInvokeReq(method, args);
		// 发送请求
		ChannelHolder.getInstance().getChannel().writeAndFlush(req);
		// 发送请求后在接收请求前应该阻塞当前线程
		String res = ResponseBuffer.getResp(req.getInvokeId());
		return gson.fromJson(res, method.getGenericReturnType());
	}

	private InvokeReqProto wrapInvokeReq(Method method, Object[] args) {
		return new InvokeReqProto()
				.setInvokeId(1L)
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
