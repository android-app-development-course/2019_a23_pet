package com.example.gohome.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * json和对象转换
 * @author chencaihui
 * @datetime 创建时间：2017年3月17日 下午11:05:41
 */
public class GsonUtil {
	
	public static String object2Json(Object object) {
		Gson gson = new Gson();
		return gson.toJson(object);
	}

	public static <T> T json2Object(String json, Class<T> clazz) throws JsonSyntaxException{
		Gson gson = new Gson();
		return gson.fromJson(json, clazz);
	}
}
