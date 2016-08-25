package com.netty.server.core;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;

/**
 * 异常处理器
 *   
 */
public interface ExceptionProcesser<A>{
	
	public A process(ChannelHandlerContext ctx, ExceptionEvent event) throws Exception;

}
