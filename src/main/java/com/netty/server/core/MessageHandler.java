package com.netty.server.core;


/**
 * 消息处理器
 * 
 * 处理业务请求消息，处理完成返回业务响应消息
 *   
 */
public interface MessageHandler<BR ,BA> {
	
	public BA execute(ServerContext serverContext, BR requestMessage);
	
	public BA defaultFailedResponse(ServerContext serverContext, Throwable throwable, String errorMessage);
	
}
