package com.zhongjp.springTest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

/**
 * ApplicationContext自动检测实现BeanPostProcessor接口的配置元数据中定义的所有bean。
 * 它将这些bean注册为后处理器，以便以后在创建bean时调用它们
 * 然后Spring将在调用初始化回调方法之前和之后将每个bean实例传递给这两个方法，
 * 在这两个方法中，你可以按自己喜欢的方式处理bean实例。
 *
 * 通常，spring的DI容器会执行以下步骤来创建一个bean：
 *
 * 1、通过构造函数或工厂方法重新创建bean实例
 * 2、设置属性值值和对其它bean的引用
 * 3、调用所有*Aware接口中定义的setter方法
 * 4、将bean实例传递给每个bean后处理器的postProcessBeforeInitialization（）方法
 * 5、调用初始化回调方法
 * 6、将Bean实例传递到每个Bean后处理器的postProcessAfterInitialization（）方法
 * 7、这个bean已经可以被使用了
 * 8、当容器关闭时，调用销毁回调方法
 *
 * 如果有多个BeanPostProcessor实例，我们可以通过设置order属性或实现Ordered接口来控制执行顺序。
 */
//@Component
public class CustomBeanPostProcessor implements BeanPostProcessor, PriorityOrdered {
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("Called postProcessBeforeInitialization() for :" + beanName);
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		//System.out.println("Called postProcessAfterInitialization() for :" + beanName);
		return null;
	}

	@Override
	public int getOrder() {
		return 10;
	}
}
