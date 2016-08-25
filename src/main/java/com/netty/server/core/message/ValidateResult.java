package com.netty.server.core.message;

import org.apache.commons.lang.StringUtils;

public class ValidateResult {

	private Integer resultCode = 0;		
	private String resultMessage = StringUtils.EMPTY;
	
	public ValidateResult(){
		super();
	}
	
	public static ValidateResult successInstance(){
		return new ValidateResult(1);
	}
	
	public static ValidateResult failureInstance(){
		return new ValidateResult(0);
	}
	
	public static ValidateResult failureInstance(String resultMessage){
		return new ValidateResult(0,resultMessage);
	}
	
	public ValidateResult(int resultCode){
		this.resultCode = resultCode;
	}
	
	public ValidateResult(int resultCode,String resultMessage){
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
	}
	
	public boolean isPass(){
		if(resultCode == 1){
			return true;
		}
		return false;
	}
	
	public boolean notPass(){
		return !isPass();
	}

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}


}
