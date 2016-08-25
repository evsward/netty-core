package com.netty.server.test.support.tcp;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.Message;
import com.netty.server.utils.ByteUtils;

public abstract class AbstractTcpTestCase {

	protected static Logger logger = LoggerFactory.getLogger(AbstractTcpTestCase.class);
	
	private Channel channel;

	public abstract String getServerIp();

	public abstract int getServerPort();

	protected int getMessageHeaderLength() {
		return 12;
	}

	@Before
	public void init() {

		ChannelFactory factory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool(), Runtime
				.getRuntime().availableProcessors());

		ClientBootstrap bootstrap = new ClientBootstrap(factory);

		bootstrap.setOption("connectTimeoutMillis", 10000);
		bootstrap.setOption("connectResponseTimeoutMillis", 10000);
		bootstrap.setOption("sendBufferSize", 1048576);
		bootstrap.setOption("receiveBufferSize", 1048576);
		bootstrap.setOption("tcpNoDelay", true);

		bootstrap.setPipelineFactory(new ClientPipelinFactory());

		ChannelFuture connectFuture = bootstrap.connect(new InetSocketAddress(getServerIp(), getServerPort()));
		connectFuture.awaitUninterruptibly();
		if (connectFuture.isSuccess()){
			channel = connectFuture.getChannel();
		}	
	}
	
	protected int getCharset(){
		return 1;
	}

	protected ChannelBuffer prepareRequest(byte[] body, int cmd) {
		ChannelBuffer buffer = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, body.length + getMessageHeaderLength());

		int check = getCharset();
		
		buffer.writeInt(cmd);
		buffer.writeInt(body.length);
		buffer.writeInt(check);
		buffer.writeBytes(body);
		
		logger.info(String.format("Req Head:%s,%s,%s",cmd,body.length,check));
		logger.info("Req Body:"+ByteUtils.getHexString(body));
		
		return buffer;
	}

	protected ChannelBuffer request(Message message, int cmd) throws InterruptedException {
		logger.info(String.format("Req Message:%s,%s",cmd,message));
		
		byte[] body = message == null ? new byte[] {} : message.toByteArray();
		ChannelBuffer buffer = prepareRequest(body, cmd);
		
		ChannelFuture future = channel.write(buffer);
		future.awaitUninterruptibly();

		ClientHandler handler = (ClientHandler) channel.getPipeline().get("handler");
		return handler.getAck();
	}

	@SuppressWarnings("unchecked")
	protected <T> T requestWithAck(Message message, int cmd, Class<? extends Message> t) throws Exception {
		logger.info(String.format("Req Message:%s,\n%s",cmd,message));
		byte[] body = message == null ? new byte[] {} : message.toByteArray();
		ChannelBuffer buffer = prepareRequest(body, cmd);

		ChannelFuture future = channel.write(buffer);
		future.awaitUninterruptibly();

		ClientHandler handler = (ClientHandler) channel.getPipeline().get("handler");
		return (T)parse(t, handler.getAck().array());
	}

	@SuppressWarnings("unchecked")
	protected <T> T parse(Class<? extends Message> t, byte[] b) throws Exception {
		
		byte[] fullMessage = Arrays.copyOfRange(b, 0, getMessageHeaderLength());

		logger.info("Ack Head:"+ByteUtils.getHexString(fullMessage));		
		
		byte[] body = Arrays.copyOfRange(b, getMessageHeaderLength(), b.length);
		
		logger.info("Ack Body:"+ByteUtils.getHexString(body));
		
		CodedInputStream in = CodedInputStream.newInstance(body, 0, body.length);
		Method method = t.getMethod("parseFrom", CodedInputStream.class);
		
		T result = (T) method.invoke(t, in);		
		logger.info("Ack message:\n"+result);
		return result;
	}

	@After
	public void destory() {
		channel.close().awaitUninterruptibly();
	}

}
