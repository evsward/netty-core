package com.netty.server.core.exception;

import com.netty.server.core.message.ValidateResult;

/**
 * 系统错误信息
 * 
 */
public class ValidateException extends ServerException {

	private static final long serialVersionUID = -8693198223791347135L;

	private ValidateResult validateResult;

	public ValidateException(ValidateResult validateResult) {
		super(validateResult.getResultMessage());	
	}

	public ValidateResult getValidateResult() {
		return validateResult;
	}

	public void setValidateResult(ValidateResult validateResult) {
		this.validateResult = validateResult;
	}

}
