package com.example.gohome.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 参数处理类
 * 
 * @author chencaihui
 * @Date 2015-10-14
 */
public class ParamUtil {

	private static Logger LoggerUtil = LoggerFactory.getLogger("ParamUtil");

	/**
	 * 获取所有提交的参数、值
	 * 
	 * @author chencaihui
	 * @datetime 创建时间：2017年10月17日 下午4:28:53
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getAllParams(final HttpServletRequest request) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.putAll(getGetPostParams(request));
		paramsMap.putAll(getPostParams(request));
		return paramsMap;
	}

	/**
	 * 获取get/post提交的参数、值
	 * 
	 * @author chencaihui
	 * @datetime 创建时间：2017年10月17日 下午4:28:53
	 * @param request
	 * @return
	 */
	public static Map<String, String> getGetPostParams(final HttpServletRequest request) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		@SuppressWarnings("unchecked")
		Enumeration<String> allParameter = request.getParameterNames();
		while (allParameter.hasMoreElements()) {
			String key = allParameter.nextElement();
			String[] values = request.getParameterValues(key);
			if (values != null && values.length > 1) {
				StringBuffer valuesb = new StringBuffer();
				for (String value : values) {
					if (NullUtil.isNull(value)) {
						continue;
					}
					if (valuesb.length() > 0) {
						valuesb.append(";");
					}
					valuesb.append(value.trim());
				}
				paramsMap.put(key, valuesb.toString());
			} else if (values != null && values.length == 1) {
				paramsMap.put(key, NullUtil.isNull(values[0]) ? null : values[0]);
			} else {
				String value = request.getParameter(key);
				paramsMap.put(key, NullUtil.isNull(value) ? null : value);
			}
		}
		return paramsMap;
	}

	/**
	 * 获取post提交的参数、值
	 * 
	 * @author chencaihui
	 * @datetime 创建时间：2017年10月17日 下午4:28:53
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getPostParams(final HttpServletRequest request) {
		try (InputStream is = request.getInputStream();) {
			byte[] paramByte = FileUtil.getBytes(is);
			if (NullUtil.isNotNull(paramByte)) {
				String paramstr = new String(paramByte, ConstantUtil.UTF8);
				if (paramstr.indexOf("=") != -1) {
					Map<String, String> paramsMap = new HashMap<String, String>();
					String[] params = paramstr.split("&");
					for (String param : params) {
						String[] ps = param.split("=");
						if (ps.length == 2)
							paramsMap.put(ps[0], ps[1]);
						else {
							paramsMap.put(ps[0], "");
						}
					}
					return paramsMap;
				}
				return GsonUtil.json2Object(paramstr, Map.class);
			}
		} catch (Exception e) {
			LoggerUtil.error(e.getMessage());
		}
		return newHashMap();
	}

	public static <K, V> HashMap<K, V> newHashMap() {
		return new HashMap<K, V>();
	}

	public static int getPageIndex(Map<String, ?> paramMap) {
		Integer pageIndex = null;
		if (NullUtil.isNotNull(paramMap)) {
			if (paramMap.containsKey("pageIndex")) {
				try {
					pageIndex = Integer.parseInt(paramMap.get("pageIndex").toString());
				} catch (NumberFormatException e) {
				}
			}
			if (NullUtil.isNull(pageIndex) && paramMap.containsKey("pageNo")) {
				try {
					pageIndex = Integer.parseInt(paramMap.get("pageNo").toString());
				} catch (NumberFormatException e) {
				}
			}
			if (NullUtil.isNull(pageIndex) && paramMap.containsKey("pageNumber")) {
				try {
					pageIndex = Integer.parseInt(paramMap.get("pageNumber").toString());
				} catch (NumberFormatException e) {
				}
			}
		}
		if (NullUtil.isNull(pageIndex) || pageIndex <= 0) {
			return ConstantUtil.INT_1;
		}
		return pageIndex;
	}

	public static int getPageSize(Map<String, ?> paramMap) {
		Integer pageSize = null;
		if (NullUtil.isNotNull(paramMap)) {
			if (paramMap.containsKey("pageSize")) {
				try {
					pageSize = Integer.parseInt(paramMap.get("pageSize").toString());
				} catch (NumberFormatException e) {
				}
			}
		}
		if (NullUtil.isNull(pageSize) || pageSize <= 0) {
			return ConstantUtil.DEFAULT_PAGE_SIZE;
		}
		return pageSize;
	}

	// 设置排序
	public static void setOrderBy(Map<String, Object> paramMap, String orderByKey, boolean isAsc) {
		if (paramMap != null) {
			paramMap.put("orderColumn", orderByKey);
			paramMap.put("orderTurn", isAsc ? ConstantUtil.ASC : ConstantUtil.DESC);
		}
	}

	// 获取排序属性
	public static String getOrderByColumn(Map<String, Object> paramMap) {
		if (paramMap != null && paramMap.containsKey("orderColumn")) {
			return String.valueOf(paramMap.get("orderColumn"));
		}
		return null;
	}

	// 获取排序值
	public static String getOrderByTurn(Map<String, Object> paramMap) {
		if (paramMap != null && paramMap.containsKey("orderTurn")) {
			return String.valueOf(paramMap.get("orderTurn"));
		}
		return ConstantUtil.DESC;
	}

}