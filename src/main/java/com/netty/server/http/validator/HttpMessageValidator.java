package com.netty.server.http.validator;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netty.server.core.support.IpWhiteListValidator;
import com.netty.server.http.message.HttpOriginalRequestMessage;

public class HttpMessageValidator extends IpWhiteListValidator<HttpOriginalRequestMessage> {

	protected static Logger logger = LoggerFactory.getLogger(HttpMessageValidator.class);

	public static final String QUERY_STRING_SPLIT = "?";

	@Override
	public String fetchBizKey(HttpOriginalRequestMessage requestMessage) {
		String uri = requestMessage.getUri();

		if (StringUtils.isBlank(uri)) {
			return "/";
		}

		if (uri.indexOf(QUERY_STRING_SPLIT) != -1) {
			return uri.substring(0, uri.indexOf(QUERY_STRING_SPLIT));
		}
		return uri;
	}

	@Override
	public String fetchRequestIp(HttpOriginalRequestMessage requestMessage) {
		return requestMessage.getServerContext().getClientIp();
	}
	
	@Override
	public boolean isExcludes(String bizKey, String requestIp){
		String nameSpace = parseRequestNameSpace(bizKey);
		
		if(EXCLUDES_WHITELIST.contains(nameSpace)){
			return true;
		}
		
		return false;
	}
	
	public static String parseRequestNameSpace(String URI){
		if(!URI.startsWith("/")){
			return "";
		}
		int last = URI.lastIndexOf("/");
		
		if(last == 0){
			return "/";
		}
		
		if(last == URI.length()){
			return "";
		}
		
		return URI.substring(0,last);
	}
	
	public static void main(String[] args) {
		String U1 = "/lkUTkh";
		String U2 = "/lkUTk1h/";
		String U3 = "/role/abc";
		String U4 = "/role/user/sdss";
		
		System.out.println(parseRequestNameSpace(U1));
		System.out.println(parseRequestNameSpace(U2));
		System.out.println(parseRequestNameSpace(U3));
		System.out.println(parseRequestNameSpace(U4));
		
	}

}
