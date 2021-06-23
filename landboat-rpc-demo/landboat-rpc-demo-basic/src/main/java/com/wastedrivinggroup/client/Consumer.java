package com.wastedrivinggroup.client;

import com.wastedrivinggroup.consumer.rpc.InvokeProxy;
import com.wastedrivinggroup.netty.ConsumerBootstrap;
import com.wastedrivinggroup.service.naming.consul.ConsulServiceDiscovery;

/**
 * @author chen
 * @date 2021/5/2
 **/
public class Consumer {

	public static final String serviceName = "echo";

	public static void main(String[] args) throws InterruptedException {
		// 启动 Netty 客户端
		ConsumerBootstrap clent = new ConsumerBootstrap();
		clent.start();
		// 服务发现
		new ConsulServiceDiscovery().discovery(serviceName);
		final EchoServiceApi proxy = InvokeProxy.createProxy(EchoServiceApi.class);
		final String echo = proxy.echo("Hello");
		System.out.println(echo);
	}
}
