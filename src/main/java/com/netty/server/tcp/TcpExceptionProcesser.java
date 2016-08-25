package com.netty.server.tcp;

import java.io.IOException;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.MessageLite;
import com.netty.server.core.ServerContext;
import com.netty.server.core.exception.AlertException;
import com.netty.server.core.exception.ValidateException;
import com.netty.server.core.message.ValidateResult;
import com.netty.server.core.support.GenericExceptionProcesser;
import com.netty.server.tcp.message.TcpOriginalRequestMessage;
import com.netty.server.tcp.message.TcpOriginalResponseMessage;
import com.netty.server.tcp.message.SystemMessage.AlertMessage;
import com.netty.server.utils.Constant;

/**
 * 请求统一处理接收RequestMessage并进行业务处理，处理后返回ResponseMessage
 * 
 */
public class TcpExceptionProcesser extends GenericExceptionProcesser<TcpOriginalRequestMessage, TcpOriginalResponseMessage, MessageLite, MessageLite.Builder> {

	protected static Logger logger = LoggerFactory.getLogger(TcpExceptionProcesser.class);

	private static final String EXCEPTION_SYSTEM 		= " TCP|EXC|%s|%s";
	 
	@Override
	public MessageLite.Builder alertExceptionProcess(ChannelHandlerContext ctx, ExceptionEvent event, ServerContext serverContext,
			AlertException exception) throws IOException {
		//系统级别处理异常，一般为配置问题或消息号错误，需要关闭channel
		// TCP|EXC|1128675508373513
		logger.error(String.format(EXCEPTION_SYSTEM, serverContext.getFlowId(), parseSimpleError(exception)));
		
		AlertMessage.Builder alert = AlertMessage.newBuilder();
		alert.setCode(exception.getAlertCode());
		alert.setRemark(exception.getMessage());
		
		return alert;
	}

	@Override
	public MessageLite.Builder validateExceptionProcess(ChannelHandlerContext ctx, ExceptionEvent event, ServerContext serverContext,
			ValidateException exception) throws IOException {
		//业务级别校验异常，一般为非法请求，例如：请求Ip白名单校验失败，需要关闭channel
		// TCP|EXC|1128675508373513|
		logger.error(String.format(EXCEPTION_SYSTEM, serverContext.getFlowId(), parseSimpleError(exception)));
		
		ValidateResult validateResult = exception.getValidateResult();
		
		AlertMessage.Builder alert = AlertMessage.newBuilder();
		
		if(validateResult != null){
			alert.setCode(validateResult.getResultCode());
			alert.setRemark(validateResult.getResultMessage());	
		}else{
			alert.setCode(Constant.ErrorCode.UNKNOWN_ERROR);
			alert.setRemark(exception.getMessage());
		}
		
		return alert;
	}
	

}
