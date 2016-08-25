package com.netty.server.http.codec;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netty.server.core.message.Message;
import com.netty.server.http.HttpServerContext;
import com.netty.server.utils.MathUtil;

public class HttpResponseEncoder extends org.jboss.netty.handler.codec.http.HttpResponseEncoder {
	protected static Logger logger = LoggerFactory.getLogger(HttpResponseEncoder.class);

	private static final String MESSAGE_FORMAT_ACK   = "HTTP|ACK|%s|%s ms"; 
	
	public HttpResponseEncoder() {
		super();
	}

	@Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
		Message responseMessage = (Message)msg;

		Object ackObj = super.encode(ctx, channel, responseMessage.getBody());
		
		HttpServerContext context = (HttpServerContext)responseMessage.getServerContext();
		
		//HTTP|ACK|1128238693932995||12 ms
		logger.info(String.format(MESSAGE_FORMAT_ACK, context.getFlowId(), MathUtil.usedTimeMS(context.getFlowIdStr())));
		
		ctx.setAttachment(null);
		
		return ackObj;
	}

}
