package com.netty.server.test.support.tcp;

import static org.jboss.netty.channel.Channels.pipeline;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;

public class ClientPipelinFactory implements ChannelPipelineFactory {

	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline p = pipeline();
		p.addLast("decode", new ClientTcpRequestDecoder());
		p.addLast("handler", new ClientHandler());
		
		return p;
	}

}
