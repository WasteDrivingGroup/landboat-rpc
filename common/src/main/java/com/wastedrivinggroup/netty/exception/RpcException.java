package com.wastedrivinggroup.netty.exception;

/**
 * @author chen
 * @date 2021/6/15
 **/
public class RpcException extends RuntimeException {

	private int code;

	private String memo;

	public RpcException(int code, String memo) {
		this.code = code;
		this.memo = memo;
	}
}
