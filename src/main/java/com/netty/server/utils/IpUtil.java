package com.netty.server.utils;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * 
 * To convert user's ip to ipnum.
 *
 */
public class IpUtil {
	public static Logger log = Logger.getLogger(IpUtil.class);
	
	public static final String IP_PATTERN = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";
	
	public static BigInteger IP_RANGE=new BigInteger("256");
	public static BigInteger DECIDE = new BigInteger("-1");
	
	public static final BigInteger MAX_IP = new BigInteger("4294967295");

	/**
	 *  ip转为整形， 把IP地址看成是4位256进制的数字
	 * @param ipStr
	 * @return
	 */
	public static BigInteger toNumber(String ipStr) {
		BigInteger a = new BigInteger("0");
		
		try {
			String[] arrIpStr = ipStr.split("[.]");
			for (int i = 0; i < arrIpStr.length; i++) {
				a = a.multiply(IP_RANGE).add(new BigInteger(arrIpStr[i]));
			}
		} catch (Exception e) {
			
		}
		return a;
	}
	
	public static boolean isIp(String ipAddress){        
        Pattern pattern = Pattern.compile(IP_PATTERN);
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();
    }
	
	/**
	 * 整形转为ip地址
	 * @param no
	 * @return
	 */
	public static String toString(BigInteger no) {		
		BigInteger ip1 = no.divide(IP_RANGE).divide(IP_RANGE).divide(IP_RANGE);
		BigInteger ip2 = no.divide(IP_RANGE).divide(IP_RANGE).add(ip1.multiply(IP_RANGE).multiply(DECIDE));
		BigInteger ip3 = no.divide(IP_RANGE).add(ip1.multiply(IP_RANGE).multiply(IP_RANGE).multiply(DECIDE)).add(ip2.multiply(IP_RANGE).multiply(DECIDE));
		BigInteger ip4 = no.add(ip1.multiply(IP_RANGE).multiply(IP_RANGE).multiply(IP_RANGE).multiply(DECIDE)).add(ip2.multiply(IP_RANGE).multiply(IP_RANGE).multiply(DECIDE)).add(ip3.multiply(IP_RANGE).multiply(DECIDE));
		if(no.compareTo(new BigInteger("0"))<0){
			ip1=ip1.add(new BigInteger("256"));
			ip2=ip2.add(new BigInteger("256"));
			ip3=ip3.add(new BigInteger("256"));
			ip4=ip4.add(new BigInteger("256"));			
		}
		return ip1.toString() + "." + ip2.toString() + "." + ip3.toString() + "." + ip4.toString(); 
	}	
	
	// RoleService 使用  127.0.0.1 ---> 16777343
    // 将127.0.0.1形式的IP地址转换成十进制整数，这里没有进行任何错误处理
    public static int ipToInt(String strIp) {  
        int[] ip = new int[4];  
        // 先找到IP地址字符串中.的位置  
        int position1 = strIp.indexOf(".");  
        int position2 = strIp.indexOf(".", position1 + 1);  
        int position3 = strIp.indexOf(".", position2 + 1);  
        // 将每个.之间的字符串转换成整型  
        ip[0] = Integer.parseInt(strIp.substring(0, position1));  
        ip[1] = Integer.parseInt(strIp.substring(position1 + 1, position2));  
        ip[2] = Integer.parseInt(strIp.substring(position2 + 1, position3));  
        ip[3] = Integer.parseInt(strIp.substring(position3 + 1));  
        return (ip[3] << 24) + (ip[2] << 16) + (ip[1] << 8) + ip[0];  
    }  
  
    // 将十进制整数形式转换成127.0.0.1形式的ip地址  
    public static String intToIP(long intIp) {  
        StringBuffer sb = new StringBuffer("");
        
        // 将高24位置0  
        sb.append(String.valueOf((intIp & 0x000000FF)));
        sb.append(".");  
        // 将高16位置0，然后右移8位  
        sb.append(String.valueOf((intIp & 0x0000FFFF) >>> 8));
        sb.append(".");  
        // 将高8位置0，然后右移16位  
        sb.append(String.valueOf((intIp & 0x00FFFFFF) >>> 16)); 
        
        sb.append(".");
        // 直接右移24位  
        sb.append(String.valueOf((intIp >>> 24)));   
        
        return sb.toString();  
    }  
	

	public static void main(String[] args) {
		
		System.out.println(IpUtil.toNumber("255.255.255.255"));
		
		System.out.println(toNumber("172.28.8.119"));
		System.out.println(toNumber("172.28.8.120"));
		
		System.out.println(toNumber("255.255.255.254"));
		System.out.println(toNumber("255.255.255.255"));
		
	}
		
}
