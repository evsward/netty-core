package com.netty.server.http.handler;

import static com.netty.server.utils.LogUtil.info;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netty.server.core.ServerContext;
import com.netty.server.core.annotation.URIBasedPolicy;
import com.netty.server.http.message.HttpRequestMessage;
import com.netty.server.http.message.HttpResponseMessage;
import com.netty.server.utils.ResourceUtil;

@URIBasedPolicy("/admin/ipWhiteList/refresh")
public class IpWhiteListRefreshHttpHandler implements HttpMessageHandler<HttpRequestMessage, HttpResponseMessage> {

	protected static Logger logger = LoggerFactory.getLogger(IpWhiteListRefreshHttpHandler.class);

	private static final String RETURN_MESSAGE = "{\"success\":\"true\",\"message\":\"%s\"}";
	private static final String INVALIDATE_MESSAGE = "{\"result\":\"error\",\"message\":\"invaliteIP %s\"}";

	private String managerIp = StringUtils.EMPTY;
	
	@Override
	public HttpResponseMessage execute(ServerContext serverContext, HttpRequestMessage requestMessage) {
		
		String clientIp = serverContext.getClientIp();
		
		if(managerIp.indexOf(clientIp) == -1){
			info(logger, serverContext, String.format(INVALIDATE_MESSAGE, clientIp));
			return new HttpResponseMessage(String.format(INVALIDATE_MESSAGE, clientIp));
		}
		
		StringBuffer message = new StringBuffer();

		message.append("Refreshing ipWhiteList , Effect in " + (ResourceUtil.getActiveTime() / 1000) + " s , " + new Date());

		return new HttpResponseMessage(String.format(RETURN_MESSAGE, message.toString()));
	}
	
	@Override
	public HttpResponseMessage defaultFailedResponse(ServerContext serverContext, Throwable throwable, String errorMessage) {		
		return HttpResponseMessage.defaultFailedResponse();
	}

	public void setManagerIp(String managerIp) {
		this.managerIp = managerIp;
	}

}
