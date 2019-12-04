/**
 * FileName:     TimeUtil.java
 * @Description: TODO(用一句话描述该文件做什么)
* All rights Reserved, Designed By ZTE-ITS
 * Copyright:    Copyright(C) 2010-2011
 * Company       ZTE-ITS WuXi LTD.
 * @author:    名字
 * @version    V1.0 
 * Createdate:         2019年1月21日 上午9:36:45
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2019年1月21日       wu.zh          1.0             1.0
 * Why & What is modified: <修改原因描述>
 */

package com.example.gohome.utils.time;

import org.joda.time.DateTime;

/**
 * @ClassName: TimeUtil.java
 * @Description: 时间类工具
 * 
 * @author fengjichi
 * @version V1.0
 * @Date 2019年1月21日 上午9:36:45
 */

public class TimeUtil {

	/**
	 * 得到多少分钟前的时间字符串
	 * @param minutes 多少分钟前
	 * @param format 格式，如"yyyy-MM-dd HH:mm:ss"
	 * @return String 时间字符串
	 */
	public static String getMinusMinutes(int minutes,String format) {
		DateTime dt = new DateTime();
		DateTime minusMinutes = dt.minusMinutes(minutes);
		String timeString = minusMinutes.toString(format);
		System.out.println(timeString);
		return timeString;
	}
	
	/**
	 * 生成时间戳字符串
	 * @return String 时间字符串
	*/
	public static String getTimeString() {
		return Long.toString(System.currentTimeMillis() / 1000); 
	}

	/**
	 * @param args
	 */
	/*
	 * public static void main(String[] args) { DateTime dt = new DateTime();
	 * DateTime minusMinutes = dt.minusMinutes(5); String string =
	 * minusMinutes.toString("yyyy-MM-dd HH:mm:ss"); System.out.println(string);
	 * }
	 */

}
