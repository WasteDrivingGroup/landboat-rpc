package com.wastedrivinggroup.service.naming.consul;

import com.orbitz.consul.AgentClient;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.agent.Registration;
import com.wastedrivinggroup.netty.server.config.NettyServerConfig;
import com.wastedrivinggroup.service.naming.AbstractRegisterPolicy;
import com.wastedrivinggroup.utils.GracefulShutdown;
import com.wastedrivinggroup.utils.GracefulShutdownChain;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

/**
 * @author chen
 * @date 2021/6/19
 **/
@Slf4j
public class ConsulServiceRegister extends AbstractRegisterPolicy {
	@Override
	public void registered(String serviceName) {
		if (!ConsulClientHolder.existClient()) {
			throw new RuntimeException("No consul servers have been connected");
		}
		NettyServerConfig.checkConfig();
		// 向 Consul 注册服务
		// name 可以重复，表示同个服务的多个实例,但是 id 必须全局唯一
		// TODO: 缺少健康检测
		Registration newService = ImmutableRegistration.builder()
				.id(generateInstanceId())
				.name(serviceName)
				.tags(Collections.singletonList("rpc"))
				.address(NettyServerConfig.getHost())
				.port(NettyServerConfig.getPort())
				.build();
		for (AgentClient client : ConsulClientHolder.getAgentClients()) {
			client.register(newService);
			// TODO: 不确定 AgentClient 是否是临时创建，存在对象引用逃逸的可能
			GracefulShutdownChain.addShutdown(new GracefulShutdown() {

				@Override
				public void clear() {
					// 正常调用没事,但是在
					client.deregister(newService.getId());
				}

				@Override
				public int getPriority() {
					return 0;
				}
			});
			if (log.isInfoEnabled()) {
				log.info("register consul service success,serviceName:{}", serviceName);
			}
		}
		registeredService.add(serviceName);
	}

	private String generateInstanceId() {
		// 生成id 的方式如果包含 Http URL 一些字符或导致 API 调用报错,没有做转义
		// 即使使用 URLEncoder 转义显示也很离谱
		return "rpc$" + NettyServerConfig.getHost() + "$" + NettyServerConfig.getPort();
	}

	@Override
	public void disRegistered(String serviceName) {
		final List<AgentClient> agentClients = ConsulClientHolder.getAgentClients();
		if (agentClients.isEmpty()) {
			return;
		}
		for (AgentClient agentClient : agentClients) {
			agentClient.deregister(generateInstanceId());
		}
		registeredService.remove(serviceName);
	}

	@Override
	public boolean isValid() {
		return !ConsulConfig.isValid();
	}
}
