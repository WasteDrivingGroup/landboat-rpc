package service;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

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
		@NotNull
		private final Method method;

		@NotNull
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
		final String classFullName = aClass.getName();
		// ！！！declaredMethods 就是当前类中所有实现的方法，不包括从父类继承的方法
		final Method[] declaredMethods = aClass.getDeclaredMethods();
		int cnt = 0;
		for (Method method : declaredMethods) {
			if (shouldSkip(method)) {
				continue;
			}
			cnt++;
			SERVICE_MAP.put(buildServiceName(classFullName, method), new ReflectService(method, object));
		}
		return cnt;
	}

	public boolean shouldSkip(Method method) {
		final String name = method.getName();
		return "hashCode".equals(name) || "equals".equals(name) || "clone".equals(name);
	}

	private String buildServiceName(String classFullName, Method method) {
		assert method != null;
		final Class<?>[] parameterTypes = method.getParameterTypes();
		StringBuilder ans = new StringBuilder();
		ans.append(classFullName)
				.append(":")
				.append(method.getName());
		for (Class<?> clazz : parameterTypes) {
			ans.append(":").append(clazz.getSimpleName());
		}
		return ans.toString();
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
