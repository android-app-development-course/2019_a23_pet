package com.example.gohome.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;


/**
 * 
 * 修改历史
 * ---编号---				---修改人---			---修改时间---				---描述---
 * 新需求                                          	陈秀红				2019年1月23日				降级需求
 *
 */

@SuppressWarnings("deprecation")
public class HttpUtils {

	private static PoolingHttpClientConnectionManager connMgr;
	private static RequestConfig requestConfig;
	private static final int MAX_TIMEOUT = 20000;
	
	private static Logger LoggerUtil = LoggerFactory.getLogger("HttpUtils");

	static {
		// 设置连接池
		connMgr = new PoolingHttpClientConnectionManager();
		// 设置连接池大小
		connMgr.setMaxTotal(100);
		connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
		RequestConfig.Builder configBuilder = RequestConfig.custom();
		// 设置连接超时
		configBuilder.setConnectTimeout(MAX_TIMEOUT);
		// 设置读取超时
		configBuilder.setSocketTimeout(MAX_TIMEOUT);
		// 设置从连接池获取连接实例的超时
		configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
		// 在提交请求之前 测试连接是否可用
		configBuilder.setStaleConnectionCheckEnabled(true);
		requestConfig = configBuilder.build();
		
	}
	
	//发送POST请求
	public static String postData(String postData, String postUrl) {
        try {
            URL url = new URL(postUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Length", "" + postData.length());
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), ConstantUtil.UTF8);
            out.write(postData);
            out.flush();
            out.close();
            //获取响应状态
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //获取响应内容体
                StringBuffer result = new StringBuffer();
                String line;
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), ConstantUtil.UTF8));
                while ((line = in.readLine()) != null) {
                	result.append(line);
                }
                in.close();
                return result.toString();
            }
        } catch (IOException e) {
        	LoggerUtil.error("提交数据异常："+e.getMessage());
        }
        return null;
    }
	
	/**
	 * post xml
	 *@author chencaihui 
	 *@datetime 创建时间：2017年8月9日 下午5:45:18 
	 * @param apiUrl
	 * @param xml
	 * @return
	 */
	public static String post(String apiUrl, String xml) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String httpStr = null;
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;
		try {
			httpPost.setConfig(requestConfig);
			StringEntity stringEntity = new StringEntity(xml, "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("text/xml");
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			httpStr = EntityUtils.toString(entity, "UTF-8");
		} catch (IOException e) {
			LoggerUtil.error(e.getMessage());
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					LoggerUtil.error(e.getMessage());
				}
			}
		}
		return httpStr;
	}

	/**
	 * 发送 SSL POST 请求（HTTPS），JSON形式
	 * 
	 * @param apiUrl
	 *            API接口URL
	 * @param json
	 *            JSON对象
	 * @return
	 */
	public static String doPostSSL(String apiUrl, String json) {
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
				.setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;
		String httpStr = null;

		try {
			httpPost.setConfig(requestConfig);
			StringEntity stringEntity = new StringEntity(json, "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			httpStr = EntityUtils.toString(entity, "utf-8");
		} catch (Exception e) {
			LoggerUtil.error(e.getMessage());
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					LoggerUtil.error(e.getMessage());
				}
			}
		}
		return httpStr;
	}
	
	/**
	 * 发送 SSL POST 请求（HTTPS），JSON形式<br/>并且可以添加请求头
	 *@author chencaihui 
	 *@datetime 创建时间：2018年2月6日 下午2:38:21 
	 * @param apiUrl 请求地址
	 * @param json json参数
	 * @param headerMap 请求头
	 * @return
	 */
	public static String doPostSSL(String apiUrl, String json, Map<String, String> headerMap) {
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
				.setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;
		String httpStr = null;
		try {
			httpPost.setConfig(requestConfig);
			StringEntity stringEntity = new StringEntity(json, "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			if(NullUtil.isNotNull(headerMap)){
				Iterator<String> its = headerMap.keySet().iterator();
				while(its.hasNext()){
					String key = its.next();
					httpPost.setHeader(key, headerMap.get(key));
				}
			}
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			httpStr = EntityUtils.toString(entity, "utf-8");
		} catch (Exception e) {
			LoggerUtil.info(e.getMessage());
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					LoggerUtil.error(e.getMessage());
				}
			}
		}
		return httpStr;
	}

	/**
	 * 发送 POST 请求（HTTP），JSON形式
	 * @param apiUrl
	 * @param json
	 *            json对象
	 * @return
	 */
	public static String doPost(String apiUrl, String json) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String httpStr = null;
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;
		try {
			httpPost.setConfig(requestConfig);
			StringEntity stringEntity = new StringEntity(json, "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			httpStr = EntityUtils.toString(entity, "UTF-8");
		} catch (IOException e) {
			LoggerUtil.info(e.getMessage());
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					LoggerUtil.error(e.getMessage());
				}
			}
		}
		return httpStr;
	}
	
	/**
	 * 发送 POST 请求（HTTP），JSON形式<br/>并且可以添加请求头
	 *@author chencaihui 
	 *@datetime 创建时间：2018年2月6日 下午2:46:03 
	 * @param apiUrl
	 * @param json
	 * @param headerMap 请求头
	 * @return
	 */
	public static String doPost(String apiUrl, String json, Map<String, String> headerMap) throws IOException{
		LoggerUtil.info("成功进入doPost方法-----开始创建httpClient对象...");
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String httpStr = null;
		LoggerUtil.info("开始创建httpPost对象...apiUrl为："+apiUrl);
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;
		try {
			httpPost.setConfig(requestConfig);
			StringEntity stringEntity = new StringEntity(json, "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			if(NullUtil.isNotNull(headerMap)){
				Iterator<String> its = headerMap.keySet().iterator();
				while(its.hasNext()){
					String key = its.next();
					httpPost.setHeader(key, headerMap.get(key));
				}
			}
			httpPost.setEntity(stringEntity);
			LoggerUtil.info("准备进行POST请求------apiUrl:"+apiUrl+"----json为："+json);
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			httpStr = EntityUtils.toString(entity, "UTF-8");
			LoggerUtil.info("POST请求完成，response为："+httpStr+"----status:"+response.getStatusLine().getStatusCode());
		} catch (IOException e) {
			//降级处理
			LoggerUtil.error("#############################doPost^^^^IOException^^^^################################");
			LoggerUtil.error("#############################doPost^^^^IOException^^^^################################");
			LoggerUtil.error(e.getMessage());
			throw e;
		} catch(Exception e) { //降级处理
			LoggerUtil.error("##############################doPost***Exception****###############################");
			LoggerUtil.error("##############################doPost***Exception****###############################");
			LoggerUtil.error(e.getMessage());
		}
		finally {
			LoggerUtil.error("##############################doPost finally ###############################");
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					LoggerUtil.error(e.getMessage());
				}
			}
		}
		return httpStr;
	}
	

	/**
	 * 发送 SSL POST 请求（HTTPS），K-V形式
	 * @param apiUrl
	 *            API接口URL
	 * @param params
	 *            参数map
	 * @return
	 */
	public static String doPostSSL(String apiUrl, Map<String, Object> params) {
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
				.setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;
		String httpStr = null;
		try {
			httpPost.setConfig(requestConfig);
			List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
				pairList.add(pair);
			}
			httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("utf-8")));
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			httpStr = EntityUtils.toString(entity, "utf-8");
		} catch (Exception e) {
			LoggerUtil.error(e.getMessage());
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					LoggerUtil.error(e.getMessage());
				}
			}
		}
		return httpStr;
	}

	/**
	 * 发送 POST 请求（HTTP），K-V形式
	 * @param apiUrl
	 *            API接口URL
	 * @param params
	 *            参数map
	 * @return
	 */
	public static String doPost(String apiUrl, Map<String, Object> params) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String httpStr = null;
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;
		try {
			httpPost.setConfig(requestConfig);
			List<NameValuePair> pairList = new ArrayList<>(params.size());
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
				pairList.add(pair);
			}
			httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			httpStr = EntityUtils.toString(entity, "UTF-8");
		} catch (IOException e) {
			LoggerUtil.error(e.getMessage());
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					LoggerUtil.error(e.getMessage());
				}
			}
		}
		return httpStr;
	}
	
	
    
    public static String doGet(String url){
    	CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
				.setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        HttpGet httpGet= null;  
        String result = null;  
        try {  
            httpGet = new HttpGet(url);  
            HttpResponse response = httpClient.execute(httpGet);  
            if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity, "utf-8");  
                }  
            }  
        } catch (Exception e) {  
        	LoggerUtil.error(e.getMessage());
        }  
        return result;  
    }

	/**
	 * 创建SSL安全连接
	 */
	private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
		SSLConnectionSocketFactory sslsf = null;
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			/*sslsf = new SSLConnectionSocketFactory(sslContext, new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					LoggerUtil.info("HostnameVerifier verify hostname:"+hostname);
					return true;
				}
			});*/
			sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {
				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
				@Override
				public void verify(String host, SSLSocket ssl) throws IOException {
				}
				@Override
				public void verify(String host, X509Certificate cert) throws SSLException {
				}
				@Override
				public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
				}
			});
		} catch (GeneralSecurityException e) {
			LoggerUtil.error(e.getMessage());
		}
		return sslsf;
	}
	
	/**
	 * post请求xml，返回map
	 * @param url
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> postXml(String url, String xml) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		StringEntity myEntity = new StringEntity(xml, "UTF-8");
		httppost.addHeader("Content-Type", "text/xml");
		httppost.setEntity(myEntity);
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity resEntity = response.getEntity();
		InputStream ins = resEntity.getContent();
		Document doc = reader.read(ins);
		Element root = doc.getRootElement();
		@SuppressWarnings("unchecked")
		List<Element> list = root.elements();
		for (Element e : list) {
			map.put(e.getName(), e.getText());
		}
		root.clearContent();
		return map;
	}
	
	/**
	 * 通过basic auth请求数据
	 * @param username
	 * @param password
	 * @param uri
	 * @param paramMap
	 * @return
	 */
	public static String httpClientWithBasicAuth(String username, String password, String uri, Map<String, String> paramMap) {
		try { 
		    // 创建HttpClientBuilder
		    HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		    CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		    HttpPost httpPost = new HttpPost(uri); 
		    //添加http头信息 
		    httpPost.addHeader("Authorization", "Basic " + Base64.getUrlEncoder().encodeToString((username + ":" + password).getBytes()));
		    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		    paramMap.forEach((k,v)->{
		         builder.addPart(k, new StringBody(v, ContentType.MULTIPART_FORM_DATA));
		    });
		    HttpEntity postEntity = builder.build();
		    httpPost.setEntity(postEntity);
		    String result = "";
		    HttpResponse httpResponse = null;
		    HttpEntity entity = null;
		    try {
		        httpResponse = closeableHttpClient.execute(httpPost);
		        entity = httpResponse.getEntity();
		        if( entity != null ){
		            result = EntityUtils.toString(entity);
		        }
		    } catch (ClientProtocolException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    // 关闭连接
		    closeableHttpClient.close();
//		    System.out.println("响应数据:"+result);
		    LoggerUtil.info("响应数据:"+result);
		    return result;
		}catch (Exception e) {
//		    System.out.println(e.getStackTrace());
		    LoggerUtil.info("异常信息:"+e.getStackTrace());
		}
		return null;
	}
}
