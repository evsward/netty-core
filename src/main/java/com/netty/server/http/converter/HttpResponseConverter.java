package com.netty.server.http.converter;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netty.server.core.ResponseConverter;
import com.netty.server.core.ServerContext;
import com.netty.server.http.HttpServerContext;
import com.netty.server.http.message.HttpOriginalRequestMessage;
import com.netty.server.http.message.HttpOriginalResponseMessage;
import com.netty.server.http.message.HttpResponseMessage;
import com.netty.server.utils.Constant;


public abstract class HttpResponseConverter implements ResponseConverter<HttpOriginalRequestMessage, HttpOriginalResponseMessage, HttpResponseMessage> {
	
	protected static Logger logger = LoggerFactory.getLogger(HttpResponseConverter.class);
	
	protected static final String HEADER_SERVER_INFO = "Server-Info";
	protected static final String HEADER_SERVER_INFO_VALUE = "%s|%s|%s";
	
	protected static final String HTTP_RESPONSE_INFO = "HTTP|ACK|%s|%s|%s|%s ms|%s";

	@Override
	public HttpOriginalResponseMessage convert(ServerContext serverContext, HttpResponseMessage bizResponse) throws IOException {
		HttpServerContext httpServerContext = (HttpServerContext)serverContext;
		
		HttpResponse response = buildResponse(httpServerContext, bizResponse);

		parseHeader(response, httpServerContext, bizResponse);
				
		parseContent(response, httpServerContext, bizResponse);	
		
		return new HttpOriginalResponseMessage(serverContext,response);
	}
	
	protected abstract HttpResponse buildResponse(HttpServerContext serverContext, HttpResponseMessage bizResponse) throws IOException ;
	
	protected void parseHeader(HttpResponse response, ServerContext serverContext, HttpResponseMessage bizResponse) throws IOException {
		if(bizResponse == null){
			return;
		}
		
		if(response == null){
			return;
		}
		
		Map<String,String> headerMap = bizResponse.getHeader();
		
		if(headerMap == null){
			return;
		}
		
		for (String name : headerMap.keySet()) {
			//下划线开头的header表示保留header，不需要赋给response
			if(!name.startsWith("_")){
				response.setHeader(name, headerMap.get(name));	
			}
		}		
		
	}
	
	protected void parseContent(HttpResponse response, HttpServerContext serverContext, HttpResponseMessage bizResponse) throws IOException {
		String charsetName = Constant.Http.DEFAULT_CHARSET; 
		
		if(serverContext.getCharset() != null){
			charsetName = serverContext.getCharset().displayName();	
		}
		
		String result = bizResponse.getContent();
		
		if(StringUtils.isBlank(result)){
			result = StringUtils.EMPTY;
		}
		
		if(Constant.Http.DEFAULT_CHARSET.equalsIgnoreCase(charsetName)){
			response.setContent(ChannelBuffers.copiedBuffer(result, serverContext.getCharset()));
		}else{
			response.setContent(ChannelBuffers.copiedBuffer(new String(result.getBytes(),charsetName), serverContext.getCharset()));
		}
		
		HttpHeaders.setContentLength(response, response.getContent().readableBytes());		
	}	

	public HttpResponseStatus parseStatus(HttpResponseMessage bizResponse, HttpResponseStatus defaultStatus){
		
		if(bizResponse.getStatus() == 0){
			return defaultStatus;
		}
		
		return HttpResponseStatus.valueOf(bizResponse.getStatus());
	}
	
}
