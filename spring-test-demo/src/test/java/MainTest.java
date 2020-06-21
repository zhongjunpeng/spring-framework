import com.zhongjp.springTest.JavaConfig;
import com.zhongjp.springTest.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainTest {

	@Test
	public void testApplicationContext() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(JavaConfig.class);
		User user = ac.getBean(User.class);
		System.out.println(user.toString());
	}
}
