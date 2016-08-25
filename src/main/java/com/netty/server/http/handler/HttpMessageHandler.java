package com.netty.server.http.handler;

import com.netty.server.core.MessageHandler;
import com.netty.server.core.ServerContext;
import com.netty.server.http.message.HttpRequestMessage;
import com.netty.server.http.message.HttpResponseMessage;


public interface HttpMessageHandler<BR extends HttpRequestMessage,BA extends HttpResponseMessage>  extends MessageHandler<BR,BA>{

	public BA execute(ServerContext serverContext, BR requestMessage);

}
