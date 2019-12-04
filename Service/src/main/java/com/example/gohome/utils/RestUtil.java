package com.example.gohome.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求rest的工具类
 * @author chencaihui
 * @date 2015-07-20
 */
public class RestUtil {
	
	private static Logger LoggerUtil = LoggerFactory.getLogger("RestUtil");
	
	private static final String SERVLET_POST = "POST";
	private static final String SERVLET_GET = "GET";
	private static final String SERVLET_DELETE = "DELETE";
	private static final String SERVLET_PUT = "PUT";

	private static String prepareParam(Map<String, Object> paramMap) {
		StringBuffer sb = new StringBuffer();
		if (paramMap == null || paramMap.isEmpty()) {
			return "";
		} else {
			for (String key : paramMap.keySet()) {
				String value = paramMap.get(key).toString();
				if("token".equals(key)){
					try {
						value = URLEncoder.encode(value, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						
					}
				}
				if (sb.length() < 1) {
					sb.append(key).append("=").append(value);
				} else {
					sb.append("&").append(key).append("=").append(value);
				}
			}
			return sb.toString();
		}
	}
	
	/**
	 * post提交json数据
	 */
	public static String doPost(String urlStr, Map<String, Object> paramMap)
			throws Exception {
		HttpURLConnection conn = null;
		try{
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(SERVLET_POST);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setRequestProperty("Accept", "application/json"); // 设置接收数据的格�? 
			conn.setRequestProperty("Content-Type", "application/json"); // 设置发�?数据�?
			OutputStream os = conn.getOutputStream();
			os.write(JsonUtil.getJson(paramMap).getBytes("UTF-8"));
			os.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			StringBuffer result = new StringBuffer();
			while ((line = br.readLine()) != null) {
				result.append(line);
			}
			br.close();
			return result.toString();
		}catch (Exception e) {
			throw e;
		}finally{
			if(conn!=null) conn.disconnect();
		}
	}
	
	//提交表单
	public static String postFrom(String urlStr, Map<String, Object> paramMap) throws Exception {
		HttpURLConnection conn = null;
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			// 发�?POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.getOutputStream().write(RestUtil.prepareParam(paramMap).getBytes("UTF-8"));// 输入参数
			// 定义BufferedReader输入流来读取URL的响�?
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			StringBuffer result = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		} catch (Exception e) {
			throw e;
		} finally{
			if(conn!=null) conn.disconnect();
		}
	}
	
	//注册短信接口
	public static Map<String, String> doSmsRegister(String urlStr, String json, String headerStr){
		LoggerUtil.info("RestUtil doSmsRegister start......");
		Map<String, String> resultMap = new HashMap<String, String>();
		HttpsURLConnection conn = null;
		try{
			//跳过证书验证
			trustAllHttpsCertificates();
        	HttpsURLConnection.setDefaultHostnameVerifier(hv);
			URL url = new URL(urlStr);
			conn = (HttpsURLConnection) url.openConnection();
			conn.setRequestMethod(SERVLET_POST);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("X-EBPLAT-AUTH", headerStr);
			OutputStream os = conn.getOutputStream();
			System.out.println("注册短信接口接口连通");
			os.write(json.getBytes("UTF-8"));
			os.close();
			int code = conn.getResponseCode();
			System.out.println("注册短信接口code:"+code);
			LoggerUtil.info("响应码："+code);
			if(conn.getResponseCode()==200){
				resultMap.put("result", ConstantUtil.SUCCESS);
				String authStr = conn.getHeaderField("X-EBPLAT-AUTH");
				LoggerUtil.info("authStr:"+authStr);
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				String line;
				StringBuffer result = new StringBuffer();
				while ((line = br.readLine()) != null) {
					result.append(line);
				}
				br.close();
				LoggerUtil.info("响应数据："+result.toString());
				if(NullUtil.isNotNull(authStr)){
					String[] auths = authStr.split(",");
					if(auths[0].startsWith("AUTHCODE")){
						resultMap.put("AUTHCODE", auths[0].split("=")[1]);
						resultMap.put("SESSIONID", auths[1].split("=")[1]);
					}else{
						resultMap.put("AUTHCODE", auths[1].split("=")[1]);
						resultMap.put("SESSIONID", auths[0].split("=")[1]);
					}
				}
				return resultMap;
			}
		}catch (Exception e) {
			LoggerUtil.error(e.getMessage());
		}finally{
			if(conn!=null) conn.disconnect();
		}
		return null;
	}

	public static String doGet(String urlStr, Map<String, Object> paramMap)
			throws Exception {
		HttpURLConnection conn = null;
		try{
			String paramStr = prepareParam(paramMap);
			if (paramStr == null || paramStr.trim().length() < 1) {

			} else {
				urlStr += "?" + paramStr;
			}
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(SERVLET_GET);
			//conn.setRequestProperty("Content-Type", "text/html; charset=UTF-8");
			conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.connect();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line;
			StringBuffer result = new StringBuffer();
			while ((line = br.readLine()) != null) {
				result.append(line);
			}
			br.close();
			return result.toString();
		}catch (Exception e) {
			throw e;
		}finally{
			if(conn!=null) conn.disconnect();
		}
	}

	public static String doPut(String urlStr, Map<String, Object> paramMap)
			throws Exception {
		HttpURLConnection conn = null;
		try{
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(SERVLET_PUT);
			String paramStr = prepareParam(paramMap);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			OutputStream os = conn.getOutputStream();
			os.write(paramStr.toString().getBytes("UTF-8"));
			os.close();
	
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line;
			String result = "";
			while ((line = br.readLine()) != null) {
				result += "/n" + line;
			}
			br.close();	
			return result;
		}catch (Exception e) {
			throw e;
		}finally{
			if(conn!=null) conn.disconnect();
		}
	}

	public static int doDelete(String urlStr, Map<String, Object> paramMap)
			throws Exception {
		HttpURLConnection conn = null;
		try{
			String paramStr = prepareParam(paramMap);
			if (paramStr != null && paramStr.trim().length() > 0) {
				urlStr += "?" + paramStr;
			}
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod(SERVLET_DELETE);
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			return conn.getResponseCode();
		}catch (Exception e) {
			throw e;
		}finally{
			if(conn!=null) conn.disconnect();
		}
	}
	
	private static HostnameVerifier hv = new HostnameVerifier() {  
        public boolean verify(String urlHostName, SSLSession session) {  
            LoggerUtil.warn("Warning: URL Host: " + urlHostName + " vs. "  
                               + session.getPeerHost());
            return true;  
        }  
    };
	
	private static void trustAllHttpsCertificates() throws Exception {  
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];  
        javax.net.ssl.TrustManager tm = new CustomTrustManager();  
        trustAllCerts[0] = tm;  
        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext  
                .getInstance("SSL");  
        sc.init(null, trustAllCerts, null);  
        HttpsURLConnection.setDefaultSSLSocketFactory(sc
                .getSocketFactory());  
    }

	private static class CustomTrustManager implements javax.net.ssl.TrustManager, javax.net.ssl.X509TrustManager {
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}
		@SuppressWarnings("unused")
		public boolean isServerTrusted(java.security.cert.X509Certificate[] certs) {
			return true;
		}
		@SuppressWarnings("unused")
		public boolean isClientTrusted(java.security.cert.X509Certificate[] certs) {
			return true;
		}
		public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException {
			return;
		}
		public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException {
			return;
		}
	}  
	
	/*public static void main(String[] args) {
		String urlstr = "http://183.6.152.76:80/ykqapi/api/login/op/new";
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("loginName", "18819499920");
			paramMap.put("password", "999999");
			paramMap.put("equipmentId", "43SSGQE343526FGQ1UI6K");
			String result = doPost(urlstr, paramMap);
			LoggerUtil.info(result);
		} catch (Exception e) {
			
		}
	}*/
}
