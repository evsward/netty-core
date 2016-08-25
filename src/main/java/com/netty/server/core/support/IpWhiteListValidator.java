package com.netty.server.core.support;

import java.math.BigInteger;
import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.netty.server.core.RequestValidator;
import com.netty.server.core.message.ValidateResult;
import com.netty.server.utils.Constant;
import com.netty.server.utils.IpUtil;
import com.netty.server.utils.ResourceUtil;

public abstract class IpWhiteListValidator<R> implements RequestValidator<R> {

	protected static Logger logger = LoggerFactory.getLogger(IpWhiteListValidator.class);
	
	public static final BigInteger WIDCARD_IP_ALL = new BigInteger("8888888888");

	public static final String IP_LOCALHOST = "127.0.0.1";
	public static final String IP_GLOBAL_SUFFIX = ".global";

	public Map<String, Set<BigInteger>> IP_WHITELIST = Maps.newHashMap();
	public Set<String> EXCLUDES_WHITELIST = Sets.newHashSet(); 

	private String propertiesFileName;
	private String keyPrefix;

	public void init() {
		initIpWhiteList(IP_WHITELIST);
	}

	public String reload() {

		logger.info(" reload IpWhiteList ... ");

		Map<String, Set<BigInteger>> transitionIpWhiteList = Maps.newHashMap();

		initIpWhiteList(transitionIpWhiteList);

		IP_WHITELIST = transitionIpWhiteList;

		return printfIpWhiteList(IP_WHITELIST);
	}
	
	public void dynamicAdd(String bizKey, String ipList) {

		parseIps(IP_WHITELIST, bizKey, ipList);
	}

	public String printfIpWhiteList(Map<String, Set<BigInteger>> IP_WHITELIST) {
		StringBuffer listInfo = new StringBuffer();
		logger.info(Constant.Str.CUTOFF_RULE);
		for (Entry<String, Set<BigInteger>> entry : IP_WHITELIST.entrySet()) {
			listInfo.append("\n");
			String _whlist = String.format("* IpWhiteList \t %s \t IpSize:%s ", entry.getKey(), (entry.getValue() == null) ? 0 : entry.getValue().size());
			logger.info(_whlist);
			listInfo.append(_whlist);
		}		
		return listInfo.toString();
	}
	
	public String printfCurrentIpWhiteList() {
		return printfIpWhiteList(IP_WHITELIST);
	}

	public void initIpWhiteList(Map<String, Set<BigInteger>> ipWhiteList) {
		try {
			ResourceBundle bundle = ResourceUtil.getBundle(propertiesFileName);

			Enumeration<String> keys = bundle.getKeys();

			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				String value = bundle.getString(key);
				
				if (key.startsWith(Constant.Str.LEFT_BRACKET) && key.indexOf(Constant.Str.RIGHT_BRACKET) != -1 && !key.endsWith(Constant.Str.RIGHT_BRACKET)) {
					/*
					 *  config1:
							tcp.cmd.501=172.28.19.1
							[2]tcp.cmd.501=172.28.19.2
							[3]tcp.cmd.501=172.28.19.3
							
						config2:
							tcp.cmd.501=172.28.19.1,172.28.19.2,172.28.19.3
						
						config1 the same to config2 
					*/
					key = key.substring(key.indexOf(Constant.Str.RIGHT_BRACKET)+1, key.length());
				}
				
				if (!key.startsWith(keyPrefix)) {
					continue;
				}
				parseIps(ipWhiteList, key, value);
			}
		} catch (java.util.MissingResourceException e) {
			logger.error(String.format("please add IP whitelist config file %s.properties ," +
					"content like %s.global=*", propertiesFileName, keyPrefix),e);
		}
	}

	@Override
	public ValidateResult validate(R requestMessage) {

		String bizKey = fetchBizKey(requestMessage);

		String requestIp = fetchRequestIp(requestMessage);

		if (IP_LOCALHOST.equals(requestIp)) {
			//logger.info(String.format(" IP 127.0.0.1 visit [%s][%s]", keyPrefix, bizKey));
			return new ValidateResult(1);
		}

		if (doValidate(bizKey, requestIp)) {
			//logger.info(String.format(" whiteList IP %s visit [%s][%s]", requestIp, keyPrefix, bizKey));
			return new ValidateResult(1);
		}

		//logger.warn(String.format(" invalidate IP %s visit [%s][%s]", requestIp, keyPrefix, bizKey));
		return new ValidateResult(0,String.format("Forbidden IP %s", requestIp));
	}

	public abstract String fetchBizKey(R requestMessage);

	public abstract String fetchRequestIp(R requestMessage);
	
	public abstract boolean isExcludes(String bizKey, String requestIp);

	public boolean doValidate(String bizKey, String requestIp) {

		if (!IpUtil.isIp(requestIp)) {
			// 非法ip直接返回失败
			return false;
		}
		
		//对于EXCLUDES无需校验，直接返回成功
		if(isExcludes(bizKey, requestIp)){
			// 无需校验，直接返回成功
			return true;
		}

		BigInteger requestIpNumber = IpUtil.toNumber(requestIp);

		Set<BigInteger> ipSet = IP_WHITELIST.get(keyPrefix + IP_GLOBAL_SUFFIX);

		// 校验全局IP白名单中是否通过，如果通过直接返回成功，如果未通过还需校验各个业务自身的IP白名单
		boolean validateResult = doValidate(ipSet, requestIpNumber);

		if (validateResult) {
			return true;
		}

		// 校验业务IP白名单
		ipSet = IP_WHITELIST.get(keyPrefix + Constant.Str.PERIOD + bizKey);

		return doValidate(ipSet, requestIpNumber);
	}

	public boolean doValidate(Set<BigInteger> ipSet, BigInteger requestIpNumber) {
		if (ipSet == null) {
			return false;
		}

		if (ipSet.contains(WIDCARD_IP_ALL)) {
			// 说明配置文件中 tcp.cmd.global=*
			// 所有请求都允许,直接返回成功
			return true;
		}

		if (ipSet.contains(requestIpNumber)) {
			// 合法请求ip,返回成功
			return true;
		}

		return false;
	}

	public void parseIps(Map<String, Set<BigInteger>> ipWhiteList, String key, String ipsValue) {
		boolean needSave = false;
		Set<BigInteger> _IpSet = ipWhiteList.get(key);

		if (_IpSet == null) {
			_IpSet = Sets.newHashSet();
			needSave = true;
		}

		// eg. ipsValue 172.28.19.79-90,192.168.1.158
		String[] ipArray = StringUtils.split(ipsValue, Constant.Str.COMMA);

		if (ipArray == null || ipArray.length == 0) {
			return;
		}

		for (String _ip : ipArray) {
			if (StringUtils.isBlank(_ip)) {
				continue;
			}
			parseIp(_IpSet, _ip.trim());
		}

		if (needSave) {
			ipWhiteList.put(key, _IpSet);
		}
	}

	public void parseIp(Set<BigInteger> ipSet, String ipValue) {

		if (IpUtil.isIp(ipValue)) {
			ipSet.add(IpUtil.toNumber(ipValue));
		} else {
			if (Constant.Str.WIDCARD.equals(ipValue)) {
				// 1）等于通配符 *
				ipSet.add(WIDCARD_IP_ALL);

			} else if (ipValue.endsWith(Constant.Str.PERIOD_WIDCARD)) {
				// 2）包含通配符 172.28.19.*
				parseWidcardIp(ipSet, ipValue);

			} else if (ipValue.indexOf(Constant.Str.MINUS) != -1) {
				// 3）包含区间 172.28.19.79-90
				parseRangeIp(ipSet, ipValue);

			} else {
				logger.info("ignore ip:" + ipValue);
			}

		}

	}

	// 包含区间 172.28.19.79-90
	// 约定：只支持最后一位带有 区间符号 -
	public int parseRangeIp(Set<BigInteger> ipSet, String ipValue) {

		// 约定：区间和通配符不能混合使用
		if (ipValue.indexOf(Constant.Str.WIDCARD) != -1) {
			logger.info("ignore ip in Range:" + ipValue);
			return 0;
		}

		String startIP = ipValue.substring(0, ipValue.indexOf(Constant.Str.MINUS));

		if (!IpUtil.isIp(startIP)) {
			logger.info("ignore start ip:" + ipValue);
			return 0;
		}

		String endIP = ipValue.substring(0, ipValue.lastIndexOf(Constant.Str.PERIOD) + 1)
				+ ipValue.substring(ipValue.indexOf(Constant.Str.MINUS) + 1);

		if (!IpUtil.isIp(endIP)) {
			logger.info("ignore end ip:" + ipValue);
			return 0;
		}

		for (long i = IpUtil.toNumber(startIP).longValue(); i <= IpUtil.toNumber(endIP).longValue(); i++) {
			ipSet.add(BigInteger.valueOf(i));
		}

		return ipSet.size();
	}

	// 包含通配符，例如： 172.28.19.* 表示 172.28.19.1 至 172.28.19.255
	// 约定：只支持最后一位为通配符*
	public int parseWidcardIp(Set<BigInteger> ipSet, String ipValue) {

		// 约定：区间和通配符不能混合使用,即包含 * 就不能包含 -
		if (ipValue.indexOf(Constant.Str.MINUS) != -1) {
			logger.info("ignore ip in Widcard:" + ipValue);
			return 0;
		}

		String startIP = ipValue.substring(0, ipValue.length() - 1) + "0";

		if (!IpUtil.isIp(startIP)) {
			logger.info("ignore start ip:" + ipValue);
			return 0;
		}

		String endIP = ipValue.substring(0, ipValue.length() - 1) + "255";

		if (!IpUtil.isIp(endIP)) {
			logger.info("ignore end ip:" + ipValue);
			return 0;
		}

		for (long i = IpUtil.toNumber(startIP).longValue(); i <= IpUtil.toNumber(endIP).longValue(); i++) {
			ipSet.add(BigInteger.valueOf(i));
		}

		return ipSet.size();
	}

	public String getPropertiesFileName() {
		return propertiesFileName;
	}

	public void setPropertiesFileName(String propertiesFileName) {
		if (propertiesFileName.endsWith(".properties")) {
			propertiesFileName = propertiesFileName.substring(0, propertiesFileName.lastIndexOf(".properties"));
		}
		this.propertiesFileName = propertiesFileName;
	}

	public String getKeyPrefix() {
		return keyPrefix;
	}

	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	public void setExcludesList(Set<String> excludesList) {
		this.EXCLUDES_WHITELIST = excludesList;
	}
	
}
