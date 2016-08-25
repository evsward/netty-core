package com.netty.server.tcp;

import com.google.protobuf.MessageLite;
import com.netty.server.core.support.GenericRequestProcesser;
import com.netty.server.tcp.message.TcpOriginalRequestMessage;
import com.netty.server.tcp.message.TcpOriginalResponseMessage;

/**
 * 请求统一处理 接收RequestMessage并进行业务处理，处理后返回ResponseMessage
 * 
 */
public class TcpRequestProcesser extends GenericRequestProcesser<TcpOriginalRequestMessage, TcpOriginalResponseMessage, MessageLite, MessageLite.Builder> {

}
