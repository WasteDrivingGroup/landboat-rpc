package com.wastedrivinggroup.service.naming.redis;

import com.wastedrivinggroup.service.naming.AbstractRegisterPolicy;
import lombok.extern.slf4j.Slf4j;

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
