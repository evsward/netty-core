package com.netty.server.utils;

import org.slf4j.Logger;

import com.netty.server.core.ServerContext;

public class LogUtil {
	public static final String LOG_FORMAT = "%s|HAN|%s|%s";

	public static void info(Logger logger, ServerContext serverContext, String message) {
		logger.info(String.format(LOG_FORMAT, serverContext.getProtocol(), serverContext.getFlowId(), message));
	}

	public static void warn(Logger logger, ServerContext serverContext, String message) {
		logger.warn(String.format(LOG_FORMAT, serverContext.getProtocol(), serverContext.getFlowId(), message));
	}
	
	public static void error(Logger logger, ServerContext serverContext, String message, Throwable throwable) {
		logger.error(String.format(LOG_FORMAT, serverContext.getProtocol(), serverContext.getFlowId(), message), throwable);
	}	
}
