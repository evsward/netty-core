package com.netty.server.http.converter;

import java.io.IOException;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;

import com.netty.server.http.HttpServerContext;
import com.netty.server.http.message.HttpResponseImageMessage;
import com.netty.server.http.message.HttpResponseMessage;
import com.netty.server.utils.Constant;

public class HttpResponseImageConverter extends HttpResponseConverter {
	 
	private static final String HEADER_IMAGE_CONTENT_TYPE = "image/%s";

	@Override
	public HttpResponse buildResponse(HttpServerContext serverContext, HttpResponseMessage bizResponse) throws IOException{

		HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, parseStatus(bizResponse,HttpResponseStatus.OK));
		
		response.setHeader(Constant.Http.HEADER_CONTENT_TYPE, String.format(HEADER_IMAGE_CONTENT_TYPE, ((HttpResponseImageMessage) bizResponse).getType()));
		response.setHeader(Constant.Http.HEADER_CACHE_CONTROL, Constant.Http.HEADER_CACHE_CONTROL_VALUE);
		response.setHeader(Constant.Http.HEADER_PRAGMA, Constant.Http.HEADER_PRAGMA_VALUE);
		
		return response;
	}
	
	@Override
	protected void parseContent(HttpResponse response, HttpServerContext serverContext, HttpResponseMessage bizResponse) throws IOException {
		byte[] resultByte = ((HttpResponseImageMessage) bizResponse).getContentBytes();
		if (resultByte!=null) {
			response.setContent(ChannelBuffers.copiedBuffer(resultByte));			
		}
		
		HttpHeaders.setContentLength(response, response.getContent().readableBytes());		
	}	

}
