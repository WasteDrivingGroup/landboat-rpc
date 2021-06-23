package com.wastedrivinggroup.netty;


import io.netty.channel.Channel;

import java.util.Objects;

/**
 * Channel 的持有类
 * TODO: 后续可能提升为接口，改用{@link io.netty.channel.pool.ChannelPool},依旧可以使用该类获取
 *
 * @author chen
 * @date 2021/6/16
 **/
public class ChannelHolder {

	private static final ChannelHolder INSTANCE = new ChannelHolder();

	private ChannelHolder() {
	}

	private static Channel channel;

	public void setChannel(Channel channel) {
		ChannelHolder.channel = channel;
	}

	public Channel getChannel() {
		if (Objects.isNull(channel)) {
			throw new RuntimeException("Channel is null");
		}
		return channel;
	}

	public static ChannelHolder getInstance() {
		return INSTANCE;
	}
}
