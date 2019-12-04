package com.example.gohome.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
* 配置类与表、列与属性
* @author chencaihui 
* @datetime 创建时间：2017年3月17日 下午5:32:42 
*/
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Reflect {
	
	/**
	 * 作用类上的是表名；作用于列上的是列名
	 *@author chencaihui 
	 *@datetime 创建时间：2017年5月13日 下午3:45:53 
	 * @return
	 */
	String name();
	
	/**
	 * 更新的时候是否不处理
	 *@author chencaihui 
	 *@datetime 创建时间：2018年1月30日 上午10:58:58 
	 * @return
	 */
	boolean updateNotCopy() default false;
	
}
