package com.example.gohome.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取ApplicationContext和Object的工具类
 * @author chencaihui
 */
@Component
public class SpringBeanUtil implements ApplicationContextAware {
	
    private static ApplicationContext applicationContext;
 
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
    	SpringBeanUtil.applicationContext = applicationContext;
    }
    
    /**
     * 获取applicationContext对象
     * @return
     */
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }
     
    /**
     * 根据bean的id来查找对象
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
	public static <T> T getBean(String id){
        return (T)applicationContext.getBean(id);
    }
     
    /**
     * 根据bean的class来查找对象
     * @param c
     * @return
     */
    public static <T> T getBean(Class<T> c){
        return applicationContext.getBean(c);
    }
     
}