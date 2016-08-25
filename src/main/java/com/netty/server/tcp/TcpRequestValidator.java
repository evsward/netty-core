package com.netty.server.tcp;

import org.springframework.stereotype.Component;

import com.netty.server.core.RequestValidator;
import com.netty.server.core.message.ValidateResult;
import com.netty.server.tcp.message.TcpOriginalRequestMessage;

@Component
public class TcpRequestValidator implements RequestValidator<TcpOriginalRequestMessage> {

	@Override
	public ValidateResult validate(TcpOriginalRequestMessage requestMessage) {

		return null;
	}

}
