package com.netty.server.tcp.codec;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netty.server.core.support.ServerConfig;
import com.netty.server.tcp.TcpServerContext;
import com.netty.server.tcp.message.TcpOriginalRequestMessage;
import com.netty.server.utils.ByteUtils;

public class TcpRequestDecoder extends FrameDecoder {
	protected static Logger logger = LoggerFactory.getLogger(TcpRequestDecoder.class);

	//TCP|REQ|1365453134404046|HEX|01 3C 00 00 0E 00 00 00 01 00 00 00 0A 0A 74 65 72 72 61 6E 73 6F 66 74 10 00 
	private static final String MESSAGE_FORMAT_DEBUG = " TCP|R0X|%s|%s";
	
	//protocol|REQ|flowId|clientIp|serverIp|cmdId|length|charset
	// TCP|REQ|1365453134404046|127.0.0.1|172.28.19.76|15361|14|UTF-8
	private static final String MESSAGE_FORMAT_REQ = " TCP|REQ|%s|%s|%s|%s|%s|%s"; 
	
	private static final String TCP_REQ_DEBUG = "tcp.req.debug";
	private static final String TCP_REQ_DEBUG_INDEPTH = "tcp.req.debug.indepth";

	private ServerConfig serverConfig; 

	public TcpRequestDecoder() {
		super();
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {

		if (buffer.readableBytes() < 4) {
			debugInDepthHexRequest("<4", buffer);
			return null;
		}

		int commandId = ByteUtils.getInt(buffer.array(), 0);

		if (buffer.readableBytes() < 8) {
			debugInDepthHexRequest("<8", buffer);
			return null;
		}

		int bodyLength = ByteUtils.getInt(buffer.array(), 4);

		if (buffer.readableBytes() < 12) {
			debugInDepthHexRequest("<12", buffer);
			return null;
		}
		
		int charset = ByteUtils.getInt(buffer.array(), 8); // 0:GBK,1:UTF-8

		if (buffer.readableBytes() < 12 + bodyLength) {
			debugInDepthHexRequest("<12+len", buffer);
			return null;
		}

		long flowId = System.nanoTime();
				
		debugHexRequest(flowId, buffer);

		buffer.readerIndex(buffer.readerIndex() + 12);

		ChannelBuffer body = buffer.readBytes(bodyLength);

		TcpOriginalRequestMessage requestMessage = new TcpOriginalRequestMessage(flowId, channel, serverConfig , commandId, bodyLength, charset ,body);		
		
		TcpServerContext context = (TcpServerContext)requestMessage.getServerContext();
		
		logger.info(String.format(MESSAGE_FORMAT_REQ, context.getFlowId(), context.getClientIp(), context.getServerIp(), context.getCommandId(), context.getBodyLength(), context.getCharset().displayName()));
		
		return requestMessage;
	}

	public void debugHexRequest(long flowId, ChannelBuffer request) {
		if (!serverConfig.isTurnOn(TCP_REQ_DEBUG)) {
			return;
		}
		printfHexRequest(String.valueOf(flowId), request);
	}

	public void debugInDepthHexRequest(String key, ChannelBuffer request) {
		if (!serverConfig.isTurnOn(TCP_REQ_DEBUG_INDEPTH)) {
			return;
		}
		printfHexRequest(key, request);
	}

	public void printfHexRequest(String key, ChannelBuffer request) {
		logger.info(String.format(MESSAGE_FORMAT_DEBUG, key, ByteUtils.getHexString(request.array())));
	}

	public void setServerConfig(ServerConfig serverConfig) {
		this.serverConfig = serverConfig;
	}
	
}
