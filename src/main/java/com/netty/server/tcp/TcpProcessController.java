package com.netty.server.tcp;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.protobuf.MessageLite;
import com.netty.server.core.MessageConverter;
import com.netty.server.core.MessageHandler;
import com.netty.server.core.ProcessController;
import com.netty.server.core.RequestValidator;
import com.netty.server.core.ServerContext;
import com.netty.server.tcp.message.TcpOriginalRequestMessage;
import com.netty.server.tcp.message.TcpOriginalResponseMessage;

public class TcpProcessController implements ProcessController<TcpOriginalRequestMessage, TcpOriginalResponseMessage, MessageLite, MessageLite.Builder> {

	protected static Logger logger = LoggerFactory.getLogger(TcpProcessController.class);

	private TcpControlerConfig controlerConfig;
	
	private List<RequestValidator<TcpOriginalRequestMessage>> requestValidators = Lists.newArrayList();

	private MessageConverter<TcpOriginalRequestMessage, TcpOriginalResponseMessage, MessageLite, MessageLite.Builder> messageConverter;

	@Override
	public List<RequestValidator<TcpOriginalRequestMessage>> getRequestValidators(ServerContext serverContext) {
		return requestValidators;
	}

	@SuppressWarnings("unchecked")
	@Override
	public MessageHandler<MessageLite, MessageLite.Builder> getMessageHandler(ServerContext serverContext) {
		return (MessageHandler<MessageLite, MessageLite.Builder>) controlerConfig.getMessageHandler(((TcpServerContext)serverContext).getCommandId());
	}

	public MessageConverter<TcpOriginalRequestMessage, TcpOriginalResponseMessage, MessageLite, MessageLite.Builder> getMessageConverter(ServerContext serverContext) {
		return messageConverter;
	}

	public void setControlerConfig(TcpControlerConfig controlerConfig) {
		this.controlerConfig = controlerConfig;
	}

	public void setMessageConverter(MessageConverter<TcpOriginalRequestMessage, TcpOriginalResponseMessage, MessageLite, MessageLite.Builder> messageConverter) {
		this.messageConverter = messageConverter;
	}

	public void setRequestValidators(List<RequestValidator<TcpOriginalRequestMessage>> requestValidators) {
		this.requestValidators = requestValidators;
	}
	
	
}
