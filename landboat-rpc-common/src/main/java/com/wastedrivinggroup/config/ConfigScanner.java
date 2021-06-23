package com.wastedrivinggroup.config;

/**
 * @author chen
 * @date 2021-06-18
 **/
public interface ConfigScanner {

	/**
	 * 是否可以加载该配置
	 *
	 * @return true —> should
	 */
	boolean shouldLoad();

	/**
	 * 加载配置
	 */
	void loadConfig();

}
