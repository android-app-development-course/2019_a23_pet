package com.example.gohome.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 响应工具类
 * @author chencaihui
 */
public class ResponseUtil {
	private static Logger logger = LoggerFactory.getLogger(ResponseUtil.class);
	public static void printJson(HttpServletResponse response, final Object result, int status) {
		response.reset();
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "No-cache");
		response.setDateHeader("Expires", 0);
		response.setStatus(status);// 设置状态码
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);// 设置ContentType
		response.setCharacterEncoding(ConstantUtil.UTF8);
		try (PrintWriter pw = response.getWriter();){
			if(result instanceof String){
				pw.write(String.valueOf(result));
			}else{
				pw.write(new ObjectMapper().writeValueAsString(result));
			}
		} catch (Exception e) {
			logger.error("输出流数据异常", e);
		} finally{
			try {
				response.flushBuffer();
			} catch (IOException e1) {}
		}
	}
	
	public static void printJson(HttpServletResponse response, final Object result) {
		printJson(response, result, HttpStatus.OK.value());
	}
	
	public static void outwriteFile(OutputStream os, File downloadFile){
		if(NullUtil.isNotNull(downloadFile)){
			try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(downloadFile));){
				byte[] buff = new byte[1024];
				int data;
				while ((data=(bis.read(buff)))!=-1) {
					os.write(buff, 0, data);
				}
			}catch (Exception e) {
				logger.warn("输出流数据异常:"+e.getMessage());
			}
		}
	}
	
	public static void outwriteFile(HttpServletResponse response, File downloadFile){
		try {
			try (OutputStream os = response.getOutputStream();){
				outwriteFile(os, downloadFile);
			}
		} catch (IOException e) {
			logger.warn("输出流数据异常:"+e.getMessage());
		}
	}
	
	/**重定向*/
	public static String redirect(String url){
		return "redirect:"+url;
	}
	
	public static <T> BaseResponse<T> fail() {
		BaseResponse<T> baseResponse = new BaseResponse<T>();
		baseResponse.setFlag(BaseFlagMessage.FLAG_FAIL);
		return baseResponse;
	}
	
	public static <T> BaseResponse<T> fail(int flag) {
		BaseResponse<T> baseResponse = new BaseResponse<T>();
		baseResponse.setFlag(flag);
		return baseResponse;
	}
	
	public static <T> BaseResponse<T> fail(int flag, String msg) {
		BaseResponse<T> baseResponse = new BaseResponse<T>();
		baseResponse.setMessage(flag, msg);
		return baseResponse;
	}
	
	public static <T> BaseResponse<T> fail(int flag, Exception e) {
		BaseResponse<T> baseResponse = new BaseResponse<T>();
		baseResponse.setMessage(flag, e.getMessage());
		return baseResponse;
	}
	
	public static <T> BaseResponse<T> success() {
		BaseResponse<T> baseResponse = new BaseResponse<T>();
		baseResponse.setFlag(BaseFlagMessage.FLAG_SUCCESS);
		return baseResponse;
	}
	
	public static <T> BaseResponse<T> success(int flag, String msg) {
		BaseResponse<T> baseResponse = new BaseResponse<T>();
		baseResponse.setMessage(flag, msg);
		return baseResponse;
	}
	
	public static <T> BaseResponse<List<T>> success(List<T> results) {
		BaseResponse<List<T>> baseResponse = new BaseResponse<List<T>>();
		baseResponse.setFlag(BaseFlagMessage.FLAG_SUCCESS);
		baseResponse.setDatas(results);
		return baseResponse;
	}
	
	public static <T> BaseResponse<List<T>> success(T[] results) {
		BaseResponse<List<T>> baseResponse = new BaseResponse<List<T>>();
		List<T> datas = new ArrayList<T>(results.length);
		for (T result : results) {
			datas.add(result);
		}
		baseResponse.setFlag(BaseFlagMessage.FLAG_SUCCESS);
		baseResponse.setDatas(datas);
		return baseResponse;
	}
	
	public static <T> BaseResponse<T> success(T result) {
		BaseResponse<T> baseResponse = new BaseResponse<T>();
		baseResponse.setFlag(BaseFlagMessage.FLAG_SUCCESS);
		baseResponse.setDatas(result);
		return baseResponse;
	}
	
	public static <T> BaseResponse<T> success(T result, String msg) {
		BaseResponse<T> baseResponse = new BaseResponse<T>();
		baseResponse.setMessage(BaseFlagMessage.FLAG_SUCCESS, msg);
		baseResponse.setDatas(result);
		return baseResponse;
	}
	
	public static <T> BaseResponse<List<T>> success(List<T> results, int total) {
		BaseResponse<List<T>> baseResponse = new BaseResponse<List<T>>();
		baseResponse.setFlag(BaseFlagMessage.FLAG_SUCCESS);
		baseResponse.setDatas(results);
		baseResponse.setTotal(total);
		return baseResponse;
	}
	
	public static <T> BaseResponse<List<T>> success(List<T> results, long total) {
		BaseResponse<List<T>> baseResponse = new BaseResponse<List<T>>();
		baseResponse.setFlag(BaseFlagMessage.FLAG_SUCCESS);
		baseResponse.setDatas(results);
		baseResponse.setTotal((int)total);
		return baseResponse;
	}
}
