package com.netty.server.http;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.netty.server.core.MessageConverter;
import com.netty.server.core.ServerContext;
import com.netty.server.http.converter.HttpResponseConverter;
import com.netty.server.http.message.HttpOriginalRequestMessage;
import com.netty.server.http.message.HttpOriginalResponseMessage;
import com.netty.server.http.message.HttpRequestMessage;
import com.netty.server.http.message.HttpResponseContentType;
import com.netty.server.http.message.HttpResponseMessage;


public class HttpMessageConverter implements MessageConverter<HttpOriginalRequestMessage, HttpOriginalResponseMessage, HttpRequestMessage, HttpResponseMessage> {

	protected static Logger logger = LoggerFactory.getLogger(HttpMessageConverter.class);
	
	private static final String TCP_REQUEST_INFO  = "HTTP|RBO|%s|%s";
	private static final String TCP_RESPONSE_INFO = "HTTP|ABO|%s|%s|%s";
					
	private Map<HttpResponseContentType, HttpResponseConverter> converters = Maps.newHashMap();
			
	@Override
	public HttpRequestMessage toBizRequest(HttpOriginalRequestMessage requestMessage) throws IOException {
		HttpRequestMessage reqMessage =  new HttpRequestMessage(requestMessage);
		
		logger.info(String.format(TCP_REQUEST_INFO,requestMessage.getServerContext().getFlowId(),reqMessage.getParameters()));
		
		return reqMessage;
	}

	@Override
	public HttpOriginalResponseMessage toResponse(ServerContext serverContext, HttpResponseMessage bizResponse) throws IOException {
				
		HttpResponseConverter responseConverter = fetchAdapter(bizResponse); 
		
		if(responseConverter == null){
			throw new RuntimeException(" HttpResponseConverter is null, pls config ");
		}
		
		HttpOriginalResponseMessage ackMessage = responseConverter.convert(serverContext, bizResponse);	
		
		//((HttpResponse)ackMessage.getBody()).getStatus()
		logger.info(String.format(TCP_RESPONSE_INFO, serverContext.getFlowId(), bizResponse.getContentType().name(),bizResponse.getContent()));
		
		return ackMessage;
	}
	
	public HttpResponseConverter fetchAdapter(HttpResponseMessage bizResponse){
		
		if(bizResponse == null){
			return converters.get(HttpResponseContentType.text);
		}
		
		if(bizResponse.getContentType() == null){
			return converters.get(HttpResponseContentType.text);
		}
		
		return converters.get(bizResponse.getContentType());
		
	}	
	
	public void setConverters(Map<HttpResponseContentType, HttpResponseConverter> converters) {
		this.converters = converters;
	}
	
}
