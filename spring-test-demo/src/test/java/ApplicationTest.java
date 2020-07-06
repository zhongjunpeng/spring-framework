import com.zhongjp.springTest.*;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApplicationTest {

	/**
	 * 编程式使用IOC容器
	 * <p>
	 * 整个过程分为三个步骤：资源定位、装载、注册。
	 * <p>
	 * 装载：BeanDefinitionReader读取、解析Resource资源，将用户自定义的Bean表示成 IOC容器的内部数据结构：BeanDefinition。
	 * 在IOC容器内部维护着一个BeanDefinition Map的数据结构；
	 * 在配置文件中每一个<bean></bean>都对应着一个 BeanDefinition 对象。
	 * <p>
	 * 注册：向IOC容器注册解析得到的BeanDefinition对象，这个过程是通过BeanDefinitionRegistry接口来实现的。
	 * 在IoC容器内部将解析得到的BeanDefinition注入到HashMap容器中，IoC容器其实就是通过这个HashMap来维护这些BeanDefinition对象的。
	 * 在这里需要注意这个过程并没有完成依赖注入。因为在BeanFactory中，Bean的创建发生在应用第一次调用#getBean(...)方法时。
	 * 可以通过预处理的方式，对某个Bean设置 lazyinit = false 属性，那么这个Bean的依赖注入就会在容器初始化的时候完成。
	 * 这一点跟ApplicationContext不一样，ApplicationContext是容器初始化时就把Bean创建了。
	 * <p>
	 * XML Resource => XML Document => Bean Definition 。
	 */
	@Test
	public void testWithFreshInputStream() {
		//资源定位
		Resource resource = new ClassPathResource("/xml/test.xml");
		DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		//加载、注册
		reader.loadBeanDefinitions(resource);
	}


	@Test
	public void testAnnotationConfigApplicationContext() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(JavaConfig.class);
		User user = ac.getBean(User.class);
		System.out.println(user.toString());
	}

	@Test
	public void testAnnotationConfigApplicationContextRegist() {
		// spring容器初始化 默认创建 DefaultListableBeanFactory
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
		// 创建 beanFactory 后置处理器
		ac.addBeanFactoryPostProcessor(new CustomBeanFactoryPostProcessor());
		// 将Java对象注册到 BeanDefinition
		ac.register(JavaConfig.class);
		// 实例化和初始化bean
		ac.refresh();
		User user = ac.getBean(User.class);
		System.out.println(user);
	}

	@Test
	public void testClassPathXmlApplicationContext() {
		// 用我们的配置文件来启动一个 ApplicationContext
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/xml/User.xml");
		System.out.println("context 启动成功");
		// 从 context 中取出我们的 Bean，而不是用 new MessageServiceImpl() 这种方式
		User user = context.getBean(User.class);
		// 这句将输出: hello world
		System.out.println(user.toString());
	}

	/**
	 * 例子：https://blog.csdn.net/zhanyu1/article/details/83114684
	 *
	 * BeanFactoryPostProcessor接口是针对bean容器的，它的实现类可以在当前BeanFactory初始化（spring容器加载bean定义文件）后，
	 * bean实例化之前修改bean的定义属性，达到影响之后实例化bean的效果。
	 * 也就是说，Spring允许BeanFactoryPostProcessor在容器实例化任何其它bean之前读取配置元数据，
	 * 并可以根据需要进行修改，例如可以把bean的scope从singleton改为prototype，也可以把property的值给修改掉。
	 * 可以同时配置多个BeanFactoryPostProcessor，并通过设置’order’属性来控制各个BeanFactoryPostProcessor的执行次序。
	 *
	 * BeanPostProcessor能在spring容器实例化bean之后，在执行bean的初始化方法前后，添加一些自己的处理逻辑。初始化方法包括以下两种：
	 *
	 * 1、实现InitializingBean接口的bean，对应方法为afterPropertiesSet
	 * 2、xml定义中，通过init-method设置的方法
	 *
	 * BeanPostProcessor是BeanFactoryPostProcessor之后执行的。
	 * 如果自定义了多个的BeanPostProcessor的实现类，通过实现Ordered接口，设置order属性，可以按照顺序执行实现类的方法
	 *
	 * InitializingBean接口为bean提供了初始化方法的方式，它只包括afterPropertiesSet方法，
	 * 凡是继承该接口的类，在初始化bean的时候都会执行该方法。
	 * 在Spring初始化bean的时候，如果该bean实现了InitializingBean接口，并且同时在配置文件中指定了init-method，
	 * 系统则是先调用afterPropertieSet()方法，然后再调用init-method中指定的方法。
	 * 那么这种方式在spring中是怎么实现的呢，通过查看Spring加载bean的源码类 AbstractAutowireCapableBeanFactory 可以看出其中的奥妙，
	 * AbstractAutowireCapableBeanFactory 类中的invokeInitMethods说的非常清楚
	 *
	 * 总结：分析以上结果：在bean实例化之前，首先执行BeanFactoryPostProcessor实现类的方法，
	 * 然后通过调用bean的无参构造函数实例化bean，并调用set方法注入属性值。bean实例化后，
	 * 执行初始化操作，调用两个初始化方法（两个初始化方法的顺序：先执行afterPropertiesSet，
	 * 再执行init-method）前后，执行了BeanPostProcessor实现类的两个方法。
	 *
	 */
	@Test
	public void postProcessorTest() {
		ApplicationContext context = new ClassPathXmlApplicationContext("xml/postprocessor.xml");
		CustomBean bean = (CustomBean) context.getBean("customBean");
		System.out.println("################ 实例化、初始化bean完成");
		System.out.println("****************下面输出结果");
		System.out.println("描述：" + bean.getDesc());
		System.out.println("备注：" + bean.getRemark());

	}

	@Test
	public void test123() {
		ExecutorService executorService = Executors.newFixedThreadPool(1);
		//executorService.execute();
	}
}
