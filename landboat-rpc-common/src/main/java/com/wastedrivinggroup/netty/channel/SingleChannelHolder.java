package com.wastedrivinggroup.netty.channel;


import io.netty.channel.Channel;

import java.util.Objects;

/**
 * Channel 的持有类
 * TODO: 后续可能提升为接口，改用{@link io.netty.channel.pool.ChannelPool},依旧可以使用该类获取
 *
 * @author chen
 * @date 2021/6/16
 **/
public class SingleChannelHolder implements ChannelHolder {

	private static final SingleChannelHolder INSTANCE = new SingleChannelHolder();

	private SingleChannelHolder() {
	}

	private static Channel channel;

	public void setChannel(Channel channel) {
		SingleChannelHolder.channel = channel;
	}

	@Override
	public Channel getChannel(String key) {
		if (Objects.isNull(channel)) {
			throw new RuntimeException("Channel is null");
		}
		return channel;
	}

	public static SingleChannelHolder getInstance() {
		return INSTANCE;
	}
}
