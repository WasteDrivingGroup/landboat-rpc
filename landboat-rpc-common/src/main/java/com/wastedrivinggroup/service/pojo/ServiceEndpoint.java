package com.wastedrivinggroup.service.pojo;

import com.orbitz.consul.model.health.Service;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 服务地址
 * <p>
 * TODO: 后续可以增加权重 weight 等字段
 *
 * @author chen
 * @date 2021-06-18
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ServiceEndpoint {
	private String host;

	private Integer port;

	public static ServiceEndpoint tran2(Service service) {
		return new ServiceEndpoint(service.getAddress(), service.getPort());
	}
}
