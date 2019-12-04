package com.example.gohome.utils;

/** 
* 数值工具类
* @author chencaihui 
* @datetime 创建时间：2017年5月15日 下午2:35:58 
*/
public class NumberUtil {

	public static boolean isLong(Object value){
		try {
			if(NullUtil.isNotNull(value)){
				Long.parseLong(value.toString());
				return true;
			}
		} catch (NumberFormatException e) {}
		return false;
	}
	
	public static boolean isDouble(Object value){
		try {
			if(NullUtil.isNotNull(value)){
				Double.parseDouble(value.toString());
				return true;
			}
		} catch (NumberFormatException e) {}
		return false;
	}
	
	public static boolean isInteger(Object value){
		try {
			if(NullUtil.isNotNull(value)){
				Integer.parseInt(value.toString());
				return true;
			}
		} catch (NumberFormatException e) {}
		return false;
	}
}
