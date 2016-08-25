package com.netty.server.tcp.codec;

import java.nio.ByteOrder;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.MessageLite;
import com.netty.server.core.support.ServerConfig;
import com.netty.server.tcp.TcpServerContext;
import com.netty.server.tcp.message.TcpOriginalResponseMessage;
import com.netty.server.utils.ByteUtils;
import com.netty.server.utils.MathUtil;

public class TcpResponseEncoder extends OneToOneEncoder {
	protected static Logger logger = LoggerFactory.getLogger(TcpResponseEncoder.class);
	
	private static final String MESSAGE_FORMAT_DEBUG = " TCP|A0X|%s|%s";
	private static final String MESSAGE_FORMAT_ACK   = " TCP|ACK|%s|%s|%s|%s|%s ms"; 
	
	private static final String TCP_ACK_DEBUG = "tcp.ack.debug";
	
	private ServerConfig serverConfig; 
	
	public TcpResponseEncoder() {
		super();
	}

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {

		if (!(msg instanceof TcpOriginalResponseMessage)) {
			return msg;
		}
		
		TcpOriginalResponseMessage responseMessage = (TcpOriginalResponseMessage) msg;
		
		TcpServerContext context = (TcpServerContext)responseMessage.getServerContext();

		byte[] body = new byte[] {};

		MessageLite messageBody = responseMessage.getBody();

		if (messageBody != null) {
			body = messageBody.toByteArray();
		}

		ChannelBuffer result = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, 12 + body.length);

		int ackCommandId = context.getCommandId() | responseMessage.getAckEncryptKey(); 
		
		int ackBodyLength = body.length; 
				
		result.writeInt(ackCommandId);

		result.writeInt(ackBodyLength);
		result.writeInt(context.getOriginalCharset());
		result.writeBytes(body);

		// TCP|ACK|1128238693932995|15361|14|1|2 ms
		logger.info(String.format(MESSAGE_FORMAT_ACK, context.getFlowId(), ackCommandId,ackBodyLength,context.getCharset().displayName(),MathUtil.usedTimeMS(context.getFlowId())));
		
		printfHexResponse(responseMessage, result);
		
		ctx.setAttachment(null);
		
		return result;
	}
	
	public void printfHexResponse(TcpOriginalResponseMessage responseMessage, ChannelBuffer request) {		
		if (!serverConfig.isTurnOn(TCP_ACK_DEBUG)) {
			return;
		}
		logger.info(String.format(MESSAGE_FORMAT_DEBUG, responseMessage.getFlowId(), ByteUtils.getHexString(request.array())));
	}

	public void setServerConfig(ServerConfig serverConfig) {
		this.serverConfig = serverConfig;
	}
	
}
