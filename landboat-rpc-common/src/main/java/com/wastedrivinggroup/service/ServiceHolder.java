package com.wastedrivinggroup.service;

import com.wastedrivinggroup.service.pojo.ServiceEndpoint;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务的持有者，单例形式
 * <p>
 * 客户端或者服务端本地做缓存
 *
 * @author chen
 * @date 2021-06-18
 **/
public class ServiceHolder {
	private static ConcurrentHashMap<String, ServiceEndpoint> services = new ConcurrentHashMap<>();
}
