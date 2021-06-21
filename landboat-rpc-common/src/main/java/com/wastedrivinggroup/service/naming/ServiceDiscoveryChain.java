package com.wastedrivinggroup.service.naming;

import com.wastedrivinggroup.service.pojo.ServiceEndpoint;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chen
 * @date 2021/6/20
 **/
public class ServiceDiscoveryChain implements ServiceDiscovery {

	private static final List<CacheableServiceDiscovery> discoveries;

	static {
		discoveries = new ArrayList<>();
	}


	@Override
	public List<ServiceEndpoint> discovery(String serviceName) {
		return null;
	}
}
