package service;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * @author chen
 * @date 2021/6/15
 **/
@Slf4j
public class TestService {

	private HashMap<String, Integer> hash = new HashMap<>();

	public Integer incr(String name) {
		int value;
		synchronized (TestService.class) {
			value = hash.getOrDefault(name, 0);
			hash.put(name, ++value);
		}
		return value;
	}
}
