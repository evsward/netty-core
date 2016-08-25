package com.netty.server.core.support;

import java.util.List;

import org.jboss.netty.channel.ChannelHandlerContext;

import com.netty.server.core.MessageConverter;
import com.netty.server.core.MessageHandler;
import com.netty.server.core.ProcessController;
import com.netty.server.core.RequestProcesser;
import com.netty.server.core.RequestValidator;
import com.netty.server.core.ServerContext;
import com.netty.server.core.exception.AlertException;
import com.netty.server.core.exception.ValidateException;
import com.netty.server.core.message.Message;
import com.netty.server.core.message.ValidateResult;

/**
 * 请求统一处理 接收RequestMessage并进行业务处理，处理后返回ResponseMessage
 * 
 * R	表示 requestMessage
 * A	表示 responseMessage
 * BR	表示 bizRequest
 * BA	表示 bizResponse
 * 
 */
public abstract class GenericRequestProcesser<R extends Message, A extends Message, BR, BA> implements RequestProcesser<R, A> {

	private ProcessController<R, A, BR, BA> processController;

	@Override
	public A process(ChannelHandlerContext ctx, R requestMessage) throws Exception {

		ServerContext serverContext = requestMessage.getServerContext(); 
		
		ctx.setAttachment(serverContext);
		
		// 1)获取请求校验器列表
		List<RequestValidator<R>> validators = processController.getRequestValidators(serverContext);

		// 2)存在校验器则执行校验处理
		for (RequestValidator<R> requestValidator : validators) {
			ValidateResult validateResult = requestValidator.validate(requestMessage);
			if (!validateResult.isPass()) {
				// 校验失败
				throw new ValidateException(validateResult);
			}
			// 校验成功继续执行其他校验器
		}

		// 3)将RequestMesage解析为protobuf类型消息(MessageLite)
		MessageConverter<R, A, BR, BA> converter = processController.getMessageConverter(serverContext);

		if (converter == null) {
			throw new AlertException("MessageConverter is null,pls config");
		}

		BR reqMessage = converter.toBizRequest(requestMessage);

		// 4)根据commandId获取业务处理
		MessageHandler<BR, BA> messageHandler = processController.getMessageHandler(serverContext);

		if (messageHandler == null) {
			throw new AlertException("MessageHandler is null,pls config");
		}

		// 5)进行业务处理
		BA ackMessage = messageHandler.execute(serverContext, reqMessage);

		// 6)将处理结果封装为 ResponseMessage
		return converter.toResponse(serverContext, ackMessage);

	}

	public void setProcessController(ProcessController<R, A, BR, BA> processController) {
		this.processController = processController;
	}
	
	

}
