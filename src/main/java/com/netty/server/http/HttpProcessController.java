package com.netty.server.http;

import java.util.List;

import org.jboss.netty.handler.codec.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.netty.server.core.MessageConverter;
import com.netty.server.core.MessageHandler;
import com.netty.server.core.ProcessController;
import com.netty.server.core.RequestValidator;
import com.netty.server.core.ServerContext;
import com.netty.server.http.message.HttpOriginalRequestMessage;
import com.netty.server.http.message.HttpRequestMessage;
import com.netty.server.http.message.HttpResponseMessage;


public class HttpProcessController implements ProcessController<HttpOriginalRequestMessage, HttpResponse, HttpRequestMessage, HttpResponseMessage> {
	
	protected static Logger logger = LoggerFactory.getLogger(HttpProcessController.class);
		
	private HttpControlerConfig controlerConfig;
	
	private List<RequestValidator<HttpOriginalRequestMessage>> requestValidators = Lists.newArrayList();
	
	private MessageConverter<HttpOriginalRequestMessage, HttpResponse, HttpRequestMessage, HttpResponseMessage> messageConverter;
	
	@Override
	public List<RequestValidator<HttpOriginalRequestMessage>> getRequestValidators(ServerContext serverContext){
		return requestValidators;
	}

	@SuppressWarnings({"unchecked" })
	@Override
	public MessageHandler<HttpRequestMessage, HttpResponseMessage> getMessageHandler(ServerContext serverContext) {

		String requestURI = ((HttpServerContext)serverContext).getURI();

		int _index = requestURI.indexOf("?");
		if(_index > 0){
			requestURI = requestURI.substring(0,_index);
		}		
		return (MessageHandler<HttpRequestMessage, HttpResponseMessage>)controlerConfig.getMessageHandler(requestURI);
	}
	
	public MessageConverter<HttpOriginalRequestMessage, HttpResponse, HttpRequestMessage, HttpResponseMessage> getMessageConverter(ServerContext serverContext){
		return messageConverter;
	}

	public void setControlerConfig(HttpControlerConfig controlerConfig) {
		this.controlerConfig = controlerConfig;
	}

	public void setMessageConverter(MessageConverter<HttpOriginalRequestMessage, HttpResponse, HttpRequestMessage, HttpResponseMessage> messageConverter) {
		this.messageConverter = messageConverter;
	}

	public void setRequestValidators(List<RequestValidator<HttpOriginalRequestMessage>> requestValidators) {
		this.requestValidators = requestValidators;
	}

}
