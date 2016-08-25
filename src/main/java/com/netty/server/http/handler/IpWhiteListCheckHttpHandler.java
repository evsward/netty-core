package com.netty.server.http.handler;

import static com.netty.server.utils.LogUtil.info;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.netty.server.core.ServerContext;
import com.netty.server.core.annotation.URIBasedPolicy;
import com.netty.server.core.support.IpWhiteListValidator;
import com.netty.server.http.message.HttpRequestMessage;
import com.netty.server.http.message.HttpResponseMessage;

@URIBasedPolicy("/admin/ipWhiteList/check")
public class IpWhiteListCheckHttpHandler implements HttpMessageHandler<HttpRequestMessage,HttpResponseMessage> {
 
	protected static Logger logger = LoggerFactory.getLogger(IpWhiteListCheckHttpHandler.class);
	
	private static final String RETURN_MESSAGE = "{\"success\":\"true\",\"message\":\"%s\"}";
	private static final String FAILED_MESSAGE = "{\"error\":\"true\",\"message\":\"need params cmd and ip . Usage: http://ip:port/admin/ipWhiteList/check?cmd=517&ip=172.28.19.76\"}";
	
	@SuppressWarnings("rawtypes")
	private List<IpWhiteListValidator> ipWhiteListValidators = Lists.newArrayList();

	@SuppressWarnings("rawtypes")
	@Override
	public HttpResponseMessage execute(ServerContext serverContext, HttpRequestMessage requestMessage) {
	
		String bizKey = requestMessage.getParameter("cmd");
		String requestIp = requestMessage.getParameter("ip");
		
		if(StringUtils.isBlank(bizKey) || StringUtils.isBlank(requestIp)){
			info(logger, serverContext, FAILED_MESSAGE);
			return new HttpResponseMessage(FAILED_MESSAGE);
		}
		
		StringBuffer message = new StringBuffer("\n");
		
		for (IpWhiteListValidator validator : ipWhiteListValidators) {
			boolean checkResult = validator.doValidate(bizKey, requestIp);
			message.append(String.format("%s|%s|%s|%s\n", validator.getKeyPrefix(),bizKey,requestIp,checkResult));
		}
		
		return new HttpResponseMessage(String.format(RETURN_MESSAGE, message.toString()));
	}
	
	@Override
	public HttpResponseMessage defaultFailedResponse(ServerContext serverContext, Throwable throwable, String errorMessage) {		
		return HttpResponseMessage.defaultFailedResponse();
	}	

	@SuppressWarnings("rawtypes")
	public void setIpWhiteListValidators(List<IpWhiteListValidator> ipWhiteListValidators) {
		this.ipWhiteListValidators = ipWhiteListValidators;
	}	
	
}
