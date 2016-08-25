package com.netty.server.core;

import java.io.IOException;

/**
 * 消息转换器
 * 
 * 将请求消息转换为业务消息，将业务响应转换为响应消息
 *   
 */
public interface MessageConverter<R, A, BR, BA> {

	public BR toBizRequest(R requestMessage) throws IOException;

	public A toResponse(ServerContext serverContext, BA bizResponse) throws IOException;

}
