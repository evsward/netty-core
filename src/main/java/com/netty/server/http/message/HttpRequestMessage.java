package com.netty.server.http.message;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;

/**
 * HTTP请求消息
 * 
 */
public class HttpRequestMessage {

	public static final String QUERY_STRING_SPLIT = "?";

	private HttpOriginalRequestMessage request;

	private QueryStringDecoder queryStringDecoder;

	public HttpRequestMessage(HttpOriginalRequestMessage request) {
		this.request = request;
		initQueryStringDecoder(request, request.getCharset());
	}

	public HttpRequestMessage(HttpOriginalRequestMessage request, Charset charset) {
		this.request = request;		
		initQueryStringDecoder(request, charset);
	}
	
	public void initQueryStringDecoder(HttpOriginalRequestMessage request, Charset charset){
		String requestContent = new String(request.getOriginalRequest().getContent().array());

		if (StringUtils.isNotBlank(requestContent)) {
			this.queryStringDecoder = new QueryStringDecoder(QUERY_STRING_SPLIT + requestContent, charset);
		} else {
			this.queryStringDecoder = new QueryStringDecoder(request.getUri(), charset);
		}
	}

	public String getUri() {
		return request.getUri();
	}

	public Map<String, List<String>> getParameters() {
		return queryStringDecoder.getParameters();
	}

	public List<String> getParameterValue(String paramName) {
		return getParameters().get(paramName);
	}

	public String getParameter(String paramName) {
		List<String> paramValue = getParameters().get(paramName);
		if (paramValue == null || paramValue.isEmpty()) {
			return null;
		}
		return paramValue.get(0);
	}

	public HttpOriginalRequestMessage getRequest() {
		return request;
	}

	public QueryStringDecoder getQueryStringDecoder() {
		return queryStringDecoder;
	}

	@Override
	public String toString() {
		return request.toString();
	}

}
