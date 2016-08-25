package com.netty.server.core;

import org.jboss.netty.channel.ChannelHandlerContext;

/**
 * 请求统一处理
 * 
 * 接收RequestMessage并进行业务处理，处理后返回ResponseMessage
 *  
 * @author zouhongw
 *
 */
public interface RequestProcesser<R,A> {
	
	public A process(ChannelHandlerContext ctx, R requestMessage) throws Exception;

}
