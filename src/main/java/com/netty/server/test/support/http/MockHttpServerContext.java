package com.netty.server.test.support.http;

import java.nio.charset.Charset;

import org.jboss.netty.util.CharsetUtil;

import com.netty.server.http.HttpServerContext;

public class MockHttpServerContext extends HttpServerContext {

	public MockHttpServerContext(String URI) {
		super(null, null);
		setCharset(CharsetUtil.UTF_8);		
	}
	
	public MockHttpServerContext(String URI,Charset charset) {
		super(null, null);
		setCharset(charset);
	}	

}
