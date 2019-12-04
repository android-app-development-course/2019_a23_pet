package com.example.gohome.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 返回结果体
 */
public class BaseFlagMessage implements Serializable {

	private static final long serialVersionUID = 5757465295311476873L;
	/** 成功 */
	public static Integer FLAG_SUCCESS = 200;
	/** 失败  */
	public static Integer FLAG_FAIL = 500;
	/** 等待  */
	public static Integer FLAG_WAIT = 300;
	/** 用户未登录 */
	public static Integer FLAG_NOT_LOGIN = 201;
	/*** 用户不存在 */
	public static Integer FLAG_USER_NOT_FIND = 202;
	/** 登录超时 */
	public static Integer FLAG_LOGIN_TIMEOUT = 203;

	/*** 参数错误 */
	public static Integer FLAG_PARAM_ERROR = 400;

	/*** Json串解析异常 */
	public static Integer FLAG_DATA_FORMAT_ERROR = 501;
	/** 数据不存在 */
	public static Integer FLAG_DATA_NOT_FIND = 502;
	/** 权限限制 */
	public static Integer FLAG_POWER_ERROR = 503;
	/** 系统未知错误 */
	public static Integer FLAG_UNKNOW = 505;
	
	
	/**
	 * 代码对应中文描述
	 */
	public static final Map<Integer, String> FLAG_MSG_MAP = new HashMap<Integer, String>();

	static {
		FLAG_MSG_MAP.put(FLAG_SUCCESS, "操作成功");
		FLAG_MSG_MAP.put(FLAG_FAIL, "操作失败");
		FLAG_MSG_MAP.put(FLAG_WAIT, "请稍候,正在处理中...");
		FLAG_MSG_MAP.put(FLAG_NOT_LOGIN, "未登录");
		FLAG_MSG_MAP.put(FLAG_USER_NOT_FIND, "用户不存在");
		FLAG_MSG_MAP.put(FLAG_LOGIN_TIMEOUT, "登录超时");
		FLAG_MSG_MAP.put(FLAG_PARAM_ERROR, "参数错误");
		FLAG_MSG_MAP.put(FLAG_UNKNOW, "未知错误");
		FLAG_MSG_MAP.put(FLAG_DATA_FORMAT_ERROR, "数据格式错误");
		FLAG_MSG_MAP.put(FLAG_DATA_NOT_FIND, "数据不存在");
		FLAG_MSG_MAP.put(FLAG_POWER_ERROR, "权限不足");
	}
}
