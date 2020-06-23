import com.zhongjp.springTest.JavaConfig;
import com.zhongjp.springTest.User;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ApplicationTest {

	/**
	 * 编程式使用IOC容器
	 *
	 * 整个过程分为三个步骤：资源定位、装载、注册。
	 *
	 * 装载：BeanDefinitionReader读取、解析Resource资源，将用户自定义的Bean表示成 IOC容器的内部数据结构：BeanDefinition。
	 * 		在IOC容器内部维护着一个BeanDefinition Map的数据结构；
	 * 		在配置文件中每一个<bean></bean>都对应着一个 BeanDefinition 对象。
	 *
	 * 	注册：向IOC容器注册解析得到的BeanDefinition对象，这个过程是通过BeanDefinitionRegistry接口来实现的。
	 * 	在IoC容器内部将解析得到的BeanDefinition注入到HashMap容器中，IoC容器其实就是通过这个HashMap来维护这些BeanDefinition对象的。
	 * 		在这里需要注意这个过程并没有完成依赖注入。因为在BeanFactory中，Bean的创建发生在应用第一次调用#getBean(...)方法时。
	 * 		可以通过预处理的方式，对某个Bean设置 lazyinit = false 属性，那么这个Bean的依赖注入就会在容器初始化的时候完成。
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
		ApplicationContext ac = new AnnotationConfigApplicationContext(JavaConfig.class);
		User user = ac.getBean(User.class);
		System.out.println(user.toString());
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
}
