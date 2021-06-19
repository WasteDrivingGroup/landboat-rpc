package com.wastedrivinggroup.service.naming;

import com.orbitz.consul.AgentClient;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.agent.Registration;
import com.wastedrivinggroup.service.naming.consul.ConsulClientHolder;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

/**
 * @author chen
 * @date 2021-06-18
 **/
@Slf4j
public class RedisRegisterPolicy extends AbstractRegisterPolicy {

	@Override
	public void registered(String serviceName) {


	}

	@Override
	public void disRegistered(String serviceName) {

	}
}
