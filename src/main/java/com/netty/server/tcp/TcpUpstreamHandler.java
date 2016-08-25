package com.netty.server.tcp;

import com.netty.server.core.support.GenericUpstreamHandler;
import com.netty.server.tcp.message.TcpOriginalRequestMessage;
import com.netty.server.tcp.message.TcpOriginalResponseMessage;

/**
 * TCP上行消息统一处理入口
 * 
 */

public class TcpUpstreamHandler extends GenericUpstreamHandler<TcpOriginalRequestMessage, TcpOriginalResponseMessage> {

}
