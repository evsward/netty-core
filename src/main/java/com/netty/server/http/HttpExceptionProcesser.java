package com.netty.server.http;

import java.io.IOException;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netty.server.core.ServerContext;
import com.netty.server.core.exception.AlertException;
import com.netty.server.core.exception.ValidateException;
import com.netty.server.core.support.GenericExceptionProcesser;
import com.netty.server.http.message.HttpOriginalRequestMessage;
import com.netty.server.http.message.HttpOriginalResponseMessage;
import com.netty.server.http.message.HttpRequestMessage;
import com.netty.server.http.message.HttpResponseMessage;

/**
 * 请求统一处理 接收RequestMessage并进行业务处理，处理后返回ResponseMessager
 * 
 */

public class HttpExceptionProcesser extends GenericExceptionProcesser<HttpOriginalRequestMessage,HttpOriginalResponseMessage,HttpRequestMessage, HttpResponseMessage> {

	protected static Logger logger = LoggerFactory.getLogger(HttpExceptionProcesser.class);
	
	private static final String EXCEPTION_SYSTEM 		= "HTTP|EXC|%s|%s";
	

	@Override
	public HttpResponseMessage alertExceptionProcess(ChannelHandlerContext ctx, ExceptionEvent event, ServerContext serverContext,
			AlertException exception) throws IOException {
		//系统级别处理异常，一般为配置问题或消息号错误，需要关闭channel
		//HTTP|EXCEPTION|ALERT|1128675508373513
		String simpleError = parseSimpleError(exception);
		
		logger.error(String.format(EXCEPTION_SYSTEM, serverContext.getFlowId(), simpleError));
		return new HttpResponseMessage(simpleError);
	}

	@Override
	public HttpResponseMessage validateExceptionProcess(ChannelHandlerContext ctx, ExceptionEvent event, ServerContext serverContext,
			ValidateException exception) throws IOException {
		//业务级别校验异常，一般为非法请求，例如：请求Ip白名单校验失败，需要关闭channel
		// TCP|EXCEPTION|VALID|1128675508373513|
		String simpleError = parseSimpleError(exception);
		
		logger.error(String.format(EXCEPTION_SYSTEM, serverContext.getFlowId(), simpleError));
		return new HttpResponseMessage(simpleError);
	}

}
