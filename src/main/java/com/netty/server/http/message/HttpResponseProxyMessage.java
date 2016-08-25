package com.netty.server.http.message;

import com.netty.server.utils.Constant;


public class HttpResponseProxyMessage extends HttpResponseMessage {
			
	public HttpResponseProxyMessage(String targetUrl) {
		super(HttpResponseContentType.proxy);
		this.addHeader(Constant.Http.HEADER_PROXY_LOCATION, targetUrl);
		this.setContent("proxy|"+targetUrl);
	}

}
