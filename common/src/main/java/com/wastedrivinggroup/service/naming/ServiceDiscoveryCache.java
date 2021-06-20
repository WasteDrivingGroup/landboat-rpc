package com.wastedrivinggroup.service.naming;

import com.wastedrivinggroup.service.pojo.ServiceEndpoint;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务的缓存,暂时用 {@link ConcurrentHashMap} 简单实现
 *
 * @author chen
 * @date 2021/6/19
 **/
@Slf4j
public class ServiceDiscoveryCache {
	private static final Map<String, List<ServiceEndpoint>> cache;

	static {
		cache = new ConcurrentHashMap<>();
	}

	public static List<ServiceEndpoint> getEndpointList(String serviceName) {
		return cache.get(serviceName);
	}

	public static void addEndpoint(String serviceName, List<ServiceEndpoint> endpoints) {
		synchronized (cache) {
			final List<ServiceEndpoint> orDefault = cache.getOrDefault(serviceName, new ArrayList<>());
			orDefault.addAll(endpoints);
			cache.put(serviceName, orDefault);
		}
	}

	public static void addEndpoint(String serviceName, ServiceEndpoint endpoint) {
		synchronized (cache) {
			final List<ServiceEndpoint> orDefault = cache.getOrDefault(serviceName, new ArrayList<>());
			orDefault.add(endpoint);
			cache.put(serviceName, orDefault);
		}
	}
}
