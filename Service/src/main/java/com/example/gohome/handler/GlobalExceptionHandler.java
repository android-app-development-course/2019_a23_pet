package com.example.gohome.handler;

import com.example.gohome.exception.UpdatePasswordHandleException;
import com.example.gohome.exception.UpdatePasswordRecordException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


//自定义的异常处理类
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    private Map<String,Object> exceptionHandler(HttpServletRequest req, Exception e){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success" , false);
        modelMap.put("errMsg" , e.getMessage());
        return modelMap;
    }

    @ExceptionHandler(value = UpdatePasswordHandleException.class)
    @ResponseBody
    private Map<String,Object> exceptionHandler(HttpServletRequest req, UpdatePasswordHandleException e){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success" , false);
        modelMap.put("errMsg" , e.getMessage());
        return modelMap;
    }

    @ExceptionHandler(value = UpdatePasswordRecordException.class)
    @ResponseBody
    private Map<String,Object> exceptionHandler(HttpServletRequest req, UpdatePasswordRecordException e){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success" , false);
        modelMap.put("errMsg" , e.getMessage());
        return modelMap;
    }
}
