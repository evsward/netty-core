package com.netty.server.utils;

public interface Constant {

	class System{
		public static final int PING_CMD = 0x00000100;			//256
		public static final int GLID_ACK = 0x08000000;		
		
		public static final int ALERT_ACK = 0x00000010;			//16	
		
		public static final String HEADER_CLIENT_IP = "Reserve-ClientIp";
		public static final String HEADER_FLOW_ID = "Reserve-FlowId";
		
		public static final String CHARSET_GBK = "GBK";
		public static final String CHARSET_UTF8 = "UTF-8";
	}
	
	class ErrorCode{		
		public static final int UNKNOWN_ERROR = 10;		
		public static final int UNSUPPORT_ERROR = 11;		
		
	}
	
	class Str{
		public static final String WIDCARD = "*";	
		public static final String COMMA = ",";
		public static final String PERIOD = ".";
		public static final String MINUS = "-";
		public static final String VERTICAL = "|";
		public static final String NEWLINE = "\n";
		public static final String BLANK = " ";
		public static final String UNDERLINE = "_";
		
		public static final String LEFT_BRACKET = "[";
		public static final String RIGHT_BRACKET = "]";
		
		public static final String PERIOD_WIDCARD = ".*";	
		
		public static final String CUTOFF_RULE = "****************************************************************************************";
						
	}
	
	class Http{
		public static final String DEFAULT_CHARSET = "UTF-8";
		
		public static final String HEADER_LOCATION = "Location";
		public static final String HEADER_PROXY_LOCATION = "_Proxy-Location";
		
		public static final String HEADER_CONTENT_TYPE = "Content-Type";
		
		public static final String HEADER_ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
				
		public static final String HEADER_CACHE_CONTROL = "Cache-Control";
		public static final String HEADER_CACHE_CONTROL_VALUE = "no-store";
		
		public static final String HEADER_PRAGMA = "Pragma";
		public static final String HEADER_PRAGMA_VALUE = "no-cache";
		
		public static final String PNG = "png";
		public static final String JPEG = "jpeg";
		
	}
}
