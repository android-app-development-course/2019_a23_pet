package com.example.gohome.utils;

import net.sf.json.JSONObject;

import java.util.Map;

/**
 * json工具
 * 
 * @author chencaihui
 * @date 2016
 */
public class JsonUtil {

	public static String getJson(Map<String, Object> paramMap) {
		if (NullUtil.isNotNull(paramMap)) {
			JSONObject json = new JSONObject();
			for (String key : paramMap.keySet()) {
				json.put(key, paramMap.get(key));
			}
			return json.toString();
		}
		return "";
	}

}