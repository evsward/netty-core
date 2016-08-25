package com.netty.server.core;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Map;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.util.CharsetUtil;

import com.google.common.collect.Maps;
import com.netty.server.core.support.ServerConfig;


public class ServerContext {
	
	private static final String REQ_MESSAGE_FORMAT = "%s|%s|%s|%s|%s|%s";
	
	protected long flowId;	
	protected String clientIp;
	protected String serverIp;	
	protected Charset charset = CharsetUtil.UTF_8;
	
	protected Channel channel;
	protected Map<String,String> attributes;
	
	protected String protocol = " TCP";
	
	private boolean occurException = false;
	private boolean closeFuture = false;
	
	public ServerContext(Channel channel, ServerConfig serverConfig){
		this.flowId = System.nanoTime();
		this.channel = channel;
		if(channel != null){
			this.clientIp = ((InetSocketAddress) channel.getRemoteAddress()).getAddress().getHostAddress();
		}
		if(serverConfig != null){
			this.serverIp = serverConfig.getHostAddress();
		}
	}
	
	public String toString() {
		return String.format(REQ_MESSAGE_FORMAT, protocol, flowId, clientIp, serverIp, charset);
	}
	
	public long getFlowId(){
		return flowId;
	}
	
	public String getFlowIdStr(){
		return String.valueOf(flowId);
	}
	
	public String getClientIp(){
		return clientIp;
	}
	
	public Channel getChannel(){
		return channel;
	}
	
	public String getAttribute(String name){
		if(attributes == null){
			return null;
		}		
		return attributes.get(name);
	}
	
	public void setAttribute(String name,String value){
		if(attributes == null){
			attributes = Maps.newHashMap();
		}
		attributes.put(name,value);
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public void setFlowId(long flowId) {
		this.flowId = flowId;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public Charset getCharset() {
		return charset;
	}

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	public boolean isOccurException() {
		return occurException;
	}

	public void setOccurException(boolean occurException) {
		this.occurException = occurException;
	}

	public boolean isCloseFuture() {
		return closeFuture;
	}

	public void setCloseFuture(boolean closeFuture) {
		this.closeFuture = closeFuture;
	}
	
}
