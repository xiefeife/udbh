package com.tydic.udbh.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * 日期工具类
 * @author XIE
 *
 * 2020年4月17日
 */
public class DateUtil {
	
	
	 /**
     * 获取时间戳
     * @return
     */
    public static String getTimestamp() {
        return String.valueOf(System.currentTimeMillis()).substring(0, 10);
    }

    /**
     * 获取随机字符串
     * @return
     */
    public static String getNonce_str() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(5, 25);
    }
	
	
	
	
	/**
	 * 返回当前日期的字符串表示
	 * YYYY-MM-DD
	 * @return 当前日期的字符串表示
	 */
	public static String getNowDate(){
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}
	
	/**
	 * 返回当前的年和月时间
	 * @return：
	 */
	public static String getDataByMandY() {
		return new SimpleDateFormat("yyyy-MM").format(new Date());
	}
	
	/**
	 * 返回yyyy-MM-dd hh:mm:ss类型的时间
	 * @param dateStr
	 * @return
	 */

	/**
	 * 获取当前日期的Date格式化类型(yyyy-MM-dd)
	 * @return
	 */
	public static Date createDate(){
		
		
		Calendar calendar = Calendar.getInstance(); // get current instance of the calendar  
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); 
		
		System.out.println(formatter.format(calendar.getTime()));  
		
		return calendar.getTime();
	}
	
	
	
	
}
