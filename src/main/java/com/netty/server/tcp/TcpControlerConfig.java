package com.netty.server.tcp;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
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
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.MethodCallback;
import org.springframework.util.ReflectionUtils.MethodFilter;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.protobuf.MessageLite;
import com.netty.server.core.ServerContext;
import com.netty.server.core.annotation.CommandIdBasedPolicy;
import com.netty.server.core.support.HandlerConfig;
import com.netty.server.tcp.codec.CodecField;
import com.netty.server.tcp.handler.ProtobufMessageHandler;
import com.netty.server.utils.Constant;

//@Component
public class TcpControlerConfig implements ApplicationContextAware {

	protected static Logger logger = LoggerFactory.getLogger(TcpControlerConfig.class);

	public static final Map<Integer, MessageLite> CMD_MESSAGELITE_MAPPING = Maps.newHashMap();
	public static final Map<Integer, ProtobufMessageHandler<?, ?>> CMD_HANDLER_MAPPING = Maps.newHashMap();

	public static final Map<Integer, List<CodecField>> CMD_REQ_STRING_FIELD_MAPPING = Maps.newHashMap();
	public static final Map<Integer, List<CodecField>> CMD_ACK_BUILDER_STRING_FIELD_MAPPING = Maps.newHashMap();
	public static final Map<Integer, List<CodecField>> CMD_ACK_STRING_FIELD_MAPPING = Maps.newHashMap();
	
	public static final Map<Integer, Integer> CMD_ACK_ENCRYPY_KEY_MAPPING = Maps.newHashMap();
	
	public static final List<HandlerConfig> handlerConfigList = Lists.newArrayList();
	
	public static final String METHOD_GETTER = "get";

	private ApplicationContext applicationContext;

	public MessageLite getMessageLite(Integer commandId) {
		return CMD_MESSAGELITE_MAPPING.get(commandId);
	}
	
	public int getAckEncryptKey(Integer commandId) {
		Integer key = CMD_ACK_ENCRYPY_KEY_MAPPING.get(commandId);
		if(key == null){
			return commandId;
		}
		return key;
	}

	public ProtobufMessageHandler<?, ?> getMessageHandler(Integer commandId) {
		return CMD_HANDLER_MAPPING.get(commandId);
	}
	
	public List<CodecField> getReqStringFieldList(Integer commandId) {
		return CMD_REQ_STRING_FIELD_MAPPING.get(commandId);
	}
	
	public List<CodecField> getAckBuilderStringFieldList(Integer commandId) {
		return CMD_ACK_BUILDER_STRING_FIELD_MAPPING.get(commandId);
	}

	public List<CodecField> getAckStringFieldList(Integer commandId) {
		return CMD_ACK_STRING_FIELD_MAPPING.get(commandId);
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
    	    	Integer iBizKey1 = Integer.parseInt(config1.getBizKey());
    	    	Integer iBizKey2 = Integer.parseInt(config2.getBizKey());
    	    	
    	        return iBizKey1.compareTo(iBizKey2);
    	    }  
    	});
		
		for (HandlerConfig config : handlerConfigList) {			
			logger.info(String.format("* %-35s\t%-30s\t%-30s", config.getBizKey(), config.getHandler(), config.getRequestType()));
		}
	}

	public void initMessageHandlerConfig() {
		@SuppressWarnings("rawtypes")
		Map<String, ProtobufMessageHandler> allHandlers = applicationContext.getBeansOfType(ProtobufMessageHandler.class);

		logger.info(Constant.Str.CUTOFF_RULE);
		
		HandlerConfig handlerConfig = null; 

		for (String key : allHandlers.keySet()) {

			ProtobufMessageHandler<?, ?> messageHandler = allHandlers.get(key);

			// CommandIdBasedPolicy commandIdAnnotation = messageHandler.getClass().getAnnotation(CommandIdBasedPolicy.class);
			CommandIdBasedPolicy commandIdAnnotation = applicationContext.findAnnotationOnBean(key, CommandIdBasedPolicy.class);

			if (commandIdAnnotation == null) {
				// 忽略没有配置 CommandIdBasedPolicy 的handler
				continue;
			}

			int cmdId = commandIdAnnotation.value();
			
			CMD_ACK_ENCRYPY_KEY_MAPPING.put(cmdId, commandIdAnnotation.encryptKey());

			for (Method method : messageHandler.getClass().getMethods()) {

				Class<?>[] params = method.getParameterTypes();

				if (!"execute".equals(method.getName()) || params.length != 2) {
					continue;
				}

				if (Arrays.equals(new Class<?>[] {ServerContext.class, MessageLite.class }, method.getParameterTypes())) {
					continue;
				}

				if (Arrays.equals(new Class<?>[] {ServerContext.class, Object.class }, method.getParameterTypes())) {
					continue;
				}

				if (CMD_HANDLER_MAPPING.containsKey(cmdId)) {
					logger.error("* cmdId exists,ignor:" + cmdId);
					continue;
				}

				CMD_HANDLER_MAPPING.put(cmdId, messageHandler);
				Class<?> clazz = method.getParameterTypes()[1];

				CMD_MESSAGELITE_MAPPING.put(cmdId,
						(MessageLite) ReflectionUtils.invokeMethod(ReflectionUtils.findMethod(clazz, "getDefaultInstance"), clazz));

				CMD_REQ_STRING_FIELD_MAPPING.put(cmdId, parseCodecFieldFields(clazz));				
				CMD_ACK_BUILDER_STRING_FIELD_MAPPING.put(cmdId, parseCodecFieldFields(method.getReturnType()));
				
				Class<?> nonBuilerClazz = parseNonBuilerClazzName(method.getReturnType());
				
				if(nonBuilerClazz != null){
					CMD_ACK_STRING_FIELD_MAPPING.put(cmdId, parseCodecFieldFields(nonBuilerClazz));	
				}				
				
				//logger.info(String.format("* handler\t%s\t%s\t%s", cmdId, key, clazz.getSimpleName()));				
				handlerConfig = new HandlerConfig(String.valueOf(cmdId),key,clazz.getSimpleName());				
				handlerConfigList.add(handlerConfig);
				
			}
		}

		if (CMD_MESSAGELITE_MAPPING.size() == 0) {
			logger.warn("CMD_MESSAGELITE_MAPPING is null");
		}

		if (CMD_HANDLER_MAPPING.size() == 0) {
			logger.warn("CMD_HANDLER_MAPPING is null");
		}
	}
	
	public Class<?> parseNonBuilerClazzName(Class<?> builderClazz){
		String builderClazzName = builderClazz.getName();
				
		try {
			return Class.forName(builderClazzName.substring(0, builderClazzName.length()-8));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	public List<CodecField> parseCodecFieldFields(final Class<?> classss) { 

		final List<CodecField> fieldList = Lists.newArrayList();

		ReflectionUtils.doWithMethods(classss, new MethodCallback() {
			@SuppressWarnings("rawtypes")
			@Override
			public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
				
				CodecField codecField = new CodecField();
				
				String propertyName = parseStringFiledName(method.getName());
				
				codecField.setName(propertyName);
								
				if (method.getReturnType() == String.class) {
					codecField.setType(0);
				}else if (method.getReturnType() == List.class) {
					codecField.setType(2);
					ParameterizedType returnType = (ParameterizedType)method.getGenericReturnType();										
					List<CodecField> liteList = parseCodecFieldFields((Class)returnType.getActualTypeArguments()[0]);
					if(liteList != null && liteList.size() > 0){
						codecField.setChildren(liteList);	
					}					
				}else{
					codecField.setType(1);
					List<CodecField> liteList = parseCodecFieldFields(method.getReturnType());
					if(liteList != null && liteList.size() > 0){
						codecField.setChildren(liteList);	
					}
				}
				
				Field field = ReflectionUtils.findField(classss, propertyName);
				
				if(field != null){
					ReflectionUtils.makeAccessible(field);
					codecField.setField(field);
					fieldList.add(codecField);
				}
			}
		}, new MethodFilter() {
			@Override
			public boolean matches(Method method) {
				if (!method.getName().startsWith("get")) {
					return false;
				}
				
				if (method.getParameterTypes().length != 0) {
					return false;
				}
				
				if (method.getReturnType() == String.class) {
					return true;
				}
				if (method.getReturnType() == com.google.protobuf.GeneratedMessage.class) {
					return true;
				}				
				
				if (method.getReturnType() == List.class) {
					if(method.getName().endsWith("OrBuilderList") || method.getName().endsWith("BuilderList")){
						return false;
					}
					return true;
				}
				
				if(method.getReturnType().getPackage() != null && method.getReturnType().getPackage().getName().startsWith("com.netty")){
					if(method.getName().startsWith("getDefaultInstance") || method.getName().endsWith("Builder") || method.getName().endsWith("OrBuilder")){
						return false;
					}
					return true;
				}
				return false;
			}
		});
		
		return fieldList;
	}

	public List<Field> parseStringFields(Class<?> classss) { 

		List<Field> fieldList = Lists.newArrayList();

		final List<String> stringFiledNameList = Lists.newArrayList();

		ReflectionUtils.doWithMethods(classss, new MethodCallback() {
			@Override
			public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
				stringFiledNameList.add(parseStringFiledName(method.getName()));
			}
		}, new MethodFilter() {
			@Override
			public boolean matches(Method method) {
				if (!method.getName().startsWith(METHOD_GETTER)) {
					return false;
				}
				if (method.getReturnType() != String.class) {
					return false;
				}
				return true;
			}
		});

		Field field = null;

		for (String filedName : stringFiledNameList) {
			field = ReflectionUtils.findField(classss, filedName);
			ReflectionUtils.makeAccessible(field);

			fieldList.add(field);
		}

		return fieldList;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	public String parseStringFiledName(String methodName) {
		if(methodName.endsWith("List")){
			methodName = methodName.substring(0, methodName.length()-4);
		}
		return (new StringBuilder()).append(Character.toLowerCase(methodName.charAt(3))).append(methodName.substring(4)).append(Constant.Str.UNDERLINE).toString();
	}

}
