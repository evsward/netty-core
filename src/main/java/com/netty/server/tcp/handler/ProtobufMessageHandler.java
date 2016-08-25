package com.netty.server.tcp.handler;

import com.google.protobuf.MessageLite;
import com.netty.server.core.MessageHandler;
import com.netty.server.core.ServerContext;


public interface ProtobufMessageHandler<BR extends MessageLite,BA extends MessageLite.Builder> extends MessageHandler<BR,BA>{
	
	public BA execute(ServerContext serverContext, BR requestMessage);
	

}
