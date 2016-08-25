### Version1.0.2.RELEASE（2016-08-25 newest）
- reconstruct the project.
- make it more convenient to use.

### Version1.0.1.RELEASE（2015-07-28）
- 1）TcpServerContext 中添加 originalCharset 字段，用来记录原始的请求中check的值，C++平台发送的请求中此值不是表示编码，ACK时需要将此值原封不动的返回给C++客户端

### Version1.0.0（2014-10-10）
- 1）add ServerContext support
- 2）dynamic add ip to tcp and http

### Version0.1.5（2014-09-01）
- 1）tcp turnoff closeChannelAfterWrite
- 2）close channel if ReadTimeout 300s

### Version0.1.4（2014-08-28）
- 1）tcp turnon closeChannelAfterWrite
- 2）add HttpResponseHtmlMessage
- 3）add HttpResponseProxyMessage

### Version0.1.3
- 重构 Protobuf 编解码代码（GBK <-> UTF-8）

### Version0.1.2
- 在annotation CommandIdBasedPolicy中添加encryptKey属性用来自定义响应消息ID
