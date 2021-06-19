package com.wastedrivinggroup.service.naming;

import java.util.HashSet;

/**
 * @author chen
 * @date 2021-06-18
 **/
public abstract class AbstractRegisterPolicy implements RegisterPolicy {

	private HashSet<String> registeredService;

	public AbstractRegisterPolicy() {
		registeredService = new HashSet<>();
	}

	@Override
	public boolean isRegistered(String serviceName) {
		return registeredService.contains(serviceName);
	}
}
