package com.wastedrivinggroup.service.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 服务地址
 *
 * @author chen
 * @date 2021-06-18
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ServiceAddress {
	private String ip;

	private Integer port;
}
