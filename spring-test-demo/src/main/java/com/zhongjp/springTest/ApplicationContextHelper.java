package com.zhongjp.springTest;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

public class ApplicationContextHelper implements ApplicationContextAware {

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		//Map<String,Object> beans = applicationContext.getBeansWithAnnotation(JavaConfig.class);
	}
}
