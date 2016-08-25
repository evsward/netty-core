package com.netty.server.tcp.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netty.server.core.support.IpWhiteListValidator;
import com.netty.server.tcp.message.TcpOriginalRequestMessage;

public class TcpMessageValidator extends IpWhiteListValidator<TcpOriginalRequestMessage>{

	protected static Logger logger = LoggerFactory.getLogger(TcpMessageValidator.class);

	@Override
	public String fetchBizKey(TcpOriginalRequestMessage requestMessage) {
		return String.valueOf(requestMessage.getCommandId());
	}

	@Override
	public String fetchRequestIp(TcpOriginalRequestMessage requestMessage) {
		return requestMessage.getClientIp();
	}
	
	@Override
	public boolean isExcludes(String bizKey, String requestIp){
		
		if(EXCLUDES_WHITELIST.contains(bizKey)){
			return true;
		}
		
		return false;
	}
}
