package com.example.gohome.utils;

import java.util.UUID;

/** 
* id工具类
* @author chencaihui 
* @datetime 创建时间：2017年3月27日 上午10:03:30 
*/
public class IdHelper {

	/**
	 * 获取uuid
	 *@author chencaihui 
	 *@datetime 创建时间：2017年3月27日 上午10:05:47 
	 * @return
	 */
	public synchronized static final String getUUID(){
		try {
			return UUID.randomUUID().toString().replace("-", "");
		} catch (Exception e) {
			return RandomUtil.getRandomString(32);
		}
	}
	
	/**
	 * 获取自增长值，不存就从0开始
	 *@author chencaihui 
	 *@datetime 创建时间：2017年5月13日 下午10:26:29 
	 * @param key
	 * @return
	 */
	public synchronized static final long getNextId(String key){
		if(ConstantUtil.ID_MAP.containsKey(key)){
			long id = ConstantUtil.ID_MAP.get(key);
			ConstantUtil.ID_MAP.put(key, (id+1));
			return id;
		}
		ConstantUtil.ID_MAP.put(key, new Long(1));
		return new Long(0);
	}
}
