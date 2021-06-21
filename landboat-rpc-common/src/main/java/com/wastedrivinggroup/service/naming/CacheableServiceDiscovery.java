package com.wastedrivinggroup.service.naming;

import com.wastedrivinggroup.service.pojo.ServiceEndpoint;

import java.util.List;

/**
 * @author chen
 * @date 2021/6/19
 **/
public abstract class CacheableServiceDiscovery implements ServiceDiscovery {
	@Override
	public List<ServiceEndpoint> discovery(String serviceName) {
		final List<ServiceEndpoint> endpoints = ServiceDiscoveryCache.getEndpointList(serviceName);
		if (!endpoints.isEmpty()) {
			return endpoints;
		}
		final List<ServiceEndpoint> ans = doDiscovery(serviceName);
		ServiceDiscoveryCache.addEndpoint(serviceName, ans);
		return ServiceDiscoveryCache.getEndpointList(serviceName);
	}

	/**
	 * 子类实现
	 *
	 * @param serviceName 服务名称
	 * @return 服务列表
	 */
	protected abstract List<ServiceEndpoint> doDiscovery(String serviceName);
}
