package com.wastedrivinggroup.provider.service;

import com.wastedrivinggroup.consumer.netty.ServiceNameBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chen
 * @date 2021/6/15
 **/
@Slf4j
public class ServiceHolder {

	@Getter
	@AllArgsConstructor
	static final class ReflectService {
		private final Method method;

		private final Object object;
	}

	/**
	 * ServiceName --> Method
	 * ServiceName 为 ClassName : MethodName : argsTypeName[]
	 * 服务名称
	 */
	private static final ConcurrentHashMap<String, ReflectService> SERVICE_MAP;

	private static final HashSet<Class<?>> loadedClass;

	static {
		if (log.isInfoEnabled()) {
			log.info("initialize ServiceHolder success");
		}
		SERVICE_MAP = new ConcurrentHashMap<>();
		loadedClass = new HashSet<>();
	}

	public static ServiceHolder getInstance() {
		return RealServiceHolder.getInstance();
	}

	public ReflectService findService(String serviceName) {
		return SERVICE_MAP.get(serviceName);
	}

	/**
	 * 加载服务
	 *
	 * @return 加载的服务数
	 */
	public int loadService(Object object) {
		final Class<?> aClass = object.getClass();
		final String classFullName = aClass.getSimpleName();
		// ！！！declaredMethods 就是当前类中所有实现的方法，不包括从父类继承的方法
		final Method[] declaredMethods = aClass.getDeclaredMethods();
		int cnt = 0;
		for (Method method : declaredMethods) {
			if (shouldSkip(method)) {
				continue;
			}
			cnt++;
			final String serviceName = ServiceNameBuilder.buildServiceName(classFullName, method);
			final ReflectService targetService = new ReflectService(method, object);
			final ReflectService oldService = SERVICE_MAP.putIfAbsent(serviceName, targetService);
			if (oldService != null) {
				if (log.isWarnEnabled()) {
					log.warn("repeat load same service ,service name:{}", serviceName);
				}
			} else {
				if (log.isInfoEnabled()) {
					log.info("load new service,service name:{}", serviceName);
				}
			}
		}
		return cnt;
	}

	public int loadService(Class<?> clazz) {
		try {
			final Object obj = clazz.getConstructor().newInstance();
			return loadService(obj);
		} catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
			throw new RuntimeException("服务注册失败");
		}
	}

	public boolean shouldSkip(Method method) {
		final String name = method.getName();
		return "hashCode".equals(name) || "equals".equals(name) || "clone".equals(name);
	}


	/**
	 * 静态内部类实现单例
	 */
	private static final class RealServiceHolder {
		private static final ServiceHolder INSTANCE = new ServiceHolder();

		public static ServiceHolder getInstance() {
			return INSTANCE;
		}
	}

}
