package com.zhongjp.springTest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("第五步：BeanPostProcessor，对象" + beanName + "调用初始化方法之前的数据： " + bean.toString());
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("第八步：BeanPostProcessor，对象" + beanName + "调用初始化方法之后的数据：" + bean.toString());
		return bean;
	}
}
