package com.netty.server.tcp.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netty.server.core.ServerContext;
import com.netty.server.core.annotation.CommandIdBasedPolicy;
import com.netty.server.tcp.message.SystemMessage.PingMessage;
import com.netty.server.tcp.message.SystemMessage.PingMessage.Builder;

@Component
@CommandIdBasedPolicy(256)
public class PingMessageHandler implements ProtobufMessageHandler<PingMessage,PingMessage.Builder> {

	private static Logger logger = LoggerFactory.getLogger(PingMessageHandler.class);
	
	@Override
	public PingMessage.Builder execute(ServerContext serverContext, PingMessage requestMessage) {
		PingMessage.Builder result = PingMessage.newBuilder();
		logger.info(requestMessage.getRemark());
		result.setRemark("ok");
		return result;
	}

	@Override
	public Builder defaultFailedResponse(ServerContext serverContext, Throwable throwable, String errorMessage) {
		return PingMessage.newBuilder().setRemark("exception"+errorMessage);
	}

}
