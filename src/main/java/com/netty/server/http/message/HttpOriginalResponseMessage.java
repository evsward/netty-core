package com.netty.server.http.message;

import org.jboss.netty.handler.codec.http.HttpResponse;

import com.netty.server.core.ServerContext;
import com.netty.server.core.message.Message;

public class HttpOriginalResponseMessage implements Message {

	private ServerContext serverContext;

	private HttpResponse httpResponse;

	public HttpOriginalResponseMessage(ServerContext serverContext, HttpResponse httpResponse) {
		this.serverContext = serverContext;
		this.httpResponse = httpResponse;
	}

	@Override
	public ServerContext getServerContext() {
		return serverContext;
	}

	@Override
	public Object getBody() {
		return httpResponse;
	}

}
