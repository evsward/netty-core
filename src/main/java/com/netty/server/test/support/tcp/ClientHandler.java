package com.netty.server.test.support.tcp;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientHandler extends SimpleChannelUpstreamHandler {

	protected static Logger logger = LoggerFactory.getLogger(ClientHandler.class);

	private BlockingQueue<ChannelBuffer> result = new LinkedBlockingQueue<ChannelBuffer>();

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {

		logger.info(">>>>>> messageReceived");

		ChannelBuffer buffer = (ChannelBuffer) e.getMessage();

		result.offer(buffer);
	}

	public ChannelBuffer getAck() throws InterruptedException {
		return result.take();
	}

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		logger.info(">>>>>> channelClosed");
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		logger.info(">>>>>> channelConnected");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		Throwable throwable = e.getCause();
		
		logger.error(">>>>>> exceptionCaught",throwable);
	}
}
