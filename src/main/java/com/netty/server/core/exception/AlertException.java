package com.netty.server.core.exception;

import com.netty.server.utils.Constant;

/**
 * 系统错误信息
 *  
 */
public class AlertException extends ServerException{
	
	private static final long serialVersionUID = -8693198223791347135L;
	
	private int alertCode = Constant.ErrorCode.UNKNOWN_ERROR;
	private String alertMessage;
	
	public AlertException(String alertMessage) {
		super(alertMessage);
		this.alertMessage = alertMessage;
	}

	public AlertException(int alertCode, String alertMessage) {
		super(alertMessage);
		this.alertCode = alertCode;
		this.alertMessage = alertMessage;
	}

	public int getAlertCode() {
		return alertCode;
	}

	public void setAlertCode(int alertCode) {
		this.alertCode = alertCode;
	}

	public String getAlertMessage() {
		return alertMessage;
	}

	public void setAlertMessage(String alertMessage) {
		this.alertMessage = alertMessage;
	}
	
	
}
