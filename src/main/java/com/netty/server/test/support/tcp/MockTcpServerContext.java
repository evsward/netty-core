package com.netty.server.test.support.tcp;

import com.netty.server.tcp.TcpServerContext;

public class MockTcpServerContext extends TcpServerContext {

	public MockTcpServerContext(int commandId) {
		super(System.nanoTime(), null, null, commandId, 10, 0);
	}
	
	public MockTcpServerContext(int commandId, int charset) {
		super(System.nanoTime(), null, null, commandId, 10, charset);
	}

}
