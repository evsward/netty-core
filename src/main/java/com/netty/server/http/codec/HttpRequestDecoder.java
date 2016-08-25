package com.netty.server.http.codec;

import org.apache.commons.lang.StringUtils;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.DefaultHttpRequest;
import org.jboss.netty.handler.codec.http.HttpMessage;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netty.server.core.support.ServerConfig;
import com.netty.server.http.HttpServerContext;
import com.netty.server.http.message.HttpOriginalRequestMessage;

public class HttpRequestDecoder extends org.jboss.netty.handler.codec.http.HttpRequestDecoder {
	protected static Logger logger = LoggerFactory.getLogger(HttpRequestDecoder.class);

	//protocol|REQ|flowId|clientIp|serverIp|cmdId|length|charset
	//HTTP|REQ|1365453134404046|127.0.0.1|172.28.19.76|/hello|UTF-8
	private static final String MESSAGE_FORMAT_REQ = "HTTP|REQ|%s|%s|%s|%s|%s"; 

	public HttpRequestDecoder() {
		super();
	}
	
	private ServerConfig serverConfig;

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer, State state) throws Exception {

		Object result = super.decode(ctx, channel, buffer, state);

		if (result instanceof HttpRequest) {
			HttpRequest request = (HttpRequest) result;
			
			HttpOriginalRequestMessage requestMessage = new HttpOriginalRequestMessage(channel, request , serverConfig);
			
			HttpServerContext context = (HttpServerContext)requestMessage.getServerContext();
			
			logger.info(String.format(MESSAGE_FORMAT_REQ, context.getFlowId(), context.getClientIp(), context.getServerIp(), context.getCharset().displayName(), context.getURI()));
			
			return requestMessage;
		}

		return result;
	}

	@Override
	protected HttpMessage createMessage(String[] initialLine) throws Exception {
		//(Request-Line): GET /abcd HTTP/1.1
		String httpMethod = initialLine[0];
		String httpURI = initialLine[1];
		String httpVersion = initialLine[2];

		if(httpMethod.startsWith("/")){
			// httpMethod ignore
			//(Request-Line): /abcd HTTP/1.1
			
			httpVersion = "HTTP/1.1";
			httpURI = httpMethod;
			httpMethod = "GET";			
		}else{
			if (!"GET".equalsIgnoreCase(httpMethod) && !"POST".equalsIgnoreCase(httpMethod)) {
				httpMethod = "GET";
			}
			
			if (StringUtils.isBlank(httpURI) || !httpURI.startsWith("/")) {
				httpURI = "/";
			}

			if (!"HTTP/1.1".equalsIgnoreCase(httpVersion) && !"HTTP/1.0".equalsIgnoreCase(httpVersion)) {
				httpVersion = "HTTP/1.1";
			}
		}

		return new DefaultHttpRequest(HttpVersion.valueOf(httpVersion), HttpMethod.valueOf(httpMethod), httpURI);

	}

	public void setServerConfig(ServerConfig serverConfig) {
		this.serverConfig = serverConfig;
	}	

}
