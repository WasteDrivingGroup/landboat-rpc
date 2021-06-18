package com.wastedrivinggroup.service.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 服务
 *
 * @author chen
 * @date 2021-06-18
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Service {
	private String serviceName;

	private List<ServiceAddress> addresses;
}
