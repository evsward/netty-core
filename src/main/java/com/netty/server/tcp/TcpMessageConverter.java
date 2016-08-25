package com.netty.server.tcp;

import java.io.IOException;
import java.util.List;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.MessageLite;
import com.netty.server.core.MessageConverter;
import com.netty.server.core.ServerContext;
import com.netty.server.core.exception.AlertException;
import com.netty.server.tcp.codec.CodecField;
import com.netty.server.tcp.message.TcpOriginalRequestMessage;
import com.netty.server.tcp.message.TcpOriginalResponseMessage;
import com.netty.server.utils.Constant;
import com.netty.server.utils.ProtobufCodecUtil;


public class TcpMessageConverter implements MessageConverter<TcpOriginalRequestMessage, TcpOriginalResponseMessage, MessageLite, MessageLite.Builder> {

	protected static Logger logger = LoggerFactory.getLogger(TcpMessageConverter.class);

	// TCP|RBO|1365453134404046|
	private static final String TCP_REQUEST_INFO  = " TCP|RBO|%s|\n%s";
	private static final String TCP_RESPONSE_INFO = " TCP|ABO|%s|\n%s";
	
	private TcpControlerConfig controlerConfig;
	
	@Override
	public MessageLite toBizRequest(TcpOriginalRequestMessage requestMessage) throws IOException {

		MessageLite request = null; 
				
		ChannelBuffer requestBody = requestMessage.getBody();

		MessageLite prototype = controlerConfig.getMessageLite(requestMessage.getCommandId());
		
		if (prototype == null) {
			throw new AlertException(Constant.ErrorCode.UNSUPPORT_ERROR, 
					String.format("Unsupport MessageLite type for cmdId=%s",requestMessage.getCommandId()));
		}

		if (requestBody.hasArray()) {
			final int offset = requestBody.readerIndex();
			request = prototype.newBuilderForType().mergeFrom(requestBody.array(), requestBody.arrayOffset() + offset, requestBody.readableBytes()).build();			
		}
		
		if(request == null){
			request = prototype.newBuilderForType().mergeFrom(new ChannelBufferInputStream(requestBody)).build();	
		}

		logger.info(String.format(TCP_REQUEST_INFO,requestMessage.getServerContext().getFlowId(),request.toString()));
		
		if(requestMessage.getCharset() == 0){
			//为gbk编码			
			List<CodecField> codecFields = controlerConfig.getReqStringFieldList(requestMessage.getCommandId());
			
			ProtobufCodecUtil.decodeProtoField(codecFields, request, Constant.System.CHARSET_GBK);
		}
		
		return request;

	}

	@Override
	public TcpOriginalResponseMessage toResponse(ServerContext serverContext, MessageLite.Builder bizResponse) throws IOException {

		TcpServerContext tcpServerContext = (TcpServerContext)serverContext;
		
		long flowId = serverContext.getFlowId();
		
		if(tcpServerContext.getiCharset() == 0){
			//为gbk编码
			List<CodecField> codecFields = controlerConfig.getAckBuilderStringFieldList(tcpServerContext.getCommandId());
			
			ProtobufCodecUtil.encodeProtoField(codecFields, bizResponse, Constant.System.CHARSET_GBK);			
		}
		
		TcpOriginalResponseMessage responseMessage = new TcpOriginalResponseMessage(tcpServerContext, bizResponse.build());
		
		responseMessage.setAckEncryptKey(controlerConfig.getAckEncryptKey(responseMessage.getCommandId()));
				
		logger.info(String.format(TCP_RESPONSE_INFO, flowId, responseMessage.toString())); 
		
		return responseMessage;
	}

	public void setControlerConfig(TcpControlerConfig controlerConfig) {
		this.controlerConfig = controlerConfig;
	}

}
