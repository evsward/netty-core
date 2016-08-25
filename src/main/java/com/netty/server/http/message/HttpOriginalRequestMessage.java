package com.netty.server.http.message;

import java.nio.charset.Charset;

import org.apache.commons.lang.StringUtils;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.util.CharsetUtil;

import com.netty.server.core.ServerContext;
import com.netty.server.core.message.Message;
import com.netty.server.core.support.ServerConfig;
import com.netty.server.http.HttpServerContext;

public class HttpOriginalRequestMessage implements Message {
	
	private static final String QUERY_STRING_SPLIT = "?";
	
	private HttpRequest httpRequest; 
	
	private HttpServerContext serverContext;

    public HttpOriginalRequestMessage(Channel channel, HttpRequest httpRequest, ServerConfig serverConfig) {
        this.httpRequest = httpRequest;        
        initServerContext(channel, serverConfig);
    }

	
	public void initServerContext(Channel channel, ServerConfig serverConfig) {
		if(serverContext != null){
			return;
		}
		serverContext = new HttpServerContext(channel, serverConfig);
		serverContext.setCharset(initCharset(httpRequest)); 
		serverContext.setURI(getShortUri());
	}

	@Override
	public ServerContext getServerContext() {
		return serverContext;
	}
	
	@Override
	public Object getBody() {
		return httpRequest;
	}
	
    
    public Charset initCharset(HttpRequest httpRequest){
    	//判断请求参数中是否包含charset参数    	
    	String requestUri = httpRequest.getUri();
    	String requestContent = new String(httpRequest.getContent().array());
    	
    	String queryString = StringUtils.EMPTY;
    	
    	int splitIndex = requestUri.indexOf(QUERY_STRING_SPLIT);
    	
    	//uri中包含 ?
    	if(splitIndex != -1){    		
    		queryString = requestUri.substring(splitIndex+1);
    		Charset queryCharset = parseCharset(queryString);
    		if(queryCharset != null){
    			return queryCharset;
    		}
    	}
    	
    	//content中包含 charset
    	if (StringUtils.isNotBlank(requestContent)) {
    		Charset contentCharset = parseCharset(requestContent);	
    		if(contentCharset != null){
    			return contentCharset;
    		}
		}
		return CharsetUtil.UTF_8;
    }
    
    public Charset parseCharset(String charsetParam){
    	
    	charsetParam = charsetParam.toLowerCase();
    	
    	int charsetIndex = charsetParam.indexOf("charset=");
    	
    	if(charsetIndex < 0){ 
    		return null;
    	}
		
    	String[] params = StringUtils.split(charsetParam, "&");
    	
    	for (String param : params) {
    		if(param.startsWith("charset=") && param.length() > 8){
    			return fetchCharset(param.substring(param.indexOf("=")+1)); 
    		}
		}
    	
    	return null;
    }
    
    public Charset fetchCharset(String charsetStr){
    	
    	if("gbk".equalsIgnoreCase(charsetStr)){
    		return Charset.forName("GBK");
    	}
    	return CharsetUtil.UTF_8;
    }

	public long getFlowId() {
		return serverContext.getFlowId();
	}
	
	public String getUri(){
		return httpRequest.getUri();
	}
	
	public String getShortUri(){
		String uri = httpRequest.getUri();
		if(StringUtils.isNotBlank(uri) && uri.indexOf(QUERY_STRING_SPLIT) != -1){
			return uri.substring(0, uri.indexOf(QUERY_STRING_SPLIT));
		}
		return httpRequest.getUri();
	}
	
	public String getHeader(String name){
		return httpRequest.getHeader(name);
	}
			
	public HttpRequest getOriginalRequest() {
		return httpRequest;
	}	

	public String getClientIp() {
		return serverContext.getClientIp();
	}
	
	public Charset getCharset() {
		return serverContext.getCharset();
	}


}
