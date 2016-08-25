package com.netty.server.tcp;

import java.nio.charset.Charset;

import org.jboss.netty.channel.Channel;

import com.netty.server.core.ServerContext;
import com.netty.server.core.support.ServerConfig;

public class TcpServerContext extends ServerContext {
	
	// TCP|101124404249287|172.28.19.76|501|5|1
	private static final String REQ_MESSAGE_FORMAT = " TCP|%s|%s|%s|%s|%s";
	
	private int commandId;
	private int bodyLength;
	private int iCharset = 0; // 0:GBK,1:UTF-8
	private int originalCharset = 0; //原始的请求中check的值，C++平台发送的请求中此值不是表示编码，ACK时需要将此值原封不动的返回给C++客户端

	public TcpServerContext(long flowId, Channel channel, ServerConfig serverConfig, int commandId, int bodyLength, int charset) {
		super(channel, serverConfig);
		this.setFlowId(flowId);
		this.commandId = commandId;
		this.bodyLength = bodyLength;	
		this.originalCharset = charset;
		
		if (commandId == 2001 || commandId == 2002 || commandId == 2003 || commandId == 2101 || commandId == 2102) {
			this.iCharset = 1;
		} else {
    		//未显式指定为UTF-8，都视为GBK
    		if (charset == 1) {
    			this.iCharset = 1;
    		}else{
    			this.iCharset = 0;
    			setCharset(Charset.forName("GBK"));
    		}
		}
	}
	
	public String toString() {
		return String.format(REQ_MESSAGE_FORMAT, this.getFlowId(), this.getClientIp(), commandId, bodyLength, iCharset);
	}
	
	public int getCommandId() {
		return commandId;
	}

	public void setCommandId(int commandId) {
		this.commandId = commandId;
	}

	public int getBodyLength() {
		return bodyLength;
	}

	public void setBodyLength(int bodyLength) {
		this.bodyLength = bodyLength;
	}

	public int getiCharset() {
		return iCharset;
	}

	public void setiCharset(int iCharset) {
		this.iCharset = iCharset;
	}

	public int getOriginalCharset() {
		return originalCharset;
	}
	
}
