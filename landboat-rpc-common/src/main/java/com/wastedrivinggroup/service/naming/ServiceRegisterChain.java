package com.wastedrivinggroup.service.naming;

import com.wastedrivinggroup.service.naming.consul.ConsulServiceRegister;

import java.util.ArrayList;
import java.util.List;

/**
 * 将已有的{@link RegisterPolicy}组成一个链，顺序调用，用来支持向多个注册中心注册
 * <p>
 * 可以在构造函数中通过{@link java.util.ServiceLoader}的SPI机制，完成自定义的注册
 * <p>
 * Haha 过度优化一笔
 *
 * @author chen
 * @date 2021/6/19
 **/
public class ServiceRegisterChain implements RegisterPolicy {
	private static final ServiceRegisterChain INSTANCE = new ServiceRegisterChain();
	private static List<RegisterPolicy> policies;

	static {
		policies = new ArrayList<>();
		policies.add(new ConsulServiceRegister());
	}

	@Override
	public void registered(String serviceName) {
		for (RegisterPolicy node : policies) {
			if (node.isValid()) {
				node.registered(serviceName);
			}
		}

	}

	@Override
	public void disRegistered(String serviceName) {
		for (RegisterPolicy node : policies) {
			if (node.isRegistered(serviceName)) {
				node.disRegistered(serviceName);
			}
		}
	}

	// single instance

	public static ServiceRegisterChain getInstance() {
		return INSTANCE;
	}

}
