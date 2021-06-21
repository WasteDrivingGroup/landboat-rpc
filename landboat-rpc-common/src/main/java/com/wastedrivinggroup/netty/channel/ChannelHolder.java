package com.wastedrivinggroup.netty.channel;

import io.netty.channel.Channel;

/**
 * Channel 的持有这
 *
 * @author chen
 * @date 2021/6/21
 **/
public interface ChannelHolder {

	/**
	 * 随机获取一个
	 *
	 * @return {@link Channel}
	 */
	default Channel getChannel() {
		return getChannel(null);
	}

	/**
	 * 获取一个 Channel
	 *
	 * @param key Channel特征,服务名称
	 * @return {@link Channel}
	 */
	Channel getChannel(String key);
}
