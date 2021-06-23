package com.wastedrivinggroup.provider.service;

import com.wastedrivinggroup.exception.ServiceNotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * 服务调用代理,主要处理服务的各类调用情景
 * {@link com.wastedrivinggroup.provider.netty.handle.MethodInvokeHandler}
 *
 * @author chen
 * @date 2021/6/15
 **/
public class ServiceDelegate {

	public static Object invoke(String serviceName, Object[] args) throws InvocationTargetException, IllegalAccessException {
		final ServiceHolder holder = ServiceHolder.getInstance();
		final ServiceHolder.ReflectService service = holder.findService(serviceName);
		if (Objects.isNull(service)) {
			throw new ServiceNotFoundException(serviceName);
		}

		return service.getMethod().invoke(service.getObject(), args);
	}
}
