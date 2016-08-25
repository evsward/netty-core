package com.netty.server.http;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.netty.server.core.ServerContext;
import com.netty.server.core.annotation.URIBasedPolicy;
import com.netty.server.core.support.HandlerConfig;
import com.netty.server.http.handler.HttpMessageHandler;
import com.netty.server.http.message.HttpOriginalRequestMessage;
import com.netty.server.utils.Constant;


public class HttpControlerConfig implements ApplicationContextAware {

	protected static Logger logger = LoggerFactory.getLogger(HttpControlerConfig.class);
	
	public static final Map<String, HttpMessageHandler<?, ?>> URI_HANDLER_MAPPING = Maps.newHashMap();
	
	public static final List<HandlerConfig> handlerConfigList = Lists.newArrayList();	

	private ApplicationContext applicationContext;

	private HttpMessageHandler<?, ?> defaultHttpHandler;

	public HttpMessageHandler<?, ?> getMessageHandler(String URI) {
		
		HttpMessageHandler<?, ?> handler = URI_HANDLER_MAPPING.get(URI);
		
		if(handler != null){
			return handler;
		}
		
		return defaultHttpHandler;
	}

	protected void init() {
		initMessageHandlerConfig();
		
		printConfigInfo();
	}
	
	public void printConfigInfo(){
		if(handlerConfigList == null || handlerConfigList.isEmpty()){
			return;
		}
		Collections.sort(handlerConfigList, new Comparator<HandlerConfig>(){  
    	    public int compare(HandlerConfig config1, HandlerConfig config2) {  
    	        return config1.getBizKey().compareTo(config2.getBizKey());  
    	    }  
    	});
		
		for (HandlerConfig config : handlerConfigList) {			
			logger.info(String.format("* %-35s\t%-30s\t%-30s", config.getBizKey(), config.getHandler(), config.getRequestType()));
		}
	}

	public void initMessageHandlerConfig() {
		@SuppressWarnings("rawtypes")
		Map<String, HttpMessageHandler> allHandlers = applicationContext.getBeansOfType(HttpMessageHandler.class);
		
		logger.info(Constant.Str.CUTOFF_RULE);
		
		HandlerConfig handlerConfig = null; 
		
		for (String key : allHandlers.keySet()) {
			
			HttpMessageHandler<?, ?> messageHandler = allHandlers.get(key);
		
			//URIBasedPolicy uriAnnotation = messageHandler.getClass().getAnnotation(URIBasedPolicy.class);			
			URIBasedPolicy uriAnnotation = applicationContext.findAnnotationOnBean(key, URIBasedPolicy.class);

			if (uriAnnotation == null) {
				continue; 
			}

			String URI = uriAnnotation.value();

			for (Method method : messageHandler.getClass().getMethods()) {
				//public BA execute(ServerContext serverContext, BR requestMessage);
				
				Class<?>[] params = method.getParameterTypes();

				if (!"execute".equals(method.getName()) || params.length != 2) {
					continue;
				}

				if (Arrays.equals(new Class<?>[] {ServerContext.class, HttpOriginalRequestMessage.class }, method.getParameterTypes())) {
					continue;
				}
				
				if (Arrays.equals(new Class<?>[] {ServerContext.class, Object.class }, method.getParameterTypes())) {
					continue;
				}

				if (URI_HANDLER_MAPPING.containsKey(URI)) {
					logger.error("* URI exists,ignor:" + URI); 
					continue;
				}

				URI_HANDLER_MAPPING.put(URI, messageHandler);
				Class<?> clazz = method.getParameterTypes()[1];

				handlerConfig = new HandlerConfig(URI,key,clazz.getSimpleName());				
				handlerConfigList.add(handlerConfig);
			}
		}
		

		if (URI_HANDLER_MAPPING.size() == 0) {
			logger.warn("* URI_HANDLER_MAPPING is null");
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void setDefaultHttpHandler(HttpMessageHandler<?, ?> defaultHttpHandler) {
		this.defaultHttpHandler = defaultHttpHandler;
	}

	

}
