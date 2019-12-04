package com.example.gohome.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
* 对字段进行条件查询
* @author chencaihui 
* @datetime 创建时间：2017年3月17日 下午5:32:42 
*/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Select {
	
	/**
	 *是否模糊查询
	 *@author chencaihui 
	 *@datetime 创建时间：2017年3月20日 下午12:03:15 
	 */
	boolean isLike() default false;
	
	
	/**
	 *是否范围查询（数字）
	 *@author chencaihui 
	 *@datetime 创建时间：2017年3月20日 下午12:03:15 
	 */
	boolean isNumberRange() default false;
	
	/**
	 *是否范围查询(时间)
	 *@author chencaihui 
	 *@datetime 创建时间：2017年3月20日 下午12:03:15 
	 */
	boolean isTimeRange() default false;
	
	
	/**
	 * 判断是否是外键查询(表示一对一或一对多)
	 *@author chencaihui 
	 *@datetime 创建时间：2017年3月27日 上午11:14:20 
	 * @return
	 */
	boolean isEqualSelect() default false;
	
	
	/**
	 * 是否关联查询，将实体的多个字段作为等值查询AND条件
	 *@author chencaihui 
	 *@datetime 创建时间：2017年3月28日 上午9:04:59 
	 * @return
	 */
	boolean isMoreConnect() default false;
	
	/**
	 * 是否关联查询，将实体的多个字段作为等值查询AND条件
	 *@author chencaihui 
	 *@datetime 创建时间：2017年3月28日 上午9:04:59 
	 * @return
	 */
	boolean isMoreConnect2() default false;
	
	/**
	 * 是否关联查询，将实体的多个字段作为等值查询AND条件
	 *@author chencaihui 
	 *@datetime 创建时间：2017年3月28日 上午9:04:59 
	 * @return
	 */
	boolean isMoreConnect3() default false;
	/**
	 * 是否关联查询，将实体的多个字段作为等值查询AND条件
	 *@author chencaihui 
	 *@datetime 创建时间：2017年3月28日 上午9:04:59 
	 * @return
	 */
	boolean isMoreConnect4() default false;
	/**
	 * 是否关联查询，将实体的多个字段作为等值查询AND条件
	 *@author chencaihui 
	 *@datetime 创建时间：2017年3月28日 上午9:04:59 
	 * @return
	 */
	boolean isMoreConnect5() default false;
	/**
	 * 是否关联查询，将实体的多个字段作为等值查询AND条件
	 *@author chencaihui 
	 *@datetime 创建时间：2017年3月28日 上午9:04:59 
	 * @return
	 */
	boolean isMoreConnect6() default false;
	
	/**
	 *关联createId查询
	 *@author chencaihui 
	 *@datetime 创建时间：2017年3月28日 上午9:04:59 
	 * @return
	 */
	boolean isConnectCreateId() default false;
	
	/**
	 * 是否时间等值查询
	 *@author chencaihui 
	 *@datetime 创建时间：2018年1月16日 下午3:32:18 
	 * @return
	 */
	boolean isEqualTime() default false;
	
	/**
	 * 日期格式,格式为数据库格式
	 *@author chencaihui 
	 *@datetime 创建时间：2018年1月16日 下午3:32:07 
	 * @return
	 */
	String TimeFormat() default "%Y-%m-%d %H:%i";
	
	/***
	 * 当条件name='isNull'，构建 name is not null
	 *@author chencaihui 
	 *@datetime 创建时间：2018年2月9日 上午11:48:46 
	 * @return
	 */
	boolean isNull() default false;
	
	/***
	 * 当条件name='isNotNull'，构建 name is not null 
	 *@author chencaihui 
	 *@datetime 创建时间：2018年2月9日 上午11:48:46 
	 * @return
	 */
	boolean isNotNull() default false;
}
