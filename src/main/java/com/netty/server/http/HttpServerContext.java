package com.netty.server.http;

import org.jboss.netty.channel.Channel;

import com.netty.server.core.ServerContext;
import com.netty.server.core.support.ServerConfig;


public class HttpServerContext extends ServerContext{
	
	private static final String REQ_MESSAGE_FORMAT = "HTTP|REQ|%s|%s|%s";
	
	private String URI;
		
	public HttpServerContext(Channel channel, ServerConfig serverConfig){
		super( channel,  serverConfig);
		setProtocol("HTTP");
	}

	public String getURI() {
		return URI;
	}

	public void setURI(String uRI) {
		URI = uRI;
	}

	public String toString() {
		return String.format(REQ_MESSAGE_FORMAT, this.getFlowId(), this.getClientIp(), URI);
	}
}
