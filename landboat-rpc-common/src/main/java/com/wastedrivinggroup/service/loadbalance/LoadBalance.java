package com.wastedrivinggroup.service.loadbalance;

import com.wastedrivinggroup.service.pojo.ServiceEndpoint;

/**
 * 负载均衡顶级接口
 *
 * @author chen
 * @date 2021-06-18
 **/
@FunctionalInterface
public interface LoadBalance {
	/**
	 * 选择一个服务
	 *
	 * @return 服务地址
	 */
	ServiceEndpoint choose();
}
