package com.netty.server.core.message;

import com.netty.server.core.ServerContext;

public interface Message {

	public ServerContext getServerContext();
	
	public Object getBody();
		
}
