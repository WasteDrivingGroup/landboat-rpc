package com.wastedrivinggroup.service.naming;

import com.wastedrivinggroup.service.pojo.ServiceEndpoint;

import java.util.List;

/**
 * @author chen
 * @date 2021-06-18
 **/
public interface ServiceDiscovery {
	/**
	 * 服务发现
	 *
	 * @param serviceName 服务名称
	 * @return 服务点 {@link ServiceEndpoint}
	 */
	List<ServiceEndpoint> discovery(String serviceName);
}
