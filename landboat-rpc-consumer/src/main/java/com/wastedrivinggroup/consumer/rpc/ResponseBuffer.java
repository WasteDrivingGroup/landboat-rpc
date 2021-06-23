package com.wastedrivinggroup.consumer.rpc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.LockSupport;

/**
 * @author chen
 * @date 2021/6/17
 **/
@Slf4j
public class ResponseBuffer {

	private ResponseBuffer() {

	}

	private static final ConcurrentHashMap<Long, Thread> reqBuffer = new ConcurrentHashMap<>();
	private static final ConcurrentHashMap<Long, String> respBuffer = new ConcurrentHashMap<>();

	public static String getResp(Long invokeId) throws InterruptedException {
		// 如果已经有响应了，直接返回
		if (respBuffer.containsKey(invokeId)) {
			return respBuffer.remove(invokeId);
		}
		// 缓存 Thread
		reqBuffer.put(invokeId, Thread.currentThread());
		while (!respBuffer.containsKey(invokeId)) {
			// 是否中断
			if (Thread.currentThread().isInterrupted()) {
				reqBuffer.remove(invokeId);
				throw new InterruptedException("get interrupted when wait response");
			}
			if (!reqBuffer.containsKey(invokeId)) {
				return respBuffer.get(invokeId);
			}
			LockSupport.park();
		}
		return respBuffer.remove(invokeId);
	}

	public static void setResp(Long invokeId, String res) {
		if (!reqBuffer.containsKey(invokeId)) {
			return;
		}
		respBuffer.put(invokeId, res);
		final Thread target = reqBuffer.remove(invokeId);
		LockSupport.unpark(target);
	}
}
