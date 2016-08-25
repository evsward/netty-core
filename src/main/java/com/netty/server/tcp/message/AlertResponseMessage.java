package com.netty.server.tcp.message;

import com.google.protobuf.MessageLite;
import com.netty.server.core.ServerContext;
import com.netty.server.core.exception.AlertException;
import com.netty.server.core.exception.ValidateException;
import com.netty.server.core.message.ValidateResult;
import com.netty.server.tcp.TcpServerContext;
import com.netty.server.tcp.message.SystemMessage.AlertMessage;
import com.netty.server.utils.Constant;


public class AlertResponseMessage extends TcpOriginalResponseMessage {
	
	public AlertResponseMessage(ServerContext serverContext, MessageLite messageList) {
		super((TcpServerContext)serverContext, messageList);
	}
	
	public AlertResponseMessage(ServerContext serverContext, Throwable throwable) {
		
		super((TcpServerContext)serverContext);
		
		setCommandId(Constant.System.ALERT_ACK);
		
		AlertMessage.Builder alert = AlertMessage.newBuilder();
		alert.setCode(Constant.ErrorCode.UNKNOWN_ERROR);
		
		if(throwable != null && throwable.getMessage() != null){
			alert.setRemark(throwable.getMessage());	
		}				
		setBody(alert.build());
	}

	public AlertResponseMessage(ServerContext serverContext, AlertException alertException) {
		
		super((TcpServerContext)serverContext);
		
		setCommandId(Constant.System.ALERT_ACK);
		
		AlertMessage.Builder alert = AlertMessage.newBuilder();
		alert.setCode(alertException.getAlertCode());
		alert.setRemark(alertException.getMessage());

		setBody(alert.build());
	}
	
	public AlertResponseMessage(ServerContext serverContext, ValidateException alertException) {
				
		super((TcpServerContext)serverContext);
		
		setCommandId(Constant.System.ALERT_ACK);
		
		ValidateResult validateResult = alertException.getValidateResult();
		
		AlertMessage.Builder alert = AlertMessage.newBuilder();
		
		if(validateResult != null){
			alert.setCode(validateResult.getResultCode());
			alert.setRemark(validateResult.getResultMessage());	
		}else{
			alert.setCode(Constant.ErrorCode.UNKNOWN_ERROR);
			alert.setRemark(alertException.getMessage());
		}

		setBody(alert.build());
	}

}
