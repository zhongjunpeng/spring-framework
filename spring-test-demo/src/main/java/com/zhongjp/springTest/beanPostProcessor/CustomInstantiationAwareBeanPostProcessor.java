package com.zhongjp.springTest.beanPostProcessor;

import com.zhongjp.springTest.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.util.HashMap;

/**
 * InstantiationAwareBeanPostProcessor接口继承BeanPostProcessor接口，
 * 它内部提供了3个方法，再加上BeanPostProcessor接口内部的2个方法，所以实现这个接口需要实现5个方法。
 * InstantiationAwareBeanPostProcessor接口的主要作用在于目标对象的实例化过程中需要处理的事情，
 * 包括实例化对象的前后过程以及实例的属性设置
 *
 * postProcessBeforeInstantiation方法是最先执行的方法，它在目标对象实例化之前调用，
 * 该方法的返回值类型是Object，我们可以返回任何类型的值。由于这个时候目标对象还未实例化，
 * 所以这个返回值可以用来代替原本该生成的目标对象的实例(比如代理对象)。
 * 如果该方法的返回值代替原本该生成的目标对象，后续只有postProcessAfterInitialization方法会调用，其它方法不再调用；否则按照正常的流程走
 *
 * postProcessAfterInstantiation方法在目标对象实例化之后调用，这个时候对象已经被实例化，
 * 但是该实例的属性还未被设置，都是null。如果该方法返回false，会忽略属性值的设置；如果返回true，会按照正常流程设置属性值
 *
 * postProcessPropertyValues方法对属性值进行修改(这个时候属性值还未被设置，但是我们可以修改原本该设置进去的属性值)。
 * 如果postProcessAfterInstantiation方法返回false，该方法不会被调用。可以在该方法内对属性值进行修改
 *
 * 父接口BeanPostProcessor的2个方法postProcessBeforeInitialization
 * 和postProcessAfterInitialization都是在目标对象被实例化之后，并且属性也被设置之后调用的
 * Instantiation表示实例化，Initialization表示初始化。实例化的意思在对象还未生成，初始化的意思在对象已经生成
 */

@Component
public class CustomInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
	/**
	 * 在实例化之前调用，如果返回null，一切按照正常顺序执行，如果返回的是一个实例的对象，那么这个将会跳过实例化、初始化的过程
	 * @param beanClass
	 * @param beanName
	 * @return
	 */
	@Override
	public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
		if (beanClass == User.class) {
			System.out.println("postProcessBeforeInstantiation执行");
			return null;
		}

		return null;
	}

	/**
	 * 在实例化之后，postProcessBeforeInitialization之前执行
	 * @param bean
	 * @param beanName
	 * @return
	 * @throws BeansException
	 */
	@Override
	public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
		if (bean instanceof User) {
			System.out.println("postProcessAfterInstantiation执行");
			return true;
		}

		return true;
	}

	/**
	 * 实例化之后调用，属性填充之前
	 * @param pvs PropertyValues对象，用于封装指定类的对象，简单来说就是PropertyValue的集合，里面相当于以key-value形式存放类的属性和值
	 * @param pds PropertyDescriptor对象数组，PropertyDescriptor相当于存储类的属性，不过可以调用set，get方法设置和获取对应属性的值
	 * @param bean 当前的bean
	 * @param beanName beanName
	 * @return 如果返回null，那么将不会进行后续的属性填充，比如依赖注入等，如果返回的pvs额外的添加了属性，那么后续会填充到该类对应的属性中。
	 */
	@Override
	public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
		if (pvs instanceof MutablePropertyValues && bean instanceof User){
			MutablePropertyValues mutablePropertyValues= (MutablePropertyValues) pvs;
			HashMap<Object, Object> map = new HashMap<>();
			map.put("username","ghs");
			map.put("uid",44);
			mutablePropertyValues.addPropertyValues(map);
			System.out.println("postProcessPropertyValues执行");
			return mutablePropertyValues;
		}

		/**使用pds设置值
		 if (bean instanceof User) {
		 for (PropertyDescriptor descriptor:pds) {
		 try {
		 if ("name".equals(descriptor.getName())) {
		 descriptor.getWriteMethod().invoke(bean, "陈加兵");
		 }else if("age".equals(descriptor.getName())){
		 descriptor.getWriteMethod().invoke(bean,40);
		 }
		 }catch (Exception e){
		 e.printStackTrace();
		 }
		 }
		 return null;
		 }**/
		return pvs;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof User) {
			System.out.println("postProcessBeforeInitialization执行");
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof User) {
			System.out.println("postProcessAfterInitialization执行");
		}
		return bean;
	}
}
