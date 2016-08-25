package com.netty.server.http.converter;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;

import com.netty.server.http.HttpServerContext;
import com.netty.server.http.message.HttpResponseMessage;
import com.netty.server.utils.Constant;
import com.netty.server.utils.HttpUtils;

public class HttpResponseProxyConverter extends HttpResponseConverter {
	
	@Override
	public HttpResponse buildResponse(HttpServerContext serverContext, HttpResponseMessage bizResponse) throws IOException{

		return new DefaultHttpResponse(HttpVersion.HTTP_1_1, parseStatus(bizResponse,HttpResponseStatus.OK));
	}
	
	protected void parseContent(HttpResponse response, HttpServerContext serverContext, HttpResponseMessage bizResponse) throws IOException {
		
		String targetUrl = bizResponse.getHeaderValue(Constant.Http.HEADER_PROXY_LOCATION);
		
		String charsetName = Constant.Http.DEFAULT_CHARSET; 
		
		if(serverContext.getCharset() != null){
			charsetName = serverContext.getCharset().displayName();	
		}
		
		String result = HttpUtils.getUrlContentAnyWay(targetUrl);
		
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

}
