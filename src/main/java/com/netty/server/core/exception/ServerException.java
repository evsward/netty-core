package com.netty.server.core.exception;

import java.text.MessageFormat;

/**
 * 系统错误信息
 *  
 */
public abstract class ServerException extends RuntimeException {
	
	private static final long serialVersionUID = -8693198223791347135L;

	public ServerException(String info) {
		super(info);
	}

	public ServerException(String info, Object[] args) {
		super(MessageFormat.format(info, args));
	}
	
}
