package com.wastedrivinggroup.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author chen
 * @date 2021/6/20
 **/
@Slf4j
public class GracefulShutdownChain implements GracefulShutdown {
	private static final GracefulShutdownChain triggerShutdownRegister;

	private static final List<GracefulShutdown> chain;
	private static final AtomicBoolean isRegister;

	private GracefulShutdownChain() {
		if (!isRegister.getAndSet(true)) {
			Runtime.getRuntime().addShutdownHook(new Thread(this::clear));
		}
	}

	static {
		chain = new ArrayList<>();
		isRegister = new AtomicBoolean(false);
		triggerShutdownRegister = new GracefulShutdownChain();
	}

	public static void addShutdown(GracefulShutdown shutdown) {
		if (log.isDebugEnabled()) {
			log.debug("add a GracefulShutdown to GracefulShutdownChain");
		}
		chain.add(shutdown);
	}

	public static void removeShutdown(GracefulShutdown shutdown) {
		if (log.isDebugEnabled()) {
			log.debug("remove a GracefulShutdown from GracefulShutdownChain");
		}
		chain.remove(shutdown);
	}


	@Override
	public void clear() {
		chain.sort(Comparator.comparingInt(GracefulShutdown::getPriority));
		if (log.isInfoEnabled()) {
			log.info("start running shutdown hook");
		}
		chain.forEach(GracefulShutdown::clear);
	}
}
