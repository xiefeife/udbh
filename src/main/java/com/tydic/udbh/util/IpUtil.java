package com.tydic.udbh.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 获取ip的工具类
 * @author XIE
 *
 * 2020年4月17日
 */
public class IpUtil {
	/**
	 * 获取ip地址
	 * @return：返回ip地址
	 */
	public static String getIp() {
		//获得本机IP  
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}      
		String ip=addr.getHostAddress();
		
		return ip;
	}
}
