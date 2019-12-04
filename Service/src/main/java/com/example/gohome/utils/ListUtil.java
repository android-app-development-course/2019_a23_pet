package com.example.gohome.utils;

import com.example.gohome.utils.object.AnnotationUtil;
import com.example.gohome.utils.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/** 
* 针对集合的处理类
* @author chencaihui 
* @datetime 创建时间：2017年4月1日 下午1:12:18 
*/
public class ListUtil {
	
	private static Logger LoggerUtil = LoggerFactory.getLogger("ListUtil");

	/**
	 * 将集合中的某列数值数据升序/降序
	 *@author chencaihui 
	 *@datetime 创建时间：2017年3月28日 下午5:49:47 
	 * @param results
	 * @param sortColumnName
	 * @param isAsc是否升序，是：true
	 */
	public static <T> void sortToNumber(List<T> results, String sortColumnName, boolean isAsc){
		try {
			if (NullUtil.isNotNull(results)) {
				int max=results.size();
				if(max>1){
					for (int i = 0; i < max-1; i++) {
						for (int j = 0; j < max-i-1; j++) {
							T object1 = results.get(j);
							T object2 = results.get(j+1);
							
							long value1 = Long.parseLong(String.valueOf(AnnotationUtil.getFieldValue(object1, sortColumnName)));
							long value2 = Long.parseLong(String.valueOf(AnnotationUtil.getFieldValue(object2, sortColumnName)));
							if(isAsc){
								if(value1 > value2){
									results.set(j, object2);
									results.set(j+1, object1);
								}
							}else{
								if(value1 < value2){
									results.set(j, object2);
									results.set(j+1, object1);
								}
							}
						}
					}
				}
			}
		} catch (NumberFormatException e) {
			LoggerUtil.error("数据转换失败："+e);
		}
	}
	
	/**
	 * 将集合中的某列数值数据升序/降序
	 *@author chencaihui 
	 *@datetime 创建时间：2017年3月28日 下午5:49:47 
	 * @param results
	 * @param sortColumnName
	 * @param isAsc是否升序，是：true
	 */
	public static void sortToNumber2(List<Map<String, String>> results, String sortColumnName, boolean isAsc){
		try {
			if (NullUtil.isNotNull(results)) {
				int max=results.size();
				if(max>1){
					for (int i = 0; i < max-1; i++) {
						for (int j = 0; j < max-i-1; j++) {
							Map<String, String> resultMap1 = results.get(j);
							Map<String, String> resultMap2 = results.get(j+1);
							long value1 = Long.parseLong(resultMap1.get(sortColumnName));
							long value2 = Long.parseLong(resultMap2.get(sortColumnName));
							if(isAsc){
								if(value1 > value2){
									results.set(j, resultMap2);
									results.set(j+1, resultMap1);
								}
							}else{
								if(value1 < value2){
									results.set(j, resultMap2);
									results.set(j+1, resultMap1);
								}
							}
						}
					}
				}
			}
		} catch (NumberFormatException e) {
			LoggerUtil.error("数据转换失败："+e);
		}
	}
	
	/**
	 * 将集合中的某列数值数据升序/降序
	 *@author chencaihui 
	 *@datetime 创建时间：2017年3月28日 下午5:49:47 
	 * @param results
	 * @param sortColumnName
	 * @param isAsc是否升序，是：true
	 */
	public static <T> void sortToTime(List<T> results, String sortColumnName, boolean isAsc){
		try {
			if (NullUtil.isNotNull(results)) {
				int max=results.size();
				if(max>1){
					for (int i = 0; i < max-1; i++) {
						for (int j = 0; j < max-i-1; j++) {
							T object1 = results.get(j);
							T object2 = results.get(j+1);
							Date startDate = DateUtils.isDate(String.valueOf(AnnotationUtil.getFieldValue(object1, sortColumnName)));
							Date endDate = DateUtils.isDate(String.valueOf(AnnotationUtil.getFieldValue(object2, sortColumnName)));
							long value1 = startDate.getTime();
							long value2 = endDate.getTime();
							if(isAsc){
								if(value1 > value2){
									results.set(j, object2);
									results.set(j+1, object1);
								}
							}else{
								if(value1 < value2){
									results.set(j, object2);
									results.set(j+1, object1);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			LoggerUtil.error("数据转换失败："+e);
		}
	}
	
	/**
	 * 将集合中的某列数值数据升序/降序
	 *@author chencaihui 
	 *@datetime 创建时间：2017年3月28日 下午5:49:47 
	 * @param results
	 * @param sortColumnName
	 * @param isAsc是否升序，是：true
	 */
	public static void sortToTime2(List<Map<String, String>> results, String sortColumnName, boolean isAsc){
		try {
			if (NullUtil.isNotNull(results)) {
				int max=results.size();
				if(max>1){
					for (int i = 0; i < max-1; i++) {
						for (int j = 0; j < max-i-1; j++) {
							Map<String, String> resultMap1 = results.get(j);
							Map<String, String> resultMap2 = results.get(j+1);
							Date startDate = DateUtils.isDate(resultMap1.get(sortColumnName));
							Date endDate = DateUtils.isDate(resultMap2.get(sortColumnName));
							long value1 = startDate.getTime();
							long value2 = endDate.getTime();
							if(isAsc){
								if(value1 > value2){
									results.set(j, resultMap2);
									results.set(j+1, resultMap1);
								}
							}else{
								if(value1 < value2){
									results.set(j, resultMap2);
									results.set(j+1, resultMap1);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			LoggerUtil.error("数据转换失败："+e);
		}
	}
	
	/**
	 * 将集合中的某列进行去除重复,并返回去重后的集合值
	 *@author chencaihui 
	 *@datetime 创建时间：2017年3月28日 下午5:49:47 
	 * @param results
	 * @param repeatColumnName
	 */
	public static List<String> cleanRepeatList(List<Map<String, String>> results, String repeatColumnName){
		try {
			if (NullUtil.isNotNull(results)) {
				int max=results.size();
				List<String> onlyList = new ArrayList<String>();
				if(max>1){
					for (int i = 0; i < max; i++) {
						String value = results.get(i).get(repeatColumnName);
						if(!onlyList.contains(value)){
							onlyList.add(value);
						}
					}
					return onlyList;
				}else if(max==1){
					onlyList.add(results.get(0).get(repeatColumnName));
					return onlyList;
				}
			}
		} catch (Exception e) {
			LoggerUtil.error("去除集合中的重复列失败："+e);
		}
		return null;
	}
	
	/**
	 * 过滤对象中某个属性值重复的，返回唯一值的对象集合
	 *@author chencaihui 
	 *@datetime 创建时间：2018年2月26日 下午2:42:16 
	 * @param results
	 * @param repeatColumnName
	 * @return
	 */
	public static <T> List<T> cleanRepeatObject(List<T> results, String repeatColumnName){
		try {
			if (NullUtil.isNotNull(results)) {
				int max = results.size();
				if(max > 1){
					List<String> repeatList = new ArrayList<String>();
					List<T> noRepeatList = new ArrayList<T>();
					for (int i = 0; i < max; i++) {
						T item = results.get(i);
						String value = String.valueOf(AnnotationUtil.getFieldValue(item, repeatColumnName));
						if(!repeatList.contains(value)){
							repeatList.add(value);
							noRepeatList.add(item);
						}
					}
					repeatList.clear();
					return noRepeatList;
				}
			}
		} catch (Exception e) {
			LoggerUtil.error("去除对象中的重复列失败："+e);
		}
		return results;
	}
	
	/**
	 * 将某列的值过滤重复并返回值集合
	 *@author chencaihui 
	 *@datetime 创建时间：2018年2月26日 下午2:50:58 
	 * @param results
	 * @param repeatColumnName
	 * @return
	 */
	public static <T> List<String> cleanRepeatValue(List<T> results, String repeatColumnName){
		try {
			if (NullUtil.isNotNull(results)) {
				int max = results.size();
				List<String> repeatList = new ArrayList<String>();
				for (int i = 0; i < max; i++) {
					T item = results.get(i);
					String value = String.valueOf(AnnotationUtil.getFieldValue(item, repeatColumnName));
					if(!repeatList.contains(value)){
						repeatList.add(value);
					}
				}
				return repeatList;
			}
		} catch (Exception e) {
			LoggerUtil.error("去除对象中的重复列失败："+e);
		}
		return new ArrayList<String>(0);
	}
	
	
	/**
	 * 获取两个集合某一属性值相等的数据
	 *@author chencaihui 
	 *@datetime 创建时间：2018年3月15日 下午5:00:24 
	 * @param source1s
	 * @param source2s
	 * @param repeatColumnName
	 * @return
	 */
	public static List<Map<String, String>> getRepeatList(List<Map<String, String>> source1s, List<Map<String, String>> source2s, String repeatColumnName){
		try {
			if (NullUtil.isNotNull(source1s) && NullUtil.isNotNull(source2s)) {
				int max1=source1s.size();
				int max2=source2s.size();
				List<Map<String, String>> repeatList = new ArrayList<Map<String, String>>();
				if(max1 > max2){//小的在里面
					for (int i = 0; i < max1; i++) {
						Map<String, String> source1 = source1s.get(i);
						String value1 = source1.get(repeatColumnName);
						for (int j = 0; j < max2; j++) {
							if(value1.equals(source2s.get(j).get(repeatColumnName))){
								repeatList.add(source1);
								source2s.remove(j);
								max2--;
								break;
							}
						}
					}
				}else{
					for (int i = 0; i < max2; i++) {
						Map<String, String> source2 = source2s.get(i);
						String value2 = source2.get(repeatColumnName);
						for (int j = 0; j < max1; j++) {
							if(value2.equals(source1s.get(j).get(repeatColumnName))){
								repeatList.add(source2);
								source1s.remove(j);
								max1--;
								break;
							}
						}
					}
				}
				return repeatList;
			}
		} catch (Exception e) {
			LoggerUtil.error("去除集合中的重复列失败："+e);
		}
		return new ArrayList<Map<String, String>>(0);
	}
	
	/***
	 * 过滤重复值
	 *@author chencaihui 
	 *@datetime 创建时间：2018年4月25日 上午11:20:24 
	 * @param source1s
	 * @param source2s
	 * @return
	 */
	public static List<String> getRepeatList(List<String> source1s, List<String> source2s){
		try {
			if (NullUtil.isNotNull(source1s) && NullUtil.isNotNull(source2s)) {
				int max1=source1s.size();
				int max2=source2s.size();
				List<String> repeatList = new ArrayList<String>();
				if(max1 > max2){//小的在里面
					for (int i = 0; i < max1; i++) {
						String value1 = source1s.get(i);
						for (int j = 0; j < max2; j++) {
							if(value1.equals(source2s.get(j))){
								repeatList.add(value1);
								source2s.remove(j);
								max2--;
								break;
							}
						}
					}
				}else{
					for (int i = 0; i < max2; i++) {
						String value2 = source2s.get(i);
						for (int j = 0; j < max1; j++) {
							if(value2.equals(source1s.get(j))){
								repeatList.add(value2);
								source1s.remove(j);
								max1--;
								break;
							}
						}
					}
				}
				return repeatList;
			}
		} catch (Exception e) {
			LoggerUtil.error("去除集合中的重复列失败："+e);
		}
		return new ArrayList<String>(0);
	}

	/**
	 * 计算集合中某列数值的平均分
	 *@author chencaihui 
	 *@datetime 创建时间：2018年4月8日 下午3:50:43 
	 * @param scores
	 * @param columnName
	 * @param defValue
	 * @return
	 */
	public static <T> double avg(List<T> scores, String columnName, double defValue) {
		if(NullUtil.isNotNull(scores)){
			int max = scores.size();
			double score = 0.00;
			for (int i = 0; i < max; i++) {
				T object = scores.get(i);
				String value = String.valueOf(AnnotationUtil.getFieldValue(object, columnName));
				if(NullUtil.isNotNull(value)){
					score += Double.valueOf(value);
				}
			}
			return score/(double)max;
		}
		return defValue;
	}
}
