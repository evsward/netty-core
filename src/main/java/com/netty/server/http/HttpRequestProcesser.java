package com.netty.server.http;

import com.netty.server.core.support.GenericRequestProcesser;
import com.netty.server.http.message.HttpOriginalRequestMessage;
import com.netty.server.http.message.HttpOriginalResponseMessage;
import com.netty.server.http.message.HttpRequestMessage;
import com.netty.server.http.message.HttpResponseMessage;



/**
 * 请求统一处理
 * 接收HttpRequest并进行业务处理，处理后返回HttpResponse
 * 
 */
public class HttpRequestProcesser extends GenericRequestProcesser<HttpOriginalRequestMessage, HttpOriginalResponseMessage, HttpRequestMessage, HttpResponseMessage> {

}
