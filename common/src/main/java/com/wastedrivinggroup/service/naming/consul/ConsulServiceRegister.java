package com.wastedrivinggroup.service.naming.consul;

import com.orbitz.consul.AgentClient;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.agent.Registration;
import com.wastedrivinggroup.netty.server.config.NettyServerConfig;
import com.wastedrivinggroup.service.naming.RegisterPolicy;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

/**
 * @author chen
 * @date 2021/6/19
 **/
@Slf4j
public class ConsulServiceRegister implements RegisterPolicy {
	@Override
	public void registered(String serviceName) {
		final List<AgentClient> agentClients = ConsulClientHolder.getAgentClients();
		if (agentClients.isEmpty()) {
			throw new RuntimeException("No consul servers have been connected");
		}
		// 向 Consul 注册服务
		// TODO: 缺少健康检测
		Registration newService = ImmutableRegistration.builder()
				.id(serviceName)
				.name(serviceName)
				.tags(Collections.singletonList("rpc"))
				.address(NettyServerConfig.getHost())
				.port(NettyServerConfig.getPort())
				.build();
		for (AgentClient client : ConsulClientHolder.getAgentClients()) {
			client.register(newService);
			if (log.isInfoEnabled()) {
				log.info("register consul service success,serviceName:{}", serviceName);
			}
		}
	}

	@Override
	public void disRegistered(String serviceName) {
		final List<AgentClient> agentClients = ConsulClientHolder.getAgentClients();
		if (agentClients.isEmpty()) {
			return;
		}
		for (AgentClient agentClient : agentClients) {
			agentClient.deregister(serviceName);
		}
	}

	@Override
	public boolean isValid() {
		return !ConsulConfig.isValid();
	}
}
