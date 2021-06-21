package com.wastedrivinggroup.netty.server.config;

import com.google.common.base.Preconditions;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author chen
 * @date 2021/4/16
 **/
public class NettyServerConfig {

	/**
	 * 主机地址
	 */
	private static String HOST;

	/**
	 * 服务端口号
	 */
	private static Integer PORT;

	/**
	 * 服务名称
	 */
	private static String SERVICE_NAME;

	static {
		try {
			final InetAddress localHost = InetAddress.getLocalHost();
			HOST = localHost.getHostAddress();
			SERVICE_NAME = localHost.getHostName();
			PORT = 8888;
		} catch (UnknownHostException e) {
			// NOOP
		}
	}

	public static String getHost() {
		return HOST;
	}

	public static Integer getPort() {
		return PORT;
	}

	public static String getServiceName() {
		return SERVICE_NAME;
	}

	public static void setHost(String HOST) {
		NettyServerConfig.HOST = HOST;
	}

	public static void setPort(Integer PORT) {
		NettyServerConfig.PORT = PORT;
	}

	public static void setServiceName(String serviceName) {
		SERVICE_NAME = serviceName;
	}

	public static void checkConfig() {
		Preconditions.checkNotNull(HOST, "server's HOST can't be null");
		Preconditions.checkNotNull(PORT, "server's PORT can't be null");
		Preconditions.checkNotNull(SERVICE_NAME, "server's SERVICE_NAME can't be null");
	}
}
