package com.netty.server.test.support.tcp;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netty.server.utils.ByteUtils;

public class ClientTcpRequestDecoder extends FrameDecoder {
	protected static Logger logger = LoggerFactory.getLogger(ClientTcpRequestDecoder.class);

	private static final String MESSAGE_DEBUG_FORMAT = " TCP|ACK|%s|HEX|%s";
	private static final String MESSAGE_DETAIL_DEBUG_FORMAT = "CMDID|%s|LEN|%s|CHECK|%s";

	public ClientTcpRequestDecoder() {
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

		int check = ByteUtils.getInt(buffer.array(), 8);

		if (buffer.readableBytes() < 12 + bodyLength) {
			debugInDepthHexRequest("<12+len", buffer);
			return null;
		}

		buffer.readerIndex(0);

		ChannelBuffer body = buffer.readBytes(buffer.readableBytes());

		long flowId = System.nanoTime();

		debugHexRequest(flowId, buffer);

		logger.info(String.format(MESSAGE_DETAIL_DEBUG_FORMAT, commandId, bodyLength, check));

		return body;
	}

	public void debugHexRequest(long flowId, ChannelBuffer request) {

		printfHexRequest(String.valueOf(flowId), request);
	}

	public void debugInDepthHexRequest(String key, ChannelBuffer request) {

		printfHexRequest(key, request);
	}

	public void printfHexRequest(String key, ChannelBuffer request) {
		logger.info(String.format(MESSAGE_DEBUG_FORMAT, key, ByteUtils.getHexString(request.array())));
	}

}
