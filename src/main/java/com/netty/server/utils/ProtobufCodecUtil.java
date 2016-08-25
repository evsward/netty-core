package com.netty.server.utils;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import com.google.protobuf.ByteString;
import com.netty.server.tcp.codec.CodecField;

public class ProtobufCodecUtil {
	protected static Logger logger = LoggerFactory.getLogger(ProtobufCodecUtil.class);

	// 0:String ,1:MessageLite ,2:List
	public static void decodeProtoField(List<CodecField> fields, Object target, String charset) {
		
		if(fields == null){
			return;
		}

		for (CodecField codecField : fields) {

			if (codecField.getType() == 0) {
				// 0:String
				decodeStringField(codecField, target, charset);
			} else if (codecField.getType() == 1) {
				// 1:MessageLite
				decodeMessageLiteField(codecField, target, charset);
			} else if (codecField.getType() == 2) {
				// 2:List
				decodeListField(codecField, target, charset);
			}

		}

	}

	public static void decodeStringField(CodecField codecField, Object target, String charset) {
		Object currentFiledValue = ReflectionUtils.getField(codecField.getField(), target);
		if (currentFiledValue == null) {
			return;
		}
		try {
			if (currentFiledValue instanceof String) {
				ReflectionUtils.setField(codecField.getField(), target, new String(((String) currentFiledValue).getBytes(), charset));								
			} else if (currentFiledValue instanceof ByteString) {
				ReflectionUtils.setField(codecField.getField(), target, new String(((ByteString) currentFiledValue).toByteArray(), charset));
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("EncodeStringField error", e);
		}
	}

	public static void decodeMessageLiteField(CodecField codecField, Object target, String charset) {
		if (codecField.getChildren() == null || codecField.getChildren().size() == 0) {
			return;
		}

		Object messageLiteObj = ReflectionUtils.getField(codecField.getField(), target);

		for (CodecField childField : codecField.getChildren()) {
			decodeStringField(childField, messageLiteObj, charset);
		}
	}

	public static void decodeListField(CodecField codecField, Object target, String charset) {
		if (codecField.getChildren() == null || codecField.getChildren().size() == 0) {
			return;
		}

		@SuppressWarnings("unchecked")
		List<Object> listField = (List<Object>) ReflectionUtils.getField(codecField.getField(), target);

		if (listField == null || listField.size() == 0) {
			return;
		}

		for (Object listObj : listField) {
			if(listObj == null){
				continue;
			}
			decodeProtoField(codecField.getChildren(), listObj, charset);
		}
	}
	
	public static void encodeProtoField(List<CodecField> fields, Object target, String charset) {
		if(fields == null){
			return;
		}

		for (CodecField codecField : fields) {

			if (codecField.getType() == 0) {
				// 0:String
				encodeStringField(codecField, target, charset);
			} else if (codecField.getType() == 1) {
				// 1:MessageLite
				encodeMessageLiteField(codecField, target, charset);
			} else if (codecField.getType() == 2) {
				// 2:List
				encodeListField(codecField, target, charset);
			}

		}

	}

	public static void encodeStringField(CodecField codecField, Object target, String charset) {
		Object currentFiledValue = ReflectionUtils.getField(codecField.getField(), target);
		if (currentFiledValue == null) {
			return;
		}
		try {
			if(currentFiledValue instanceof String){								
				ReflectionUtils.setField(codecField.getField(), target, ByteString.copyFrom(((String)currentFiledValue).getBytes(charset)));
			}else if(currentFiledValue instanceof ByteString){
				ReflectionUtils.setField(codecField.getField(), target, new String(((ByteString)currentFiledValue).toByteArray(),charset));	
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("EncodeStringField error", e);
		}
	}

	public static void encodeMessageLiteField(CodecField codecField, Object target, String charset) {
		if (codecField.getChildren() == null || codecField.getChildren().size() == 0) {
			return;
		}

		Object messageLiteObj = ReflectionUtils.getField(codecField.getField(), target);

		for (CodecField childField : codecField.getChildren()) {
			encodeStringField(childField, messageLiteObj, charset);
		}
	}

	public static void encodeListField(CodecField codecField, Object target, String charset) {
		if (codecField.getChildren() == null || codecField.getChildren().size() == 0) {
			return;
		}

		@SuppressWarnings("unchecked")
		List<Object> listField = (List<Object>) ReflectionUtils.getField(codecField.getField(), target);

		if (listField == null || listField.size() == 0) {
			return;
		}

		for (Object listObj : listField) {
			if(listObj == null){
				continue;
			}
			encodeProtoField(codecField.getChildren(), listObj, charset);
		}
	}

	public static void main(String[] args) {
		/*
		 * byte[] ab = getBytes(35 + "," + 10000, 0);
		 * System.out.println(ab.length);
		 */
	}
}
