package com.netty.server.test.support.http;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netty.server.utils.HttpUtils;


public abstract class AbstractHttpTestCase {

	protected static Logger logger = LoggerFactory.getLogger(AbstractHttpTestCase.class);

	private static final String HTTP_HOST = "http://%s:%s%s";
	
	public abstract String getServerIp();
	
	public abstract int getServerPort();

	public String httpPost(String uri, Map<String, String> params) throws Exception {
		return httpSend(uri, params, null, "post");
	}
	
	public String httpGet(String uri, Map<String, String> params) throws Exception {
		return httpSend(uri, params, null, "get");
	}
	
	public String httpPost(String uri, Map<String, String> params, String charset) throws Exception {
		return httpSend(uri, params, charset, "post");
	}
	
	public String httpGet(String uri, Map<String, String> params, String charset) throws Exception {
		return httpSend(uri, params, charset, "get");
	}
	
	private String httpSend(String uri, Map<String, String> params, String charset, String method) throws Exception {

		String url = String.format(HTTP_HOST, getServerIp(), getServerPort(), uri);
		logger.info(method + " request URL:" + url);
		logger.info(method + " request URI:" + uri);

		String returnStr = StringUtils.EMPTY;
		
		if(StringUtils.isBlank(charset)){
			charset = "utf-8";
		}

		if ("get".equalsIgnoreCase(method)) {
			returnStr = HttpUtils.getUrlContent(url, params == null ? new HashMap<String, String>() : params, charset);
		} else {
			returnStr = HttpUtils.postForm(url, params == null ? new HashMap<String, String>() : params, charset);
		}

		logger.info(method + " result:" + returnStr);

		return returnStr;
	}

}
