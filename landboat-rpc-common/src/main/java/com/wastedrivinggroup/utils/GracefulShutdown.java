package com.wastedrivinggroup.utils;

/**
 * 优雅停机接口
 *
 * @author chen
 * @date 2021/6/20
 **/
public interface GracefulShutdown {

	/**
	 * 清理线程
	 */
	void clear();

	/**
	 * 获取优先级,默认为5
	 *
	 * @return 越小越先执行
	 */
	default int getPriority() {
		return 5;
	}
}
