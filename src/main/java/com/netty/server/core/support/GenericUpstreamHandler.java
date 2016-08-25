package com.netty.server.core.support;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.timeout.ReadTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netty.server.core.ExceptionProcesser;
import com.netty.server.core.RequestProcesser;
import com.netty.server.core.ServerContext;
import com.netty.server.core.message.Message;

/**
 * 上行消息统一处理入口
 * 
 */

public abstract class GenericUpstreamHandler<R extends Message, A extends Message> extends SimpleChannelUpstreamHandler {
	
	protected static Logger logger = LoggerFactory.getLogger(GenericUpstreamHandler.class);

	private RequestProcesser<R, A> requestProcesser;
	
	private ExceptionProcesser<A> exceptionProcesser;
	
	private boolean closeChannelAfterWrite = false;

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent event) throws Exception {
		
		@SuppressWarnings("unchecked")
		R requestMessage = (R)event.getMessage();
				
		A responseMessage = requestProcesser.process(ctx, requestMessage);
		
		if (responseMessage != null && event.getChannel().isWritable()) {
			writeResponseMessage(event.getChannel(),responseMessage);
		} else {
			logger.warn("Channel is not writable ...");
		}

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent event) throws Exception {
		try {
			
			A responseMessage = exceptionProcesser.process(ctx, event);

			if (responseMessage != null && event.getChannel().isWritable()) {
				writeResponseMessage(ctx, event, responseMessage);
			}else{
				Channels.close(event.getChannel());
			}
		} catch (Exception e) {
			logger.error("ExceptionProcesser exceptionCaught ,Channel close", e);
			Channels.close(event.getChannel());
		}finally{
			ctx.setAttachment(null);
		}
	}
	
	protected void writeResponseMessage(Channel channel,Object responseMessage){
		if(closeChannelAfterWrite){
			ChannelFuture future = channel.write(responseMessage);  
		    future.addListener(ChannelFutureListener.CLOSE);
		}else{
			channel.write(responseMessage);
		}
	}
	
	protected void writeResponseMessage(ChannelHandlerContext ctx, ExceptionEvent event,Object responseMessage){
		Channel channel = event.getChannel();
		if(closeChannelAfterWrite){
			ChannelFuture future = channel.write(responseMessage);  
		    future.addListener(ChannelFutureListener.CLOSE);
		}else{			
			ServerContext serverContext = (ServerContext) ctx.getAttachment();
			
			if(serverContext != null && serverContext.isCloseFuture()){
				//写完此消息后，将channel关闭
				ChannelFuture future = channel.write(responseMessage);  
			    future.addListener(ChannelFutureListener.CLOSE);
			}else{
				channel.write(responseMessage);
			}
		}
	}
	
	public boolean isReadTimeout(ExceptionEvent event){
		Throwable throwable = event.getCause();
		
		if (throwable instanceof ReadTimeoutException) {
			return true;
		}
		return false;
	}

	public void setRequestProcesser(RequestProcesser<R, A> requestProcesser) {
		this.requestProcesser = requestProcesser;
	}

	public void setExceptionProcesser(ExceptionProcesser<A> exceptionProcesser) {
		this.exceptionProcesser = exceptionProcesser;
	}

	public void setCloseChannelAfterWrite(boolean closeChannelAfterWrite) {
		this.closeChannelAfterWrite = closeChannelAfterWrite;
	}

}
