package com.dsep.common.logger;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

import com.dsep.common.annotation.EnableLog;
/**
 * Loggor 注册类
 * @author OPCUser
 *
 */
public class LoggerInjector implements BeanPostProcessor{
	
	/* (non-Javadoc)
	 * 在spring初始化bean后进行logger的实例化
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(java.lang.Object, java.lang.String)
	 */
	@Override
	public Object postProcessAfterInitialization(final Object bean, String beanName)
			throws BeansException {
		// TODO Auto-generated method stub
		ReflectionUtils.doWithFields(bean.getClass(),new FieldCallback() { // spring reflection utils  
            @Override  
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {  
                ReflectionUtils.makeAccessible(field);  
  
                if (field.getType().equals(Logger.class) && field.isAnnotationPresent(EnableLog.class)) {//Log Annotation check  
                    EnableLog logAnnotation = field.getAnnotation(EnableLog.class);  
                    String loggerName = logAnnotation.value();  
                    Logger logger = null;  
                    if (loggerName != null && !loggerName.equals("")) {  
                        logger = Logger.getLogger(loggerName);  
                    }else {  
                        logger = Logger.getLogger(bean.getClass());  
                    }  
                    field.set(bean, logger);// init value  
                }  
            }  
        });  
		return bean;
	}

	
	 /**
	  * spring实例化bean之前的函数
	  */
	 @Override  
	 public Object postProcessBeforeInitialization(final Object bean, String beanName) throws BeansException {  
	       
	        return bean;  
	    }  

}
