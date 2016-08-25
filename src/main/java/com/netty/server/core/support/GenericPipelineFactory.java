package com.netty.server.core.support;

import static org.jboss.netty.channel.Channels.pipeline;

import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.execution.ExecutionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通用ChannelPipelineFactory
 * 
 */
public class GenericPipelineFactory implements ChannelPipelineFactory {
	
	protected static Logger logger = LoggerFactory.getLogger(GenericPipelineFactory.class);
		
	private boolean readTimeoutTurnon = false;
	
	private ExecutionHandler executor;

	private ChannelHandler handler;
	private ChannelHandler decoder;	
	private ChannelHandler encoder;	
		
	private ChannelHandler  readTimeoutHandler;
		
	public ChannelPipeline getPipeline() throws Exception {		

		ChannelPipeline pipeline = pipeline();
		
		pipeline.addLast("execution", executor);				//(1) Executor
		
		if(isReadTimeoutTurnon()){			
			pipeline.addLast("readTimeout", getReadTimeoutHandler());//(2) readTimeoutHandler
		}
		
		pipeline.addLast("decoder", getDecoder()); 				//(3) ChannelBuffer    == > RequestMessage
		pipeline.addLast("encoder", getEncoder()); 				//(5) ResponseMessage   == > ChannelBuffer		

		
		pipeline.addLast("handler", handler);					//(4) RequestMessage    == > ResponseMessage
		
		return pipeline;
	}

	public boolean isReadTimeoutTurnon() {
		return readTimeoutTurnon;
	}

	public void setReadTimeoutTurnon(boolean readTimeoutTurnon) {
		this.readTimeoutTurnon = readTimeoutTurnon;
	}

	public ChannelHandler getHandler() {
		return handler;
	}

	public void setHandler(ChannelHandler handler) {
		this.handler = handler;
	}

	public ChannelHandler getDecoder() {
		return decoder;
	}

	public void setDecoder(ChannelHandler decoder) {
		this.decoder = decoder;
	}

	public ChannelHandler getEncoder() {
		return encoder;
	}

	public void setEncoder(ChannelHandler encoder) {
		this.encoder = encoder;
	}

	public ExecutionHandler getExecutor() {
		return executor;
	}

	public void setExecutor(ExecutionHandler executor) {
		this.executor = executor;
	}

	public ChannelHandler getReadTimeoutHandler() {
		return readTimeoutHandler;
	}

	public void setReadTimeoutHandler(ChannelHandler readTimeoutHandler) {
		this.readTimeoutHandler = readTimeoutHandler;
	}
	
		
}
