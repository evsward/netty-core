package com.netty.server.core.support;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.netty.server.utils.Constant;

public class ServerConfig {
	private static Logger logger = LoggerFactory.getLogger(ServerConfig.class);

	private static final String SERVER_INFO_FORMAT = "*\t%s : %s";

	private String hostName = StringUtils.EMPTY;
	private String hostAddress = StringUtils.EMPTY;
	private String serviceName = StringUtils.EMPTY;
	private String canonicalHostName = StringUtils.EMPTY;
	
	private Map<String,Boolean> turnOnConfig = Maps.newHashMap(); 

	public void init() {

		try {
			InetAddress serverLocal = InetAddress.getLocalHost();

			this.hostName = serverLocal.getHostName();
			this.hostAddress = serverLocal.getHostAddress();
			this.canonicalHostName = serverLocal.getCanonicalHostName();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		logger.info(Constant.Str.CUTOFF_RULE);
		logger.info(String.format(SERVER_INFO_FORMAT, "ServiceName      ", serviceName));
		logger.info(String.format(SERVER_INFO_FORMAT, "HostName         ", hostName));
		logger.info(String.format(SERVER_INFO_FORMAT, "HostAddress      ", hostAddress));
		logger.info(String.format(SERVER_INFO_FORMAT, "CanonicalHostName", canonicalHostName));
		logger.info(Constant.Str.CUTOFF_RULE);
		logger.info(StringUtils.EMPTY);

	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getCanonicalHostName() {
		return this.canonicalHostName;
	}

	public String getHostAddress() {
		return this.hostAddress;
	}

	public String getHostName() {
		return this.hostName;
	}

	public String getServiceName() {
		return this.serviceName;
	}
	
	public boolean isTurnOn(String bizTurnOn) {
		
		if(turnOnConfig.get(bizTurnOn)){
			return true;
		}
		
		return false;
	}

	public Map<String, Boolean> getTurnOnConfig() {
		return turnOnConfig;
	}

	public void setTurnOnConfig(Map<String, Boolean> turnOnConfig) {
		this.turnOnConfig = turnOnConfig;
	}

}
