package com.netty.server.core;

import java.io.IOException;



public interface ResponseConverter<R, A, BA> {

	public A convert(ServerContext serverContext, BA bizResponse) throws IOException;

}
