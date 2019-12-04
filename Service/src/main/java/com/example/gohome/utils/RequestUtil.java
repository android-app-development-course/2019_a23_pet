package com.example.gohome.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**  
 *请求工具
* @author chencaihui 
* @datetime 创建时间：2017年3月18日 下午8:54:08 
*/
public class RequestUtil {
	
	private static Logger LoggerUtil = LoggerFactory.getLogger("RequestUtil");
	
	public static String getSessionId(HttpServletRequest request) {
		String sessionId = null;
		if(request!=null){
			try {
				sessionId = request.getSession().getId();
			} catch (Exception e) {
				sessionId = null;
			}
			if(NullUtil.isNull(sessionId)){
				sessionId = request.getRequestedSessionId();
			}
		}
		return sessionId;
	}

	//判断浏览器类型
	public static String getBrowserName(HttpServletRequest request) {
		final String agent = request.getHeader("User-Agent").toLowerCase();
		if (agent.indexOf("msie") > 0 || (agent.indexOf("gecko") > 0 && agent.indexOf("rv:11") > 0)) {
			return "ie";
		}else if (agent.indexOf("chrome") > 0) {
			return "chrome";
		}else if (agent.indexOf("opera") > 0) {
			return "opera";
		}else if (agent.indexOf("firefox") > 0) {
			return "firefox";
		} else if (agent.indexOf("webkit") > 0) {
			return "webkit";
		} else {
			return "other";
		}
	}
	
	/**
	 * 获取请求头信息
	 *@author chencaihui 
	 *@datetime 创建时间：2017年4月21日 下午4:52:42 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getHeader(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Enumeration<?> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String key = String.valueOf(headerNames.nextElement()).toLowerCase();
				String value = request.getHeader(key);
				map.put(key, value);
			}
			LoggerUtil.info("请求头信息:"+map.toString());
		} catch (Exception e) {
			LoggerUtil.error("读取请求头信息异常", e);
			return null;
		}
		return map;
	}
	
	/**
	 * 获取ip地址
	 * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
	 * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
	 * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
	 * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 192.168.1.100
	 * 用户真实IP为： 192.168.1.110
	 *@author chencaihui 
	 *@datetime 创建时间：2017年5月2日 下午4:31:47 
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("x-forwarded-for");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	
	
	

	
	
	
}
