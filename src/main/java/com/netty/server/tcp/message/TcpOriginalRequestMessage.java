package com.netty.server.tcp.message;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;

import com.netty.server.core.ServerContext;
import com.netty.server.core.message.Message;
import com.netty.server.core.support.ServerConfig;
import com.netty.server.tcp.TcpServerContext;

public class TcpOriginalRequestMessage implements Message {

	private ChannelBuffer body;

	private TcpServerContext serverContext;

	public TcpOriginalRequestMessage(long flowId ,Channel channel, ServerConfig serverConfig, int commandId, int bodyLength, int charset, ChannelBuffer body) {		
		initServerContext(flowId, channel, serverConfig, commandId, bodyLength, charset);
		setBody(body);		
	}

	public String toString() {
		return serverContext.toString();
	}

	public String getClientIp() {
		return serverContext.getClientIp();
	}

	public ChannelBuffer getBody() {
		return body;
	}

	public void setBody(ChannelBuffer body) {
		this.body = body;
	}

	public long getFlowId() {
		return serverContext.getFlowId();
	}

	@Override
	public ServerContext getServerContext() {
		return this.serverContext;
	}

	private void initServerContext(long flowId ,Channel channel, ServerConfig serverConfig, int commandId, int bodyLength, int charset) {		
		this.serverContext = new TcpServerContext(flowId, channel, serverConfig,  commandId,  bodyLength,  charset);
	}
	
	public int getCommandId() {
		return serverContext.getCommandId();
	}

	public int getBodyLength() {
		return serverContext.getBodyLength();
	}

	public int getCharset() {
		return serverContext.getiCharset();
	}

}
