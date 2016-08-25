package com.netty.server.utils;

import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByteUtils {
	protected static Logger logger = LoggerFactory.getLogger(ByteUtils.class);

	public static final String BLANK = " ";
	public static final String ZERO = "0";

	public static byte[] getBytes4Ip(String ip) {
		byte[] ret = new byte[4];
		StringTokenizer st = new StringTokenizer(ip, ".");
		if (st.countTokens() == 4) {
			ret[0] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
			ret[1] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
			ret[2] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
			ret[3] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
		}
		return ret;
	}

	public static int getIpIntFromString(String ip) {
		if (StringUtils.isEmpty(ip))
			return 0;
		byte[] b = getBytes4Ip(ip);
		int offset = 0;
		return ((b[offset + 3] & 0xff) << 24) | ((b[offset + 2] & 0xff) << 16) | ((b[offset + 1] & 0xff) << 8) | (b[offset + 0] & 0xff);
	}

	public static byte[] charToByte(char c) {
		byte[] b = new byte[2];
		b[0] = (byte) ((c & 0xFF00) >>> 8);
		b[1] = (byte) (c & 0xFF);
		return b;
	}

	public static int getIntFromString(String s) {
		if (StringUtils.isEmpty(s)) {
			return 0;
		}
		return Integer.valueOf(s);
	}

/*	public static String readFromByteBuffer(ByteBuffer bb, int length) {
		byte[] nameByte = new byte[length];
		bb.get(nameByte);
		try {
			return new String(nameByte, "GBK");
		} catch (UnsupportedEncodingException e1) {
		}
		return StringUtils.EMPTY;
	}*/

/*	public static byte[] getBytes(String src, int length) {
		try {
			if (src == null)
				src = "";
			byte[] bs = src.getBytes("GBK");
			if (length == 0)
				return bs;
			return Arrays.copyOf(bs, length);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}*/

	/**
	 * 以小尾的顺序生成整数的字节数组
	 * 
	 * @param n
	 * @return
	 */
	public static byte[] getBytes(int n) {
		byte[] ab = new byte[4];
		ab[0] = (byte) (0xff & n);
		ab[1] = (byte) ((0xff00 & n) >> 8);
		ab[2] = (byte) ((0xff0000 & n) >> 16);
		ab[3] = (byte) ((0xff000000 & n) >> 24);
		return ab;
	}

	/**
	 * 以小尾的顺序生成短整数的字节数组
	 * 
	 * @param n
	 * @return
	 */
	public static byte[] getBytes(short n) {
		byte[] ab = new byte[2];
		ab[0] = (byte) (0xff & n);
		ab[1] = (byte) ((0xff00 & n) >> 8);
		return ab;
	}

	/**
	 * 以小尾的顺序生成char的字节数组
	 * 
	 * @param n
	 * @return
	 */
	public static byte[] getBytes(char c) {
		int i = Character.valueOf(c).hashCode();
		if (i < 256)// 单字节
			return new byte[] { (byte) i };
		else
			// 双字节
			return getBytes((short) i);
	}

	public static byte[] getBytes(boolean b) {
		byte[] ab = new byte[1];
		ab[0] = (byte) (b ? 1 : 0);
		return ab;
	}

	/**
	 * 以小尾的顺序读取整数
	 * 
	 * @param ab
	 * @param offset
	 * @return
	 */
	public static int getInt(byte[] ab, int offset) {
		return ((ab[offset + 3] & 0xff) << 24) | ((ab[offset + 2] & 0xff) << 16) | ((ab[offset + 1] & 0xff) << 8) | (ab[offset + 0] & 0xff);
	}

	public static boolean getBoolean(byte[] ab, int offset) {
		return ab[offset] != 0;
	}

	/**
	 * 以小尾的顺序读取短整数
	 * 
	 * @param ab
	 * @param offset
	 * @return
	 */
	public static short getShort(byte[] ab, int offset) {
		return (short) (((ab[offset + 1] & 0xff) << 8) | (ab[offset + 0] & 0xff));
	}

/*	public static String getString(byte[] ab, int offset, int length) {
		try {
			return new String(ab, offset, length, "GBK").trim();
		} catch (Exception e) {
			return "";
		}
	}*/

	/**
	 * 以 0 字节为字符串的终止符
	 * 
	 * @param ab
	 * @param offset
	 * @return
	 */
/*	public static String getString(byte[] ab, int offset) {
		ByteBuffer bb = ByteBuffer.allocate(1024).order(ByteOrder.LITTLE_ENDIAN);
		for (int i = offset; i < ab.length; i++) {
			if (ab[i] == (byte) 0)
				break;
			bb.put(ab[i]);
		}
		try {
			return new String(Arrays.copyOf(bb.array(), bb.position()), "GBK").trim();
		} catch (UnsupportedEncodingException e) {
			return "";
		}

	}*/

	public static void copyTo(byte[] src, byte[] dst, int offset) {
		if (dst.length < src.length + offset)
			throw new IllegalArgumentException("The destination array has not capacity to fill source array.");
		for (int i = 0; i < src.length; i++) {
			dst[offset + i] = src[i];
		}
	}

	public static int findFirst(byte[] ab, int offset, byte target) {
		int i = offset;
		for (; i < ab.length; i++) {
			if (ab[i] == target) {
				break;
			}
		}
		return i;
	}

	/**
	 * 从字节数组的输出字符串中还原字节数组<Br>
	 * 字符串格式如下:[45, -128, 0, -128, 0, 46, 0, 0, 1, 0, 0, 0]
	 * 
	 * @param arrayString
	 * @return
	 */
	public static byte[] getBytesFromArrayString(String arrayString) {
		if (arrayString.startsWith("[") && arrayString.endsWith("]")) {
			arrayString = arrayString.substring(1, arrayString.length() - 1);
			String[] array = arrayString.split(",");
			if (array.length > 0) {
				byte[] bytes = new byte[array.length];
				for (int i = 0; i < array.length; i++) {
					bytes[i] = Byte.parseByte(array[i].trim());
				}
				return bytes;
			}
		}
		return new byte[0];
	}

	public static String getHexString(byte[] bs) {
		StringBuffer sb = new StringBuffer();
		for (byte b : bs) {
			String temp = Integer.toHexString((Byte.valueOf(b).intValue()) & 0xFF).toUpperCase();
			if (temp.length() == 1) {
				sb.append(ZERO);
			}
			sb.append(temp).append(BLANK);
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		/*byte[] ab = getBytes(35 + "," + 10000, 0);
		System.out.println(ab.length);*/
	}
}
