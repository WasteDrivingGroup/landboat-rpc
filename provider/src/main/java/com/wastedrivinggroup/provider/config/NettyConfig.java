package com.wastedrivinggroup.provider.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author chen
 * @date 2021-06-18
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class NettyConfig {

	private String ip = "localhost";

	private Integer port = 8888;
}
