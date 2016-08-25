package com.netty.server.core.support;


public class HandlerConfig {
	private static final String SERVER_INFO_FORMAT = "* handler\t%s\t%s\t%s";
	
	public HandlerConfig(String bizKey, String handler, String requestType){
		this.bizKey = bizKey;
		this.handler = handler;
		this.requestType = requestType;
	}
	
	public HandlerConfig(String bizKey, String handler, String requestType, String responseType){
		this.bizKey = bizKey;
		this.handler = handler;
		this.requestType = requestType;
		this.responseType = responseType;
	}
	
	private String bizKey;
	
	private String handler;
	
	private String requestType;
	
	private String responseType;
	
	@Override
	public String toString() {
		return String.format(SERVER_INFO_FORMAT, bizKey, handler, requestType);
	}

	public String getBizKey() {
		return bizKey;
	}

	public void setBizKey(String bizKey) {
		this.bizKey = bizKey;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

}
