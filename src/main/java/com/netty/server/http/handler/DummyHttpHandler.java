package com.netty.server.http.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netty.server.core.ServerContext;
import com.netty.server.http.message.HttpRequestMessage;
import com.netty.server.http.message.HttpResponseMessage;

@Component("dummyHttpHandler")
public class DummyHttpHandler implements HttpMessageHandler<HttpRequestMessage,HttpResponseMessage> {
 
	protected static Logger logger = LoggerFactory.getLogger(DummyHttpHandler.class);
	
	private static final String DUMMY_MESSAGE = "{\"errorCode\":\"404\",\"errorMessage\":\"%s\"}";
	
	@Override
	public HttpResponseMessage execute(ServerContext serverContext, HttpRequestMessage requestMessage) {
		return new HttpResponseMessage(String.format(DUMMY_MESSAGE, requestMessage.getUri()));
	}

	@Override
	public HttpResponseMessage defaultFailedResponse(ServerContext serverContext, Throwable throwable, String errorMessage) {		
		return HttpResponseMessage.defaultFailedResponse();
	}	

}
