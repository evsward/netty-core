package com.netty.server.core.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netty.server.utils.Constant;

public class InetSocketAddress extends java.net.InetSocketAddress {

	private static final long serialVersionUID = 5442811314966713068L;
	
	protected static Logger logger = LoggerFactory.getLogger(InetSocketAddress.class);
	
	private static final String SOCKET_ADDRESS_PORT 		=  "* %s SERVICE BIND PORT %s ";
	
	public InetSocketAddress(int port ,String serviceName) {
		super(port);
		
		logger.info(String.format(SOCKET_ADDRESS_PORT, serviceName, port));
		logger.info(Constant.Str.CUTOFF_RULE);
	}

}
