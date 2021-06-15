package service;

import netty.exception.ServiceNotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * @author chen
 * @date 2021/6/15
 **/
public class ServiceDelegate {

	public static Object invoke(String serviceName, String[] args) throws InvocationTargetException, IllegalAccessException {
		final ServiceHolder holder = ServiceHolder.getInstance();
		final ServiceHolder.ReflectService service = holder.findService(serviceName);
		if (Objects.isNull(service)) {
			throw new ServiceNotFoundException(serviceName);
		}

		return service.getMethod().invoke(service.getObject(), args);
	}
}
