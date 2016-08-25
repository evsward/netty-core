package com.netty.server.core;

import java.util.List;


public interface ProcessController<R, A, BR, BA>{

	public MessageHandler<BR, BA> getMessageHandler(ServerContext requestMessage);
	
	public List<RequestValidator<R>> getRequestValidators(ServerContext requestMessage);
	
	public MessageConverter<R, A, BR, BA> getMessageConverter(ServerContext requestMessage);
	
}
