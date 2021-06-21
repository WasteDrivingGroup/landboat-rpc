package com.wastedrivinggroup.exception;

import com.wastedrivinggroup.service.Constant;

/**
 * @author chen
 * @date 2021/6/15
 **/
public class ServiceNotFoundException extends RpcException {

	public ServiceNotFoundException(String serviceName) {
		super(Constant.ErrCode.SERVICE_NOT_FOUND, serviceName + "服务未找到");
	}
}
