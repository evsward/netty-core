package com.netty.server.http.message;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * HTTP请求消息
 * 
 */
public class HttpResponseMessage {

	private static final String DEFAULT_ERROR_MESSAGE = "{\"errorCode\":\"500\",\"errorMessage\":\"%s\"}";
	
	private int status;
	private String content;
	private HttpResponseContentType contentType = HttpResponseContentType.json;
	private Map<String,String> header = Maps.newHashMap();

	public HttpResponseMessage() {
		super();
	}
	
	public HttpResponseMessage(String content) {
		this.content = content;
	}
	
	public HttpResponseMessage(HttpResponseContentType contentType) {
		this.contentType = contentType;
	}
	
	public HttpResponseMessage(String content, HttpResponseContentType contentType) {
		this.content = content;
		this.contentType = contentType;
	}
	
	public HttpResponseMessage(String content, HttpResponseContentType contentType, int status) {
		this.content = content;
		this.contentType = contentType;
		this.status = status;
	}
	
	public static HttpResponseMessage defaultFailedResponse() {
		return new HttpResponseMessage(String.format(DEFAULT_ERROR_MESSAGE, "Exception"));
	}
	
	public static HttpResponseMessage defaultFailedResponse(String content) {
		return new HttpResponseMessage(String.format(DEFAULT_ERROR_MESSAGE, content));
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public HttpResponseContentType getContentType() {
		return contentType;
	}

	public void setContentType(HttpResponseContentType contentType) {
		this.contentType = contentType;
	}

	public Map<String, String> getHeader() {
		return header;
	}

	public void setHeader(Map<String, String> header) {
		this.header = header;
	};
	
	public Map<String, String> addHeader(String name ,String value) {
		header.put(name, value);
		return header;
	}
	
	public String getHeaderValue(String name) {		
		return header.get(name);
	}

}
