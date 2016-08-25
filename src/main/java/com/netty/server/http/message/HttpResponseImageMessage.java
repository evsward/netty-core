package com.netty.server.http.message;


public class HttpResponseImageMessage extends HttpResponseMessage {
	
	private String type = "jpeg"; 
	private byte[] contentBytes; 
	
	public HttpResponseImageMessage() {
		super();
		setContentType(HttpResponseContentType.image);
	}
	
	public HttpResponseImageMessage(byte[] contentByte) {
		this.contentBytes = contentByte;
		setContentType(HttpResponseContentType.image);
	}
	
	public HttpResponseImageMessage(byte[] contentByte, String type) {
		this.type = type;
		this.contentBytes = contentByte;
		setContentType(HttpResponseContentType.image);
	}

	public byte[] getContentBytes() {
		return contentBytes;
	}

	public void setContentBytes(byte[] contentBytes) {
		this.contentBytes = contentBytes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}	

}
