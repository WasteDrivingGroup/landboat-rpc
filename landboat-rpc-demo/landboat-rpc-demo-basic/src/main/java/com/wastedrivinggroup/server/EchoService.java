package com.wastedrivinggroup.server;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chen
 * @date 2021/6/15
 **/
@Slf4j
public class EchoService {

	public String echo(String sentence) {
		log.debug("echo from sentence:{}", sentence);
		return sentence;
	}
}
