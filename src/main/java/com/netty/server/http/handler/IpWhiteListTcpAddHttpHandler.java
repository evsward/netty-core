package com.netty.server.http.handler;

import static com.netty.server.utils.LogUtil.info;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netty.server.core.ServerContext;
import com.netty.server.core.annotation.URIBasedPolicy;
import com.netty.server.core.support.IpWhiteListValidator;
import com.netty.server.http.message.HttpRequestMessage;
import com.netty.server.http.message.HttpResponseMessage;

@URIBasedPolicy("/admin/ipWhiteList/tcpAdd")
public class IpWhiteListTcpAddHttpHandler implements HttpMessageHandler<HttpRequestMessage,HttpResponseMessage> {
 
	protected static Logger logger = LoggerFactory.getLogger(IpWhiteListTcpAddHttpHandler.class);
	
	private static final String RETURN_MESSAGE = "{\"success\":\"true\",\"message\":\"%s\"}";
	private static final String FAILED_MESSAGE = "{\"error\":\"true\",\"message\":\"need params key and ip . Usage: http://ip:port/admin/ipWhiteList/tcpAdd?key=tcp.cmd.global&ip=172.28.19.76\"}";
	private static final String INVALIDATE_MESSAGE = "{\"result\":\"error\",\"message\":\"invaliteIP %s\"}";
	
	private String managerIp = StringUtils.EMPTY;
	
	@SuppressWarnings("rawtypes")
	private IpWhiteListValidator ipWhiteListValidator;

	@Override
	public HttpResponseMessage execute(ServerContext serverContext, HttpRequestMessage requestMessage) {
		//tcp.cmd.global=192.168.1.158
		
		//tcp.cmd.global
		String bizKey = requestMessage.getParameter("key");
		
		//192.168.1.158
		String requestIp = requestMessage.getParameter("ip");
		
		if(StringUtils.isBlank(bizKey) || StringUtils.isBlank(requestIp)){
			info(logger, serverContext, FAILED_MESSAGE);
			return new HttpResponseMessage(FAILED_MESSAGE);
		}
		
		String clientIp = serverContext.getClientIp();
		
		if(managerIp.indexOf(clientIp) == -1){
			info(logger, serverContext, String.format(INVALIDATE_MESSAGE, clientIp));
			return new HttpResponseMessage(String.format(INVALIDATE_MESSAGE, clientIp));
		}
		
		StringBuffer message = new StringBuffer("\n");
		
		
		ipWhiteListValidator.dynamicAdd(bizKey, requestIp);
		
		message.append(String.format("%s|%s\n", bizKey,requestIp));
				
		return new HttpResponseMessage(String.format(RETURN_MESSAGE, message.toString()));
	}
	
	@Override
	public HttpResponseMessage defaultFailedResponse(ServerContext serverContext, Throwable throwable, String errorMessage) {		
		return HttpResponseMessage.defaultFailedResponse();
	}

	@SuppressWarnings("rawtypes")
	public void setIpWhiteListValidator(IpWhiteListValidator ipWhiteListValidator) {
		this.ipWhiteListValidator = ipWhiteListValidator;
	}	

	public void setManagerIp(String managerIp) {
		this.managerIp = managerIp;
	}
	
}
