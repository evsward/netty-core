package com.netty.server.core;

import com.netty.server.core.message.ValidateResult;

/**
 * 请求消息校验器
 * 
 * 校验RequestMessage是否合法
 *  
 */
public interface RequestValidator<R> {
	
	public ValidateResult validate(R requestMessage);
	
	RequestValidator<Object> NOOP = new RequestValidator<Object>() {
		public ValidateResult validate(Object requestMessage) {			
			return new ValidateResult(1,"NOOP Validator pass");
		}
	};
}
