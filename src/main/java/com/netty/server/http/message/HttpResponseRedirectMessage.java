package com.netty.server.http.message;

import com.netty.server.utils.Constant;


public class HttpResponseRedirectMessage extends HttpResponseMessage {
			
	public HttpResponseRedirectMessage(String targetUrl) {
		super(HttpResponseContentType.redirect);
		this.addHeader(Constant.Http.HEADER_LOCATION, targetUrl);
		this.setContent("302|"+targetUrl);
	}
	
	public HttpResponseRedirectMessage(String targetUrl, int status) {
		super(HttpResponseContentType.redirect);
		this.addHeader(Constant.Http.HEADER_LOCATION, targetUrl);
		this.setStatus(status);
		this.setContent(status+"|"+targetUrl);
	}

}
