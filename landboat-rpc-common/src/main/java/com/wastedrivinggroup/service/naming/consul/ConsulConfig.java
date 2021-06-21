package com.wastedrivinggroup.service.naming.consul;

import java.util.Objects;

/**
 * @author chen
 * @date 2021/6/19
 **/
public class ConsulConfig {

	private static String host;

	private static Integer port;

	public static String getHost() {
		return host;
	}

	public static void setHost(String host) {
		ConsulConfig.host = host;
	}

	public static Integer getPort() {
		return port;
	}

	public static void setPort(Integer port) {
		ConsulConfig.port = port;
	}

	public static boolean isValid() {
		// TODO: 检查可以细化
		return Objects.nonNull(host) && Objects.nonNull(port);
	}
}
