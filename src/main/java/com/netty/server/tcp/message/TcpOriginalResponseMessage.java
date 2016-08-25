package com.netty.server.tcp.message;

import org.apache.commons.lang.StringUtils;

import com.google.protobuf.MessageLite;
import com.netty.server.core.ServerContext;
import com.netty.server.core.message.Message;
import com.netty.server.tcp.TcpServerContext;
import com.netty.server.utils.Constant;

public class TcpOriginalResponseMessage implements Message {

	private TcpServerContext serverContext;
	
	private MessageLite body;

	private int ackEncryptKey = Constant.System.GLID_ACK;
		
	@Override
	public ServerContext getServerContext() {
		return this.serverContext;
	}
		
	public TcpOriginalResponseMessage(TcpServerContext serverContext) {
		this.serverContext = serverContext;
	}
	
	public TcpOriginalResponseMessage(TcpServerContext serverContext, MessageLite messageList) {
		this.serverContext = serverContext;
		this.body = messageList;
	}

	public String toString() {
		if(body == null){
			return StringUtils.EMPTY;
		}
		return body.toString();
	}

	public int getCommandId() {
		return this.serverContext.getCommandId();
	}

	public void setCommandId(int commandId){
		this.serverContext.setCommandId(commandId);
	}
	
	public int getCharset() {
		return this.serverContext.getiCharset();
	}

	public MessageLite getBody() {
		return body;
	}

	public void setBody(MessageLite body) {
		this.body = body;
	}

	public long getFlowId() {
		return this.serverContext.getFlowId();
	}

	public int getAckEncryptKey() {
		return ackEncryptKey;
	}

	public void setAckEncryptKey(int ackEncryptKey) {
		this.ackEncryptKey = ackEncryptKey;
	}

}
